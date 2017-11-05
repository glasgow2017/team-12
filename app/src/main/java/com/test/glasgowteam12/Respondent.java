package com.test.glasgowteam12;

import android.support.annotation.NonNull;

/**
 * Created by Daumantas on 2017-11-05.
 */

public class Respondent implements Comparable<Respondent>{
    static String name;
    static String email;
    static String experience;
    static String service;


    public Respondent(String name, String email, String experience, String service) {
        this.name = name;
        this.email = email;
        this.experience = experience;
        this.service = service;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Respondent.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Respondent.email = email;
    }

    public static String getExperience() {
        return experience;
    }

    public static void setExperience(String experience) {
        Respondent.experience = experience;
    }

    public static String getService() {
        return service;
    }

    public static void setService(String service) {
        Respondent.service = service;
    }

    @Override
    public int compareTo(@NonNull Respondent r) {
        return Integer.parseInt(this.experience) -Integer.parseInt(r.experience) ;
    }
}
