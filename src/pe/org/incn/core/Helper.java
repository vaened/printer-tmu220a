package pe.org.incn.core;

import java.util.Locale;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author eneasdh-fs
 */
public class Helper {
   
    public static String removeAccent(String input) 
    {
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        
        for (int i=0; i<original.length(); i++) {            
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        
        return output;
    }            
    
    public static String splitCamelCase(String s) {
        return s.replaceAll(
           String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])" ), " "
        );
     }
    
    public static String formatMoney(double money)
    {
        return String.format(Locale.ROOT, "S/ %.3f", money);
    }
            
}
