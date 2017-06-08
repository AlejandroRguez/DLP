/**
 * @generated VGen 1.3.3
 */

package visitor;

import ast.*;

public interface Visitor {
	public Object visit(Program node, Object param);
	public Object visit(CampoStruct node, Object param);
	public Object visit(DefinicionVariable node, Object param);
	public Object visit(DefinicionStruct node, Object param);
	public Object visit(DefinicionFuncion node, Object param);
	public Object visit(OperacionAritmetica node, Object param);
	public Object visit(OperacionRelacional node, Object param);
	public Object visit(OperacionLogica node, Object param);
	public Object visit(Distinto node, Object param);
	public Object visit(Cast node, Object param);
	public Object visit(InvocacionExpresion node, Object param);
	public Object visit(AccesoArray node, Object param);
	public Object visit(Acceso node, Object param);
	public Object visit(Variable node, Object param);
	public Object visit(LiteralEntero node, Object param);
	public Object visit(LiteralReal node, Object param);
	public Object visit(Caracter node, Object param);
	public Object visit(Asignacion node, Object param);
	public Object visit(Return node, Object param);
	public Object visit(Print node, Object param);
	public Object visit(Read node, Object param);
	public Object visit(If node, Object param);
	public Object visit(While node, Object param);
	public Object visit(InvocacionSentencia node, Object param);
	public Object visit(TipoEntero node, Object param);
	public Object visit(TipoReal node, Object param);
	public Object visit(TipoCaracter node, Object param);
	public Object visit(TipoArray node, Object param);
	public Object visit(TipoStruct node, Object param);
}
