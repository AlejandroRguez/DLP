/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	print:sentencia -> expresion:expresion  sufijo:String

public class Print extends AbstractSentencia {

	public Print(Expresion expresion, String sufijo) {
		this.expresion = expresion;
		this.sufijo = sufijo;

		searchForPositions(expresion);	// Obtener linea/columna a partir de los hijos
	}

	public Print(Object expresion, Object sufijo) {
		this.expresion = (Expresion) expresion;
		this.sufijo = (sufijo instanceof Token) ? ((Token)sufijo).getLexeme() : (String) sufijo;

		searchForPositions(expresion, sufijo);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getExpresion() {
		return expresion;
	}
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	public String getSufijo() {
		return sufijo;
	}
	public void setSufijo(String sufijo) {
		this.sufijo = sufijo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion expresion;
	private String sufijo;
}

