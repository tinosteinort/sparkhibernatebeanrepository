package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tinosteinort.beanrepository.BeanAccessor;
import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoListTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoTO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ServiceClient {

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ServiceClient(final BeanAccessor beans) {
        this.httpClient = beans.getBean(CloseableHttpClient.class);
        this.objectMapper = beans.getBean(ObjectMapper.class);
    }

    private <T> T get(final String url, final Class<T> returnClass) throws IOException {
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

    public InputInfoListTO getInputInfos() throws IOException {
        return get("https://localhost:8123/data?name=tino", InputInfoListTO.class);
    }

    public InputInfoTO create(final CreateInputTO input) throws IOException {
        return post("https://localhost:8123/data?name=tino", input, InputInfoTO.class);
    }
}
