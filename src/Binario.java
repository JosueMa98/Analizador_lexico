import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Binario {
    String input;
    int dataOffset = 0; 
    int codeOffset = 0; 

    public Binario(String intermedio) {
        input = intermedio;
    }
    
//------------------------------------------------------------------------------------------------------------------


    public String convertirABinario() {
        StringBuilder codigoBinario = new StringBuilder("SEGM:OFFSET\n");
        String[] lineas = input.split("\n");

        String segmento = "0000"; // Comienza en el segmento de datos

        for (String linea : lineas) {
        	
        	   linea = linea.trim(); // Eliminar espacios en blanco al inicio y fin
        	   
        	    if (linea.isEmpty() ) {
        	        continue; 
        	    }

            if (linea.contains(".DATA")) {
            	segmento = "  0000"; 
                dataOffset = 0; 
                continue; 
            } 
            
            else if (linea.contains(".CODE")) {
            	segmento = "  0A00"; 
                codeOffset = 0; 
                continue; 
            }

            String binarioLinea = procesarLinea(linea);
            
            if (segmento.equals("0000")) {
                codigoBinario.append(String.format("%s: %04X ", segmento, dataOffset++) + binarioLinea + "\n");
            } 
            
            else {
                codigoBinario.append(String.format("%s: %04X ", segmento, codeOffset++) + binarioLinea + "\n");
            }
        }

        return codigoBinario.toString();
    }
    
//------------------------------------------------------------------------------------------------------------------

    private String procesarLinea(String linea) {
        Matcher reg_mem = Pattern.compile("MOV\\s+(\\w+),\\s*(AX|ax)").matcher(linea);
        
        if (reg_mem.find()) {
            String var = reg_mem.group(1);
            if (var.matches("[a-zA-Z]+")) {  
                return obtenerCodigoOperacional("MOV var, AX") + " " + convertirVariable(var);
            }
        }

        String[] partes = linea.trim().split("\\s+");
        String codigoOp = obtenerCodigoOperacional(partes[0]);
        String operandos = "";

        for (int i = 1; i < partes.length; i++) {
            operandos += convertirNumeroBinario(partes[i].replaceAll("[^0-9.]", ""));
        }
        
        return codigoOp + " " + operandos;
    }

//------------------------------------------------------------------------------------------------------------------

    private String obtenerCodigoOperacional(String instruccion) {
        if (instruccion.equals("MOV var, AX")) {
            return "	  1000 1001";  // Opcode para MOV variable, AX
        }

        switch (instruccion) {
            case "MOV":
                return "	  1011 1000";
            case "ADD":
                return "	  0001 0101";
            case "MUL":
                return "	  0010 0011";
            case "SUB":
                return "	  0010 1010";
            case "DIV":
                return "	  0011 0010";
            default:
                return "	  0000 0000";
        }
    }
    
//------------------------------------------------------------------------------------------------------------------


    private String convertirNumeroBinario(String numero) {
    	
        try {
            int num = Integer.parseInt(numero);
            String bin = Integer.toBinaryString(num);
            bin = String.format("%8s", bin).replace(' ', '0');  
            bin = bin.substring(0, 8) + " " + bin.substring(8); 
            return bin;

        } catch (NumberFormatException e) {
            return "00000000"; 
        }
        
    	  
    }
     
//------------------------------------------------------------------------------------------------------------------


    private String convertirVariable(String variable) {
  
        return "0001 0010";  
    }
    
}//clase
