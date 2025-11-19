package neptunPSD1T8JSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONWritePSD1T8 {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        
        try {
            Object obj = parser.parse(new FileReader("../orarendPSD1T8.json"));
            
            JSONObject jsonObject = (JSONObject) obj;
            
            JSONObject orarendObj = (JSONObject) jsonObject.get("PSD1T8_orarend");
            
            JSONArray orak = (JSONArray) orarendObj.get("ora");
            
            System.out.println("PSD1T8 Órarend 2025 - Feldolgozás és Kiírás");
            System.out.println("=============================================");
            
            JSONObject newJsonObject = new JSONObject();
            JSONObject newOrarendObj = new JSONObject();
            JSONArray newOrak = new JSONArray();
            
            for (int i = 0; i < orak.size(); i++) {
                JSONObject ora = (JSONObject) orak.get(i);
                
                System.out.println("\nÓra #" + (i + 1));
                System.out.println("Tárgy: " + ora.get("targy"));
                
                JSONObject idopont = (JSONObject) ora.get("idopont");
                System.out.println("Időpont: " + idopont.get("nap") + " " + 
                                 idopont.get("tol") + "-" + idopont.get("ig"));
                
                System.out.println("Helyszín: " + ora.get("helyszin"));
                System.out.println("Oktató: " + ora.get("oktato"));
                System.out.println("Szak: " + ora.get("szak"));
                System.out.println("----------------------");
                
                JSONObject newOra = new JSONObject();
                newOra.put("targy", ora.get("targy"));
                newOra.put("idopont", idopont);
                newOra.put("helyszin", ora.get("helyszin"));
                newOra.put("oktato", ora.get("oktato"));
                newOra.put("szak", ora.get("szak"));
                
                newOrak.add(newOra);
            }
            
            newOrarendObj.put("ora", newOrak);
            newJsonObject.put("PSD1T8_orarend_masolat", newOrarendObj);
            
            try (FileWriter file = new FileWriter("../orarendPSD1T8_1.json")) {
                file.write(newJsonObject.toJSONString());
                file.flush();
                System.out.println("\nJSON fájl sikeresen kiírva: orarendPSD1T8_1.json");
            }
            
            System.out.println("\nGenerált JSON tartalom:");
            System.out.println(newJsonObject.toJSONString());
            
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
