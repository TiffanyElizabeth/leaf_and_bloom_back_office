package final_project.leaf_and_bloom_back_office.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "teas")
public class Tea {

    // attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Please specify a name for the tea")
    @Size(max = 50, message = "The tea's name cannot have more than 50 characters")
    private String name;

    @NotBlank(message = "Please include a short description of the tea")
    @Size(max = 500, message = "The tea's description cannot have more than 500 characters")
    private String description;

    @NotNull(message = "Please specify if the tea is caffeinated or not")
    private Boolean caffeinated;

    @Size(max = 200, message = "The specified url must have no more than 200 characters")
    private String photoUrl;

    @NotNull(message = "Please specify the current number of items in stock.")
    @PositiveOrZero(message = "There cannot be a negative number of items in stock")
    private Integer stock;

    @NotNull(message = "Please specify the price.")
    @PositiveOrZero(message = "The price cannot be a negative number.")
    private Double price;

    // relationships
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // empty constructor for JPA
    public Tea() {
    }

    // constructor - why?

    public Tea(String name, String description, Boolean caffeinated, String photoUrl, Integer stock,
            Double price, Category category) {
        this.name = name;
        this.description = description;
        this.caffeinated = caffeinated;
        this.photoUrl = photoUrl;
        this.stock = stock;
        this.price = price;
        this.category = category;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCaffeinated() {
        return this.caffeinated;
    }

    public void setCaffeinated(Boolean caffeinated) {
        this.caffeinated = caffeinated;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // toString
    @Override
    public String toString() {
        return String.format("%s, €%.2f", this.name, this.price);
    }
}
