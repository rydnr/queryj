// $ANTLR 3.4 PerComment.g 2012-06-22 06:18:25

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


import org.antlr.runtime.*;

import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class PerCommentParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AT", "BOOL", "CLOSE_PAREN", "COMMA", "DECORATOR", "DIGIT", "DQUOTE", "ID", "ISA", "ISAREFS", "ISATYPE", "LETTER", "NAMECHAR", "OPEN_PAREN", "ORASEQ", "QUOTE", "READONLY", "RELATIONSHIP", "SQUOTE", "STATIC", "TEXT", "WS"
    };

    public static final int EOF=-1;
    public static final int AT=4;
    public static final int BOOL=5;
    public static final int CLOSE_PAREN=6;
    public static final int COMMA=7;
    public static final int DECORATOR=8;
    public static final int DIGIT=9;
    public static final int DQUOTE=10;
    public static final int ID=11;
    public static final int ISA=12;
    public static final int ISAREFS=13;
    public static final int ISATYPE=14;
    public static final int LETTER=15;
    public static final int NAMECHAR=16;
    public static final int OPEN_PAREN=17;
    public static final int ORASEQ=18;
    public static final int QUOTE=19;
    public static final int READONLY=20;
    public static final int RELATIONSHIP=21;
    public static final int SQUOTE=22;
    public static final int STATIC=23;
    public static final int TEXT=24;
    public static final int WS=25;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public PerCommentParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public PerCommentParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return PerCommentParser.tokenNames; }
    public String getGrammarFileName() { return "PerComment.g"; }


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



    // $ANTLR start "tableComment"
    // PerComment.g:490:1: tableComment : (t= text )? ( tab_annotation )* ;
    public final void tableComment() throws RecognitionException {
        String t =null;


        try {
            // PerComment.g:490:14: ( (t= text )? ( tab_annotation )* )
            // PerComment.g:490:16: (t= text )? ( tab_annotation )*
            {
            // PerComment.g:490:16: (t= text )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( ((LA1_0 >= CLOSE_PAREN && LA1_0 <= COMMA)||LA1_0==ID||LA1_0==OPEN_PAREN||LA1_0==QUOTE||(LA1_0 >= TEXT && LA1_0 <= WS)) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // PerComment.g:490:17: t= text
                    {
                    pushFollow(FOLLOW_text_in_tableComment46);
                    t=text();

                    state._fsp--;


                    }
                    break;

            }


            // PerComment.g:490:26: ( tab_annotation )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==DECORATOR||LA2_0==ISA||LA2_0==ISATYPE||LA2_0==RELATIONSHIP||LA2_0==STATIC) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // PerComment.g:490:28: tab_annotation
            	    {
            	    pushFollow(FOLLOW_tab_annotation_in_tableComment52);
            	    tab_annotation();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


             setTableComment(t); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "tableComment"



    // $ANTLR start "columnComment"
    // PerComment.g:492:1: columnComment : (t= text )? ( col_annotation )* ;
    public final void columnComment() throws RecognitionException {
        String t =null;


        try {
            // PerComment.g:492:15: ( (t= text )? ( col_annotation )* )
            // PerComment.g:492:17: (t= text )? ( col_annotation )*
            {
            // PerComment.g:492:17: (t= text )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( ((LA3_0 >= CLOSE_PAREN && LA3_0 <= COMMA)||LA3_0==ID||LA3_0==OPEN_PAREN||LA3_0==QUOTE||(LA3_0 >= TEXT && LA3_0 <= WS)) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // PerComment.g:492:18: t= text
                    {
                    pushFollow(FOLLOW_text_in_columnComment76);
                    t=text();

                    state._fsp--;


                    }
                    break;

            }


            // PerComment.g:492:27: ( col_annotation )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==BOOL||LA4_0==ISAREFS||LA4_0==ORASEQ||LA4_0==READONLY) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // PerComment.g:492:29: col_annotation
            	    {
            	    pushFollow(FOLLOW_col_annotation_in_columnComment82);
            	    col_annotation();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


             setColumnComment(t); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "columnComment"



    // $ANTLR start "text"
    // PerComment.g:494:10: fragment text returns [String result] : (t= text_or_id |c= COMMA | WS | OPEN_PAREN | CLOSE_PAREN | QUOTE )+ ;
    public final String text() throws RecognitionException {
        String result = null;


        Token c=null;
        PerCommentParser.text_or_id_return t =null;


         result = null; StringBuffer aux = new StringBuffer(); 
        try {
            // PerComment.g:496:3: ( (t= text_or_id |c= COMMA | WS | OPEN_PAREN | CLOSE_PAREN | QUOTE )+ )
            // PerComment.g:496:5: (t= text_or_id |c= COMMA | WS | OPEN_PAREN | CLOSE_PAREN | QUOTE )+
            {
            // PerComment.g:496:5: (t= text_or_id |c= COMMA | WS | OPEN_PAREN | CLOSE_PAREN | QUOTE )+
            int cnt5=0;
            loop5:
            do {
                int alt5=7;
                switch ( input.LA(1) ) {
                case ID:
                case TEXT:
                    {
                    alt5=1;
                    }
                    break;
                case COMMA:
                    {
                    alt5=2;
                    }
                    break;
                case WS:
                    {
                    alt5=3;
                    }
                    break;
                case OPEN_PAREN:
                    {
                    alt5=4;
                    }
                    break;
                case CLOSE_PAREN:
                    {
                    alt5=5;
                    }
                    break;
                case QUOTE:
                    {
                    alt5=6;
                    }
                    break;

                }

                switch (alt5) {
            	case 1 :
            	    // PerComment.g:496:8: t= text_or_id
            	    {
            	    pushFollow(FOLLOW_text_or_id_in_text113);
            	    t=text_or_id();

            	    state._fsp--;


            	     aux.append((t!=null?input.toString(t.start,t.stop):null)); 

            	    }
            	    break;
            	case 2 :
            	    // PerComment.g:497:8: c= COMMA
            	    {
            	    c=(Token)match(input,COMMA,FOLLOW_COMMA_in_text127); 

            	     aux.append((c!=null?c.getText():null)); 

            	    }
            	    break;
            	case 3 :
            	    // PerComment.g:498:8: WS
            	    {
            	    match(input,WS,FOLLOW_WS_in_text138); 

            	    }
            	    break;
            	case 4 :
            	    // PerComment.g:499:8: OPEN_PAREN
            	    {
            	    match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_text147); 

            	    }
            	    break;
            	case 5 :
            	    // PerComment.g:500:8: CLOSE_PAREN
            	    {
            	    match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_text156); 

            	    }
            	    break;
            	case 6 :
            	    // PerComment.g:501:8: QUOTE
            	    {
            	    match(input,QUOTE,FOLLOW_QUOTE_in_text165); 

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


             result = aux.toString(); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return result;
    }
    // $ANTLR end "text"



    // $ANTLR start "tab_annotation"
    // PerComment.g:505:10: fragment tab_annotation : (s= tab_static |i= tab_isa |t= tab_isatype | tab_decorator | tab_relationship ) ;
    public final void tab_annotation() throws RecognitionException {
        String s =null;

        String i =null;

        String t =null;


        try {
            // PerComment.g:506:3: ( (s= tab_static |i= tab_isa |t= tab_isatype | tab_decorator | tab_relationship ) )
            // PerComment.g:506:5: (s= tab_static |i= tab_isa |t= tab_isatype | tab_decorator | tab_relationship )
            {
            // PerComment.g:506:5: (s= tab_static |i= tab_isa |t= tab_isatype | tab_decorator | tab_relationship )
            int alt6=5;
            switch ( input.LA(1) ) {
            case STATIC:
                {
                alt6=1;
                }
                break;
            case ISA:
                {
                alt6=2;
                }
                break;
            case ISATYPE:
                {
                alt6=3;
                }
                break;
            case DECORATOR:
                {
                alt6=4;
                }
                break;
            case RELATIONSHIP:
                {
                alt6=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }

            switch (alt6) {
                case 1 :
                    // PerComment.g:507:9: s= tab_static
                    {
                    pushFollow(FOLLOW_tab_static_in_tab_annotation209);
                    s=tab_static();

                    state._fsp--;


                     setTableStatic(s); 

                    }
                    break;
                case 2 :
                    // PerComment.g:508:9: i= tab_isa
                    {
                    pushFollow(FOLLOW_tab_isa_in_tab_annotation229);
                    i=tab_isa();

                    state._fsp--;


                     setTableIsa(i); 

                    }
                    break;
                case 3 :
                    // PerComment.g:509:9: t= tab_isatype
                    {
                    pushFollow(FOLLOW_tab_isatype_in_tab_annotation252);
                    t=tab_isatype();

                    state._fsp--;


                     setTableIsaType(t); 

                    }
                    break;
                case 4 :
                    // PerComment.g:510:11: tab_decorator
                    {
                    pushFollow(FOLLOW_tab_decorator_in_tab_annotation271);
                    tab_decorator();

                    state._fsp--;


                     setTableDecorator(true); 

                    }
                    break;
                case 5 :
                    // PerComment.g:511:11: tab_relationship
                    {
                    pushFollow(FOLLOW_tab_relationship_in_tab_annotation288);
                    tab_relationship();

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "tab_annotation"



    // $ANTLR start "tab_static"
    // PerComment.g:515:10: fragment tab_static returns [String result] : STATIC WS i= ident ( WS )? ;
    public final String tab_static() throws RecognitionException {
        String result = null;


        String i =null;


         result = null; 
        try {
            // PerComment.g:517:3: ( STATIC WS i= ident ( WS )? )
            // PerComment.g:517:5: STATIC WS i= ident ( WS )?
            {
            match(input,STATIC,FOLLOW_STATIC_in_tab_static318); 

            match(input,WS,FOLLOW_WS_in_tab_static320); 

            pushFollow(FOLLOW_ident_in_tab_static324);
            i=ident();

            state._fsp--;


            // PerComment.g:517:23: ( WS )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==WS) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // PerComment.g:517:23: WS
                    {
                    match(input,WS,FOLLOW_WS_in_tab_static326); 

                    }
                    break;

            }


             result = i; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return result;
    }
    // $ANTLR end "tab_static"



    // $ANTLR start "tab_isa"
    // PerComment.g:520:10: fragment tab_isa returns [String result] : ISA WS i= ident ( WS )? ;
    public final String tab_isa() throws RecognitionException {
        String result = null;


        String i =null;


         result = null; 
        try {
            // PerComment.g:522:3: ( ISA WS i= ident ( WS )? )
            // PerComment.g:522:5: ISA WS i= ident ( WS )?
            {
            match(input,ISA,FOLLOW_ISA_in_tab_isa353); 

            match(input,WS,FOLLOW_WS_in_tab_isa355); 

            pushFollow(FOLLOW_ident_in_tab_isa359);
            i=ident();

            state._fsp--;


            // PerComment.g:522:20: ( WS )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==WS) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // PerComment.g:522:20: WS
                    {
                    match(input,WS,FOLLOW_WS_in_tab_isa361); 

                    }
                    break;

            }


             result = i; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return result;
    }
    // $ANTLR end "tab_isa"



    // $ANTLR start "tab_isatype"
    // PerComment.g:525:10: fragment tab_isatype returns [String result] : ISATYPE WS i= ident ( WS )? ;
    public final String tab_isatype() throws RecognitionException {
        String result = null;


        String i =null;


         result = null; 
        try {
            // PerComment.g:527:3: ( ISATYPE WS i= ident ( WS )? )
            // PerComment.g:527:5: ISATYPE WS i= ident ( WS )?
            {
            match(input,ISATYPE,FOLLOW_ISATYPE_in_tab_isatype388); 

            match(input,WS,FOLLOW_WS_in_tab_isatype390); 

            pushFollow(FOLLOW_ident_in_tab_isatype394);
            i=ident();

            state._fsp--;


            // PerComment.g:527:24: ( WS )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==WS) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // PerComment.g:527:24: WS
                    {
                    match(input,WS,FOLLOW_WS_in_tab_isatype396); 

                    }
                    break;

            }


             result = i; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return result;
    }
    // $ANTLR end "tab_isatype"



    // $ANTLR start "tab_decorator"
    // PerComment.g:530:10: fragment tab_decorator : DECORATOR ( WS )? ;
    public final void tab_decorator() throws RecognitionException {
        try {
            // PerComment.g:530:24: ( DECORATOR ( WS )? )
            // PerComment.g:530:27: DECORATOR ( WS )?
            {
            match(input,DECORATOR,FOLLOW_DECORATOR_in_tab_decorator413); 

            // PerComment.g:530:37: ( WS )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==WS) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // PerComment.g:530:37: WS
                    {
                    match(input,WS,FOLLOW_WS_in_tab_decorator415); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "tab_decorator"



    // $ANTLR start "tab_relationship"
    // PerComment.g:532:10: fragment tab_relationship : RELATIONSHIP WS ( OPEN_PAREN ( WS )? ( ( SQUOTE a= text_or_id SQUOTE ) | ( DQUOTE b= text_or_id DQUOTE ) | (c= text_or_id ) ) ( WS )? COMMA ( WS )? ( ( SQUOTE d= ID SQUOTE ) | ( DQUOTE e= ID DQUOTE ) | (f= ID ) ) ( WS )? CLOSE_PAREN ( WS )? ( COMMA )? )+ ;
    public final void tab_relationship() throws RecognitionException {
        Token d=null;
        Token e=null;
        Token f=null;
        PerCommentParser.text_or_id_return a =null;

        PerCommentParser.text_or_id_return b =null;

        PerCommentParser.text_or_id_return c =null;



            List contents = new ArrayList();
            String first = null;
            String second = null;

        try {
            // PerComment.g:539:3: ( RELATIONSHIP WS ( OPEN_PAREN ( WS )? ( ( SQUOTE a= text_or_id SQUOTE ) | ( DQUOTE b= text_or_id DQUOTE ) | (c= text_or_id ) ) ( WS )? COMMA ( WS )? ( ( SQUOTE d= ID SQUOTE ) | ( DQUOTE e= ID DQUOTE ) | (f= ID ) ) ( WS )? CLOSE_PAREN ( WS )? ( COMMA )? )+ )
            // PerComment.g:539:6: RELATIONSHIP WS ( OPEN_PAREN ( WS )? ( ( SQUOTE a= text_or_id SQUOTE ) | ( DQUOTE b= text_or_id DQUOTE ) | (c= text_or_id ) ) ( WS )? COMMA ( WS )? ( ( SQUOTE d= ID SQUOTE ) | ( DQUOTE e= ID DQUOTE ) | (f= ID ) ) ( WS )? CLOSE_PAREN ( WS )? ( COMMA )? )+
            {
            match(input,RELATIONSHIP,FOLLOW_RELATIONSHIP_in_tab_relationship434); 

            match(input,WS,FOLLOW_WS_in_tab_relationship441); 

            // PerComment.g:541:6: ( OPEN_PAREN ( WS )? ( ( SQUOTE a= text_or_id SQUOTE ) | ( DQUOTE b= text_or_id DQUOTE ) | (c= text_or_id ) ) ( WS )? COMMA ( WS )? ( ( SQUOTE d= ID SQUOTE ) | ( DQUOTE e= ID DQUOTE ) | (f= ID ) ) ( WS )? CLOSE_PAREN ( WS )? ( COMMA )? )+
            int cnt19=0;
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==OPEN_PAREN) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // PerComment.g:542:8: OPEN_PAREN ( WS )? ( ( SQUOTE a= text_or_id SQUOTE ) | ( DQUOTE b= text_or_id DQUOTE ) | (c= text_or_id ) ) ( WS )? COMMA ( WS )? ( ( SQUOTE d= ID SQUOTE ) | ( DQUOTE e= ID DQUOTE ) | (f= ID ) ) ( WS )? CLOSE_PAREN ( WS )? ( COMMA )?
            	    {
            	    match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_tab_relationship457); 

            	    // PerComment.g:542:19: ( WS )?
            	    int alt11=2;
            	    int LA11_0 = input.LA(1);

            	    if ( (LA11_0==WS) ) {
            	        alt11=1;
            	    }
            	    switch (alt11) {
            	        case 1 :
            	            // PerComment.g:542:19: WS
            	            {
            	            match(input,WS,FOLLOW_WS_in_tab_relationship459); 

            	            }
            	            break;

            	    }


            	    // PerComment.g:543:8: ( ( SQUOTE a= text_or_id SQUOTE ) | ( DQUOTE b= text_or_id DQUOTE ) | (c= text_or_id ) )
            	    int alt12=3;
            	    switch ( input.LA(1) ) {
            	    case SQUOTE:
            	        {
            	        alt12=1;
            	        }
            	        break;
            	    case DQUOTE:
            	        {
            	        alt12=2;
            	        }
            	        break;
            	    case ID:
            	    case TEXT:
            	        {
            	        alt12=3;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 12, 0, input);

            	        throw nvae;

            	    }

            	    switch (alt12) {
            	        case 1 :
            	            // PerComment.g:543:11: ( SQUOTE a= text_or_id SQUOTE )
            	            {
            	            // PerComment.g:543:11: ( SQUOTE a= text_or_id SQUOTE )
            	            // PerComment.g:543:12: SQUOTE a= text_or_id SQUOTE
            	            {
            	            match(input,SQUOTE,FOLLOW_SQUOTE_in_tab_relationship473); 

            	            pushFollow(FOLLOW_text_or_id_in_tab_relationship477);
            	            a=text_or_id();

            	            state._fsp--;


            	            match(input,SQUOTE,FOLLOW_SQUOTE_in_tab_relationship479); 

            	            }


            	             first = (a!=null?input.toString(a.start,a.stop):null); 

            	            }
            	            break;
            	        case 2 :
            	            // PerComment.g:544:11: ( DQUOTE b= text_or_id DQUOTE )
            	            {
            	            // PerComment.g:544:11: ( DQUOTE b= text_or_id DQUOTE )
            	            // PerComment.g:544:12: DQUOTE b= text_or_id DQUOTE
            	            {
            	            match(input,DQUOTE,FOLLOW_DQUOTE_in_tab_relationship495); 

            	            pushFollow(FOLLOW_text_or_id_in_tab_relationship499);
            	            b=text_or_id();

            	            state._fsp--;


            	            match(input,DQUOTE,FOLLOW_DQUOTE_in_tab_relationship501); 

            	            }


            	             first = (b!=null?input.toString(b.start,b.stop):null); 

            	            }
            	            break;
            	        case 3 :
            	            // PerComment.g:545:11: (c= text_or_id )
            	            {
            	            // PerComment.g:545:11: (c= text_or_id )
            	            // PerComment.g:545:12: c= text_or_id
            	            {
            	            pushFollow(FOLLOW_text_or_id_in_tab_relationship519);
            	            c=text_or_id();

            	            state._fsp--;


            	            }


            	             first = (c!=null?input.toString(c.start,c.stop):null); 

            	            }
            	            break;

            	    }


            	    // PerComment.g:546:8: ( WS )?
            	    int alt13=2;
            	    int LA13_0 = input.LA(1);

            	    if ( (LA13_0==WS) ) {
            	        alt13=1;
            	    }
            	    switch (alt13) {
            	        case 1 :
            	            // PerComment.g:546:8: WS
            	            {
            	            match(input,WS,FOLLOW_WS_in_tab_relationship532); 

            	            }
            	            break;

            	    }


            	    match(input,COMMA,FOLLOW_COMMA_in_tab_relationship535); 

            	    // PerComment.g:546:18: ( WS )?
            	    int alt14=2;
            	    int LA14_0 = input.LA(1);

            	    if ( (LA14_0==WS) ) {
            	        alt14=1;
            	    }
            	    switch (alt14) {
            	        case 1 :
            	            // PerComment.g:546:18: WS
            	            {
            	            match(input,WS,FOLLOW_WS_in_tab_relationship537); 

            	            }
            	            break;

            	    }


            	    // PerComment.g:547:8: ( ( SQUOTE d= ID SQUOTE ) | ( DQUOTE e= ID DQUOTE ) | (f= ID ) )
            	    int alt15=3;
            	    switch ( input.LA(1) ) {
            	    case SQUOTE:
            	        {
            	        alt15=1;
            	        }
            	        break;
            	    case DQUOTE:
            	        {
            	        alt15=2;
            	        }
            	        break;
            	    case ID:
            	        {
            	        alt15=3;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 15, 0, input);

            	        throw nvae;

            	    }

            	    switch (alt15) {
            	        case 1 :
            	            // PerComment.g:547:11: ( SQUOTE d= ID SQUOTE )
            	            {
            	            // PerComment.g:547:11: ( SQUOTE d= ID SQUOTE )
            	            // PerComment.g:547:12: SQUOTE d= ID SQUOTE
            	            {
            	            match(input,SQUOTE,FOLLOW_SQUOTE_in_tab_relationship551); 

            	            d=(Token)match(input,ID,FOLLOW_ID_in_tab_relationship555); 

            	            match(input,SQUOTE,FOLLOW_SQUOTE_in_tab_relationship557); 

            	            }


            	             second = (d!=null?d.getText():null); 

            	            }
            	            break;
            	        case 2 :
            	            // PerComment.g:548:11: ( DQUOTE e= ID DQUOTE )
            	            {
            	            // PerComment.g:548:11: ( DQUOTE e= ID DQUOTE )
            	            // PerComment.g:548:12: DQUOTE e= ID DQUOTE
            	            {
            	            match(input,DQUOTE,FOLLOW_DQUOTE_in_tab_relationship573); 

            	            e=(Token)match(input,ID,FOLLOW_ID_in_tab_relationship577); 

            	            match(input,DQUOTE,FOLLOW_DQUOTE_in_tab_relationship579); 

            	            }


            	             second = (e!=null?e.getText():null); 

            	            }
            	            break;
            	        case 3 :
            	            // PerComment.g:549:11: (f= ID )
            	            {
            	            // PerComment.g:549:11: (f= ID )
            	            // PerComment.g:549:12: f= ID
            	            {
            	            f=(Token)match(input,ID,FOLLOW_ID_in_tab_relationship597); 

            	            }


            	             second = (f!=null?f.getText():null); 

            	            }
            	            break;

            	    }


            	    // PerComment.g:550:8: ( WS )?
            	    int alt16=2;
            	    int LA16_0 = input.LA(1);

            	    if ( (LA16_0==WS) ) {
            	        alt16=1;
            	    }
            	    switch (alt16) {
            	        case 1 :
            	            // PerComment.g:550:8: WS
            	            {
            	            match(input,WS,FOLLOW_WS_in_tab_relationship610); 

            	            }
            	            break;

            	    }


            	    match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_tab_relationship613); 

            	    // PerComment.g:550:24: ( WS )?
            	    int alt17=2;
            	    int LA17_0 = input.LA(1);

            	    if ( (LA17_0==WS) ) {
            	        alt17=1;
            	    }
            	    switch (alt17) {
            	        case 1 :
            	            // PerComment.g:550:24: WS
            	            {
            	            match(input,WS,FOLLOW_WS_in_tab_relationship615); 

            	            }
            	            break;

            	    }


            	    // PerComment.g:550:28: ( COMMA )?
            	    int alt18=2;
            	    int LA18_0 = input.LA(1);

            	    if ( (LA18_0==COMMA) ) {
            	        alt18=1;
            	    }
            	    switch (alt18) {
            	        case 1 :
            	            // PerComment.g:550:28: COMMA
            	            {
            	            match(input,COMMA,FOLLOW_COMMA_in_tab_relationship618); 

            	            }
            	            break;

            	    }



            	             contents.add(new String[] { trim(first), trim(second) });
            	           

            	    }
            	    break;

            	default :
            	    if ( cnt19 >= 1 ) break loop19;
                        EarlyExitException eee =
                            new EarlyExitException(19, input);
                        throw eee;
                }
                cnt19++;
            } while (true);



                   setTableRelationship((String[][]) contents.toArray(new String[0][0]));
                 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "tab_relationship"



    // $ANTLR start "col_annotation"
    // PerComment.g:560:10: fragment col_annotation : ( col_readonly | col_bool | col_isarefs |s= col_oraseq ) ;
    public final void col_annotation() throws RecognitionException {
        String s =null;


        try {
            // PerComment.g:561:3: ( ( col_readonly | col_bool | col_isarefs |s= col_oraseq ) )
            // PerComment.g:561:5: ( col_readonly | col_bool | col_isarefs |s= col_oraseq )
            {
            // PerComment.g:561:5: ( col_readonly | col_bool | col_isarefs |s= col_oraseq )
            int alt20=4;
            switch ( input.LA(1) ) {
            case READONLY:
                {
                alt20=1;
                }
                break;
            case BOOL:
                {
                alt20=2;
                }
                break;
            case ISAREFS:
                {
                alt20=3;
                }
                break;
            case ORASEQ:
                {
                alt20=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;

            }

            switch (alt20) {
                case 1 :
                    // PerComment.g:562:10: col_readonly
                    {
                    pushFollow(FOLLOW_col_readonly_in_col_annotation669);
                    col_readonly();

                    state._fsp--;


                     setColumnReadOnly(true); 

                    }
                    break;
                case 2 :
                    // PerComment.g:563:10: col_bool
                    {
                    pushFollow(FOLLOW_col_bool_in_col_annotation682);
                    col_bool();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // PerComment.g:564:10: col_isarefs
                    {
                    pushFollow(FOLLOW_col_isarefs_in_col_annotation693);
                    col_isarefs();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // PerComment.g:565:8: s= col_oraseq
                    {
                    pushFollow(FOLLOW_col_oraseq_in_col_annotation704);
                    s=col_oraseq();

                    state._fsp--;


                     setColumnOraSeq(s); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "col_annotation"



    // $ANTLR start "col_readonly"
    // PerComment.g:568:10: fragment col_readonly : READONLY ( WS )? ;
    public final void col_readonly() throws RecognitionException {
        try {
            // PerComment.g:568:23: ( READONLY ( WS )? )
            // PerComment.g:568:26: READONLY ( WS )?
            {
            match(input,READONLY,FOLLOW_READONLY_in_col_readonly725); 

            // PerComment.g:568:35: ( WS )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==WS) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // PerComment.g:568:35: WS
                    {
                    match(input,WS,FOLLOW_WS_in_col_readonly727); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "col_readonly"



    // $ANTLR start "col_bool"
    // PerComment.g:570:10: fragment col_bool : BOOL WS i= ident ( WS )? COMMA ( WS )? j= ident ( WS )? ( COMMA ( WS )? k= ident ( WS )? )? ;
    public final void col_bool() throws RecognitionException {
        String i =null;

        String j =null;

        String k =null;


        try {
            // PerComment.g:571:3: ( BOOL WS i= ident ( WS )? COMMA ( WS )? j= ident ( WS )? ( COMMA ( WS )? k= ident ( WS )? )? )
            // PerComment.g:571:5: BOOL WS i= ident ( WS )? COMMA ( WS )? j= ident ( WS )? ( COMMA ( WS )? k= ident ( WS )? )?
            {
            match(input,BOOL,FOLLOW_BOOL_in_col_bool740); 

            match(input,WS,FOLLOW_WS_in_col_bool742); 

            pushFollow(FOLLOW_ident_in_col_bool750);
            i=ident();

            state._fsp--;



                  setColumnBoolTrue(i);
                

            // PerComment.g:576:5: ( WS )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==WS) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // PerComment.g:576:5: WS
                    {
                    match(input,WS,FOLLOW_WS_in_col_bool762); 

                    }
                    break;

            }


            match(input,COMMA,FOLLOW_COMMA_in_col_bool765); 

            // PerComment.g:576:15: ( WS )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==WS) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // PerComment.g:576:15: WS
                    {
                    match(input,WS,FOLLOW_WS_in_col_bool767); 

                    }
                    break;

            }


            pushFollow(FOLLOW_ident_in_col_bool776);
            j=ident();

            state._fsp--;


             setColumnBoolFalse(j); 

            // PerComment.g:578:5: ( WS )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==WS) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // PerComment.g:578:5: WS
                    {
                    match(input,WS,FOLLOW_WS_in_col_bool784); 

                    }
                    break;

            }


            // PerComment.g:578:9: ( COMMA ( WS )? k= ident ( WS )? )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==COMMA) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // PerComment.g:578:10: COMMA ( WS )? k= ident ( WS )?
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_col_bool788); 

                    // PerComment.g:578:16: ( WS )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==WS) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // PerComment.g:578:16: WS
                            {
                            match(input,WS,FOLLOW_WS_in_col_bool790); 

                            }
                            break;

                    }


                    pushFollow(FOLLOW_ident_in_col_bool795);
                    k=ident();

                    state._fsp--;


                    // PerComment.g:578:28: ( WS )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==WS) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // PerComment.g:578:28: WS
                            {
                            match(input,WS,FOLLOW_WS_in_col_bool797); 

                            }
                            break;

                    }


                     setColumnBoolNull(k); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "col_bool"


    public static class text_or_id_return extends ParserRuleReturnScope {
    };


    // $ANTLR start "text_or_id"
    // PerComment.g:581:10: fragment text_or_id : ( ID | TEXT );
    public final PerCommentParser.text_or_id_return text_or_id() throws RecognitionException {
        PerCommentParser.text_or_id_return retval = new PerCommentParser.text_or_id_return();
        retval.start = input.LT(1);


        try {
            // PerComment.g:582:5: ( ID | TEXT )
            // PerComment.g:
            {
            if ( input.LA(1)==ID||input.LA(1)==TEXT ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "text_or_id"



    // $ANTLR start "ident"
    // PerComment.g:586:10: fragment ident returns [String text] : ( ( SQUOTE i= ID SQUOTE ) | ( DQUOTE j= ID DQUOTE ) |k= ID );
    public final String ident() throws RecognitionException {
        String text = null;


        Token i=null;
        Token j=null;
        Token k=null;

        text = null;
        try {
            // PerComment.g:588:5: ( ( SQUOTE i= ID SQUOTE ) | ( DQUOTE j= ID DQUOTE ) |k= ID )
            int alt28=3;
            switch ( input.LA(1) ) {
            case SQUOTE:
                {
                alt28=1;
                }
                break;
            case DQUOTE:
                {
                alt28=2;
                }
                break;
            case ID:
                {
                alt28=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;

            }

            switch (alt28) {
                case 1 :
                    // PerComment.g:588:9: ( SQUOTE i= ID SQUOTE )
                    {
                    // PerComment.g:588:9: ( SQUOTE i= ID SQUOTE )
                    // PerComment.g:588:10: SQUOTE i= ID SQUOTE
                    {
                    match(input,SQUOTE,FOLLOW_SQUOTE_in_ident866); 

                    i=(Token)match(input,ID,FOLLOW_ID_in_ident870); 

                    match(input,SQUOTE,FOLLOW_SQUOTE_in_ident872); 

                    }


                    text = ((i!=null?i.getText():null));

                    }
                    break;
                case 2 :
                    // PerComment.g:589:9: ( DQUOTE j= ID DQUOTE )
                    {
                    // PerComment.g:589:9: ( DQUOTE j= ID DQUOTE )
                    // PerComment.g:589:10: DQUOTE j= ID DQUOTE
                    {
                    match(input,DQUOTE,FOLLOW_DQUOTE_in_ident886); 

                    j=(Token)match(input,ID,FOLLOW_ID_in_ident890); 

                    match(input,DQUOTE,FOLLOW_DQUOTE_in_ident892); 

                    }


                    text = ((j!=null?j.getText():null));

                    }
                    break;
                case 3 :
                    // PerComment.g:590:7: k= ID
                    {
                    k=(Token)match(input,ID,FOLLOW_ID_in_ident905); 

                    text = (k!=null?k.getText():null);

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return text;
    }
    // $ANTLR end "ident"



    // $ANTLR start "col_isarefs"
    // PerComment.g:593:10: fragment col_isarefs : ISAREFS WS ( OPEN_PAREN ( WS )? ( ( SQUOTE a= text_or_id SQUOTE ) | ( DQUOTE b= text_or_id DQUOTE ) | (c= text_or_id ) ) ( WS )? COMMA ( WS )? ( ( SQUOTE d= ID SQUOTE ) | ( DQUOTE e= ID DQUOTE ) | (f= ID ) ) ( WS )? CLOSE_PAREN ( WS )? ( COMMA )? )+ ;
    public final void col_isarefs() throws RecognitionException {
        Token d=null;
        Token e=null;
        Token f=null;
        PerCommentParser.text_or_id_return a =null;

        PerCommentParser.text_or_id_return b =null;

        PerCommentParser.text_or_id_return c =null;



            List contents = new ArrayList();
            String first = null;
            String second = null;

        try {
            // PerComment.g:600:3: ( ISAREFS WS ( OPEN_PAREN ( WS )? ( ( SQUOTE a= text_or_id SQUOTE ) | ( DQUOTE b= text_or_id DQUOTE ) | (c= text_or_id ) ) ( WS )? COMMA ( WS )? ( ( SQUOTE d= ID SQUOTE ) | ( DQUOTE e= ID DQUOTE ) | (f= ID ) ) ( WS )? CLOSE_PAREN ( WS )? ( COMMA )? )+ )
            // PerComment.g:600:6: ISAREFS WS ( OPEN_PAREN ( WS )? ( ( SQUOTE a= text_or_id SQUOTE ) | ( DQUOTE b= text_or_id DQUOTE ) | (c= text_or_id ) ) ( WS )? COMMA ( WS )? ( ( SQUOTE d= ID SQUOTE ) | ( DQUOTE e= ID DQUOTE ) | (f= ID ) ) ( WS )? CLOSE_PAREN ( WS )? ( COMMA )? )+
            {
            match(input,ISAREFS,FOLLOW_ISAREFS_in_col_isarefs930); 

            match(input,WS,FOLLOW_WS_in_col_isarefs937); 

            // PerComment.g:602:6: ( OPEN_PAREN ( WS )? ( ( SQUOTE a= text_or_id SQUOTE ) | ( DQUOTE b= text_or_id DQUOTE ) | (c= text_or_id ) ) ( WS )? COMMA ( WS )? ( ( SQUOTE d= ID SQUOTE ) | ( DQUOTE e= ID DQUOTE ) | (f= ID ) ) ( WS )? CLOSE_PAREN ( WS )? ( COMMA )? )+
            int cnt37=0;
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==OPEN_PAREN) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // PerComment.g:603:8: OPEN_PAREN ( WS )? ( ( SQUOTE a= text_or_id SQUOTE ) | ( DQUOTE b= text_or_id DQUOTE ) | (c= text_or_id ) ) ( WS )? COMMA ( WS )? ( ( SQUOTE d= ID SQUOTE ) | ( DQUOTE e= ID DQUOTE ) | (f= ID ) ) ( WS )? CLOSE_PAREN ( WS )? ( COMMA )?
            	    {
            	    match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_col_isarefs953); 

            	    // PerComment.g:603:19: ( WS )?
            	    int alt29=2;
            	    int LA29_0 = input.LA(1);

            	    if ( (LA29_0==WS) ) {
            	        alt29=1;
            	    }
            	    switch (alt29) {
            	        case 1 :
            	            // PerComment.g:603:19: WS
            	            {
            	            match(input,WS,FOLLOW_WS_in_col_isarefs955); 

            	            }
            	            break;

            	    }


            	    // PerComment.g:604:8: ( ( SQUOTE a= text_or_id SQUOTE ) | ( DQUOTE b= text_or_id DQUOTE ) | (c= text_or_id ) )
            	    int alt30=3;
            	    switch ( input.LA(1) ) {
            	    case SQUOTE:
            	        {
            	        alt30=1;
            	        }
            	        break;
            	    case DQUOTE:
            	        {
            	        alt30=2;
            	        }
            	        break;
            	    case ID:
            	    case TEXT:
            	        {
            	        alt30=3;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 30, 0, input);

            	        throw nvae;

            	    }

            	    switch (alt30) {
            	        case 1 :
            	            // PerComment.g:604:11: ( SQUOTE a= text_or_id SQUOTE )
            	            {
            	            // PerComment.g:604:11: ( SQUOTE a= text_or_id SQUOTE )
            	            // PerComment.g:604:12: SQUOTE a= text_or_id SQUOTE
            	            {
            	            match(input,SQUOTE,FOLLOW_SQUOTE_in_col_isarefs969); 

            	            pushFollow(FOLLOW_text_or_id_in_col_isarefs973);
            	            a=text_or_id();

            	            state._fsp--;


            	            match(input,SQUOTE,FOLLOW_SQUOTE_in_col_isarefs975); 

            	            }


            	             first = (a!=null?input.toString(a.start,a.stop):null); 

            	            }
            	            break;
            	        case 2 :
            	            // PerComment.g:605:11: ( DQUOTE b= text_or_id DQUOTE )
            	            {
            	            // PerComment.g:605:11: ( DQUOTE b= text_or_id DQUOTE )
            	            // PerComment.g:605:12: DQUOTE b= text_or_id DQUOTE
            	            {
            	            match(input,DQUOTE,FOLLOW_DQUOTE_in_col_isarefs991); 

            	            pushFollow(FOLLOW_text_or_id_in_col_isarefs995);
            	            b=text_or_id();

            	            state._fsp--;


            	            match(input,DQUOTE,FOLLOW_DQUOTE_in_col_isarefs997); 

            	            }


            	             first = (b!=null?input.toString(b.start,b.stop):null); 

            	            }
            	            break;
            	        case 3 :
            	            // PerComment.g:606:11: (c= text_or_id )
            	            {
            	            // PerComment.g:606:11: (c= text_or_id )
            	            // PerComment.g:606:12: c= text_or_id
            	            {
            	            pushFollow(FOLLOW_text_or_id_in_col_isarefs1015);
            	            c=text_or_id();

            	            state._fsp--;


            	            }


            	             first = (c!=null?input.toString(c.start,c.stop):null); 

            	            }
            	            break;

            	    }


            	    // PerComment.g:607:8: ( WS )?
            	    int alt31=2;
            	    int LA31_0 = input.LA(1);

            	    if ( (LA31_0==WS) ) {
            	        alt31=1;
            	    }
            	    switch (alt31) {
            	        case 1 :
            	            // PerComment.g:607:8: WS
            	            {
            	            match(input,WS,FOLLOW_WS_in_col_isarefs1028); 

            	            }
            	            break;

            	    }


            	    match(input,COMMA,FOLLOW_COMMA_in_col_isarefs1031); 

            	    // PerComment.g:607:18: ( WS )?
            	    int alt32=2;
            	    int LA32_0 = input.LA(1);

            	    if ( (LA32_0==WS) ) {
            	        alt32=1;
            	    }
            	    switch (alt32) {
            	        case 1 :
            	            // PerComment.g:607:18: WS
            	            {
            	            match(input,WS,FOLLOW_WS_in_col_isarefs1033); 

            	            }
            	            break;

            	    }


            	    // PerComment.g:608:8: ( ( SQUOTE d= ID SQUOTE ) | ( DQUOTE e= ID DQUOTE ) | (f= ID ) )
            	    int alt33=3;
            	    switch ( input.LA(1) ) {
            	    case SQUOTE:
            	        {
            	        alt33=1;
            	        }
            	        break;
            	    case DQUOTE:
            	        {
            	        alt33=2;
            	        }
            	        break;
            	    case ID:
            	        {
            	        alt33=3;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 33, 0, input);

            	        throw nvae;

            	    }

            	    switch (alt33) {
            	        case 1 :
            	            // PerComment.g:608:11: ( SQUOTE d= ID SQUOTE )
            	            {
            	            // PerComment.g:608:11: ( SQUOTE d= ID SQUOTE )
            	            // PerComment.g:608:12: SQUOTE d= ID SQUOTE
            	            {
            	            match(input,SQUOTE,FOLLOW_SQUOTE_in_col_isarefs1047); 

            	            d=(Token)match(input,ID,FOLLOW_ID_in_col_isarefs1051); 

            	            match(input,SQUOTE,FOLLOW_SQUOTE_in_col_isarefs1053); 

            	            }


            	             second = (d!=null?d.getText():null); 

            	            }
            	            break;
            	        case 2 :
            	            // PerComment.g:609:11: ( DQUOTE e= ID DQUOTE )
            	            {
            	            // PerComment.g:609:11: ( DQUOTE e= ID DQUOTE )
            	            // PerComment.g:609:12: DQUOTE e= ID DQUOTE
            	            {
            	            match(input,DQUOTE,FOLLOW_DQUOTE_in_col_isarefs1069); 

            	            e=(Token)match(input,ID,FOLLOW_ID_in_col_isarefs1073); 

            	            match(input,DQUOTE,FOLLOW_DQUOTE_in_col_isarefs1075); 

            	            }


            	             second = (e!=null?e.getText():null); 

            	            }
            	            break;
            	        case 3 :
            	            // PerComment.g:610:11: (f= ID )
            	            {
            	            // PerComment.g:610:11: (f= ID )
            	            // PerComment.g:610:12: f= ID
            	            {
            	            f=(Token)match(input,ID,FOLLOW_ID_in_col_isarefs1093); 

            	            }


            	             second = (f!=null?f.getText():null); 

            	            }
            	            break;

            	    }


            	    // PerComment.g:611:8: ( WS )?
            	    int alt34=2;
            	    int LA34_0 = input.LA(1);

            	    if ( (LA34_0==WS) ) {
            	        alt34=1;
            	    }
            	    switch (alt34) {
            	        case 1 :
            	            // PerComment.g:611:8: WS
            	            {
            	            match(input,WS,FOLLOW_WS_in_col_isarefs1106); 

            	            }
            	            break;

            	    }


            	    match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_col_isarefs1109); 

            	    // PerComment.g:611:24: ( WS )?
            	    int alt35=2;
            	    int LA35_0 = input.LA(1);

            	    if ( (LA35_0==WS) ) {
            	        alt35=1;
            	    }
            	    switch (alt35) {
            	        case 1 :
            	            // PerComment.g:611:24: WS
            	            {
            	            match(input,WS,FOLLOW_WS_in_col_isarefs1111); 

            	            }
            	            break;

            	    }


            	    // PerComment.g:611:28: ( COMMA )?
            	    int alt36=2;
            	    int LA36_0 = input.LA(1);

            	    if ( (LA36_0==COMMA) ) {
            	        alt36=1;
            	    }
            	    switch (alt36) {
            	        case 1 :
            	            // PerComment.g:611:28: COMMA
            	            {
            	            match(input,COMMA,FOLLOW_COMMA_in_col_isarefs1114); 

            	            }
            	            break;

            	    }



            	             contents.add(new String[] { trim(first), trim(second) });
            	           

            	    }
            	    break;

            	default :
            	    if ( cnt37 >= 1 ) break loop37;
                        EarlyExitException eee =
                            new EarlyExitException(37, input);
                        throw eee;
                }
                cnt37++;
            } while (true);



                   setColumnIsaRefs((String[][]) contents.toArray(new String[0][0]));
                 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "col_isarefs"



    // $ANTLR start "col_oraseq"
    // PerComment.g:621:10: fragment col_oraseq returns [String result] : ORASEQ WS i= ident ( WS )? ;
    public final String col_oraseq() throws RecognitionException {
        String result = null;


        String i =null;


         result = null; 
        try {
            // PerComment.g:623:3: ( ORASEQ WS i= ident ( WS )? )
            // PerComment.g:623:5: ORASEQ WS i= ident ( WS )?
            {
            match(input,ORASEQ,FOLLOW_ORASEQ_in_col_oraseq1163); 

            match(input,WS,FOLLOW_WS_in_col_oraseq1165); 

            pushFollow(FOLLOW_ident_in_col_oraseq1169);
            i=ident();

            state._fsp--;


            // PerComment.g:623:23: ( WS )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==WS) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // PerComment.g:623:23: WS
                    {
                    match(input,WS,FOLLOW_WS_in_col_oraseq1171); 

                    }
                    break;

            }


             result = i; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return result;
    }
    // $ANTLR end "col_oraseq"

    // Delegated rules


 

    public static final BitSet FOLLOW_text_in_tableComment46 = new BitSet(new long[]{0x0000000000A05102L});
    public static final BitSet FOLLOW_tab_annotation_in_tableComment52 = new BitSet(new long[]{0x0000000000A05102L});
    public static final BitSet FOLLOW_text_in_columnComment76 = new BitSet(new long[]{0x0000000000142022L});
    public static final BitSet FOLLOW_col_annotation_in_columnComment82 = new BitSet(new long[]{0x0000000000142022L});
    public static final BitSet FOLLOW_text_or_id_in_text113 = new BitSet(new long[]{0x00000000030A08C2L});
    public static final BitSet FOLLOW_COMMA_in_text127 = new BitSet(new long[]{0x00000000030A08C2L});
    public static final BitSet FOLLOW_WS_in_text138 = new BitSet(new long[]{0x00000000030A08C2L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_text147 = new BitSet(new long[]{0x00000000030A08C2L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_text156 = new BitSet(new long[]{0x00000000030A08C2L});
    public static final BitSet FOLLOW_QUOTE_in_text165 = new BitSet(new long[]{0x00000000030A08C2L});
    public static final BitSet FOLLOW_tab_static_in_tab_annotation209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tab_isa_in_tab_annotation229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tab_isatype_in_tab_annotation252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tab_decorator_in_tab_annotation271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tab_relationship_in_tab_annotation288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STATIC_in_tab_static318 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_WS_in_tab_static320 = new BitSet(new long[]{0x0000000000400C00L});
    public static final BitSet FOLLOW_ident_in_tab_static324 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_WS_in_tab_static326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ISA_in_tab_isa353 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_WS_in_tab_isa355 = new BitSet(new long[]{0x0000000000400C00L});
    public static final BitSet FOLLOW_ident_in_tab_isa359 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_WS_in_tab_isa361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ISATYPE_in_tab_isatype388 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_WS_in_tab_isatype390 = new BitSet(new long[]{0x0000000000400C00L});
    public static final BitSet FOLLOW_ident_in_tab_isatype394 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_WS_in_tab_isatype396 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECORATOR_in_tab_decorator413 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_WS_in_tab_decorator415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RELATIONSHIP_in_tab_relationship434 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_WS_in_tab_relationship441 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_tab_relationship457 = new BitSet(new long[]{0x0000000003400C00L});
    public static final BitSet FOLLOW_WS_in_tab_relationship459 = new BitSet(new long[]{0x0000000001400C00L});
    public static final BitSet FOLLOW_SQUOTE_in_tab_relationship473 = new BitSet(new long[]{0x0000000001000800L});
    public static final BitSet FOLLOW_text_or_id_in_tab_relationship477 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_SQUOTE_in_tab_relationship479 = new BitSet(new long[]{0x0000000002000080L});
    public static final BitSet FOLLOW_DQUOTE_in_tab_relationship495 = new BitSet(new long[]{0x0000000001000800L});
    public static final BitSet FOLLOW_text_or_id_in_tab_relationship499 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DQUOTE_in_tab_relationship501 = new BitSet(new long[]{0x0000000002000080L});
    public static final BitSet FOLLOW_text_or_id_in_tab_relationship519 = new BitSet(new long[]{0x0000000002000080L});
    public static final BitSet FOLLOW_WS_in_tab_relationship532 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_COMMA_in_tab_relationship535 = new BitSet(new long[]{0x0000000002400C00L});
    public static final BitSet FOLLOW_WS_in_tab_relationship537 = new BitSet(new long[]{0x0000000000400C00L});
    public static final BitSet FOLLOW_SQUOTE_in_tab_relationship551 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_ID_in_tab_relationship555 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_SQUOTE_in_tab_relationship557 = new BitSet(new long[]{0x0000000002000040L});
    public static final BitSet FOLLOW_DQUOTE_in_tab_relationship573 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_ID_in_tab_relationship577 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DQUOTE_in_tab_relationship579 = new BitSet(new long[]{0x0000000002000040L});
    public static final BitSet FOLLOW_ID_in_tab_relationship597 = new BitSet(new long[]{0x0000000002000040L});
    public static final BitSet FOLLOW_WS_in_tab_relationship610 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_tab_relationship613 = new BitSet(new long[]{0x0000000002020082L});
    public static final BitSet FOLLOW_WS_in_tab_relationship615 = new BitSet(new long[]{0x0000000000020082L});
    public static final BitSet FOLLOW_COMMA_in_tab_relationship618 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_col_readonly_in_col_annotation669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_bool_in_col_annotation682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_isarefs_in_col_annotation693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_oraseq_in_col_annotation704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_READONLY_in_col_readonly725 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_WS_in_col_readonly727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_col_bool740 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_WS_in_col_bool742 = new BitSet(new long[]{0x0000000000400C00L});
    public static final BitSet FOLLOW_ident_in_col_bool750 = new BitSet(new long[]{0x0000000002000080L});
    public static final BitSet FOLLOW_WS_in_col_bool762 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_COMMA_in_col_bool765 = new BitSet(new long[]{0x0000000002400C00L});
    public static final BitSet FOLLOW_WS_in_col_bool767 = new BitSet(new long[]{0x0000000000400C00L});
    public static final BitSet FOLLOW_ident_in_col_bool776 = new BitSet(new long[]{0x0000000002000082L});
    public static final BitSet FOLLOW_WS_in_col_bool784 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_COMMA_in_col_bool788 = new BitSet(new long[]{0x0000000002400C00L});
    public static final BitSet FOLLOW_WS_in_col_bool790 = new BitSet(new long[]{0x0000000000400C00L});
    public static final BitSet FOLLOW_ident_in_col_bool795 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_WS_in_col_bool797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SQUOTE_in_ident866 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_ID_in_ident870 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_SQUOTE_in_ident872 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DQUOTE_in_ident886 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_ID_in_ident890 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DQUOTE_in_ident892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_ident905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ISAREFS_in_col_isarefs930 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_WS_in_col_isarefs937 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_col_isarefs953 = new BitSet(new long[]{0x0000000003400C00L});
    public static final BitSet FOLLOW_WS_in_col_isarefs955 = new BitSet(new long[]{0x0000000001400C00L});
    public static final BitSet FOLLOW_SQUOTE_in_col_isarefs969 = new BitSet(new long[]{0x0000000001000800L});
    public static final BitSet FOLLOW_text_or_id_in_col_isarefs973 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_SQUOTE_in_col_isarefs975 = new BitSet(new long[]{0x0000000002000080L});
    public static final BitSet FOLLOW_DQUOTE_in_col_isarefs991 = new BitSet(new long[]{0x0000000001000800L});
    public static final BitSet FOLLOW_text_or_id_in_col_isarefs995 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DQUOTE_in_col_isarefs997 = new BitSet(new long[]{0x0000000002000080L});
    public static final BitSet FOLLOW_text_or_id_in_col_isarefs1015 = new BitSet(new long[]{0x0000000002000080L});
    public static final BitSet FOLLOW_WS_in_col_isarefs1028 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_COMMA_in_col_isarefs1031 = new BitSet(new long[]{0x0000000002400C00L});
    public static final BitSet FOLLOW_WS_in_col_isarefs1033 = new BitSet(new long[]{0x0000000000400C00L});
    public static final BitSet FOLLOW_SQUOTE_in_col_isarefs1047 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_ID_in_col_isarefs1051 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_SQUOTE_in_col_isarefs1053 = new BitSet(new long[]{0x0000000002000040L});
    public static final BitSet FOLLOW_DQUOTE_in_col_isarefs1069 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_ID_in_col_isarefs1073 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DQUOTE_in_col_isarefs1075 = new BitSet(new long[]{0x0000000002000040L});
    public static final BitSet FOLLOW_ID_in_col_isarefs1093 = new BitSet(new long[]{0x0000000002000040L});
    public static final BitSet FOLLOW_WS_in_col_isarefs1106 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_col_isarefs1109 = new BitSet(new long[]{0x0000000002020082L});
    public static final BitSet FOLLOW_WS_in_col_isarefs1111 = new BitSet(new long[]{0x0000000000020082L});
    public static final BitSet FOLLOW_COMMA_in_col_isarefs1114 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_ORASEQ_in_col_oraseq1163 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_WS_in_col_oraseq1165 = new BitSet(new long[]{0x0000000000400C00L});
    public static final BitSet FOLLOW_ident_in_col_oraseq1169 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_WS_in_col_oraseq1171 = new BitSet(new long[]{0x0000000000000002L});

}