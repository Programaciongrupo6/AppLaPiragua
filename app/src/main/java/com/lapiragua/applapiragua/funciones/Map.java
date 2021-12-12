package com.lapiragua.applapiragua.funciones;

/**
 *
 * @author Eduardo
 */
public class Map {
    public boolean coordenadasLimites(String  ubicacion){
        String[] b = ubicacion.split(",");
       
         double latitud = Double.parseDouble(b[0]);
         double longitud = Double.parseDouble(b[1]);
        //if( latitud >  12.4466 && latitud < 4.2083){
        if( latitud >  12.4466 || latitud < 4.2083){
            return false;
        }else{
            //if(longitud >-81.8501 && longitud <-66.8483 ){
            if(longitud <-81.8501 || longitud >-66.8483 ){
                return false;
            }else{
                return true;
            }
        }
        
    }
    
    public String convertirUbicacion(String ubicacion){
        String[] b = ubicacion.split(",");
       
         double latitud = Double.parseDouble(b[0]);
         double longitud = Double.parseDouble(b[1]);     
        int gradosLat = (int) Math.round(latitud);
        double minLat = latitud % 1;
        minLat = minLat*60;
        int minutos = (int) Math.round(minLat);
        double segLat = minLat % 1;
        segLat = segLat*60;
        segLat = Math.round(segLat*100.0)/100.0;
        String lat = gradosLat+"°"+minutos+"’"+segLat+"”O";
        
        int gradosLon = (int) Math.round(longitud);
        double minLon = latitud % 1;
        minLon = minLon*60;
        int minutosLon = (int) Math.round(minLon);
        double segLon = minLon % 1;
        segLon = segLon*60;
        segLon = Math.round(segLat*100.0)/100.0;
        String lon = gradosLon+"°"+minutosLon+"’"+segLon+"”N";
        
        
        return lat+","+lon;
    }
   
}
