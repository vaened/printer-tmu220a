package pe.org.incn.templates.order;

import org.json.JSONArray;
import org.json.JSONObject;
import pe.org.incn.core.Printable;

/**
 *
 * @author Administrador
 */
public class OrderTemplate extends Printable {
    public OrderTemplate(JSONObject json) {
      super(json);
      this.margin = 14;
   }

    @Override
   public Printable draw() {
      JSONObject config = this.json.getJSONObject("config");
      JSONObject responsible = this.json.getJSONObject("responsible");
      double neto = 0;
      
      this.row = this.center(config.getString("name").toUpperCase(), this.row);
      String name = this.json.getString("patient");
      String title = "ORDENES DE EXAMENES Y PROCEDIMIENTOS";
      ++this.row;
      this.printCharAtCol(this.row, 1, this.width, "-");
      this.row = this.center(title, this.row);
      ++this.row;
      this.row = this.center(name, this.row);
      this.writeLine("H.C.: " + this.json.getString("clinic_history"), "Categoria: " + this.json.getString("category"), this.row);
      ++this.row;
      ++this.row;
      this.printCharAtCol(this.row, 0, this.width, "=");
      this.row = this.center(" DATOS DE LA ORDEN", this.row);
      this.writeLine("Fecha: " + this.json.getString("created_at"), "Hora: " + this.json.getString("hour"), this.row);
      ++this.row;
      this.writeLine("Secuencia: " + this.json.getInt("number"), "Prefactura: " + this.json.getInt("pre_invoice"), this.row);
      ++this.row;
      this.printTextWrap(this.row, this.row, 1, this.width, "Profesional");
      ++this.row;
      this.row = this.paddingLeft(responsible.getString("name"), this.row, 3);
      this.printTextWrap(this.row, this.row, 1, this.width, "Servicio");
      ++this.row;
      this.row = this.paddingLeft(this.json.getString("service"), this.row, 3);
      this.printTextWrap(this.row, this.row, 1, this.width, "Ubicacion");
      ++this.row;
      this.row = this.paddingLeft(responsible.getString("location"), this.row, 3);
      ++this.row;
      this.separator("=");
      this.setMiddleText("DETALLE");
      JSONArray procedures = this.procedures();
      this.writeLine("CANT", "DESCRIPCION", this.row, 5);
      this.nextLine();
      int index = 0;

      for(int len = this.procedures().length(); index < len; ++index) {
         JSONObject product = procedures.getJSONObject(index);
         String cantidad = String.format("%s", product.getInt("quantity"));
         String description = this.reduce(product.getString("description").toLowerCase());
         String price = String.format("%s", product.getDouble("price"));
         String total = String.format("%s", product.getDouble("total"));
         String footer = String.format("p.u.: %s tot: %s", price, total);
         this.writeLine(cantidad, description, this.row, 5);
         
         neto +=  product.getDouble("total");
         
         this.nextLine();
         this.pullRight(footer);
         this.nextLine();
      }

      this.nextLine();
      this.separator("-");
      this.nextLine();
      
      this.pullRight(String.format("neto %s", neto));
      
      this.nextLine();
      this.separator("-_");
      this.nextLine();
      return this;
   }

    public String reduce(String value) {
      Integer len = value.length();
      return len <= 34 ? value : value.substring(0, 34);
   }

   public JSONArray procedures() {
      return this.json.getJSONArray("details");
   }
}
