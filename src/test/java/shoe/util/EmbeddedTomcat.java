package shoe.util;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class EmbeddedTomcat {
    private static EmbeddedTomcat theInstance;

    private Tomcat tomcat;
    private String endpointUrl;

    public static EmbeddedTomcat instance() throws ServletException, LifecycleException {
        init();
        return theInstance;
    }

    private static void init() throws ServletException, LifecycleException {
        if (theInstance == null) {
            theInstance = new EmbeddedTomcat();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        theInstance.tomcat.stop();
                    } catch (LifecycleException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private EmbeddedTomcat() throws ServletException, LifecycleException {
        String webPort = calculatePort();
        String defaultPort = String.format("http://localhost:%s", webPort);
        endpointUrl = System.getProperty("service.url", defaultPort);

        String webappDirLocation = "src/main/webapp/";

        tomcat = new Tomcat();
        tomcat.setPort(Integer.valueOf(webPort));
        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        tomcat.start();
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    private String calculatePort() {
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        return webPort;
    }

    public static void main(String args) throws Exception {
        instance();
        System.in.read();
    }
}
