package nl.crook.olly.contract.demo.consumer.contract.apitest;

import lombok.extern.slf4j.Slf4j;
import nl.crook.olly.contract.demo.provider.api.ProviderApiClient;
import nl.crook.olly.contract.demo.provider.domain.ConsumerDetails;
import nl.crook.olly.contract.demo.provider.domain.Product;
import nl.crook.olly.contract.demo.provider.domain.Products;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Change StubRunnerProperties.StubsMode to REMOTE if you want to use Nexus instead of your local Maven repo
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ActiveProfiles("api-tests")
@SpringBootTest
@DirtiesContext
@AutoConfigureStubRunner(ids = {"nl.crook.olly.contract.demo.provider:provider:+:stubs:8091"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        repositoryRoot = "https://maven.dummy/nexus/content/repositories/release"
)
public class ProviderApiContractTest {

    private ConsumerDetails consumerDetails;

    @Autowired
    private ProviderApiClient providerApiClient;

    @BeforeEach
    public void setup() {
        this.consumerDetails = new ConsumerDetails();
        consumerDetails.setConsumerId("Api Test from Consumer");
    }

    @Test
    @DisplayName("Call getProducts in provider-service, for category ICEMONGO, using groovy contracts and MongoDB in the provider service ")
    public void getProducts_ICEMONGO() {

        // do test
        final ResponseEntity<Products> response = this.providerApiClient.getProducts("ICEMONGO", this.consumerDetails);

        // verify results
        assertEquals(200, response.getStatusCodeValue());

        final List<Product> products = response.getBody().getProducts();
        assertEquals(2, products.size());

        final Product product0 = products.get(0);
        assertEquals("ICEMONGO", product0.getCategory());
        assertEquals("CONTRACT-STRAWBERRY", product0.getCode());
        assertEquals("Strawberry ice cream", product0.getName());
        assertEquals("23", product0.getPrice());
        assertNull(product0.getDescription());  // We don't really need this null check in the test. I added it for demonstration purposes only

        final Product product1 = products.get(1);
        assertEquals("ICEMONGO", product1.getCategory());
        assertEquals("CONTRACT-CHOCOLATE", product1.getCode());
        assertEquals("Chocolate ice cream", product1.getName());
        assertEquals("99", product1.getPrice());
        assertNull(product1.getDescription());  // We don't really need this null check in the test. I added it for demonstration purposes only
    }

    @Test
    @DisplayName("Call getProducts in provider-service, for category ICEMOCK, using ContractTestUsingMockBeans.java and mock beans in the provder service")
    public void getProducts_MOCK_CONTRACT_GROOVY() {

        // do test
        final ResponseEntity<Products> response = this.providerApiClient.getProducts("MOCKED_CONTRACT_GROOVY", this.consumerDetails);

        // verify results
        assertEquals(200, response.getStatusCodeValue());

        final List<Product> products = response.getBody().getProducts();
        assertEquals(1, products.size());

        final Product product0 = products.get(0);
        assertEquals("MOCKED_CONTRACT_GROOVY", product0.getCategory());
        assertEquals("CONTRACT-MOCK", product0.getCode());
        assertEquals("Tasty mock surprise", product0.getName());
        // assertEquals("This is the product description. It should not have been filled by the mock bean", product0.getDescription());
        assertEquals("23", product0.getPrice());
    }

    @Test
    @DisplayName("Call getProducts in provider-service, using the Java contract in provider service")
    public void getProducts_MOCK_CONTRACT_JAVA() {

        // do test
        final ResponseEntity<Products> response = this.providerApiClient.getProducts("MOCKED_CONTRACT_JAVA", this.consumerDetails);

        // verify results
        assertEquals(200, response.getStatusCodeValue());

        final List<Product> products = response.getBody().getProducts();
        assertEquals(1, products.size());

        final Product product0 = products.get(0);
        assertEquals("MOCKED_CONTRACT_JAVA", product0.getCategory());
        assertEquals("CONTRACT-MOCK", product0.getCode());
        assertEquals("Tasty mock surprise", product0.getName());
        assertEquals("This is the product description. It should not have been filled by the mock bean", product0.getDescription());
        assertEquals("23", product0.getPrice());
    }
}
