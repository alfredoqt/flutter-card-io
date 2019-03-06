#import "CardIoPlugin.h"
#import "CardIO.h"

@interface CardIoPlugin ()<CardIOPaymentViewControllerDelegate>
@end

@implementation CardIoPlugin {
    // Save a reference to the view contoller
    UIViewController* _rootViewController;
    // The arguments passed from the client
    NSDictionary* _cardIOArguments;
    // Store the result to call it elsewhere
    FlutterResult* _result;
    // Save a reference to start the CardIOPaymentViewController
    CardIOPaymentViewController* _cardIOPaymentViewController;
}

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
    FlutterMethodChannel* channel = [FlutterMethodChannel
                                     methodChannelWithName:@"card_io"
                                     binaryMessenger:[registrar messenger]];
    
    // Get the root view controller
    UIViewController* rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
    // Custom initialization with the root view controller
    
    CardIoPlugin* instance = [[CardIoPlugin alloc] initWithViewController:rootViewController];
    [registrar addMethodCallDelegate:instance channel:channel];
}

// Custom initializer
- (instancetype)initWithViewController:(UIViewController*)viewController {
    // Do the initialization
    self = [super init];
    
    // Custom initialization
    if (self) {
        _rootViewController = viewController;
        
        // Initialize the payment view controller;
        _rootViewController = [[CardIOPaymentViewController alloc] initWithPaymentDelegate:self];
    }
    
    return self;
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
    if ([@"scanCard" isEqualToString:call.method]) {
        // TODO: Check whether this is required
        _cardIOPaymentViewController.delegate = self;
        
        // Check for duplicate calls
        if (_result) {
            // Send back an error
            result([FlutterError errorWithCode:@"multiple_request" message:@"Cancelled by a second request." details:nil]);
        }
        _result = result;
        _cardIOArguments = call.arguments;
        // Setup default parameters
        _cardIOPaymentViewController.languageOrLocale = [_cardIOArguments objectForKey:@"languageOrLocale"];
        _cardIOPaymentViewController.keepStatusBarStyle = [_cardIOArguments objectForKey:@"keepApplicationTheme"] ? [[_cardIOArguments objectForKey:@"keepApplicationTheme"] boolValue] : false;
        
        UIColor* guideColor = nil;
        if ([_cardIOArguments objectForKey:@"guideColor"]) {
            // Construct the color from an int
            int rgbValue = [[_cardIOArguments objectForKey:@"guideColor"] intValue];
            guideColor = [UIColor colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 green:((float)((rgbValue & 0xFF00) >> 8))/255.0 blue:((float)(rgbValue & 0xFF))/255.0 alpha:1.0];
        }
        _cardIOPaymentViewController.guideColor = guideColor;
        _cardIOPaymentViewController.suppressScanConfirmation = [_cardIOArguments objectForKey:@"suppressConfirmation"] ? [[_cardIOArguments objectForKey:@"suppressConfirmation"] boolValue] : false;
        _cardIOPaymentViewController.disableManualEntryButtons = [_cardIOArguments objectForKey:@"suppressManualEntry"] ? [[_cardIOArguments objectForKey:@"suppressManualEntry"] boolValue] : false;
        
        // TODO: Test this to know whether it is the same flag as SUPPRESS_SCAN in the Android SDK
        _cardIOPaymentViewController.suppressScannedCardImage = [_cardIOArguments objectForKey:@"suppressScan"] ? [[_cardIOArguments objectForKey:@"suppressScan"] boolValue] : false;
        _cardIOPaymentViewController.scanInstructions = [_cardIOArguments objectForKey:@"scanInstructions"];
        _cardIOPaymentViewController.hideCardIOLogo = [_cardIOArguments objectForKey:@"hideCardIOLogo"] ? [[_cardIOArguments objectForKey:@"hideCardIOLogo"] boolValue] : false;
        
        // Setting the same defaults as in Android SDK, for some strange reason they differ
        _cardIOPaymentViewController.collectExpiry = [_cardIOArguments objectForKey:@"requireExpiry"] ? [[_cardIOArguments objectForKey:@"requireExpiry"] boolValue] : false;
        _cardIOPaymentViewController.collectCVV = [_cardIOArguments objectForKey:@"requireCVV"] ? [[_cardIOArguments objectForKey:@"requireCVV"] boolValue] : false;
        _cardIOPaymentViewController.scanExpiry = [_cardIOArguments objectForKey:@"scanExpiry"] ? [[_cardIOArguments objectForKey:@"scanExpiry"] boolValue] : true;
        _cardIOPaymentViewController.collectPostalCode = [_cardIOArguments objectForKey:@"requirePostalCode"] ? [[_cardIOArguments objectForKey:@"requirePostalCode"] boolValue] : false;
        _cardIOPaymentViewController.restrictPostalCodeToNumericOnly = [_cardIOArguments objectForKey:@"restrictPostalCodeToNumericOnly"] ? [[_cardIOArguments objectForKey:@"restrictPostalCodeToNumericOnly"] boolValue] : false;
        _cardIOPaymentViewController.collectCardholderName = [_cardIOArguments objectForKey:@"requireCardholderName"] ? [[_cardIOArguments objectForKey:@"requireCardholderName"] boolValue] : false;
        _cardIOPaymentViewController.useCardIOLogo = [_cardIOArguments objectForKey:@"useCardIOLogo"] ? [[_cardIOArguments objectForKey:@"useCardIOLogo"] boolValue] : false;
        
        // Present the card payment view controller
        [_rootViewController presentViewController:_cardIOPaymentViewController animated:YES completion:nil];
    } else {
        result(FlutterMethodNotImplemented);
    }
}

- (void)userDidCancelPaymentViewController:(CardIOPaymentViewController *)paymentViewController {
    // Dismiss and send back null to the user
    [_cardIOPaymentViewController dismissViewControllerAnimated:YES completion:nil];
    _result([NSNull null]);
    
    // Reset everything
    _result = nil;
    _cardIOArguments = nil;
}

- (void)userDidProvideCreditCardInfo:(CardIOCreditCardInfo *)cardInfo inPaymentViewController:(CardIOPaymentViewController *)paymentViewController {
    // Store the card type
    NSString* cardType = nil;
    
    // TODO: Maybe we need to modify the SDK to support the other card types supported in the Android SDK
    if (cardInfo.cardType != CardIOCreditCardTypeUnrecognized && cardInfo.cardType != CardIOCreditCardTypeAmbiguous) {
        switch (cardInfo.cardType) {
            case CardIOCreditCardTypeAmex:
                cardType = @"Amex";
                break;
            case CardIOCreditCardTypeJCB:
                cardType = @"JCB";
                break;
            case CardIOCreditCardTypeVisa:
                cardType = @"Visa";
                break;
            case CardIOCreditCardTypeDiscover:
                cardType = @"Discover";
                break;
            case CardIOCreditCardTypeMastercard:
                cardType = @"Mastercard";
                break;
            default:
                break;
        }
    }
    
    [_cardIOPaymentViewController dismissViewControllerAnimated:YES completion:nil];
    
    // Set the results map and send it back to the client
    _result(@{
              @"cardholderName": ObjectOrNull(cardInfo.cardholderName),
              @"cardNumber": ObjectOrNull(cardInfo.cardNumber),
              @"cardType": ObjectOrNull(cardType),
              @"redactedCardNumber": ObjectOrNull(cardInfo.redactedCardNumber),
              @"expiryMonth": ObjectOrNull(@(cardInfo.expiryMonth)),
              @"expiryYear": ObjectOrNull(@(cardInfo.expiryYear)),
              @"cvv": ObjectOrNull(cardInfo.cvv),
              @"postalCode": ObjectOrNull(cardInfo.postalCode)
              });
    // Reset everything
    _result = nil;
    _cardIOArguments = nil;
}

static id ObjectOrNull(id object) {
    return object ?: [NSNull null];
}


@end
