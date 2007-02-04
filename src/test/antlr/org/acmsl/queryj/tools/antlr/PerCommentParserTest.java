//;-*- mode: java -*-
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
 * Filename: PerCommentParserTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Indicates JUnit how to test PerCommentParser class.
 *
 */
package org.acmsl.queryj.tools.antlr;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.antlr.PerCommentLexer;
import org.acmsl.queryj.tools.antlr.PerCommentParser;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some ANTLR 3 classes.
 */
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing JUnit classes.
 */
import junit.framework.TestCase;
import junit.textui.TestRunner;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Indicates JUnit how to test <code>PerCommentParser</code> class.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 */
public class PerCommentParserTest
    extends  TestCase
{
    /**
     * The table comment.
     */
    protected static final String TABLE_COMMENT =
        "Dummy table used in tests.";
    
    /**
     * The table 'static' annotation.
     */
    protected static final String TABLE_STATIC = "name";
    
    /**
     * The table 'isa' annotation.
     */
    protected static final String TABLE_ISA = "parent_table";
    
    /**
     * The table 'isatype' annotation.
     */
    protected static final String TABLE_ISATYPE = "isa_parent";
    
    /**
     * The table comment tests.
     */
    public static final String[] TABLE_COMMENT_TESTS =
        new String[]
        {
            TABLE_COMMENT,
            TABLE_COMMENT +                                                       " @static " + TABLE_STATIC,
            TABLE_COMMENT +                                " @isa " + TABLE_ISA,
            TABLE_COMMENT +                                " @isa " + TABLE_ISA + " @static " + TABLE_STATIC,
            TABLE_COMMENT + " @isatype " + TABLE_ISATYPE,
            TABLE_COMMENT + " @isatype " + TABLE_ISATYPE                        + " @static " + TABLE_STATIC,
            TABLE_COMMENT + " @isatype " + TABLE_ISATYPE + " @isa " + TABLE_ISA,
            TABLE_COMMENT + " @isatype " + TABLE_ISATYPE + " @isa " + TABLE_ISA + " @static " + TABLE_STATIC,
            TABLE_COMMENT +                                                     " @isatype " + TABLE_ISATYPE,
            TABLE_COMMENT +                        " @static " + TABLE_STATIC,
            TABLE_COMMENT +                        " @static " + TABLE_STATIC + " @isatype " + TABLE_ISATYPE,
            TABLE_COMMENT + " @isa " + TABLE_ISA,
            TABLE_COMMENT + " @isa " + TABLE_ISA                              + " @isatype " + TABLE_ISATYPE,
            TABLE_COMMENT + " @isa " + TABLE_ISA + " @static " + TABLE_STATIC,
            TABLE_COMMENT + " @isa " + TABLE_ISA + " @static " + TABLE_STATIC + " @isatype " + TABLE_ISATYPE
        };

    /**
     * The column comment.
     */
    protected static final String COLUMN_COMMENT =
        "Dummy column used in tests.";

    /**
     * The column 'bool' annotation.
     */
    protected static final String COLUMN_BOOL = "'Y'";

    /**
     * The isaref ids.
     */
    protected static final String[] ISAREFS_IDS =
        new String[] {"1", "2"};

    /**
     * The isaref references.
     */
    protected static final String[] ISAREFS_REFERENCES =
        new String[] {"first_isa_child", "second_isa_child"};

    /**
     * The column 'isarefs' annotation.
     */
    protected static final String COLUMN_ISAREFS =
          "( " + ISAREFS_IDS[0] + ", \"" + ISAREFS_REFERENCES[0] + "\") " 
        + "("  + ISAREFS_IDS[1] + ", \"" + ISAREFS_REFERENCES[1] + "\" )";

    /**
     * The column 'oraseq' annotation.
     */
    protected static final String COLUMN_ORASEQ = "DUMMY_SEQ";

    /**
     * The column comment tests.
     */
    protected static final String[] COLUMN_COMMENT_TESTS =
        new String[]
        {
/*0*/       COLUMN_COMMENT,
/*1*/       COLUMN_COMMENT +                                                           " @readonly",
/*2*/       COLUMN_COMMENT +                                 " @bool " + COLUMN_BOOL,
/*3*/            COLUMN_COMMENT +                                 " @bool " + COLUMN_BOOL + " @readonly",
/*4*/            COLUMN_COMMENT + " @isarefs " + COLUMN_ISAREFS,
/*5*/            COLUMN_COMMENT + " @isarefs " + COLUMN_ISAREFS + " @readonly",
/*6*/            COLUMN_COMMENT + " @isarefs " + COLUMN_ISAREFS + " @bool " + COLUMN_BOOL,
/*7*/            COLUMN_COMMENT + " @isarefs " + COLUMN_ISAREFS + " @bool " + COLUMN_BOOL + " @readonly",
/*8*/            COLUMN_COMMENT +                                                           " @readonly",
/*9*/            COLUMN_COMMENT +                           " @isarefs " + COLUMN_ISAREFS,
/*10*/            COLUMN_COMMENT +                           " @isarefs " + COLUMN_ISAREFS + " @readonly",
/*11*/            COLUMN_COMMENT + " @bool " + COLUMN_BOOL,
/*12*/            COLUMN_COMMENT + " @bool " + COLUMN_BOOL +                                 " @readonly",
/*13*/            COLUMN_COMMENT + " @bool " + COLUMN_BOOL + " @isarefs " + COLUMN_ISAREFS,
/*14*/            COLUMN_COMMENT + " @bool " + COLUMN_BOOL + " @isarefs " + COLUMN_ISAREFS + " @readonly",
/*15*/            COLUMN_COMMENT +                                                           " @readonly",
/*16*/            COLUMN_COMMENT +                                 " @oraseq " + COLUMN_ORASEQ,
/*17*/            COLUMN_COMMENT +                                 " @oraseq " + COLUMN_ORASEQ + " @readonly",
/*18*/            COLUMN_COMMENT + " @isarefs " + COLUMN_ISAREFS,
/*19*/            COLUMN_COMMENT + " @isarefs " + COLUMN_ISAREFS + " @readonly",
/*20*/            COLUMN_COMMENT + " @isarefs " + COLUMN_ISAREFS + " @oraseq " + COLUMN_ORASEQ,
/*21*/            COLUMN_COMMENT + " @isarefs " + COLUMN_ISAREFS + " @oraseq " + COLUMN_ORASEQ + " @readonly",
/*22*/            COLUMN_COMMENT +                                                           " @readonly",
/*23*/            COLUMN_COMMENT +                               " @isarefs " + COLUMN_ISAREFS,
/*24*/            COLUMN_COMMENT +                               " @isarefs " + COLUMN_ISAREFS + " @readonly",
/*25*/            COLUMN_COMMENT + " @oraseq " + COLUMN_ORASEQ,
/*26*/            COLUMN_COMMENT + " @oraseq " + COLUMN_ORASEQ +                                 " @readonly",
/*27*/            COLUMN_COMMENT + " @oraseq " + COLUMN_ORASEQ + " @isarefs " + COLUMN_ISAREFS,
/*28*/            COLUMN_COMMENT + " @oraseq " + COLUMN_ORASEQ + " @isarefs " + COLUMN_ISAREFS + " @readonly"
        };

    /**
     * Constructs a test case with the given name.
     * @param name the test case name.
     */
    public PerCommentParserTest(final String name)
    {
        super(name);
    }

    /**
     * Executes the tests from command line.
     * @param args the command-line arguments. Not needed so far.
     */
    public static void main(final String args[])
    {
        TestRunner.run(PerCommentParserTest.class);
    }

    /**
     * Tests the <code>getTableComment()</code> method
     * @see org.acmsl.queryj.tools.antlr.PerCommentParser#getTableComment(String)
     */
    public void testTableComment()
    {
        int t_iIndex = 0;
        
        try
        {
            int t_iCount =
                (TABLE_COMMENT_TESTS != null) ? TABLE_COMMENT_TESTS.length : 0;
            
            for  (t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                PerCommentParser t_Parser =
                    setUpParser(TABLE_COMMENT_TESTS[t_iIndex]);

                t_Parser.tableComment();

                assertEquals(
                      "Test failed on table comment "
                    + t_iIndex
                    + " \"" + TABLE_COMMENT_TESTS[t_iIndex] + "\"",
                    TABLE_COMMENT,
                    t_Parser.getTableComment());
            }
        }
        catch  (final RecognitionException recognitionException)
        {
            fail(
                  recognitionException.getMessage()
                + " on test " + t_iIndex + " "
                + TABLE_COMMENT_TESTS[t_iIndex]);
        }
    }

    /**
     * Tests the <code>getColumnComment()</code> method
     * @see org.acmsl.queryj.tools.antlr.PerCommentParser#getColumnComment(String)
     */
    public void testColumnComment()
    {
        int t_iIndex = 0;
        
        try
        {
            int t_iCount =
                (COLUMN_COMMENT_TESTS != null)
                ?  COLUMN_COMMENT_TESTS.length
                :  0;
            
            for  (t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                PerCommentParser t_Parser =
                    setUpParser(COLUMN_COMMENT_TESTS[t_iIndex]);

                t_Parser.columnComment();

                assertEquals(
                      "Test failed on column comment "
                    + t_iIndex
                    + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                    COLUMN_COMMENT,
                    t_Parser.getColumnComment());
            }
        }
        catch  (final RecognitionException recognitionException)
        {
            fail(
                  recognitionException.getMessage()
                + " on test " + t_iIndex + " "
                + COLUMN_COMMENT_TESTS[t_iIndex]);
        }
    }

    /**
     * Tests the <code>getTableStatic()</code> method
     * @see org.acmsl.queryj.tools.antlr.PerCommentParser#getTableStatic()
     */
    public void testTableStatic()
    {
        int t_iIndex = 0;
        
        try
        {
            int t_iCount =
                (TABLE_COMMENT_TESTS != null) ? TABLE_COMMENT_TESTS.length : 0;
            
            for  (t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                PerCommentParser t_Parser =
                    setUpParser(TABLE_COMMENT_TESTS[t_iIndex]);

                t_Parser.tableComment();

                switch  (t_iIndex)
                {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 9:
                    case 10:
                    case 13:
                    case 14:
                        assertEquals(
                              "@static test failed on table comment "
                            + t_iIndex
                            + " \"" + TABLE_COMMENT_TESTS[t_iIndex] + "\"",
                            TABLE_STATIC,
                            t_Parser.getTableStatic());
                        break;

                    default:
                        assertNull(
                              "@static test failed on table comment "
                            + t_iIndex
                            + " \"" + TABLE_COMMENT_TESTS[t_iIndex] + "\"",
                            t_Parser.getTableStatic());
                        break;
                }
            }
        }
        catch  (final RecognitionException recognitionException)
        {
            fail(
                  recognitionException.getMessage()
                + " on test " + t_iIndex + " "
                + TABLE_COMMENT_TESTS[t_iIndex]);
        }
    }

    /**
     * Tests the <code>getTableIsa()</code> method
     * @see org.acmsl.queryj.tools.antlr.PerCommentParser#getTableIsa()
     */
    public void testTableIsa()
    {
        int t_iIndex = 0;
        
        try
        {
            int t_iCount =
                (TABLE_COMMENT_TESTS != null) ? TABLE_COMMENT_TESTS.length : 0;
            
            for  (t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                PerCommentParser t_Parser =
                    setUpParser(TABLE_COMMENT_TESTS[t_iIndex]);

                t_Parser.tableComment();

                switch  (t_iIndex)
                {
                    case 2:
                    case 3:
                    case 6:
                    case 7:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                        assertEquals(
                              "@isa test failed on table comment "
                            + t_iIndex
                            + " \"" + TABLE_COMMENT_TESTS[t_iIndex] + "\"",
                            TABLE_ISA,
                            t_Parser.getTableIsa());
                        break;

                    default:
                        assertNull(
                              "@isa test failed on table comment "
                            + t_iIndex
                            + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                            t_Parser.getTableIsa());
                        break;
                }
            }
        }
        catch  (final RecognitionException recognitionException)
        {
            fail(
                  recognitionException.getMessage()
                + " on test " + t_iIndex + " "
                + TABLE_COMMENT_TESTS[t_iIndex]);
        }
    }

    /**
     * Tests the <code>getTableIsaType()</code> method
     * @see org.acmsl.queryj.tools.antlr.PerCommentParser#getTableIsaType()
     */
    public void testTableIsaType()
    {
        int t_iIndex = 0;
        
        try
        {
            int t_iCount =
                (TABLE_COMMENT_TESTS != null) ? TABLE_COMMENT_TESTS.length : 0;
            
            for  (t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                PerCommentParser t_Parser =
                    setUpParser(TABLE_COMMENT_TESTS[t_iIndex]);

                t_Parser.tableComment();

                switch  (t_iIndex)
                {
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                    case 14:
                        assertEquals(
                              "@isatype test failed on table comment "
                            + t_iIndex
                            + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                            TABLE_ISATYPE,
                            t_Parser.getTableIsaType());
                        break;

                    default:
                        assertNull(
                              "@isatype test failed on table comment "
                            + t_iIndex
                            + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                            t_Parser.getTableIsaType());
                        break;
                }
            }
        }
        catch  (final RecognitionException recognitionException)
        {
            fail(
                  recognitionException.getMessage()
                + " on test " + t_iIndex + " "
                + TABLE_COMMENT_TESTS[t_iIndex]);
        }
    }

    /**
     * Tests the <code>getColumnReadOnly()</code> method
     * @see org.acmsl.queryj.tools.antlr.PerCommentParser#getColumnReadOnly()
     */
    public void testColumnReadOnly()
    {
        int t_iIndex = 0;
        
        try
        {
            int t_iCount =
                (COLUMN_COMMENT_TESTS != null)
                ?  COLUMN_COMMENT_TESTS.length
                :  0;
            
            for  (t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                PerCommentParser t_Parser =
                    setUpParser(COLUMN_COMMENT_TESTS[t_iIndex]);

                t_Parser.columnComment();

                switch  (t_iIndex)
                {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                    case 17:
                    case 19:
                    case 21:
                    case 22:
                    case 24:
                    case 26:
                    case 28:
                        assertTrue(
                              "@readonly test failed on column comment "
                            + t_iIndex
                            + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                            t_Parser.getColumnReadOnly());
                        break;

                    default:
                        assertFalse(
                              "@readonly test failed on column comment "
                            + t_iIndex
                            + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                            t_Parser.getColumnReadOnly());
                        break;
                }
            }
        }
        catch  (final RecognitionException recognitionException)
        {
            fail(
                  recognitionException.getMessage()
                + " on test " + t_iIndex + " "
                + COLUMN_COMMENT_TESTS[t_iIndex]);
        }
    }

    /**
     * Tests the <code>getColumnBool()</code> method
     * @see org.acmsl.queryj.tools.antlr.PerCommentParser#getColumnBool()
     */
    public void testColumnBool()
    {
        int t_iIndex = 0;
        
        try
        {
            int t_iCount =
                (COLUMN_COMMENT_TESTS != null)
                ?  COLUMN_COMMENT_TESTS.length
                :  0;
            
            for  (t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                PerCommentParser t_Parser =
                    setUpParser(COLUMN_COMMENT_TESTS[t_iIndex]);

                t_Parser.columnComment();

                switch  (t_iIndex)
                {
                    case 2:
                    case 3:
                    case 6:
                    case 7:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                        assertEquals(
                              "@bool test failed on column comment "
                            + t_iIndex
                            + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                            COLUMN_BOOL,
                            t_Parser.getColumnBool());
                        break;

                    default:
                        assertNull(
                              "@bool test failed on column comment "
                            + t_iIndex
                            + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                              t_Parser.getColumnBool());
                        break;
                }
            }
        }
        catch  (final RecognitionException recognitionException)
        {
            fail(
                  recognitionException.getMessage()
                + " on test " + t_iIndex + " "
                + COLUMN_COMMENT_TESTS[t_iIndex]);
        }
    }

    /**
     * Tests the <code>getColumnIsaRefs()</code> method
     * @see org.acmsl.queryj.tools.antlr.PerCommentParser#getColumnIsaRefs()
     */
    public void testColumnIsaRefs()
    {
        int t_iIndex = 0;
        
        try
        {
            int t_iCount =
                (COLUMN_COMMENT_TESTS != null)
                ?  COLUMN_COMMENT_TESTS.length
                :  0;
            
            for  (t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                PerCommentParser t_Parser =
                    setUpParser(COLUMN_COMMENT_TESTS[t_iIndex]);

                t_Parser.columnComment();

                String[][] t_astrIsaRefs = t_Parser.getColumnIsaRefs();

                switch  (t_iIndex)
                {
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 9:
                    case 10:
                    case 13:
                    case 14:
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 23:
                    case 24:
                    case 27:
                    case 28:
                    case 29:
                    case 32:
                    case 33:
                        assertNotNull(
                              "@isarefs test failed on column comment "
                            + t_iIndex
                            + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                            t_astrIsaRefs);

                        for  (int t_iIsaRefIndex = 0;
                                  t_iIsaRefIndex < ISAREFS_IDS.length; 
                                  t_iIsaRefIndex++)
                        {
                            assertNotNull(
                                  "@isarefs test failed on column comment "
                                + t_iIndex
                                + " \""
                                + COLUMN_COMMENT_TESTS[t_iIndex]
                                + "\"",
                                t_astrIsaRefs[t_iIsaRefIndex]);

                            assertTrue(
                                  "@isarefs test failed on column comment "
                                + t_iIndex
                                + " \""
                                + COLUMN_COMMENT_TESTS[t_iIndex]
                                + "\"",
                                t_astrIsaRefs[t_iIsaRefIndex].length == 2);

                            assertEquals(
                                  "@isarefs test failed on column comment "
                                + t_iIndex
                                + " \""
                                + COLUMN_COMMENT_TESTS[t_iIndex]
                                + "\"",
                                ISAREFS_IDS[t_iIsaRefIndex],
                                t_astrIsaRefs[t_iIsaRefIndex][0]);

                            assertEquals(
                                  "@isarefs test failed on column comment "
                                + t_iIndex
                                + " \""
                                + COLUMN_COMMENT_TESTS[t_iIndex]
                                + "\"",
                                ISAREFS_REFERENCES[t_iIsaRefIndex],
                                t_astrIsaRefs[t_iIsaRefIndex][1]);
                        }
                        break;

                    default:
                        assertNull(
                              "@isarefs test failed on column comment "
                            + t_iIndex
                            + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                            t_astrIsaRefs);
                        break;
                }
            }
        }
        catch  (final RecognitionException recognitionException)
        {
            fail(
                  recognitionException.getMessage()
                + " on test " + t_iIndex + " "
                + COLUMN_COMMENT_TESTS[t_iIndex]);
        }
    }

    /**
     * Tests the <code>getColumnOraSeq()</code> method
     * @see org.acmsl.queryj.tools.antlr.PerCommentParser#getColumnOraSeq()
     */
    public void testColumnOraSeq()
    {
        int t_iIndex = 0;
        
        try
        {
            int t_iCount =
                (COLUMN_COMMENT_TESTS != null)
                ?  COLUMN_COMMENT_TESTS.length
                :  0;
            
            for  (t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                PerCommentParser t_Parser =
                    setUpParser(COLUMN_COMMENT_TESTS[t_iIndex]);

                t_Parser.columnComment();

                switch  (t_iIndex)
                {
                    case 16:
                    case 17:
                    case 20:
                    case 21:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                        assertEquals(
                              "@oraseq test failed on column comment "
                            + t_iIndex
                            + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                            COLUMN_ORASEQ,
                            t_Parser.getColumnOraSeq());
                        break;

                    default:
                        assertNull(
                              "@oraseq test failed on column comment "
                            + t_iIndex
                            + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                              t_Parser.getColumnOraSeq());
                        break;
                }
            }
        }
        catch  (final RecognitionException recognitionException)
        {
            fail(
                  recognitionException.getMessage()
                + " on test " + t_iIndex + " "
                + COLUMN_COMMENT_TESTS[t_iIndex]);
        }
    }

    /**
     * Sets up the comment parser.
     * @param comment the comment to parse.
     * @return the <code>PerCommentParser</code> instance.
     * @throws RecognitionException if the comment cannot be parsed.
     * @precondition comment != null
     */
    protected PerCommentParser setUpParser(final String comment)
        throws  RecognitionException
    {
        PerCommentParser result = null;

        Log t_Log = UniqueLogFactory.getLog(PerCommentParserTest.class);

        if  (t_Log != null)
        {
            t_Log.info("Parsing '" + comment + "'");
        }

        PerCommentLexer t_Lexer =
            new PerCommentLexer(
                new ANTLRStringStream(comment));
            
        CommonTokenStream t_Tokens =
            new CommonTokenStream(t_Lexer);

        result = new PerCommentParser(t_Tokens);

        
        return result;
    }
}
