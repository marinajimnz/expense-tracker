import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String descritpion;

    /**
     * Attribute that represents the category of the expense
     */
    private Category category;

    // ---- CONSTRUCTOR ----
    // If category is indicated
    public Expense (String description, Double amount, String category) {
        if(description == null || amount == null || category == null) {
            throw new IllegalArgumentException("Description or amount argument is missing.");
        }

        this.id = nextId;
        this.amount = amount;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.descritpion = description;
        
        setCategory(category);

        nextId++;
    }

    // If category is not indicated
    public Expense (String description, Double amount) {
        if(description == null || amount == null) {
            throw new IllegalArgumentException("Description or amount argument is missing.");
        }

        this.id = nextId;
        this.amount = amount;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.descritpion = description;
        setCategory("General");

        nextId++;
    }

    // GETTERS + SETTERS
    public int getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public Category getCategory() {
        return category;
    }

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

    // ---- COMPARE ----
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Expense expense = (Expense) obj;
        return id == expense.id &&
               amount.equals(expense.amount) &&
               date.equals(expense.date) &&
               descritpion.equals(expense.descritpion) &&
               category.equals(expense.category);
    }

    // ---- PRINT ----
    @Override
    public String toString() { 
        return "ID: " + id + 
                "\nDescription: " + descritpion +
                "\nAmount: " + amount +
                "\nCategory: " + category +
                "\nDate: " + date; 
    }
}
