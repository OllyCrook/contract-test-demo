package contracts.contractUsingMockBeans

import org.springframework.cloud.contract.spec.Contract

// Because the pom specifies packageWithBaseClasses, the base class is based on the name of directory containing this groovy file
// This test uses base class ContractUsingMockBeansBase.java, which sets up MockBeans to simulate various Java service methods
// See README.md for more details
Contract.make {
    description "Contract using mock bean in the controller, for category MOCKED_CONTRACT_GROOVY"
    name "Groovy contract"

    request {
        method POST()
        url '/provider/products/MOCKED_CONTRACT_GROOVY'
        headers {
            contentType(applicationJson())
        }
        body(file("request/getProducts_MOCKED_CONTRACT_GROOVY.json"))
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body(file("response/getProducts_MOCKED_CONTRACT_GROOVY.json"))
    }
}