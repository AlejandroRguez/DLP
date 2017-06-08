package semantico;

import java.util.ArrayList;
import java.util.List;

import main.GestorErrores;
import visitor.DefaultVisitor;
import ast.Acceso;
import ast.AccesoArray;
import ast.Asignacion;
import ast.CampoStruct;
import ast.Caracter;
import ast.Cast;
import ast.Definicion;
import ast.DefinicionFuncion;
import ast.DefinicionStruct;
import ast.DefinicionVariable;
import ast.Distinto;
import ast.Expresion;
import ast.If;
import ast.InvocacionExpresion;
import ast.InvocacionSentencia;
import ast.LiteralEntero;
import ast.LiteralReal;
import ast.OperacionAritmetica;
import ast.OperacionLogica;
import ast.OperacionRelacional;
import ast.Position;
import ast.Print;
import ast.Program;
import ast.Read;
import ast.Return;
import ast.Sentencia;
import ast.Tipo;
import ast.TipoArray;
import ast.TipoCaracter;
import ast.TipoEntero;
import ast.TipoReal;
import ast.TipoStruct;
import ast.Variable;
import ast.While;

public class ComprobacionDeTipos extends DefaultVisitor {
	
	private List<Object> tiposSimples = new ArrayList<Object>();
	private List<Object> tiposNumericos = new ArrayList<Object>();

	public ComprobacionDeTipos(GestorErrores gestor) {
		this.gestorErrores = gestor;
		tiposSimples.add(new TipoEntero().getClass());
		tiposSimples.add(new TipoReal().getClass());
		tiposSimples.add(new TipoCaracter().getClass());
		tiposNumericos.add(new TipoEntero().getClass());
		tiposNumericos.add(new TipoReal().getClass());
	}


	//	class Program { List<Definicion> definiciones; }
	public Object visit(Program node, Object param) {

		// super.visit(node, param);

		if (node.getDefiniciones() != null)
			for (Definicion child : node.getDefiniciones())
				child.accept(this, param);

		return null;
	}

	//	class CampoStruct { String nombre;  Tipo tipo; }
	public Object visit(CampoStruct node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	//	class DefinicionVariable { String nombre;  Tipo tipo;  Ambito ambito; }
	public Object visit(DefinicionVariable node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	//	class DefinicionStruct { String nombre;  List<CampoStruct> campos; }
	public Object visit(DefinicionStruct node, Object param) {

		// super.visit(node, param);

		if (node.getCampos() != null)
			for (CampoStruct child : node.getCampos())
				child.accept(this, param);

		return null;
	}

	//	class DefinicionFuncion { String nombre;  List<DefinicionVariable> parametros;  Tipo tipo;  List<DefinicionVariable> variables;  List<Sentencia> cuerpo; }
	public Object visit(DefinicionFuncion node, Object param) {

		// super.visit(node, param);

		if (node.getParametros() != null)
			for (DefinicionVariable child : node.getParametros()){
				child.accept(this, param);
				predicado(tiposSimples.contains(child.getTipo().getClass()), "Los parámetros deben ser de tipo simple", node.getStart());
			}

		if (node.getTipo() != null){
			node.getTipo().accept(this, param);
			predicado(tiposSimples.contains(node.getTipo().getClass()), "El tipo de retorno de una función ha de ser un tipo simple", node.getStart());
		}
		
		if (node.getVariables() != null)
			for (DefinicionVariable child : node.getVariables())
				child.accept(this, param);
			

		if (node.getCuerpo() != null)
			for (Sentencia child : node.getCuerpo()){
				child.setContenedora(node);
				child.accept(this, param);
			}
		
		

		return null;
	}

	//	class OperacionAritmetica { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(OperacionAritmetica node, Object param) {

		// super.visit(node, param);

		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		
		predicado(isTipoNumerico(node.getDerecha()), "Los operandos han de ser de tipo numérico", node.getStart());
		predicado(mismoTipo(node.getDerecha(), node.getIzquierda()), "Los operandos han de ser del mismo tipo", node.getStart());
		node.setTipo(node.getDerecha().getTipo());
		node.setModificable(false);

		
		return null;
	}

	//	class OperacionRelacional { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(OperacionRelacional node, Object param) {

		// super.visit(node, param);

		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		
		predicado(isTipoNumerico(node.getDerecha()), "Los operandos han de ser de tipo numérico", node.getStart());
		predicado(mismoTipo(node.getDerecha(), node.getIzquierda()), "Los operandos han de ser del mismo tipo", node.getStart());
		node.setTipo(new TipoEntero());
		node.setModificable(false);

		return null;
	}

	//	class OperacionLogica { Expresion izquierda;  String operador;  Expresion derecha; }
	public Object visit(OperacionLogica node, Object param) {

		// super.visit(node, param);

		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		
		predicado(node.getDerecha().getTipo().getClass().equals(new TipoEntero().getClass()), "Los operandos han de ser de tipo entero", node.getStart());
		predicado(mismoTipo(node.getDerecha(), node.getIzquierda()), "Los operandos han de ser del mismo tipo", node.getStart());
		node.setTipo(node.getDerecha().getTipo());
		node.setModificable(false);

		return null;
	}

	//	class Distinto { Expresion expresion; }
	public Object visit(Distinto node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		predicado(node.getExpresion().getTipo().getClass().equals(new TipoEntero().getClass()), "La expresión debe ser de tipo entero", node.getStart());
		node.setTipo(new TipoEntero());
		node.setModificable(false);
		return null;
	}

	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
	
		predicado(noMismoTipo(node.getTipo(), node.getExpresion()), "El tipo de conversión ha de ser distinto del tipo original", node.getStart());
		predicado(tiposSimples.contains(node.getTipo().getClass()), "El tipo de conversión ha de ser un tipo simple", node.getStart());
		predicado(isTipoSimple(node.getExpresion()), "El elemento a convertir ha de ser de tipo simple", node.getStart());	
		node.setModificable(false);

		return null;
	}

	//	class InvocacionExpresion { String nombre;  List<Expresion> argumentos; }
	public Object visit(InvocacionExpresion node, Object param) {

		// super.visit(node, param);

		if (node.getArgumentos() != null)
			for (Expresion child : node.getArgumentos())
				child.accept(this, param);
		
		
		predicado(comprobarLongitudListas(node.getArgumentos(), node.getDefinicion().getParametros()), "El número de parámetros no coincide", node.getStart());	
		predicado(comprobarTipoParametros(node.getArgumentos(), node.getDefinicion().getParametros()), "El tipo de los parámetros no coincide", node.getStart());	
		predicado(node.getDefinicion().getTipo() != null , "Debe tener un tipo de retorno" , node.getStart());
		node.setTipo(node.getDefinicion().getTipo());
		node.setModificable(false);
		

		return null;
	}

	//	class AccesoArray { Expresion nombre;  Expresion indice; }
	public Object visit(AccesoArray node, Object param) {

		// super.visit(node, param);

		if (node.getNombre() != null)
			node.getNombre().accept(this, param);

		if (node.getIndice() != null)
			node.getIndice().accept(this, param);
		
		predicado(node.getNombre().getTipo() instanceof TipoArray,
			"El elemento al que se intenta acceder no es un array", node.getStart());
		predicado(node.getIndice().getTipo().getClass().equals(new TipoEntero().getClass()), "El índice debe ser un tipo entero", node.getStart());
		if(node.getNombre().getTipo() instanceof TipoArray)
			node.setTipo(((TipoArray) node.getNombre().getTipo()).getTipo());
		node.setModificable(true);
		return null;
	}

	//	class Acceso { Expresion expresion;  String propiedad; }
	public Object visit(Acceso node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		
		predicado(node.getExpresion().getTipo().getClass().equals(new TipoStruct(null).getClass()),
				"El elemento al que se intenta acceder no es una estructura", node.getStart());
		if(node.getExpresion().getTipo() instanceof TipoStruct){
			predicado(buscarCampo(node.getPropiedad(), ((TipoStruct) node.getExpresion().getTipo()).getDefinicion()),
					"El campo no existe o no está definido", node.getStart());
			node.setTipo(obtenerTipoCampo(node.getPropiedad(), ((TipoStruct) node.getExpresion().getTipo()).getDefinicion()));
		}
		node.setModificable(true);
		return null;
	}

	//	class Variable { String valor; }
	public Object visit(Variable node, Object param) {
		node.setTipo(node.getDefinicion().getTipo());
		node.setModificable(true);
		return null;
	}

	//	class LiteralEntero { String valor; }
	public Object visit(LiteralEntero node, Object param) {
		node.setTipo(new TipoEntero());
		node.setModificable(false);
		return null;
	}

	//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		node.setTipo(new TipoReal());
		node.setModificable(false);
		return null;
	}

	//	class Caracter { String valor; }
	public Object visit(Caracter node, Object param) {
		node.setTipo(new TipoCaracter());
		node.setModificable(false);
		return null;
	}

	//	class Asignacion { Expresion izquierda;  Expresion derecha; }
	public Object visit(Asignacion node, Object param) {

		// super.visit(node, param);

		if (node.getIzquierda() != null)
			node.getIzquierda().accept(this, param);

		if (node.getDerecha() != null)
			node.getDerecha().accept(this, param);
		
		predicado(node.getIzquierda().isModificable(), "La parte izquierda de una asignación debe ser modificable" , node.getStart());
		predicado(mismoTipo(node.getDerecha(), node.getIzquierda()), "Los elementos son de distinto tipo" , node.getStart());
		predicado(isTipoSimple(node.getIzquierda()), "Debe ser tipo simple" , node.getStart());
		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {

		// super.visit(node, param);
		if(node.getContenedora().getTipo() == null){
			predicado(node.getExpresion() == null, "No debe haber tipo de retorno", node.getStart());
		}
		
		if(node.getContenedora().getTipo() != null){
			if (node.getExpresion() != null){
				node.getExpresion().accept(this, param);
				predicado(node.getExpresion().getTipo().getClass().equals(node.getContenedora().getTipo().getClass())
						, "El tipo de retorno no coincide con el tipo de la función" , node.getStart());
			}else{
				predicado (node.getExpresion() != null, "Debe haber un valor de retorno" , node.getStart());

			}
		}
		

		return null;
	}

	//	class Print { Expresion expresion;  String sufijo; }
	public Object visit(Print node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null){
			node.getExpresion().accept(this, param);
			predicado(isTipoSimple(node.getExpresion()), "Debe ser tipo simple" , node.getStart());
		}
		


		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {

		// super.visit(node, param);

		if (node.getExpresion() != null){
			node.getExpresion().accept(this, param);
			predicado(isTipoSimple(node.getExpresion()), "Debe ser tipo simple" , node.getStart());
			predicado(node.getExpresion().isModificable(), "Debe ser modificable" , node.getStart());
		}

		return null;
	}

	//	class If { Expresion condicion;  List<Sentencia> cuerpoIf;  List<Sentencia> cuerpoElse; }
	public Object visit(If node, Object param) {

		// super.visit(node, param);

		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);

		if (node.getCuerpoIf() != null)
			for (Sentencia child : node.getCuerpoIf()){
				child.setContenedora(node.getContenedora());
				child.accept(this, param);
			}

		if (node.getCuerpoElse() != null)
			for (Sentencia child : node.getCuerpoElse()){
				child.setContenedora(node.getContenedora());
				child.accept(this, param);
			}
		predicado(node.getCondicion().getTipo().getClass().equals(new TipoEntero().getClass())
				,"La condición debe ser un tipo entero", node.getStart());
		return null;
	}

	//	class While { Expresion condicion;  List<Sentencia> cuerpoWhile; }
	public Object visit(While node, Object param) {

		// super.visit(node, param);

		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);

		if (node.getCuerpoWhile() != null)
			for (Sentencia child : node.getCuerpoWhile()){
					child.setContenedora(node.getContenedora());
				child.accept(this, param);
			}
		
		predicado(node.getCondicion().getTipo().getClass().equals(new TipoEntero().getClass())
				,"La condición debe ser un tipo entero", node.getStart());

		return null;
	}

	//	class InvocacionSentencia { String nombre;  List<Expresion> argumentos; }
	public Object visit(InvocacionSentencia node, Object param) {

		// super.visit(node, param);

		if (node.getArgumentos() != null)
			for (Expresion child : node.getArgumentos())
				child.accept(this, param);
		predicado(comprobarLongitudListas(node.getArgumentos(), node.getDefinicion().getParametros()), "El número de parámetros no coincide", node.getStart());	
		predicado(comprobarTipoParametros(node.getArgumentos(), node.getDefinicion().getParametros()), "El tipo de los parámetros no coincide", node.getStart());	
		//System.out.println(node.getDefinicion().getNombre().toString());
		return null;
	}

	//	class TipoEntero {  }
	public Object visit(TipoEntero node, Object param) {
		return null;
	}

	//	class TipoReal {  }
	public Object visit(TipoReal node, Object param) {
		return null;
	}

	//	class TipoCaracter {  }
	public Object visit(TipoCaracter node, Object param) {
		return null;
	}

	//	class TipoArray { int tamanyo;  Tipo tipo; }
	public Object visit(TipoArray node, Object param) {

		// super.visit(node, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);

		return null;
	}

	//	class TipoStruct { String nombre; }
	public Object visit(TipoStruct node, Object param) {
		return null;
	}

	
	/**
	 * Método auxiliar opcional para ayudar a implementar los predicados de la Gramática Atribuida.
	 * 
	 * Ejemplo de uso (suponiendo implementado el método "esPrimitivo"):
	 * 	predicado(esPrimitivo(expr.tipo), "La expresión debe ser de un tipo primitivo", expr.getStart());
	 * 	predicado(esPrimitivo(expr.tipo), "La expresión debe ser de un tipo primitivo", null);
	 * 
	 * NOTA: El método getStart() indica la linea/columna del fichero fuente de donde se leyó el nodo.
	 * Si se usa VGen dicho método será generado en todos los nodos AST. Si no se quiere usar getStart() se puede pasar null.
	 * 
	 * @param condicion Debe cumplirse para que no se produzca un error
	 * @param mensajeError Se imprime si no se cumple la condición
	 * @param posicionError Fila y columna del fichero donde se ha producido el error. Es opcional (acepta null)
	 */
	private void predicado(boolean condicion, String mensajeError, Position posicionError) {
		if (!condicion)
			gestorErrores.error("Comprobación de tipos", mensajeError, posicionError);
	}
	
	private boolean mismoTipo (Expresion uno, Expresion dos){
		if(uno.getTipo().getClass().equals(dos.getTipo().getClass()))
			return true;
		return false;
	}
	
	private boolean noMismoTipo (Tipo uno, Expresion dos){
		if(!(uno.getClass().equals(dos.getTipo().getClass())))
			return true;
		return false;
	}
	
	private boolean isTipoSimple(Expresion expresion){
		if(expresion.getTipo() == null)
			return false;
		if (tiposSimples.contains(expresion.getTipo().getClass()))
			return true;
		return false;
	}
	private boolean isTipoNumerico(Expresion expresion){
		if(expresion.getTipo() == null)
			return false;
		if (tiposNumericos.contains(expresion.getTipo().getClass()))
			return true;
		return false;
	}
	
	private boolean comprobarLongitudListas(List<Expresion> uno, List<DefinicionVariable> dos){
		if(uno.size() == dos.size())
			return true;
		return false;
	}
	
	private boolean comprobarTipoParametros(List<Expresion> uno, List<DefinicionVariable> dos){
		if(uno.isEmpty() && dos.isEmpty()){
			return true;
		}
		if(comprobarLongitudListas(uno, dos)){
			for(int i=0; i<uno.size(); i++){
				if(!(uno.get(i).getTipo().getClass().equals
						(dos.get(i).getTipo().getClass())))
					return false;
			}
		}
		return true;
	}
	
	private boolean buscarCampo(String propiedad, DefinicionStruct definicion){
		for(CampoStruct c: definicion.getCampos()){
			if(propiedad.equals(c.getNombre())){
				return true;
			}
		}
		return false;
	}
	
	private Tipo obtenerTipoCampo(String propiedad, DefinicionStruct definicion){
		for(CampoStruct c: definicion.getCampos()){
			if(propiedad.equals(c.getNombre())){
				return c.getTipo();
			}
		}
		return null;
	}
	
	
	private GestorErrores gestorErrores;
	
	//Mirar cast de la línea 90 y no detecta error cuanto el tipo de los parámetros no coincide con el de la definicion
}
