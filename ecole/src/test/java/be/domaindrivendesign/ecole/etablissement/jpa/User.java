package be.domaindrivendesign.ecole.etablissement.jpa;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class User extends be.domaindrivendesign.kernel.common.model.Entity {
    String name;
    String email;

    public static User create(String name, String email) {
        User u = new User();
        u.setId(UUID.randomUUID());
        u.setName(name);
        u.setEmail(email);
        return u;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
