//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Filename: MetaLanguageUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides insight about the meta-language used in model
 *              descriptions.
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.antlr.PerCommentLexer;
import org.acmsl.queryj.tools.antlr.PerCommentParser;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing some ANTLR 3 classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Provides insight about the meta-language used in model descriptions.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class MetaLanguageUtils
    implements  Singleton,
                Utils
{
    /**
     * The cache of parsers.
     */
    private final Map PARSER_CACHE = new HashMap();

    /**
     * An empty String array array.
     */
    static final String[][] EMPTY_STRING_ARRAY_ARRAY =
        new String[0][0];

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class MetaLanguageUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final MetaLanguageUtils SINGLETON =
            new MetaLanguageUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected MetaLanguageUtils() {};

    /**
     * Retrieves a <code>MetaLanguageUtils</code> instance.
     * @return such instance.
     */
    public static MetaLanguageUtils getInstance()
    {
        return MetaLanguageUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Checks whether given table is static.
     * @param tableComment the table's comment.
     * @return <code>true</code> in such case.
     * @precondition tableComment != null
     */
    public boolean isStatic(final String tableComment)
    {
        return (retrieveStaticAttribute(tableComment) != null);
    }

    /**
    * Retrieves the attribute used to identify table content's
    * staticly.
    * @param tableComment the table's comment.
    * @return such attribute.
    */
    public String retrieveStaticAttribute(final String tableComment)
    {
        String result = null;

        if  (!isEmpty(tableComment))
        {
            try
            {
                PerCommentParser t_Parser = setUpParser(tableComment);

                t_Parser.tableComment();

                result = t_Parser.getTableStatic();
            }
            catch  (final RecognitionException recognitionException)
            {
                Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        "Invalid table comment: " + tableComment,
                        recognitionException);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the parent table in an ISA relationship, as
     * declated by the table comment.
     * @param tableComment the table's comment.
     * @return the parent table, or <code>null</code> otherwise.
     * @precondition tableComment != null
     */
    public String retrieveDeclaredParent(final String tableComment)
    {
        String result = null;

        if  (!isEmpty(tableComment))
        {
            try
            {
                PerCommentParser t_Parser = setUpParser(tableComment);

                t_Parser.tableComment();

                result = t_Parser.getTableIsa();
            }
            catch  (final RecognitionException recognitionException)
            {
                Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        "Invalid table comment: " + tableComment,
                        recognitionException);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the table the comment owner discriminates among its
     * children in an ISA relationship.
     * @param tableComment the table's comment.
     * @return the ISA parent table.
     * @precondition tableComment != null
     */
    public String retrieveDiscriminatingParent(final String tableComment)
    {
        String result = null;

        if  (!isEmpty(tableComment))
        {
            try
            {
                PerCommentParser t_Parser = setUpParser(tableComment);

                t_Parser.tableComment();

                result = t_Parser.getTableIsaType();
            }
            catch  (final RecognitionException recognitionException)
            {
                Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        "Invalid table comment: " + tableComment,
                        recognitionException);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the value to denote <code>true</code> values. If
     * <code>null</code>, then the column is not considered boolean.
     * @param columnComment the column comment.
     * @return the values to use in the database to denote <code>true</code>,
     * <code>false</code> and <code>null</code> values, or <code>null</code>
     * if the column is not boolean.
     * @precondition columnComment != null
     */
    public String[] retrieveColumnBool(final String columnComment)
    {
        String[] result = null;

        if  (!isEmpty(columnComment))
        {
             try
             {
                 PerCommentParser t_Parser = setUpParser(columnComment);

                 t_Parser.columnComment();

                 String t_strTrue = t_Parser.getColumnBoolTrue();

                 if  (t_strTrue != null)
                 {
                     result = new String[3];
                     result[0] = t_strTrue;
                     result[1] = t_Parser.getColumnBoolFalse();
                     result[2] = t_Parser.getColumnBoolNull();
                 }
             }
             catch  (final RecognitionException recognitionException)
             {
                 Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                 if  (t_Log != null)
                 {
                     t_Log.error(
                         "Invalid column comment: " + columnComment,
                         recognitionException);
                 }
             }
        }

        return result;
    }

    /**
     * Retrieves whether the column is meant to be read-only from the
     * Java side (i.e. a last-modified or creation-date timestamp).
     * @param columnComment the column comment.
     * @return such condition.
     * @precondition columnComment != null
     */
    public boolean retrieveColumnReadOnly(final String columnComment)
    {
        boolean result = false;

        if  (!isEmpty(columnComment))
        {
            try
            {
                PerCommentParser t_Parser = setUpParser(columnComment);

                t_Parser.columnComment();

                result = t_Parser.getColumnReadOnly();
            }
            catch  (final RecognitionException recognitionException)
            {
                Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        "Invalid column comment: " + columnComment,
                        recognitionException);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves which values of the column correspond to which ISA descendant
     * table (whose parent is declared in table comments as <code>@isatype</code>).
     * @param columnComment the column comment.
     * @return such associations.
     * @precondition columnComment != null
     */
    public String[][] retrieveColumnDiscriminatedTables(
        final String columnComment)
    {
        String[][] result = PerCommentParser.EMPTY_STRING_STRING_ARRAY;

        if  (!isEmpty(columnComment))
        {
            try
            {
                PerCommentParser t_Parser = setUpParser(columnComment);

                t_Parser.columnComment();
                result = t_Parser.getColumnIsaRefs();
            }
            catch  (final RecognitionException recognitionException)
            {
                Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        "Invalid column comment: " + columnComment,
                        recognitionException);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves whether the table is meant to be decorated.
     * @param tableComment the table comment.
     * @return such condition.
     * @precondition tableComment != null
     */
    public boolean retrieveTableDecorator(final String tableComment)
    {
        boolean result = false;

        if  (!isEmpty(tableComment))
        {
            try
            {
                PerCommentParser t_Parser = setUpParser(tableComment);

                t_Parser.tableComment();

                result = t_Parser.getTableDecorator();
            }
            catch  (final RecognitionException recognitionException)
            {
                Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        "Invalid table comment: " + tableComment,
                        recognitionException);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves whether the table is modelling a relationship.
     * @param tableComment the table comment.
     * @return such condition.
     * @precondition tableComment != null
     */
    public String[][] retrieveTableRelationship(final String tableComment)
    {
        String[][] result = EMPTY_STRING_ARRAY_ARRAY;

        if  (!isEmpty(tableComment))
        {
            try
            {
                PerCommentParser t_Parser = setUpParser(tableComment);

                t_Parser.tableComment();

                result = t_Parser.getTableRelationship();
            }
            catch  (final RecognitionException recognitionException)
            {
                Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        "Invalid table comment: " + tableComment,
                        recognitionException);
                }
            }
        }

        return result;
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

        Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

        if  (   (t_Log != null)
             && (t_Log.isDebugEnabled()))
        {
            t_Log.debug("Parsing '" + comment + "'");
        }

        if  (comment != null)
        {
            result = (PerCommentParser) PARSER_CACHE.get(comment);

            if  (result == null)
            {
                PerCommentLexer t_Lexer =
                    new PerCommentLexer(
                        new ANTLRStringStream(comment));
            
                CommonTokenStream t_Tokens =
                    new CommonTokenStream(t_Lexer);

                result = new PerCommentParser(t_Tokens);

                PARSER_CACHE.put(comment, result);
            }
        }

        return result;
    }

    /**
     * Checks whether given input is empty or not.
     * @param text the text.
     * @return <code>true</code> in such case.
     */
    protected boolean isEmpty(final String text)
    {
        return isEmpty(text, StringValidator.getInstance());
    }

    /**
     * Checks whether given input is empty or not.
     * @param text the text.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @return <code>true</code> in such case.
     * @precondition stringValidator != null
     */
    protected boolean isEmpty(
        final String text, final StringValidator stringValidator)
    {
        return stringValidator.isEmpty(text);
    }

    /**
     * Checks whether given table contains static values or not.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return <code>true</code> in such case.
     */
    public boolean containsStaticValues(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        return metadataManager.isTableStatic(tableName);
    }

}
