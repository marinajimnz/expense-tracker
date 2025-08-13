import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Responsible for managing all expenses:
 * Add, delete, modify and export to CSV.
 */

public class ExpensesManager {
    // ---- ATTRIBUTES ----
    /**
     * Attribute that stores the expenses that the user adds.
     */
    private ArrayList<Expense> expenses;

    /**
     * Attribute for the .json file path.
     */
    private final Path FILE_PATH = Path.of("expenses.json");

    // ---- CONSTRUCTOR ----
    /**
     * Initializes the expense manager and loads the JSON file.
     * 
     * @param filePath Path to the JSON file.
     */
    public ExpensesManager() {
        this.expenses = loadExpenses();
    }

    /**
     * Add a new expense with the given description, amount and category
     * 
     * @param description
     * @param amount
     * @param category Can be null
     * 
     */
    public void addExpense(String description, Double amount, String category) {
        Expense newExpense;
        if(category != null) {
            newExpense = new Expense(description, amount, category);
        } else {
            newExpense = new Expense(description, amount, category);
        }

        expenses.add(newExpense);
        System.out.println(description + " added with the amount: " + amount);
    }

    /**
     * Delete an expense by ID
     * 
     */
    public void deleteExpense(int id) {
        Expense expenseToDelete = getExpenseById(id);

        if(expenseToDelete != null) {
            expenses.remove(expenseToDelete);
            System.out.println("Expense with id " + id + " deleted successfully");
        } else {
            System.out.println("ID: " + id + " doesn't exist.");
        }
    }

    public void updateExpense(int id, String description, Double amount, String category) {
        // Validation: the parameters couldn't be all null
        if(Stream.of(description,amount,category).allMatch(null)) { // Stream used to avoid many "OR" statements
            throw new IllegalArgumentException("At least one parameter should be submitted.");
        }

        Expense expense = Optional.ofNullable(getExpenseById(id)) // Optional works as a container that couldn't be null
            .orElseThrow(() -> new RuntimeException("The expense with id: " + id + " could'n be found."));

        Optional.ofNullable(description).ifPresent(expense::setDescription);
        Optional.ofNullable(amount).ifPresent(expense::setAmount);
        Optional.ofNullable(category).ifPresent(expense::setCategory);
        
        System.out.println("Expense with id " + id + " updated successfully");
        saveExpenses();
    }

    public void listAll() {
        for(Expense expense : expenses) {
            System.out.println(expense.toString());;
        }
    }

    public void listCategory(Category category) {
        for(Expense expense : expenses) {
            if(expense.getCategory().equals(category)) {
                System.out.println(expense.toString());
            }
        }
    }

    public Expense getExpenseById(int id) {
        for(Expense expense : expenses) {
            if(expense.getId() == id) {
                return expense;
            }
        }

        return null;
    }

    public ArrayList<Expense> loadExpenses() {
        ArrayList<Expense> expensesList = new ArrayList<>();

        if (!Files.exists(FILE_PATH)) { // If the file doesn't exist
            return expensesList;
        }

        // If the file exists.
        try {
            String fileContent = Files.readString(FILE_PATH);
            if (fileContent.startsWith("[") && fileContent.endsWith("]")) {
                fileContent = fileContent.substring(1, fileContent.length() - 1).trim();

                // Key verification to avoid error with empty file (only "[]")
                if (fileContent.isEmpty()) {
                    return expensesList;
                }

                // Separates objects by "},"
                String[] expensesArray = fileContent.split("(?<=\\}),\\s*");

                for (String expenseJson : expensesArray) {
                    expenseJson = expenseJson.trim();

                    // Ensures each object ends with }
                    if (!expenseJson.endsWith("}")) {
                        expenseJson += "}";
                    }

                    expensesList.add(Expense.fromJson(expenseJson));
                }
            }
        } catch (IOException e) {
            System.out.println("The file couldn't be read.");
        }

        return expensesList;
    }

    /**
     * Saves all current expenses to the JSON file.
     */
    public void saveExpenses() {
        ArrayList<String> jsonExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            jsonExpenses.add(expense.toJson());
        }

        String jsonContent = "[\n" + String.join(",\n", jsonExpenses) + "\n]"; // Adds brackets at the beginning and end of
                                                                            // the JSON

        try { // Attempts to write to the file
            Files.writeString(FILE_PATH, jsonContent);
        } catch (IOException e) { // If it doesn't succeed
            System.out.println("Expenses couldn't be saved to the JSON file.");
        }
    }
}
