package final_project.leaf_and_bloom_back_office.controller;

import final_project.leaf_and_bloom_back_office.exception.TeaNotFoundException;
import final_project.leaf_and_bloom_back_office.model.Tea;
import final_project.leaf_and_bloom_back_office.service.TeaService;

import java.util.List; // gives List inferface from Collections framework - used for working with lists of teas returned from TeaService
import org.springframework.stereotype.Controller; // marks class as Spring MVC controller
import org.springframework.ui.Model; // represents the data model passed to the view - used to add attributes that Thymeleaf can then access
import jakarta.validation.Valid; // for validating form input according to restraints outlined in model
import org.springframework.validation.BindingResult; // captures the result of validation and let's us check for errors
import org.springframework.beans.factory.annotation.Autowired; // marks as dependency injection - Spring automatically provides an instance of TeaService for my controller
import org.springframework.web.bind.annotation.GetMapping; // maps HTTP GET requests to a specific controller method 
import org.springframework.web.bind.annotation.RequestMapping; // sets a base path for all routes in this controller 
import org.springframework.web.bind.annotation.ModelAttribute; // binds form data to a JAva object and make sit available in the Controller method
import org.springframework.web.bind.annotation.PathVariable; // extracts values from the URL path
import org.springframework.web.bind.annotation.PostMapping; // maps HTTP POST requests to a controller method
import org.springframework.web.bind.annotation.RequestParam; // extracts query parameters from the URL

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

        List<Tea> teas = teaService.findFiltered(name, categoryId);

        model.addAttribute("teas", teas);
        model.addAttribute("hasTeas", !teas.isEmpty());
        model.addAttribute("searchTerm", name);
        model.addAttribute("categoryId", categoryId);

        // if findFiltered returns an empty teas list, return...
        if (teas.isEmpty()) {
            model.addAttribute("errorMessage", "We apologise. No teas match your search.");
        }

        // return "Tea not found" alert (redirect from show/edit/delete)
        if ("notfound".equals(error)) {
            model.addAttribute("errorMessage", "Tea not found!");
        }
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        try {
            model.addAttribute("tea", teaService.getById(id));
            return "detail";
        } catch (TeaNotFoundException exception) {
            return "redirect:/teas?error=notfound";
        }
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
