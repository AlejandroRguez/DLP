/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	program -> definiciones:definicion*

public class Program extends AbstractTraceable implements AST {

	public Program(List<Definicion> definiciones) {
		this.definiciones = definiciones;

		searchForPositions(definiciones);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public Program(Object definiciones) {
		this.definiciones = (List<Definicion>) definiciones;

		searchForPositions(definiciones);	// Obtener linea/columna a partir de los hijos
	}

	public List<Definicion> getDefiniciones() {
		return definiciones;
	}
	public void setDefiniciones(List<Definicion> definiciones) {
		this.definiciones = definiciones;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private List<Definicion> definiciones;
}

