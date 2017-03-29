package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tinosteinort.beanrepository.BeanRepository;
import de.tse.example.sparkhibernatebeanrepository.api.command.CreateDataCommand;
import de.tse.example.sparkhibernatebeanrepository.api.command.DeleteDataCommand;
import de.tse.example.sparkhibernatebeanrepository.api.command.GetDataCommand;
import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.FilterTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoListTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoTO;
import de.tse.example.sparkhibernatebeanrepository.client.services.CommandService;
import de.tse.example.sparkhibernatebeanrepository.client.services.LoginService;
import de.tse.example.sparkhibernatebeanrepository.client.base.Configuration;
import de.tse.example.sparkhibernatebeanrepository.client.base.HttpClientFactory;
import de.tse.example.sparkhibernatebeanrepository.client.base.HttpService;
import de.tse.example.sparkhibernatebeanrepository.client.base.ObjectMapperFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
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
        config.setBaseUrl("https://localhost:8123");

        repo = new BeanRepository.BeanRepositoryBuilder()
                .instance(config)
                .singletonFactory(ObjectMapper.class, ObjectMapperFactory::new)
                .singletonFactory(CloseableHttpClient.class, HttpClientFactory::new, Configuration.class)
                .singleton(LoginService.class, LoginService::new)
                .singleton(CommandService.class, CommandService::new)
                .singleton(HttpService.class, HttpService::new, CloseableHttpClient.class, ObjectMapper.class)
                .build();

        repo.getBean(LoginService.class).login("tino", "tinopw");
    }

    @Test public void testGet() throws IOException {

        final CommandService service = repo.getBean(CommandService.class);

        final InputInfoListTO result = service.execute(new GetDataCommand(new FilterTO()));
        System.out.println("InfoCount: " + result.getInputInfos().size());
        for (InputInfoTO info : result.getInputInfos()) {
            System.out.println("  " + info);
        }
    }

    @Test public void testCreate() throws IOException {

        final CommandService service = repo.getBean(CommandService.class);

        final CreateInputTO newInput = new CreateInputTO("TiTaTest");
        final InputInfoTO createdInput = service.execute(new CreateDataCommand(newInput));
        System.out.println(createdInput);
    }

    @Test public void testDelete() throws Exception {

        final CommandService service = repo.getBean(CommandService.class);

        final int initialDataCount = service.execute(new GetDataCommand(new FilterTO())).getInputInfos().size();

        final CreateInputTO newData = new CreateInputTO("DeletionTest");
        final InputInfoTO createdData = service.execute(new CreateDataCommand(newData));

        final int dataCountAfterCreate = service.execute(new GetDataCommand(new FilterTO())).getInputInfos().size();
        Assert.assertEquals(initialDataCount + 1, dataCountAfterCreate);

        service.execute(new DeleteDataCommand(createdData.getId()));

        final int dataCountAfterDelete = service.execute(new GetDataCommand(new FilterTO())).getInputInfos().size();
        Assert.assertEquals(initialDataCount, dataCountAfterDelete);
    }

    @Test public void testFind() throws Exception {

        final CommandService service = repo.getBean(CommandService.class);

        final InputInfoTO item1 = service.execute(new CreateDataCommand(new CreateInputTO("Item1")));
        final InputInfoTO item11 = service.execute(new CreateDataCommand(new CreateInputTO("item11")));
        final InputInfoTO item2 = service.execute(new CreateDataCommand(new CreateInputTO("Item2")));

        final FilterTO filter = new FilterTO();
        filter.setSearchValue("Item1");
        final InputInfoListTO searchResult = service.execute(new GetDataCommand(filter));
        Assert.assertEquals(2, searchResult.getInputInfos().size());
        Assert.assertEquals("item11", searchResult.getInputInfos().get(0).getData());
        Assert.assertEquals("Item1", searchResult.getInputInfos().get(1).getData());

        service.execute(new DeleteDataCommand(item1.getId()));
        service.execute(new DeleteDataCommand(item11.getId()));
        service.execute(new DeleteDataCommand(item2.getId()));
    }
}
