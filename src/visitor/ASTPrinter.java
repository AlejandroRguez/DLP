/**
 * @generated VGen 1.3.3
 */

package visitor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import ast.AST;
import ast.Acceso;
import ast.AccesoArray;
import ast.Asignacion;
import ast.CampoStruct;
import ast.Caracter;
import ast.Cast;
import ast.DefinicionFuncion;
import ast.DefinicionStruct;
import ast.DefinicionVariable;
import ast.Distinto;
import ast.If;
import ast.InvocacionExpresion;
import ast.InvocacionSentencia;
import ast.LiteralEntero;
import ast.LiteralReal;
import ast.OperacionAritmetica;
import ast.OperacionLogica;
import ast.OperacionRelacional;
import ast.Position;
import ast.Print;
import ast.Program;
import ast.Read;
import ast.Return;
import ast.TipoArray;
import ast.TipoCaracter;
import ast.TipoEntero;
import ast.TipoReal;
import ast.TipoStruct;
import ast.Traceable;
import ast.Variable;
import ast.While;

/**
 * ASTPrinter. Utilidad que ayuda a validar un arbol AST:
 * 	-	Muestra la estructura del árbol en HTML.
 * 	-	Destaca los hijos/propiedades a null.
 * 	-	Muestra a qué texto apuntan las posiciones de cada nodo (linea/columna)
 * 		ayudando a decidir cual de ellas usar en los errores y generación de código.
 * 
 * Esta clase se genera con VGen. El uso de esta clase es opcional (puede eliminarse del proyecto). 
 * 
 */
public class ASTPrinter extends DefaultVisitor {

	/**
	 * toHtml. Muestra la estructura del AST indicando qué hay en las posiciones (linea y columna) de cada nodo.
	 * 
	 * @param sourceFile	El fichero del cual se ha obtenido el AST
	 * @param raiz				El AST creado a partir de sourceFile
	 * @param filename		Nombre del fichero HMTL a crear con la traza del AST
	 */

	public static void toHtml(String sourceFile, AST raiz, String filename) {
		toHtml(sourceFile, raiz, filename, 4);
	}
	
	public static void toHtml(AST raiz, String filename) {
		toHtml(null, raiz, filename);
	}

	// tabWidth deberían ser los espacios correspondientes a un tabulador en eclipse.
	// Normalmente no será necesario especificarlo. Usar mejor los dos métodos anteriores.
	public static void toHtml(String sourceFile, AST raiz, String filename, int tabWidth) {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(filename.endsWith(".html") ? filename : filename + ".html"));
			generateHeader(writer);
			writer.println("[ASTPrinter] -------------------------------- line:col  line:col");
			if (raiz != null) {
				ASTPrinter tracer = new ASTPrinter(writer, loadLines(sourceFile, tabWidth));
				raiz.accept(tracer, new Integer(0));
			} else
				writer.println("raiz == null");
			writer.println(ls + ls + "[ASTPrinter] --------------------------------");
			generateFooter(writer);
			writer.close();
			System.out.println(ls + "ASTPrinter: Fichero '" + filename + ".html' generado con éxito. Abra el fichero para validar el árbol AST generado.");
		} catch (IOException e) {
			System.out.println(ls + "ASTPrinter: No se ha podido crear el fichero " + filename);
			e.printStackTrace();
		}
	}

	private static void generateHeader(PrintWriter writer) {
		writer.println("<html>\r\n" +
				"<head>\r\n" +
				"<style type=\"text/css\">\r\n" +
				".value { font-weight: bold; }\r\n" +
				".dots { color: #888888; }\r\n" +
				".type { color: #BBBBBB; }\r\n" +
				".pos { color: #CCCCCC; }\r\n" +
				".sourceText { color: #BBBBBB; }\r\n" +
				".posText {\r\n" +
				"	color: #BBBBBB;\r\n" +
				"	text-decoration: underline; font-weight: bold;\r\n" +
				"}\r\n" +
				".null {\r\n" +
				"	color: #FF0000;\r\n" +
				"	font-weight: bold;\r\n" +
				"	font-style: italic;\r\n" +
				"}\r\n" +
			//	 "pre { font-family: Arial, Helvetica, sans-serif; font-size: 11px; }\r\n" +
			//	"pre { font-size: 11px; }\r\n" +
				"</style>\r\n" +
				"</head>\r\n" +
				"\r\n" +
				"<body><pre>");
	}

	private static void generateFooter(PrintWriter writer) {
		writer.println("</pre>\r\n" +
				"</body>\r\n" +
				"</html>");
	}

	private ASTPrinter(PrintWriter writer, List<String> sourceLines) {
		this.writer = writer;
		this.sourceLines = sourceLines;
	}

	// ----------------------------------------------

	//	class Program { List<Definicion> definiciones; }
	public Object visit(Program node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Program", node, false);

		visit(indent + 1, "definiciones", "List<Definicion>",node.getDefiniciones());
		return null;
	}

	//	class CampoStruct { String nombre;  Tipo tipo; }
	public Object visit(CampoStruct node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "CampoStruct", node, false);

		print(indent + 1, "nombre", "String", node.getNombre());
		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		print(indent + 1, "direccion", "int", node.getDireccion());

		return null;
	}

	//	class DefinicionVariable { String nombre;  Tipo tipo;  Ambito ambito; }
	public Object visit(DefinicionVariable node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "DefinicionVariable", node, false);

		print(indent + 1, "nombre", "String", node.getNombre());
		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		print(indent + 1, "ambito", "Ambito", node.getAmbito());
		print(indent + 1, "direccion", "int", node.getDireccion());

		return null;
	}

	//	class DefinicionStruct { String nombre;  List<CampoStruct> campos; }
	public Object visit(DefinicionStruct node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "DefinicionStruct", node, false);

		print(indent + 1, "nombre", "String", node.getNombre());
		visit(indent + 1, "campos", "List<CampoStruct>",node.getCampos());
		return null;
	}

	//	class DefinicionFuncion { String nombre;  List<DefinicionVariable> parametros;  Tipo tipo;  List<DefinicionVariable> variables;  List<Sentencia> cuerpo; }
	public Object visit(DefinicionFuncion node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "DefinicionFuncion", node, false);

		print(indent + 1, "nombre", "String", node.getNombre());
		visit(indent + 1, "parametros", "List<DefinicionVariable>",node.getParametros());
		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		visit(indent + 1, "variables", "List<DefinicionVariable>",node.getVariables());
		visit(indent + 1, "cuerpo", "List<Sentencia>",node.getCuerpo());
		return null;
	}

	//	class OperacionAritmetica { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(OperacionAritmetica node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "OperacionAritmetica", node, false);

		visit(indent + 1, "izquierda", "Expresion",node.getIzquierda());
		print(indent + 1, "operador", "String", node.getOperador());
		visit(indent + 1, "derecha", "Expresion",node.getDerecha());
		return null;
	}

	//	class OperacionRelacional { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(OperacionRelacional node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "OperacionRelacional", node, false);

		visit(indent + 1, "izquierda", "Expresion",node.getIzquierda());
		print(indent + 1, "operador", "String", node.getOperador());
		visit(indent + 1, "derecha", "Expresion",node.getDerecha());
		return null;
	}

	//	class OperacionLogica { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(OperacionLogica node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "OperacionLogica", node, false);

		visit(indent + 1, "izquierda", "Expresion",node.getIzquierda());
		print(indent + 1, "operador", "String", node.getOperador());
		visit(indent + 1, "derecha", "Expresion",node.getDerecha());
		return null;
	}

	//	class Distinto { Expresion expresion; }
	public Object visit(Distinto node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Distinto", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Cast", node, false);

		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class InvocacionExpresion { String nombre;  List<Expresion> argumentos; }
	public Object visit(InvocacionExpresion node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "InvocacionExpresion", node, false);

		print(indent + 1, "nombre", "String", node.getNombre());
		visit(indent + 1, "argumentos", "List<Expresion>",node.getArgumentos());
		return null;
	}

	//	class AccesoArray { Expresion nombre;  Expresion indice; }
	public Object visit(AccesoArray node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "AccesoArray", node, false);

		visit(indent + 1, "nombre", "Expresion",node.getNombre());
		visit(indent + 1, "indice", "Expresion",node.getIndice());
		return null;
	}

	//	class Acceso { Expresion expresion;  String propiedad; }
	public Object visit(Acceso node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Acceso", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		print(indent + 1, "propiedad", "String", node.getPropiedad());
		return null;
	}

	//	class Variable { String valor; }
	public Object visit(Variable node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "Variable", node, "valor", node.getValor());
		return null;
	}

	//	class LiteralEntero { String valor; }
	public Object visit(LiteralEntero node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "LiteralEntero", node, "valor", node.getValor());
		return null;
	}

	//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "LiteralReal", node, "valor", node.getValor());
		return null;
	}

	//	class Caracter { String valor; }
	public Object visit(Caracter node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "Caracter", node, "valor", node.getValor());
		return null;
	}

	//	class Asignacion { Expresion izquierda;  Expresion derecha; }
	public Object visit(Asignacion node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Asignacion", node, false);

		visit(indent + 1, "izquierda", "Expresion",node.getIzquierda());
		visit(indent + 1, "derecha", "Expresion",node.getDerecha());
		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Return", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class Print { Expresion expresion;  String sufijo; }
	public Object visit(Print node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Print", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		print(indent + 1, "sufijo", "String", node.getSufijo());
		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "Read", node, false);

		visit(indent + 1, "expresion", "Expresion",node.getExpresion());
		return null;
	}

	//	class If { Expresion condicion;  List<Sentencia> cuerpoIf;  List<Sentencia> cuerpoElse; }
	public Object visit(If node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "If", node, false);

		visit(indent + 1, "condicion", "Expresion",node.getCondicion());
		visit(indent + 1, "cuerpoIf", "List<Sentencia>",node.getCuerpoIf());
		visit(indent + 1, "cuerpoElse", "List<Sentencia>",node.getCuerpoElse());
		return null;
	}

	//	class While { Expresion condicion;  List<Sentencia> cuerpoWhile; }
	public Object visit(While node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "While", node, false);

		visit(indent + 1, "condicion", "Expresion",node.getCondicion());
		visit(indent + 1, "cuerpoWhile", "List<Sentencia>",node.getCuerpoWhile());
		return null;
	}

	//	class InvocacionSentencia { String nombre;  List<Expresion> argumentos; }
	public Object visit(InvocacionSentencia node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "InvocacionSentencia", node, false);

		print(indent + 1, "nombre", "String", node.getNombre());
		visit(indent + 1, "argumentos", "List<Expresion>",node.getArgumentos());
		return null;
	}

	//	class TipoEntero {  }
	public Object visit(TipoEntero node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "TipoEntero", node, true);

		return null;
	}

	//	class TipoReal {  }
	public Object visit(TipoReal node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "TipoReal", node, true);

		return null;
	}

	//	class TipoCaracter {  }
	public Object visit(TipoCaracter node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "TipoCaracter", node, true);

		return null;
	}

	//	class TipoArray { int tamanyo;  Tipo tipo; }
	public Object visit(TipoArray node, Object param) {
		int indent = ((Integer)param).intValue();

		printName(indent, "TipoArray", node, false);

		print(indent + 1, "tamanyo", "int", node.getTamanyo());
		visit(indent + 1, "tipo", "Tipo",node.getTipo());
		return null;
	}

	//	class TipoStruct { String nombre; }
	public Object visit(TipoStruct node, Object param) {
		int indent = ((Integer)param).intValue();

		printCompact(indent, "TipoStruct", node, "nombre", node.getNombre());
		return null;
	}

	// -----------------------------------------------------------------
	// Métodos invocados desde los métodos visit -----------------------

	private void printName(int indent, String name, AST node, boolean empty) {
		String text = ls + tabula(indent) + name + " &rarr;  ";
		text = String.format("%1$-" + 93 + "s", text);
		if (empty)
			text = text.replace(name, valueTag(name));
		writer.print(text + getPosition(node));
	}

	private void print(int indent, String name, String type, Object value) {
		write(indent, formatValue(value) + "  " + typeTag(type));
	}

	private void print(int indent, String attName, String type, List<? extends Object> children) {
		write(indent, attName + "  " + typeTag(type) + " = ");
		if (children != null)
			for (Object child : children)
				write(indent + 1, formatValue(child));
		else
			writer.print(" " + valueTag(null));
	}

	// Versión compacta de una linea para nodos que solo tienen un atributo String
	private void printCompact(int indent, String nodeName, AST node, String attName, Object value) {
		String fullName = nodeName + '.' + attName;
		String text = ls + tabula(indent) + '\"' + value + "\"  " + fullName;
		text = String.format("%1$-" + 88 + "s", text);
		// text = text.replace(value.toString(), valueTag(value));
		text = text.replace(fullName, typeTag(fullName));
		writer.print(text + getPosition(node));
	}

	private void visit(int indent, String attName, String type, List<? extends AST> children) {
		write(indent, attName + "  " + typeTag(type) + " = ");
		if (children != null)
			for (AST child : children)
				child.accept(this, indent + 1);
		else
			writer.print(" " + valueTag(null));
	}

	private void visit(int indent, String attName, String type, AST child) {
		if (child != null)
			child.accept(this, new Integer(indent));
		else
			write(indent, valueTag(null) + "  " + attName + ':' + typeTag(type));
	}

	// -----------------------------------------------------------------
	// Métodos auxiliares privados -------------------------------------

	private void write(int indent, String text) {
		writer.print(ls + tabula(indent) + text);
	}

	private static String tabula(int count) {
		StringBuffer cadena = new StringBuffer("<span class=\"dots\">");
		for (int i = 0; i < count; i++)
			cadena.append(i % 2 == 0 && i > 0 ? "|  " : "·  ");
		return cadena.toString() + "</span>";
	}

	private String typeTag(String type) {
		if (type.equals("String"))
			return "";
		return "<span class=\"type\">" + type.replace("<", "&lt;").replace(">", "&gt;") + "</span>";
	}

	private String valueTag(Object value) {
		if (value == null)
			return "<span class=\"null\">null</span>";
		return "<span class=\"value\">" + value + "</span>";
	}

	private String formatValue(Object value) {
		String text = valueTag(value);
		if (value instanceof String)
			text = "\"" + text + '"';
		return text;
	}


	// -----------------------------------------------------------------
	// Métodos para mostrar las Posiciones -----------------------------

	private String getPosition(Traceable node) {
		String text = node.getStart() + "  " + node.getEnd();
		text = "<span class=\"pos\">" + String.format("%1$-" + 13 + "s", text) + "</span>";
		text = text.replace("null", "<span class=\"null\">null</span>");
		String sourceText = findSourceText(node);
		if (sourceText != null)
			text += sourceText;
		return text;
	}

	private String findSourceText(Traceable node) {
		if (sourceLines == null)
			return null;

		Position start = node.getStart();
		Position end = node.getEnd();
		if (start == null || end == null)
			return null;

		String afterText, text, beforeText;
		if (start.getLine() == end.getLine()) {
			String line = sourceLines.get(start.getLine() - 1);
			afterText = line.substring(0, start.getColumn() - 1);
			text = line.substring(start.getColumn() - 1, end.getColumn());
			beforeText = line.substring(end.getColumn());
		} else {
			String firstLine = sourceLines.get(start.getLine() - 1);
			String lastLine = sourceLines.get(end.getLine() - 1);

			afterText = firstLine.substring(0, start.getColumn() - 1);

			text = firstLine.substring(start.getColumn() - 1);
			text += "</span><span class=\"sourceText\">" + " ... " + "</span><span class=\"posText\">";
			text += lastLine.substring(0, end.getColumn()).replaceAll("^\\s+", "");

			beforeText = lastLine.substring(end.getColumn());
		}
		return "<span class=\"sourceText\">" + afterText.replaceAll("^\\s+", "")
				+ "</span><span class=\"posText\">" + text
				+ "</span><span class=\"sourceText\">" + beforeText + "</span>";
	}

	private static List<String> loadLines(String sourceFile, int tabWidth) {
		if (sourceFile == null)
			return null;
		try {
			String spaces = new String(new char[tabWidth]).replace("\0", " ");
			
			List<String> lines = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader(sourceFile));
			String line;
			while ((line = br.readLine()) != null)
				lines.add(line.replace("\t", spaces));
			br.close();
			return lines;
		} catch (FileNotFoundException e) {
			System.out.println("Warning. No se pudo encontrar el fichero fuente '" + sourceFile + "'. No se mostrará informaicón de posición.");
			return null;
		} catch (IOException e) {
			System.out.println("Warning. Error al leer del fichero fuente '" + sourceFile + "'. No se mostrará informaicón de posición.");
			return null;
		}
	}


	private List<String> sourceLines;
	private static String ls = System.getProperty("line.separator");
	private PrintWriter writer;
}

