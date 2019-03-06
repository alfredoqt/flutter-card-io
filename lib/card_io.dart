import 'dart:async';

import 'package:flutter/services.dart';

class CardIoResult {
  String cardholderName;
  String cardNumber;
  String cardType;
  String redactedCardNumber;
  int expiryMonth;
  int expiryYear;
  String cvv;
  String postalCode;

  CardIoResult.fromJson(Map<String, dynamic> json)
      : cardholderName = json['cardholderName'],
        cardNumber = json['cardNumber'],
        cardType = json['cardType'],
        redactedCardNumber = json['redactedCardNumber'],
        expiryMonth = json['expiryMonth'],
        expiryYear = json['expiryYear'],
        cvv = json['cvv'],
        postalCode = json['postalCode'];
}

class CardIo {
  static const MethodChannel _channel = const MethodChannel('card_io');

  static Future<CardIoResult> scanCard({
    String languageOrLocale,
    bool keepApplicationTheme,
    int guideColor,
    bool suppressConfirmation,
    bool suppressManualEntry,
    bool suppressScan,
    String scanInstructions,
    bool hideCardIOLogo,
    bool requireExpiry,
    bool requireCVV,
    bool scanExpiry,
    bool requirePostalCode,
    bool restrictPostalCodeToNumericOnly,
    bool requireCardholderName,
    bool useCardIOLogo,
  }) async {
    // final Map<String, dynamic> options = {
    //   'languageOrLocale': languageOrLocale,
    //   'keepApplicationTheme': keepApplicationTheme,
    //   'guideColor': guideColor,
    //   'suppressConfirmation': suppressConfirmation,
    //   'suppressManualEntry': suppressManualEntry,
    //   'suppressScan': suppressScan,
    //   'scanInstructions': scanInstructions,
    //   'hideCardIOLogo': hideCardIOLogo,
    //   'requireExpiry': requireExpiry,
    //   'requireCVV': requireCVV,
    //   'scanExpiry': scanExpiry,
    //   'requirePostalCode': requirePostalCode,
    //   'restrictPostalCodeToNumericOnly': restrictPostalCodeToNumericOnly,
    //   'requireCardholderName': requireCardholderName,
    //   'useCardIOLogo': useCardIOLogo,
    // };
    Map<String, dynamic> options = new Map();
    if (languageOrLocale != null) {
      options['languageOrLocale'] = languageOrLocale;
    }
    if (keepApplicationTheme != null) {
      options['keepApplicationTheme'] = keepApplicationTheme;
    }
    if (guideColor != null) {
      options['guideColor'] = guideColor;
    }
    if (suppressConfirmation != null) {
      options['suppressConfirmation'] = suppressConfirmation;
    }
    if (suppressManualEntry != null) {
      options['suppressManualEntry'] = suppressManualEntry;
    }
    if (suppressScan != null) {
      options['suppressScan'] = suppressScan;
    }
    if (scanInstructions != null) {
      options['scanInstructions'] = scanInstructions;
    }
    if (hideCardIOLogo != null) {
      options['hideCardIOLogo'] = hideCardIOLogo;
    }
    if (requireExpiry != null) {
      options['requireExpiry'] = requireExpiry;
    }
    if (requireCVV != null) {
      options['requireCVV'] = requireCVV;
    }
    if (scanExpiry != null) {
      options['scanExpiry'] = scanExpiry;
    }
    if (requirePostalCode != null) {
      options['requirePostalCode'] = requirePostalCode;
    }
    if (restrictPostalCodeToNumericOnly != null) {
      options['restrictPostalCodeToNumericOnly'] =
          restrictPostalCodeToNumericOnly;
    }
    if (requireCardholderName != null) {
      options['requireCardholderName'] = requireCardholderName;
    }
    if (useCardIOLogo != null) {
      options['useCardIOLogo'] = useCardIOLogo;
    }
    return CardIoResult.fromJson(
        await _channel.invokeMethod<Map<String, dynamic>>('scanCard', options));
  }
}
