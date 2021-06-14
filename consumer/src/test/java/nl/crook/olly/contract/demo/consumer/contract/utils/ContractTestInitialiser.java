package nl.crook.olly.contract.demo.consumer.contract.utils;


import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ContractTestInitialiser {

    @Bean(name="consumerFunctionalContractTestClientInterceptor")
    public RequestInterceptor functionalContractTestClientInterceptor() {
        return new ContractTestClientInterceptor();
    }

}

