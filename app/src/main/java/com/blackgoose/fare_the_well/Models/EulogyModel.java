package com.blackgoose.fare_the_well.Models;

import java.io.Serializable;
import java.util.List;

public class EulogyModel implements Serializable {
    private List<String> imageUrls;
    private List<ProgramModel> funeralPrograms;
    String early;
    String authorName;
    String burialLocation;
    String dateOfBirth;
    String passingOnDate;

    String firstName;
    String lastName;
    String secondName;
    String education;
    String family;
    String finalMoment;
    String userUid;
    String work;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;
    int authorContact;

    public EulogyModel(){

    }

    public EulogyModel(List<String>imageUrls,List<ProgramModel>funeralPrograms, String early, String authorName, String burialLocation, String dateOfBirth, String passingOnDate, String firstName, String lastName, String secondName, String education, String family, String finalMoment, String userUid, String work, int authorContact) {
        this.imageUrls = imageUrls;
        this.funeralPrograms = funeralPrograms;
        this.early = early;
        this.authorName = authorName;
        this.burialLocation = burialLocation;
        this.dateOfBirth = dateOfBirth;
        this.passingOnDate = passingOnDate;
        this.secondName = secondName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.education = education;
        this.family = family;
        this.finalMoment = finalMoment;
        this.userUid = userUid;
        this.work = work;
        this.authorContact = authorContact;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<ProgramModel> getFuneralPrograms() {
        return funeralPrograms;
    }

    public void setFuneralPrograms(List<ProgramModel> funeralPrograms) {
        this.funeralPrograms = funeralPrograms;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassingOnDate() {
        return passingOnDate;
    }

    public void setPassingOnDate(String passingOnDate) {
        this.passingOnDate = passingOnDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBurialLocation() {
        return burialLocation;
    }

    public void setBurialLocation(String burialLocation) {
        this.burialLocation = burialLocation;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }


    public int getAuthorContact() {
        return authorContact;
    }

    public void setAuthorContact(int authorContact) {
        this.authorContact = authorContact;
    }

    public String getEarly() {
        return early;
    }

    public void setEarly(String early) {
        this.early = early;
    }

    public String getFinalMoment() {
        return finalMoment;
    }

    public void setFinalMoment(String finalMoment) {
        this.finalMoment = finalMoment;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}
