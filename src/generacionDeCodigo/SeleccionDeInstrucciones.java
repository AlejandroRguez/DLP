package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;

import ast.Ambito;
import ast.Asignacion;
import ast.CampoStruct;
import ast.Definicion;
import ast.DefinicionFuncion;
import ast.DefinicionStruct;
import ast.DefinicionVariable;
import ast.Expresion;
import ast.If;
import ast.InvocacionSentencia;
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
import ast.While;
import visitor.DefaultVisitor;

public class SeleccionDeInstrucciones extends DefaultVisitor {

	private ValorVisitor valorVisitor;
	private DireccionVisitor direccionVisitor;
	
	public SeleccionDeInstrucciones(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;
		
		valorVisitor = new ValorVisitor(this);
		direccionVisitor = new DireccionVisitor(this);
		
		
	}

	//	class Program { List<Definicion> definiciones; }
	public Object visit(Program node, Object param) {

		genera("#source \"" + sourceFile + "\"");
		genera("call main");
		genera("halt");
		
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
		
		if(node.getAmbito().equals(Ambito.GLOBAL)){
			genera("#GLOBAL " + node.getNombre() + ":" + node.getTipo().getNombreMAPL());
		}

		if(node.getAmbito().equals(Ambito.LOCAL)){
			genera("#LOCAL " + node.getNombre() + ":" + node.getTipo().getNombreMAPL());
		}
		
		if(node.getAmbito().equals(Ambito.PARAMETRO)){
			genera("#PARAM " + node.getNombre() + ":" + node.getTipo().getNombreMAPL());
		}
		

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
		
		genera("#line " + node.getEnd().getLine());
		genera("#FUNC " + node.getNombre());
		genera(node.getNombre() + ":");
		
		if (node.getParametros() != null)
			for (DefinicionVariable child : node.getParametros())
				child.accept(this, param);

		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		
		if (node.getVariables() != null)
			for (DefinicionVariable child : node.getVariables())
				child.accept(this, param);
		
		genera("enter " + node.getTamañoLocales());

		if (node.getCuerpo() != null)
			for (Sentencia child : node.getCuerpo())
				child.accept(this, param);
		
		if(node.getTipo() == null)
			genera("ret 0," + node.getTamañoLocales() + "," + node.getTamañoParametros());
		
		return null;
	}

	//	class Asignacion { Expresion izquierda;  Expresion derecha; }
	public Object visit(Asignacion node, Object param) {
		genera("#line " + node.getEnd().getLine());
		// super.visit(node, param);

		if (node.getIzquierda() != null)
			node.getIzquierda().accept(direccionVisitor, param);

		if (node.getDerecha() != null)
			node.getDerecha().accept(valorVisitor, param);
		
		genera("store" , node.getDerecha().getTipo());

		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		
		genera("#line " + node.getEnd().getLine());
		
		// super.visit(node, param);

		if (node.getExpresion() != null){
			genera("#RET " + node.getContenedora().getTipo().getNombreMAPL());
			node.getExpresion().accept(valorVisitor, param);
			genera("ret " + node.getExpresion().getTipo().getSize()
					+ "," + node.getContenedora().getTamañoLocales()
					+ "," + node.getContenedora().getTamañoParametros());
			
		}
		else{
			genera("#RET VOID");
			genera("ret 0" + "," + node.getContenedora().getTamañoLocales()
					+ "," + node.getContenedora().getTamañoParametros());
		}
		
		return null;
	}

	//	class Print { Expresion expresion;  String sufijo; }
	public Object visit(Print node, Object param) {
		genera("#line " + node.getEnd().getLine());
		
		if(node.getSufijo() == null){
			node.getExpresion().accept(valorVisitor, param);
			genera("out", node.getExpresion().getTipo()); 
		}
		
		else if(node.getSufijo().equals("ln") && node.getExpresion() == null){
			genera("pushb 10");
			genera("outb");
		}
		
		else if(node.getSufijo().equals("ln") && node.getExpresion() != null){
			node.getExpresion().accept(valorVisitor, param);
			genera("out", node.getExpresion().getTipo()); 
			genera("pushb 10");
			genera("outb");
		}
		
		else if(node.getSufijo().equals("sp")){
			node.getExpresion().accept(valorVisitor, param);
			genera("out", node.getExpresion().getTipo()); 
			genera("pushb 32");
			genera("outb");
		}
		
		
		
		

		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		genera("#line " + node.getEnd().getLine());
		// super.visit(node, param);

		if (node.getExpresion() != null)
			node.getExpresion().accept(direccionVisitor, param);
		genera("in" , node.getExpresion().getTipo());
		genera("store" , node.getExpresion().getTipo());

		return null;
	}

	//	class If { Expresion condicion;  List<Sentencia> cuerpoIf;  List<Sentencia> cuerpoElse; }
	public Object visit(If node, Object param) {
		genera("#line " + node.getEnd().getLine());
		// super.visit(node, param);
		
		String elses = "ELSE" + getNumber(1);
		String finElses = "FINELSE" + getNumber(1);

		if (node.getCondicion() != null)
			node.getCondicion().accept(valorVisitor, param);
		
		genera("jz " + elses);

		if (node.getCuerpoIf() != null)
			for (Sentencia child : node.getCuerpoIf())
				child.accept(this, param);
		
		genera("jmp " + finElses);
		genera(elses + ":");
		
		if (node.getCuerpoElse() != null)
			for (Sentencia child : node.getCuerpoElse())
				child.accept(this, param);

		genera(finElses + ":");
		return null;
	}

	//	class While { Expresion condicion;  List<Sentencia> cuerpoWhile; }
	public Object visit(While node, Object param) {
		genera("#line " + node.getEnd().getLine());
		// super.visit(node, param);
		String whiles = "WHILE" + getNumber(1);
		String finWhiles = "FINWHILE" + getNumber(1);
		
		genera(whiles + ":");
		
		if (node.getCondicion() != null)
			node.getCondicion().accept(valorVisitor, param);
		
		genera("jz " + finWhiles);
		
		if (node.getCuerpoWhile() != null)
			for (Sentencia child : node.getCuerpoWhile())
				child.accept(this, param);
		
		genera("jmp " + whiles);
		genera(finWhiles + ":");

		return null;
	}

	//	class InvocacionSentencia { String nombre;  List<Expresion> argumentos; }
	public Object visit(InvocacionSentencia node, Object param) {
		genera("#line " + node.getEnd().getLine());
		// super.visit(node, param);

		if (node.getArgumentos() != null)
			for (Expresion child : node.getArgumentos())
				child.accept(valorVisitor, param);
		
		genera ("call " + node.getNombre());
		
		if(node.getDefinicion().getTipo() != null){
			genera("pop" , node.getDefinicion().getTipo());
		}

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

	// Método auxiliar recomendado -------------
	protected void genera(String instruccion) {
		writer.println(instruccion);
	}
	
	protected void genera(String instruccion, Tipo tipo) {
		genera(instruccion + tipo.getSufijo());
	}
	
	public DefaultVisitor getDireccionVisitor() {
		return direccionVisitor;
	}

	public DefaultVisitor getValorVisitor() {
		return valorVisitor;
	}
		
	private int getNumber(int i) {
		int temp = this.etiquetas;
		this.etiquetas += i;
		return temp;
	}
	
	private int etiquetas;
	private PrintWriter writer;
	private String sourceFile;
}
