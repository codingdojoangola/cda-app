package com.codingdojoangola.models.chat;


public class ChatData {

    private String imagemUrl, imagemTitulo;

    public ChatData(String imagemUrl, String imagemTitulo) {
        this.imagemUrl = imagemUrl;
        this.imagemTitulo = imagemTitulo;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getImagemTitulo() {
        return imagemTitulo;
    }

    public void setImagemTitulo(String imagemTitulo) {
        this.imagemTitulo = imagemTitulo;
    }
}
