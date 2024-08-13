package maids.springboot.library.response;

public class RegisterResponse {

    private Long id;

    private String fullName;

    private String email;

    public Long getId() {
        return id;
    }

    public RegisterResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public RegisterResponse setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterResponse setEmail(String email) {
        this.email = email;
        return this;
    }
}

