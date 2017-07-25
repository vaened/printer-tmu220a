
package dhack.server;

import core.pl.Config;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import pe.org.incn.main.Connection;


/**
 *
 * @author eneasdh-fs
 */
public class Server extends Connect  {
    
    public Server() throws IOException {
      super();
    }
    

    
    public static void main(String args[]) throws IOException
    {
        
        Properties properties = new Properties();
        InputStreamReader in = null;
        try {
             in = new InputStreamReader(new FileInputStream("C:\\settei.properties"), "UTF-8");
             System.out.print("settei property loaded");
             properties.load(in);
        }       
        catch (UnsupportedEncodingException | FileNotFoundException ex) {
            System.out.println( ex.getMessage() );
        } catch (IOException ex) {
            System.out.println( ex.getMessage() );
        } finally {
            if (null != in) {
             try {
                 in.close();
             } catch (IOException ex) { System.out.println( ex.getMessage() ); }
            }
        }
        
        if (properties.getProperty("print_dialog") != null){
            Config.printDialog = properties.getProperty("print_dialog").equals("true");
        }

        if (properties.getProperty("print_default_name") != null)            {
            Config.printDefaultName = properties.getProperty("print_default_name");
        }

        
        new Connection().open();
    }

    @Override
    public String template(String json) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}
