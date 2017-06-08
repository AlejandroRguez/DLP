/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	while:sentencia -> condicion:expresion  cuerpoWhile:sentencia*

public class While extends AbstractSentencia {

	public While(Expresion condicion, List<Sentencia> cuerpoWhile) {
		this.condicion = condicion;
		this.cuerpoWhile = cuerpoWhile;

		searchForPositions(condicion, cuerpoWhile);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public While(Object condicion, Object cuerpoWhile) {
		this.condicion = (Expresion) condicion;
		this.cuerpoWhile = (List<Sentencia>) cuerpoWhile;

		searchForPositions(condicion, cuerpoWhile);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getCondicion() {
		return condicion;
	}
	public void setCondicion(Expresion condicion) {
		this.condicion = condicion;
	}

	public List<Sentencia> getCuerpoWhile() {
		return cuerpoWhile;
	}
	public void setCuerpoWhile(List<Sentencia> cuerpoWhile) {
		this.cuerpoWhile = cuerpoWhile;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion condicion;
	private List<Sentencia> cuerpoWhile;
}

