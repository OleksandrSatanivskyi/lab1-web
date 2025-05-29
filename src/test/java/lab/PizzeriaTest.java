package lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PizzeriaTest {

    private Pizzeria pizzeria;

    @BeforeEach
    void setup() {
        pizzeria = new Pizzeria("Test Pizza Place", "Test Address");
    }

    @Test
    void addPizza_ValidData_ShouldAddPizza() {
        Map<String, Double> ingredients = new HashMap<>();
        ingredients.put("Cheese", 1.0);
        ingredients.put("Tomato", 0.5);

        Pizza pizza = new Pizza("Margarita", 10.0, ingredients);
        pizzeria.addPizza(pizza);

        assertEquals(1, pizzeria.getMenu().size());
        assertEquals("Margarita", pizzeria.getMenu().get(0).getName());
    }

    @Test
    void setIngredients_InvalidQuantity_ShouldThrow() {
        Pizza pizza = new Pizza();
        Map<String, Double> ingredients = new HashMap<>();
        ingredients.put("Cheese", 0.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            pizza.setIngredients(ingredients);
        });
        assertTrue(ex.getMessage().contains("quantity must be positive"));
    }

    @Test
    void addOrUpdateIngredient_NegativeQuantity_ShouldThrow() {
        Map<String, Double> ingredients = new HashMap<>();
        ingredients.put("Cheese", 1.0);
        Pizza pizza = new Pizza("Test", 10.0, ingredients);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            pizza.addOrUpdateIngredient("Tomato", -1.0);
        });
        assertEquals("The ingredient quantity must be positive.", ex.getMessage());
    }

    @Test
    void updateIngredientQuantity_NotFound_ShouldThrow() {
        Map<String, Double> ingredients = new HashMap<>();
        ingredients.put("Cheese", 1.0);
        Pizza pizza = new Pizza("Test", 10.0, ingredients);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            pizza.updateIngredientQuantity("Tomato", 2.0);
        });
        assertTrue(ex.getMessage().contains("Ingredient not found"));
    }

    @Test
    void addClient_ShouldAddClientToList() {
        Client client = new Client("1", "John Doe", "john@example.com");
        pizzeria.addClient(client);
        assertEquals(1, pizzeria.getClients().size());
        assertEquals("John Doe", pizzeria.getClients().get(0).getName());
    }

    @Test
    void addIngredientStock_ShouldIncreaseStock() {
        pizzeria.addIngredientStock("Cheese", 5);
        pizzeria.addIngredientStock("Cheese", 3);
        assertEquals(8, pizzeria.getIngredientStock().get("Cheese"));
    }

    @Test
    void findPizzaByName_ExistingPizza_ShouldReturnPizza() {
        Pizza pizza = new Pizza("Pepperoni", 12.0, Map.of("Cheese", 1.0));
        pizzeria.addPizza(pizza);
        Pizza found = pizzeria.findPizzaByName("Pepperoni");
        assertNotNull(found);
        assertEquals("Pepperoni", found.getName());
    }

    @Test
    void findPizzaByName_NonExistingPizza_ShouldReturnNull() {
        Pizza found = pizzeria.findPizzaByName("NonExist");
        assertNull(found);
    }

    @Test
    void sortDataByNameAsc_ShouldSortMenuAndClients() {
        Pizza p1 = new Pizza("Pizza1", 10, Map.of("Cheese", 1.0));
        Pizza p2 = new Pizza("Pizza2", 8, Map.of("Tomato", 0.5));
        pizzeria.addPizza(p1);
        pizzeria.addPizza(p2);

        Client c1 = new Client("1", "Zoe", "zoe@mail.com");
        Client c2 = new Client("2", "Adam", "adam@mail.com");
        pizzeria.addClient(c1);
        pizzeria.addClient(c2);

        pizzeria.sortDataByNameAsc();

        assertEquals("Pizza1", pizzeria.getMenu().get(0).getName());
        assertEquals("Pizza2", pizzeria.getMenu().get(1).getName());

        assertEquals("Adam", pizzeria.getClients().get(0).getName());
        assertEquals("Zoe", pizzeria.getClients().get(1).getName());
    }

    @Test
    void testInitializationCreatesFileWithDefaultData() throws IOException {
        String tempPath = "temp_test_pizzeria.json";
        File file = new File(tempPath);
        if (file.exists()) file.delete();

        PizzeriaStorage storage = new PizzeriaStorage(tempPath);
        assertTrue(file.exists());

        Pizzeria pizzeria = storage.loadPizzeria();
        assertEquals("Pizzeria", pizzeria.getName());
        assertEquals("123 Main St", pizzeria.getAddress());

        file.delete();
    }

    @Test
    void testSaveAndLoadPizzeriaData() throws IOException {
        String tempPath = "temp_test_pizzeria.json";
        File file = new File(tempPath);
        if (file.exists()) file.delete();

        PizzeriaStorage storage = new PizzeriaStorage(tempPath);

        Pizzeria pizzeria = new Pizzeria("Test Pizza Place", "456 Test Ave");
        pizzeria.addPizza(new Pizza("Margherita", 10.0, Map.of("Cheese", 1.0)));
        storage.savePizzeria(pizzeria, true);

        Pizzeria loaded = storage.loadPizzeria();
        assertEquals("Test Pizza Place", loaded.getName());
        assertEquals(1, loaded.getMenu().size());
        assertEquals("Margherita", loaded.getMenu().get(0).getName());

        file.delete();
    }

    @Test
    void testSavePizzeriaSortAscAndDesc() throws IOException {
        String tempPath = "temp_test_pizzeria.json";
        File file = new File(tempPath);
        if (file.exists()) file.delete();

        PizzeriaStorage storage = new PizzeriaStorage(tempPath);

        Pizzeria pizzeria = new Pizzeria("Test Place", "789 Test Blvd");
        pizzeria.addPizza(new Pizza("Pepperoni", 12.0, Map.of("Pepperoni", 1.0)));
        pizzeria.addPizza(new Pizza("Hawaiian", 11.0, Map.of("Pineapple", 1.0)));

        storage.savePizzeria(pizzeria, true);

        Pizzeria loadedAsc = storage.loadPizzeria();
        assertEquals("Hawaiian", loadedAsc.getMenu().get(0).getName());

        storage.savePizzeria(pizzeria, false);

        Pizzeria loadedDesc = storage.loadPizzeria();
        assertEquals("Pepperoni", loadedDesc.getMenu().get(0).getName());

        file.delete();
    }


}