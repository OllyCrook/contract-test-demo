package nl.crook.olly.contract.demo.provider.database;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<ProductDetail, String> {

    List<ProductDetail> findByProductCategory(final String productCategory);

    ProductDetail findByProductCode(final String productCode);

}
