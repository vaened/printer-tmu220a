
package printer.pl;

import pe.org.incn.core.Helper;
import adilson.util.PrinterMatrix;
import core.pl.Config;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author eneasdh-fs
 */
public  abstract class Printer  extends PrinterMatrix{

     @Override
     public void printTextWrap(int linI, int linE, int colI, int colE, String text)
    {
      int textSize = text.length();
      int limitH = linE - linI;
      int limitV = colE - colI;
      int wrap = 0;
      if (textSize > limitV)
      {
        wrap = textSize / limitV;
        if (textSize % limitV > 0) {
          wrap += 1;
        }
      }
      else
      {
        wrap = 1;
      }
      
      
      String[] wraped = new String[wrap];

      int end = 0;
      int init = 0;
      for (int i = 1; i <= wrap; i++)
      {
        end = i * limitV;
        if (end > text.length()) {
          end = text.length();
        }
        wraped[(i - 1)] = text.substring(init, end);
        
        init = end;
      }
      
      for (int b = 0; b < wraped.length; b++) {
        if (b <= linE) {
          this.page[linI][colI] = Helper.removeAccent(wraped[b]);
          linI++;
        }
        
      }
    }
    
    protected int middle = 20;
    protected int width = 40;
    
 
    
    public PrintService find( String printName ){
        
          PrintService[] ps = PrintServiceLookup.lookupPrintServices( null , null);
          
          for( PrintService s: ps )
          {
              if ( s.getName().equals( printName ) )
                return s;
              
          }
          
          return null;
    }
    
    public PrintService showDialog(DocFlavor flavor, PrintRequestAttributeSet pras)
    {
        PrintService printService[] =  PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();        
        return ServiceUI.printDialog(null, 700, 200, printService, defaultService, flavor, pras);
    }
    
    public int paddingLeft( String text, int line, int padding ){
        int
            w = width - padding,
            len = text.length();

        
        if ( len <= w  )
        {            
            printTextWrap( line, line, padding, w, text );
            
            line ++;
            return line;
        }
        
        int find = len;
        String prev = text;
       
        while ( find > w )
        {
            find = prev.lastIndexOf(" ");
            prev = prev.substring( 0, find );            
        }

        printTextWrap( line, line, padding, w, prev);   
        
        line ++;

        return paddingLeft( text.substring( find, len ), line, padding);
        
    }
    
    
    public  int center( String text, int line ){
        int
            w = Config.sizePaperForAppointmentTicke,
            len = text.length();
        
        if ( len <= w )
        {            
            printTextWrap( line, line, 0, w, centralizar( w / 2 , text));            
            line ++;
            return line;
        }
        
        int find = len;
        String prev = text;
       
        while ( find > w )
        {
            find = prev.lastIndexOf(" ");
            prev = prev.substring( 0, find );            
        }

        printTextWrap( line, line, 0, w, centralizar( w / 2 , prev));   
        
        line ++;
                 
        return center( text.substring( find, len ), line);
        
    }
    
    //printTextWrap(row, row, 1, middle, "Hora: " +  object.getString("pachis"));
    public void writeLine( String line1, int row ){
       printTextWrap(row, row, 1, width,  line1);
    }

    public void writeLine( String line1, String line2, int row ){
        printTextWrap(row, row, 1, middle,  line1);
        printTextWrap(row, row, middle + 1, width ,  line2);
        
    }
    
   public void writeLine(String line1, String line2, int row, int position) {
      this.printTextWrap(row, row, 1, position, line1);
      this.printTextWrap(row, row, position + 1, this.width, line2);
   }    
}
