package com.innprojects.gymapp.javaModels;

/**
 * Created by simransarin on 23/10/17.
 */

public class Stat {

    String weight;
    String impedance;
    String BMI;
    String body_fat;
    String muscle;
    String water;
    String protein;
    String bone_mass;
    String BMR;
    String visceral_fat;
    String fitness_age;

    public Stat(String weight, String impedance, String BMI, String body_fat, String muscle, String water, String protein, String bone_mass, String BMR, String visceral_fat, String fitness_age) {
        this.weight = weight;
        this.impedance = impedance;
        this.BMI = BMI;
        this.body_fat = body_fat;
        this.muscle = muscle;
        this.water = water;
        this.protein = protein;
        this.bone_mass = bone_mass;
        this.BMR = BMR;
        this.visceral_fat = visceral_fat;
        this.fitness_age = fitness_age;
    }

    public String getFitness_age() {
        return fitness_age;
    }

    public void setFitness_age(String fitness_age) {
        this.fitness_age = fitness_age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImpedance() {
        return impedance;
    }

    public void setImpedance(String impedance) {
        this.impedance = impedance;
    }

    public String getBMI() {
        return BMI;
    }

    public void setBMI(String BMI) {
        this.BMI = BMI;
    }

    public String getBody_fat() {
        return body_fat;
    }

    public void setBody_fat(String body_fat) {
        this.body_fat = body_fat;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getBone_mass() {
        return bone_mass;
    }

    public void setBone_mass(String bone_mass) {
        this.bone_mass = bone_mass;
    }

    public String getBMR() {
        return BMR;
    }

    public void setBMR(String BMR) {
        this.BMR = BMR;
    }

    public String getVisceral_fat() {
        return visceral_fat;
    }

    public void setVisceral_fat(String visceral_fat) {
        this.visceral_fat = visceral_fat;
    }
}
