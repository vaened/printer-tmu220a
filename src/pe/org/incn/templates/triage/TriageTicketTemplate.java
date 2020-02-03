package pe.org.incn.templates.triage;

import org.json.JSONObject;
import pe.org.incn.core.Printable;

/**
 *
 * @author ezuñigav
 */
public class TriageTicketTemplate extends Printable {
    
    public TriageTicketTemplate(JSONObject json) {
        super(json);
        this.margin = 14;
    }
@Override
    public Printable draw() {
        
        setMiddleText(":: TICKECT ::");
        nextLine();
        setMiddleText("type_person");
        setMiddleText("sequence");
        
        separator("=");
        setText("Nombres: " + json.getString("person_name"));
        
        setLeftText( hasDni() ? "DNI" :"CE" , "document");
        setRightText("Categoria", "category");
        
        if( hasDni()){
            setText("Procedencia: " + json.getString("procedence"));
            nextLine(); 
        }
        
        setMiddleText( getDate());
        setMiddleText( getTime());
        
        separator("=");
        
        /* line: - title*/
        setMiddleText("*** IMPORTANTE ***");
        
        setText(json.getString("message"));
           
        return this;
    }
    
    protected boolean hasDni()
    {
        return "1".equals(json.getString("type_document"));
    }
    
    protected String getDate()
    {
        return "FECHA : " + json.getString("date");
    }
    
    protected String getTime()
    {
        return "HORA : " + json.getString("time");
    }
}