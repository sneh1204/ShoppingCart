package com.example.shoppingcart.models;

public class Utils {

    public static Integer parseInt(String value){
        Integer converted;
        try{
            converted = Integer.valueOf(value);
            if(converted < 1) throw new NumberFormatException();
        }catch (NumberFormatException exc){
            return null;
        }
        return converted;
    }

}
