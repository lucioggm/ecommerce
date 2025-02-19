package lucioggm.ecomerce.controller;

import jakarta.servlet.http.HttpSession;
import lucioggm.ecomerce.model.Category;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    ;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    ;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    ;

    @GetMapping("/products")
    public String products() {
        return "product";
    }

    @GetMapping("/product")
    public String product() {
        return "/view_product";
    }


}
