import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:card_io/card_io.dart';

void main() {
  const MethodChannel channel = MethodChannel('card_io');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  // test('getPlatformVersion', () async {
  //   expect(await CardIo.platformVersion, '42');
  // });
}
