package com.viethoa.lazadaretailer.utilities;

/**
 * Created by trimao on 8/31/16.
 */
public class HexadecimalUtils {
    private static final String TAG = HexadecimalUtils.class.getSimpleName();
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
    private static final char HEX_SEPARATE = ' ';

    public static String convertHexByteToHexString(byte[] buf) {
        char[] decimalChars = new char[3 * buf.length];
        for (int i = 0; i < buf.length; ++i) {
            decimalChars[3 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            decimalChars[3 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
            decimalChars[3 * i + 2] = HEX_SEPARATE;
        }
        return new String(decimalChars);
    }
}