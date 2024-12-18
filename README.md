# **Analizador Léxico - Proyecto en Java**

## **Descripción del Proyecto**
Este proyecto es un **Analizador Léxico** desarrollado 100% en **Java** como parte de la clase de **Autómatas**. El analizador permite procesar un lenguaje diseñado por nosotros, identificando y clasificando sus componentes léxicos. Además, el sistema es capaz de convertir las entradas del lenguaje hasta nivel **binario**.

El objetivo principal del proyecto es demostrar el funcionamiento de un analizador léxico y su capacidad para procesar un lenguaje formal mediante una herramienta robusta y eficiente.

---

## **Funcionalidades Principales**
1. **Análisis Léxico**:
   - Identificación de **tokens** como palabras clave, identificadores, operadores, literales, etc.
   - Clasificación de los componentes léxicos según las reglas del lenguaje creado.

2. **Conversión a Nivel Binario**:
   - Transformación de los tokens identificados a su representación en **binario**.

3. **Manejo de Errores Léxicos**:
   - Reporte de errores en caso de encontrar símbolos no reconocidos.

4. **Estructura Modular**:
   - Código organizado en clases para facilitar el mantenimiento y escalabilidad del proyecto.

---

## **Tecnologías Utilizadas**
- **Lenguaje de Programación**: Java
- **Entorno de Desarrollo**: Eclipse IDE

---

## **Requisitos del Sistema**
Para ejecutar el proyecto, asegúrate de tener:
- **Java Development Kit (JDK)** 8 o superior instalado.
- **Eclipse IDE** (o cualquier otro IDE compatible con Java).
- Sistema operativo: Windows, macOS o Linux.

---

## **Instalación y Ejecución**
Sigue los siguientes pasos para ejecutar el analizador léxico:

1. **Clonar el Repositorio**:
   ```bash
   git clone https://github.com/JosueMa98/AnalizadorLexico2.git
   ```

2. **Importar el Proyecto en Eclipse**:
   - Abre **Eclipse IDE**.
   - Selecciona **File > Import > Existing Projects into Workspace**.
   - Navega a la carpeta del proyecto clonado y selecciona el directorio.

3. **Compilar y Ejecutar**:
   - Ejecuta el archivo principal del proyecto (**Lexer.java**) para iniciar el analizador léxico.
   
---

## **Estructura del Proyecto**
```bash
AnalizadorLexico2/
|-- src/
|   |-- Lexer.java        # Archivo principal
|   |-- intermedio.java               
|   |-- Interfaz.java               
|   |-- Binario.java      # Conversión a nivel binario
```

---

## **Ejemplo de Entrada y Salida**
### **Entrada**:
```plaintext
program CA{ 
x=2+32
Y=45*6
w=42-23
Z=50/25 
}
```

### **Salida**:
```plaintext
Token --  Tipo:  PalabraR - Valor: 'program'
Token --  Tipo:  ID - Valor: 'CA'
Token --  Tipo:  Inicio - Valor: '{'
Token --  Tipo:  ID - Valor: 'x'
Token --  Tipo:  Operador - Valor: '='
Token --  Tipo:  Numero - Valor: '2'
Token --  Tipo:  Operador - Valor: '+'
Token --  Tipo:  Numero - Valor: '32'
Token --  Tipo:  ID - Valor: 'Y'
Token --  Tipo:  Operador - Valor: '='
Token --  Tipo:  Numero - Valor: '45'
Token --  Tipo:  Operador - Valor: '*'
Token --  Tipo:  Numero - Valor: '6'
Token --  Tipo:  ID - Valor: 'w'
Token --  Tipo:  Operador - Valor: '='
Token --  Tipo:  Numero - Valor: '42'
Token --  Tipo:  Operador - Valor: '-'
Token --  Tipo:  Numero - Valor: '23'
Token --  Tipo:  ID - Valor: 'Z'
Token --  Tipo:  Operador - Valor: '='
Token --  Tipo:  Numero - Valor: '50'
Token --  Tipo:  Operador - Valor: '/'
Token --  Tipo:  Numero - Valor: '25'
Token --  Tipo:  Fin - Valor: '}'

---
Binario:
  0A00: 0000 	  1011 1000 0000000000000010 
  0A00: 0001 	  0001 0101 0000000000100000 
  0A00: 0002 	  1000 1001 0001 0010
  0A00: 0003 	  1011 1000 0000000000101101 
  0A00: 0004 	  0010 0011 0000000000000110
........
```

---

## **Créditos**
- **Desarrollador**: Victor Josué Maldonado Arana
- **Institución**: Instituto Tecnológico de Culiacán
- **Materia**: Lenguajes Autómatas

---

## **Licencia**
Este proyecto está licenciado bajo la [Licencia Apache 2.0](LICENSE).

---

## **Contacto**
Si tienes alguna pregunta o sugerencia, no dudes en ponerte en contacto:
- **Correo Electrónico**: [L20171583@culiacan.tecnm.mx](L20171583@culiacan.tecnm.mx)
- **GitHub**: [JosueMa98](https://github.com/JosueMa98)

---

¡Gracias por revisar nuestro proyecto **Analizador Léxico**! 🚀

