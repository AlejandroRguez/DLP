/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	definicionFuncion:definicion -> nombre:String  parametros:definicionVariable*  tipo:tipo  variables:definicionVariable*  cuerpo:sentencia*

public class DefinicionFuncion extends AbstractDefinicion {

	public DefinicionFuncion(String nombre, List<DefinicionVariable> parametros, Tipo tipo, List<DefinicionVariable> variables, List<Sentencia> cuerpo) {
		this.nombre = nombre;
		this.parametros = parametros;
		this.tipo = tipo;
		this.variables = variables;
		this.cuerpo = cuerpo;

		searchForPositions(parametros, tipo, variables, cuerpo);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public DefinicionFuncion(Object nombre, Object parametros, Object tipo, Object variables, Object cuerpo) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.parametros = (List<DefinicionVariable>) parametros;
		this.tipo = (Tipo) tipo;
		this.variables = (List<DefinicionVariable>) variables;
		this.cuerpo = (List<Sentencia>) cuerpo;

		searchForPositions(nombre, parametros, tipo, variables, cuerpo);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DefinicionVariable> getParametros() {
		return parametros;
	}
	public void setParametros(List<DefinicionVariable> parametros) {
		this.parametros = parametros;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public List<DefinicionVariable> getVariables() {
		return variables;
	}
	public void setVariables(List<DefinicionVariable> variables) {
		this.variables = variables;
	}

	public List<Sentencia> getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(List<Sentencia> cuerpo) {
		this.cuerpo = cuerpo;
	}
	
	public int getTamañoLocales(){
		tamañoLocales = 0;
		for(DefinicionVariable def : this.getVariables()){
			tamañoLocales += def.getTipo().getSize();
		}
		return tamañoLocales;
	}
	
	public int getTamañoParametros(){
		tamañoParametros = 0;
		for(DefinicionVariable def : this.getParametros()){
			tamañoParametros += def.getTipo().getSize();
		}
		return tamañoParametros;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<DefinicionVariable> parametros;
	private Tipo tipo;
	private List<DefinicionVariable> variables;
	private List<Sentencia> cuerpo;
	private int tamañoLocales;
	private int tamañoParametros;
}

