package de.tse.example.sparkhibernatebeanrepository.client;

import de.tse.example.sparkhibernatebeanrepository.api.to.CreateInputTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoListTO;
import de.tse.example.sparkhibernatebeanrepository.api.to.InputInfoTO;
import de.tse.example.sparkhibernatebeanrepository.client.base.CredentialProvider;
import de.tse.example.sparkhibernatebeanrepository.client.base.HttpService;

import java.io.IOException;

public class ServiceClient {

    private final HttpService httpService;
    private final CredentialProvider credentialProvider;

    public ServiceClient(final HttpService httpService, final CredentialProvider credentialProvider) {
        this.httpService = httpService;
        this.credentialProvider = credentialProvider;
    }

    public InputInfoListTO getInputInfos() {
        try {
            return httpService.get("https://localhost:8123/data?name=" + credentialProvider.getName(), InputInfoListTO.class);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public InputInfoTO create(final CreateInputTO input) {
        try {
            return httpService.post("https://localhost:8123/data?name=" + credentialProvider.getName(), input, InputInfoTO.class);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delete(final InputInfoTO input) {
        try {
            httpService.delete("https://localhost:8123/data/" + input.getId() + "?name=" + credentialProvider.getName(), input);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
