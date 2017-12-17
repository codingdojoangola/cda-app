package com.codingdojoangola.models.split;


public class DadosChat {

    private String imagemUrl, imagemTitulo;

    public DadosChat(String imagemUrl, String imagemTitulo) {
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
