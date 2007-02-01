// $ANTLR 3.0b6 PerComment.g 2007-02-01 17:27:59

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
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class PerCommentParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "TEXT", "STATIC", "ISA", "ISATYPE", "READONLY", "BOOL", "ID", "ISAREFS", "OPEN_PAREN", "COMMA", "CLOSE_PAREN", "LETTER", "NAMECHAR", "WHITESPACE", "DIGIT"
    };
    public static final int COMMA=13;
    public static final int STATIC=5;
    public static final int LETTER=15;
    public static final int BOOL=9;
    public static final int CLOSE_PAREN=14;
    public static final int ISATYPE=7;
    public static final int EOF=-1;
    public static final int TEXT=4;
    public static final int READONLY=8;
    public static final int OPEN_PAREN=12;
    public static final int NAMECHAR=16;
    public static final int ISA=6;
    public static final int DIGIT=18;
    public static final int ISAREFS=11;
    public static final int WHITESPACE=17;
    public static final int ID=10;

        public PerCommentParser(TokenStream input) {
            super(input);
        }
        

    public String[] getTokenNames() { return tokenNames; }
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
     * Specifies the table comment.
     * @param comment such comment.
     */
    protected void setTableComment(final String comment)
    {
        tableComment = comment;
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
        tableStatic = name;
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
        tableIsa = name;
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
        tableIsaType = name;
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
        columnComment = comment;
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
        columnBool = value;
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



    // $ANTLR start tableComment
    // PerComment.g:335:1: tableComment : t= text ( tab_annotation )* ;
    public void tableComment() throws RecognitionException {
        String t = null;


        try {
            // PerComment.g:335:16: (t= text ( tab_annotation )* )
            // PerComment.g:335:16: t= text ( tab_annotation )*
            {
            pushFollow(FOLLOW_text_in_tableComment45);
            t=text();
            _fsp--;

            // PerComment.g:335:23: ( tab_annotation )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);
                if ( ((LA1_0>=STATIC && LA1_0<=ISATYPE)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // PerComment.g:335:25: tab_annotation
            	    {
            	    pushFollow(FOLLOW_tab_annotation_in_tableComment49);
            	    tab_annotation();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
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
        }
        return ;
    }
    // $ANTLR end tableComment


    // $ANTLR start text
    // PerComment.g:337:10: fragment text returns [String result] : t= TEXT ;
    public String text() throws RecognitionException {
        String result = null;

        Token t=null;

         result = null; 
        try {
            // PerComment.g:339:5: (t= TEXT )
            // PerComment.g:339:5: t= TEXT
            {
            t=(Token)input.LT(1);
            match(input,TEXT,FOLLOW_TEXT_in_text85); 
             result = t.getText(); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end text


    // $ANTLR start tab_annotation
    // PerComment.g:342:10: fragment tab_annotation : (s= tab_static | i= tab_isa | t= tab_isatype ) ;
    public void tab_annotation() throws RecognitionException {
        String s = null;

        String i = null;

        String t = null;


        try {
            // PerComment.g:343:5: ( (s= tab_static | i= tab_isa | t= tab_isatype ) )
            // PerComment.g:343:5: (s= tab_static | i= tab_isa | t= tab_isatype )
            {
            // PerComment.g:343:5: (s= tab_static | i= tab_isa | t= tab_isatype )
            int alt2=3;
            switch ( input.LA(1) ) {
            case STATIC:
                alt2=1;
                break;
            case ISA:
                alt2=2;
                break;
            case ISATYPE:
                alt2=3;
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("343:5: (s= tab_static | i= tab_isa | t= tab_isatype )", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // PerComment.g:344:9: s= tab_static
                    {
                    pushFollow(FOLLOW_tab_static_in_tab_annotation122);
                    s=tab_static();
                    _fsp--;

                     setTableStatic(s); 

                    }
                    break;
                case 2 :
                    // PerComment.g:345:9: i= tab_isa
                    {
                    pushFollow(FOLLOW_tab_isa_in_tab_annotation137);
                    i=tab_isa();
                    _fsp--;

                     setTableIsa(i); 

                    }
                    break;
                case 3 :
                    // PerComment.g:346:9: t= tab_isatype
                    {
                    pushFollow(FOLLOW_tab_isatype_in_tab_annotation155);
                    t=tab_isatype();
                    _fsp--;

                     setTableIsaType(t); 

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
        }
        return ;
    }
    // $ANTLR end tab_annotation


    // $ANTLR start tab_static
    // PerComment.g:350:10: fragment tab_static returns [String result] : STATIC i= identifier ;
    public String tab_static() throws RecognitionException {
        String result = null;

        identifier_return i = null;


         result = null; 
        try {
            // PerComment.g:352:5: ( STATIC i= identifier )
            // PerComment.g:352:5: STATIC i= identifier
            {
            match(input,STATIC,FOLLOW_STATIC_in_tab_static187); 
            pushFollow(FOLLOW_identifier_in_tab_static191);
            i=identifier();
            _fsp--;

             result = input.toString(i.start,i.stop); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end tab_static


    // $ANTLR start tab_isa
    // PerComment.g:355:10: fragment tab_isa returns [String result] : ISA i= identifier ;
    public String tab_isa() throws RecognitionException {
        String result = null;

        identifier_return i = null;


         result = null; 
        try {
            // PerComment.g:357:5: ( ISA i= identifier )
            // PerComment.g:357:5: ISA i= identifier
            {
            match(input,ISA,FOLLOW_ISA_in_tab_isa217); 
            pushFollow(FOLLOW_identifier_in_tab_isa221);
            i=identifier();
            _fsp--;

             result = input.toString(i.start,i.stop); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end tab_isa


    // $ANTLR start tab_isatype
    // PerComment.g:360:10: fragment tab_isatype returns [String result] : ISATYPE i= identifier ;
    public String tab_isatype() throws RecognitionException {
        String result = null;

        identifier_return i = null;


         result = null; 
        try {
            // PerComment.g:362:5: ( ISATYPE i= identifier )
            // PerComment.g:362:5: ISATYPE i= identifier
            {
            match(input,ISATYPE,FOLLOW_ISATYPE_in_tab_isatype247); 
            pushFollow(FOLLOW_identifier_in_tab_isatype251);
            i=identifier();
            _fsp--;

             result = input.toString(i.start,i.stop); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end tab_isatype


    // $ANTLR start columnComment
    // PerComment.g:365:1: columnComment : t= text ( col_annotation )* ;
    public void columnComment() throws RecognitionException {
        String t = null;


        try {
            // PerComment.g:365:17: (t= text ( col_annotation )* )
            // PerComment.g:365:17: t= text ( col_annotation )*
            {
            pushFollow(FOLLOW_text_in_columnComment266);
            t=text();
            _fsp--;

            // PerComment.g:365:24: ( col_annotation )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);
                if ( ((LA3_0>=READONLY && LA3_0<=BOOL)||LA3_0==ISAREFS) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // PerComment.g:365:26: col_annotation
            	    {
            	    pushFollow(FOLLOW_col_annotation_in_columnComment270);
            	    col_annotation();
            	    _fsp--;


            	    }
            	    break;

            	default :
            	    break loop3;
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
        }
        return ;
    }
    // $ANTLR end columnComment


    // $ANTLR start col_annotation
    // PerComment.g:367:10: fragment col_annotation : ( col_readonly | b= col_bool | col_isarefs ) ;
    public void col_annotation() throws RecognitionException {
        String b = null;


        try {
            // PerComment.g:368:5: ( ( col_readonly | b= col_bool | col_isarefs ) )
            // PerComment.g:368:5: ( col_readonly | b= col_bool | col_isarefs )
            {
            // PerComment.g:368:5: ( col_readonly | b= col_bool | col_isarefs )
            int alt4=3;
            switch ( input.LA(1) ) {
            case READONLY:
                alt4=1;
                break;
            case BOOL:
                alt4=2;
                break;
            case ISAREFS:
                alt4=3;
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("368:5: ( col_readonly | b= col_bool | col_isarefs )", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // PerComment.g:369:10: col_readonly
                    {
                    pushFollow(FOLLOW_col_readonly_in_col_annotation298);
                    col_readonly();
                    _fsp--;

                     setColumnReadOnly(true); 

                    }
                    break;
                case 2 :
                    // PerComment.g:370:8: b= col_bool
                    {
                    pushFollow(FOLLOW_col_bool_in_col_annotation311);
                    b=col_bool();
                    _fsp--;

                     setColumnBool(b); 

                    }
                    break;
                case 3 :
                    // PerComment.g:371:8: col_isarefs
                    {
                    pushFollow(FOLLOW_col_isarefs_in_col_annotation326);
                    col_isarefs();
                    _fsp--;


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
        }
        return ;
    }
    // $ANTLR end col_annotation


    // $ANTLR start col_readonly
    // PerComment.g:374:10: fragment col_readonly : READONLY ;
    public void col_readonly() throws RecognitionException {
        try {
            // PerComment.g:374:26: ( READONLY )
            // PerComment.g:374:26: READONLY
            {
            match(input,READONLY,FOLLOW_READONLY_in_col_readonly343); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end col_readonly


    // $ANTLR start col_bool
    // PerComment.g:376:10: fragment col_bool returns [String result] : BOOL i= identifier ;
    public String col_bool() throws RecognitionException {
        String result = null;

        identifier_return i = null;


         result = null; 
        try {
            // PerComment.g:378:5: ( BOOL i= identifier )
            // PerComment.g:378:5: BOOL i= identifier
            {
            match(input,BOOL,FOLLOW_BOOL_in_col_bool364); 
            pushFollow(FOLLOW_identifier_in_col_bool368);
            i=identifier();
            _fsp--;

             result = input.toString(i.start,i.stop); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return result;
    }
    // $ANTLR end col_bool

    public static class identifier_return extends ParserRuleReturnScope {
    };

    // $ANTLR start identifier
    // PerComment.g:381:10: fragment identifier : ID ;
    public identifier_return identifier() throws RecognitionException {
        identifier_return retval = new identifier_return();
        retval.start = input.LT(1);

        try {
            // PerComment.g:381:23: ( ID )
            // PerComment.g:381:23: ID
            {
            match(input,ID,FOLLOW_ID_in_identifier383); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            retval.stop = input.LT(-1);

        }
        return retval;
    }
    // $ANTLR end identifier


    // $ANTLR start col_isarefs
    // PerComment.g:383:10: fragment col_isarefs : ISAREFS ( OPEN_PAREN a= identifier COMMA b= identifier CLOSE_PAREN )+ ;
    public void col_isarefs() throws RecognitionException {
        identifier_return a = null;

        identifier_return b = null;



            List contents = new ArrayList();

        try {
            // PerComment.g:388:6: ( ISAREFS ( OPEN_PAREN a= identifier COMMA b= identifier CLOSE_PAREN )+ )
            // PerComment.g:388:6: ISAREFS ( OPEN_PAREN a= identifier COMMA b= identifier CLOSE_PAREN )+
            {
            match(input,ISAREFS,FOLLOW_ISAREFS_in_col_isarefs401); 
            // PerComment.g:389:6: ( OPEN_PAREN a= identifier COMMA b= identifier CLOSE_PAREN )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);
                if ( (LA5_0==OPEN_PAREN) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // PerComment.g:390:8: OPEN_PAREN a= identifier COMMA b= identifier CLOSE_PAREN
            	    {
            	    match(input,OPEN_PAREN,FOLLOW_OPEN_PAREN_in_col_isarefs417); 
            	    pushFollow(FOLLOW_identifier_in_col_isarefs421);
            	    a=identifier();
            	    _fsp--;

            	    match(input,COMMA,FOLLOW_COMMA_in_col_isarefs423); 
            	    pushFollow(FOLLOW_identifier_in_col_isarefs427);
            	    b=identifier();
            	    _fsp--;

            	    match(input,CLOSE_PAREN,FOLLOW_CLOSE_PAREN_in_col_isarefs429); 

            	             contents.add(new String[] { input.toString(a.start,a.stop), input.toString(b.start,b.stop) });
            	           

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


                   setColumnIsaRefs((String[][]) contents.toArray(new String[0][0]));
                 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end col_isarefs


 

    public static final BitSet FOLLOW_text_in_tableComment45 = new BitSet(new long[]{0x00000000000000E2L});
    public static final BitSet FOLLOW_tab_annotation_in_tableComment49 = new BitSet(new long[]{0x00000000000000E2L});
    public static final BitSet FOLLOW_TEXT_in_text85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tab_static_in_tab_annotation122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tab_isa_in_tab_annotation137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tab_isatype_in_tab_annotation155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STATIC_in_tab_static187 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_identifier_in_tab_static191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ISA_in_tab_isa217 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_identifier_in_tab_isa221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ISATYPE_in_tab_isatype247 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_identifier_in_tab_isatype251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_text_in_columnComment266 = new BitSet(new long[]{0x0000000000000B02L});
    public static final BitSet FOLLOW_col_annotation_in_columnComment270 = new BitSet(new long[]{0x0000000000000B02L});
    public static final BitSet FOLLOW_col_readonly_in_col_annotation298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_bool_in_col_annotation311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_col_isarefs_in_col_annotation326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_READONLY_in_col_readonly343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_in_col_bool364 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_identifier_in_col_bool368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_identifier383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ISAREFS_in_col_isarefs401 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_OPEN_PAREN_in_col_isarefs417 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_identifier_in_col_isarefs421 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_COMMA_in_col_isarefs423 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_identifier_in_col_isarefs427 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_CLOSE_PAREN_in_col_isarefs429 = new BitSet(new long[]{0x0000000000001002L});

}