/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	operacionLogica:expresion -> izquierda:expresion  operador:String  derecha:expresion

public class OperacionLogica extends AbstractExpresion {

	public OperacionLogica(Expresion izquierda, String operador, Expresion derecha) {
		this.izquierda = izquierda;
		this.operador = operador;
		this.derecha = derecha;

		searchForPositions(izquierda, derecha);	// Obtener linea/columna a partir de los hijos
	}

	public OperacionLogica(Object izquierda, Object operador, Object derecha) {
		this.izquierda = (Expresion) izquierda;
		this.operador = (operador instanceof Token) ? ((Token)operador).getLexeme() : (String) operador;
		this.derecha = (Expresion) derecha;

		searchForPositions(izquierda, operador, derecha);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getIzquierda() {
		return izquierda;
	}
	public void setIzquierda(Expresion izquierda) {
		this.izquierda = izquierda;
	}

	public String getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}

	public Expresion getDerecha() {
		return derecha;
	}
	public void setDerecha(Expresion derecha) {
		this.derecha = derecha;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion izquierda;
	private String operador;
	private Expresion derecha;
}

