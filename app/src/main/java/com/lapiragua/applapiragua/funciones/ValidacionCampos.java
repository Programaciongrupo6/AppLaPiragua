package com.lapiragua.applapiragua.funciones;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionCampos {


    public boolean formRegistros(String nombreLugar, String tipoPatrimonio, String keyWords, String keyTag, String ubicacion, String descripcion, Uri imgUri, Context context) {

        // \u00C0-\u017F es para admitir acentos
        Pattern regex = Pattern.compile("[a-zA-Z\\u00C0-\\u017F\\s]{3,}");
        Pattern wo = Pattern.compile("[a-zA-Z,\\u00C0-\\u017F\\s]{3,}");
        Pattern ta = Pattern.compile("[A-Z0-9]{9}");

        Matcher site = regex.matcher(nombreLugar);
        Matcher type = regex.matcher(tipoPatrimonio);
        Matcher word = wo.matcher(keyWords);
        Matcher tag = ta.matcher(keyTag);
        Matcher location = regex.matcher(ubicacion);

        System.out.println("ACA:" +!descripcion.isEmpty());
        if (nombreLugar.isEmpty() || tipoPatrimonio.isEmpty() || keyWords.isEmpty() || keyTag.isEmpty() || ubicacion.isEmpty()|| descripcion.isEmpty()) {
            System.out.println("Todos los campos son obligatorios");
            Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return false;
        } else {

            if (site.matches() == true) {
                if (word.matches() == true) {
                    if (location.matches() == true) {
                        if (type.matches() == true && tag.matches() == true) {
                            if (imgUri != null) {
                                return true;
                            }else{
                                System.out.println("Por favor verifica los datos ingresados");
                                Toast.makeText(context, "Selecciona un fotografía", Toast.LENGTH_SHORT).show();
                                return false;
                            }

                        } else {
                            System.out.println("Por favor verifica los datos ingresados");
                            Toast.makeText(context, "Por favor verifica los datos ingresados", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    } else {
                        System.out.println("La ubicación debe tener más de 3 caracteres únicamente alfabéticos ");
                        Toast.makeText(context, "La ubicación debe tener más de 3 caracteres únicamente alfabéticos ", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    System.out.println("Las palabras clave debe tener más de 3 caracteres únicamente alfabéticos , separados por coma ");
                    Toast.makeText(context, "Las palabras clave debe tener más de 3 caracteres únicamente alfabéticos , separados por coma ", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                System.out.println("El nombre debe tener más de 3 caracteres únicamente alfabéticos ");
                Toast.makeText(context, "El nombre debe tener más de 3 caracteres únicamente alfabéticos ", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

    }

    public boolean formBusqueda(String keyWords, Context context) {
        Pattern regex = Pattern.compile("[a-zA-Z,\\u00C0-\\u017F\\s]{2,}");
        Matcher word = regex.matcher(keyWords);
        if (!keyWords.isEmpty()) {
            if (word.matches() == true) {
                return true;
            } else {
                System.out.println("Las palabras clave debe tener más de 2 caracteres únicamente alfabéticos , separados por coma");
                return false;
            }
        } else {
            System.out.println("Campo vacío, por favor escribe una palabra de búsqueda");
            Toast.makeText(context, "Campo vacío, por favor escribe una palabra de búsqueda", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean formEtiqueta(String keyTag) {
        Pattern regex = Pattern.compile("[a-zA-Z0-9]{2,9}");
        Matcher tag = regex.matcher(keyTag);
        if (keyTag.isEmpty()) {
            System.out.println("Está vacio");
            return false;
        } else {
            if (tag.matches()) {
                System.out.println("los campos cumplen con las condiciones");
                return true;
            } else {
                System.out.println("POR FAVOR VERIFICA LOS DATOS INGRESADOS");
                return false;
            }
        }
    }

    public String generadorEtiqueta(String nombreLugar, String tipoPatrimonio,
                                    int numeroDeLugares) {
        //Pattern regex = Pattern.compile("[A-Z]");
//        if (nombreLugar.isEmpty() || tipoPatrimonio.isEmpty()){
//            return "Tiene campos vacíos";
//        }
        int consecutivo = 1000 + numeroDeLugares;
        String nombre = nombreLugar.substring(0, 3).toUpperCase();
        String tipo;
        String etiqueta;

        if (tipoPatrimonio.equals("Patrimonio inmaterial")) {
            tipo = "PI";
            etiqueta = nombre + tipo + consecutivo;
            return (etiqueta);
        } else {
            tipo = "PM";
            etiqueta = nombre + tipo + consecutivo;
            return (etiqueta);
        }

    }

}
