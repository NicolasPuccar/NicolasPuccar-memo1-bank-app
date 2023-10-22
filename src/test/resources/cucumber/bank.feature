Feature: Bank account promo, get 10% extra in your $2000+ deposits, up to $500

  Scenario: Successfully promo applied, cap not reached.
    Given Account nico with a balance of 0
    When Trying nico to deposit 2000
    Then Account balance should be 2200

  Scenario: Successfully promo applied, cap reached.
    Given Account nico with a balance of 0
    When Trying nico to deposit 6000
    Then Account balance should be 6500

  Scenario: Promo not applied
    Given Account nico with a balance of 0
    When Trying nico to deposit 1500
    Then Account balance should be 1500

  Scenario: Cannot withdraw more money than the account balance
    Given Account nico with a balance of 1000
    When Trying nico to withdraw 1001
    Then Operation should be denied due to insufficient funds
    And Account balance should remain 1000

  Scenario: Cannot deposit money when sum is negative
    Given Account nico with a balance of 200
    When Trying nico to deposit -100
    Then Operation should be denied due to negative sum
    And Account balance should remain 200

  Scenario: Successfully withdraw money when balance is enough
    Given Account nico with a balance of 1000
    When Trying nico to withdraw 500
    Then Account balance should be 500

  Scenario: Successfully deposit money when sum is not negative
    Given Account nico with a balance of 1000
    When Trying nico to deposit 500
    Then Account balance should be 1500