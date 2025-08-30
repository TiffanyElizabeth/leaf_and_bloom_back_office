package final_project.leaf_and_bloom_back_office.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import final_project.leaf_and_bloom_back_office.model.Tea;
import final_project.leaf_and_bloom_back_office.service.CategoryService;
import final_project.leaf_and_bloom_back_office.service.TeaService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/teas")
public class TeaController {

    @Autowired
    private TeaService teaService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(@RequestParam(required = false) Integer categoryId, Model model) {
        List<Tea> teas;

        if (categoryId != null) {
            teas = teaService.findByCategoryId(categoryId);
        } else {
            teas = teaService.findAll();
        }

        model.addAttribute("teas", teas);
        model.addAttribute("hasTeas", !teas.isEmpty());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selectedCategoryId", categoryId);

        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("tea", teaService.getById(id));
        return "detail";
    }

    @GetMapping("searchByName")
    public String searchByName(@RequestParam("name") String name, Model model) {
        List<Tea> teas = teaService.findByName(name);
        model.addAttribute("teas", teas);
        model.addAttribute("hasTeas", !teas.isEmpty());
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("tea", new Tea());
        return "create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("tea") Tea formTea, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        teaService.create(formTea);
        return "redirect:/teas";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("tea", teaService.getById(id));
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("tea") Tea formTea,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }

        teaService.update(formTea);
        return "redirect:/teas";
    }

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        teaService.deleteById(id);
        return "redirect:/teas";
    }

}
