/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	accesoArray:expresion -> nombre:expresion  indice:expresion

public class AccesoArray extends AbstractExpresion {

	public AccesoArray(Expresion nombre, Expresion indice) {
		this.nombre = nombre;
		this.indice = indice;

		searchForPositions(nombre, indice);	// Obtener linea/columna a partir de los hijos
	}

	public AccesoArray(Object nombre, Object indice) {
		this.nombre = (Expresion) nombre;
		this.indice = (Expresion) indice;

		searchForPositions(nombre, indice);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getNombre() {
		return nombre;
	}
	public void setNombre(Expresion nombre) {
		this.nombre = nombre;
	}

	public Expresion getIndice() {
		return indice;
	}
	public void setIndice(Expresion indice) {
		this.indice = indice;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion nombre;
	private Expresion indice;
}

