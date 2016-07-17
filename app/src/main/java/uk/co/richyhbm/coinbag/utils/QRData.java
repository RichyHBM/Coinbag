package uk.co.richyhbm.coinbag.utils;

//Holds basic data for generating a qr code
public class QRData {
    public int width = 1024;
    public int height = 1024;
    public String data = "";

    public QRData(){

    }

    public QRData(String str) {
        data = str;
    }

    public QRData(String str, int w, int h) {
        data = str;
        width = w;
        height = h;
    }
}
