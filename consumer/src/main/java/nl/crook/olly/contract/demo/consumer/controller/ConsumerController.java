package nl.crook.olly.contract.demo.consumer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.crook.olly.contract.demo.consumer.api.ConsumerApi;
import nl.crook.olly.contract.demo.consumer.domain.ProductPrice;
import nl.crook.olly.contract.demo.consumer.domain.ProductPrices;
import nl.crook.olly.contract.demo.consumer.service.MarketingService;
import nl.crook.olly.contract.demo.consumer.service.ProviderService;
import nl.crook.olly.contract.demo.marketing.domain.PromotionDetails;
import nl.crook.olly.contract.demo.provider.domain.Product;
import nl.crook.olly.contract.demo.provider.domain.Products;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ConsumerController implements ConsumerApi {

    private final ProviderService providerService;
    private final MarketingService marketingService;

    @Override
    public ResponseEntity<ProductPrices> getProductList(final String productCategory) {
        final Products products = providerService.getProducts(productCategory);
        final PromotionDetails promotionDetails = marketingService.getPromotionDetails(productCategory);

        ProductPrices productPrices = new ProductPrices();
        for(Product product: products.getProducts()) {
            ProductPrice productPrice = new ProductPrice();
            productPrice.setCode(product.getCategory());
            productPrice.setId(product.getCode());
            productPrice.setName(product.getName());
            productPrice.setDescription(product.getDescription());
            productPrice.setPrice(product.getPrice());
            productPrices.addProductsItem(productPrice);
        }

        return new ResponseEntity<>(productPrices, HttpStatus.OK);
    }
}
