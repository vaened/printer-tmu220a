
package dhack.templete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class ShowActivity {


  private final static String JSON_DATA =
     "{" 
   + "  \"geodata\": [" 
   + "    {" 
   + "      \"id\": \"1\"," 
   + "      \"name\": \"Julie Sherman\","                  
   + "      \"gender\" : \"female\"," 
   + "      \"latitude\" : \"37.33774833333334\"," 
   + "      \"longitude\" : \"-121.88670166666667\""
   + "    }," 
   + "    {" 
   + "      \"id\": \"2\"," 
   + "      \"name\": \"Johnny Depp\","          
   + "      \"gender\" : \"male\"," 
   + "      \"latitude\" : \"37.336453\"," 
   + "      \"longitude\" : \"-121.884985\""
   + "    }" 
   + "  ]" 
   + "}"; 

  public static void main(final String[] argv) throws JSONException {
      
      
    final JSONObject obj = new JSONObject(JSON_DATA);
    final JSONArray geodata = obj.getJSONArray("geodata");
    final int n = geodata.length();
    for (int i = 0; i < n; ++i) {
      final JSONObject person = geodata.getJSONObject(i);
      System.out.println(person.getInt("id"));
      System.out.println(person.getString("name"));
      System.out.println(person.getString("gender"));
      System.out.println(person.getDouble("latitude"));
      System.out.println(person.getDouble("longitude"));
    }
  }
}