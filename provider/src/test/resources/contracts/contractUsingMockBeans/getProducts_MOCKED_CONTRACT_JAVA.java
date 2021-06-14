package contracts.contractUsingMockBeans;

import org.springframework.cloud.contract.spec.Contract;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class getProducts_contract_using_mockbean implements Supplier<Collection<Contract>> {

    @Override
    public Collection<Contract> get() {
        return Collections.singletonList(Contract.make(contract -> {
            contract.description("Contract written in Java, using mock bean in the controller, for category MOCKED_CONTRACT_JAVA");
            contract.name("Java contract");

            // request
            contract.request(request -> {
                request.url("/provider/products/MOCKED_CONTRACT_JAVA");
                request.method(request.POST());
                request.headers(header -> {
                    header.contentType(header.applicationJson());
                });
                request.body(request.file("request/getProducts_MOCKED_CONTRACT_JAVA.json"));
            });

            //response
            contract.response(response -> {
                response.status(response.OK());
                response.headers(header -> {
                    header.contentType(header.applicationJson());
                });
                response.body(response.file("response/getProducts_MOCKED_CONTRACT_JAVA.json"));
            });
        }));
    }

}
