package nl.crook.olly.contract.demo.provider.contract.base;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import nl.crook.olly.contract.demo.provider.domain.Product;
import nl.crook.olly.contract.demo.provider.domain.Products;
import nl.crook.olly.contract.demo.provider.service.ProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)  // random port
@DirtiesContext
@AutoConfigureStubRunner
public class ContractUsingMockBeansBase {

    @MockBean
    public ProductsService productsService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .build());

        setupMockExpectations("MOCKED_CONTRACT_GROOVY");
        setupMockExpectations("MOCKED_CONTRACT_JAVA");
        setupMockExpectations("MOCKED_CONTRACT_YAML");
    }

    private void setupMockExpectations(final String category) {
        Products products = new Products();
        Product product = new Product();
        product.setCategory(category);
        product.setName("Tasty mock surprise");
        product.setCode("CONTRACT-MOCK");
        product.setPrice("23");
        product.setDescription("This is the product description. It should not have been filled by the mock bean");
        products.addProductsItem(product);
        when(this.productsService.getProducts(category)).thenReturn(products);
    }

}
