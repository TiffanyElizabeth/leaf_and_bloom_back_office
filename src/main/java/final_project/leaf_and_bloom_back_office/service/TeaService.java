package final_project.leaf_and_bloom_back_office.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import final_project.leaf_and_bloom_back_office.exception.TeaNotFoundException;
import final_project.leaf_and_bloom_back_office.model.Tea;
import final_project.leaf_and_bloom_back_office.repository.TeaRepository;

@Service
public class TeaService {
    @Autowired
    private TeaRepository teaRepository;

    // return all teas
    public List<Tea> findAll() {
        return teaRepository.findAll();
    }

    // return optional - if it is empty, the caller decides what to do - i don't
    // always have to handle the exception (depends on the caller)
    public Optional<Tea> findById(Integer id) {
        return teaRepository.findById(id);
    }

    // returns a tea or throws an exception
    public Tea getById(Integer id) {
        Optional<Tea> teaOptional = teaRepository.findById(id);
        if (teaOptional.isPresent()) {
            return teaOptional.get();
        } else {
            throw new TeaNotFoundException(id);
        }
    }

    // return teas by name
    public List<Tea> findByName(String name) {
        return teaRepository.findByNameContainingIgnoreCase(name);
    }

    // return teas by category
    public List<Tea> findByCategoryId(Integer categoryId) {
        return teaRepository.findByCategoryId(categoryId);
    }

    // add a new tea
    public Tea create(Tea tea) {
        return teaRepository.save(tea);
    }

    // update an exiting tea
    public Tea update(Tea tea) {
        return teaRepository.save(tea);
    }

    // delete a tea, throwing exception if the tea isn't found thanks to getById
    public void deleteById(Integer id) {
        Tea tea = getById(id);
        teaRepository.delete(tea);
    }
}
