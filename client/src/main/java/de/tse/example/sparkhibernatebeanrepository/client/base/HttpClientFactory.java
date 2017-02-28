package de.tse.example.sparkhibernatebeanrepository.client.base;

import com.github.tinosteinort.beanrepository.Factory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;

public class HttpClientFactory implements Factory<CloseableHttpClient> {

    private final Configuration configuration;

    public HttpClientFactory(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override public CloseableHttpClient createInstance() {

        final SSLContext sslcontext;
        try {
            sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(new File(configuration.getTrustStore()),
                            configuration.getTrustStorePassword(),
                            new TrustSelfSignedStrategy())
                    .build();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        final SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        return HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
    }
}
