package uk.co.richyhbm.coinbag.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.security.InvalidParameterException;

import uk.co.richyhbm.coinbag.interfaces.IAsyncResult;

//Class for generating a QR code from a string
public class QRGenerator extends AsyncTask<QRData, Void, Bitmap> {
    //Background color, slightly gray
    private static final int BACKGROUND_COLOR = 0xfffafafa;
    public IAsyncResult<Bitmap> asyncresult;

    @Override
    protected Bitmap doInBackground(QRData... params) {
        if(params.length != 1)
            throw new InvalidParameterException("QR Generator needs just 1 set of data");
        if(params[0].data.isEmpty())
            return Bitmap.createBitmap(params[0].width, params[0].height, Bitmap.Config.ARGB_8888);

        String data = params[0].data;
        int width = params[0].width;
        int height = params[0].height;

        //Encode a string using zxing
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height, null);
        } catch (WriterException we) {
            // Unsupported format
            we.printStackTrace();
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

    @Override
    protected void onPostExecute(Bitmap result) {
        if(asyncresult != null)
            asyncresult.processResult(result);
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
