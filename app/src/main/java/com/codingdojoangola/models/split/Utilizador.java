package com.codingdojoangola.models.split;


public class Utilizador {

    private String name;
    private String email;
    private String image;
    public String id;
    public Utilizador(){
    }

    public Utilizador(String name, String email, String image) {
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
