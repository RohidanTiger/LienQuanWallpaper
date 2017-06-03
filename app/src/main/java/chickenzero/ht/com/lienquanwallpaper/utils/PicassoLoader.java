package chickenzero.ht.com.lienquanwallpaper.utils;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by billymobile on 12/13/16.
 */

public class PicassoLoader {

    private static Picasso sPicasso;

    private static Picasso newInstance(Context context) {

        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
        } };

        Picasso.Builder builder = new Picasso.Builder(context);

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            okHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
            OkHttpDownloader okHttpDownloader = new OkHttpDownloader(okHttpClient);
            builder.downloader(okHttpDownloader);
            sPicasso = builder.build();
        } catch (Exception e) {
        }
        return sPicasso;
    }

    public static Picasso getInstance(Context context) {
        if (sPicasso == null) return newInstance(context);
        return sPicasso;
    }
}
