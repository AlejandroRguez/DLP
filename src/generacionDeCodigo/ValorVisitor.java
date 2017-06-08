package generacionDeCodigo;

import java.util.HashMap;
import java.util.Map;

import ast.Acceso;
import ast.AccesoArray;
import ast.CampoStruct;
import ast.Caracter;
import ast.Cast;
import ast.DefinicionStruct;
import ast.Distinto;
import ast.Expresion;
import ast.InvocacionExpresion;
import ast.LiteralEntero;
import ast.LiteralReal;
import ast.OperacionAritmetica;
import ast.OperacionLogica;
import ast.OperacionRelacional;
import ast.TipoArray;
import ast.TipoStruct;
import ast.Variable;
import visitor.DefaultVisitor;

public class ValorVisitor extends DefaultVisitor {
	
	private Map<String, String> instruccion = new HashMap<String, String>();
	private SeleccionDeInstrucciones seleccion;
	
	public ValorVisitor(SeleccionDeInstrucciones seleccion) {
		this.seleccion = seleccion;
		
		instruccion.put("+" , "add");
		instruccion.put("-" , "sub");
		instruccion.put("*" , "mul");
		instruccion.put("/" , "div");
		instruccion.put("<" , "lt");
		instruccion.put(">" , "gt");
		instruccion.put("<=" , "le");
		instruccion.put(">=" , "ge");
		instruccion.put("==" , "eq");
		instruccion.put("!=" , "ne");
	}
	
	
	//	class OperacionAritmetica { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(OperacionAritmetica node, Object param) {

		// super.visit(node, param);

		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		
		seleccion.genera(instruccion.get(node.getOperador()), node.getIzquierda().getTipo());

		return null;
	}

	//	class OperacionRelacional { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(OperacionRelacional node, Object param) {

		// super.visit(node, param);

		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		
		seleccion.genera(instruccion.get(node.getOperador()), node.getIzquierda().getTipo());

		return null;

	}

	//	class OperacionLogica { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(OperacionLogica node, Object param) {

		// super.visit(node, param);

		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		
		if(node.getOperador().equals("&&")){
			seleccion.genera("and");
		}
		else if (node.getOperador().equals("||")){
			seleccion.genera("or");
		}
		return null;

	}

	//	class Distinto { Expresion expresion; }
	public Object visit(Distinto node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		seleccion.genera("not");

		return null;
	}

	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		seleccion.genera(node.getExpresion().getTipo().getSufijo() + "2" + node.getTipo().getSufijo());

		return null;
	}

	//	class InvocacionExpresion { String nombre;  List<Expresion> argumentos; }
	public Object visit(InvocacionExpresion node, Object param) {

		// super.visit(node, param);

		if (node.getArgumentos() != null)
			for (Expresion child : node.getArgumentos())
				child.accept(this, param);
		
		seleccion.genera ("call " + node.getNombre());

		return null;
	}
	//	class AccesoArray { Expresion nombre;  Expresion indice; }
	public Object visit(AccesoArray node, Object param) {


		node.accept(seleccion.getDireccionVisitor(), param);

		seleccion.genera("load" , ((TipoArray) node.getNombre().getTipo()).getTipo());

		return null;
	}

	//	class Acceso { Expresion expresion;  String propiedad; }
	public Object visit(Acceso node, Object param) {

		node.accept(seleccion.getDireccionVisitor(), param);
		/*Aquí hay algo que es null*/
		seleccion.genera("load" , buscarCampo(node.getPropiedad(), ((TipoStruct) node.getExpresion().getTipo()).getDefinicion()).getTipo());
		return null;
	}

	//	class Variable { String valor; }
	public Object visit(Variable node, Object param) {
		node.accept(seleccion.getDireccionVisitor(), param);
		seleccion.genera("load", node.getDefinicion().getTipo());
		return null;
	}

	//	class LiteralEntero { String valor; }
	public Object visit(LiteralEntero node, Object param) {
		seleccion.genera("push " + node.getValor());
		return null;
	}

	//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		seleccion.genera("pushf " + node.getValor());
		return null;
	}

	//	class Caracter { String valor; }
	public Object visit(Caracter node, Object param) {
		if(node.getValor().equals("'\\n'")){
			seleccion.genera("pushb 10");
		}
		else
			seleccion.genera("pushb " + ((int)node.getValor().charAt(1)));
		return null;
	}

	
	private CampoStruct buscarCampo(String propiedad, DefinicionStruct definicion){
		for(CampoStruct c: definicion.getCampos()){
			if(propiedad.equals(c.getNombre())){
				return c;
			}
		}
		return null;
	}

}
