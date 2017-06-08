/**
 * @generated VGen 1.3.3
 */

package visitor;

import ast.*;
import java.util.*;

/*
DefaultVisitor. Implementación base del visitor para ser derivada por nuevos visitor.
	No modificar esta clase. Para crear nuevos visitor usar el fichero "_PlantillaParaVisitors.txt".
	DefaultVisitor ofrece una implementación por defecto de cada nodo que se limita a visitar los nodos hijos.
*/
public class DefaultVisitor implements Visitor {

	//	class Program { List<Definicion> definiciones; }
	public Object visit(Program node, Object param) {
		visitChildren(node.getDefiniciones(), param);
		return null;
	}

	//	class CampoStruct { String nombre;  Tipo tipo; }
	public Object visit(CampoStruct node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class DefinicionVariable { String nombre;  Tipo tipo;  Ambito ambito; }
	public Object visit(DefinicionVariable node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class DefinicionStruct { String nombre;  List<CampoStruct> campos; }
	public Object visit(DefinicionStruct node, Object param) {
		visitChildren(node.getCampos(), param);
		return null;
	}

	//	class DefinicionFuncion { String nombre;  List<DefinicionVariable> parametros;  Tipo tipo;  List<DefinicionVariable> variables;  List<Sentencia> cuerpo; }
	public Object visit(DefinicionFuncion node, Object param) {
		visitChildren(node.getParametros(), param);
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		visitChildren(node.getVariables(), param);
		visitChildren(node.getCuerpo(), param);
		return null;
	}

	//	class OperacionAritmetica { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(OperacionAritmetica node, Object param) {
		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);
		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		return null;
	}

	//	class OperacionRelacional { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(OperacionRelacional node, Object param) {
		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);
		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		return null;
	}

	//	class OperacionLogica { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(OperacionLogica node, Object param) {
		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);
		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		return null;
	}

	//	class Distinto { Expresion expresion; }
	public Object visit(Distinto node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class InvocacionExpresion { String nombre;  List<Expresion> argumentos; }
	public Object visit(InvocacionExpresion node, Object param) {
		visitChildren(node.getArgumentos(), param);
		return null;
	}

	//	class AccesoArray { Expresion nombre;  Expresion indice; }
	public Object visit(AccesoArray node, Object param) {
		if (node.getNombre() != null)
			node.getNombre().accept(this, param);
		if (node.getIndice() != null)
			node.getIndice().accept(this, param);
		return null;
	}

	//	class Acceso { Expresion expresion;  String propiedad; }
	public Object visit(Acceso node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Variable { String valor; }
	public Object visit(Variable node, Object param) {
		return null;
	}

	//	class LiteralEntero { String valor; }
	public Object visit(LiteralEntero node, Object param) {
		return null;
	}

	//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		return null;
	}

	//	class Caracter { String valor; }
	public Object visit(Caracter node, Object param) {
		return null;
	}

	//	class Asignacion { Expresion izquierda;  Expresion derecha; }
	public Object visit(Asignacion node, Object param) {
		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);
		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Print { Expresion expresion;  String sufijo; }
	public Object visit(Print node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class If { Expresion condicion;  List<Sentencia> cuerpoIf;  List<Sentencia> cuerpoElse; }
	public Object visit(If node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getCuerpoIf(), param);
		visitChildren(node.getCuerpoElse(), param);
		return null;
	}

	//	class While { Expresion condicion;  List<Sentencia> cuerpoWhile; }
	public Object visit(While node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getCuerpoWhile(), param);
		return null;
	}

	//	class InvocacionSentencia { String nombre;  List<Expresion> argumentos; }
	public Object visit(InvocacionSentencia node, Object param) {
		visitChildren(node.getArgumentos(), param);
		return null;
	}

	//	class TipoEntero {  }
	public Object visit(TipoEntero node, Object param) {
		return null;
	}

	//	class TipoReal {  }
	public Object visit(TipoReal node, Object param) {
		return null;
	}

	//	class TipoCaracter {  }
	public Object visit(TipoCaracter node, Object param) {
		return null;
	}

	//	class TipoArray { int tamanyo;  Tipo tipo; }
	public Object visit(TipoArray node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class TipoStruct { String nombre; }
	public Object visit(TipoStruct node, Object param) {
		return null;
	}
	
	// Método auxiliar -----------------------------
	protected void visitChildren(List<? extends AST> children, Object param) {
		if (children != null)
			for (AST child : children)
				child.accept(this, param);
	}
}
