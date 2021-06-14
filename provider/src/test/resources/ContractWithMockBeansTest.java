package contracts.contractUsingMockBeans;

import nl.crook.olly.contract.demo.provider.contract.base.ContractUsingMockBeansBase;
import nl.crook.olly.contract.demo.provider.domain.Product;
import nl.crook.olly.contract.demo.provider.domain.Products;
import org.springframework.cloud.contract.spec.Contract;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class ContractWithMockBeansTest extends ContractUsingMockBeansBase implements Supplier<Collection<Contract>> {

    @Override
    public Collection<Contract> get() {
        return Collections.singletonList(Contract.make(contract -> {
            contract.request(request -> {
                request.url("/provider/products/ICEOLLY");
                request.method(request.GET());
            });
            contract.response(response -> {
                response.status(response.OK());
                Products products = new Products();
                Product product = new Product();
                product.setName("My icecream");
                products.addProductsItem(product);
                response.body(products);
            });
        }));
    }

//    @Test
//    public void validate_getProducts_contract_test () throws Exception {
//        setupMockBean("ICEMOCK");
//
//        // given:
//        MockMvcRequestSpecification request = given();
//
//        // when:
//        ResponseOptions response = given().spec(request)
//                .get("/provider/products/ICEMOCK");
//
//        // then:
//        assertThat(response.statusCode()).isEqualTo(200);
//        assertThat(response.header("Content-Type")).matches("application/json.*");
//
//        // and:
//        DocumentContext parsedJson = JsonPath.parse(response.getBody().asString());
//        assertThatJson(parsedJson).array("['products']").contains("['name']").isEqualTo("Tasty surprise");
//   }

//    private void setupMockBean(final String category) {
//        Products products = new Products();
//        Product product = new Product();
//        product.setName("Tasty surprise");
//        products.addProductsItem(product);
//        when(this.productsService.getProducts(category)).thenReturn(products);
//    }
}
