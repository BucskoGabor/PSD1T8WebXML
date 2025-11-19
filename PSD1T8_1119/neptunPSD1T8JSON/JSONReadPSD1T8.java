package neptunPSD1T8JSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JSONReadPSD1T8 {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        
        try {
            Object obj = parser.parse(new FileReader("../orarendPSD1T8.json"));
            
            JSONObject jsonObject = (JSONObject) obj;
            
            JSONObject orarendObj = (JSONObject) jsonObject.get("PSD1T8_orarend");
            
            JSONArray orak = (JSONArray) orarendObj.get("ora");
            
            System.out.println("PSD1T8 Órarend 2025");
            System.out.println("======================");
            
            for (int i = 0; i < orak.size(); i++) {
                JSONObject ora = (JSONObject) orak.get(i);
                
                System.out.println("\nÓra #" + (i + 1));
                System.out.println("Tárgy: " + ora.get("targy"));
                
                JSONObject idopont = (JSONObject) ora.get("idopont");
                System.out.println("Nap: " + idopont.get("nap"));
                System.out.println("Kezdés: " + idopont.get("tol"));
                System.out.println("Befejezés: " + idopont.get("ig"));
                
                System.out.println("Helyszín: " + ora.get("helyszin"));
                System.out.println("Oktató: " + ora.get("oktato"));
                System.out.println("Szak: " + ora.get("szak"));
                System.out.println("----------------------");
            }
            
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
