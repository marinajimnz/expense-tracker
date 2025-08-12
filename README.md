# Expense Tracker CLI

A simple command-line interface (CLI) application to manage and track your personal expenses. This application allows you to keep track of your spending, categorize expenses, and get insights into your financial habits.

## Description

Expense Tracker is a command-line tool that helps you manage your finances efficiently. All expenses are stored in a local JSON file, making it easy to use and requiring no additional setup.

## Features

- 💰 **Add expenses**: Create new expenses with description, amount, and optional category
- ✏️ **Update expenses**: Modify existing expense details
- 🗑️ **Delete expenses**: Remove expenses you no longer need
- 📋 **List expenses**: View all expenses with detailed information
- 📊 **Summary reports**: Get total expenses or monthly summaries
- 🏷️ **Categories**: Organize expenses by category and filter by them
- 💾 **Local storage**: Expenses are saved in a JSON file
- 🔄 **CSV export**: Export your expenses to CSV format
- 📈 **Budget tracking**: Set monthly budgets and get warnings when exceeded

## Requirements

- **Java 8 or higher**
- No external dependencies - uses only Java standard library

## Installation

1. Clone or download the project
2. Navigate to the project directory
3. Compile the Java files:
   ```bash
   javac src/*.java
   ```
4. Run the application:
   ```bash
   java -cp src App [commands]
   ```

## Project Structure

```
src/
├── App.java         # Main CLI application and user interface
├── Expense.java     # Expense model (POJO)
└── Store.java       # Data persistence (CSV operations)
```

## Usage

### Available Commands

#### Add a new expense
```bash
java -cp src App add --description "Lunch" --amount 20
# Output: Expense added successfully (ID: 1)

java -cp src App add --description "Groceries" --amount 50 --category "Food"
# Output: Expense added successfully (ID: 2)
```

#### Update an existing expense
```bash
java -cp src App update --id 1 --description "Business lunch" --amount 25
```

#### Delete an expense
```bash
java -cp src App delete --id 2
# Output: Expense deleted successfully
```

#### List all expenses
```bash
java -cp src App list
# ID  Date       Description    Amount   Category
# 1   2024-08-06  Business lunch  $25     Food
# 3   2024-08-06  Gas           $40     Transport
```

#### List expenses by category
```bash
java -cp src App list --category "Food"
```

#### View expense summary
```bash
# Total expenses
java -cp src App summary
# Output: Total expenses: $65

# Monthly summary
java -cp src App summary --month 8
# Output: Total expenses for August: $65
```

#### Export to CSV
```bash
java -cp src App export --file "expenses_2024.csv"
# Output: Expenses exported successfully to expenses_2024.csv
```

#### Set monthly budget
```bash
java -cp src App budget --month 8 --amount 1000
# Output: Budget for August set to $1000
```

## Expense Structure

The `Expense.java` POJO contains the following properties:

- **id**: Unique expense identifier (int)
- **date**: Date of expense (String in YYYY-MM-DD format) 
- **description**: Brief expense description (String)
- **amount**: Expense amount (double, positive decimal number)
- **category**: Expense category (String, defaults to "General")

## Storage

Expenses are stored in an `expenses.csv` file in the current directory. This file is automatically created if it doesn't exist.

Example CSV file structure:
```csv
id,date,description,amount,category
1,2024-08-06,Business lunch,25.00,Food
2,2024-08-06,Gas,40.00,Transport
3,2024-08-06,Movie ticket,12.00,Entertainment
```

## Default Categories

- **Food**: Meals, groceries, dining out
- **Transport**: Gas, public transport, parking
- **Entertainment**: Movies, games, hobbies
- **Health**: Medical, pharmacy, fitness
- **Shopping**: Clothes, electronics, household items
- **Bills**: Utilities, rent, subscriptions
- **General**: Miscellaneous expenses

## Budget Management

Monthly budgets can be stored in a separate `budgets.csv` file with the following structure:
```csv
month,budget
2024-08,1000.00
2024-09,1200.00
```

When expenses exceed 80% of the monthly budget, a warning is displayed. When the budget is exceeded, an alert is shown.

## Implementation

### Technical Features

- **App.java**: Command-line interface and user interaction
- **Expense.java**: Simple POJO model for expense data
- **Store.java**: CSV file operations and data persistence
- Uses Java's built-in argument parsing
- Comprehensive error handling and input validation
- CSV format for easy data interchange
- No external dependencies - pure Java implementation

### Recommended Development Flow

1. **Environment setup**: Ensure Java 8+ is installed
2. **Create Expense POJO**: Implement the basic expense model
3. **Implement Store class**: CSV read/write operations and ID management
4. **Build CLI interface**: Implement App.java with command parsing
5. **Core CRUD operations**: Add, update, delete, list expenses
6. **Summary and reporting**: Implement summary calculations
7. **Advanced features**: Categories, budgets, enhanced filtering
8. **Testing**: Verify each feature thoroughly
9. **Documentation**: Complete code documentation

## Error Handling

The application handles various error scenarios:

- **Invalid amounts**: Negative or non-numeric values
- **Non-existent IDs**: Attempting to update/delete non-existent expenses
- **Invalid dates**: Malformed date formats
- **File system errors**: Permission issues, disk space
- **Invalid categories**: Non-existent category filters
- **CSV export errors**: File write permissions

## Contribution

This project is designed as a practice exercise to improve programming skills, including:

- Command-line application development
- File system operations
- JSON data manipulation
- Data validation and error handling
- Financial data management
- CSV file generation

## Example Workflow

```bash
# Compile the project
javac src/*.java

# Add some expenses
java -cp src App add --description "Coffee" --amount 4.50 --category "Food"
java -cp src App add --description "Bus ticket" --amount 2.75 --category "Transport"
java -cp src App add --description "Movie ticket" --amount 12.00 --category "Entertainment"

# View all expenses
java -cp src App list

# Check summary
java -cp src App summary

# Set a budget
java -cp src App budget --month 8 --amount 500

# Export to CSV for analysis
java -cp src App export --file "august_expenses.csv"

# Filter by category
java -cp src App list --category "Food"
```

## Notes

- All monetary amounts are stored with 2 decimal precision
- Dates are stored in ISO format (YYYY-MM-DD)
- The CSV files are automatically created in the current directory
- Expense IDs are automatically assigned incrementally by Store.nextId()
- CSV format allows easy data interchange with spreadsheet applications

---

Start managing your expenses efficiently with Expense Tracker CLI 💰

## Project URL

This project is inspired by the roadmap.sh backend projects: https://roadmap.sh/backend/projects