

public class intermedio
{
    String input;

    public intermedio(String code)
    {
        input = code;
    }

    
    public String traducir()
    {
        String lineas[] = input.split("\\r?\\n");  //'\\r?\\n' separa las lineas de codigo
        String code = "\t.CODE\n";
        String data = "\t.DATA\n";

        for (int i = 1; i < lineas.length - 1; i++)  //recoorer todas las lineas
        {
            String variable = "";
            String valor    = "";  //reiniciar en cada ciclo
            int    cont     = 0;

            for (int j = 0; j < lineas[i].length(); j++) //recorrer cada caracter de la linea actual
            {
                char actual = lineas[i].charAt(j);  //obtiene el caracter actual de la linea actual

                if (actual == '=')
                {
                    data += variable + "       DW     ?\n";
                    j = lineas[i].length();
                }
                else
                {
                    variable += actual;
                }
            }

            for (int j = 0; j < lineas[i].length(); j++)
            {
                char actual = lineas[i].charAt(j);

                if (actual == '=')
                {
                    valor = "";
                }
                
                 
                else if (actual == '+')
                {
                    code += "MOV  AX,  " + valor + "\nADD  AX,  ";
                    valor = "";
                    cont = 1;
                }
                else if (actual == '-')
                {
                    code += "MOV  AX,  " + valor + "\nSUB  AX,  ";
                    valor = "";
                    cont = 1;
                }
                else if (actual == '/')
                {
                    code += "MOV  AX,  " + valor + "\nDIV  AX,  ";
                    valor = "";
                    cont = 1;
                }
                else if (actual == '*')
                {
                    code += "MOV  AX,  " + valor + "\nMUL  AX,  ";
                    valor = "";
                    cont = 1;
                }
                else
                {
                    valor += actual;
                }
            }//fin ciclo linea actual

            if (cont == 1)
            {
                code += valor + "\nMOV  " + variable + ", AX\n";
            }
            else
            {
                code += "MOV  " + variable + ", " + valor + "\n";
            }
        
        }//fin ciclo todas las lineas
        
        return data + "\n" + code;
    }

    
}
