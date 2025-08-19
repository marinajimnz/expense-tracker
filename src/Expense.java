import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Individual expense of the expense tracker
 */

public class Expense {

    // ---- ATTRIBUTES ----
    /**
     * Attribute that represents the expense id.
     */
    private int id;

    /**
     * Attribute that represents the next expense id.
     */
    private static int nextId = 1;

    /**
     * Attribute that represents the amount of the expense.
     */
    private Double amount;

    /**
     * Attribute that represents the date of the expense.
     */
    private String date;

    /**
     * Attribute that represents the description of the expense
     */
    private String description;

    /**
     * Attribute that represents the category of the expense
     */
    private Category category;

    // ---- CONSTRUCTOR ----
    /**
     * If category is indicated
     */ 
    public Expense(String description, Double amount, String category) {
        if (description == null || amount == null || category == null) {
            throw new IllegalArgumentException("Description or amount argument is missing.");
        }

        this.id = nextId;
        this.amount = amount;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.description = description;

        setCategory(category);

        nextId++;
    }

    /**
     * If category is not indicated
     */ 
    public Expense(String description, Double amount) {
        if (description == null || amount == null) {
            throw new IllegalArgumentException("Description or amount argument is missing.");
        }

        this.id = nextId;
        this.amount = amount;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.description = description;
        setCategory("General");

        nextId++;
    }

    /**
     * When loaded from JSON
     */ 
    public Expense(int id, String description, Double amount, Category category, String date) {
        if (description == null || amount == null) {
            throw new IllegalArgumentException("Description or amount argument is missing.");
        }

        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    // ---- GETTERS + SETTERS ----

    /**
     * Getter of the attribute id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter of the attribute amount
     * @return amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Setter of the attribute amount
     */    
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Getter of the attribute date
     * @return date
     */  
    public String getDate() {
        return date;
    }

    /**
     * Setter of the attribute date
     */  
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter of the attribute description
     * @return description
     */  
    public String getDescription() {
        return description;
    }

    /**
     * Setter of the attribute description
     */  
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter of the attribute category
     * @return category
     */  
    public Category getCategory() {
        return category;
    }

    /**
     * Setter of the attribute category
     */  
    public void setCategory(String category) {
        category = category.trim().toUpperCase();

        switch (category) {
            case "GENERAL":
                this.category = Category.GENERAL;
                break;
            case "FOOD":
                this.category = Category.FOOD;
                break;
            case "ENTERTAINMENT":
                this.category = Category.ENTERTAINMENT;
                break;
            case "HEALTH":
                this.category = Category.HEALTH;
                break;
            case "SHOPPING":
                this.category = Category.SHOPPING;
                break;
            case "BILLS":
                this.category = Category.BILLS;
                break;
            default:
                this.category = Category.GENERAL;
                break;
        }
    }

    // ---- METHODS ----
    /**
     * Converts the expense to a JSON-like representation (as String).
     */
    public String toJson() {
        return String.format("{\n" +
                "\"id\": %d,\n" +
                "\"description\": \"%s\",\n" +
                "\"amount\": \"%.2f\",\n" +
                "\"category\": \"%s\",\n" +
                "\"date\": \"%s\"\n" +
                "}",
                id, description, amount, category, date);
    }

    /**
     * Extracts the expenses from a JSON-like representation.
     */
    public static Expense fromJson(String jsonFile) {
        // Error handling if it finds an empty JSON.
        if (jsonFile == null || jsonFile.trim().isEmpty()) {
            throw new IllegalArgumentException("JSON can't be null or empty.");
        }

        // Cleans the JSON without removing all quotes (only the braces).
        jsonFile = jsonFile.trim();
        if (jsonFile.startsWith("{")) {
            jsonFile = jsonFile.substring(1);
        }
        if (jsonFile.endsWith("}")) {
            jsonFile = jsonFile.substring(0, jsonFile.length() - 1);
        }

        // Separates elements by comma (commas inside values between quotes don't).
        String[] elements = jsonFile.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        String id = "";
        String description = "";
        String amount = "";
        Category category = Category.GENERAL;
        String date = "";

        for (String element : elements) {
            String[] keyValue = element.trim().split(":", 2); // Can only have 2 elements: key and value.

            if (keyValue.length != 2) {
                continue;
            }

            // Removes quotes from key and value
            String key = keyValue[0].trim().replace("\"", ""); // Key.
            String value = keyValue[1].trim().replace("\"", ""); // Value.

            // Saves values in their corresponding variables according to the key.
            switch (key) {
                case "id":
                    id = value;
                    break;
                case "description":
                    description = value;
                    break;
                case "amount":
                    amount = value;
                    break;
                case "category":
                    category = Category.valueOf(value.trim().toUpperCase());
                    break;
                case "date":
                    date = value;
                    break;
            }
        }

        // To check that ids are unique.
        int expenseId = Integer.parseInt(id);
        if (expenseId >= nextId) {
            nextId = expenseId + 1;
        }

        // To parse amount to Double
        double expenseAmount = 0;
        try {
            expenseAmount = parseAmount(amount);
        } catch (Exception ParseException) {
            System.out.println("Couldn't parse the amount.");
        }


        return new Expense(expenseId, description, expenseAmount, category, date);
    }

    /**
     * Parses the amount to Double taking into account that the local language is ES. 
     * That means that terminal has as decimal divider "." and it is saved as "," at the JSON file.
     * This method manages the difference.
     */  
    static double parseAmount(String text) throws ParseException {
        NumberFormat nf = NumberFormat.getInstance(Locale.of("es", "ES"));
        Number n = nf.parse(text.trim());
        return n.doubleValue();
    }

    // ---- COMPARE ----
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Expense expense = (Expense) obj;
        return id == expense.id &&
                amount.equals(expense.amount) &&
                date.equals(expense.date) &&
                description.equals(expense.description) &&
                category.equals(expense.category);
    }

    // ---- PRINT ----
    @Override
    public String toString() {
        return "ID: " + id +
                "\nDescription: " + description +
                "\nAmount: " + amount +
                "\nCategory: " + category +
                "\nDate: " + date;
    }
}
