
package dhack.server;

import appointment.pl.Appointment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;

        
/**
 *
 * @author eneasdh-fs
 */
public abstract class Connect {
    
    
    protected ServerSocket serverSocket;
    
    public abstract  String template( String json ); 
    
    protected final int port = 9004;
    
    protected Socket connection;
    
 
   
    public void  open() throws IOException
    {        
        System.out.println("run");
        
        serverSocket = new ServerSocket( this.port );
        
         
        
        while ( true )
        {            
            System.out.println("init server");
            connection = serverSocket.accept();
         
            System.out.println("connect");
            
            BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
            );

            BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(connection.getOutputStream())
            );

            String json;


            while ((json  = in.readLine()) != null) {
        
                JSONObject obj = new JSONObject(json).getJSONObject(
                    core.pl.Config.nodeObjectName 
                );
                
                System.out.println( json );
                String response = "";

                switch( obj.getString("class") )
                {
                    case "appointment":                        
                        response = new Appointment( obj ).getResponse();                        
                        break;                        
                    
                    case "pharmacy":
                        
                        break;
                }
                

                
                out.write( response + "\n" );
                out.flush();
            }

            out.close();
            in.close();
            connection.close();   
        }
        
    }
    
    
    
    
}
