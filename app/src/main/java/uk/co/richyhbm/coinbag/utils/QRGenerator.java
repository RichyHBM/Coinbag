package uk.co.richyhbm.coinbag.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;

//Class for generating a QR code from a string
//TODO: Use AsyncTask to not run in same thread as UI
public class QRGenerator {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 1024;
    //Background color, slightly gray
    private static final int BACKGROUND_COLOR = 0xfffafafa;

    //Create a QR from a string at the default size
    public static Bitmap qrFromString(String str) throws WriterException {
        return qrFromString(str, WIDTH, HEIGHT);
    }

    //Create a qr code from a string at a specified sized
    public static Bitmap qrFromString(String str, int width, int height) throws WriterException {
        //Encode a string using zxing
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, width, height, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            iae.printStackTrace();
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        //Write the encoded data into a java bitmap
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];

        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : BACKGROUND_COLOR;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }

    //Turns a byte array into a bitmap
    public static Bitmap fromBytes(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    //Turns a bitmap into a byte array
    public static byte[] toBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
