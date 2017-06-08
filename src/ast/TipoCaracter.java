/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	tipoCaracter:tipo -> 

public class TipoCaracter extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	@Override
	public int getSize() {
		return 1;
	}
	
	public String getSufijo(){
		return "b";
	}
	
	public String getNombreMAPL(){
		return "char";
	}
	
	

}

