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
 * $HeadURL: http://svn.acm-sl.org/queryj/branches/br-0_6--ventura24-2_0-0/src/main/antlr/PerComment.g $
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
 * The table decorator attribute.
 */
private boolean tableDecorator;

/**
 * The table relationship attribute.
 */
private String[][] tableRelationship;

/**
 * The column comment.
 */
private String columnComment;

/**
 * The column boolean <code>true</code> values.
 */
private String columnBoolTrue;

/**
 * The column boolean <code>false</code> values.
 */
private String columnBoolFalse;

/**
 * The column boolean <code>null</code> values.
 */
private String columnBoolNull;

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
 * Specifies whether the table-specific value-object will be wrapped
 * by custom decorators.
 * @param flag such flag.
 */
protected void setTableDecorator(final boolean flag)
{
    tableDecorator = flag;
}

/**
 * Retrieves whether the table-specific value-object will be wrapped
 * by custom decorators.
 * @return such information.
 */
public boolean getTableDecorator()
{
    return tableDecorator;
}

/**
 * Specifies the relationship this table models.
 * @param relationship such content.
 */
protected void setTableRelationship(final String[][] relationship)
{
    tableRelationship = relationship;
}

/**
 * Retrieves whether the table models a relationship.
 * @return such information.
 */
public String[][] getTableRelationship()
{
    return tableRelationship;
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
 * Specifies the value used as <code>true</code> for boolean attributes.
 * and how it denotes a <code>true</code> value.
 * @param value the value denoting <code>true</code> values.
 */
protected void setColumnBoolTrue(final String value)
{
    columnBoolTrue = value;
}

/**
 * Retrieves the value used as <code>true</code> for boolean attributes.
 * and how it denotes a <code>true</code> value.
 * @return  the <code>true</code> value, or null if
 * the column is not defined as boolean.
 */
public String getColumnBoolTrue()
{
    return columnBoolTrue;
}

/**
 * Specifies the value used as <code>false</code> for boolean attributes.
 * and how it denotes a <code>false</code> value.
 * @param value the value denoting <code>false</code> values.
 */
protected void setColumnBoolFalse(final String value)
{
    columnBoolFalse = value;
}

/**
 * Retrieves the value used as <code>false</code> for boolean attributes.
 * and how it denotes a <code>false</code> value.
 * @return  the <code>false</code> value, or null if
 * the column is not defined as boolean.
 */
public String getColumnBoolFalse()
{
    return columnBoolFalse;
}

/**
 * Specifies the value used as <code>null</code> for boolean attributes.
 * and how it denotes a <code>null</code> value.
 * @param value the value denoting <code>null</code> values.
 */
protected void setColumnBoolNull(final String value)
{
    columnBoolNull = value;
}

/**
 * Retrieves the value used as <code>null</code> for boolean attributes.
 * and how it denotes a <code>null</code> value.
 * @return  the <code>null</code> value, or null if
 * the column is not defined as boolean.
 */
public String getColumnBoolNull()
{
    return columnBoolNull;
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

/**
 * Called when a token mismatch occurs.
 * We override it to raise the exception,
 * rather than recovering, on mismatched token within alt.
 * @param input the input.
 * @param type the type.
 * @param follow whatever it means in ANTLR engine.
 * @throws RecognitionException always.
 */
protected void mismatch(
    final IntStream input, final int type, final BitSet follow)
  throws RecognitionException
{
    throw new MismatchedTokenException(type, input);
}
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

tableComment : (t=text)? ( tab_annotation )* { setTableComment(t); };
        
columnComment : (t=text)? ( col_annotation )* { setColumnComment(t); };

fragment text returns [String result]
@init { result = null; StringBuffer aux = new StringBuffer(); }
  : (  t=text_or_id  { aux.append($t.text); }
     | c=COMMA { aux.append($c.text); }
     | WS
     | OPEN_PAREN
     | CLOSE_PAREN
     | QUOTE )+
    { result = aux.toString(); }
  ;
        
fragment tab_annotation
  : (
        s=tab_static       { setTableStatic(s); }
      | i=tab_isa          { setTableIsa(i); }
      | t=tab_isatype      { setTableIsaType(t); }
      |   tab_decorator    { setTableDecorator(true); }
      |   tab_relationship
    )
  ;

fragment tab_static returns [String result]
@init { result = null; }
  : STATIC WS i=ident WS? { result = $i.text; }
  ;

fragment tab_isa returns [String result]
@init { result = null; }
  : ISA WS i=ident WS? { result = $i.text; }
  ;

fragment tab_isatype returns [String result]
@init { result = null; }
  : ISATYPE WS i=ident WS? { result = $i.text; }
  ;

fragment tab_decorator :  DECORATOR WS?;

fragment tab_relationship
@init
{
    List contents = new ArrayList();
    String first = null;
    String second = null;
}
  :  RELATIONSHIP
     WS
     (
       OPEN_PAREN WS?
       (  (SQUOTE a=text_or_id SQUOTE) { first = $a.text; }
        | (DQUOTE b=text_or_id DQUOTE) { first = $b.text; }
        | (c=text_or_id) { first = $c.text; })
       WS? COMMA WS?
       (  (SQUOTE d=ID SQUOTE) { second = $d.text; }
        | (DQUOTE e=ID DQUOTE) { second = $e.text; }
        | (f=ID) { second = $f.text; })
       WS? CLOSE_PAREN WS? COMMA?
       {
         contents.add(new String[] { trim(first), trim(second) });
       }
     )+
     {
       setTableRelationship((String[][]) contents.toArray(new String[0][0]));
     }
  ;

fragment col_annotation
  : (
         col_readonly { setColumnReadOnly(true); }
     |   col_bool
     |   col_isarefs
     | s=col_oraseq   { setColumnOraSeq(s); }
    );

fragment col_readonly :  READONLY WS?;

fragment col_bool
  : BOOL WS
    i=ident
    {
      setColumnBoolTrue($i.text);
    }
    WS? COMMA WS?
    j=ident { setColumnBoolFalse($j.text); }
    WS? (COMMA WS? k=ident WS? { setColumnBoolNull($k.text); })?
  ;

fragment text_or_id 
    :    ID
    |     TEXT
    ;

fragment ident returns [String text]
@init {text = null;}
    :   (SQUOTE i=ID SQUOTE) {text = ($i.text);}
    |   (DQUOTE j=ID DQUOTE) {text = ($j.text);}
    | k=ID {text = $k.text;}
    ;

fragment col_isarefs
@init
{
    List contents = new ArrayList();
    String first = null;
    String second = null;
}
  :  ISAREFS
     WS
     (
       OPEN_PAREN WS?
       (  (SQUOTE a=text_or_id SQUOTE) { first = $a.text; }
        | (DQUOTE b=text_or_id DQUOTE) { first = $b.text; }
        | (c=text_or_id) { first = $c.text; })
       WS? COMMA WS?
       (  (SQUOTE d=ID SQUOTE) { second = $d.text; }
        | (DQUOTE e=ID DQUOTE) { second = $e.text; }
        | (f=ID) { second = $f.text; })
       WS? CLOSE_PAREN WS? COMMA?
       {
         contents.add(new String[] { trim(first), trim(second) });
       }
     )+
     {
       setColumnIsaRefs((String[][]) contents.toArray(new String[0][0]));
     }
  ;

fragment col_oraseq returns [String result]
@init { result = null; }
  : ORASEQ WS i=ident WS? { result = $i.text; }
  ;

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

AT
    :  (  ('@static')    => STATIC    {$type = STATIC;}
        | ('@isa')       => ISA       {$type = ISA;}
        | ('@isatype')   => ISATYPE   {$type = ISATYPE;}
        | ('@decorator') => DECORATOR {$type = DECORATOR;}
        | ('@isarefs')   => ISAREFS   {$type = ISAREFS;}
        | ('@readonly')  => READONLY  {$type = READONLY;}
        | ('@bool')      => BOOL      {$type = BOOL;}
        | ('@oraseq')    => ORASEQ    {$type = ORASEQ;}
        | ('@relationship')  => RELATIONSHIP  {$type = RELATIONSHIP;}
        | '@')
    ;

SQUOTE : ('\'');
DQUOTE : ('"');
fragment QUOTE : (SQUOTE | DQUOTE);

// literals
OPEN_PAREN : '(';
CLOSE_PAREN : ')';
COMMA : ',';

// keywords
fragment STATIC : '@static';
fragment ISA : '@isa';
fragment ISATYPE : '@isatype';
fragment DECORATOR : '@decorator';
fragment ISAREFS : '@isarefs';
fragment READONLY : '@readonly';
fragment RELATIONSHIP : '@relationship';
fragment BOOL : '@bool';
fragment ORASEQ : '@oraseq';

WS : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+;// {$channel = HIDDEN;};

ID
    : (LETTER | '_' | DIGIT ) (NAMECHAR)* WS? {$type = ID;}
    ;

TEXT
   : (  (ID) => ID {$type = ID;}
      | ('\t') => WS {$type = WS;}
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
