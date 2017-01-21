package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tinosteinort.beanrepository.BeanRepository;
import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoListTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoTO;
import de.tse.example.sparkhibernatebeanrepository.client.base.*;
import de.tse.example.sparkhibernatebeanrepository.client.ServiceClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Damit die Tests laufen, muss der Server gestartet werden
 */
public class TestClient {

    private BeanRepository repo;

    @Before public void setUp() {
        final Configuration config = new Configuration();
        config.setTrustStore("../certs/localhost-serverkeystore");
        config.setTrustStorePassword("changeit".toCharArray());

        repo = new BeanRepository.BeanRepositoryBuilder()
                .instance(config)
                .singletonFactory(ObjectMapper.class, ObjectMapperTestFactory::new)
                .singletonFactory(CloseableHttpClient.class, HttpClientTestFactory::new, Configuration.class)
                .singleton(ServiceClient.class, ServiceClient::new, HttpService.class, CredentialProvider.class)
                .singleton(CredentialProvider.class, CredentialProvider::new)
                .singleton(HttpService.class, HttpService::new, CloseableHttpClient.class, ObjectMapper.class)
                .build();

        repo.getBean(CredentialProvider.class).setName("tino");
    }

    @Test public void testGet() throws IOException {

        final ServiceClient service = repo.getBean(ServiceClient.class);

        final InputInfoListTO result = service.getInputInfos();
        System.out.println("InfoCount: " + result.getInputInfos().size());
        for (InputInfoTO info : result.getInputInfos()) {
            System.out.println("  " + info);
        }
    }

    @Test public void testCreate() throws IOException {

        final ServiceClient service = repo.getBean(ServiceClient.class);

        final CreateInputTO newInput = new CreateInputTO("TiTaTest");
        final InputInfoTO createdInput = service.create(newInput);
        System.out.println(createdInput);
    }
}
