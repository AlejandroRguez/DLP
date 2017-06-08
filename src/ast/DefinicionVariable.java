/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	definicionVariable:definicion -> nombre:String  tipo:tipo  ambito:Ambito

public class DefinicionVariable extends AbstractDefinicion {

	public DefinicionVariable(String nombre, Tipo tipo, Ambito ambito) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.ambito = ambito;

		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public DefinicionVariable(Object nombre, Object tipo, Object ambito) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.tipo = (Tipo) tipo;
		this.ambito = (Ambito) ambito;

		searchForPositions(nombre, tipo, ambito);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Ambito getAmbito() {
		return ambito;
	}
	public void setAmbito(Ambito ambito) {
		this.ambito = ambito;
	}
	
	public void setDireccion(int direccion){
		this.direccion = direccion;
	}
	
	public int getDireccion(){
		return direccion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private Tipo tipo;
	private Ambito ambito;
	private int direccion;
}

