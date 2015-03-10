import org.junit.*;

import controllers.DashboardController;
import play.Logger;
import play.mvc.*;
import play.test.*;
import play.libs.F.*;
import scala.PartialFunction;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(9010, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:9010");
                assertThat(browser.pageSource()).contains("Welcome to Sample Sphere DEMO.");
                Result result = routeAndCall(fakeRequest(GET, "/dashboard"));
                assertThat(result).isNotNull();
                assertThat(status(result)).isEqualTo(200);
                assertThat(contentAsString(result)).contains("productsCount");
                assertThat(contentAsString(result)).contains("customersCount");
                assertThat(contentAsString(result)).contains("ordersPerCustomer");
            }
        });
    }
    

}
