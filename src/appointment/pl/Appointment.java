package appointment.pl;

import core.pl.Base;
import org.json.JSONObject;
import response.pl.PrintResponse;
import response.pl.Response;

/**
 *
 * @author eneasdh-fs
 */
public class Appointment extends Base{
    
    private int invnum;
    
    private JSONObject jsonObject;
    
    public Appointment( final JSONObject object ){
        this.jsonObject = object;
    }
    


    @Override
    public String getResponse() {
  
        switch( jsonObject.getString("case") )
        {
            case "ticket":                
                return jsonToString( new Ticket( jsonObject ).print() );                
         
        }
        
        return this.jsonToString();
        
    }

    @Override
    public Response message() {
        PrintResponse nope = new PrintResponse();
        nope.setState("oie no");
        
        return nope;
    }
}
