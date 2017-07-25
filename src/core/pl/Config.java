
package core.pl;


/**
 *
 * @author eneasdh-fs
 */
public class Config {
    
    public Config(){
        

    }
    
    public static final String nodeArrayName = "content";
    public static final String nodeObjectName = "object";
    
    public static String printDefaultName = "Generic / Text Only";
    public static boolean printDialog = false;
            
    public static String printDefaultName(){
        return printDefaultName;
    }
    
    
    public static final int sizePaperForAppointmentTicke = 40;
    public static final String ConfigPropertyName = "config";
}
