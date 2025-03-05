package lucioggm.ecomerce.service;

import lucioggm.ecomerce.model.Product;

import java.util.List;

public interface ProductService {

    public Product saveProduct (Product product);

    public List<Product> getAllProducts();

    public Boolean deleteProduct(int id);
}
