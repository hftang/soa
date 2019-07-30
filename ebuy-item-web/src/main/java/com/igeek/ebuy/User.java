package com.igeek.ebuy;

/**
 * @author hftang
 * @date 2019-07-29 11:27
 * @desc
 */
public class User {
    private int id;
    private String name;
    private String tel;

    public User() {
    }

    public User(int id, String name, String tel) {
        this.id = id;
        this.name = name;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
