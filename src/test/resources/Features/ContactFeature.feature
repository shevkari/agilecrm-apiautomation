Feature: Verify Contact api's


  Scenario: Verify get all Contact info
    Given I setup a request structure to get contact information
    When I hit api of get all Contact
    Then I verify Contact response


  @GetMockData
    Scenario: Verify get contact info by id
      Given I setup a request structure to get contact information
      When I hit an api of get contact by id
      Then I verify Contact by id response


    Scenario: Verify get all contact info
      Given I setup a request structure to get contact information
      When I hit api of get all Contact
      Then I verify Contact response
      When I hit an api of get contact by ID
      Then I verify contact by id in response


    Scenario: Verify get all contact info
      Given I setup a request structure to get contact information
      When I hit api of get all Contact
      Then I verify Contact response
      When I setup request structure to get contact by id
      And I hit an api of get contact by id with path param
      Then I verify contact by id in response as pathparam

  @getContact
  Scenario: Verify get all contact info
    Given I setup a request structure to get contact information
    When I hit api of get all Contact
    Then I verify Contact response
    When I setup request structure to get contact by id using loop
    And I hit an api of get contact by id with path param using loop
    Then I verify contact by id in response as iteration of pathParam


  #@CreateContact
  Scenario: verify create contact with valid details using string request body
    Given I setup a request structure to create contact with following info
      |firstname|Virat|
      |lastname |Kohli|
    When I hit a create contact api
    Then I verify contact created successfully

  @CreateContact
    Scenario Outline: Verify create contact with details using Hashmap body
      Given I setup a request structure to create contact with following hashmap information
    |firstname|lastname|email|
    |<firstname>|<lastname>|<email>|
      When I hit an Post Api to create Contact
      Then I verify with Contact created with <statuscode> status code
      Examples:
        | firstname | lastname | email                 | statuscode |
        | rohit     | sharma   | rohitsharma@gmail.com | 200        |
        |           | sharma   | rohitsharma@gmail.com | 400        |
        | rohit     |          | rohitsharma@gmail.com | 400        |
        | rohit     | sharma   |                       | 400        |
        | null      | shrama   | rohitsharma@gmail.com | 400        |
        | rohit     | null     | rohitsharma@gmail.com | 400        |
        | rohit     | sharma   | null                  | 400        |
        | null      | null     | null                  | 400        |
        | null      | null     | rohitsharma@gmail.com | 400        |
        | rohit     | null     | null                  | 400        |
        | null      | sharma   | null                  | 400        |

  #@CreateContactWithFile
  Scenario: verify create contact with valid details using File request body
    Given I setup a request structure to create contact with file info
    When I hit a create contact api
    Then I verify contact created successfully with 200 status code

  @ContactSerialization
  Scenario: Verify Create Contact record
    Given I setup a request structure to create contact
    When I hit an api to create contact
    Then I verify Contact Created successfully

  @ContactSerialization&Deserialization
  Scenario: Verify Create Contact record
    Given I setup a request structure to create contact
    When I hit an api to create contact
    Then I verify Contact Created successfully with Deserialization
    Then I verify created contact in response with by contact id Api
    Then I verify created contact in get all contact response

  @EndToEndScenarioContact
  Scenario: Verify CRUD operations on Contact Api
    Given I setup a request to create Contact using request body using Serialization
    When I hit an api to create Contact
    Then I verify created Contact in the response using DeSerialization
    When I update the name and url of Contact
    Then I verify response in particular Contact using DeSerialization
    And I verify response in get all Contact using Deserialization
    When I delete Contact
    Then I verify delete Contact Successfully in particular contact Api
    And I verify Contact deleted successfully in get all Contact Api

    @ContactDynamicSearch
    Scenario: Verify dynamic search operation on Contact Api
      Given I setup a request structure to search contact dynamic search
      When I hit an Api to search Contact
      Then I verify Contacts are in sorted order in the response