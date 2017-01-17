package de.tse.example.sparkhibernatebeanrepository.client.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpService {

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public HttpService(final CloseableHttpClient httpClient, final ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public <T> T get(final String url, final Class<T> returnClass) throws IOException {
        final HttpGet get = new HttpGet(url);

        try (final CloseableHttpResponse response = httpClient.execute(get)) {

            final HttpEntity entity = response.getEntity();

            final T data = objectMapper.readValue(EntityUtils.toString(entity), returnClass);
            EntityUtils.consume(entity);
            return data;
        }
    }

    public <R, P> R post(final String url, final P data, final Class<R> returnClass) throws IOException {
        final HttpPost post = new HttpPost(url);

        final StringEntity postData = (data == null ? null : new StringEntity(objectMapper.writeValueAsString(data)));
        post.setEntity(postData);

        try (final CloseableHttpResponse response = httpClient.execute(post)) {

            final HttpEntity entity = response.getEntity();

            final R inputInfo = objectMapper.readValue(EntityUtils.toString(entity), returnClass);
            EntityUtils.consume(entity);
            return inputInfo;
        }
    }

    public <P> void delete(final String url, final P data) throws IOException {
        final HttpDelete delete = new HttpDelete(url);

        try (final CloseableHttpResponse response = httpClient.execute(delete)) {

            final HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
        }
    }
}
