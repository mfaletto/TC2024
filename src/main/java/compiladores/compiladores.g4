/* grammar compiladores;

@header {
package compiladores;
}

// fragment LETRA : [A-Za-z] ;
// fragment DIGITO : [0-9] ;

// NUMERO : DIGITO+ ;
// OTRO : . ;

// ID : (LETRA | '_')(LETRA | DIGITO | '_')* ;

// s : ID     { System.out.println("ID ->" + $ID.getText() + "<--"); }         s
//   | NUMERO { System.out.println("NUMERO ->" + $NUMERO.getText() + "<--"); } s
//   | OTRO   { System.out.println("Otro ->" + $OTRO.getText() + "<--"); }     s
//   | EOF
//   ;


// PA : '(' ;
// PB : ')' ;
// CA : '[' ;
// CB : ']' ;
// LA : '{' ;
// LB : '}' ;

// si : s EOF;

// s : CA s CB s
//   | LA s LB s
//   | PA s PB s
//   |
//   ;


fragment LETRA : [A-Za-z] ;
fragment DIGITO : [0-9] ;

WS: [ \t\r\n]+ -> skip;

PYC: ';';
LLA: '{';
LLC: '}';
INT: 'int'; //Palabra reservada antes del ID porque genera ambiguedad
IGUAL: '=';
DOUBLE: 'double';
ID : (LETRA | '_')(LETRA | DIGITO | '_')* ;

NUMERO : DIGITO+ ;




programa: instrucciones EOF; //programa es una secuencia de instrucciones hasta el vacio

instrucciones: instruccion instrucciones 
              |
              ;

instruccion: LLA instrucciones LLC //depende del lenguaje
            | declaracion
            | asignacion
            ;

declaracion: INT ID PYC;

asignacion: ID IGUAL NUMERO PYC;
/* 
{
    int x;
}



programa:  instrucciones  EOF;

instrucciones: instruccion instrucciones | instruccion;

instruccion: LLA instrucciones LLC 
            | declaracion
            | asignacion
            ;

declaracion: (INT | DOUBLE) declaraciones PYC;

declaraciones: declaracion | declaraciones ',' declaracion;

asignacion: ID IGUAL NUMERO PYC;



-------------------------------------------------------------------


// Definición de reglas léxicas
INT: 'int';
DOUBLE: 'double';
ID: [a-zA-Z]+;
EQUALS: '=';
COMMA: ',';
SEMICOLON: ';';
LPAREN: '(';
RPAREN: ')';
WHILE: 'while';
COMPARE: ('<' | '>' | '<=' | '>=' | '==' | '!=');
WS: [ \t\r\n]+ -> skip;

// Definición de reglas sintácticas
programa: (declaracion | asignacion | iwhile)*;

declaracion: tipo ID (EQUALS NUMERO)? (COMMA ID (EQUALS NUMERO)?)* SEMICOLON;

asignacion: ID EQUALS expresion SEMICOLON;

iwhile: WHILE LPAREN expresion RPAREN LLAVE_L instrucciones LLAVE_R;

tipo: INT | DOUBLE;

expresion: ID COMPARE ID;

instrucciones: (declaracion | asignacion | iwhile);

NUMERO: [0-9]+;
LLAVE_L: '{';
LLAVE_R: '}';



grammar compiladores;

@header {
package compiladores;
}

fragment LETRA : [A-Za-z] ;
fragment DIGITO : [0-9] ;

WS: [ \t\r\n]+ -> skip;

PYC: ';';
LLA: '{';
LLC: '}';
LP: '(';
RP: ')';
INT: 'int'; 
IF: 'if';
FOR: 'for';
WHILE: 'while';
ELSE: 'else';
RETURN: 'return';
PRINTF: 'printf'; 
VOID: 'void';
COMPARE: ('<' | '>' | '<=' | '>=' | '==' | '!=');
ACUM: ('++' | '--');
IGUAL: '=';
MAS: '+';
MENOS: '-';
DOUBLE: 'double';
FLOAT: 'float';
COMMA: ',';
PUNTO: '.';
STRING: '"' (~["\\] | '\\' .)* '"';

ID : (LETRA | '_')(LETRA | DIGITO | '_')* ;
NUMERO : (DIGITO+ | DIGITO+ PUNTO DIGITO+) ;



programa: (declaracionFuncion | instrucciones) EOF;

instrucciones: instruccion*;

instruccion: bloque
            | declaracion
            | asignacion
            | ifStatement
            | forStatement
            | whileStatement
            | declaracionFuncion
            | llamadaFuncion
            | llamadaPrintf
            | returnStatement
            ;
bloque: LLA instrucciones LLC;

declaracion: (INT | DOUBLE | FLOAT) ID PYC;

asignacion: ID ((IGUAL | MAS | MENOS) (ID | NUMERO))* PYC;

ifStatement: IF LP expression RP bloque (ELSE bloque)?;

forStatement: FOR LP forExpression RP bloque ;


whileStatement: WHILE LP expression RP bloque ;

expression: (ID | NUMERO) COMPARE (ID | NUMERO);

forExpression: asignacion expression PYC ID ACUM;

llamadaPrintf: PRINTF LP STRING (COMMA argumentos)? RP PYC;

llamadaFuncion: ID LP argumentos? RP PYC;

argumentos: (ID | NUMERO) (',' (ID | NUMERO))*;

declaracionFuncion: (INT | DOUBLE | FLOAT | VOID) ID LP parametros? RP bloque;

parametros: (INT | DOUBLE | FLOAT) ID (',' (INT | DOUBLE | FLOAT) ID)*;

returnStatement: RETURN (NUMERO | expression?) PYC;











grammar compiladores;

@header {
package compiladores;
}

fragment LETRA : [A-Za-z] ;
fragment DIGITO : [0-9] ;

WS: [ \t\r\n]+ -> skip;

PYC: ';';
LLA: '{';
LLC: '}';
LP: '(';
RP: ')';
INT: 'int'; 
IF: 'if';
FOR: 'for';
WHILE: 'while';
ELSE: 'else';
RETURN: 'return';
PRINTF: 'printf'; 
VOID: 'void';
COMPARE: ('<' | '>' | '<=' | '>=' | '==' | '!=');
ACUM: ('++' | '--');
IGUAL: '=';
MAS: '+';
MENOS: '-';
MULT: '*';
DIV: '/';
DOUBLE: 'double';
FLOAT: 'float';
COMMA: ',';
PUNTO: '.';
COMENTARIO: '//';
STRING: '"' (~["\\] | '\\' .)* '"';

ID : (LETRA | '_')(LETRA | DIGITO | '_')* ;
NUMERO : (DIGITO+ | DIGITO+ PUNTO DIGITO+) ;

programa: (declaracionFuncion | instrucciones) EOF;

instrucciones: instruccion*;

instruccion: bloque
            | declaracion
            | asignacion
            | ifStatement
            | forStatement
            | whileStatement
            | declaracionFuncion
            | llamadaFuncion 
            | llamadaPrintf
            | returnStatement
            | puntocoma
            ;

bloque: LLA instrucciones LLC;

puntocoma: PYC;

declaracion: INT ID puntocoma;

asignacion: (INT | DOUBLE | FLOAT)? ID IGUAL ( llamadaFuncion | expression | ID | NUMERO) puntocoma;

ifStatement: IF LP expression RP bloque (ELSE bloque)?;

forStatement: FOR LP forExpression RP bloque;

forExpression: asignacion expression puntocoma ID ACUM;

whileStatement: WHILE LP expression RP bloque;

llamadaFuncion: ID LP argumentos? RP ;

expression: term ((MAS | MENOS | COMPARE) term)*;

term: factor ((MULT | DIV) factor)*;

factor: ID | NUMERO | LP expression RP | llamadaFuncion;

llamadaPrintf: PRINTF LP STRING (COMMA argumentos)? RP puntocoma;

argumentos: expression (COMMA expression)*;

declaracionFuncion: (INT | DOUBLE | FLOAT | VOID) ID LP parametros? RP bloque;

parametros: (INT | DOUBLE | FLOAT) ID (COMMA (INT | DOUBLE | FLOAT) ID)*;

returnStatement: RETURN (expression | NUMERO)? puntocoma;

comentario: COMENTARIO STRING puntocoma;*/

grammar compiladores;

@header {
package compiladores;
}

fragment LETRA : [A-Za-z] ;
fragment DIGITO : [0-9] ;

WS: [ \t\r\n]+ -> skip;

PYC: ';';
LLA: '{';
LLC: '}';
LP: '(';
RP: ')';
INT: 'int'; 
IF: 'if';
FOR: 'for';
WHILE: 'while';
ELSE: 'else';
RETURN: 'return';
PRINTF: 'printf'; 
VOID: 'void';
COMPARE: ('<' | '>' | '<=' | '>=' | '==' | '!=');
ACUM: ('++' | '--');
IGUAL: '=';
MAS: '+';
MENOS: '-';
MULT: '*';
DIV: '/';
DOUBLE: 'double';
FLOAT: 'float';
COMMA: ',';
PUNTO: '.';
COMENTARIO: '//';
STRING: '"' (~["\\] | '\\' .)* '"';

ID : (LETRA | '_')(LETRA | DIGITO | '_')* ;
NUMERO : (DIGITO+ | DIGITO+ PUNTO DIGITO+) ;

programa: (declaracionFuncion | instrucciones) EOF;

instrucciones: instruccion*;

instruccion: bloque
            | declaracion
            | asignacion
            | ifStatement
            | forStatement
            | whileStatement
            | declaracionFuncion
            | llamadaFuncion 
            | llamadaPrintf
            | returnStatement
            ;

bloque: LLA instrucciones LLC;

declaracion: (INT | DOUBLE | FLOAT) ID ((IGUAL(NUMERO | ID))|(COMMA ID))* PYC;

asignacion: (INT | DOUBLE | FLOAT)? ID IGUAL ( llamadaFuncion | expression | ID | NUMERO) PYC;

ifStatement: IF LP expression RP bloque (ELSE bloque)?;

forStatement: FOR LP forExpression RP bloque;

forExpression: asignacion expression PYC ID ACUM;

whileStatement: WHILE LP expression RP bloque;

llamadaFuncion: ID LP argumentos? RP ;

expression: term ((MAS | MENOS | COMPARE) term)*;

term: factor ((MULT | DIV) factor)*;

factor: ID | NUMERO | LP expression RP | llamadaFuncion;

llamadaPrintf: PRINTF LP STRING (COMMA argumentos)? RP PYC;

argumentos: expression (COMMA expression)*;

declaracionFuncion: (INT | DOUBLE | FLOAT | VOID) ID LP parametros? RP bloque;

parametros: (INT | DOUBLE | FLOAT) ID (COMMA (INT | DOUBLE | FLOAT) ID)*;

returnStatement: RETURN (expression | NUMERO)? PYC;

comentario: COMENTARIO STRING PYC;