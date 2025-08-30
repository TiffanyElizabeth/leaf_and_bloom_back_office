package final_project.leaf_and_bloom_back_office.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping
public class HomeController {
    @GetMapping
    public String home(Model model) {
        return "home";
    }

}
