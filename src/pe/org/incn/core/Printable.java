/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.org.incn.core;

import appointment.pl.Ticket;
import core.pl.Config;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import org.json.JSONObject;
import pe.org.incn.helpers.Response;
import printer.pl.Printer;

/**
 *
 * @author efloresp
 */
public abstract class Printable extends Printer {

    protected int margin = 10;
      
    protected Response response;

    protected JSONObject json;

    protected final String file = "printer.txt";
    
    protected int row = 0;

    public Printable(JSONObject json) {
        this.width = Config.sizePaperForAppointmentTicke;
        this.middle = width / 2;
        this.json = json;
        this.response = new Response();
                
        setOutSize(1000, width);
    }

    protected JSONObject config() {
        return this.json.getJSONObject(Config.ConfigPropertyName);
    }

    protected void separator(String character) {
        nextLine();
        printCharAtCol(row, 1, width, character);
    }

    protected String config(String key) {
        return this.json.getJSONObject(Config.ConfigPropertyName).getString(key);
    }

    public Response reponse() {
        return this.response;
    }

    public abstract Printable draw();

    @Override
    public String toFile(String fileName) {
        
        int filled = 0;
        
        for (String[] row : this.page) {
            for (String item : row) {
                if (item != null) {
                    filled ++;
                    break;
                }
            }
        }
        
        System.out.println(margin);
        
            System.out.println(filled + margin);
        this.page = Arrays.copyOf(page, filled + margin);
                
        return super.toFile(fileName);
    }

    protected int nextLine() {
        return this.row ++;
    }

    
    protected int previousLine() {
        return this.row --;
    }

    
    private void drawText(String title, String content) {
        if (content == null || content.isEmpty()) {
            writeLine(title, row);
        } else {
            writeLine(title + ": " + make(content), row);
        }

        this.nextLine();
    }

    private void drawTextWrap(String title, String content, boolean left) {

        String line = title + ": " + make(content);
        
        if (!left) {
            printTextWrap(row, row, middle + 1, width, line);
            this.nextLine();
        } else {
            printTextWrap(row, row, 1, middle, line);
        }

    }

    protected boolean isEmpty(String key) {
        return key == null || key.isEmpty() || json.isNull(key);
    }

    protected void setText(String title, String content) {
        drawText(title, content);
    }

    protected void setText(String title) {
        drawText(title, null);
    }

    protected void setLeftText(String title, String content) {
        drawTextWrap(title, content, true);
    }
    
    /**
     * Set Text with padding left
     * 
     * @param title
     * @param until
     */
    protected void setLeftText(String title, int until) {
        until = (int) Math.round((double)width / 100 * until );
        printTextWrap(row, row, 1, until, title);
    }
    
    /**
     * Set Text with padding right
     *
     * @param title
     * @param until
     */
    protected void setRightText(String title, int until) {
        until = (int) Math.round((double)width / 100 * until ) + 1;
        printTextWrap(row, row, width - until, width, title);
        nextLine( );
    }

    protected void setRightText(String title, String content) {
        drawTextWrap(title, content, false);
        
    }

    protected void setMiddleText(String title) {
        center(make(title), row);
        this.nextLine();
    }
    
    protected void setMiddleText(String title, String decorate) {
        center(decorate + make(title) + decorate, row);
        this.nextLine();
    }
    
    public void pullRight( String text)
    {
      String space = "";
      for (int i = 1, total = width - text.length(); i < total; i++) {
        space += " ";
      }
      
      String n = space + text;
        System.err.println(n);
        System.err.println(n.length());
        writeLine(space + text, row);
    }
    
    protected String make(String key) {
        
        if(json.has(key)) {
            key = json.getString(key);
            return key == null || key.isEmpty() ? " - " : key;
        } 
        
        return key == null || this.isEmpty(key) ? key : json.getString(key);
    }

    protected void setTextWithPadding(String title, int padding)
    {
        paddingLeft(make(title), row, padding);
        nextLine();
    }

    protected void setTextWithPadding(String title)
    {
        paddingLeft(make(title), row, 3);
        nextLine();
    }
    
    
    public Printable print() {
        
        
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

        PrintService service = null;

        if (!Config.printDialog) {
            service = find(Config.printDefaultName());
        }

        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

        if (Config.printDialog || service == null) {
            service = showDialog(flavor, attributeSet);
        }

        if (service == null) {
            response.setState("cancel");
            return this;
        }

        String path = toFile(file);

        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Doc doc = new SimpleDoc(width, flavor, null);
        Doc doc = new SimpleDoc(inputStream, flavor, null);

        DocPrintJob printJob = service.createPrintJob();

        try {
            printJob.print(doc, attributeSet);

        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " otro error _v");
            //ex.printStackTrace();
            response.setState("error");
            return this;
        }

        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + "  -- Error al cerrar el archivo");
        }

        response.setState("success");

        return this;

    }

}
