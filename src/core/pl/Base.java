
package core.pl;

import org.json.JSONObject;
import response.pl.Response;

/**
 *
 * @author eneasdh-fs
 */
public abstract class Base {
    
    public abstract String getResponse();
    
    public abstract Response message();
    
    public String jsonToString( Response object ){
        return new JSONObject(object).toString();
    }
    
    public String jsonToString( ){
        return new JSONObject( this.message() ).toString();
    }
    
    
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
    
}
