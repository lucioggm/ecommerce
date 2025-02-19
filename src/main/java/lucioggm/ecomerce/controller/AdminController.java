package lucioggm.ecomerce.controller;

import jakarta.servlet.http.HttpSession;
import lucioggm.ecomerce.model.Category;
import lucioggm.ecomerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String index() {
        return "/admin/index";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct() {
        return "/admin/add_product";
    }

    @GetMapping("category")
    public String category() {
        return "/admin/category";
    }


    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, HttpSession session) {
        Boolean existCategory = categoryService.existCategory(category.getName());
        if (existCategory) {
            session.setAttribute("errMsg", "Category Name Already Exist");
        } else {
            Category saveCategory = categoryService.saveCategory(category);

            if (ObjectUtils.isEmpty(saveCategory)) {
                session.setAttribute("errMsg", "Not Saved! Internal Server Error");
            } else {
                session.setAttribute("succMsg", "Saved Successfully");

            }

        }

        categoryService.saveCategory(category);
        return "redirect:/category";
    }
}
