public class App {
    public static void main(String[] args) throws Exception {

        ExpensesManager em = new ExpensesManager();

        if (args.length < 1) {
            throw new IllegalArgumentException("Use: java -cp bin App\n" +
                    "  add <description> <amount> [category]\n" +
                    "  update <id> <descriptionesc|null> <amount|null> <category|null>\n" +
                    "  delete <id>\n" +
                    "  list-<general|food|entertainment|health|shopping|bills>\n" +
                    "  list-all");
        }

        String command = args[0].toLowerCase();

        switch (command) {
            case "add":
                if (args.length < 3) {
                    throw new IllegalArgumentException("Use: add <description> <amount> [category]");
                }
                String desc = args[1];
                Double amount = Double.parseDouble(args[2]);
                String cat = (args.length >= 4) ? args[3] : null; // opcional
                em.addExpense(desc, amount, cat);
                em.saveExpenses();
                break;
            case "update":
                try {
                    if (args.length < 4) {
                        System.out.println("Use: update <id> <field> <value> [<field> <value> ...]");
                        System.out.println("Valid fields: description | amount | category");
                        break;
                    }

                    // 1) Parse expense ID
                    Integer id = Integer.parseInt(args[1]);

                    // 2) Initialize as null so updateExpense only updates what is not null
                    String newDesc = null;
                    Double newAmount = null;
                    String newCat = null;

                    // 3) Check if arguments after ID are in pairs (field + value)
                    if ((args.length - 2) % 2 != 0) {
                        System.out.println("Error: You must pass pairs <field> <value>.");
                        break;
                    }

                    // 4) Iterate through field-value pairs
                    for (int i = 2; i < args.length; i += 2) {
                        String field = args[i].toLowerCase();
                        String value = args[i + 1];

                        switch (field) {
                            case "description":
                                newDesc = "null".equalsIgnoreCase(value) ? null : value;
                                break;

                            case "amount":
                                if ("null".equalsIgnoreCase(value)) {
                                    newAmount = null;
                                } else {
                                    try {
                                        newAmount = Double.valueOf(value);
                                    } catch (NumberFormatException nfe) {
                                        System.out.println("Error: 'amount' must be numeric. Received: " + value);
                                        return; // exit without updating
                                    }
                                }
                                break;

                            case "category":
                                newCat = "null".equalsIgnoreCase(value) ? null : value;
                                break;

                            default:
                                System.out
                                        .println("Invalid field: " + field + ". Use: description | amount | category");
                                return; // exit without updating
                        }
                    }

                    // 5) Prevent empty update (all fields are null)
                    if (newDesc == null && newAmount == null && newCat == null) {
                        System.out.println("Nothing to update: all values are null.");
                        break;
                    }

                    // 6) Perform update
                    em.updateExpense(id, newDesc, newAmount, newCat);

                } catch (NumberFormatException nfe) {
                    System.out.println("Error: ID must be numeric. Received: " + args[1]);
                } catch (Exception e) {
                    // In case exception message is null, show class name to avoid "null"
                    System.out.println("Error updating the expense: "
                            + (e.getMessage() != null ? e.getMessage() : e.getClass().getName()));
                }
                break;
            case "delete":
                if (args.length < 2) {
                    throw new IllegalArgumentException("Use: delete <id>");
                }
                em.deleteExpense(Integer.parseInt(args[1]));
                em.saveExpenses();
                break;
            case "list-general":
                em.listCategory(Category.GENERAL);
                break;
            case "list-food":
                em.listCategory(Category.FOOD);
                break;
            case "list-entertainment":
                em.listCategory(Category.ENTERTAINMENT);
                break;
            case "list-health":
                em.listCategory(Category.HEALTH);
                break;
            case "list-shopping":
                em.listCategory(Category.SHOPPING);
                break;
            case "list-bills":
                em.listCategory(Category.BILLS);
                break;
            case "list-all":
                em.listAll();
                break;

            default:
                System.out.println("Unknown command.");
                break;
        }

    }
}
