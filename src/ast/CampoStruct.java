/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	campoStruct -> nombre:String  tipo:tipo

public class CampoStruct extends AbstractTraceable implements AST {

	public CampoStruct(String nombre, Tipo tipo) {
		this.nombre = nombre;
		this.tipo = tipo;

		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public CampoStruct(Object nombre, Object tipo) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.tipo = (Tipo) tipo;

		searchForPositions(nombre, tipo);	// Obtener linea/columna a partir de los hijos
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
	
	public DefinicionStruct getDefinicion() {
		return definicion;
	}

	public void setDefinicion(DefinicionStruct definicion) {
		this.definicion = definicion;
	}
	
	public int getDireccion() {
		return direccion;
	}

	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}
	
	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private Tipo tipo;
	private DefinicionStruct definicion;
	private int direccion;
	

		
		
	
}

