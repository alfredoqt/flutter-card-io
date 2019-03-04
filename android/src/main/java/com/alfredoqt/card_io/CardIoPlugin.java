package com.alfredoqt.card_io;

import android.app.Activity;
import android.content.Intent;

import io.card.payment.CardIOActivity;
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

            CardIoExtras cardIoExtras = CardIoExtras.fromMethodCall(call);

            Intent intent = new Intent(activity, CardIOActivity.class)
                    .putExtra(CardIOActivity.EXTRA_NO_CAMERA, cardIoExtras.noCamera())
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, cardIoExtras.requireExpiry())
                    .putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, cardIoExtras.scanExpiry())
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, cardIoExtras.requireCVV())
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, cardIoExtras.requirePostalCode())
                    .putExtra(CardIOActivity.EXTRA_RESTRICT_POSTAL_CODE_TO_NUMERIC_ONLY, cardIoExtras.restrictPostalCodeToNumericOnly())
                    .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, cardIoExtras.requireCardholderName())
                    .putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, cardIoExtras.suppressManualEntry())
                    .putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, cardIoExtras.useCardIOLogo())
                    .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, cardIoExtras.getLanguageOrLocale())
                    .putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, cardIoExtras.usePayPalActionBarIcon())
                    .putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, cardIoExtras.keepApplicationTheme())
                    .putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, cardIoExtras.getGuideColor())
                    .putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, cardIoExtras.suppressConfirmation())
                    .putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, cardIoExtras.suppressScan())
                    .putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, cardIoExtras.returnCardImage())
                    .putExtra(CardIOActivity.EXTRA_SCAN_INSTRUCTIONS, cardIoExtras.getScanInstructions())
                    .putExtra(CardIOActivity.EXTRA_UNBLUR_DIGITS, cardIoExtras.getUnblurDigits())
                    .putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, cardIoExtras.getCapturedCardImage())
                    .putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, cardIoExtras.hideCardIOLogo());

            activity.startActivityForResult(intent, REQUEST_CODE);
        } else {
            result.notImplemented();
        }
    }

    @Override
    public boolean onActivityResult(int i, int i1, Intent intent) {
        return false;
    }
}
