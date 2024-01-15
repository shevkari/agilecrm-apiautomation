package com.agilecrm_automation.stepdefinition;

import com.agilecrm_automation.common.CommonFunctions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.io.IOException;

public class COMStepDef {
    Response response;
    @Given("I get contacts information")
    public void iGetContactsInformation() throws IOException {
        CommonFunctions commonFunctions = new CommonFunctions();
        response = commonFunctions.getResponse("contact");
    }

    @Then("I verify all contacts in response")
    public void iVerifyAllContactsInResponse() {
        response.prettyPrint();
    }
}
