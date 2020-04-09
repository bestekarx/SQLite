package com.example.sqlite;

import java.io.Serializable;

public class user implements Serializable
{
    public static final String TABLE = "user";

    // column names
    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_FULLNAME = "fullname";

    public long id;
    public String username;
    public String password;
    public String fullname;

    public user(){}

    public user(long id, String username, String password,  String fullname)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
    }
}
