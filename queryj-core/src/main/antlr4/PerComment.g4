//;-*- mode: antlr -*-
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
                        chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * (From http://svn.acm-sl.org/queryj/branches/br-0_6--ventura24-2_0-0/src/main/antlr/PerComment.g)
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the ANTLR parser rules for physical e/r comments.
 *
 */
grammar PerComment;

@parser::header
{
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
                        chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Generated from PerComment.g by ANTLR.
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR parser for PerComment.g
 *
 */
package org.acmsl.queryj.tools.antlr;
}

@lexer::header
{
/*
                        QueryJ

    Copyright (C) 2002-today  Jose San Leandro Armendariz
                        chous@acm-sl.org

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Generated from PerComment.g by ANTLR.
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR lexer for PerComment.g
 *
 */
package org.acmsl.queryj.tools.antlr;
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

tableComment : (text)? ( tabAnnotation )*;
        
columnComment : (text)? ( colAnnotation )*;

text :
    (  textOrId 
     | COMMA 
     | WS
     | OPEN_PAREN
     | CLOSE_PAREN
     | QUOTE )+
  ;
        
tabAnnotation
  : (
        tabStatic
      | tabIsa
      | tabIsatype
      | tabDecorator
      | tabRelationship
    )
  ;

tabStatic 
  : STATIC WS ident WS?
  ;

tabIsa
  : ISA WS ident WS?
  ;

tabIsatype
  : ISATYPE WS ident WS?
  ;

tabDecorator :  DECORATOR WS?;

tabRelationship
  :  RELATIONSHIP
     WS
     (
       OPEN_PAREN WS?
       (  (SQUOTE textOrId SQUOTE)
        | (DQUOTE textOrId DQUOTE)
        | (textOrId)
       )
       WS? COMMA WS?
       (  (SQUOTE ID SQUOTE)
        | (DQUOTE ID DQUOTE)
        | (ID)
       )
       WS? CLOSE_PAREN WS? COMMA?
     )+
  ;

colAnnotation
  : (
         colReadonly
     |   colBool
     |   colIsarefs
     |   colOraseq
    );

colReadonly :  READONLY WS?;

colBool
  : BOOL WS
    ident
    WS? COMMA WS?
    ident
    WS? (COMMA WS? ident WS?)?
  ;

textOrId 
    :    ID
    |    TEXT
    ;

ident 
    : (SQUOTE ID SQUOTE)
    | (DQUOTE ID DQUOTE)
    | ID
    ;

colIsarefs
  :  ISAREFS
     WS
     (
       OPEN_PAREN WS?
       (  (SQUOTE textOrId SQUOTE)
        | (DQUOTE textOrId DQUOTE)
        | (textOrId)
       )
       WS? COMMA WS?
       (  (SQUOTE ID SQUOTE)
        | (DQUOTE ID DQUOTE)
        | (ID)
       )
       WS? CLOSE_PAREN WS? COMMA?
     )+
  ;

colOraseq
  : ORASEQ WS ident WS?
  ;

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

AT
    :  (  STATIC
        | ISA
        | ISATYPE
        | DECORATOR
        | ISAREFS
        | READONLY
        | BOOL
        | ORASEQ
        | RELATIONSHIP
        | '@')
    ;

SQUOTE : ('\'');
DQUOTE : ('"');
QUOTE : (SQUOTE | DQUOTE);

// literals
OPEN_PAREN : '(';
CLOSE_PAREN : ')';
COMMA : ',';

// keywords
STATIC : '@static';
ISA : '@isa';
ISATYPE : '@isatype';
DECORATOR : '@decorator';
ISAREFS : '@isarefs';
READONLY : '@readonly';
RELATIONSHIP : '@relationship';
BOOL : '@bool';
ORASEQ : '@oraseq';

WS : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+;// {$channel = HIDDEN;};

ID
    : (LETTER | '_' | DIGIT ) (NAMECHAR)* WS?
    ;

TEXT
   : (  ID
      | WS
      | OPEN_PAREN
      | CLOSE_PAREN
      | COMMA
      | QUOTE
      | AT
      | (~('@'|','|'('|')'|'\''|'"')+))
   ;

fragment NAMECHAR
    : LETTER | DIGIT | '.' | '-' | '_' | ':' | '$'
    ;

fragment DIGIT
    :    '0'..'9'
    ;

fragment LETTER
    : 'a'..'z'
    | 'A'..'Z'
    ;
