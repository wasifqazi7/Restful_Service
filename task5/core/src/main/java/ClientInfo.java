package service.core;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Class that contains client information.
 */
public class ClientInfo implements Serializable {
    public static final String MALE = "M";
    public static final String FEMALE = "F";

    private String name;
    private String gender;  // changed to String for better compatibility with Jackson
    private int age;
    private double height;
    private double weight;
    private boolean smoker;
    private boolean medicalIssues;

    // Default constructor for Jackson
    public ClientInfo() {}

    // Constructor for creating ClientInfo object
    @JsonCreator
    public ClientInfo(@JsonProperty("name") String name,
                      @JsonProperty("gender") String gender,
                      @JsonProperty("age") int age,
                      @JsonProperty("height") double height,
                      @JsonProperty("weight") double weight,
                      @JsonProperty("smoker") boolean smoker,
                      @JsonProperty("medicalIssues") boolean medicalIssues) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.smoker = smoker;
        this.medicalIssues = medicalIssues;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public boolean isSmoker() { return smoker; }
    public void setSmoker(boolean smoker) { this.smoker = smoker; }

    public boolean isMedicalIssues() { return medicalIssues; }
    public void setMedicalIssues(boolean medicalIssues) { this.medicalIssues = medicalIssues; }
}
