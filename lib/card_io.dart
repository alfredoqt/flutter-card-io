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
    final Map<String, dynamic> options = {
      'languageOrLocale': languageOrLocale,
      'keepApplicationTheme': keepApplicationTheme,
      'guideColor': guideColor,
      'suppressConfirmation': suppressConfirmation,
      'suppressManualEntry': suppressManualEntry,
      'suppressScan': suppressScan,
      'scanInstructions': scanInstructions,
      'hideCardIOLogo': hideCardIOLogo,
      'requireExpiry': requireExpiry,
      'requireCVV': requireCVV,
      'scanExpiry': scanExpiry,
      'requirePostalCode': requirePostalCode,
      'restrictPostalCodeToNumericOnly': restrictPostalCodeToNumericOnly,
      'requireCardholderName': requireCardholderName,
      'useCardIOLogo': useCardIOLogo,
    };
    return CardIoResult.fromJson(
        await _channel.invokeMethod<Map<String, dynamic>>('scanCard', options));
  }
}
