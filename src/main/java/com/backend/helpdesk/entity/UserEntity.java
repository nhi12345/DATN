package com.backend.helpdesk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "users")
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id = 0;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;

    private String lastName;

    private int age;

    private Date birthday;

    private boolean gender;

    private String address;

    private Date startingDay;

    @Column(nullable = false)
    private boolean enable = false;

    @Lob
    @Column(name = "avatar")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roleEntities;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects;

    @OneToMany(mappedBy = "userEntity")
    private List<Comment> comments;

    @OneToMany(mappedBy = "userCreate")
    private List<Card> cards;

    public UserEntity() {
    }

    public UserEntity(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartingDay() {
        return startingDay;
    }

    public void setStartingDay(Date startingDay) {
        this.startingDay = startingDay;
    }

    public Set<RoleEntity> getRoleEntities() {
        return roleEntities;
    }

    public void setRoleEntities(Set<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
