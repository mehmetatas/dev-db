grammar Filter;

expression
 : LPAREN expression RPAREN                       #parenExpression
 | left=expression op=EQ right=expression         #eqExpression
 | left=expression op=NEQ right=expression        #neqExpression
 | left=expression op=LT right=expression         #ltExpression
 | left=expression op=LTE right=expression        #lteExpression
 | left=expression op=GT right=expression         #gtExpression
 | left=expression op=GTE right=expression        #gteExpression
 | left=expression op=IN right=array              #inExpression
 | left=expression op=AND right=expression        #andExpression
 | left=expression op=OR right=expression         #orExpression
 | IDENTIFIER ('.' IDENTIFIER)*                   #identifierExpression
 | value                                          #valueExpression
 ;

array
 : LBRACKET (value? | (value (COMMA value)+)) RBRACKET;

value
 : NUMBER                                         #numberExpression
 | STRING                                         #stringExpression
 | BOOL                                           #boolExpression
 | NULL                                           #nullExpression
 | DATE                                           #dateExpression
 | DATETIME                                       #dateTimeExpression
 | array                                          #arrayExpression
 ;

fragment DIGIT  : [0-9];
fragment DIGIT2 : DIGIT DIGIT;
fragment DIGIT3 : DIGIT DIGIT DIGIT;
fragment DIGIT4 : DIGIT DIGIT DIGIT DIGIT;
fragment TIME   : DIGIT2 ':' DIGIT2 ':' DIGIT2 ('.' DIGIT3)?;
fragment ITEM   : (NULL | STRING | NUMBER | BOOL | DATE | DATETIME);

AND        : '&';
OR         : '|';

EQ         : '=';
NEQ        : '!=';
LT         : '<';
LTE        : '<=';
GT         : '>';
GTE        : '>=';
IN         : 'in';

LPAREN     : '(';
RPAREN     : ')';
LBRACKET   : '[';
RBRACKET   : ']';
COMMA      : ',';

NUMBER     : '-'? DIGIT+ ( '.' DIGIT+ )?;
STRING     : '"' ('\\"' | ~'"')* '"';
BOOL       : 'true' | 'false';
NULL       : 'null';
DATE       : '@' DIGIT4 '-' DIGIT2 '-' DIGIT2;
DATETIME   : DATE ' ' TIME;

IDENTIFIER : [a-zA-Z_] [a-zA-Z_0-9]*;

WS         : [ \r\t\u000C\n]+ -> skip;