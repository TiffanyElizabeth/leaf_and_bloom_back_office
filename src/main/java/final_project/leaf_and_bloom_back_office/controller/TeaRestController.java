package final_project.leaf_and_bloom_back_office.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import final_project.leaf_and_bloom_back_office.model.Tea;
import final_project.leaf_and_bloom_back_office.service.TeaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/teas")
public class TeaRestController {
    @Autowired
    private TeaService teaService;

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
            return new ResponseEntity<Tea>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<Tea>(teaAttempt.get(), HttpStatus.OK);
    }

}
