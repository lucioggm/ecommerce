package lucioggm.ecomerce.serviceImpl;

import lucioggm.ecomerce.model.Product;
import lucioggm.ecomerce.repository.ProductRepository;
import lucioggm.ecomerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Boolean deleteProduct(int id) {
       Product product = productRepository.findById(id).orElse(null);
       if(!ObjectUtils.isEmpty(product))
       {
            productRepository.delete(product);
            return true;
       }
        return false;
    }

}
