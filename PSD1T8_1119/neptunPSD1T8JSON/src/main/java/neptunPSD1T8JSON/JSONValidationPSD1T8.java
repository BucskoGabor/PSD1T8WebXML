package neptunPSD1T8JSON;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class JSONValidationPSD1T8 {
    public static void main(String[] args) {
        System.out.println("PSD1T8 órarend JSON Schema Validation");
        System.out.println("======================================");
        
        try {
            InputStream schemaStream = new FileInputStream("../orarendPSD1T8Schema.json");
            JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance();
            JsonSchema schema = schemaFactory.getSchema(schemaStream);
            
            InputStream jsonDataStream = new FileInputStream("../orarendPSD1T8.json");
            JSONObject jsonData = new JSONObject(new JSONTokener(jsonDataStream));
            
            System.out.println("JSON fájl betöltve: orarendPSD1T8.json");
            System.out.println("JSON séma betöltve: orarendPSD1T8Schema.json");
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonData.toString());
            
            Set<ValidationMessage> validationMessages = schema.validate(jsonNode);
            
            if (validationMessages.isEmpty()) {
                System.out.println("\n✓ VALIDATION SUCCESSFUL");
                System.out.println("A JSON dokumentum megfelel a sémának!");
                
                validateJsonStructure(jsonData);
                
            } else {
                System.out.println("\n✗ VALIDATION FAILED");
                System.out.println("Hibák a validáció során:");
                for (ValidationMessage message : validationMessages) {
                    System.out.println("  - " + message.getMessage());
                }
            }
            
        } catch (IOException e) {
            System.err.println("Hiba a fájlok beolvasása közben:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Váratlan hiba történt:");
            e.printStackTrace();
        }
    }
    
    private static void validateJsonStructure(JSONObject jsonData) {
        System.out.println("\nJSON Struktúra Elemzése:");
        System.out.println("------------------------");
        
        try {
            if (jsonData.has("PSD1T8_orarend")) {
                System.out.println("✓ Gyökér objektum: PSD1T8_orarend");
                
                JSONObject orarend = jsonData.getJSONObject("PSD1T8_orarend");
                
                if (orarend.has("ora")) {
                    org.json.JSONArray orak = orarend.getJSONArray("ora");
                    System.out.println("✓ Órák száma: " + orak.length());
                    
                    for (int i = 0; i < orak.length(); i++) {
                        JSONObject ora = orak.getJSONObject(i);
                        System.out.println("\nÓra #" + (i + 1) + " ellenőrzése:");
                        
                        String[] requiredFields = {"targy", "idopont", "helyszin", "oktato", "szak"};
                        for (String field : requiredFields) {
                            if (ora.has(field)) {
                                System.out.println("  ✓ " + field + ": " + ora.get(field));
                            } else {
                                System.out.println("  ✗ Hiányzó mező: " + field);
                            }
                        }
                        
                        if (ora.has("idopont")) {
                            JSONObject idopont = ora.getJSONObject("idopont");
                            String[] timeFields = {"nap", "tol", "ig"};
                            for (String field : timeFields) {
                                if (idopont.has(field)) {
                                    System.out.println("    ✓ " + field + ": " + idopont.get(field));
                                } else {
                                    System.out.println("    ✗ Hiányzó időpont mező: " + field);
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("✗ Hiányzik az 'ora' tömb");
                }
            } else {
                System.out.println("✗ Hiányzik a 'PSD1T8_orarend' gyökér objektum");
            }
            
        } catch (Exception e) {
            System.err.println("Hiba a struktúra elemzése közben:");
            e.printStackTrace();
        }
    }
}
