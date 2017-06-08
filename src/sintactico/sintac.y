// @author Alejandro Rodríguez

/* No es necesario modificar esta sección ------------------ */
%{
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
%}

/* Precedencias aquí --------------------------------------- */
%right '='
%left '(' ')'
%left 'AND' '!' 'OR'
%left '>' '<' 'IGUAL' 'DISTINTO' 'MAYORIGUAL' 'MENORIGUAL'
%left '+' '-'
%left '*' '/'
%left '[' ']' 
%left '.'

%%

/* Añadir las reglas en esta sección ----------------------- */

program: definiciones 					{ raiz = new Program((List<Definicion>)$1); }
	;
	
definiciones: definiciones definicion	{$$ = $1; ((ArrayList<Definicion>)$$).add(((Definicion)$2)); }
	|									{$$ = new ArrayList<Definicion>(); }
	;
	
definicion: definicionVariableGlobal	{$$ = $1; }
	| definicionStruct					{$$ = $1; }
	| definicionFuncion					{$$ = $1; }
	;

	
definicionVariableGlobal: 'VAR' 'IDENT' ':' tipo ';' 								{$$ = new DefinicionVariable ($2,$4, Ambito.GLOBAL);}
	;
	
definicionVariableLocal: 'VAR' 'IDENT' ':' tipo ';'                                 {$$ = new DefinicionVariable ($2,$4, Ambito.LOCAL);}
	;

	
definicionesVariableLocal: definicionesVariableLocal definicionVariableLocal		{$$=$1; ((ArrayList<DefinicionVariable>)$$).add(((DefinicionVariable)$2));  }
	|																				{$$ = new ArrayList<DefinicionVariable>(); }
	;

tipo: 'INT'												   {$$ = new TipoEntero(); }
	| 'FLOAT'											   {$$ = new TipoReal(); }
	| 'CHAR'											   {$$ = new TipoCaracter(); }
	| '[' 'LITENT' ']' tipo								   {$$ = new TipoArray($2, $4); }
	| 'IDENT'											   {$$ = new TipoStruct($1); }
	;

definicionStruct: 'STRUCT' 'IDENT' '{' campos '}' ';'      {$$ = new DefinicionStruct ($2, $4);}
	;

campo: 'IDENT' ':' tipo ';'   {$$ = new CampoStruct($1,$3);}
	;

campos: campos campo		  {$$ = $1; ((ArrayList<CampoStruct>)$$).add(((CampoStruct)$2)); }
	|                         {$$ = new ArrayList<CampoStruct>(); }
	;

definicionFuncion: 'IDENT' '(' parametrosOpcional ')' ':' tipo '{' definicionesVariableLocal sentencias '}'    {$$ = new DefinicionFuncion($1, $3, $6, $8, $9); }	
	| 'IDENT' '(' parametrosOpcional ')' '{' definicionesVariableLocal sentencias '}'                          {$$ = new DefinicionFuncion($1, $3, null , $6, $7); }
	;
	
parametro: 'IDENT' ':' tipo 			{$$ = new DefinicionVariable($1, $3, Ambito.PARAMETRO); }
	;

parametros: parametro					{$$ = new ArrayList<DefinicionVariable>(); ((ArrayList<DefinicionVariable>)$$).add(((DefinicionVariable)$1)); }
	| parametros ',' parametro 			{ ((List)$1).add(((DefinicionVariable)$3)); $$ = $1;}
	;
	
parametrosOpcional: parametros			{$$ = $1;}
	|                                   {$$ = new ArrayList<DefinicionVariable>();}
	;

expr: 'IDENT'									{$$ = new Variable ($1); }
	| 'LITENT'									{$$ = new LiteralEntero ($1); }
	| 'LITREAL'									{$$ = new LiteralReal ($1); }
	| 'LITCHAR'									{$$ = new Caracter ($1); }
	| expr '+' expr								{$$ = new OperacionAritmetica($1,$2,$3);}
	| expr '-' expr								{$$ = new OperacionAritmetica($1,$2,$3);}
	| expr '*' expr								{$$ = new OperacionAritmetica($1,$2,$3);}
	| expr '/' expr								{$$ = new OperacionAritmetica($1,$2,$3);}
	| expr 'MENORIGUAL' expr					{$$ = new OperacionRelacional($1,$2,$3);}
	| expr 'MAYORIGUAL' expr					{$$ = new OperacionRelacional($1,$2,$3);}
	| expr '<' expr								{$$ = new OperacionRelacional($1,$2,$3);}
	| expr '>' expr								{$$ = new OperacionRelacional($1,$2,$3);}
	| expr 'IGUAL' expr							{$$ = new OperacionRelacional($1,$2,$3);}
	| expr 'DISTINTO' expr						{$$ = new OperacionRelacional($1,$2,$3);}
	| expr 'AND' expr							{$$ = new OperacionLogica($1,$2,$3);}
	| expr 'OR' expr							{$$ = new OperacionLogica($1,$2,$3);}
	| '!' expr									{$$ = new Distinto($2); }
	| expr '.' 'IDENT'   						{$$ = new Acceso($1, $3);}
	| '(' expr ')'								{$$ = $2; }
	| 'CAST' '<' tipo '>' '(' expr ')'			{$$ = new Cast($3, $6); }
	| expr '[' expr ']'							{$$ = new AccesoArray($1, $3); }
	| 'IDENT' '(' expresionesOpcional ')'		{$$ = new InvocacionExpresion($1, $3); }
	;
	
expresiones: expr						{$$ = new ArrayList<Expresion>(); ((ArrayList<Expresion>)$$).add(((Expresion)$1)); }
	| expresiones ',' expr              { ((ArrayList<Expresion>)$1).add(((Expresion)$3)); $$ = $1;}
	;
	
expresionesOpcional: expresiones		{$$ = $1;}
	|									{$$ = new ArrayList<Expresion>();}
	;

sentencia: expr '=' expr ';'									{$$ = new Asignacion($1,$3); }
	| 'RETURN' expr ';'											{$$ = new Return($2); }
	| 'RETURN' ';'												{$$ = new Return(null).setPositions($1); }
	| 'PRINT' expr ';'											{$$ = new Print($2, null); }
	| 'PRINTSP' expr ';'										{$$ = new Print($2, "sp"); }
	| 'PRINTLN' expr ';'										{$$ = new Print($2, "ln"); }
	| 'PRINTLN' ';'												{$$ = new Print(null, "ln").setPositions($1); }
	| 'READ' expr ';'											{$$ = new Read ($2); }
	| 'IF' expr '{' sentencias '}'								{$$ = new If ($2, $4, null); }
	| 'IF' expr '{' sentencias '}' 'ELSE' '{' sentencias '}'	{$$ = new If ($2, $4, $8); }
	| 'WHILE' expr '{' sentencias '}'							{$$ = new While ($2, $4); }
	| 'IDENT' '(' expresionesOpcional ')' ';' 					{$$ = new InvocacionSentencia($1, $3); }
	;
	
sentencias: sentencias sentencia	  {$$ = $1; ((ArrayList<Sentencia>)$$).add(((Sentencia)$2)); }
	|                                 {$$ = new ArrayList<Sentencia>(); }
	;
	

%%
/* No es necesario modificar esta sección ------------------ */

public Parser(Yylex lex, GestorErrores gestor, boolean debug) {
	this(debug);
	this.lex = lex;
	this.gestor = gestor;
}

// Métodos de acceso para el main -------------
public int parse() { return yyparse(); }
public AST getAST() { return raiz; }

// Funciones requeridas por Yacc --------------
void yyerror(String msg) {
	Token lastToken = (Token) yylval;
	gestor.error("Sintáctico", "Token = " + lastToken.getToken() + ", lexema = \"" + lastToken.getLexeme() + "\". " + msg, lastToken.getStart());
}

int yylex() {
	try {
		int token = lex.yylex();
		yylval = new Token(token, lex.lexeme(), lex.line(), lex.column());
		return token;
	} catch (IOException e) {
		return -1;
	}
}

private Yylex lex;
private GestorErrores gestor;
private AST raiz;
