package lab;

import java.util.Map;
import java.util.Objects;

public class Pizza {
    private String name;
    private double price;
    private Map<String, Double> ingredients;

    public Pizza() {

    }

    public Pizza(String name, double price, Map<String, Double> ingredients) {
        this.name = name;
        this.price = calculatePriceWithTax(price, 0.05);
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Map<String, Double> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, Double> ingredients) {
        if (ingredients != null) {
            for (Map.Entry<String, Double> entry : ingredients.entrySet()) {
                if (entry.getValue() == null || entry.getValue() <= 0) {
                    throw new IllegalArgumentException("The ingredient quantity must be positive: " + entry.getKey());
                }
            }
        }
        this.ingredients = ingredients;
    }

    public void addOrUpdateIngredient(String name, Double quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("The ingredient quantity must be positive.");
        }
        ingredients.put(name, quantity);
    }

    public void removeIngredient(String name) {
        ingredients.remove(name);
    }

    public void updateIngredientQuantity(String name, Double newQuantity) {
        if (!ingredients.containsKey(name)) {
            throw new IllegalArgumentException("Ingredient not found: " + name);
        }
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("The ingredient quantity must be positive.");
        }
        ingredients.put(name, newQuantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return Double.compare(pizza.price, price) == 0 &&
                Objects.equals(name, pizza.name);
    }

    @Override
    public int hashCode() {
        int result = (name == null) ? 0 : name.hashCode();
        result = 31 * result + Double.hashCode(price);
        result = 31 * result + (ingredients == null ? 0 : ingredients.hashCode());
        return result;
    }

    public double calculatePriceWithTax(double price, double taxRate) {
        return price * (1 + taxRate);
    }
}
