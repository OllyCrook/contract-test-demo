package nl.crook.olly.contract.demo.provider.contract.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import nl.crook.olly.contract.demo.provider.contract.utils.ContractTestInitialiser;
import nl.crook.olly.contract.demo.provider.database.ProductDetail;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.cloud.contract.verifier.assertion.SpringCloudContractAssertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)  // random port
@ActiveProfiles("contract-tests")
@DirtiesContext
public abstract class AbstractContractBase {

    private WireMockServer wireMockServer;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    ContractTestInitialiser functionalContractInitialiser;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        final Collection<Filter> filterCollection = this.webApplicationContext.getBeansOfType(Filter.class).values();
        final Filter[] filters = filterCollection.toArray(new Filter[filterCollection.size()]);

        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilters(filters)
                .build());

        this.wireMockServer = new WireMockServer(WireMockSpring.options().port(0)); // random port
        this.wireMockServer.start();

        // setup Mongo database
        this.mongoTemplate.dropCollection("local");
        this.mongoTemplate.createCollection("local");
        setupDatabaseRecords();

        // verify that there were no unmatched Wiremock requests
        final List<LoggedRequest> allUnmatchedRequests = this.wireMockServer.findAllUnmatchedRequests();
        assertThat(allUnmatchedRequests.size()).isEqualTo(0);

        WireMock.resetAllRequests();
    }

    /**
     * An example custom conmand for verifying a json object does NOT contain the specified element
     *
     * @param jsonBody the json body to verify
     * @param bodyelement the field that you want ot verify.
     */
    public void elementNotPresent(final Map<String, Object> jsonBody, final String bodyelement) {
        assertFalse(jsonBody.containsKey(bodyelement));
    }

    /**
     * An example custom conmand for verifying a json object does NOT contain the specified element
     *
     * @param jsonBody the json body to verify
     * @param bodyelement the field that you want ot verify.
     */
    public void generateRandomPrice(final Map<String, Object> jsonBody, final String bodyelement) {
        assertFalse(jsonBody.containsKey(bodyelement));
    }

    /**
     * This method is called from groovy contract files to verify if GET calls have been made (or not made) to wiremock services
     */
    public void verifyGetRequestFor(final String path, final int times) {
        verify(times, getRequestedFor(urlEqualTo(path)));
    }

    /**
     * This method is called from groovy contract files to verify if POST calls have been made (or not made) to wiremock services
     */
    public void verifyPostRequestedFor(final String path, final int times) {
        verify(times, postRequestedFor(urlEqualTo(path)));
    }

    private ProductDetail getDatabaseFile(final String path, final String fileName) {
        try {
            final File databaseFile = new File(getClass().getClassLoader().getResource(path + "/" + fileName + ".json").getFile());
            final JsonNode jsonNode = this.objectMapper.readValue(databaseFile, JsonNode.class);
            final ProductDetail dbRecord = new ProductDetail();
            dbRecord.setProductCategory(jsonNode.get("productCategory").asText());
            dbRecord.setProductCode(jsonNode.get("productCode").asText());
            dbRecord.setProductName(jsonNode.get("productName").asText());
            dbRecord.setProductDescription(jsonNode.get("productDescription").asText());
            dbRecord.setProductPrice(new BigDecimal(jsonNode.get("productPrice").asText()));
            dbRecord.setCreatedAt("2020-01-01T01:00:00.00Z");
            return dbRecord;

        } catch (final IOException e) {
            fail("Could not reed database file endpoint: " + path + " fileName" + fileName);
            return null;
        }
    }

    /**
     * Setup the in-memory flapdoodle mongodb database
     */
    public void setupDatabaseRecords() {
        final List<ProductDetail> dbRecords = new ArrayList<>();
        dbRecords.add(getDatabaseFile("database/", "ICE-strawberry"));
        dbRecords.add(getDatabaseFile("database/", "ICE-chocolate"));

        dbRecords.add(getDatabaseFile("database/", "ICEMONGO-strawberry"));
        dbRecords.add(getDatabaseFile("database/", "ICEMONGO-chocolate"));

        dbRecords.add(getDatabaseFile("database/", "ICEMONGO2-chocolate"));
        dbRecords.add(getDatabaseFile("database/", "ICEMONGO3-chocolate"));
        dbRecords.add(getDatabaseFile("database/", "ICEMONGO4-chocolate"));
        dbRecords.add(getDatabaseFile("database/", "ICEMONGO5-chocolate"));
        dbRecords.add(getDatabaseFile("database/", "ICEMONGO6-chocolate"));

        this.mongoTemplate.insertAll(dbRecords);
    }
}