/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	tipoStruct:tipo -> nombre:String

public class TipoStruct extends AbstractTipo {

	public TipoStruct(String nombre) {
		this.nombre = nombre;
	}

	public TipoStruct(Object nombre) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;

		searchForPositions(nombre);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public DefinicionStruct getDefinicion() {
		return definicion;
	}

	public void setDefinicion(DefinicionStruct definicion) {
		this.definicion = definicion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	
	@Override
	public int getSize() {
		int tamaño = 0;
		for (CampoStruct c : getDefinicion().getCampos()){
			tamaño += c.getTipo().getSize();
		}
		return tamaño;
	}
	
	public String getSufijo() {
		return null;
	}
	
	public String getNombreMAPL(){
		StringBuilder s = new StringBuilder();
		for (CampoStruct c: this.getDefinicion().getCampos())
			s.append(c.getNombre() + ":" + c.getTipo().getNombreMAPL() + "\n");
		
		return "{" + s.toString() + "}";
	}

	private String nombre;
	private DefinicionStruct definicion;
	
}

