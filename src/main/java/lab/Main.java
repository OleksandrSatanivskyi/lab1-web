package lab;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String storageFile = "pizzeria.json";
            PizzeriaStorage storage = new PizzeriaStorage(storageFile);
            Pizzeria pizzeria = storage.loadPizzeria();

            if (pizzeria == null) {
                System.out.println("No existing data, creating new pizzeria.");
                pizzeria = new Pizzeria("My Pizza Place", "123 Main St");
            } else {
                System.out.println("Loaded pizzeria from " + storageFile);
            }

            boolean exit = false;
            while (!exit) {
                System.out.println("\n--- Pizzeria Menu ---");
                System.out.println("1. View pizzas");
                System.out.println("2. Add pizza");
                System.out.println("3. View clients");
                System.out.println("4. Add client");
                System.out.println("5. View ingredient stock");
                System.out.println("6. Add ingredient stock");
                System.out.println("7. Save and exit");
                System.out.println("8. Exit without save");
                System.out.print("Select an option: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> {
                        System.out.println("Pizza Menu:");
                        pizzeria.getMenu().forEach(p ->
                                System.out.printf("- %s: $%.2f\n", p.getName(), p.getPrice())
                        );
                    }
                    case 2 -> {
                        System.out.print("Enter pizza name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter price: ");
                        double price = Double.parseDouble(scanner.nextLine());
                        Map<String, Double> ingredients = new HashMap<>();
                        System.out.println("Enter ingredients (name quantity), blank to finish:");
                        while (true) {
                            System.out.print("> ");
                            String line = scanner.nextLine().trim();
                            if (line.isEmpty()) break;
                            String[] parts = line.split(" ");
                            ingredients.put(parts[0], Double.parseDouble(parts[1]));
                        }
                        pizzeria.addPizza(new Pizza(name, price, ingredients));
                        System.out.println("Pizza added.");
                    }
                    case 3 -> {
                        System.out.println("Clients:");
                        pizzeria.getClients().forEach(c ->
                                System.out.printf("- %s (%s)\n", c.getName(), c.getEmail())
                        );
                    }
                    case 4 -> {
                        System.out.print("Enter client id: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter name: ");
                        String clientName = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        pizzeria.addClient(new Client(id, clientName, email));
                        System.out.println("Client added.");
                    }
                    case 5 -> {
                        System.out.println("Ingredient stock:");
                        pizzeria.getIngredientStock().forEach((ingredient, qty) ->
                                System.out.printf("- %s: %d\n", ingredient, qty)
                        );
                    }
                    case 6 -> {
                        System.out.print("Enter ingredient name: ");
                        String ingredient = scanner.nextLine();
                        System.out.print("Enter quantity to add: ");
                        int quantity = Integer.parseInt(scanner.nextLine());
                        pizzeria.addIngredientStock(ingredient, quantity);
                        System.out.println("Stock updated.");
                    }
                    case 7 -> {
                        System.out.print("Sort ascending before save? (y/n): ");
                        boolean asc = scanner.nextLine().equalsIgnoreCase("y");
                        storage.savePizzeria(pizzeria, asc);
                        System.out.println("Data saved to " + storageFile);
                        exit = true;
                    }
                    case 8 -> exit = true;
                    default -> System.out.println("Invalid option.");
                }
            }
            System.out.println("Goodbye!");
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error with file operations: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format entered.");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

}
