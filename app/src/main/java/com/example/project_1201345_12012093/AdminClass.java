package com.example.project_1201345_12012093;

public class AdminClass {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String hashedPassword;

    private String gender;
    private String role;




    public AdminClass(){

    }

    public AdminClass(String firstName, String lastName, String email, String phone, String hashedPassword, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.hashedPassword = hashedPassword;
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Constructor
    public AdminClass(String email, String phone, String firstName, String lastName, String gender, String password, String role) {
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.hashedPassword = password;
        this.role = role;
    }


    // Getters and Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", gender='" + gender+'}';
    }

}
