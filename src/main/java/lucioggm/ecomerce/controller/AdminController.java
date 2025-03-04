package lucioggm.ecomerce.controller;

import jakarta.servlet.http.HttpSession;
import lucioggm.ecomerce.model.Category;
import lucioggm.ecomerce.service.CategoryService;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

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
    public String loadAddProduct(Model m) {

        List<Category> categories = categoryService.getAllCategory();
        categories = categories.stream()
                .filter(Category::getIsActive)
                .collect(Collectors.toList());

        m.addAttribute("categories", categories);
        return "/admin/add_product";

    }

    @GetMapping("category")
    public String category(Model model) {
        model.addAttribute("categorys", categoryService.getAllCategory());
        return "/admin/category";
    }


    @PostMapping("/saveCategory")

    public String saveCategory(@ModelAttribute Category category,
                               @RequestParam("file") MultipartFile file,
                               HttpSession session) {
        try {
            String imageName = (file != null && !file.isEmpty()) ? file.getOriginalFilename() : "default.jpg";
            category.setImageName(imageName);

            if (categoryService.existCategory(category.getName())) {
                session.setAttribute("errorMsg", "Category Name Already Exists");
                return "redirect:/admin/category";
            }
            Category savedCategory = categoryService.saveCategory(category);

            File saveFile = new ClassPathResource("/static/img").getFile();

            Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator + file.getOriginalFilename());


            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            session.setAttribute("succMsg", "Saved Successfully");

        } catch (Exception e) {
            session.setAttribute("errorMsg", "Not Saved! Internal Server Error: " + e.getMessage());
        }

        return "redirect:/admin/category";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") int id, HttpSession session) {

        Boolean deleteCategory = categoryService.deleteCategory(id);
        if (deleteCategory) {
            session.setAttribute("succMsg", "Category deleted succesfully");
        } else {
            session.setAttribute("errorMsg", "something wrong on server");
        }
        return "redirect:/admin/category";

    }

    @GetMapping("/loadEditCategory/{id}")
    public String loadEditCategory(@PathVariable("id") int id, Model m) {
        m.addAttribute("category", categoryService.getCategoryById(id));


        return "/admin/edit_category";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {

        Category oldCategory = categoryService.getCategoryById(category.getId());
        String imageName = !file.isEmpty() ? file.getOriginalFilename() : oldCategory.getImageName();

        if (!ObjectUtils.isEmpty(category)) {
            oldCategory.setName(category.getName());
            oldCategory.setIsActive(category.getIsActive());
            oldCategory.setImageName(imageName);
        }

        Category updatedCategory = categoryService.saveCategory(oldCategory);

        if (!ObjectUtils.isEmpty(updatedCategory)) {

            if (!file.isEmpty()) {
                File saveFile = new ClassPathResource("/static/img").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            }
            session.setAttribute("succMsg", "Category updated successfully");
        } else {
            session.setAttribute("errorMsg", "something wrong on server");
        }


        return "redirect:/admin/loadEditCategory/" + category.getId();


    }
}
