package main;

import java.util.UUID;

public class Account {

  private UUID accountNumber;

  private Customer customer;

  // https://smartasset.com/checking-account/checking-account-average-balance
  private Double accountBalance = 2900.00;

  // https://smartasset.com/checking-account/daily-atm-withdrawal-limits-daily-debit-purchase-limits
  private Double withdrawalLimit = 1000.00;

  // https://www.fdic.gov/deposit/deposits/
  private Double maxAccountBalance = 250000.00;

  public Account(Customer customer) {
    this.customer = customer;

    // Currently not implemented but here you would query a database and find the customer's account
    // and populate the data.
    this.accountNumber = UUID.randomUUID();
    this.populateAccountData(this.customer);
  }

  public Double getAccountBalance() {
    return this.accountBalance;
  }

  public Boolean makeDeposit(final Double deposit) {

    if (deposit == null || deposit < 0) {
      System.out.println("Invalid deposit amount. Try again OR enter 0 to cancel.");
      // Invalid input
      return null;
    }

    if (deposit + accountBalance > maxAccountBalance) {
      System.out.println(
          "Depositing amount greater than account limit. Please try again or enter 0 to cancel.");
      // Overflow on account limit
      return false;
    }

    // Not implemented here but you would make a transaction request to update the account balance
    // in the database.
    this.accountBalance += deposit;
    this.updateDB(this.accountNumber, this.customer, this.accountBalance);


    // Successful deposit
    return true;

  }

  // Only can withdraw in units of 20
  public Boolean makeWithdrawal(final Integer withdrawal) {
    if (withdrawal % 20 != 0) {

      System.out.println("Not a multiple of 20. Try again OR enter 0 to cancel.");
      // Invalid request
      return null;
    }

    if (withdrawal > this.withdrawalLimit) {
      System.out.println("Amount greater than withdrawal limit of $" + this.withdrawalLimit
          + ". Please try again or enter 0 to cancel.");
      return false;
    }

    if (this.accountBalance - withdrawal < 0) {
      System.out.println(
          "Withdrawing amount greater than account balance. Please try again or enter 0 to cancel.");
      return false;
    }

    // Not implemented here but you would make a transaction request to update the account balance
    // in the database as well as keeping track of all withdrawals across a 24 hour period to ensure
    // withdrawal limit isn't reached.
    this.accountBalance -= withdrawal;
    this.updateDB(this.accountNumber, this.customer, this.accountBalance);

    // Successful withdrawal
    return true;
  }

  // Not implemented
  private boolean updateDB(final UUID accountNumber, final Customer customer,
      final Double accountBalance) {
    return true;
  }

  // Not implemented
  private boolean populateAccountData(final Customer customer) {
    // TODO retrieve user account data via customerId
    return true;
  }
}
