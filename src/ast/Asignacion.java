/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	asignacion:sentencia -> izquierda:expresion  derecha:expresion

public class Asignacion extends AbstractSentencia {

	public Asignacion(Expresion izquierda, Expresion derecha) {
		this.izquierda = izquierda;
		this.derecha = derecha;

		searchForPositions(izquierda, derecha);	// Obtener linea/columna a partir de los hijos
	}

	public Asignacion(Object izquierda, Object derecha) {
		this.izquierda = (Expresion) izquierda;
		this.derecha = (Expresion) derecha;

		searchForPositions(izquierda, derecha);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getIzquierda() {
		return izquierda;
	}
	public void setIzquierda(Expresion izquierda) {
		this.izquierda = izquierda;
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
	private Expresion derecha;
}

