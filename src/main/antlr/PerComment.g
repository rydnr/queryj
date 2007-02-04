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
 * $HeadURL: $
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
 * Generated from PerComment.g by ANTLR.
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR parser for PerComment.g
 *
 */
package org.acmsl.queryj.tools.antlr;

/*
 * Importing some JDK classes.
 */
import java.util.List;
}

@lexer::header
{
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
 * Generated from PerComment.g by ANTLR.
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR lexer for PerComment.g
 *
 */
package org.acmsl.queryj.tools.antlr;
}

@parser::members
{
/**
 * An empty String String array.
 */
public static final String[][] EMPTY_STRING_STRING_ARRAY = new String[0][0];

/**
 * The table comment.
 */
private String tableComment;

/**
 * The table static.
 */
private String tableStatic;

/**
 * The table ISA.
 */
private String tableIsa;

/**
 * The table ISA-type.
 */
private String tableIsaType;

/**
 * The column comment.
 */
private String columnComment;

/**
 * The column boolean attribute.
 */
private String columnBool;

/**
 * Whether the column is readonly.
 */
private boolean columnReadOnly = false;;

/**
 * The column isa-ref mappings.
 */
private String[][] columnIsaRefs;

/**
 * The column oraseq attribute.
 */
private String columnOraSeq;

/**
 * Specifies the table comment.
 * @param comment such comment.
 */
protected void setTableComment(final String comment)
{
    tableComment = trim(comment);
}

/**
 * Retrieves the table comment.
 * @return such information.
 */
public String getTableComment()
{
    return tableComment;
}

/**
 * Specifies the column name used to distinguish the 
 * static content.
 * @param name such name.
 */
protected void setTableStatic(final String name)
{
    tableStatic = trim(name);
}

/**
 * Retrieves the column name used to distinguish the 
 * static content.
 * @return such column name.
 */
public String getTableStatic()
{
    return tableStatic;
}

/**
 * Specifies the table name defined as parent table.
 * @param name such name.
 */
protected void setTableIsa(final String name)
{
    tableIsa = trim(name);
}

/**
 * Retrieves the table name defined as parent table.
 * @return such table name.
 */
public String getTableIsa()
{
    return tableIsa;
}

/**
 * Specifies the table name whose descendants get identified
 * by the contents of this table.
 * @param name such name.
 */
protected void setTableIsaType(final String name)
{
    tableIsaType = trim(name);
}

/**
 * Retrieves the table name whose descendants get identified
 * by the contents of this table.
 * @return such table name.
 */
public String getTableIsaType()
{
    return tableIsaType;
}
/**
 * Specifies the column comment.
 * @param comment such comment.
 */
protected void setColumnComment(final String comment)
{
    columnComment = trim(comment);
}

/**
 * Retrieves the column comment.
 * @return such information.
 */
public String getColumnComment()
{
    return columnComment;
}

/**
 * Specifies whether the column is defined as boolean,
 * and how it denotes a <code>true</code> value.
 * @param value the <code>true</code> value, or null if
 * the column is not defined as boolean.
 */
protected void setColumnBool(final String value)
{
    columnBool = trim(value);
}

/**
 * Retrieves whether the column is defined as boolean,
 * and how it denotes a <code>true</code> value.
 * @return  the <code>true</code> value, or null if
 * the column is not defined as boolean.
 */
public String getColumnBool()
{
    return columnBool;
}

/**
 * Specifies whether the column is declared as read-only or not.
 * @param flag such flag.
 */
protected void setColumnReadOnly(final boolean flag)
{
    columnReadOnly = flag;
}

/**
 * Retrieves whether the column is declared as read-only or not.
 * @return such information.
 */
public boolean getColumnReadOnly()
{
    return columnReadOnly;
}

/**
 * Specifies the associations between column values and
 * table names (ISA implementations).
 * @param mappings the mappings.
 */
protected void setColumnIsaRefs(final String[][] mappings)
{
    columnIsaRefs = mappings;
}

/**
 * Retrieves the associations between column values and
 * table names (ISA implementations).
 * @return such mappings.
 */
public String[][] getColumnIsaRefs()
{
    return columnIsaRefs;
}

/**
 * Specifies the 'oraseq' attribute.
 * @param value such value.
 */
protected void setColumnOraSeq(final String value)
{
    columnOraSeq = trim(value);
}

/**
 * Retrieves the 'oraseq' attribute.
 * @return such information.
 */
public String getColumnOraSeq()
{
    return columnOraSeq;
}

/**
 * Trims given value.
 * @param value the value.
 */
protected String trim(final String value)
{
    String result = value;

    if  (result != null)
    {
        result = result.trim();
    }

    return result;
}
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

tableComment : WS? t=text ( tab_annotation )* { setTableComment(t); };
        
columnComment : WS? t=text ( col_annotation )* { setColumnComment(t); };

fragment text returns [String result]
@init { result = null; StringBuffer aux = new StringBuffer(); }
  : (  t=TEXT  { aux.append($t.text); }
     | q=QUOTE { aux.append($q.text); }
     | c=COMMA { aux.append($c.text); })+
    { result = aux.toString(); }
  ;
        
fragment tab_annotation
  : (
        s=tab_static  { setTableStatic(s); }
      | i=tab_isa     { setTableIsa(i); }
      | t=tab_isatype { setTableIsaType(t); }
    )
  ;

fragment tab_static returns [String result]
@init { result = null; }
  : STATIC WS i=identifier WS? { result = $i.text; }
  ;

fragment tab_isa returns [String result]
@init { result = null; }
  : ISA WS i=identifier WS? { result = $i.text; }
  ;

fragment tab_isatype returns [String result]
@init { result = null; }
  : ISATYPE WS i=identifier WS? { result = $i.text; }
  ;

fragment col_annotation
  : (
         col_readonly { setColumnReadOnly(true); }
     | b=col_bool     { setColumnBool(b); }
     |   col_isarefs
     | s=col_oraseq   { setColumnOraSeq(s); }
    );

fragment col_readonly :  READONLY WS?;

fragment col_bool returns [String result]
@init { result = null; }
  : BOOL WS i=identifier WS? { result = $i.text; }
  ;

fragment identifier : QUOTE? ID QUOTE?;

fragment col_isarefs
@init
{
    List contents = new ArrayList();
}
  :  ISAREFS
     WS
     (
       OPEN_PAREN WS? QUOTE? a=TEXT QUOTE? WS? COMMA WS? QUOTE? b=ID QUOTE? WS? CLOSE_PAREN WS? COMMA?
       {
         contents.add(new String[] { trim($a.text), trim($b.text) });
       }
     )+
     {
       setColumnIsaRefs((String[][]) contents.toArray(new String[0][0]));
     }
  ;

fragment col_oraseq returns [String result]
@init { result = null; }
  : ORASEQ WS i=identifier WS? { result = $i.text; }
  ;

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

AT
    :  (  ('@static')   => STATIC   {$type = STATIC;}
        | ('@isa')      => ISA      {$type = ISA;}
        | ('@isatype')  => ISATYPE  {$type = ISATYPE;}
        | ('@isarefs')  => ISAREFS  {$type = ISAREFS;}
        | ('@readonly') => READONLY {$type = READONLY;}
        | ('@bool')     => BOOL     {$type = BOOL;}
        | ('@oraseq')   => ORASEQ   {$type = ORASEQ;}
        | '@')
    ;

QUOTE : ('\'' | '"');

ID
    : ( LETTER | '_' ) (NAMECHAR)* WS? {$type = ID;}
    ;

// keywords
fragment STATIC : '@static';
fragment ISA : '@isa';
fragment ISATYPE : '@isatype';
fragment ISAREFS : '@isarefs';
fragment READONLY : '@readonly';
fragment BOOL : '@bool';
fragment ORASEQ : '@oraseq';

// literals
OPEN_PAREN : '(';
CLOSE_PAREN : ')';
COMMA : ',';

WS : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+;// {$channel = HIDDEN;};

TEXT
   : (  ('\t') => WS {$type = WS;}
      | (' ')  => WS {$type = WS;}
      | ('\r') => WS {$type = WS;}
      | ('\n') => WS {$type = WS;}
      | ('\u000C') => WS {$type = WS;}
      | ('(') => OPEN_PAREN {$type = OPEN_PAREN;}
      | (')') => CLOSE_PAREN {$type = CLOSE_PAREN;}
      | (',') => COMMA {$type = COMMA;}
      | ('\''|'"') => QUOTE {$type = QUOTE;}
      | ('@') => AT
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
