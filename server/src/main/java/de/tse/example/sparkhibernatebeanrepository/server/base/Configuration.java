package de.tse.example.sparkhibernatebeanrepository.server.base;

public class Configuration {

    private String keystoreFile;
    private String keystorePassword;
    private int port;
    private String jwtPassword;

    public String getKeystoreFile() {
        return keystoreFile;
    }
    public void setKeystoreFile(String keystoreFile) {
        this.keystoreFile = keystoreFile;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }
    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public String getJwtPassword() {
        return jwtPassword;
    }
    public void setJwtPassword(String jwtPassword) {
        this.jwtPassword = jwtPassword;
    }
}
