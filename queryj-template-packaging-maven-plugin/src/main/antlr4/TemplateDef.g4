/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

grammar TemplateDef;

@parser::header
{
package org.acmsl.queryj.templates.packaging.antlr;
}

@lexer::header
{
package org.acmsl.queryj.templates.packaging.antlr;
}

def
:   name template_type output filename_builder package_name
    EOF;

name:
        'name' ':' Literal ';';

template_type:
        'type' ':' Literal ';';

output:
        'output' ':' Literal ';';

filename_builder:
        'filename' 'builder' ':' Literal ';';

package_name:
           'package' ':' Literal ';';

fragment
Literal
    :  ( EscapeSequence | ~('\\'|'"') )+
    ;

fragment
EscapeSequence
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UnicodeEscape
    |   OctalEscape
    ;

fragment
OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UnicodeEscape
    :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
    ;

fragment
HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

COMMENT
    :   '/*' .*? '*/'    -> channel(HIDDEN) // match anything between /* and */
    ;
WS  :   [ \r\t\u000C\n]+ -> channel(HIDDEN)
    ;

LINE_COMMENT
    : '//' ~[\r\n]* '\r'? '\n' -> channel(HIDDEN)
    ;