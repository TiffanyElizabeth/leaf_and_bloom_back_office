package final_project.leaf_and_bloom_back_office.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category {

    // attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Please specify a name for the category")
    @Size(max = 50, message = "The category name cannot have more than 50 characters")
    private String name;

    @Size(max = 200, message = "The specified url must have no longer than 200 characters")
    private String photoUrl;

    // relationships
    @JsonBackReference
    @OneToMany(mappedBy = "category")
    private List<Tea> teas;

    // empty constructor
    public Category() {
    }

    // constructor

    public Category(String name, String photoUrl) {
        this.name = name;
        this.photoUrl = photoUrl;
    }

    // getters and setters

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<Tea> getTeas() {
        return this.teas;
    }

    public void setTeas(List<Tea> teas) {
        this.teas = teas;
    }

    // toString
    @Override
    public String toString() {
        return String.format("%s", this.name);
    }

}
