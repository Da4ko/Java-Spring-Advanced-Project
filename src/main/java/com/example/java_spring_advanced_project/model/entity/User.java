package com.example.java_spring_advanced_project.model.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String username;
    private String email;
    private String password;
    private List<AudiCar> listOfAudiCars;
    private List<BmwCar> listOfBmwCars;
    private List<MercedesCar> listOfMercedesCars;
    private List<PorscheCar> listOfPorscheCars;
    private List<UserRoleEntity> roles;
    private boolean active;

    public User() {
    }
    @Column(nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<AudiCar> getListOfAudiCars() {
        return listOfAudiCars;
    }

    public void setListOfAudiCars(List<AudiCar> listOfAudiCars) {
        this.listOfAudiCars = listOfAudiCars;
    }
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<BmwCar> getListOfBmwCars() {
        return listOfBmwCars;
    }

    public void setListOfBmwCars(List<BmwCar> listOfBmwCars) {
        this.listOfBmwCars = listOfBmwCars;
    }
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<MercedesCar> getListOfMercedesCars() {
        return listOfMercedesCars;
    }

    public void setListOfMercedesCars(List<MercedesCar> listOfMercedesCars) {
        this.listOfMercedesCars = listOfMercedesCars;
    }
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<PorscheCar> getListOfPorscheCars() {
        return listOfPorscheCars;
    }

    public void setListOfPorscheCars(List<PorscheCar> listOfPorscheCars) {
        this.listOfPorscheCars = listOfPorscheCars;
    }
    @ManyToMany(fetch = FetchType.EAGER)
    public List<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleEntity> roles) {
        this.roles = roles;
    }
    @Column(nullable = false)
    public boolean isActive() {
        return active;
    }

    public User setActive(boolean active) {
        this.active = active;
        return this;
    }
}