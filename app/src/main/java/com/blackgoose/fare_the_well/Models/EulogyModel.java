package com.blackgoose.fare_the_well.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class EulogyModel implements Serializable {
    public String Eulogyid;
    public String firstName, secondName, lastName;
    public String birthYear, passingYear;
    public String burialLocation, eulogyText;
    public String authorName, authorPhone;
    public String mainImageUrl;
    public ArrayList<String> galleryImages;
    public ArrayList<ProgramModel> funeralPrograms;
    public String status;
    public Object dateCreated;
    public String userId, mpesaReceipt;

    public EulogyModel() {} // Required empty constructor

    public String getLastName() {
        return lastName;
    }
    public String getDateCreated() {
        if (dateCreated instanceof Long) {
            return String.valueOf(dateCreated);
        }
        return (String) dateCreated;
    }

    public void setDateCreated(Object dateCreated) {
        this.dateCreated = dateCreated;
    }

}
