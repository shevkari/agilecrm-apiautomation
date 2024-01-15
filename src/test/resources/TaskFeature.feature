Feature: Verify CURD operations on Task Module

  #@createTask
  Scenario: Verify create Task with valid details using String request body
    Given I setup a request structure to create Task with following info
      | type          | Call |
      | priority_type | HIGH |
    When I hit an api to create Task
    Then I verify with Task created Successfully with 200 status code

  #@createTask
  Scenario Outline: Verify create Task with valid details using HashMap request body
    Given I setup a request structure to create Task with following HashMap info
      | type   | priority_type   |
      | <type> | <priority_type> |
    When I hit an api to create Task
    Then I verify with Task created Successfully with <stastuscode> code
    Examples:
      | type  | priority_type | stastuscode |
      | call  | HIGH          | 200        |
      | EMAIL | high          | 400        |
      |       | HIGH          | 400        |
      | call  |               | 400        |
      | null  | HIGH          | 400        |
      | call  | null          | 400        |
      | null  | null          | 400        |
      |       |               | 400        |

  @createTaskWithFile
  Scenario: Verify create Task with valid details using File body
    Given I setup a request structure to create Task with File body
    When I hit an api to create Task
    Then I verify with Task created Successfully with 200 status code

  @TaskSerialization
  Scenario: Verify Create Task record
    Given I setup a request structure to Create Task
    When I hit an api to Create Task
    Then I verify Task Created successfully

  @EndToEndScenarioTask
  Scenario: Verify CRUD operations on Task Api
    Given I setup a request to create Task using request body using Serialization
    When I hit an api to Create Task using Serialization
    Then I verify created Task in the response using DeSerialization
    When I update the name and url of Task
    Then I verify response in particular Task using DeSerialization
    And I verify response in get all Task using Deserialization
    When I delete Task
    Then I verify delete Task Successfully in particular Task Api
    And I verify Task deleted successfully in get all Task Api  
