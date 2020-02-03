/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.incn.modules;

import org.json.JSONObject;
import pe.org.incn.core.Printable;
import pe.org.incn.templates.triage.TriageTicketTemplate;


/**
 *
 * @author ezuñigav
 */
public class TriageModule extends Module {
    
    public TriageModule( JSONObject object ) {
        super(object);
    }

    @Override
    protected Printable template() {
        switch (this.getTemplateName()) {
            case "triage":
                return new  TriageTicketTemplate(json);
        }

        return null;
    }
}
