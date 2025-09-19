package final_project.leaf_and_bloom_back_office.controller;

import java.util.List; // used to return multiple teas/categories
import java.util.Optional; // used when retrieving a single tea by ID (may or may not exist)

import org.springframework.beans.factory.annotation.Autowired; // allows Spring to inject service objects here (dependency injection)
import org.springframework.http.HttpStatus; // http response codes 
import org.springframework.http.ResponseEntity; // allows us to return data + status codes 
import org.springframework.web.bind.annotation.RequestMapping; // sets a base path for all routes in this controller
import org.springframework.web.bind.annotation.RestController; // exposes endpoints returning json responses
import org.springframework.web.bind.annotation.CrossOrigin; // allows requests from different domains (here, to allow React FE access)
import org.springframework.web.bind.annotation.GetMapping; // maps HTTP GET request to a method
import org.springframework.web.bind.annotation.PathVariable; // extracts values from the UR
import org.springframework.web.bind.annotation.RequestParam; // extracts query parameters from the URL

import final_project.leaf_and_bloom_back_office.model.*;
import final_project.leaf_and_bloom_back_office.service.CategoryService;
import final_project.leaf_and_bloom_back_office.service.TeaService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/teas")
public class TeaRestController {
    @Autowired
    private TeaService teaService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Tea> index() {
        List<Tea> teas = teaService.findAll();
        return teas;
    }

    @GetMapping("/search")
    public List<Tea> searchByName(@RequestParam("name") String name) {
        return teaService.findByName(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tea> show(@PathVariable Integer id) {
        Optional<Tea> teaAttempt = teaService.findById(id);
        if (teaAttempt.isEmpty()) {
            return new ResponseEntity<Tea>(HttpStatus.NOT_FOUND); // returns 404 NOT FOUND if the tea doesn't exist

        }

        return new ResponseEntity<Tea>(teaAttempt.get(), HttpStatus.OK); // else returns 200 OK & the tea object
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/categories/{categoryId}")
    public List<Tea> getByCategory(@PathVariable Integer categoryId) {
        return teaService.findByCategoryId(categoryId);
    }

}
