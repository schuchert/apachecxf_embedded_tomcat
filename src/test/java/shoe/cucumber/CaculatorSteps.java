package shoe.cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.MappingJsonFactory;
import shoe.calculator.service.Result;
import shoe.util.EmbeddedTomcat;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CaculatorSteps {
    private String endpointUrl;
    private List<JacksonJsonProvider> providers;
    private Response response;

    @Given("^a cleared calculator$")
    public void a_cleared_calculator() throws Throwable {
        endpointUrl = EmbeddedTomcat.instance().getEndpointUrl();
        providers = Arrays.asList(new JacksonJsonProvider());
    }

    @When("^the calculator is asked to execute \"(.*?)\"$")
    public void the_calculator_is_asked_to_execute(String expression) throws Throwable {
        WebClient client = WebClient.create(endpointUrl + "/calculator/evaluate/" + expression, providers);
        response = client.get();
    }

    @Then("^the result should be \"(.*?)\"$")
    public void the_result_should_be(String expected) throws Throwable {
        Result result = extractResult(response);
        assertThat(result.getCurrentValue(), is(expected));
    }

    private Result extractResult(Response response) throws IOException {
        MappingJsonFactory factory = new MappingJsonFactory();
        JsonParser parser = factory.createJsonParser((InputStream) response.getEntity());
        return parser.readValueAs(Result.class);
    }
}
