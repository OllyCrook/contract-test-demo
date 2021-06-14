package nl.crook.olly.contract.demo.consumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.crook.olly.contract.demo.provider.api.ProviderApiClient;
import nl.crook.olly.contract.demo.provider.domain.ConsumerDetails;
import nl.crook.olly.contract.demo.provider.domain.Products;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderApiClient providerApiClient;

    @Cacheable(value = "getProducts")
    public Products getProducts(final String productCategory) {
        final ConsumerDetails consumerDetails = new ConsumerDetails();
        consumerDetails.setConsumerId("Consumer app");
        return this.providerApiClient.getProducts(productCategory, consumerDetails).getBody();
    }
}
