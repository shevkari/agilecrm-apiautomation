Feature: Verify CRUD operations on Deal Module

  #@createDeal
  Scenario: Verify create Deal with valid details using String request body
    Given I setup a request structure to create Deal with following info
      | name           | tomato |
      | expected_value | 600    |
    When I hit an api to create Deal
    Then I verify with Deal created Successfully


  #@createDeal
  Scenario Outline: Verify create Deal with valid details using HashMap request body
    Given I setup a request structure to create Deal with following info in hashMap
      | name   | expected_value |milestone|
      | <name> | <value>        |<milestone>|
    When I hit an api to create Deal
    Then I verify with Deal created Successfully with <statuscode> status code
    Examples:
      | name       | value | milestone | statuscode |
      | tomato     | 600   | Proposal  | 200        |
      |            | 600   | Proposal  | 200        |
      | new tomato |       | Proposal  | 400        |
      | null       | 600   | Proposal  | 200        |
      | new tomato | null  | Proposal  | 400        |
      | null       | null  | Proposal  | 400        |
      | tomato     | 600   | null      | 200        |
      | tomato     | 500   |           | 400        |
      | null       | null  | null      | 400        |
      | tomato     | null  | null      | 400        |
      | null       | 500   | null      | 200        |

    @createDealWithFile
    Scenario: Verify create deal with file request body
      Given I setup a request structure to create Deal with file request body
      When I hit an api to create Deal
      Then I verify with deal created successfully with 200 status code

  @DealSerialization
  Scenario: Verify Create Deal record
    Given I setup a request structure to Create deal
    When I hit an api to Create Deal
    Then I verify Deal Created successfully

  @DealSerialization&DeSerialization
  Scenario: Verify Create Deal record
    Given I setup a request structure to Create deal
    When I hit an api to Create Deal
   Then I verify Deal Created successfully with Deserialization

  @EndToEndScenarioDeal
  Scenario: Verify CRUD operations on Deal Api
    Given I setup a request to create Deal using request body using Serialization
    When I hit an api to create deal
    Then I verify created Deal in the response using DeSerialization
    When I update the name and url of Deal
    Then I verify response in particular Deal using DeSerialization
    And I verify response in get all Deal using Deserialization
    When I delete Deal
    Then I verify delete Deal Successfully in particular Deal Api
    And I verify Deal deleted successfully in get all Deal Api
