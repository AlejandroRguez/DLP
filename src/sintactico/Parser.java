//### This file created by BYACC 1.8(/Java extension  1.14)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 5 "sintac.y"
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
//#line 24 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short AND=257;
public final static short OR=258;
public final static short IGUAL=259;
public final static short DISTINTO=260;
public final static short MAYORIGUAL=261;
public final static short MENORIGUAL=262;
public final static short VAR=263;
public final static short IDENT=264;
public final static short INT=265;
public final static short FLOAT=266;
public final static short CHAR=267;
public final static short LITENT=268;
public final static short STRUCT=269;
public final static short LITREAL=270;
public final static short LITCHAR=271;
public final static short CAST=272;
public final static short RETURN=273;
public final static short PRINT=274;
public final static short PRINTSP=275;
public final static short PRINTLN=276;
public final static short READ=277;
public final static short IF=278;
public final static short ELSE=279;
public final static short WHILE=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    3,    7,    8,    8,
    6,    6,    6,    6,    6,    4,   10,    9,    9,    5,
    5,   13,   14,   14,   11,   11,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   17,   17,
   16,   16,   18,   18,   18,   18,   18,   18,   18,   18,
   18,   18,   18,   18,   12,   12,
};
final static short yylen[] = {                            2,
    1,    2,    0,    1,    1,    1,    5,    5,    2,    0,
    1,    1,    1,    4,    1,    6,    4,    2,    0,   10,
    8,    3,    1,    3,    1,    0,    1,    1,    1,    1,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    2,    3,    3,    7,    4,    4,    1,    3,
    1,    0,    4,    3,    2,    3,    3,    3,    2,    3,
    5,    9,    5,    5,    2,    0,
};
final static short yydefred[] = {                         3,
    0,    0,    0,    0,    0,    2,    4,    5,    6,    0,
    0,    0,    0,    0,    0,   23,    0,   19,    0,   15,
   11,   12,   13,    0,    0,    0,    0,    0,    0,    7,
   22,    0,   10,   24,    0,    0,   18,    0,    0,    0,
    0,   16,   14,   10,    0,    9,    0,    0,    0,    0,
    0,    0,    0,   28,   21,   29,   30,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   65,   17,    0,    0,
    0,    0,    0,    0,    0,   55,    0,    0,    0,   59,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   20,
    0,    0,   45,    0,    0,    0,    0,   54,   56,   57,
   58,   60,   66,   66,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   44,    8,
    0,    0,    0,    0,    0,    0,   53,   47,   48,   64,
    0,    0,    0,   63,    0,    0,   46,   66,    0,   62,
};
final static short yydgoto[] = {                          1,
    2,    6,    7,    8,    9,   24,   46,   40,   28,   37,
   15,   47,   16,   17,   66,  105,  106,   67,
};
final static short yysindex[] = {                         0,
    0, -246, -256,  -24, -240,    0,    0,    0,    0,  -32,
 -230,  -83,  -88,  -14,    5,    0,    3,    0, -220,    0,
    0,    0,    0,   -8,  -88,  -45, -230, -113,  -37,    0,
    0,  -88,    0,    0,   -1,    2,    0,  -88,  -65, -204,
  -88,    0,    0,    0, -202,    0,  -33,    9, -204,    6,
   86,   86,   26,    0,    0,    0,    0,   11,   72,   86,
   86,   77,   86,   86,   86,  486,    0,    0,  -18,  -88,
   29,  363,  -41,   86,  -88,    0,  516,  522,  569,    0,
  636,  642,  694,  744,   86,   86,   86,   86,   86,   86,
   86,   86,   86,   86,   86,   86,   86,   86, -192,    0,
   17,   86,    0,  866,   36,   35,   18,    0,    0,    0,
    0,    0,    0,    0,  750,  -41,  -41,   -4,   -4,   -4,
   -4,   -4,   -4,    7,    7,  -36,  -36,  756,    0,    0,
   40,   24,   86,   44,   -3,   12,    0,    0,    0,    0,
  866,   86, -194,    0,  390,  -35,    0,    0,   27,    0,
};
final static short yyrindex[] = {                         0,
    0,   89,    0,    0,    0,    0,    0,    0,    0,    0,
    5,    0,    0,    0,    0,    0,   45,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   42,
    0,    0,    0,    0,    0,    0,    0,    0,   42,    0,
    0,    0,  777,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  102,    0,  -30,   50,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   50,    0,  -16,    0,   53,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  511,  792,  380,  407,  415,
  450,  457,  463,  335,  341,  113,  155,    0,    0,    0,
    0,  814,    0,    0,    0,    0,    0,    0,    0,    0,
   -9,    0,   57,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,    0,   -5,    0,   51,    0,    0,
    0,  -40,   69,    0,  968,   -2,    0,    0,
};
final static int YYTABLESIZE=1128;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
   96,   94,   19,   95,   99,   97,   51,   10,   69,   99,
   43,   36,   32,   43,   52,   11,    3,    4,   89,   31,
   88,   51,    5,   12,   49,   13,   39,   49,   43,   52,
   43,   50,   43,   14,   50,   48,   51,   96,   94,   18,
   95,   99,   97,   25,   52,   26,   27,   29,   96,   98,
   30,   51,   99,   97,   98,   38,   41,   44,   45,   52,
   42,   50,   43,   70,  101,   74,   51,   68,  102,  107,
   75,  129,  135,  136,   66,  130,  132,   33,  133,  134,
  139,   66,  140,  142,  146,   25,   98,  148,    1,   61,
   52,   55,   43,   51,   49,   34,   61,   98,    0,  131,
    0,    0,    0,    0,   52,    0,  100,  149,    0,   52,
    0,   51,    0,    0,    0,    0,   51,    0,   52,    0,
    0,  143,    0,    0,    0,   51,    0,    0,    0,    0,
   76,    0,    0,    0,    0,   80,  144,    0,    0,    0,
    0,    0,   27,   27,   27,   27,   27,   27,   27,    0,
   35,  150,    0,   33,   33,   33,   33,   33,    0,   33,
   27,   27,   27,   27,    0,    0,   66,    0,    0,    0,
    0,   33,   33,   33,   33,   20,   21,   22,   23,    0,
    0,   61,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   27,    0,   27,   34,   34,   34,   34,   34,
    0,   34,    0,    0,    0,   33,    0,    0,    0,    0,
    0,    0,    0,   34,   34,   34,   34,   90,   91,   92,
   93,    0,    0,    0,   27,    0,   43,   43,    0,    0,
   53,    0,    0,    0,   54,   33,   56,   57,   58,   59,
   60,   61,   62,   63,   64,   53,   65,   34,    0,   54,
    0,   56,   57,   58,   59,   60,   61,   62,   63,   64,
   53,   65,    0,    0,   54,    0,   56,   57,   58,   59,
   60,   61,   62,   63,   64,   53,   65,   34,    0,   54,
    0,   56,   57,   58,   59,   60,   61,   62,   63,   64,
   53,   65,    0,    0,   54,    0,   56,   57,   58,   59,
   60,   61,   62,   63,   64,   66,   65,    0,    0,   66,
    0,   66,   66,   66,   66,   66,   66,   66,   66,   66,
   61,   66,    0,    0,   61,    0,   61,   61,   61,   61,
   61,   61,   61,   61,   61,   71,   61,    0,    0,   54,
   71,   56,   57,   58,   54,    0,   56,   57,   58,   71,
    0,    0,    0,   54,    0,   56,   57,   58,   27,   27,
   27,   27,   27,   27,    0,    0,    0,    0,    0,   33,
   33,   33,   33,   33,   33,   31,    0,   31,   31,   31,
    0,   32,    0,   32,   32,   32,    0,    0,    0,    0,
    0,    0,    0,   31,   31,   31,   31,    0,    0,   32,
   32,   32,   32,  103,   96,   94,    0,   95,   99,   97,
    0,   34,   34,   34,   34,   34,   34,    0,    0,    0,
   38,    0,   89,   38,   88,    0,    0,   31,    0,    0,
  147,   96,   94,   32,   95,   99,   97,    0,   38,   38,
   38,   38,    0,    0,    0,    0,    0,   37,    0,   89,
   37,   88,    0,   98,    0,   39,    0,   31,   39,    0,
    0,    0,    0,   32,    0,   37,   37,   37,   37,    0,
    0,    0,   38,   39,   39,   39,   39,    0,    0,    0,
   98,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   40,    0,    0,   40,    0,    0,    0,   36,    0,   37,
   36,    0,   38,   35,    0,    0,   35,   39,   40,   40,
   40,   40,    0,    0,    0,   36,   36,   36,   36,    0,
    0,   35,   35,   35,   35,    0,    0,   96,   94,   37,
   95,   99,   97,    0,    0,    0,    0,   39,    0,    0,
    0,    0,   40,    0,    0,   89,   85,   88,    0,   36,
    0,   41,    0,    0,   41,   35,    0,   96,   94,    0,
   95,   99,   97,   96,   94,    0,   95,   99,   97,   41,
    0,   41,   40,    0,  108,   89,   98,   88,    0,   36,
  109,   89,    0,   88,    0,   35,    0,    0,    0,    0,
    0,   31,   31,   31,   31,   31,   31,   32,   32,   32,
   32,   32,   32,   41,    0,    0,   98,    0,    0,    0,
   96,   94,   98,   95,   99,   97,    0,    0,    0,   86,
   87,   90,   91,   92,   93,    0,    0,  110,   89,    0,
   88,    0,    0,   41,    0,    0,   38,   38,   38,   38,
   38,   38,    0,    0,    0,    0,   86,   87,   90,   91,
   92,   93,    0,    0,    0,    0,    0,    0,    0,   98,
    0,    0,    0,   37,   37,   37,   37,   37,   37,    0,
    0,   39,   39,   39,   39,   39,   39,   96,   94,    0,
   95,   99,   97,   96,   94,    0,   95,   99,   97,    0,
    0,    0,    0,    0,  111,   89,    0,   88,    0,    0,
  112,   89,    0,   88,    0,    0,   40,   40,   40,   40,
   40,   40,    0,   36,   36,   36,   36,   36,   36,   35,
   35,   35,   35,   35,   35,    0,   98,    0,    0,    0,
    0,    0,   98,    0,    0,   96,   94,    0,   95,   99,
   97,    0,   86,   87,   90,   91,   92,   93,    0,    0,
    0,    0,    0,   89,    0,   88,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   41,   41,    0,
    0,    0,   86,   87,   90,   91,   92,   93,   86,   87,
   90,   91,   92,   93,   98,   96,   94,    0,   95,   99,
   97,   96,   94,    0,   95,   99,   97,   96,   94,    0,
   95,   99,   97,   89,    0,   88,    0,    0,  137,   89,
    0,   88,    0,    0,    0,   89,  113,   88,   27,   27,
    0,   27,   27,   27,    0,   86,   87,   90,   91,   92,
   93,    0,   42,    0,   98,   42,   27,   27,   27,    0,
   98,    0,    0,    0,    0,    0,   98,    0,  138,    0,
   42,    0,   42,    0,    0,   48,   48,    0,   48,   48,
   48,    0,    0,    0,    0,    0,  114,   27,    0,    0,
    0,    0,    0,   48,   48,   48,    0,    0,    0,    0,
    0,    0,    0,    0,   42,    0,    0,    0,    0,    0,
    0,    0,   86,   87,   90,   91,   92,   93,   86,   87,
   90,   91,   92,   93,   48,    0,    0,   96,   94,    0,
   95,   99,   97,    0,   42,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   89,    0,   88,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   86,   87,   90,   91,   92,   93,   98,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   86,   87,   90,   91,   92,   93,   86,   87,   90,   91,
   92,   93,   86,   87,   90,   91,   92,   93,   72,   73,
    0,    0,    0,    0,    0,    0,   77,   78,   79,   81,
   82,   83,   84,   27,   27,   27,   27,   27,   27,    0,
    0,  104,    0,    0,    0,    0,    0,    0,   42,   42,
    0,    0,  115,  116,  117,  118,  119,  120,  121,  122,
  123,  124,  125,  126,  127,  128,    0,    0,    0,  104,
   48,   48,   48,   48,   48,   48,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  141,    0,    0,    0,    0,    0,    0,    0,    0,  145,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   86,   87,   90,   91,   92,   93,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   42,   43,   91,   45,   46,   47,   40,  264,   49,   46,
   41,  125,   58,   44,   33,   40,  263,  264,   60,   25,
   62,   40,  269,  264,   41,   58,   32,   44,   59,   33,
   61,   41,   38,  264,   44,   41,   40,   42,   43,  123,
   45,   46,   47,   58,   33,   41,   44,  268,   42,   91,
   59,   40,   46,   47,   91,   93,   58,  123,  263,   33,
   59,  264,   93,   58,   70,   40,   40,   59,   40,   75,
   60,  264,  113,  114,   33,   59,   41,  123,   44,   62,
   41,   40,   59,   40,  279,   41,   91,  123,    0,   33,
   41,  125,  123,   41,   44,   27,   40,   91,   -1,  102,
   -1,   -1,   -1,   -1,   33,   -1,  125,  148,   -1,   33,
   -1,   40,   -1,   -1,   -1,   -1,   40,   -1,   33,   -1,
   -1,  125,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,
   59,   -1,   -1,   -1,   -1,   59,  125,   -1,   -1,   -1,
   -1,   -1,   41,   42,   43,   44,   45,   46,   47,   -1,
  264,  125,   -1,   41,   42,   43,   44,   45,   -1,   47,
   59,   60,   61,   62,   -1,   -1,  125,   -1,   -1,   -1,
   -1,   59,   60,   61,   62,  264,  265,  266,  267,   -1,
   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   91,   -1,   93,   41,   42,   43,   44,   45,
   -1,   47,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   60,   61,   62,  259,  260,  261,
  262,   -1,   -1,   -1,  123,   -1,  257,  258,   -1,   -1,
  264,   -1,   -1,   -1,  268,  123,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  264,  280,   93,   -1,  268,
   -1,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  264,  280,   -1,   -1,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  264,  280,  123,   -1,  268,
   -1,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  264,  280,   -1,   -1,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  264,  280,   -1,   -1,  268,
   -1,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  264,  280,   -1,   -1,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  264,  280,   -1,   -1,  268,
  264,  270,  271,  272,  268,   -1,  270,  271,  272,  264,
   -1,   -1,   -1,  268,   -1,  270,  271,  272,  257,  258,
  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,   41,   -1,   43,   44,   45,
   -1,   41,   -1,   43,   44,   45,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   60,   61,   62,   -1,   -1,   59,
   60,   61,   62,   41,   42,   43,   -1,   45,   46,   47,
   -1,  257,  258,  259,  260,  261,  262,   -1,   -1,   -1,
   41,   -1,   60,   44,   62,   -1,   -1,   93,   -1,   -1,
   41,   42,   43,   93,   45,   46,   47,   -1,   59,   60,
   61,   62,   -1,   -1,   -1,   -1,   -1,   41,   -1,   60,
   44,   62,   -1,   91,   -1,   41,   -1,  123,   44,   -1,
   -1,   -1,   -1,  123,   -1,   59,   60,   61,   62,   -1,
   -1,   -1,   93,   59,   60,   61,   62,   -1,   -1,   -1,
   91,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   41,   -1,   -1,   44,   -1,   -1,   -1,   41,   -1,   93,
   44,   -1,  123,   41,   -1,   -1,   44,   93,   59,   60,
   61,   62,   -1,   -1,   -1,   59,   60,   61,   62,   -1,
   -1,   59,   60,   61,   62,   -1,   -1,   42,   43,  123,
   45,   46,   47,   -1,   -1,   -1,   -1,  123,   -1,   -1,
   -1,   -1,   93,   -1,   -1,   60,   61,   62,   -1,   93,
   -1,   41,   -1,   -1,   44,   93,   -1,   42,   43,   -1,
   45,   46,   47,   42,   43,   -1,   45,   46,   47,   59,
   -1,   61,  123,   -1,   59,   60,   91,   62,   -1,  123,
   59,   60,   -1,   62,   -1,  123,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,  257,  258,  259,
  260,  261,  262,   93,   -1,   -1,   91,   -1,   -1,   -1,
   42,   43,   91,   45,   46,   47,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,   -1,   -1,   59,   60,   -1,
   62,   -1,   -1,  123,   -1,   -1,  257,  258,  259,  260,
  261,  262,   -1,   -1,   -1,   -1,  257,  258,  259,  260,
  261,  262,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
   -1,  257,  258,  259,  260,  261,  262,   42,   43,   -1,
   45,   46,   47,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,   59,   60,   -1,   62,   -1,   -1,
   59,   60,   -1,   62,   -1,   -1,  257,  258,  259,  260,
  261,  262,   -1,  257,  258,  259,  260,  261,  262,  257,
  258,  259,  260,  261,  262,   -1,   91,   -1,   -1,   -1,
   -1,   -1,   91,   -1,   -1,   42,   43,   -1,   45,   46,
   47,   -1,  257,  258,  259,  260,  261,  262,   -1,   -1,
   -1,   -1,   -1,   60,   -1,   62,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,  257,  258,
  259,  260,  261,  262,   91,   42,   43,   -1,   45,   46,
   47,   42,   43,   -1,   45,   46,   47,   42,   43,   -1,
   45,   46,   47,   60,   -1,   62,   -1,   -1,   59,   60,
   -1,   62,   -1,   -1,   -1,   60,  123,   62,   42,   43,
   -1,   45,   46,   47,   -1,  257,  258,  259,  260,  261,
  262,   -1,   41,   -1,   91,   44,   60,   61,   62,   -1,
   91,   -1,   -1,   -1,   -1,   -1,   91,   -1,   93,   -1,
   59,   -1,   61,   -1,   -1,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,  123,   91,   -1,   -1,
   -1,   -1,   -1,   60,   61,   62,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,  257,  258,
  259,  260,  261,  262,   91,   -1,   -1,   42,   43,   -1,
   45,   46,   47,   -1,  123,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   60,   -1,   62,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,   91,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,  257,  258,  259,  260,
  261,  262,  257,  258,  259,  260,  261,  262,   51,   52,
   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,   61,   62,
   63,   64,   65,  257,  258,  259,  260,  261,  262,   -1,
   -1,   74,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
   -1,   -1,   85,   86,   87,   88,   89,   90,   91,   92,
   93,   94,   95,   96,   97,   98,   -1,   -1,   -1,  102,
  257,  258,  259,  260,  261,  262,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  133,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  142,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=280;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"\"AND\"","\"OR\"","\"IGUAL\"",
"\"DISTINTO\"","\"MAYORIGUAL\"","\"MENORIGUAL\"","\"VAR\"","\"IDENT\"",
"\"INT\"","\"FLOAT\"","\"CHAR\"","\"LITENT\"","\"STRUCT\"","\"LITREAL\"",
"\"LITCHAR\"","\"CAST\"","\"RETURN\"","\"PRINT\"","\"PRINTSP\"","\"PRINTLN\"",
"\"READ\"","\"IF\"","\"ELSE\"","\"WHILE\"",
};
final static String yyrule[] = {
"$accept : program",
"program : definiciones",
"definiciones : definiciones definicion",
"definiciones :",
"definicion : definicionVariableGlobal",
"definicion : definicionStruct",
"definicion : definicionFuncion",
"definicionVariableGlobal : \"VAR\" \"IDENT\" ':' tipo ';'",
"definicionVariableLocal : \"VAR\" \"IDENT\" ':' tipo ';'",
"definicionesVariableLocal : definicionesVariableLocal definicionVariableLocal",
"definicionesVariableLocal :",
"tipo : \"INT\"",
"tipo : \"FLOAT\"",
"tipo : \"CHAR\"",
"tipo : '[' \"LITENT\" ']' tipo",
"tipo : \"IDENT\"",
"definicionStruct : \"STRUCT\" \"IDENT\" '{' campos '}' ';'",
"campo : \"IDENT\" ':' tipo ';'",
"campos : campos campo",
"campos :",
"definicionFuncion : \"IDENT\" '(' parametrosOpcional ')' ':' tipo '{' definicionesVariableLocal sentencias '}'",
"definicionFuncion : \"IDENT\" '(' parametrosOpcional ')' '{' definicionesVariableLocal sentencias '}'",
"parametro : \"IDENT\" ':' tipo",
"parametros : parametro",
"parametros : parametros ',' parametro",
"parametrosOpcional : parametros",
"parametrosOpcional :",
"expr : \"IDENT\"",
"expr : \"LITENT\"",
"expr : \"LITREAL\"",
"expr : \"LITCHAR\"",
"expr : expr '+' expr",
"expr : expr '-' expr",
"expr : expr '*' expr",
"expr : expr '/' expr",
"expr : expr \"MENORIGUAL\" expr",
"expr : expr \"MAYORIGUAL\" expr",
"expr : expr '<' expr",
"expr : expr '>' expr",
"expr : expr \"IGUAL\" expr",
"expr : expr \"DISTINTO\" expr",
"expr : expr \"AND\" expr",
"expr : expr \"OR\" expr",
"expr : '!' expr",
"expr : expr '.' \"IDENT\"",
"expr : '(' expr ')'",
"expr : \"CAST\" '<' tipo '>' '(' expr ')'",
"expr : expr '[' expr ']'",
"expr : \"IDENT\" '(' expresionesOpcional ')'",
"expresiones : expr",
"expresiones : expresiones ',' expr",
"expresionesOpcional : expresiones",
"expresionesOpcional :",
"sentencia : expr '=' expr ';'",
"sentencia : \"RETURN\" expr ';'",
"sentencia : \"RETURN\" ';'",
"sentencia : \"PRINT\" expr ';'",
"sentencia : \"PRINTSP\" expr ';'",
"sentencia : \"PRINTLN\" expr ';'",
"sentencia : \"PRINTLN\" ';'",
"sentencia : \"READ\" expr ';'",
"sentencia : \"IF\" expr '{' sentencias '}'",
"sentencia : \"IF\" expr '{' sentencias '}' \"ELSE\" '{' sentencias '}'",
"sentencia : \"WHILE\" expr '{' sentencias '}'",
"sentencia : \"IDENT\" '(' expresionesOpcional ')' ';'",
"sentencias : sentencias sentencia",
"sentencias :",
};

//#line 135 "sintac.y"
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
//#line 561 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 27 "sintac.y"
{ raiz = new Program((List<Definicion>)val_peek(0)); }
break;
case 2:
//#line 30 "sintac.y"
{yyval = val_peek(1); ((ArrayList<Definicion>)yyval).add(((Definicion)val_peek(0))); }
break;
case 3:
//#line 31 "sintac.y"
{yyval = new ArrayList<Definicion>(); }
break;
case 4:
//#line 34 "sintac.y"
{yyval = val_peek(0); }
break;
case 5:
//#line 35 "sintac.y"
{yyval = val_peek(0); }
break;
case 6:
//#line 36 "sintac.y"
{yyval = val_peek(0); }
break;
case 7:
//#line 40 "sintac.y"
{yyval = new DefinicionVariable (val_peek(3),val_peek(1), Ambito.GLOBAL);}
break;
case 8:
//#line 43 "sintac.y"
{yyval = new DefinicionVariable (val_peek(3),val_peek(1), Ambito.LOCAL);}
break;
case 9:
//#line 47 "sintac.y"
{yyval=val_peek(1); ((ArrayList<DefinicionVariable>)yyval).add(((DefinicionVariable)val_peek(0)));  }
break;
case 10:
//#line 48 "sintac.y"
{yyval = new ArrayList<DefinicionVariable>(); }
break;
case 11:
//#line 51 "sintac.y"
{yyval = new TipoEntero(); }
break;
case 12:
//#line 52 "sintac.y"
{yyval = new TipoReal(); }
break;
case 13:
//#line 53 "sintac.y"
{yyval = new TipoCaracter(); }
break;
case 14:
//#line 54 "sintac.y"
{yyval = new TipoArray(val_peek(2), val_peek(0)); }
break;
case 15:
//#line 55 "sintac.y"
{yyval = new TipoStruct(val_peek(0)); }
break;
case 16:
//#line 58 "sintac.y"
{yyval = new DefinicionStruct (val_peek(4), val_peek(2));}
break;
case 17:
//#line 61 "sintac.y"
{yyval = new CampoStruct(val_peek(3),val_peek(1));}
break;
case 18:
//#line 64 "sintac.y"
{yyval = val_peek(1); ((ArrayList<CampoStruct>)yyval).add(((CampoStruct)val_peek(0))); }
break;
case 19:
//#line 65 "sintac.y"
{yyval = new ArrayList<CampoStruct>(); }
break;
case 20:
//#line 68 "sintac.y"
{yyval = new DefinicionFuncion(val_peek(9), val_peek(7), val_peek(4), val_peek(2), val_peek(1)); }
break;
case 21:
//#line 69 "sintac.y"
{yyval = new DefinicionFuncion(val_peek(7), val_peek(5), null , val_peek(2), val_peek(1)); }
break;
case 22:
//#line 72 "sintac.y"
{yyval = new DefinicionVariable(val_peek(2), val_peek(0), Ambito.PARAMETRO); }
break;
case 23:
//#line 75 "sintac.y"
{yyval = new ArrayList<DefinicionVariable>(); ((ArrayList<DefinicionVariable>)yyval).add(((DefinicionVariable)val_peek(0))); }
break;
case 24:
//#line 76 "sintac.y"
{ ((List)val_peek(2)).add(((DefinicionVariable)val_peek(0))); yyval = val_peek(2);}
break;
case 25:
//#line 79 "sintac.y"
{yyval = val_peek(0);}
break;
case 26:
//#line 80 "sintac.y"
{yyval = new ArrayList<DefinicionVariable>();}
break;
case 27:
//#line 83 "sintac.y"
{yyval = new Variable (val_peek(0)); }
break;
case 28:
//#line 84 "sintac.y"
{yyval = new LiteralEntero (val_peek(0)); }
break;
case 29:
//#line 85 "sintac.y"
{yyval = new LiteralReal (val_peek(0)); }
break;
case 30:
//#line 86 "sintac.y"
{yyval = new Caracter (val_peek(0)); }
break;
case 31:
//#line 87 "sintac.y"
{yyval = new OperacionAritmetica(val_peek(2),val_peek(1),val_peek(0));}
break;
case 32:
//#line 88 "sintac.y"
{yyval = new OperacionAritmetica(val_peek(2),val_peek(1),val_peek(0));}
break;
case 33:
//#line 89 "sintac.y"
{yyval = new OperacionAritmetica(val_peek(2),val_peek(1),val_peek(0));}
break;
case 34:
//#line 90 "sintac.y"
{yyval = new OperacionAritmetica(val_peek(2),val_peek(1),val_peek(0));}
break;
case 35:
//#line 91 "sintac.y"
{yyval = new OperacionRelacional(val_peek(2),val_peek(1),val_peek(0));}
break;
case 36:
//#line 92 "sintac.y"
{yyval = new OperacionRelacional(val_peek(2),val_peek(1),val_peek(0));}
break;
case 37:
//#line 93 "sintac.y"
{yyval = new OperacionRelacional(val_peek(2),val_peek(1),val_peek(0));}
break;
case 38:
//#line 94 "sintac.y"
{yyval = new OperacionRelacional(val_peek(2),val_peek(1),val_peek(0));}
break;
case 39:
//#line 95 "sintac.y"
{yyval = new OperacionRelacional(val_peek(2),val_peek(1),val_peek(0));}
break;
case 40:
//#line 96 "sintac.y"
{yyval = new OperacionRelacional(val_peek(2),val_peek(1),val_peek(0));}
break;
case 41:
//#line 97 "sintac.y"
{yyval = new OperacionLogica(val_peek(2),val_peek(1),val_peek(0));}
break;
case 42:
//#line 98 "sintac.y"
{yyval = new OperacionLogica(val_peek(2),val_peek(1),val_peek(0));}
break;
case 43:
//#line 99 "sintac.y"
{yyval = new Distinto(val_peek(0)); }
break;
case 44:
//#line 100 "sintac.y"
{yyval = new Acceso(val_peek(2), val_peek(0));}
break;
case 45:
//#line 101 "sintac.y"
{yyval = val_peek(1); }
break;
case 46:
//#line 102 "sintac.y"
{yyval = new Cast(val_peek(4), val_peek(1)); }
break;
case 47:
//#line 103 "sintac.y"
{yyval = new AccesoArray(val_peek(3), val_peek(1)); }
break;
case 48:
//#line 104 "sintac.y"
{yyval = new InvocacionExpresion(val_peek(3), val_peek(1)); }
break;
case 49:
//#line 107 "sintac.y"
{yyval = new ArrayList<Expresion>(); ((ArrayList<Expresion>)yyval).add(((Expresion)val_peek(0))); }
break;
case 50:
//#line 108 "sintac.y"
{ ((ArrayList<Expresion>)val_peek(2)).add(((Expresion)val_peek(0))); yyval = val_peek(2);}
break;
case 51:
//#line 111 "sintac.y"
{yyval = val_peek(0);}
break;
case 52:
//#line 112 "sintac.y"
{yyval = new ArrayList<Expresion>();}
break;
case 53:
//#line 115 "sintac.y"
{yyval = new Asignacion(val_peek(3),val_peek(1)); }
break;
case 54:
//#line 116 "sintac.y"
{yyval = new Return(val_peek(1)); }
break;
case 55:
//#line 117 "sintac.y"
{yyval = new Return(null).setPositions(val_peek(1)); }
break;
case 56:
//#line 118 "sintac.y"
{yyval = new Print(val_peek(1), null); }
break;
case 57:
//#line 119 "sintac.y"
{yyval = new Print(val_peek(1), "sp"); }
break;
case 58:
//#line 120 "sintac.y"
{yyval = new Print(val_peek(1), "ln"); }
break;
case 59:
//#line 121 "sintac.y"
{yyval = new Print(null, "ln").setPositions(val_peek(1)); }
break;
case 60:
//#line 122 "sintac.y"
{yyval = new Read (val_peek(1)); }
break;
case 61:
//#line 123 "sintac.y"
{yyval = new If (val_peek(3), val_peek(1), null); }
break;
case 62:
//#line 124 "sintac.y"
{yyval = new If (val_peek(7), val_peek(5), val_peek(1)); }
break;
case 63:
//#line 125 "sintac.y"
{yyval = new While (val_peek(3), val_peek(1)); }
break;
case 64:
//#line 126 "sintac.y"
{yyval = new InvocacionSentencia(val_peek(4), val_peek(2)); }
break;
case 65:
//#line 129 "sintac.y"
{yyval = val_peek(1); ((ArrayList<Sentencia>)yyval).add(((Sentencia)val_peek(0))); }
break;
case 66:
//#line 130 "sintac.y"
{yyval = new ArrayList<Sentencia>(); }
break;
//#line 973 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
