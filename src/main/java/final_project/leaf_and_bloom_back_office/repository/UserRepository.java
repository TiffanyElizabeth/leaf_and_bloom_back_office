package final_project.leaf_and_bloom_back_office.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import final_project.leaf_and_bloom_back_office.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username); // user may not exist hence the optional
}
