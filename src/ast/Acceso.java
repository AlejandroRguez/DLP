/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	acceso:expresion -> expresion:expresion  propiedad:String

public class Acceso extends AbstractExpresion {

	public Acceso(Expresion expresion, String propiedad) {
		this.expresion = expresion;
		this.propiedad = propiedad;

		searchForPositions(expresion);	// Obtener linea/columna a partir de los hijos
	}

	public Acceso(Object expresion, Object propiedad) {
		this.expresion = (Expresion) expresion;
		this.propiedad = (propiedad instanceof Token) ? ((Token)propiedad).getLexeme() : (String) propiedad;

		searchForPositions(expresion, propiedad);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getExpresion() {
		return expresion;
	}
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	public String getPropiedad() {
		return propiedad;
	}
	public void setPropiedad(String propiedad) {
		this.propiedad = propiedad;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion expresion;
	private String propiedad;
}

