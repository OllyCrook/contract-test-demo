package nl.crook.olly.contract.demo.provider.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.crook.olly.contract.demo.provider.api.ProviderApi;
import nl.crook.olly.contract.demo.provider.domain.ConsumerDetails;
import nl.crook.olly.contract.demo.provider.domain.Product;
import nl.crook.olly.contract.demo.provider.domain.Products;
import nl.crook.olly.contract.demo.provider.service.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProviderController implements ProviderApi {

    private final ProductsService productsService;

    @Override
    public ResponseEntity<Product> getProductDetails(final String productCode) {
        // Not implemented yet
        return null;
    }

    @Override
    public ResponseEntity<Products> getProducts(@Pattern(regexp = "^\\w{1,35}$") String productCategory, @Valid ConsumerDetails consumerDetails) {
        log.info("Started getProducts");
        final Products products = productsService.getProducts(productCategory);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
