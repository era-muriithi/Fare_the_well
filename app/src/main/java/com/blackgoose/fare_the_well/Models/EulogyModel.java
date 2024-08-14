//package com.blackgoose.fare_the_well.Models;
//
//public class EulogyModel {
//    String deceasedPicture;
//    String earlylifeBiography;
//    String authorName;
//    String burialLocation;
//    String dateOfBirth;
//    String dateOfDeath;
//    String deceasedFname;
//    String deceasedLname;
//    String deceasedSname;
//    String educationBiography;
//    String familyBiography;
//    String finalMoments;
//    String userUid;
//    String workBiography;
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//
//    String key;
//    int authorContact;
//
//    public EulogyModel(){
//
//    }
//
//    public EulogyModel(String deceasedPicture, String earlylifeBiography, String authorName, String burialLocation, String dateOfBirth, String dateOfDeath, String deceasedFname, String deceasedLname, String deceasedSname, String educationBiography, String familyBiography, String finalMoments, String userUid, String workBiography, int authorContact) {
//        this.deceasedPicture = deceasedPicture;
//        this.earlylifeBiography = earlylifeBiography;
//        this.authorName = authorName;
//        this.burialLocation = burialLocation;
//        this.dateOfBirth = dateOfBirth;
//        this.dateOfDeath = dateOfDeath;
//        this.deceasedFname = deceasedFname;
//        this.deceasedLname = deceasedLname;
//        this.deceasedSname = deceasedSname;
//        this.educationBiography = educationBiography;
//        this.familyBiography = familyBiography;
//        this.finalMoments = finalMoments;
//        this.userUid = userUid;
//        this.workBiography = workBiography;
//        this.authorContact = authorContact;
//    }
//
//    public String getDeceasedPicture() {
//        return deceasedPicture;
//    }
//
//    public void setDeceasedPicture(String deceasedPicture) {
//        this.deceasedPicture = deceasedPicture;
//    }
//
//    public String getEarlylifeBiography() {
//        return earlylifeBiography;
//    }
//
//    public void setEarlylifeBiography(String earlylifeBiography) {
//        this.earlylifeBiography = earlylifeBiography;
//    }
//
//    public String getAuthorName() {
//        return authorName;
//    }
//
//    public void setAuthorName(String authorName) {
//        this.authorName = authorName;
//    }
//
//    public String getBurialLocation() {
//        return burialLocation;
//    }
//
//    public void setBurialLocation(String burialLocation) {
//        this.burialLocation = burialLocation;
//    }
//
//    public String getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(String dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    public String getDateOfDeath() {
//        return dateOfDeath;
//    }
//
//    public void setDateOfDeath(String dateOfDeath) {
//        this.dateOfDeath = dateOfDeath;
//    }
//
//    public String getDeceasedFname() {
//        return deceasedFname;
//    }
//
//    public void setDeceasedFname(String deceasedFname) {
//        this.deceasedFname = deceasedFname;
//    }
//
//    public String getDeceasedLname() {
//        return deceasedLname;
//    }
//
//    public void setDeceasedLname(String deceasedLname) {
//        this.deceasedLname = deceasedLname;
//    }
//
//    public String getDeceasedSname() {
//        return deceasedSname;
//    }
//
//    public void setDeceasedSname(String deceasedSname) {
//        this.deceasedSname = deceasedSname;
//    }
//
//    public String getEducationBiography() {
//        return educationBiography;
//    }
//
//    public void setEducationBiography(String educationBiography) {
//        this.educationBiography = educationBiography;
//    }
//
//    public String getFamilyBiography() {
//        return familyBiography;
//    }
//
//    public void setFamilyBiography(String familyBiography) {
//        this.familyBiography = familyBiography;
//    }
//
//    public String getFinalMoments() {
//        return finalMoments;
//    }
//
//    public void setFinalMoments(String finalMoments) {
//        this.finalMoments = finalMoments;
//    }
//
//    public String getUserUid() {
//        return userUid;
//    }
//
//    public void setUserUid(String userUid) {
//        this.userUid = userUid;
//    }
//
//    public String getWorkBiography() {
//        return workBiography;
//    }
//
//    public void setWorkBiography(String workBiography) {
//        this.workBiography = workBiography;
//    }
//
//    public int getAuthorContact() {
//        return authorContact;
//    }
//
//    public void setAuthorContact(int authorContact) {
//        this.authorContact = authorContact;
//    }
//
//
//}
package com.blackgoose.fare_the_well.Models;

import java.util.List;

public class EulogyModel {
    private List<String> deceasedPictures;
    private String uid;// List to store multiple image URLs
    private String earlylifeBiography;
    private String authorName;
    private String burialLocation;
    private String dateOfBirth;
    private String dateOfDeath;
    private String deceasedFname;
    private String deceasedLname;
    private String deceasedSname;
    private String educationBiography;
    private String familyBiography;
    private String finalMoments;
    private String userUid;
    private String workBiography;
    private String key;
    private String authorContact; // Change this to String

    // Default constructor required for calls to DataSnapshot.getValue(EulogyModel.class)
    public EulogyModel() {
    }

    public EulogyModel(List<String> deceasedPictures, String uid, String earlylifeBiography, String authorName, String burialLocation, String dateOfBirth, String dateOfDeath, String deceasedFname, String deceasedLname, String deceasedSname, String educationBiography, String familyBiography, String finalMoments, String userUid, String workBiography, String key, String authorContact) {
        this.deceasedPictures = deceasedPictures;
        this.uid = uid;
        this.earlylifeBiography = earlylifeBiography;
        this.authorName = authorName;
        this.burialLocation = burialLocation;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.deceasedFname = deceasedFname;
        this.deceasedLname = deceasedLname;
        this.deceasedSname = deceasedSname;
        this.educationBiography = educationBiography;
        this.familyBiography = familyBiography;
        this.finalMoments = finalMoments;
        this.userUid = userUid;
        this.workBiography = workBiography;
        this.key = key;
        this.authorContact = authorContact;
    }


    // Getters and Setters
    public List<String> getDeceasedPictures() {
        return deceasedPictures;
    }

    public void setDeceasedPictures(List<String> deceasedPictures) {
        this.deceasedPictures = deceasedPictures;
    }

    public String getEarlylifeBiography() {
        return earlylifeBiography;
    }

    public void setEarlylifeBiography(String earlylifeBiography) {
        this.earlylifeBiography = earlylifeBiography;
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

    public String getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public String getDeceasedFname() {
        return deceasedFname;
    }

    public void setDeceasedFname(String deceasedFname) {
        this.deceasedFname = deceasedFname;
    }

    public String getDeceasedLname() {
        return deceasedLname;
    }

    public void setDeceasedLname(String deceasedLname) {
        this.deceasedLname = deceasedLname;
    }

    public String getDeceasedSname() {
        return deceasedSname;
    }

    public void setDeceasedSname(String deceasedSname) {
        this.deceasedSname = deceasedSname;
    }

    public String getEducationBiography() {
        return educationBiography;
    }

    public void setEducationBiography(String educationBiography) {
        this.educationBiography = educationBiography;
    }

    public String getFamilyBiography() {
        return familyBiography;
    }

    public void setFamilyBiography(String familyBiography) {
        this.familyBiography = familyBiography;
    }

    public String getFinalMoments() {
        return finalMoments;
    }

    public void setFinalMoments(String finalMoments) {
        this.finalMoments = finalMoments;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getWorkBiography() {
        return workBiography;
    }

    public void setWorkBiography(String workBiography) {
        this.workBiography = workBiography;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAuthorContact() {
        return authorContact;
    }

    public void setAuthorContact(String authorContact) {
        this.authorContact = authorContact;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;

    }
}