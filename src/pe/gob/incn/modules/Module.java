/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.incn.modules;

import org.json.JSONObject;
import pe.org.incn.core.Printable;

/**
 *
 * @author efloresp
 */
public abstract class Module {
    
    protected final JSONObject json;
    protected boolean printed =  false;
    
    protected Printable printable;
    
    public Module( final JSONObject object ){
        this.json = object;
    }
    
    protected String getTemplateName(){
        return json.getString("template");
    }

    protected abstract Printable template();
    
    public String response(){
        printable = template().draw().print();    
        return new JSONObject(printable.reponse( )).toString();
    }
    
}
