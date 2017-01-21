package de.tse.example.sparkhibernatebeanrepository.client.base;

public class Configuration {

    private String trustStore;
    private char[] trustStorePassword;

    public String getTrustStore() {
        return trustStore;
    }
    public void setTrustStore(String trustStore) {
        this.trustStore = trustStore;
    }

    public char[] getTrustStorePassword() {
        return trustStorePassword;
    }
    public void setTrustStorePassword(char[] trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }
}
