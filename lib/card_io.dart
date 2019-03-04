import 'dart:async';

import 'package:flutter/services.dart';

class CardIo {
  static const MethodChannel _channel = const MethodChannel('card_io');

  static Future<Map<String, dynamic>> scanCard(Map<String, dynamic> options) =>
      _channel.invokeMethod<Map<String, dynamic>>('scanCard');
}
