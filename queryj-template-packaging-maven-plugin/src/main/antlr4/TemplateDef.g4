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

templateDef
:   nameRule typeRule outputRule filenameBuilderRule packageRule
    EOF;

nameRule:
        'name' ':' ID ';';

typeRule:
        'type' ':' ID ';';

outputRule:
        'output' ':' ID ';';

filenameBuilderRule:
        'filename' 'builder' ':' ID ';';

packageRule:
           'package' ':' ID ';';

ID : [a-zA-Z0-9\.\-_,<>]+ ;

COMMENT
    :   '/*' .*? '*/'    -> channel(HIDDEN) // match anything between /* and */
    ;
WS  :   [ \r\t\u000C\n]+ -> channel(HIDDEN)
    ;

LINE_COMMENT
    : '//' ~[\r\n]* '\r'? '\n' -> channel(HIDDEN)
    ;