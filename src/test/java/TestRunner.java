import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/",
        glue = {"com.agilecrm_automation.stepdefinition",
        "com.agilecrm_automation.common.Hooks"},

        plugin = {
                "html:target/cucumber-reports.html",
                "junit:target/cucumber-reports/Cucumber.xml"
        },
      //  tags = "@GetCompany"
     //   tags = "@ReqRes"
    //    tags = "@getContact"
     //   tags = "@CreateCompany"
     //   tags = "@CreateContact"
    //    tags = "@createDeal"
      //  tags = "@createEvent"
      //  tags ="@createTask"
      //  tags = "@CreateCompanyUsingFile"
      //  tags ="@CreateContactWithFile"
      //  tags = "@createDealWithFile"
      //  tags = "@createEventWithFile"
      //  tags = "@createTaskWithFile"
      //  tags = "@ReqresEx"
      //  tags = "@ReqresSerialization"
      //  tags = "@DummyApi"
      //  tags = /*"@ContactSerialization"*/ /*"@CompanySerialization"*/ /*"@DealSerialization"*/ /*"@TaskSerialization"*/ "@EventSerialization"
      //  tags = "@Test"  /*"@premTest"*/   /*"@MadamTest"*/
      //  tags = /*"@ReqresDeSerialization"*/  "@CompanyDeSerialization"
      // tags = /*"@reqresSerial&Deserializ"*/ "@CompanySerialization&DeSerialization"
      //  tags = "@ContactSerialization&Deserialization"
      //  tags = "@DealSerialization&DeSerialization"
      //  tags =/*"@EndToEndScenario"*/ /*"@EndToEndScenarioContact"*/ "@EndToEndScenarioDeal"
      //  tags = "@EndToEndScenarioEvent"
      //  tags = /*"@FileUpload"*/ "@FileDownloadByteStream"
      //  tags ="@ContactDynamicSearch"
        tags ="@GetContactList"
 )
public class TestRunner {
}
