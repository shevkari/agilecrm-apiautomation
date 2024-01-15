Feature:

  @DummyApi
  Scenario: Verify get all Employee
  Given I set request structure to get all employee info
    When I hit an api to get employee
    Then I verify with employee response
