package shoe.apachedxf;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import shoe.util.EmbeddedTomcat;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class HelloWorldIT {
    private static EmbeddedTomcat tomcat;

    @BeforeClass
    public static void beforeClass() throws Exception {
        tomcat = EmbeddedTomcat.instance();
    }

    @Test
    public void testPing() throws Exception {
        String whatToEcho = "SierraTangoNevada";

        WebClient client = WebClient.create(tomcat.getEndpointUrl() + "/hello/echo/"+whatToEcho);
        Response r = client.accept("text/plain").get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream) r.getEntity());
        assertThat(value, is(whatToEcho));
    }

    @Test
    public void testJsonRoundtrip() throws Exception {
        List<JacksonJsonProvider> providers = Arrays.asList(new JacksonJsonProvider());
        JsonBean inputBean = new JsonBean();
        inputBean.setVal1("Maple");
        WebClient client = WebClient.create(tomcat.getEndpointUrl() + "/hello/jsonBean", providers);
        Response r = client.accept("application/json")
                .type("application/json")
                .post(inputBean);
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        MappingJsonFactory factory = new MappingJsonFactory();
        JsonParser parser = factory.createJsonParser((InputStream) r.getEntity());
        JsonBean output = parser.readValueAs(JsonBean.class);
        assertThat(output.getVal2(), is("Maple"));
    }
}
