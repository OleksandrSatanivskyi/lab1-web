package lab;

import java.util.*;

public class Pizzeria {
    private String name;
    private String address;
    private List<Pizza> menu;
    private Map<String, Integer> ingredientStock;
    private List<Client> clients;

    public Pizzeria() {
    }

    public Pizzeria(String name, String address) {
        this.name = name;
        this.address = address;
        this.menu = new ArrayList<>();
        this.ingredientStock = new HashMap<>();
        this.clients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Pizza> getMenu() {
        return menu;
    }

    public void setMenu(List<Pizza> menu) {
        this.menu = menu;
    }

    public Map<String, Integer> getIngredientStock() {
        return ingredientStock;
    }

    public void setIngredientStock(Map<String, Integer> ingredientStock) {
        this.ingredientStock = ingredientStock;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void addIngredientStock(String ingredient, int quantity) {
        ingredientStock.put(ingredient, ingredientStock.getOrDefault(ingredient, 0) + quantity);
    }

    public Pizza findPizzaByName(String name) {
        for (Pizza pizza : menu) {
            if (pizza.getName().equalsIgnoreCase(name)) {
                return pizza;
            }
        }
        return null;
    }

    public void addPizza(Pizza pizza) {
        this.menu.add(pizza);
    }

    public void sortDataByNameAsc() {
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;

        menu.sort(Comparator.comparing(Pizza::getName, comparator));

        if (clients != null) {
            clients.sort(Comparator.comparing(Client::getName, comparator));
        }
    }

    public void sortDataByNameDesc() {
        Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER.reversed();

        menu.sort(Comparator.comparing(Pizza::getName, comparator));

        if (clients != null) {
            clients.sort(Comparator.comparing(Client::getName, comparator));
        }
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }

    @Override
    public int hashCode() {
        int result = (name == null) ? 0 : name.hashCode();
        result = 31 * result + (address == null ? 0 : address.hashCode());
        result = 31 * result + (menu == null ? 0 : menu.hashCode());
        result = 31 * result + (clients == null ? 0 : clients.hashCode());
        result = 31 * result + (ingredientStock == null ? 0 : ingredientStock.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Pizzeria other = (Pizzeria) obj;

        return (name != null ? name.equals(other.name) : other.name == null) &&
                (address != null ? address.equals(other.address) : other.address == null) &&
                (menu != null ? menu.equals(other.menu) : other.menu == null) &&
                (clients != null ? clients.equals(other.clients) : other.clients == null) &&
                (ingredientStock != null ? ingredientStock.equals(other.ingredientStock) : other.ingredientStock == null);
    }

}

