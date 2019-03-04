package com.alfredoqt.card_io;

import io.flutter.plugin.common.MethodCall;
import android.graphics.Color;

public class CardIoExtras {

    private boolean _mNoCamera;
    private boolean _mScanExpiry;
    private int _mUnblurDigits;
    private boolean _mRequireCVV;
    private boolean _mRequirePostalCode;
    private boolean _mRestrictPostalCodeToNumericOnly;
    private boolean _mRequireCardholderName;
    private boolean _mUseCardIOLogo;
    private boolean _mManualEntryResult;
    private boolean _mSuppressManualEntry;
    private String _mLanguageOrLocale;
    private int _mGuideColor;
    private boolean _mSuppressConfirmation;
    private boolean _mHideCardIOLogo;
    private String _mScanInstructions;
    private boolean _mSuppressScan;
    private String _mCapturedCardImage;
    private boolean _mReturnCardImage;
    private boolean _mUsePayPalActionBarIcon;
    private boolean _mKeepApplicationTheme;
    private boolean _mRequireExpiry;

    public CardIoExtras() {
        _mNoCamera = false;
        _mScanExpiry = true;
        _mRequireExpiry = false;
        _mUnblurDigits = -1;
        _mRequireCVV = false;
        _mRequirePostalCode = false;
        _mRestrictPostalCodeToNumericOnly = false;
        _mRequireCardholderName = false;
        _mUseCardIOLogo = false;
        _mManualEntryResult = false;
        _mSuppressManualEntry = false;
        _mLanguageOrLocale = null;
        _mGuideColor = Color.GREEN;
        _mSuppressConfirmation = true;
        _mHideCardIOLogo = false;
        _mScanInstructions = null;
        _mSuppressScan = false;
        _mCapturedCardImage = null;
        _mReturnCardImage = false;
        _mUsePayPalActionBarIcon = false;
        _mKeepApplicationTheme = false;
    }

    public static CardIoExtras fromMethodCall(MethodCall methodCall) {
        CardIoExtras extras = new CardIoExtras();
        try {
            if (methodCall.hasArgument("noCamera")) {
                extras._mNoCamera = methodCall.argument("noCamera");
            }
            if (methodCall.hasArgument("scanExpiry")) {
                extras._mScanExpiry = methodCall.argument("scanExpiry");
            }
            if (methodCall.hasArgument("requireExpiry")) {
                extras._mRequireExpiry = methodCall.argument("requireExpiry");
            }
            if (methodCall.hasArgument("unblurDigits")) {
                extras._mUnblurDigits = methodCall.argument("unblurDigits");
            }
            if (methodCall.hasArgument("requireCVV")) {
                extras._mRequireCVV = methodCall.argument("requireCVV");
            }
            if (methodCall.hasArgument("requirePostalCode")) {
                extras._mRequirePostalCode = methodCall.argument("requirePostalCode");
            }
            if (methodCall.hasArgument("restrictPostalCodeToNumericOnly")) {
                extras._mRestrictPostalCodeToNumericOnly =
                        methodCall.argument("restrictPostalCodeToNumericOnly");
            }
            if (methodCall.hasArgument("requireCardholderName")) {
                extras._mRequireCardholderName =
                        methodCall.argument("requireCardholderName");
            }
            if (methodCall.hasArgument("useCardIOLogo")) {
                extras._mUseCardIOLogo =
                        methodCall.argument("useCardIOLogo");
            }
            if (methodCall.hasArgument("manualEntryResult")) {
                extras._mManualEntryResult =
                        methodCall.argument("manualEntryResult");
            }
            if (methodCall.hasArgument("languageOrLocale")) {
                extras._mLanguageOrLocale =
                        methodCall.argument("languageOrLocale");
            }
            if (methodCall.hasArgument("guideColor")) {
                extras._mGuideColor =
                        methodCall.argument("guideColor");
            }
            if (methodCall.hasArgument("suppressConfirmation")) {
                extras._mSuppressConfirmation =
                        methodCall.argument("suppressConfirmation");
            }
            if (methodCall.hasArgument("hideCardIOLogo")) {
                extras._mHideCardIOLogo =
                        methodCall.argument("hideCardIOLogo");
            }
            if (methodCall.hasArgument("scanInstructions")) {
                extras._mScanInstructions =
                        methodCall.argument("scanInstructions");
            }
            if (methodCall.hasArgument("suppressScan")) {
                extras._mSuppressScan =
                        methodCall.argument("suppressScan");
            }
            if (methodCall.hasArgument("capturedCardImage")) {
                extras._mCapturedCardImage =
                        methodCall.argument("capturedCardImage");
            }
            if (methodCall.hasArgument("returnCardImage")) {
                extras._mReturnCardImage =
                        methodCall.argument("returnCardImage");
            }
            if (methodCall.hasArgument("usePayPalActionBarIcon")) {
                extras._mUsePayPalActionBarIcon =
                        methodCall.argument("usePayPalActionBarIcon");
            }
            if (methodCall.hasArgument("keepApplicationTheme")) {
                extras._mKeepApplicationTheme =
                        methodCall.argument("keepApplicationTheme");
            }
            return extras;
        } catch (java.lang.NullPointerException e) {
            return null;
        }
    }

    public boolean noCamera() {
        return _mNoCamera;
    }

    public boolean requireExpiry() {
        return _mRequireExpiry;
    }

    public boolean scanExpiry() {
        return _mScanExpiry;
    }

    public int getUnblurDigits() {
        return _mUnblurDigits;
    }

    public boolean requireCVV() {
        return _mRequireCVV;
    }

    public boolean requirePostalCode() {
        return _mRequirePostalCode;
    }

    public boolean restrictPostalCodeToNumericOnly() {
        return _mRestrictPostalCodeToNumericOnly;
    }

    public boolean requireCardholderName() {
        return _mRequireCardholderName;
    }

    public boolean useCardIOLogo() {
        return _mUseCardIOLogo;
    }

    public boolean manualEntryResult() {
        return _mManualEntryResult;
    }

    public boolean suppressManualEntry() {
        return _mSuppressManualEntry;
    }

    public String getLanguageOrLocale() {
        return _mLanguageOrLocale;
    }

    public int getGuideColor() {
        return _mGuideColor;
    }

    public boolean suppressConfirmation() {
        return _mSuppressConfirmation;
    }

    public boolean hideCardIOLogo() {
        return _mHideCardIOLogo;
    }

    public String getScanInstructions() {
        return _mScanInstructions;
    }

    public boolean suppressScan() {
        return _mSuppressScan;
    }

    public String getCapturedCardImage() {
        return _mCapturedCardImage;
    }

    public boolean isReturnCardImage() {
        return _mReturnCardImage;
    }

    public boolean isUsePayPalActionBarIcon() {
        return _mUsePayPalActionBarIcon;
    }

    public boolean isKeepApplicationTheme() {
        return _mKeepApplicationTheme;
    }

}
