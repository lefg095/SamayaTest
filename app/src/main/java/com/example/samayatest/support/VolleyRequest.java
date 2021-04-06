package com.example.samayatest.support;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.example.samayatest.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class VolleyRequest {

    private static VolleyRequest mInstance;

    public static synchronized VolleyRequest getInstance() {
        if (mInstance == null) {
            mInstance = new VolleyRequest();
        }
        return mInstance;
    }

    private HostnameVerifier hostnameVerifier = (hostname, session) -> {
        HostnameVerifier hv =
                HttpsURLConnection.getDefaultHostnameVerifier();
        return hv.verify(hostname, session);
    };

    public RequestFuture<JSONArray> makeRequest(Context context, String url, int tipo, JSONArray json) {
        HurlStack hurlStack = new HurlStack() {
            @Override
            protected HttpURLConnection createConnection(java.net.URL url)
                    throws IOException {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super
                        .createConnection(url);
                try {
                    httpsURLConnection
                            .setSSLSocketFactory(getSSLSocketFactory(context));
                    httpsURLConnection.setHostnameVerifier(hostnameVerifier);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return httpsURLConnection;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context, hurlStack);
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(tipo,
                url, json, future, future);
        request.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
        requestQueue.addRequestFinishedListener(request1 -> requestQueue.getCache().clear());
        return future;
    }

    private SSLSocketFactory getSSLSocketFactory(Context context)
            throws CertificateException, KeyStoreException, IOException,
            NoSuchAlgorithmException, KeyManagementException {


        KeyStore keyStore = KeyStore.getInstance("BKS");

        keyStore.load(null, null);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf
                .getTrustManagers());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);

        return sslContext.getSocketFactory();
    }

    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return originalTrustManager.getAcceptedIssuers();
            }

            public void checkClientTrusted(X509Certificate[] certs,
                                           String authType) {
                try {
                    if (certs != null && certs.length > 0) {
                        certs[0].checkValidity();
                    } else {
                        originalTrustManager
                                .checkClientTrusted(certs, authType);
                    }
                } catch (CertificateException e) {
                    Log.w("checkClientTrusted", e.toString());
                }
            }

            public void checkServerTrusted(X509Certificate[] certs,
                                           String authType) {
                try {
                    if (certs != null && certs.length > 0) {
                        certs[0].checkValidity();
                    } else {
                        originalTrustManager
                                .checkServerTrusted(certs, authType);
                    }
                } catch (CertificateException e) {
                    Log.w("checkServerTrusted", e.toString());
                }
            }
        }};
    }
}
