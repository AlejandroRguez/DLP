/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	definicionStruct:definicion -> nombre:String  campos:campoStruct*

public class DefinicionStruct extends AbstractDefinicion {

	public DefinicionStruct(String nombre, List<CampoStruct> campos) {
		this.nombre = nombre;
		this.campos = campos;

		searchForPositions(campos);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public DefinicionStruct(Object nombre, Object campos) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.campos = (List<CampoStruct>) campos;

		searchForPositions(nombre, campos);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CampoStruct> getCampos() {
		return campos;
	}
	public void setCampos(List<CampoStruct> campos) {
		this.campos = campos;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<CampoStruct> campos;
}

