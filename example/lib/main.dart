import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:card_io/card_io.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _cardNumber = null;


  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> scanCreditCardInfo() async {
    CardIoResult result = await CardIo.scanCard(requireCardholderName: true, scanExpiry: true, requireExpiry: true, requireCVV: true, keepApplicationTheme: true);

    setState(() {
      _cardNumber = result.cardNumber;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              RaisedButton(onPressed: scanCreditCardInfo),
              _cardNumber != null ? Text(_cardNumber) : Text('missing')
            ],
          )
        ),
      ),
    );
  }
}
