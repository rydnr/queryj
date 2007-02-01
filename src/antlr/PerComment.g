//;-*- mode: antlr -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: PerCommentParser.g
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the ANTLR parser rules for physical e/r comments.
 *
 */
grammar PerComment;

@parser::header
{
  package org.acmsl.queryj.tools.antlr;
}

@parser::members {
    public static void main(String[] args) throws Exception {
        PerCommentLexer lex = new PerCommentLexer(new ANTLRFileStream(args[0]));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        PerCommentParser parser = new PerCommentLexer(tokens);

        try
        {
            parser.tableComment();
        }
        catch  (final RecognitionException exception) 
        {
            exception.printStackTrace();
        }
    }
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

tableComment : text ( tab_annotation )* ;
        
fragment text : TEXT;
        
fragment tab_annotation : ( tab_static | tab_isa | tab_isatype ) ;

fragment tab_static : STATIC id ;
fragment tab_isa : ISA id ;
fragment tab_isatype : ISATYPE id ;

columnComment : text ( col_annotation )* ;

fragment col_annotation : ( col_readonly | col_bool | col_isaref );
fragment col_readonly : READONLY id ;
fragment col_bool : BOOL id;
fragment col_isaref : ISAREF ( OPEN_PAREN id COMMA id CLOSE_PAREN )+ ;

fragment id : ID;

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

ID
    : ( LETTER | '_' ) (NAMECHAR)*
    ;

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+    { $channel = HIDDEN; } ;
        
TEXT  : (~'@')+ ;
        
fragment STATIC : '@static';
fragment ISA : '@isa';
fragment ISATYPE : '@isatype';
fragment ISAREF : '@isaref';
fragment READONLY : '@readonly';
fragment BOOL : '@bool';
fragment OPEN_PAREN : '(';
fragment CLOSE_PAREN : ')';
fragment COMMA : ',';

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

