package maids.springboot.library.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import maids.springboot.library.base.BaseEntity;

@Entity
public class Patron  extends BaseEntity<Long> {


    @NotBlank(message = "Title is Mandatory")
    private String name;

    @NotBlank(message= "Email is Mandatory")
    @Email
    private String email;

    @NotBlank(message = "Phone is Mandatory")
    private String phone;

    public Patron() {
    }

    public Patron(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public Patron setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Patron setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Patron setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
