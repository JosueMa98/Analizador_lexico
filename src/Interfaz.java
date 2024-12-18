import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

public class Interfaz
{
    private JTextArea codeInput;
    private JTextArea tokensArea;
    private JTextArea intermediotxt;
    private JTextArea binariotxt;
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new Interfaz();
            }
        });
    }

    // Constructor
    public Interfaz()
    {
        JFrame frame = new JFrame("Compilador"); // La ventana principal
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre la ventana maximizada
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        codeInput     = new JTextArea();
        tokensArea    = new JTextArea();
        intermediotxt = new JTextArea();
        binariotxt	  = new JTextArea();

        JScrollPane scrolltxt    = new JScrollPane(codeInput);
        JScrollPane scrolltokens = new JScrollPane(tokensArea);
        JScrollPane scrollInter  = new JScrollPane(intermediotxt);
        JScrollPane scrollBin  = new JScrollPane(binariotxt);
        
        scrolltxt.setBounds(20, 40, 200, 500);
        scrolltokens.setBounds(230, 40, 340, 500);
        scrollInter.setBounds(580, 40, 250, 500);
        scrollBin.setBounds(840, 40, 500, 500);

        codeInput.setFont(new Font("SansSerif", Font.PLAIN, 20));
        tokensArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
        intermediotxt.setFont(new Font("SansSerif", Font.PLAIN, 20));
        binariotxt.setFont(new Font("SansSerif", Font.PLAIN, 20));

        
        // Crear barra de menu
        JMenuBar barraMenu = new JMenuBar();
        frame.setJMenuBar(barraMenu);

        JMenu menuArchivo     = new JMenu("Archivo");

        JMenuItem newButton   = new JMenuItem("Nuevo");
        JMenuItem openButton  = new JMenuItem("Abrir");
        JMenuItem saveButton  = new JMenuItem("Guardar");
        JMenuItem clearButton = new JMenuItem("Borrar");

        
        JButton analyzeButton = new JButton("Analizar");
        JButton BorrarTokens  = new JButton("Borrar tokens");
        JButton Parser        = new JButton("Parser");
        JButton Semantico     = new JButton("Semantico");  
        JButton intermedio    = new JButton("Intermedio");
        JButton binario		  = new JButton("Codigo objeto");
           

        menuArchivo.add(newButton);
        menuArchivo.add(openButton);
        menuArchivo.add(saveButton);
        menuArchivo.add(clearButton);

        barraMenu.add(menuArchivo);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBounds(500,0,600,40);

        bottomPanel.add(analyzeButton);
        bottomPanel.add(BorrarTokens);
        bottomPanel.add(Parser);
        bottomPanel.add(Semantico);  
        bottomPanel.add(intermedio);
        bottomPanel.add(binario);
     

        frame.add(scrolltxt);
        frame.add(bottomPanel);
        frame.add(scrolltokens);
        frame.add(scrollInter);
        frame.add(scrollBin);
        
        
        // Nuevo
        newButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                codeInput.setText("");
                tokensArea.setText("");
            }
        });

        
        // Abrir
        openButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION)
                {
                    File selectedFile = fileChooser.getSelectedFile();
                    try 
                    {
                        BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                        String line;
                        StringBuilder text = new StringBuilder();

                        while ((line = reader.readLine()) != null)
                        {
                            text.append(line).append("\n");
                        }

                        reader.close();
                        codeInput.setText(text.toString());
                    } catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });

        
        // Guardar
        saveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION)
                {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
                        writer.write(codeInput.getText());
                        writer.close();
                    } catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Borrar
        clearButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                codeInput.setText("");
            }
        });

        
        // BorrarTokens
        BorrarTokens.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                tokensArea.setText("");
            }
        });

        
 // --------------------------------------------------------------------------------------------------------------------------------------
       
        // Parser
        Parser.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String code = codeInput.getText();
                Lexer lexer = new Lexer(code);

                List<String> tokens = new ArrayList<>();
                Token token = lexer.getSigToken();
                while (token.getTipo() != TipoToken.EOF)
                {
                    tokens.add(token.toString());
                    token = lexer.getSigToken();
                }

                boolean corchetesCorrectos = lexer.comprobarCorchetes();
                boolean comprobarCaracteres = lexer.comprobarCaracteres();

                // Mensajes de depuracion
                System.out.println("Tokens: " + tokens);
                System.out.println("Corchetes correctos: " + corchetesCorrectos);
                System.out.println("Caracteres validos: " + !comprobarCaracteres);

                if (corchetesCorrectos && comprobarCaracteres == false) {
                    JOptionPane.showMessageDialog(frame, "Codigo valido", "Resultado del Parser",
                            JOptionPane.INFORMATION_MESSAGE);
                } else
                {
                    JOptionPane.showMessageDialog(frame, "Syntax error", "Resultado del Parser",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

// -------------------------------------------------------------------------------------------------------------------------------------------------------

        // Analizar
        analyzeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String code = codeInput.getText();
                Lexer lexer = new Lexer(code);
                tokensArea.setText("");

                List<String> tokens = new ArrayList<>();
                Token token = lexer.getSigToken();
                while (token.getTipo() != TipoToken.EOF)
                {
                    tokens.add(token.toString());
                    token = lexer.getSigToken();
                }

                for (String tokenString : tokens)
                {
                    tokensArea.append(tokenString + "\n");
                }

            }
        });
        
        
// -----------------------------------------------------------------------------------------------------------------------------
        
        // Semantico
        Semantico.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String code = codeInput.getText();
                Lexer lexer = new Lexer(code);

                List<String> tokens = new ArrayList<>();
                Token token = lexer.getSigToken();
                while (token.getTipo() != TipoToken.EOF)
                {
                    tokens.add(token.toString());
                    token = lexer.getSigToken();
                }

                boolean corchetesCorrectos = lexer.comprobarCorchetes();
                boolean comprobarCaracteres = lexer.comprobarCaracteres();

                // Mensajes de depuracion
                System.out.println("Tokens: " + tokens);
                System.out.println("Corchetes correctos: " + corchetesCorrectos);
                System.out.println("Caracteres validos: " + !comprobarCaracteres);

                
                if (corchetesCorrectos && comprobarCaracteres == false)
                {
                    JOptionPane.showMessageDialog(frame, "Codigo semantico valido", "Resultado Semantico",
                            JOptionPane.INFORMATION_MESSAGE);
                } else
                {
                    JOptionPane.showMessageDialog(frame, "Syntax error", "Resultado Semantico",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });


// --------------------------------------------------------------------------------------------------------------------------------        

        //intermedio
        intermedio.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String code = codeInput.getText();
                intermedio inter = new intermedio(code);
                intermediotxt.setText(inter.traducir());
            }
        });

        frame.setVisible(true);
        
        
//--------------------------------------------------------------------------------------------------
        
        //binario
        binario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String codigoIntermedio = intermediotxt.getText();
                Binario binario = new Binario(codigoIntermedio);
                String resultadoBinario = binario.convertirABinario();
                binariotxt.setText(resultadoBinario);
            }
        });

       
        frame.setVisible(true);
    


 
        

        		  
        
    }//consturctor
    
    
}//class
