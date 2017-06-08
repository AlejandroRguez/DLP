/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	if:sentencia -> condicion:expresion  cuerpoIf:sentencia*  cuerpoElse:sentencia*

public class If extends AbstractSentencia {

	public If(Expresion condicion, List<Sentencia> cuerpoIf, List<Sentencia> cuerpoElse) {
		this.condicion = condicion;
		this.cuerpoIf = cuerpoIf;
		this.cuerpoElse = cuerpoElse;

		searchForPositions(condicion, cuerpoIf, cuerpoElse);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public If(Object condicion, Object cuerpoIf, Object cuerpoElse) {
		this.condicion = (Expresion) condicion;
		this.cuerpoIf = (List<Sentencia>) cuerpoIf;
		this.cuerpoElse = (List<Sentencia>) cuerpoElse;

		searchForPositions(condicion, cuerpoIf, cuerpoElse);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getCondicion() {
		return condicion;
	}
	public void setCondicion(Expresion condicion) {
		this.condicion = condicion;
	}

	public List<Sentencia> getCuerpoIf() {
		return cuerpoIf;
	}
	public void setCuerpoIf(List<Sentencia> cuerpoIf) {
		this.cuerpoIf = cuerpoIf;
	}

	public List<Sentencia> getCuerpoElse() {
		return cuerpoElse;
	}
	public void setCuerpoElse(List<Sentencia> cuerpoElse) {
		this.cuerpoElse = cuerpoElse;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion condicion;
	private List<Sentencia> cuerpoIf;
	private List<Sentencia> cuerpoElse;
}

