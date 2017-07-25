/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.org.incn.templates.pharmacy;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.org.incn.core.Helper;
import pe.org.incn.core.Printable;

/**
 *
 * @author efloresp
 */
public class ConsolidatedTemplate extends Printable {

    
    public ConsolidatedTemplate(JSONObject json) {
        super(json);
        this.margin = 11;
    }

    
    @Override
    public Printable draw() {
        
        setMiddleText(":: FARMACIA ::");
        
        setText("Nro Orden", "invnum");
        
        separator("-");        

        setMiddleText(config("name").toUpperCase());
        
        nextLine( );
                
        separator("-");
                
        /* line: - patient name */
        setMiddleText("cusnam", "\"");
        
        /* line: - patient data */
        setLeftText("H.C.", "cuscod");
        
        setRightText("Categoria", "pardes");
       

        /* line: - atention data */
        setLeftText("prefactura", "prfnum");        
        
        nextLine();
        separator("=");
        
        /* line: - title*/
        setMiddleText("DATOS DE LA ATENCION");

        /* line: - atention data */
        setLeftText("Fecha", "date");
        setRightText("Hora", "time");
        
        setText("Origen");
        
        setTextWithPadding("orides");
        
        if (! json.getString("mednam").isEmpty()) {
            setText("Medico");
            setTextWithPadding("mednam");            
        }


        if ( json.getBoolean("is_paying")) {
            separator("=");
            setText("TOTAL A PAGAR:");
            
            pullRight(String.format("%.3f", json.getDouble("invppac")));
            nextLine();
            separator("=");

        } else {
            separator("=");
            setMiddleText("DETALLE");    
            nextLine();
            
            JSONArray products  = this.products();
        
            setLeftText("DESCRIPCION", 89);
            setRightText("| CANT", 11);
            
            for(int index = 0, len = products( ).length(); index < len; index ++) {
                JSONObject product = products.getJSONObject(index);
                
                String cantidad = String.format("| %s", product.getString("qtypro"));
                pullRight(String.format("%.3f", json.getDouble("invppac")));
                String description = product.getString("despro").toLowerCase();
                
                setLeftText(description, 90);
                setRightText(cantidad, 9);
            }
            
            nextLine();
            
            setRightText(String.format("Total: %.3f", json.getDouble("invppac")), 40);
     
            separator("-_");
        
            nextLine();

            /* line: - patient name */
            setMiddleText("cusnam", "\"");

            /* line: - patient data */
            setLeftText("H.C.", "cuscod");
            setRightText("DNI", "dni");

            setLeftText("Categoria", "pardes");
            setRightText("Prefactura", "prfnum");
        }
        
        
        if ( json.getString("username") != null && ! json.getString("username").isEmpty()) {
            
            nextLine();
            
            setMiddleText(json.getString("username") + " - " + json.getString("now"));
        }
        
        return this;
    }

    /**
     * Returns the products from json object
     *
     * @return JSONArray
     */
    protected JSONArray products()
    {
        return json.getJSONArray("products");        
    }
    
}
