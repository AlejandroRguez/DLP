package visitor;


	import ast.Acceso;
	import ast.AccesoArray;
	import ast.Ambito;
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
	import ast.Print;
	import ast.Program;
	import ast.Read;
	import ast.Return;
	import ast.Sentencia;
	import ast.TipoArray;
	import ast.TipoCaracter;
	import ast.TipoEntero;
	import ast.TipoReal;
	import ast.TipoStruct;
	import ast.Variable;
	import ast.While;

	public class PrintVisitor extends DefaultVisitor {
		
//		class Program { List<Definicion> definiciones; }
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
			System.out.print("\t");
			System.out.print(node.getNombre() + ":");
			if (node.getTipo() != null)
				node.getTipo().accept(this, param);
			System.out.println(";");
			return null;
		}

		//	class DefinicionVariable { String nombre;  Tipo tipo;  Ambito ambito; }
		public Object visit(DefinicionVariable node, Object param) {

			// super.visit(node, param);

			System.out.print("var " + node.getNombre() + ":");
			if (node.getTipo() != null)
				node.getTipo().accept(this, param);
			if(node.getAmbito().equals(Ambito.PARAMETRO))
				System.out.print(",");
			else 
				System.out.println(";");
			return null;
		}

		//	class DefinicionStruct { String nombre;  List<CampoStruct> campos; }
		public Object visit(DefinicionStruct node, Object param) {

			// super.visit(node, param);
			System.out.println();
			System.out.println("struct " + node.getNombre() + "{");
			if (node.getCampos() != null)
				for (CampoStruct child : node.getCampos())
					child.accept(this, param);
			System.out.println("};");
			return null;
		}

		//	class DefinicionFuncion { String nombre;  List<DefinicionVariable> parametros;  Tipo tipo;  List<DefinicionVariable> variables;  List<Sentencia> cuerpo; }
		public Object visit(DefinicionFuncion node, Object param) {

			// super.visit(node, param);
			System.out.print(node.getNombre() + "(");
			if (node.getParametros() != null)
				for (DefinicionVariable child : node.getParametros())
					child.accept(this, param);
			System.out.print("):");
			if (node.getTipo() != null)
				node.getTipo().accept(this, param);
			System.out.println("{");
			if (node.getVariables() != null)
				for (DefinicionVariable child : node.getVariables()){
					System.out.print("\t");
					child.accept(this, param);
				}
			if (node.getCuerpo() != null)
				for (Sentencia child : node.getCuerpo()){
					System.out.print("\t");
					child.accept(this, param);
				}
			System.out.println("}");
			return null;
		}

		//	class OperacionAritmetica { Expresion izquierda;  String operador;  Expresion derecha; }
		public Object visit(OperacionAritmetica node, Object param) {

			// super.visit(node, param);

			if (node.getIzquierda() != null)
				node.getIzquierda().accept(this, param);
			System.out.print(" " + node.getOperador() + " ");
			if (node.getDerecha() != null)
				node.getDerecha().accept(this, param);

			return null;
		}

		//	class OperacionRelacional { Expresion izquierda;  String operador;  Expresion derecha; }
		public Object visit(OperacionRelacional node, Object param) {

			// super.visit(node, param);

			if (node.getIzquierda() != null)
				node.getIzquierda().accept(this, param);
			System.out.print(" " + node.getOperador() + " ");
			if (node.getDerecha() != null)
				node.getDerecha().accept(this, param);

			return null;
		}

		//	class OperacionLogica { Expresion izquierda;  String operador;  Expresion derecha; }
		public Object visit(OperacionLogica node, Object param) {

			// super.visit(node, param);

			if (node.getIzquierda() != null)
				node.getIzquierda().accept(this, param);
			System.out.print(" " + node.getOperador() + " ");
			if (node.getDerecha() != null)
				node.getDerecha().accept(this, param);

			return null;
		}

		//	class Distinto { Expresion expresion; }
		public Object visit(Distinto node, Object param) {

			// super.visit(node, param);
			System.out.print("!");
			if (node.getExpresion() != null)
				node.getExpresion().accept(this, param);

			return null;
		}

		//	class Cast { Tipo tipo;  Expresion expresion; }
		public Object visit(Cast node, Object param) {

			// super.visit(node, param);
			System.out.print("cast<");
			if (node.getTipo() != null)
				node.getTipo().accept(this, param);
			System.out.print(">");
			if (node.getExpresion() != null)
				node.getExpresion().accept(this, param);

			return null;
		}

		//	class InvocacionExpresion { String nombre;  List<Expresion> argumentos; }
		public Object visit(InvocacionExpresion node, Object param) {

			// super.visit(node, param);
			System.out.print(node.getNombre() + "(");
			if (node.getArgumentos() != null)
				for (Expresion child : node.getArgumentos())
					child.accept(this, param);
			System.out.print(")");

			return null;
		}

		//	class AccesoArray { Expresion nombre;  Expresion indice; }
		public Object visit(AccesoArray node, Object param) {

			// super.visit(node, param);

			if (node.getNombre() != null)
				node.getNombre().accept(this, param);
			System.out.print("[");
			if (node.getIndice() != null)
				node.getIndice().accept(this, param);
			System.out.print("]");
			return null;
		}

		//	class Acceso { Expresion expresion;  String propiedad; }
		public Object visit(Acceso node, Object param) {

			// super.visit(node, param);

			if (node.getExpresion() != null)
				node.getExpresion().accept(this, param);
			System.out.print("." + node.getPropiedad());
			return null;
		}

		//	class Variable { String valor; }
		public Object visit(Variable node, Object param) {
			System.out.print(node.getValor());
			return null;
		}

		//	class LiteralEntero { String valor; }
		public Object visit(LiteralEntero node, Object param) {
			System.out.print(node.getValor());
			return null;
		}

		//	class LiteralReal { String valor; }
		public Object visit(LiteralReal node, Object param) {
			System.out.print(node.getValor());
			return null;
		}

		//	class Caracter { String valor; }
		public Object visit(Caracter node, Object param) {
			System.out.print(node.getValor());
			return null;
		}

		//	class Asignacion { Expresion izquierda;  Expresion derecha; }
		public Object visit(Asignacion node, Object param) {

			// super.visit(node, param);
			if (node.getIzquierda() != null)
				node.getIzquierda().accept(this, param);
			System.out.print(" = ");
			if (node.getDerecha() != null)
				node.getDerecha().accept(this, param);
			System.out.println(";");
			return null;
		}

		//	class Return { Expresion expresion; }
		public Object visit(Return node, Object param) {

			// super.visit(node, param);
			System.out.print("return ");
			if (node.getExpresion() != null)
				node.getExpresion().accept(this, param);
			System.out.println(";");
			return null;
		}

		//	class Print { Expresion expresion;  String sufijo; }
		public Object visit(Print node, Object param) {

			// super.visit(node, param);
			System.out.print("print" + node.getSufijo() + " ");
			if (node.getExpresion() != null)
				node.getExpresion().accept(this, param);
			System.out.println(";");
			return null;
		}


		//	class Read { Expresion expresion; }
		public Object visit(Read node, Object param) {
			
			System.out.print("read ");
			if (node.getExpresion() != null)
				node.getExpresion().accept(this, param);
			System.out.println(";");
			return null;
		}

		//	class If { Expresion condicion;  List<Sentencia> cuerpoIf;  List<Sentencia> cuerpoElse; }
		public Object visit(If node, Object param) {

			// super.visit(node, param);
			System.out.print("if (");
			if (node.getCondicion() != null)
				node.getCondicion().accept(this, param);
			System.out.println(") {");
			if (node.getCuerpoIf() != null)
				for (Sentencia child : node.getCuerpoIf()){
					System.out.print("\t");
					child.accept(this, param);
				}
			System.out.print("\t");
			System.out.println("}");

			if (node.getCuerpoElse() != null){
				System.out.print("\t");
				System.out.println("else {");
				for (Sentencia child : node.getCuerpoElse()){
					System.out.print("\t");
					child.accept(this, param);
				}
				System.out.print("\t");
				System.out.println("}");
			}
			return null;
		}

		//	class While { Expresion condicion;  List<Sentencia> cuerpoWhile; }
		public Object visit(While node, Object param) {

			// super.visit(node, param);
			System.out.print("while (");
			if (node.getCondicion() != null)
				node.getCondicion().accept(this, param);
			System.out.println(") {");
			if (node.getCuerpoWhile() != null)
				for (Sentencia child : node.getCuerpoWhile()){
					System.out.print("\t");
					child.accept(this, param);
				}
			System.out.print("\t");
			System.out.println("}");
			return null;
		}

		//	class InvocacionSentencia { String nombre;  List<Expresion> argumentos; }
		public Object visit(InvocacionSentencia node, Object param) {

			// super.visit(node, param);
			System.out.print("\t");
			System.out.print(node.getNombre() + "(");
			if (node.getArgumentos() != null)
				for (Expresion child : node.getArgumentos())
					child.accept(this, param);
			System.out.println(");");
			return null;
		}

		//	class TipoEntero {  }
		public Object visit(TipoEntero node, Object param) {
			System.out.print("int ");
			return null;
		}

		//	class TipoReal {  }
		public Object visit(TipoReal node, Object param) {
			System.out.print("double ");
			return null;
		}

		//	class TipoCaracter {  }
		public Object visit(TipoCaracter node, Object param) {
			System.out.print("char ");
			return null;
		}

		//	class TipoArray { int tamanyo;  Tipo tipo; }
		public Object visit(TipoArray node, Object param) {

			// super.visit(node, param);
			System.out.print("[" + node.getTamanyo() + "]");
			if (node.getTipo() != null)
				node.getTipo().accept(this, param);

			return null;
		}

		//	class TipoStruct { String nombre; }
		public Object visit(TipoStruct node, Object param) {
			System.out.print(node.getNombre());
			return null;
		}
	}



