/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.org.incn.templates.cashbox;

import org.json.JSONObject;
import pe.org.incn.core.Printable;

/**
 *
 * @author efloresp
 */
public class PaymentDocumentTemplate extends Printable{

    public PaymentDocumentTemplate(JSONObject json) {
        super(json);
    }

    @Override
    public Printable draw() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
