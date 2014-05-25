grammar TemplateDef;

templateDef
:   nameRule typeRule outputRule filenameBuilderRule packageRule metadataRule? disabledRule? debugRule?
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

disabledRule:
        'disabled' ';';

debugRule:
        'debug' ';';

metadataRule:
        'metadata' ':' '{' keyValueRule+ '}';

keyValueRule:
        ID ':' ID ';';

ID : [a-zA-Z0-9\.\-_,<>]+ ;

COMMENT
    :   '/*' .*? '*/'    -> channel(HIDDEN) // match anything between /* and */
    ;
WS  :   [ \r\t\u000C\n]+ -> channel(HIDDEN)
    ;

LINE_COMMENT
    : '//' ~[\r\n]* '\r'? '\n' -> channel(HIDDEN)
    ;