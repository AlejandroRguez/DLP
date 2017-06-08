package generacionDeCodigo;

import ast.Acceso;
import ast.AccesoArray;
import ast.Ambito;
import ast.CampoStruct;
import ast.DefinicionStruct;
import ast.TipoArray;
import ast.TipoStruct;
import ast.Variable;
import visitor.DefaultVisitor;

public class DireccionVisitor extends DefaultVisitor {
	
	
	
	private SeleccionDeInstrucciones seleccion;
	
	public DireccionVisitor(SeleccionDeInstrucciones seleccion) {
		this.seleccion = seleccion;
		
	
		//valorVisitor = new ValorVisitor(writer);
	}
	
	//	class AccesoArray { Expresion nombre;  Expresion indice; }
	public Object visit(AccesoArray node, Object param) {

		// super.visit(node, param);

		if (node.getNombre() != null)
			node.getNombre().accept(this, param);

		if (node.getIndice() != null)
			node.getIndice().accept(seleccion.getValorVisitor(), param);
		
		seleccion.genera("push " + ((TipoArray) node.getNombre().getTipo()).getTipo().getSize());
		seleccion.genera("mul");
		seleccion.genera("add");


		return null;
	}

	//	class Acceso { Expresion expresion;  String propiedad; }
	public Object visit(Acceso node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		seleccion.genera("push " + buscarCampo(node.getPropiedad(), ((TipoStruct) node.getExpresion().getTipo()).getDefinicion()).getDireccion());
		seleccion.genera("add");

		return null;
	}

	//	class Variable { String valor; }
	public Object visit(Variable node, Object param) {
		if (node.getDefinicion().getAmbito().equals(Ambito.GLOBAL)){
			seleccion.genera("pusha " + node.getDefinicion().getDireccion());
		}
		if (node.getDefinicion().getAmbito().equals(Ambito.LOCAL)){
			seleccion.genera("pusha BP");
			seleccion.genera("push " + node.getDefinicion().getDireccion());
			seleccion.genera("sub");
		}
		if (node.getDefinicion().getAmbito().equals(Ambito.PARAMETRO)){
			seleccion.genera("pusha BP");
			seleccion.genera("push " + node.getDefinicion().getDireccion());
			seleccion.genera("add");
		}
		return null;
	}
	
	private CampoStruct buscarCampo(String propiedad, DefinicionStruct definicion){
		for(CampoStruct c: definicion.getCampos()){
			if(propiedad.equals(c.getNombre())){
				return c;
			}
		}
		return null;
	}


}
