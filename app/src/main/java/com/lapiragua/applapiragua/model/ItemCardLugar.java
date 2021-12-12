package com.lapiragua.applapiragua.model;

public class ItemCardLugar {
    String nombre;
    String localizacion;
    String path;

    public ItemCardLugar() {
    }

    public ItemCardLugar(String nombre, String localizacion, String path) {
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.path = path;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
