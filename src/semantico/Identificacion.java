package semantico;

import java.util.*;

import ast.*;
import main.*;
import visitor.*;


public class Identificacion extends DefaultVisitor {

	public Identificacion(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	
	
//	class CampoStruct { String nombre;  Tipo tipo; }
	public Object visit(CampoStruct node, Object param) {

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	//	class DefinicionVariable { String nombre;  Tipo tipo;  Ambito ambito; }
	public Object visit(DefinicionVariable node, Object param) {
		DefinicionVariable definicion;
		if(node.getAmbito().equals(Ambito.LOCAL)){
			definicion = variables.getFromTop(node.getNombre());
			predicado(definicion == null, "Variable local ya definida: " + node.getNombre(), node.getStart());
		}
		if(node.getAmbito().equals(Ambito.PARAMETRO)){
			definicion = variables.getFromTop(node.getNombre());
			predicado(definicion == null, "Parámetro ya definido: " + node.getNombre(), node.getStart());
		}
		
		if(node.getAmbito().equals(Ambito.GLOBAL)) {
			definicion = variables.getFromAny(node.getNombre());
			predicado(definicion == null, "Variable global ya definida: " + node.getNombre(), node.getStart());
		}
		variables.put(node.getNombre(), node);
		
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		
		return null;
	}
	
//	class TipoStruct { String nombre; }
	public Object visit(TipoStruct node, Object param) {
		
		DefinicionStruct definicion = estructuras.get(node.getNombre());
		predicado(definicion != null, "Estructura no definida: " + node.getNombre(), node.getStart());
		node.setDefinicion(definicion);
		
		return null;
	}

	//	class DefinicionStruct { String nombre;  List<CampoStruct> campos; }
	public Object visit(DefinicionStruct node, Object param) {
		
		Map<String, CampoStruct> campos = new HashMap<String, CampoStruct>();
		
		DefinicionStruct definicion = estructuras.get(node.getNombre());
		predicado(definicion == null, "Estructura ya definida: " + node.getNombre(), node.getStart());
		estructuras.put(node.getNombre(), node);
		
		if (node.getCampos() != null)
			for (CampoStruct child : node.getCampos()){
				predicado(!campos.containsKey(child.getNombre()), "Campo ya definido: " + child.getNombre(), child.getStart());
				campos.put(child.getNombre(), child);
				child.setDefinicion(node);
				child.accept(this, param);
			}
		
		return null;
	}

	//	class DefinicionFuncion { String nombre;  List<DefinicionVariable> parametros;  Tipo tipo;  List<DefinicionVariable> variables;  List<Sentencia> cuerpo; }
	public Object visit(DefinicionFuncion node, Object param) {

		variables.set();
		
		DefinicionFuncion definicion = funciones.get(node.getNombre());
		predicado(definicion == null, "Funcion ya definida: " + node.getNombre(), node.getStart());
		funciones.put(node.getNombre(), node);
		
		if (node.getParametros() != null)
			for (DefinicionVariable child : node.getParametros())
				child.accept(this, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		if (node.getVariables() != null)
			for (DefinicionVariable child : node.getVariables())
				child.accept(this, param);

		if (node.getCuerpo() != null)
			for (Sentencia child : node.getCuerpo())
				child.accept(this, param);
		
		variables.reset();
		return null;
	}
	
	//	class InvocacionExpresion { String nombre;  List<Expresion> argumentos; }
	public Object visit(InvocacionExpresion node, Object param) {

		DefinicionFuncion definicion = funciones.get(node.getNombre());
		predicado(definicion != null, "Funcion no definida: " + node.getNombre(), node.getStart());
		node.setDefinicion(definicion); // Enlazar referencia con definición
		
		if (node.getArgumentos() != null)
			for (Expresion child : node.getArgumentos())
				child.accept(this, param);

		
		return null;
	}
	
	//	class Variable { String valor; }
	public Object visit(Variable node, Object param) {
		DefinicionVariable definicion = variables.getFromAny(node.getValor());
		predicado(definicion != null, "Variable no definida: " + node.getValor(), node.getStart());
		node.setDefinicion(definicion);
		return null;
	}
	
//	class InvocacionSentencia { String nombre;  List<Expresion> argumentos; }
	public Object visit(InvocacionSentencia node, Object param) {

		DefinicionFuncion definicion = funciones.get(node.getNombre());
		predicado(definicion != null, "Funcion no definida: " + node.getNombre(), node.getStart());
		node.setDefinicion(definicion); // Enlazar referencia con definición

		if (node.getArgumentos() != null)
			for (Expresion child : node.getArgumentos())
				child.accept(this, param);

		return null;
	}

	
	
	/**
	 * Método auxiliar opcional para ayudar a implementar los predicados de la Gramática Atribuida.
	 * 
	 * Ejemplo de uso:
	 * 	predicado(variables.get(nombre), "La variable no ha sido definida", expr.getStart());
	 * 	predicado(variables.get(nombre), "La variable no ha sido definida", null);
	 * 
	 * NOTA: El método getStart() indica la linea/columna del fichero fuente de donde se leyó el nodo.
	 * Si se usa VGen dicho método será generado en todos los nodos AST. Si no se quiere usar getStart() se puede pasar null.
	 * 
	 * @param condicion Debe cumplirse para que no se produzca un error
	 * @param mensajeError Se imprime si no se cumple la condición
	 * @param posicionError Fila y columna del fichero donde se ha producido el error. Es opcional (acepta null)
	 */
	private void predicado(boolean condicion, String mensajeError, Position posicionError) {
		if (!condicion)
			gestorErrores.error("Identificación", mensajeError, posicionError);
	}


	private GestorErrores gestorErrores;
	ContextMap<String, DefinicionVariable> variables = new ContextMap<String, DefinicionVariable>();
	Map<String, DefinicionFuncion> funciones = new HashMap<String, DefinicionFuncion>();
	Map<String, DefinicionStruct> estructuras = new HashMap<String, DefinicionStruct>();
}
