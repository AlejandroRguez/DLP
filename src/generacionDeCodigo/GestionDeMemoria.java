package generacionDeCodigo;

import visitor.DefaultVisitor;
import ast.CampoStruct;
import ast.Definicion;
import ast.DefinicionFuncion;
import ast.DefinicionStruct;
import ast.DefinicionVariable;
import ast.Program;
import ast.Sentencia;

/** 
 * Clase encargada de asignar direcciones a las variables 
 */
public class GestionDeMemoria extends DefaultVisitor {
	

//	class Program { List<Definicion> definiciones; }
	public Object visit(Program node, Object param) {

		// super.visit(node, param);
		int direccion = 0;
		if (node.getDefiniciones() != null)
			for (Definicion child : node.getDefiniciones()){
				child.accept(this, param);
				if(child instanceof DefinicionVariable){
					((DefinicionVariable) child).setDireccion(direccion);
					direccion += ((DefinicionVariable) child).getTipo().getSize();
				}
			}
			

		return null;
	}

	//	class DefinicionStruct { String nombre;  List<CampoStruct> campos; }
	public Object visit(DefinicionStruct node, Object param) {

		// super.visit(node, param);
		int direccion = 0;
		if (node.getCampos() != null)
			for (CampoStruct child : node.getCampos()){
				child.accept(this, param);
				child.setDireccion(direccion);
				direccion += child.getTipo().getSize();
			}
		return null;
	}

	//	class DefinicionFuncion { String nombre;  List<DefinicionVariable> parametros;  Tipo tipo;  List<DefinicionVariable> variables;  List<Sentencia> cuerpo; }
	public Object visit(DefinicionFuncion node, Object param) {

		// super.visit(node, param);
		int direccionParametros = 4;
		if (node.getParametros() != null){
			for (int i = node.getParametros().size() - 1; i>=0; i--){
				node.getParametros().get(i).accept(this, param);
				node.getParametros().get(i).setDireccion(direccionParametros);
				direccionParametros += node.getParametros().get(i).getTipo().getSize();
				}
		}

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		int direccionLocales = 0;
		if (node.getVariables() != null)
			for (DefinicionVariable child : node.getVariables()){
				child.accept(this, param);
				child.setDireccion(direccionLocales + child.getTipo().getSize());
				direccionLocales += child.getTipo().getSize();
			}

		if (node.getCuerpo() != null)
			for (Sentencia child : node.getCuerpo())
				child.accept(this, param);

		return null;
	}





}
