package edu.usc.haowu.codetestforpheramor.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Hao Wu
 * @time 2018/06/20
 * @since
 */
public class User extends BaseModel implements Serializable{
    private String userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int zipcode;
    private int height;
    /**
     * 0. not tell
     * 1. male
     * 2. female
     * 3. both
     */
    private String gender;
    private int age;
    private String interestedGender;
    private int interestedMinAge;
    private int interestedMaxAge;
    private String race;
    private String religions;
    private String dob;
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInterestedGender() {
        return interestedGender;
    }

    public void setInterestedGender(String interestedGender) {
        this.interestedGender = interestedGender;
    }

    public int getInterestedMinAge() {
        return interestedMinAge;
    }

    public void setInterestedMinAge(int interestedMinAge) {
        this.interestedMinAge = interestedMinAge;
    }

    public int getInterestedMaxAge() {
        return interestedMaxAge;
    }

    public void setInterestedMaxAge(int interestedMaxAge) {
        this.interestedMaxAge = interestedMaxAge;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getReligions() {
        return religions;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setReligions(String religions) {
        this.religions = religions;
    }
}
