package final_project.leaf_and_bloom_back_office.service;

import java.lang.classfile.ClassFile.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import final_project.leaf_and_bloom_back_office.model.Tea;
import final_project.leaf_and_bloom_back_office.repository.CategoryRepository;
import final_project.leaf_and_bloom_back_office.repository.TeaRepository;

@Service
public class TeaService {
    @Autowired
    private TeaRepository teaRepository;

    public List<Tea> findAll() {
        return teaRepository.findAll();
    }

    public Optional<Tea> findById(Integer id) {
        return teaRepository.findById(id);
    }

    public Tea getById(Integer id) {
        return teaRepository.findById(id).get();
    }

    public List<Tea> findByName(String name) {
        return teaRepository.findByNameContainingIgnoreCase(name);
    }

    public Tea create(Tea tea) {
        return teaRepository.save(tea);
    }

    public Tea update(Tea tea) {
        return teaRepository.save(tea);
    }

    public void deleteById(Integer id) {
        Tea tea = getById(id);
        teaRepository.delete(tea);
    }
}
