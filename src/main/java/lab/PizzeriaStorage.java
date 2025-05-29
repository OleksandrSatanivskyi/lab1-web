package lab;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

public class PizzeriaStorage {

    private final ObjectMapper mapper;
    private final String filePath;

    public PizzeriaStorage(String filePath) throws IOException {
        this.filePath = filePath;
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);

        File file = new File(filePath);
        if (!file.exists()) {
            Pizzeria emptyPizzeria = new Pizzeria("Pizzeria", "123 Main St");
            mapper.writeValue(file, emptyPizzeria);
        }
    }

    public void savePizzeria(Pizzeria pizzeria, boolean sortAsc) throws IOException {
        if (sortAsc) {
            pizzeria.sortDataByNameAsc();
        } else {
            pizzeria.sortDataByNameDesc();
        }
        mapper.writeValue(new File(filePath), pizzeria);
    }

    public Pizzeria loadPizzeria() throws IOException {
        return mapper.readValue(new File(filePath), Pizzeria.class);
    }
}

