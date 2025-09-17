package final_project.leaf_and_bloom_back_office.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import final_project.leaf_and_bloom_back_office.exception.TeaNotFoundException;
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

    @GetMapping
    public String index(@RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String error,
            Model model) {

        boolean hasName = name != null && !name.trim().isEmpty();
        boolean hasCategory = categoryId != null;

        List<Tea> teas;

        if (hasName) {
            // global search by name (ignores categoryId)
            teas = teaService.findByName(name.trim());
            model.addAttribute("scope", "all");
        } else if (hasCategory) {
            // no search term => show teas for the given category
            teas = teaService.findByCategoryId(categoryId);
            model.addAttribute("scope", "category");
        } else {
            // no filters => show all teas
            teas = teaService.findAll();
            model.addAttribute("scope", "all");
        }

        model.addAttribute("teas", teas);
        model.addAttribute("hasTeas", !teas.isEmpty());
        model.addAttribute("searchTerm", hasName ? name.trim() : null);
        model.addAttribute("categoryId", categoryId);

        // fallback to infer category name from the first tea when showing a category
        if (hasCategory) {
            String categoryName = null;
            if (!teas.isEmpty() && teas.get(0).getCategory() != null) {
                categoryName = teas.get(0).getCategory().getName();
            }
            model.addAttribute("categoryName", categoryName);
        }

        // return error message if tea not found
        if ("notfound".equals(error)) {
            model.addAttribute("errorMessage", "Tea not found!");
        }

        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("tea", teaService.getById(id));
            return "detail";
        } catch (TeaNotFoundException exception) {
            return "redirect:/teas?error=notfound";
        }
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
        try {
            model.addAttribute("tea", teaService.getById(id));
            return "edit";
        } catch (TeaNotFoundException exception) {
            return "redirect:/teas?error=notfound";
        }
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
        try {
            teaService.deleteById(id);
            return "redirect:/teas";
        } catch (TeaNotFoundException exception) {
            return "redirect:/teas?error=notfound";
        }
    }

}
