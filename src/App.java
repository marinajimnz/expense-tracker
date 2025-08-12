public class App {
    public static void main(String[] args) throws Exception {
        Expense ex1 = new Expense("Primer gasto", 50.034);
        Expense ex2 = new Expense("Segundo gasto", 400.012903, " shopping ");

        System.out.println(ex1);
        System.out.println(ex2);
    }
}
