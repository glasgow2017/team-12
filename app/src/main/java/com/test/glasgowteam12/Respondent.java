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

    @Override
    public int compareTo(@NonNull Respondent r) {
        return Integer.parseInt(this.experience) -Integer.parseInt(r.experience) ;
    }
}
