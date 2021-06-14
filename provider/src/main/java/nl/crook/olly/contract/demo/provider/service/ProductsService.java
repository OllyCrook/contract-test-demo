package nl.crook.olly.contract.demo.provider.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.crook.olly.contract.demo.provider.database.ProductDetail;
import nl.crook.olly.contract.demo.provider.database.ProductRepository;
import nl.crook.olly.contract.demo.provider.domain.Product;
import nl.crook.olly.contract.demo.provider.domain.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsService {

    @Autowired
    private final ProductRepository productRepository;

    /**
     * Get a list of products and their prices.  Note - this does NOT return productDescription
     * @param productCategory
     * @return list of products, or empty Products object if no matching products in the DB
     */
    public Products getProducts(final String productCategory) {
        final List<ProductDetail> productDetails = this.productRepository.findByProductCategory(productCategory);

        Products products = new Products();
        for (ProductDetail productDetail : productDetails) {
            Product product = new Product();
            product.setCategory(productDetail.getProductCategory());
            product.setCode(productDetail.getProductCode());
            product.setName(productDetail.getProductName());
            product.setPrice(productDetail.getProductPrice().toString());
            products.addProductsItem(product);
        }
        return products;
    }

    public Product getProductDetails(final String productCode) {
        final ProductDetail productDetail = this.productRepository.findByProductCode(productCode);

        Product product = new Product();
        product.setCategory(productDetail.getProductCategory());
        product.setCode(productDetail.getProductCode());
        product.setName(productDetail.getProductName());
        product.setDescription(productDetail.getProductDescription());
        product.setPrice(productDetail.getProductPrice().toString());

        return product;
    }


}
