/**
 * @generated VGen 1.3.3
 */

package ast;

public interface Expresion extends AST {
	
	void setTipo(Tipo tipo);
	Tipo getTipo();
	
	boolean isModificable();
	void setModificable(boolean modificable);

}

