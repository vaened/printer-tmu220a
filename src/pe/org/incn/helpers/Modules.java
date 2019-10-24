/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.org.incn.helpers;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import pe.gob.incn.modules.AppointmentModule;
import pe.gob.incn.modules.CashboxModule;
import pe.gob.incn.modules.Module;
import pe.gob.incn.modules.PharmacyModule;
import pe.gob.incn.modules.TriageModule;

/**
 *
 * @author efloresp
 */
public class Modules {
    
    protected Map<String, Module> classes;
    
    
    public Modules( JSONObject json )
    {
        this.classes = new HashMap<>();
        this.classes.put("pharmacy", new PharmacyModule( json ));
        this.classes.put("appointment", new AppointmentModule( json ));
        this.classes.put("cashbox", new CashboxModule( json ));
        this.classes.put("triage", new TriageModule( json ));
    }
    
    
    public Map<String, Module> getClasses()
    {
        return this.classes;
    }
    
    public boolean has( String key )
    {
        return this.getClasses().containsKey(key);
    }
    
    
    public Module get(String key)
    {
        return this.getClasses().get(key);
    }
    
}
