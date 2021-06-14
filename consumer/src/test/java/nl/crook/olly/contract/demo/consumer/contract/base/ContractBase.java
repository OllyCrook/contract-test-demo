package nl.crook.olly.contract.demo.consumer.contract.base;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import nl.crook.olly.contract.demo.consumer.contract.utils.ContractTestInitialiser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.util.Collection;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;
import static org.springframework.cloud.contract.wiremock.WireMockSpring.options;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)    // random port
@ActiveProfiles("functional-tests")
@DirtiesContext
public class ContractBase {

    private WireMockServer wireMockServer;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    ContractTestInitialiser functionalContractInitialiser;

    @BeforeEach
    public void setup() {
        final Collection<Filter> filterCollection = this.webApplicationContext.getBeansOfType(Filter.class).values();
        final Filter[] filters = filterCollection.toArray(new Filter[filterCollection.size()]);

        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilters(filters)
                .build());

        this.wireMockServer = new WireMockServer(options().port(0)); // random port
        this.wireMockServer.start();

        // verify that there were no unmatched Wiremock requests
        final List<LoggedRequest> allUnmatchedRequests = this.wireMockServer.findAllUnmatchedRequests();
        assertThat(allUnmatchedRequests.size()).isEqualTo(0);

        WireMock.resetAllRequests();
    }

    // helper methods to verify if calls are made (or not made) to wiremock services (add new methods if needed for putRequestFor, deleteRequestFor, etc)
    public void verifyGetRequestFor(final String path, final int times) {
        verify(times, getRequestedFor(urlEqualTo(path)));
    }

    public void verifyPostRequestFor(final String path, final int times) {
        verify(times, postRequestedFor(urlEqualTo(path)));
    }
}
