Feature: Verify Reqres api's

  Background: Verify MockServer Data
    Given I setup request structure and hit an api to get data from mock server
    Then I verify with response of Mock data

  Scenario: verify get all users info
    Given I setup a request structure to get user information
    When I hit get all users api
    Then I verify response with users

  @ReqRes
    Scenario: verify total users on all pages
    Given I setup a request structure to get user information
    When I hit get all users api
    Then I verify total users in response

  #@ReqRes
  Scenario: verify particular user info
    Given I setup a request structure to get user info
    When I hit an api to get all users
    Then I verify particular user in response

    #@ReqresEx
    Scenario: Verify particular user info as per req
      Given I setup request structure
      When I hit an api to get info
      Then I verify the response

  #http://desktop-calvdqi:82/login.do

  @ReqresSerialization
  Scenario: Verify Total user records on all pages
    Given I setup request structure to create user
    When I hit a api to create user
    Then I verify user created successfully


  @ReqresDeSerialization
  Scenario: Verify Total user records on all pages
    Given I setup request structure to get user
    When I hit a api to get user
    Then I verify user Retrieve successfully

    @reqresSerial&Deserializ
    Scenario: Verify serialization and Deserialization
      Given I setup a request structure to create user by using serialization
      When I hit a api to create user
      Then I verify response using deserialization