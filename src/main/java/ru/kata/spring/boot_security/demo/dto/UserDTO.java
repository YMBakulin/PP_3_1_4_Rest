package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

public class UserDTO {
    private Long id;

    @NotEmpty(message = "Username is required")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String username;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Name is required")
    @Size(min = 2, max = 30, message = "fullname should be between 2 and 30 characters")
    private String fullname;

    @Min(value = 12, message = "Age should be greater than 12")
    private byte age;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return age == userDTO.age && id.equals(userDTO.id) && username.equals(userDTO.username) && password.equals(userDTO.password) && fullname.equals(userDTO.fullname) && Objects.equals(email, userDTO.email) && roles.equals(userDTO.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, fullname, age, email, roles);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullname='" + fullname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
