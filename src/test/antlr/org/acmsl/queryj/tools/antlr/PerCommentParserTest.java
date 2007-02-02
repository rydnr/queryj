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
     * The table static attribute.
     */
    protected static final String TABLE_STATIC = "name";
    
    /**
     * The table isa table.
     */
    protected static final String TABLE_ISA = "parent_table";
    
    /**
     * The table isaref reference.
     */
    protected static final String TABLE_ISAREF = "isa_discriminator";
    
    /**
     * The table comment tests.
     */
    public static final String[] TABLE_COMMENT_TESTS =
        new String[]
        {
            TABLE_COMMENT,
            TABLE_COMMENT +                                                     " @static " + TABLE_STATIC,
            TABLE_COMMENT +                              " @isa " + TABLE_ISA,
            TABLE_COMMENT +                              " @isa " + TABLE_ISA + " @static " + TABLE_STATIC,
            TABLE_COMMENT + " @isaref " + TABLE_ISAREF,
            TABLE_COMMENT + " @isaref " + TABLE_ISAREF                        + " @static " + TABLE_STATIC,
            TABLE_COMMENT + " @isaref " + TABLE_ISAREF + " @isa " + TABLE_ISA,
            TABLE_COMMENT + " @isaref " + TABLE_ISAREF + " @isa " + TABLE_ISA + " @static " + TABLE_STATIC,
            TABLE_COMMENT +                                                     " @isaref " + TABLE_ISAREF,
            TABLE_COMMENT +                        " @static " + TABLE_STATIC,
            TABLE_COMMENT +                        " @static " + TABLE_STATIC + " @isaref " + TABLE_ISAREF,
            TABLE_COMMENT + " @isa " + TABLE_ISA,
            TABLE_COMMENT + " @isa " + TABLE_ISA                              + " @isaref " + TABLE_ISAREF,
            TABLE_COMMENT + " @isa " + TABLE_ISA + " @static " + TABLE_STATIC,
            TABLE_COMMENT + " @isa " + TABLE_ISA + " @static " + TABLE_STATIC + " @isaref " + TABLE_ISAREF
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
     * Tests the getTableComment() method
     * @see org.acmsl.queryj.tools.antlr.PerCommentParser#getTableComment(String)
     */
    public void testTableCommentTest()
    {
        int t_iIndex = 0;
        
        try
        {
            int t_iCount = (TABLE_COMMENT_TESTS != null) ? TABLE_COMMENT_TESTS.length : 0;
            
            for  (t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                PerCommentParser t_Parser = setUpParser(TABLE_COMMENT_TESTS[t_iIndex]);

                t_Parser.tableComment();

                System.out.println("Table Comment:~" + t_Parser.getTableComment() + "~");

                assertEquals(
                    "Test failed on table comment \"" + TABLE_COMMENT_TESTS[t_iIndex] + "\"",
                    TABLE_COMMENT,
                    t_Parser.getTableComment());
            }
        }
        catch  (final RecognitionException recognitionException)
        {
            fail(
                  recognitionException.getMessage()
                + " on "
                + TABLE_COMMENT_TESTS[t_iIndex]);
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
