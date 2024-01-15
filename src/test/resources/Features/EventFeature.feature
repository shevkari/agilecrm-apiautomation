Feature: Verify CRUD operations on EVENT Module

 # @createEvent
  Scenario: Verify create Event with valid details using String request body
    Given I setup a request structure to create Event with following info
      | title | Tomato Event |
      | color | green         |
    When I hit an api to create Event
    Then I verify with Event created Successfully

  #@createEvent
  Scenario Outline: Verify create Event with valid details using HashMap request body
    Given I setup a request structure to create Event with following info using HashMap
      | title   | color   |
      | <title> | <color> |
    When I hit an api to create Event
    Then I verify with Event created Successfully with <statuscode> status code
    Examples:
      | title           | color  | statuscode |
      | new year event  | light  | 200        |
      | Christmas Event | shower | 200        |
      |                 | Bright | 400        |
      | null            | null   | 400        |
      |                 |        | 400        |
      | null            | Green  | 400        |
      | new year        | null   | 400        |
      | christmas       |        | 400        |

  @createEventWithFile
  Scenario: Verify create Event with valid details using File request body
    Given I setup a request structure to create Event with following info using File
    When I hit an api to create Event
    Then I verify with Event created Successfully with status code 200

  @EventSerialization
  Scenario: Verify Create Event record
    Given I setup a request structure to Create Event
    When I hit an api to Create Event
    Then I verify Event Created successfully

  @EndToEndScenarioEvent
  Scenario: Verify CRUD operations on Event Api
    Given I setup a request to create Event using request body using Serialization
    When I hit an api to create event
    Then I verify created Event in the response using DeSerialization
    When I update the name and url of Event
    Then I verify response in particular Event using DeSerialization
    And I verify response in get all Event using Deserialization
    When I delete Event
    Then I verify delete Event Successfully in particular Event Api
    And I verify Event deleted successfully in get all Event Api