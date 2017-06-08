/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	tipoReal:tipo -> 

public class TipoReal extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	@Override
	public int getSize() {
		return 4;
	}
	
	public String getSufijo(){
		return "f";
	}
	
	public String getNombreMAPL(){
		return "real";
	}
	
	

}

