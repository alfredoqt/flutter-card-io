package com.alfredoqt.card_io;

import android.content.Intent;

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

    private Result _result;

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "card_io");
        channel.setMethodCallHandler(new CardIoPlugin());
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("getPlatformVersion")) {
            CardIoExtras cardIoExtras = CardIoExtras.fromMethodCall(call);
        } else {
            result.notImplemented();
        }
    }

    @Override
    public boolean onActivityResult(int i, int i1, Intent intent) {
        return false;
    }
}
