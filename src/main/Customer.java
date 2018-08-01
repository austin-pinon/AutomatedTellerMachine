package main;

import java.util.UUID;


public class Customer {

  // Not implemented here but we should localize to common languages
  public static enum Language {
    ENGLISH {
      public String toString() {
        return "English";
      }
    }
  }

  private UUID id;

  private Integer PIN;

  private String name;

  private Language languagePreference = Language.ENGLISH;

  public Customer() {
    this.id = java.util.UUID.randomUUID();
  }

  public Customer(final String name, final Language languagePreference, final Integer PIN) {
    this();

    this.name = name;
    this.languagePreference = languagePreference;
    this.PIN = PIN;
  }

  public boolean requestNewPin(final Integer oldPIN, final Integer newPIN) {
    // Checks if PIN matches internal PIN
    if (oldPIN.equals(this.PIN)) {
      // Returns true if successful, false if non-valid PIN
      return this.setPIN(newPIN);
    } else
      return false;
  }

  public UUID getId() {
    return this.id;
  }

  public boolean checkPIN(final Integer PIN) {
    if (this.PIN.equals(PIN)) {
      return true;
    } else
      return false;
  }

  public String getName() {
    return this.name;
  }

  public String getLanguage() {
    return this.languagePreference.toString();
  }

  public void setLanguage(Language languagePreference) {
    // Not implemented here but we should update the language preference stored in the user database
    this.languagePreference = languagePreference;
  }

  private boolean setPIN(final Integer PIN) {
    if (isValidPIN(PIN)) {
      // Not implemented here but we should update the pin stored in the user database
      this.PIN = PIN;
      return true;
    } else
      return false;
  }

  private boolean isValidPIN(final Integer PIN) {

    // Check for non null value
    if (PIN == null) {
      return false;
    }

    // Convert to String
    String pinString = PIN.toString();

    // Check for valid length
    if (pinString.length() != 4) {
      return false;
    }

    // Check for valid number
    try {
      Integer.parseInt(pinString);

      return true;

    } catch (NumberFormatException e) {
      return false;
    }

  }

}
