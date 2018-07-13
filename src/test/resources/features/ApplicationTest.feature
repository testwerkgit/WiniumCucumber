Feature: Start an application, wait a few seconds and close it again

  Scenario: Search for a book
    Given I login as user "asklant"
    When I search for a book with the title "Harry Potter"
    Then I can use the ISBN

#  Scenario: Search for a customer
#    Given I login as user "asklant"
#    When I search for a person with the name "jansen"
#    Then I can use the customernumber