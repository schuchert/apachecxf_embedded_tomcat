package shoe.calculator;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.junit.Before;
import org.junit.Test;
import shoe.calculator.service.Request;
import shoe.calculator.service.Result;
import shoe.util.EmbeddedTomcat;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class RpnCalculatorServiceTest {
    private String endpointUrl;
    private List<JacksonJsonProvider> providers;

    @Before
    public void endpoint() throws Exception {
        endpointUrl = EmbeddedTomcat.instance().getEndpointUrl();
        providers = Arrays.asList(new JacksonJsonProvider());
    }

    @Test
    public void evaluateUsingHttpGet() throws Exception {
        WebClient client = WebClient.create(endpointUrl + "/calculator/evaluate/3 5 +/", providers);
        Response r = client.get();
        assertSuccessfulResult(r);

        Result output = extractResult(r);
        assertThat(output.getCurrentValue(), is("8"));
    }

    @Test
    public void evaluateUsingHttpPost() throws Exception {
        Request request = new Request("6 9 + 3 *");
        WebClient client = WebClient.create(endpointUrl + "/calculator/evaluate/", providers);
        Response r = client.accept("application/json")
                .type("application/json")
                .post(request);

        assertSuccessfulResult(r);

        Result output = extractResult(r);
        assertThat(output.getCurrentValue(), is("45"));
    }

    private void assertSuccessfulResult(Response r) {
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
    }

    private Result extractResult(Response r) throws IOException {
        MappingJsonFactory factory = new MappingJsonFactory();
        JsonParser parser = factory.createJsonParser((InputStream) r.getEntity());
        return parser.readValueAs(Result.class);
    }

}
