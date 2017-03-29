package de.tse.example.sparkhibernatebeanrepository.client;

public class ClientApplicationDev {

    public static void main(String[] args) {

        System.setProperty("truststore", "certs/localhost-serverkeystore");
        System.setProperty("truststorePassword", "changeit");
        System.setProperty("baseUrl", "https://localhost:8123");
        ClientApplication.main(args);
    }
}
