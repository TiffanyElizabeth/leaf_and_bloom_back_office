package final_project.leaf_and_bloom_back_office.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import final_project.leaf_and_bloom_back_office.model.Tea;
import final_project.leaf_and_bloom_back_office.service.TeaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class TeaController {
    @Autowired
    private TeaService teaService;

    @GetMapping
    public String homeIndex(Model model) {
        List<Tea> teas = teaService.findAll();
        model.addAttribute("teas", teas);
        model.addAttribute("hasTeas", !teas.isEmpty());
        return "home_index";
    }

    @GetMapping("/teas/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("tea", teaService.getById(id));
        return "tea_detail";
    }

    @GetMapping("teas/searchByName")
    public String searchByName(@RequestParam("name") String name, Model model) {
        List<Tea> teas = teaService.findByName(name);
        model.addAttribute("teas", teas);
        model.addAttribute("hasTeas", !teas.isEmpty());
        return "home_index";
    }

}
