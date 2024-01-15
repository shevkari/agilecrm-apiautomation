Feature: verify contact's api

  @GetContactList
  Scenario: verify get all contact api response
    Given I get contacts information
    Then I verify all contacts in response

