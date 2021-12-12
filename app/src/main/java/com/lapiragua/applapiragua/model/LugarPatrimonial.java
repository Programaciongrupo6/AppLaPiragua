package com.lapiragua.applapiragua.model;

public class LugarPatrimonial {
    String etiqueta;
    String nombre;
    String palabraClave;
    String tipo;
    String ubicacion;
    String descripcion;
    String usuario;
    String imageUri;
    String imageUrl;


    public LugarPatrimonial() {

    }


    public LugarPatrimonial(String etiqueta, String nombre, String tipo, String palabraClave, String ubicacion , String descripcion,String imageUri, String imageUrl, String usuario) {
        this.etiqueta = etiqueta;
        this.nombre = nombre;
        this.palabraClave = palabraClave;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.imageUri = imageUri;
        this.imageUrl = imageUrl;
        this.usuario = usuario;

    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPalabraClave() {
        return palabraClave;
    }

    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
