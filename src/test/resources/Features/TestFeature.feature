Feature: Verify Gorest Api

  @Test
  Scenario: Verify name values of all objects who's field is empty
    Given I setup a request Structure to get information of name values
    When I hit a api to get details
    Then I verify response with graphql data

    @premTest
    Scenario: Verify the user whose status is active
      Given I setup a request structure to get information of active users
      |status|active|
      When I hit an api to get details
      Then I verify response with active users

  @MadamTest
  Scenario: Verify the user whose status is active
    Given I setup a request structure to get information of all users
    When I hit an api to get details of all users
    Then I verify response with active and inactive users
