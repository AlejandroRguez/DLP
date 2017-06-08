/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.Visitor;

//	variable:expresion -> valor:String

public class Variable extends AbstractExpresion {

	public Variable(String valor) {
		this.valor = valor;
	}

	public Variable(Object valor) {
		this.valor = (valor instanceof Token) ? ((Token)valor).getLexeme() : (String) valor;

		searchForPositions(valor);	// Obtener linea/columna a partir de los hijos
	}

	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public DefinicionVariable getDefinicion() {
		return definicion;
	}

	public void setDefinicion(DefinicionVariable definicion) {
		this.definicion = definicion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String valor;
	private DefinicionVariable definicion;
}

