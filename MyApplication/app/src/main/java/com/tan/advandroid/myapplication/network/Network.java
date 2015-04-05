package com.tan.advandroid.myapplication.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Rustam on 25.09.2014.
 */
public class Network {
    private String url = "http://mobevo.ext.terrhq.ru/";

    public String urlConnection( String strUrl) throws IOException {
        HttpURLConnection connection = null;
        String str = "";
        try {
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream in = connection.getInputStream();
                str = handleInputStream(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return str;
    }

    private String handleInputStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String result = "", line = "";
        while ((line = reader.readLine()) != null) {
            result += line;
        }
        Log.e("", result);
        return result;
    }

    public Bitmap downloadImage(String iUrl) {
        iUrl =url + iUrl;
        Bitmap bitmap = null;
            HttpURLConnection conn = null;
            BufferedInputStream buf_stream = null;
            try {
                Log.v("download image", "Starting loading image by URL: " + iUrl);
                conn = (HttpURLConnection) new URL(iUrl).openConnection();
                conn.setDoInput(true);
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.connect();
                buf_stream = new BufferedInputStream(conn.getInputStream(), 8192);
                bitmap = BitmapFactory.decodeStream(buf_stream);
                buf_stream.close();
                conn.disconnect();
                buf_stream = null;
                conn = null;
            } catch (MalformedURLException ex) {
                Log.e("download image", "Url parsing was failed: " + iUrl);
            } catch (IOException ex) {
                Log.d("download image", iUrl + " does not exists");
            } catch (OutOfMemoryError e) {
                Log.w("download image", "Out of memory!!!");
                return null;
            } finally {
                if ( buf_stream != null )
                    try { buf_stream.close(); } catch (IOException ex) {}
                if ( conn != null )
                    conn.disconnect();
            }
            return bitmap;
        }

    /*private void saveBitmapAtDisk(Bitmap bmp, String name) throws IOException{
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                Constants.FILE_DIRECTORY;
        File dir = new File(file_path);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dir, name + ".png");
        FileOutputStream fOut = new FileOutputStream(file);

        bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
        fOut.flush();
        fOut.close();
    }*/
}

