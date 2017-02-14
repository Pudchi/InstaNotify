package com.pudchi.slidewarn;

/**
 * Created by Pudchi on 2015-10-15.
 */
public class FavItem {
    private int person_pic;
    private String person_name;
    private String person_gender;

    public String get_Person_name()
    {
        return person_name;
    }

    public void set_Person_name(String name)
    {
        this.person_name = name;
    }

    public String get_Person_gender()
    {
        return person_gender;
    }

    public void set_Person_gender(String gender)
    {
        this.person_gender = gender;
    }

    public int get_Person_pic()
    {
        return person_pic;
    }

    public void set_Person_pic(int thumbnail)
    {
        this.person_pic = thumbnail;
    }
}
