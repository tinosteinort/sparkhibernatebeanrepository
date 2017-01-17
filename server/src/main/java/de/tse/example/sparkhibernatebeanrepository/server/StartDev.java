package de.tse.example.sparkhibernatebeanrepository.server;

public class StartDev {

    public static void main(String[] args) throws Exception {

        System.setProperty("keystore", "certs/localhost-serverkeystore");
        System.setProperty("keystorePassword", "changeit");
        System.setProperty("port", "8123");
        Start.main(args);
    }
}
