import java.util.regex.Matcher; 
import java.util.regex.Pattern;


public class Lexer 
{
    private String  input; // La cadena de entrada que se analizara.
    private int     posicionActual;  // Rastrea la posicion actual en la cadena de entrada durante el analisis.
    private boolean corcheteApertura;
    private boolean corcheteCierre;
    private boolean corchetesCorrectos;
    private boolean caracterInvalido;
    private int     contInicio;
    private int     contFin;
    private boolean dentroCorchete;
    private int     contVar;



    
    // Definicion de patrones regulares para diferentes tipos de tokens.
    private static final Pattern Numero   = Pattern.compile("(\\d*+\\.\\d*+)|\\.\\d++|\\d+");
    private static final Pattern Variable = Pattern.compile("[a-zA-Z]+");
    private static final Pattern Operador = Pattern.compile("[+\\-*/=]");
    private static final Pattern Inicio   = Pattern.compile("\\{");
    private static final Pattern Fin      = Pattern.compile("\\}");
    private static final Pattern PalabraR       = Pattern.compile("Program|program");


    
//constructor    
    public Lexer(String input)
    {
        this.input             = input;
        this.posicionActual    = 0; // Inicializa la posicion actual en 0.
        this.corcheteApertura  = false;
        this.corcheteCierre    = false;
        this.corchetesCorrectos= false;
        this.caracterInvalido  = false;
        this.contInicio        = 0;
        this.contFin           = 0;
        this.dentroCorchete    = false;
        this.contVar           = 0;
    }

    public Token getSigToken() 
    {
    	
        if (posicionActual >= input.length())
        {
            return new Token(TipoToken.EOF, ""); // Si la posicion actual esta mas alla del final de la cadena, devuelve un token EOF.
        }

        char CaracterActual = input.charAt(posicionActual);  // Obtiene el caracter actual en la posicion actual.
        
        // Omitir espacios en blanco, tabulaciones y saltos de linea
        if (Character.isWhitespace(CaracterActual))   //es un metodo 
        {
        	posicionActual++;
            return getSigToken(); // Llamada recursiva para saltar espacios en blanco
        }
 
//------------------------------------------------------------------------------------------------------------------------------------------------------        
        
      //si es digito
        if (Character.isDigit(CaracterActual) || CaracterActual == '.')
        {
            // Crear un objeto Matcher llamado "numeroMatcher" utilizando el patron almacenado en la variable "Numero".
            Matcher numeroMatcher = Numero.matcher(input.substring(posicionActual));

            if (numeroMatcher.lookingAt()) 
            {
                // Matcher para validar que no haya letras despues del digito
                Matcher letrasDespuesMatcher = Variable.matcher(input.substring(posicionActual + numeroMatcher.end()));
                
                if (letrasDespuesMatcher.lookingAt())
                {
                    caracterInvalido = true;
                }
                
                //no existan numeros antes del corchete
                else if(dentroCorchete==false && posicionActual < input.length())
                {
                    caracterInvalido = true;

                }

                // Si el Matcher encuentra una coincidencia al principio de la subcadena, significa que se ha encontrado un numero valido.
                String numeroToken = numeroMatcher.group();
                
                //validar siguiente caracter deba ser digito
                if (numeroToken.contains(".") && posicionActual + 1 < input.length() && !Numero.matcher(String.valueOf(input.charAt(posicionActual + 1))).matches())
                {
                	
                	caracterInvalido= true;

                }
                

               posicionActual += numeroToken.length();

                if (numeroToken.contains(".")) 
                {
                    // Si el numero contiene un punto decimal, crea un token de tipo "Decimal" con el valor encontrado.
                    return new Token(TipoToken.Decimal, numeroToken);
                } 
                
                else 
                {
                    // Si no contiene un punto decimal, crea un token de tipo "Numero" con el valor encontrado.
                    return new Token(TipoToken.Numero, numeroToken);
                }
            }
        }



//--------------------------------------------------------------------------------------------------------------------------------------------        
       
        else if (CaracterActual == '{') 
        {
            Matcher inicioMatcher = Inicio.matcher(input.substring(posicionActual));

            if (inicioMatcher.lookingAt())
            {
                String inicioToken = inicioMatcher.group();
                posicionActual += inicioToken.length();
                corcheteApertura = true;
                dentroCorchete= true;
                contInicio++;

                // Ignorar espacios en blanco despues del corchete de apertura
                while (posicionActual < input.length() && Character.isWhitespace(input.charAt(posicionActual))) 
                {
                    posicionActual++;
                }

                // Verificar si el siguiente caracter no es una variable
                if (posicionActual < input.length() && !Variable.matcher(String.valueOf(input.charAt(posicionActual))).matches()) 
                {
                    caracterInvalido = true;
                }

                return new Token(TipoToken.Inicio, inicioToken);
            }
        }

//-----------------------------------------------------------------------------------------------------------------------------------------   
        
        else if (CaracterActual == '}') 
        {
            Matcher FinMatcher = Fin.matcher(input.substring(posicionActual));
            if (FinMatcher.lookingAt()) 
            {
                String FinToken = FinMatcher.group();
                posicionActual += FinToken.length();
                corcheteCierre = true;
                contFin++;

                // Verificar caracteres adicionales despues del corchete de cierre (excepto espacios en blanco)
                while (posicionActual < input.length() && Character.isWhitespace(input.charAt(posicionActual))) 
                {
                    // Ignorar espacios en blanco
                    posicionActual++;
                }

                if (posicionActual < input.length()) 
                {
                    // Si hay caracteres no espaciados después del corchete de cierre, marcar como un error
                    caracterInvalido = true;
                }

                return new Token(TipoToken.Fin, FinToken);
            }
        }

//------------------------------------------------------------------------------------------------------------------------------------
        
        else if (Character.isLetter(CaracterActual)) 
        {
            StringBuilder palabraReservada = new StringBuilder();
            int inicioPos = posicionActual; // Almacena la posicion inicial.

            //validar que solo sean letras y no digitos
            while (posicionActual < input.length() && Character.isLetter(input.charAt(posicionActual)) && !Character.isDigit(input.charAt(posicionActual))) 
            {
                palabraReservada.append(input.charAt(posicionActual)); //agregar caracter al stringBuilder
                posicionActual++;
            }

            String palabraReservadaStr = palabraReservada.toString(); // convertir a string

            //validar que si se ingreso una palabra reservada
            if (palabraReservadaStr.equalsIgnoreCase("Program") || palabraReservadaStr.equalsIgnoreCase("program")) 
            {
                // validar si la palabra reservada se usa mas de una vez o no esta al inicio.
                if (inicioPos != 0 || input.indexOf("Program", inicioPos + palabraReservadaStr.length()) != -1 || input.indexOf("program", inicioPos + palabraReservadaStr.length()) != -1) 
                {
                    caracterInvalido = true;
                    return new Token(TipoToken.ERROR, "Palabra reservada debe estar al inicio o se utiliza más de una vez");
                }
                
                //Validar que despues de la palabra reservada se use una variable
                else if(posicionActual + 1 < input.length() && !Variable.matcher(String.valueOf(input.charAt(posicionActual + 1))).matches())
                {
                    caracterInvalido = true;

                }
                
                           
                else 
                {
                    return new Token(TipoToken.PalabraR, palabraReservadaStr);
                }
                
                
            }
            
         
            
         // Si no es la palabra reservada y se usa correctamente, es una variable normal.
            else 
            {
            	
            	//Validar variables no tengan digitos
             	  if (posicionActual < input.length() && (Character.isDigit(input.charAt(posicionActual)))  ) 
            	  {
            	        caracterInvalido = true;
            	  }
            	  
             	  //validar no existan dos variables seguidas
            	  if (posicionActual +1 < input.length() && Character.isLetter(input.charAt(posicionActual +1))) 
            	  {
                      caracterInvalido = true;
                      return new Token(TipoToken.ERROR, "Después de una variable no puede haber otra variable");
                  }
            	  
            	 

                // Verificar si estamos dentro de los corchetes y hay al menos un caracter despues de la variable
                if (corcheteApertura && posicionActual < input.length()) 
                {
                    char siguienteCaracter = input.charAt(posicionActual);
                    
                  

                    // Verificar si el siguiente caracter despues de la variable es un signo '='
                    if (siguienteCaracter != '=') 
                    {
                        caracterInvalido = true;
                        return new Token(TipoToken.ERROR, "Después de la variable dentro de los corchetes debe haber un signo '='");
                    }
                    
                  
                }
                
              
                
                return new Token(TipoToken.ID, palabraReservadaStr);
                
                
            }
        }




//-------------------------------------------------------------------------------------------------------------------------------------------------------------


        
        
        else if (Operador.matcher(String.valueOf(CaracterActual)).matches())
        {
        	 if (posicionActual + 1 < input.length() && Operador.matcher(String.valueOf(input.charAt(posicionActual + 1))).matches()|| dentroCorchete ==false && posicionActual < input.length()) 
        	 {
                 // Si el proximo caracter tambien es un operador, entonces hay dos operadores seguidos
                 caracterInvalido = true;
        	 }
                 
                 else if (posicionActual + 1 < input.length() && Variable.matcher(String.valueOf(input.charAt(posicionActual + 1))).matches()) 
                 
                 {
                     caracterInvalido = true;

                 }
             // validar que no haya espacios en blanco despues de signo =
                 else if (posicionActual + 1 < input.length() && Character.isWhitespace(input.charAt(posicionActual + 1)))
                 {
                     caracterInvalido = true;
                 }
        	 
        	 posicionActual++;
             return new Token(TipoToken.Operador, String.valueOf(CaracterActual));
             
        	 }

        	
 //-------------------------------------------------------------------------------------------------------------------------------------------------       

        
        else 
        {
            // Caracter no reconocido, manejar error o token no valido
        	posicionActual++;
            Token errorToken = new Token(TipoToken.ERROR, String.valueOf(CaracterActual));
            caracterInvalido= true;
            return errorToken;
        }

        return new Token(TipoToken.ERROR, "No se pudo reconocer el token");
    }
    
    

    public boolean comprobarCaracteres()
    {
    	return caracterInvalido;
    	
    }
    
    public boolean comprobarCorchetes()
    {
    	
    	if(corcheteApertura==true && corcheteCierre==true && contInicio==1 && contFin== 1)
    	{
    		return corchetesCorrectos=true;
    	}
    	else
    		return corchetesCorrectos= false;
    }
 
}

enum TipoToken
{
    Numero, Decimal, ID, Operador, EOF, ERROR, Inicio, Fin, PalabraR
}

class Token
{
    private TipoToken Tipo;
    private String Valor;

//constructor
    public Token(TipoToken Tipo, String Valor)
    {
        this.Tipo = Tipo;
        this.Valor = Valor;
    }

    public TipoToken getTipo()
    {
        return Tipo;
    }

    public String getValor()
    {
        return Valor;
    }

    
    public String toString()
    {
        return "Token --  " + "Tipo:  " + Tipo + " - Valor: '" + Valor + "'";
    }










}