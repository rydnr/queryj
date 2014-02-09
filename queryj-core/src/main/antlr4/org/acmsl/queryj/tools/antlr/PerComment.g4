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
                        QueryJ-Core

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
 * Generated from PerComment.g4 by ANTLR.
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR parser for PerComment.g4
 *
 */
}

@lexer::header
{
/*
                        QueryJ-Core

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
 * Generated from PerComment.g4 by ANTLR.
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR lexer for PerComment.g4
 *
 */
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

tableComment : (TEXT)? ( tabAnnotation )*;
        
columnComment : (TEXT)? ( colAnnotation )*;

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
  : STATIC ident
  ;

tabIsa
  : ISA ident
  ;

tabIsatype
  : ISATYPE ident
  ;

tabDecorator :  DECORATOR;

tabRelationship
  :  RELATIONSHIP
     (
       OPEN_PAREN 
       ident
       COMMA 
       ident
       CLOSE_PAREN
       COMMA?
     )+
  ;

colAnnotation
  : (
         colReadonly
     |   colBool
     |   colIsarefs
     |   colOraseq
    );

colReadonly :  READONLY;

colBool
  : BOOL
    ident
    COMMA
    ident
    (COMMA ident)?
  ;

ident 
    : (SQUOTE TEXT SQUOTE)
    | (DQUOTE TEXT DQUOTE)
    | TEXT
    ;

colIsarefs
  :  ISAREFS
     (
       OPEN_PAREN
       ident
       COMMA
       ident
       CLOSE_PAREN
       COMMA?
     )+
  ;

colOraseq
  : ORASEQ ident
  ;

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

WS : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+ -> skip;

SQUOTE : ('\'');
DQUOTE : ('"');
QUOTE : (SQUOTE | DQUOTE);

// literals
OPEN_PAREN : '(';
CLOSE_PAREN : ')';
COMMA : ',';

// keywords
STATIC : '@static';
ISATYPE : '@isatype';
ISAREFS : '@isarefs';
ISA : '@isa';
DECORATOR : '@decorator';
READONLY : '@readonly';
RELATIONSHIP : '@relationship';
BOOL : '@bool';
ORASEQ : '@oraseq';

TEXT
   : (  OPEN_PAREN
      | CLOSE_PAREN
      | COMMA
      | QUOTE
      | '@'
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

fragment DOT
    : '.'
    ;