package nl.crook.olly.contract.demo.consumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import nl.crook.olly.contract.demo.marketing.api.MarketingApiClient;
import nl.crook.olly.contract.demo.marketing.domain.PromotionDetails;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketingService {

    private final MarketingApiClient marketingApiClient;

    @Cacheable(value = "getPromotionDetails")
    public PromotionDetails getPromotionDetails(final String productCode) {
        return this.marketingApiClient.getPromotionDetails(productCode).getBody();
    }
}
