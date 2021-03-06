package com.alfredoqt.card_io;

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.ActivityResultListener;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * CardIoPlugin
 */
public class CardIoPlugin implements MethodCallHandler, ActivityResultListener {

    private static final int REQUEST_CODE = 108;

    private Result _mResult;
    private Registrar _mRegistrar;

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "card_io");
        CardIoPlugin instance = new CardIoPlugin();
        instance._mRegistrar = registrar;
        registrar.addActivityResultListener(instance);
        channel.setMethodCallHandler(instance);
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("scanCard")) {
            Activity activity = _mRegistrar.activity();

            if (activity == null) {
                result.error("no_activity", "card_io plugin requires a foreground activity.", null);
                return;
            }

            // Check if it was previously requested
            if (_mResult != null) {
                result.error("multiple_request", "Cancelled by a second request.", null);
                return;
            }

            CardIoExtras cardIoExtras = CardIoExtras.fromMethodCall(call);

            _mResult = result;

            Intent intent = new Intent(activity, CardIOActivity.class)
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, cardIoExtras.requireExpiry())
                    .putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, cardIoExtras.scanExpiry())
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, cardIoExtras.requireCVV())
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, cardIoExtras.requirePostalCode())
                    .putExtra(CardIOActivity.EXTRA_RESTRICT_POSTAL_CODE_TO_NUMERIC_ONLY, cardIoExtras.restrictPostalCodeToNumericOnly())
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, cardIoExtras.requireCardholderName())
                    .putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, cardIoExtras.suppressManualEntry())
                    .putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, cardIoExtras.useCardIOLogo())
                    .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, cardIoExtras.getLanguageOrLocale())
                    .putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, cardIoExtras.keepApplicationTheme())
                    .putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, cardIoExtras.getGuideColor())
                    .putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, cardIoExtras.suppressConfirmation())
                    .putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, cardIoExtras.suppressScan())
                    .putExtra(CardIOActivity.EXTRA_SCAN_INSTRUCTIONS, cardIoExtras.getScanInstructions())
                    .putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, cardIoExtras.hideCardIOLogo());

            activity.startActivityForResult(intent, REQUEST_CODE);
        } else {
            result.notImplemented();
        }
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                // Dynamic map values
                Map<String, Object> response = new HashMap<>();

                // Put all the data from the result
                response.put("cardNumber", scanResult.cardNumber);
                response.put("expiryMonth", scanResult.expiryMonth);
                response.put("expiryYear", scanResult.expiryYear);
                response.put("cvv", scanResult.cvv);
                response.put("postalCode", scanResult.postalCode);
                response.put("redactedCardNumber", scanResult.getRedactedCardNumber());
                response.put("cardholderName", scanResult.cardholderName);

                String cardType = null;

                // Find the card type
                switch (scanResult.getCardType()) {
                    case AMEX:
                        cardType = "Amex";
                        break;
                    case JCB:
                        cardType = "JCB";
                        break;
                    case VISA:
                        cardType = "Visa";
                        break;
                    case MASTERCARD:
                        cardType = "Mastercard";
                        break;
                    case DISCOVER:
                        cardType = "Discover";
                        break;
                    default:
                        // TODO: Include MAESTRO and DINERSCLUB once the iOS source is modified
                        // Includes: UNKNOWN AND INSUFFICIENT_DIGITS
                        break;
                }
                response.put("cardType", cardType);

                // Send back the response
                _mResult.success(response);
            }
            else {
                // Send null back
                _mResult.success(null);
            }
            // Clean up memory and prepare for future requests
            _mResult = null;
            return true;
        }
        return false;
    }
}
