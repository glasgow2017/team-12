package com.test.glasgowteam12;

import java.io.Serializable;

/**
 * Created by Daumantas on 2017-11-05.
 */

public class User implements Serializable {
    static String name;
    static String email;
    static String service;
    static String age;
    static String userType;
    static String hereFor;

    public static String getName() {
        return name;
    }

    public User(String name, String email, String service, String age, String userType, String hereFor) {
        this.name = name;
        this.email = email;
        this.service = service;
        this.age = age;
        this.userType = userType;
        this.hereFor = hereFor;
    }

    public static String getEmail() {
        return email;
    }

    public static String getService() {
        return service;
    }

    public static String getAge() {
        return age;
    }

    public static String getUserType() {
        return userType;
    }

    public static String getHereFor() {
        return hereFor;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static void setService(String service) {
        User.service = service;
    }

    public static void setAge(String age) {
        User.age = age;
    }

    public static void setUserType(String userType) {
        User.userType = userType;
    }

    public static void setHereFor(String hereFor) {
        User.hereFor = hereFor;
    }
}
