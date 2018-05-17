package com.jjh.springboot.util.HttpClientUtil;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * 同步的HTTP请求对象，支持post与get方法以及可设置代理
 *
 * @author jianghong
 */
public class HttpSyncClient {

    private Logger logger = LoggerFactory.getLogger(HttpSyncClient.class);


    private PoolingHttpClientConnectionManager poolConnManager;
    /**
     * 连接池最大连接数 200
     */
    private final int maxTotalPool = 200;
    /**
     * 每个主机的并发最多只有20
     */
    private final int maxConPerRoute = 20;
    /**
     * 设置等待数据超时时间2000毫秒钟 根据业务调整
     */
    private final int socketTimeout = 2000;
    /**
     * 设置请求超时时间 3000
     */
    private final int connectionRequestTimeout = 3000;
    /**
     * 连接超时 1000
     */
    private final int connectTimeout = 1000;


    /**
     * http代理相关参数
     */
    public static String host = "192.168.3.245";
    public static int port = 11180;
    public static String username = "";
    public static String password = "";


    /**
     * 同步httpclient
     */
    private CloseableHttpClient httpClient;
    /**
     * 同步httpclient
     */
    private CloseableHttpClient proxyHttpClient;

    public HttpSyncClient() {
        try {
            this.httpClient = init(false);
            this.proxyHttpClient = init(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CloseableHttpClient init(boolean proxy) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null,
                new TrustSelfSignedStrategy())
                .build();
        HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslsf)
                .build();
        poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // Increase max total connection to 200
        poolConnManager.setMaxTotal(maxTotalPool);
        // Increase default max connection per route to 20
        poolConnManager.setDefaultMaxPerRoute(maxConPerRoute);
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).build();
        poolConnManager.setDefaultSocketConfig(socketConfig);

        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();
        CloseableHttpClient httpClient = proxy ? HttpClients.custom().setProxy(new HttpHost(host, port))
                .setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig).build() : HttpClients.custom()
                .setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig).build();
        if (poolConnManager != null && poolConnManager.getTotalStats() != null) {
            logger.info("now client pool " + poolConnManager.getTotalStats().toString());
        }
        return httpClient;
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public CloseableHttpClient getProxyHttpClient() {
        return proxyHttpClient;
    }
}