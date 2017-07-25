/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhack.server;

import java.sql.Array;
import java.util.Arrays;
import org.json.JSONObject;
import pe.gob.incn.modules.PharmacyModule;

/**
 *
 * @author efloresp
 */
public class test {
    public static void main(String[] args) {
     
        //PharmacyModule s = new PharmacyModule(new JSONObject("{template:'consolidated', name:'enea', products: [{despro:'DEXTROMETORFANO 15MG DEXTROMETORFANO 15MG  DEXTROMETORFANO 15MG  JBE 120ML', qtypro: '12'},{despro:'dear decimales a enteros java', qtypro: '12'},{despro:'dear decimales a enteros java', qtypro: '12'}], config: {name:'INSTItuto nacinal de ciencias neutologica'}}"));
        
        //s.response();
        
        String page[][];
        String page2[][] = new String[5][50];
        page = new String[10][50];
        page[0][0] = "nosemen";
        page[0][1] = "nosemen x2";
        page2 = Arrays.copyOf(page, 5);
        
        int emptys= 0;
        
        for(String p[]: page2) {
          System.err.println(p[0]);    
                
        }
      
        

    }
}
