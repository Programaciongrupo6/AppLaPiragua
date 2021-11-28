package com.lapiragua.applapiragua;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionCampos {


    public boolean formRegistros(String nombreLugar, String tipoPatrimonio,String keyWords,String keyTag, String ubicacion){
        Pattern regex = Pattern.compile("[a-zA-Z]{3,}");
        Pattern p = Pattern.compile("[a-zA-Z,]{3,}");
        Matcher site = regex.matcher(nombreLugar);
        Matcher type = regex.matcher(tipoPatrimonio);
        Matcher word = p.matcher(keyWords);
        Matcher tag = regex.matcher(keyTag);
        Matcher location = regex.matcher(ubicacion);
        if(nombreLugar.isEmpty() || tipoPatrimonio.isEmpty() || keyWords.isEmpty() || keyTag.isEmpty() || ubicacion.isEmpty()) {
            System.out.println("esta vacio");
            return false;
        }else{
            if(site.matches() == true && type.matches() == true && word.matches() == true && tag.matches() == true && location.matches()== true){
                System.out.println("los campos cumplen con las condiciones");
                return true;
            }else{
                System.out.println("POR FAVOR VERIFICA LOS DATOS INGRESADOS");
                return false;
            }
        }

    }

    public boolean formBusqueda(String keyWords) {
        Pattern regex = Pattern.compile("[a-zA-Z,]{2,}");
        Matcher word = regex.matcher(keyWords);
        if (keyWords.isEmpty()) {
            System.out.println("Está vacio, por favor escribe un valor");
            return false;
        } else{

            if (word.matches() == true) {
                System.out.println("los campos cumplen con las condiciones");
                return true;
            } else {
                System.out.println("POR FAVOR VERIFICA LOS DATOS INGRESADOS");
                return false;
            }
        }
    }

    public boolean  formEtiqueta(String keyTag){
        Pattern regex = Pattern.compile("[a-zA-Z]{2,}");
        Matcher tag = regex.matcher(keyTag);
        if(keyTag.isEmpty()){
            System.out.println("Está vacio");
            return false;
        }else {
            if (tag.matches()) {
                System.out.println("los campos cumplen con las condiciones");
                return true;
            } else {
                System.out.println("POR FAVOR VERIFICA LOS DATOS INGRESADOS");
                return false;
            }
        }
    }
    public String generadorEtiqueta(String nombreLugar, String tipoPatrimonio){
        //Pattern regex = Pattern.compile("[A-Z]");
//        if (nombreLugar.isEmpty() || tipoPatrimonio.isEmpty()){
//            return "Tiene campos vacíos";
//        }
        int consecutivo = 1002;
        String nombre = nombreLugar.substring(0,3).toUpperCase();
        String tipo;
        String etiqueta;

        if(tipoPatrimonio.equals("Patrimonio inmaterial")){
            tipo = "PI";
            etiqueta = nombre+tipo+consecutivo;
            return (etiqueta);
        }
        else{
            tipo = "PM";
            etiqueta = nombre+tipo+consecutivo;
            return (etiqueta);
        }

    }

}
