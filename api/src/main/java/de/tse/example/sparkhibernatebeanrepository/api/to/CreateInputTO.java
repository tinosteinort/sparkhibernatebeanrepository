package de.tse.example.sparkhibernatebeanrepository.api.to;

public class CreateInputTO {

    private String input;

    public CreateInputTO() {

    }

    public CreateInputTO(final String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }
    public void setInput(String input) {
        this.input = input;
    }

    @Override public String toString() {
        return "CreateInputTO{" +
                "input='" + input + '\'' +
                '}';
    }
}
