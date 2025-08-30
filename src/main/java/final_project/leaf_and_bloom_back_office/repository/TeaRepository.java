package final_project.leaf_and_bloom_back_office.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import final_project.leaf_and_bloom_back_office.model.Tea;

public interface TeaRepository extends JpaRepository<Tea, Integer> {
    public List<Tea> findByNameContainingIgnoreCase(String name);

    public List<Tea> findByCategoryId(Integer categoryId);

}
