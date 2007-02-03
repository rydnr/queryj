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
     * The column 'isarefs' annotation.
     */
    protected static final String COLUMN_ISAREFS =
        "(1, \"first_isa_child\") (2, \"second_isa_child\" )";

    /**
     * The column comment tests.
     */
    protected static final String[] COLUMN_COMMENT_TESTS =
        new String[]
        {
            COLUMN_COMMENT,
            COLUMN_COMMENT +                                                           " @readonly",
            COLUMN_COMMENT +                                 " @bool " + COLUMN_BOOL,
            COLUMN_COMMENT +                                 " @bool " + COLUMN_BOOL + " @readonly",
            COLUMN_COMMENT + " @isarefs " + COLUMN_ISAREFS,
            COLUMN_COMMENT + " @isarefs " + COLUMN_ISAREFS + " @readonly",
            COLUMN_COMMENT + " @isarefs " + COLUMN_ISAREFS + " @bool " + COLUMN_BOOL,
            COLUMN_COMMENT + " @isarefs " + COLUMN_ISAREFS + " @bool " + COLUMN_BOOL + " @readonly",
            COLUMN_COMMENT +                                                           " @readonly",
            COLUMN_COMMENT +                           " @isarefs " + COLUMN_ISAREFS,
            COLUMN_COMMENT +                           " @isarefs " + COLUMN_ISAREFS + " @readonly",
            COLUMN_COMMENT + " @bool " + COLUMN_BOOL,
            COLUMN_COMMENT + " @bool " + COLUMN_BOOL +                                 " @readonly",
            COLUMN_COMMENT + " @bool " + COLUMN_BOOL + " @isarefs " + COLUMN_ISAREFS,
            COLUMN_COMMENT + " @bool " + COLUMN_BOOL + " @isarefs " + COLUMN_ISAREFS + " @readonly"
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
                        assertNotNull(
                              "@isarefs test failed on column comment "
                            + t_iIndex
                            + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                            t_Parser.getColumnIsaRefs());
                        break;

                    default:
                        assertNull(
                              "@isarefs test failed on column comment "
                            + t_iIndex
                            + " \"" + COLUMN_COMMENT_TESTS[t_iIndex] + "\"",
                              t_Parser.getColumnIsaRefs());
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
