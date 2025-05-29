package lab;

import java.util.List;
import java.util.Objects;

public class Client {
    private String id;
    private String name;
    private String email;

    public Client() {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Client(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) &&
                Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        int result = (id == null) ? 0 : id.hashCode();
        result = 31 * result + (name == null ? 0 : name.hashCode());
        result = 31 * result + (email == null ? 0 : email.hashCode());
        return result;
    }

}
