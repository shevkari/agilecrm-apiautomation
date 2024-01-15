Feature: Verify CRUD operation on company module


  Scenario: Verify get all company info
    Given I setup a request structure to get company information
      | method |
      | POST   |
    When I hit an api to get all company info
    Then I verify company response
    * I get name of all companies
    * I get name of Companies which has tag is not empty


  @GetCompany
    Scenario: verify search feature for company
      Given I setup a request structure to get company info with below keyword
      |q        |spicejet|
      |page_size|10      |
      |  type   |COMPANY |
      When I hit an api to search company with keywords
      Then I get result according to keywords

#    @CreateCompany
    Scenario: verify create company with valid details using string request body
      Given I setup a request structure to create company with following info
      |compName|cyber success              |
      |url     |htps://www.cybersuccess.biz|
      When I hit a create company api
      Then I verify company created successfully


  #@CreateCompany
  Scenario: verify create company with valid details using hashmap request body
    Given I setup a request structure to create company with hashmap object
      |compName|cyber success              |
      |url     |htps://www.cybersuccess.biz|
    When I hit a create company api
    Then I verify company created successfully with 200 status code


  #@CreateCompany
  Scenario Outline:verify create company with valid details using hashmap request body
    Given I setup a request structure to create company with hasmap object
      |compName|  url |
      |<name>  | <url>|
    When I hit a create company api
    Then I verify company created successfully with <statusCode> code
    Examples:
      | name   | url                          | statusCode |
      | Cyber  | https://www.cybersuccess.biz | 200        |
      |        | https://www.google.com       | 400        |
      | Cyber  |                              | 400        |
      |        |                              | 400        |
      | null   | https://www.google.com       | 200        |
      | google | null                         | 200        |
      | null   | null                         | 200        |


  @CreateCompanyUsingFile
  Scenario: verify create company with valid details using json file request body
    Given I setup a request structure to create company with json file
    When I hit a create company api
    Then I verify company created successfully with 200 status code

  @CompanySerialization
  Scenario: Verify Create Company record
    Given I setup a request structure to create company
    When I hit an api to create company
    Then I verify Company Created successfully

  @CompanyDeSerialization
  Scenario: Verify Create Company record using Deserialization
    Given I setup a request structure to create company
    When I hit an api to create company
    Then I verify Company response using Deserialization successfully

  @CompanySerialization&DeSerialization
  Scenario: Verify Create Company record
    Given I setup a request structure to create company
    When I hit an api to create company
    Then I verify Company Created successfully using Deserialization
    Then I Verify Particular company in response by DeSerialization
    Then I verify particular company in get all company Api

    @EndToEndScenario
    Scenario: Verify CRUD operations on Company Api
      Given I setup a request to create company using request body using Serialization
      When I hit an api to create
      Then I verify created company in the response using DeSerialization
      When I update the name and url of company
      Then I verify response in particular Company using DeSerialization
      And I verify response in get all company using Deserialization
      When I delete Company
      Then I verify delete Company Successfully in particular company Api
      And I verify company deleted successfully in get all Company Api

