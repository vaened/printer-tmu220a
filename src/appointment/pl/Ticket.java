package appointment.pl;

import adilson.util.PrinterMatrix;
import core.pl.Config;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import org.json.JSONObject;
import response.pl.PrintResponse;
import response.pl.Response;
import core.pl.IPrint;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import org.json.JSONArray;

/**
 *
 * @author Development
 */
public class Ticket extends printer.pl.Printer implements IPrint {

    JSONObject object;

    public Ticket( JSONObject object ) {
        this.object = object;

        width = Config.sizePaperForAppointmentTicke;
        middle = width / 2;
    }

    @Override
    public Response print() {

        PrintResponse response = new PrintResponse();

        JSONObject config = object.getJSONObject("config");

        JSONArray messages = object.getJSONArray("messages");

        int for_message = 0;

        List<String> stringMessages = new ArrayList<String>();

        //String[] stringMessages;
        if (messages != null) {

            for (int i = 0, len = messages.length(); i < len; i++) {
                JSONObject message = messages.getJSONObject(i);
                String msg = message.getString("message");
                stringMessages.add(msg);

                for_message += Math.ceil((double) msg.length() / width);
            }

        }

        boolean isMedico = "1".equals(object.getString("flag_his"));

        int row = 1,
                lines = 29,
                increase = 3;

        if (isMedico) {
            lines += 11;
        }

        setOutSize((lines + for_message + increase), width);

        row = center(config.getString("name").toUpperCase(), row);

        String cell_phone = object.getString("pacmov"),
                phone = object.getString("pactel"),
                name = object.getString("pacpmn"),
                telf = null;

        telf = (phone != null && cell_phone != null) ? phone + " - " + cell_phone : (phone != null ? phone : cell_phone);

        row++;
        printCharAtCol(row, 1, width, "-");

        if (name.length() < width - 6) {
            name = "\"" + name + "\"";
        }

        row = center(name, row);

        writeLine(
                "H.C.: " + object.getString("pachis"), // line 1
                "Categoria: " + object.getString("pardes"), // line 2
                row
        );

        row++; //

        writeLine(
                "Tef: " + (telf != null ? telf : "no tiene"), // line 1
                row
        );

        row++;
        row++;
        printCharAtCol(row, 0, width, "=");

        row = center(" DATOS DE LA CITA ", row);

        writeLine(
                "Fecha: " + object.getString("date"), // line 1
                "Hora: " + object.getString("time"), // line 2                
                row
        );

        row++;

        writeLine(
                "T.Pac: " + object.getString("tcides"), // line 1
                "Prefactura: " + object.getString("prfnum"), // line 2
                row
        );

        row++;

        row++;

        //        
        printTextWrap(row, row, 1, width, "Medico");
        row++;
        row = paddingLeft(object.getString("mednam"), row, 3);

        // 
        printTextWrap(row, row, 1, width, "Servicio");
        row++;
        row = paddingLeft(object.getString("serdes"), row, 3);

        //
        printTextWrap(row, row, 1, width, "Consultorio" + " - " + object.getString("codcon"));
        row++;
        row = paddingLeft(object.getString("descon"), row, 3);

        //
        row++;

        printCharAtCol(row, 1, width, "=");

        row++;
        printTextWrap(row, row, 0, width, "INDICACIONES");
        row++;

        if (messages != null) {
            for (String msg : stringMessages) {
                row = paddingLeft(msg, row, 0);
            }
        }

        row++;

        //printCharAtCol(row, 1, width, "-");
        row = center(object.getString("username") + " - " + object.getString("citfca"), row);

        row++;

        if (isMedico) {
            row++;
            printCharAtCol(row, 1, width, "-_");

            row++;

            row = center(name, row);

            writeLine(
                    "H.C.: " + object.getString("pachis"), // line 1
                    "DNI: " + object.getString("pacdoc"), // line 2
                    row
            );

            row++;

            writeLine(
                    "Categoria: " + object.getString("pardes"), // line 2
                    "Prefactura: " + object.getString("prfnum"), // line 1
                    row
            );

            row++;
            writeLine(
                    "Fecha de Atencion: " + object.getString("date"), // line 1
                    row
            );

            row++;
            writeLine(
                    "Medico: " + object.getString("mednam"), // line 1
                    row
            );
            row++;
            row++;
            /*
        if ( object.getString("pardes").equals("SIS")) { 
            row ++;
            writeLine(
                "FUA: " + object.getString("fua"),
                row
            );  
        }   
             */

            //printCharAtCol(row, 1, width, "-");
            row = center(object.getString("username") + " - " + object.getString("citfca"), row);
        }

        
        
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
            return response;
        }

        String path = toFile("printer.txt");

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
            return response;
        }

        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + "  -- Error al cerrar el archivo");
        }

        response.setState("success");

        return response;
    }

    @Override
    public Response message() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
