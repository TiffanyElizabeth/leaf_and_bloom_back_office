package final_project.leaf_and_bloom_back_office.exception;

// custom exception

public class TeaNotFoundException extends RuntimeException {
    public TeaNotFoundException(Integer id) {
        super("Tea with ID " + id + "was not found");
    }

}
