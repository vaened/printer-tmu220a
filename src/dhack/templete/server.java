package dhack.templete;


import dhack.server.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.ColorSupported;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.Sides;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;
 
/**
 * @Autor Leyer
 * @see   GlassFish Tools Bundle For Eclipse
 * 
 * Servidor simple para multiples conexiones
 */

public class server 
{



    
	public static void main(String[] args) throws PrintException {
            
                //Ticket attribute content
   String contentTicket = "VINATERIA {{nameLocal}}\n"+
    "EXPEDIDO EN: {{expedition}}\n"+
    "DOMICILIO CONOCIDO MERIDA, YUC.\n"+
    "=============================\n"+
    "MERIDA, XXXXXXXXXXXX\n"+
    "RFC: XXX-020226-XX9\n"+
    "Caja # {{box}} - Ticket # {{ticket}}\n"+
    "LE ATENDIO: {{cajero}}\n"+
    "{{dateTime}}\n"+
    "=============================\n"+
    "{{items}}\n"+
    "=============================\n"+
    "SUBTOTAL: {{subTotal}}\n"+
    "IVA: {{tax}}\n"+
    "TOTAL: {{total}}\n\n"+
    "RECIBIDO: {{recibo}}\n"+
    "CAMBIO: {{change}}\n\n"+
    "=============================\n"+
    "GRACIAS POR SU COMPRA...\n"+
    "ESPERAMOS SU VISITA NUEVAMENTE {{nameLocal}}\n"+
    "\n"+
    "\n";
            try {
                int port = 9005;
                ServerSocket listenSock = null; //the listening server socket
                Socket sock = null;             //the socket that will actually be used for communication //the socket that will actually be used for communication
                
                
                listenSock = new ServerSocket(port);
                
                while (true) {       //we want the server to run till the end of times
                    
                    sock = listenSock.accept();             //will block until connection recieved

                    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                    
                    String line = "";
                    
                    while ( (line = in.readLine()) != null) {
           
DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
    
    //Aca obtenemos el servicio de impresion por defatul
    //Si no quieres ver el dialogo de seleccionar impresora usa esto
    //PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
    
    
    //Con esto mostramos el dialogo para seleccionar impresora
    //Si quieres ver el dialogo de seleccionar impresora usalo
    //Solo mostrara las impresoras que soporte arreglo de bits
    PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
    PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
    PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
    PrintService service = ServiceUI.printDialog(null, 700, 200, printService, defaultService, flavor, pras);
      
    //Creamos un arreglo de tipo byte
    byte[] bytes;

    //Aca convertimos el string(cuerpo del ticket) a bytes tal como
    //lo maneja la impresora(mas bien ticketera :p)
    bytes = contentTicket.getBytes();

    //Creamos un documento a imprimir, a el se le appendeara
    //el arreglo de bytes
    Doc doc = new SimpleDoc(bytes,flavor,null);
      
    //Creamos un trabajo de impresión
    DocPrintJob job = service.createPrintJob();

    //Imprimimos dentro de un try de a huevo
    try {
      //El metodo print imprime
      job.print(doc, null);
    } catch (Exception er) {
        JOptionPane.showMessageDialog(null,"Error al imprimir: " + er.getMessage());
    }
    
                        
                        JSONObject obj = new JSONObject(line).getJSONObject( core.pl.Config.nodeObjectName );
 
                        
                        System.out.println(obj.getString("name"));
                        
                       /**
                        final JSONArray geodata = obj.getJSONArray("content");
                        
                        final int n = geodata.length();
                        String name = "";
                        for (int i = 0; i < n; ++i) {
                          final JSONObject person = geodata.getJSONObject(i);
                          name = person.getString("name");
                          System.out.println( name );
                          System.out.println(person.getString("last_name"));
                          
                        }*/
                        
                        User user = new User();
                        user.setName("successful");
                        
                        JSONObject w = new JSONObject(user);
                        
                        out.write( w.toString() +  "\n");
                        out.flush();
                    }
                    
                    //Closing streams and the current socket (not the listening socket!)
                    System.out.println("server.main()");
                    out.close();
                    in.close();
                    sock.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            }

	}
}