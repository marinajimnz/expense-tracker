public class App {
    public static void main(String[] args) throws Exception {
        
        ExpensesManager em = new ExpensesManager();
        
        if (args.length < 1) {
            throw new IllegalArgumentException("Use:\n" +
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
                Integer id = Integer.parseInt(args[1]);
                String newDesc = "null".equalsIgnoreCase(args[2]) ? null : args[2];
                Double newAmount = "null".equalsIgnoreCase(args[3]) ? null : Double.parseDouble(args[3]);
                String newCat = "null".equalsIgnoreCase(args[4]) ? null : args[4];
                em.updateExpense(id, newDesc, newAmount, newCat);
                em.saveExpenses();
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
