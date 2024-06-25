grammar compiladores;

@header {
package compiladores;
}

fragment LETRA : [A-Za-z];
fragment DIGITO : [0-9];

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

ID: (LETRA | '_')(LETRA | DIGITO | '_')*;
NUMERO: (DIGITO+ | DIGITO+ PUNTO DIGITO+);

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
            | comentario
            ;

bloque: LLA instrucciones LLC;

declaracion: (INT | DOUBLE | FLOAT) ID ((IGUAL (NUMERO | ID)) | (COMMA ID))* PYC | declaracionSinPYC;

declaracionSinPYC: (INT | DOUBLE | FLOAT) ID ((IGUAL (NUMERO | ID)) | (COMMA ID))* {notifyErrorListeners("missing ';' at end of declaration");};

asignacion: (INT | DOUBLE | FLOAT)? ID IGUAL (llamadaFuncion | expression | ID | NUMERO) PYC | asignacionSinPYC;

asignacionSinPYC: (INT | DOUBLE | FLOAT)? ID IGUAL (llamadaFuncion | expression | ID | NUMERO) {notifyErrorListeners("missing ';' at end of assignment");};

ifStatement: IF LP expression RP bloque (ELSE bloque)?;

forStatement: FOR LP forExpression RP bloque;

forExpression: asignacion expression PYC ID ACUM;

whileStatement: WHILE LP expression RP bloque;

llamadaFuncion: ID LP argumentos? RP;

expression: term ((MAS | MENOS | COMPARE) term)*;

term: factor ((MULT | DIV) factor)*;

factor: ID | NUMERO | LP expression RP | llamadaFuncion;

llamadaPrintf: PRINTF LP STRING (COMMA argumentos)? RP PYC;

argumentos: expression (COMMA expression)*;

declaracionFuncion: (INT | DOUBLE | FLOAT | VOID) ID LP parametros? RP bloque;

parametros: (INT | DOUBLE | FLOAT) ID (COMMA (INT | DOUBLE | FLOAT) ID)*;

returnStatement: RETURN (expression | NUMERO)? PYC;

comentario: COMENTARIO STRING PYC;
