package de.tse.example.sparkhibernatebeanrepository.api.to;

import java.util.ArrayList;
import java.util.List;

public class InputInfoListTO {

    private final List<InputInfoTO> inputInfos = new ArrayList<>();

    public List<InputInfoTO> getInputInfos() {
        return inputInfos;
    }

    @Override public String toString() {
        return "InputInfoListTO{" +
                "inputInfos=" + inputInfos +
                '}';
    }
}
