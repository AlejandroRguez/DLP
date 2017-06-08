/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	tipoArray:tipo -> tamanyo:int  tipo:tipo

public class TipoArray extends AbstractTipo {

	public TipoArray(int tamanyo, Tipo tipo) {
		this.tamanyo = tamanyo;
		this.tipo = tipo;

		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public TipoArray(Object tamanyo, Object tipo) {
		this.tamanyo = (tamanyo instanceof Token) ? Integer.parseInt(((Token)tamanyo).getLexeme()) : (Integer) tamanyo;
		this.tipo = (Tipo) tipo;

		searchForPositions(tamanyo, tipo);	// Obtener linea/columna a partir de los hijos
	}

	public int getTamanyo() {
		return tamanyo;
	}
	public void setTamanyo(int tamanyo) {
		this.tamanyo = tamanyo;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public int getSize() {
		return getTamanyo() * getTipo().getSize();
	}
	
	public String getSufijo() {
		return null;
	}
	
	public String getNombreMAPL(){
		return this.getTamanyo() + " * " + this.getTipo().getNombreMAPL() ;
	}


	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}
	

	private int tamanyo;
	private Tipo tipo;
	
	
}

