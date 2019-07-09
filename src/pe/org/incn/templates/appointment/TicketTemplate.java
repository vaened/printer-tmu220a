/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.org.incn.templates.appointment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import pe.org.incn.core.Printable;

/**
 *
 * @author efloresp
 */
public class TicketTemplate extends Printable {

    public TicketTemplate(JSONObject json) {
        super(json);
        this.margin = 14;
    }

    @Override
    public Printable draw() {

        JSONObject config = json.getJSONObject("config");

        JSONArray messages = json.getJSONArray("messages");

        int for_message = 0;

        List<String> stringMessages = new ArrayList<>();

        //String[] stringMessages;
        if (messages != null) {

            for (int i = 0, len = messages.length(); i < len; i++) {
                JSONObject message = messages.getJSONObject(i);
                String msg = message.getString("message");
                stringMessages.add(msg);

                for_message += Math.ceil((double) msg.length() / width);
            }

        }

        boolean isMedico = "1".equals(json.getString("flag_his"));

        int lines = 29,
                increase = 3;

        if (isMedico) {
            lines += 15;
        }

        row = center(config.getString("name").toUpperCase(), row);

        String cell_phone = json.getString("pacmov"),
                phone = json.getString("pactel"),
                name = json.getString("pacpmn"),
                telf = null;

        telf = (phone != null && cell_phone != null) ? phone + " - " + cell_phone : (phone != null ? phone : cell_phone);

        row++;
        printCharAtCol(row, 1, width, "-");

        if (name.length() < width - 6) {
            name = "\"" + name + "\"";
        }

        row = center(name, row);

        writeLine(
                "H.C.: " + json.getString("pachis"), // line 1
                "Categoria: " + json.getString("pardes"), // line 2
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

        if (!Arrays.asList(new String[]{"CARD", "ENDO", "PAE1"}).contains(json.getString("sercod"))) {
            writeLine(
                    "Fecha: " + json.getString("date"), // line 1
                    "Hora: " + json.getString("time"), // line 2
                    row
            );

        } else {
            writeLine(
                    "Fecha: " + json.getString("date"),
                    row
            );
        }

        row++;

        writeLine(
                "T.Pac: " + json.getString("tcides"), // line 1
                "Prefactura: " + json.getString("prfnum"), // line 2
                row
        );

        row++; //

        writeLine(
                "Secuencia: " + json.getString("invnum"), // line 1
                row
        );

        row++;

        row++;

        //        
        printTextWrap(row, row, 1, width, isMedico ? "Medico" : "Profesional");
        row++;
        row = paddingLeft(json.getString("mednam"), row, 3);

        // 
        printTextWrap(row, row, 1, width, "Servicio");
        row++;
        row = paddingLeft(json.getString("serdes"), row, 3);

        // 
        printTextWrap(row, row, 1, width, "Concepto");
        row++;
        row = paddingLeft(json.getString("tardes"), row, 3);

        //
        printTextWrap(row, row, 1, width, "Consultorio" + " - " + json.getString("codcon"));
        row++;
        row = paddingLeft(json.getString("descon"), row, 3);

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
        row = center(json.getString("username") + " - " + json.getString("citfca"), row);

        row++;

        if (isMedico) {
            row++;
            printCharAtCol(row, 1, width, "-_");

            center("Para Farmacia", row);
            row++;

            row = center(name, row);

            writeLine(
                    "H.C.: " + json.getString("pachis"), // line 1
                    "DNI: " + json.getString("pacdoc"), // line 2
                    row
            );

            row++;

            writeLine(
                    "Categoria: " + json.getString("pardes"), // line 2
                    "Prefactura: " + json.getString("prfnum"), // line 1
                    row
            );

            row++;
            writeLine(
                    "Fecha de Atencion: " + json.getString("date"), // line 1
                    row
            );

            row++;
            writeLine(
                    "Medico: " + json.getString("mednam"), // line 1
                    row
            );
            row++;

            /*
        if ( json.getString("pardes").equals("SIS")) { 
            row ++;
            writeLine(
                "FUA: " + json.getString("fua"),
                row
            );  
        }   
             */
            //printCharAtCol(row, 1, width, "-");
            row = center(json.getString("username") + " - " + json.getString("citfca"), row);

            row++;

            printCharAtCol(row, 1, width, "-");

            row++;

            center("Para Otros Examenes", row);

            row++;

            row = center(name, row);

            writeLine(
                    "H.C.: " + json.getString("pachis"), // line 1
                    "DNI: " + json.getString("pacdoc"), // line 2
                    row
            );

            row++;

            writeLine(
                    "Categoria: " + json.getString("pardes"), // line 2
                    "Prefactura: " + json.getString("prfnum"), // line 1
                    row
            );

            row++;
            writeLine(
                    "Fecha de Atencion: " + json.getString("date"), // line 1
                    row
            );

            row++;
            writeLine(
                    "Medico: " + json.getString("mednam"), // line 1
                    row
            );
            row++;
            row++;
            /*
        if ( json.getString("pardes").equals("SIS")) { 
            row ++;
            writeLine(
                "FUA: " + json.getString("fua"),
                row
            );  
        }   
             */

            //printCharAtCol(row, 1, width, "-");
            row = center(json.getString("username") + " - " + json.getString("citfca"), row);

            row++;
            row++;
            row++;
            row++;

        }

        return this;
    }

}
