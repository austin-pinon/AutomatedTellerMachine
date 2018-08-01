package main;

import java.io.Console;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.HashSet;
import java.util.Set;

public class ATM {

  private static Set<Customer> customerTable = new HashSet<Customer>();

  private static final int sleepTime = 0;

  private static Customer activeCustomer = null;

  public static void main(String[] args) throws IOException, InterruptedException {

    initializeCustomers();

    initializeATM();

    Console input = System.console();

    if (input == null) {
      System.out.println("No console: non-interactive mode!");
      System.exit(0);
    }

    while (activeCustomer == null) {
      insertDebitCard(input);

      // Not implemented currently but we should pull the customer based on their debit card

      if (checkPIN(activeCustomer, input)) {
        // Proceed with account available actions
        loggedInActions(activeCustomer, input);
      } else
        System.out.println("Log in failed.");
    }


  }

  public static void initializeCustomers() {
    Customer customer1 = new Customer("Sample User", Customer.Language.ENGLISH, 1234);
    Customer customer2 = new Customer("Ruel Loehr", Customer.Language.ENGLISH, 4545);
    Customer customer3 = new Customer("Conor Eby", Customer.Language.ENGLISH, 7890);

    customerTable.add(customer1);
    customerTable.add(customer2);
    customerTable.add(customer3);
  }

  public static void initializeATM() throws InterruptedException {
    System.out.println(
        "Welcome to MoneyNet™, where currency is a figment of your imagination and the numbers on your screen don't accurately reflect whats ACTUALLY in our bank vault.");
    for (int i = 0; i < 3; i++) {
      TimeUnit.SECONDS.sleep(sleepTime);
      System.out.println("*");
    }

    System.out.println(
        "****************************************************************************************");
    System.out.println(
        "*******  M     M  OOOOOOO  N     N  EEEEEEE  Y     Y  N     N  EEEEEEE  TTTTTT *********");
    System.out.println(
        "*******  M M M M  OO   OO  N N   N  EE        Y   Y   N N   N  EE         TT   *********");
    System.out.println(
        "*******  M  M  M  OO   OO  N  N  N  EEEEEEE    Y Y    N  N  N  EEEEEEE    TT   *********");
    System.out.println(
        "*******  M     M  OO   OO  N   N N  EE          Y     N   N N  EE         TT   *********");
    System.out.println(
        "*******  M     M  OOOOOOO  N     N  EEEEEEE     Y     N     N  EEEEEEE    TT   *********");
    System.out.println(
        "****************************************************************************************");

    for (int i = 0; i < 3; i++) {
      TimeUnit.SECONDS.sleep(sleepTime);
      System.out.println("*");
    }


  }

  public static void insertDebitCard(final Console input) throws IOException {

    System.out.println(
        "****************************************************************************************");
    System.out.println(
        "* We cannot guarantee there isn't a card skimmer on this ATM. Proceed at your own risk.*");
    System.out.println(
        "****************************************************************************************");
    System.out.println();
    System.out.println();
    System.out.println("Please enter your debit card. (Press anything and then ENTER)");

    System.out.print(":");

    input.readLine();

    System.out.println();
    System.out.println(
        "We'll assume whatever you just put in is actually a debit card. Thanks for your cooperation!");
  }


  // Currently have customer as a parameter, but isn't really used. A more "final" version would
  // take a customer so I'm leaving this here for future updates.
  public static boolean checkPIN(final Customer customer, final Console input)
      throws NumberFormatException, IOException {
    System.out.println("Please enter your 4 digit PIN");
    System.out.println(":");

    Boolean pinEntered = null;

    Integer PIN = null;

    for (int i = 2; i >= 0; i--) {

      if (pinEntered == null || pinEntered == false) {
        PIN = Integer.parseInt(input.readLine());

        if (PIN.toString().length() != 4) {
          pinEntered = false;
          System.out.println("Please try again. " + i + " tries remaining.");
          System.out.println(":");
        } else {
          pinEntered = true;
          break;
        }
      }
    }

    // Couldn't correctly enter a pin in 3 attempts.
    if (!pinEntered) {
      System.out.println("You failed to enter the correct pin. Now logging out.");
      return false;
    }

    // Check if customer pin is correct.

    // Not currently implemented but obviously a debit card would have a direct link to a customer
    // but since I'm not implementing an actual debit card entry, I'll just loop through all the
    // customers (3).
    for (Customer c : customerTable) {
      if (c.checkPIN(PIN)) {
        activeCustomer = c;
        // Pin validated
        return true;
      }
    }

    return false;

  }

  public static void showAccountActions() {
    System.out.println("Enter 1 to show account balance.");
    System.out.println("Enter 2 to make a withdrawal.");
    System.out.println("Enter 3 to make a deposit.");
    System.out.println("Enter 0 to logout.");
    System.out.print(": ");
  }

  public static void loggedInActions(final Customer customer, final Console input)
      throws NumberFormatException, IOException {

    Account account = new Account(customer);

    System.out.println("Welcome back " + activeCustomer.getName() + ".");

    Boolean actionComplete = null;

    Integer action = null;

    while (actionComplete == null || actionComplete == false) {

      showAccountActions();

      action = Integer.parseInt(input.readLine());

      switch (action) {
        case 0:
          actionComplete = true;
          logout();
          break;
        case 1:
          showAccountBalance(account);
          break;
        case 2:
          makeWithdrawal(account, input);
          break;
        case 3:
          makeDeposit(account, input);
          break;
        default:
          // Try again
          actionComplete = false;
          System.out.println("Invalid entry. Please try again.");
          System.out.println();

      }
    }


  }

  private static void makeDeposit(final Account account, final Console input) {
    Boolean success = null;
    Double deposit = null;

    while (success == null || success == false) {

      System.out.print("Please enter your deposit amount: ");
      deposit = Double.parseDouble(input.readLine());

      if (deposit == 0) {
        return;
      }

      success = account.makeDeposit(deposit);
    }

    return;

  }

  private static void makeWithdrawal(final Account account, final Console input) {
    Boolean success = null;
    Integer withdrawal = null;

    while (success == null || success == false) {

      System.out.print("Please enter your withdrawal amount (multiples of 20): ");
      withdrawal = Integer.parseInt(input.readLine());

      success = account.makeWithdrawal(withdrawal);
    }
  }

  private static void showAccountBalance(final Account account) {
    System.out.println("This is your account balance: $" + account.getAccountBalance());
  }

  public static void logout() {
    System.out.println("Now logging out. Thanks for your business. Goodbye.");
    activeCustomer = null;
  }

}
