package lucioggm.ecomerce.serviceImpl;

import lucioggm.ecomerce.model.Category;
import lucioggm.ecomerce.repository.CategoryRepository;
import lucioggm.ecomerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Boolean existCategory(String name) {
        return categoryRepository.existsByName(name);
    }


    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

}
