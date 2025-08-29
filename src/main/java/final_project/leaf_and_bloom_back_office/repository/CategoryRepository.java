package final_project.leaf_and_bloom_back_office.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import final_project.leaf_and_bloom_back_office.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
