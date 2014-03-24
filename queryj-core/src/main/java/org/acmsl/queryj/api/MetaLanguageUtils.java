/*
                        QueryJ Core

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
package org.acmsl.queryj.api;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Table;
import org.acmsl.queryj.tools.antlr.PerCommentLoggingListener;
import org.acmsl.queryj.tools.antlr.PerCommentLexer;
import org.acmsl.queryj.tools.antlr.PerCommentListener;
import org.acmsl.queryj.tools.antlr.PerCommentParser;
import org.acmsl.queryj.tools.antlr.PerCommentVisitor;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing some ANTLR classes.
 */
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Provides insight about the meta-language used in model descriptions.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 */
@ThreadSafe
public class MetaLanguageUtils
    implements  Singleton,
                Utils
{
    /**
     * The cache of parsers.
     */
    private final Map<String, PerCommentParser> PARSER_CACHE = new ConcurrentHashMap<>();

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
    protected MetaLanguageUtils() {}

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
     */
    public boolean isStatic(@NotNull final String tableComment)
    {
        return (retrieveStaticAttribute(tableComment) != null);
    }

    /**
    * Retrieves the attribute used to identify table content.
    * @param tableComment the table's comment.
    * @return such attribute.
    */
    @Nullable
    public String retrieveStaticAttribute(@Nullable final String tableComment)
    {
        @Nullable String result = null;

        if  (!isEmpty(tableComment))
        {
            try
            {
                assert tableComment != null;

                @NotNull final PerCommentParser t_Parser = setUpParser(tableComment);

                @NotNull final ParseTree tree = t_Parser.tableComment();

                @NotNull final PerCommentVisitor<String> visitor = new PerCommentTabStaticVisitor();

                result = visitor.visit(tree);
            }
            catch  (@NotNull final RecognitionException recognitionException)
            {
                @Nullable final Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        Literals.INVALID_TABLE_COMMENT + tableComment,
                        recognitionException);
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the parent table in an ISA relationship, as
     * declared by the table comment.
     * @param tableComment the table's comment.
     * @return the parent table, or <code>null</code> otherwise.
     */
    @Nullable
    public String retrieveDeclaredParent(@NotNull final String tableComment)
    {
        @Nullable String result = null;

        if  (!isEmpty(tableComment))
        {
            try
            {
                @NotNull final PerCommentParser t_Parser = setUpParser(tableComment);

                @NotNull final ParseTree tree = t_Parser.tableComment();

                @NotNull final PerCommentVisitor<String> visitor = new PerCommentTabIsaVisitor();

                result = visitor.visit(tree);
            }
            catch  (@NotNull final RecognitionException recognitionException)
            {
                @Nullable final Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        Literals.INVALID_TABLE_COMMENT + tableComment,
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
     */
    @SuppressWarnings("unused")
    @Nullable
    public String retrieveDiscriminatingParent(@NotNull final String tableComment)
    {
        @Nullable String result = null;

        if  (!isEmpty(tableComment))
        {
            try
            {
                @NotNull final PerCommentParser t_Parser = setUpParser(tableComment);

                @NotNull final ParseTree tree = t_Parser.tableComment();

                @NotNull final PerCommentVisitor<String> visitor = new PerCommentTabIsatypeVisitor();

                result = visitor.visit(tree);
            }
            catch  (@NotNull final RecognitionException recognitionException)
            {
                @Nullable final Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        Literals.INVALID_TABLE_COMMENT + tableComment,
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
     */
    @NotNull
    @SuppressWarnings("unused")
    public String[] retrieveColumnBool(@NotNull final String columnComment)
    {
        @Nullable String[] result = null;

        boolean done = false;

        if  (!isEmpty(columnComment))
        {
             try
             {
                 @NotNull final PerCommentParser t_Parser = setUpParser(columnComment);

                 @NotNull final ParseTree tree = t_Parser.columnComment();

                 @NotNull final PerCommentVisitor<List<String>> visitor = new PerCommentColBoolVisitor();

                 @Nullable final List<String> boolDefs = visitor.visit(tree);

                 if (   (boolDefs != null)
                     && (boolDefs.size() > 1))
                 {
                     done = true;
                     result = boolDefs.toArray(new String[boolDefs.size()]);
                 }
             }
             catch  (@NotNull final RecognitionException recognitionException)
             {
                 @Nullable final Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                 if  (t_Log != null)
                 {
                     t_Log.error(
                         Literals.INVALID_COLUMN_COMMENT + columnComment,
                         recognitionException);
                 }
             }
        }

        if (   (!done)
            || (result == null))
        {
            result = new String[0];
        }

        return result;
    }

    /**
     * Retrieves whether the column is meant to be read-only from the
     * Java side (i.e. a last-modified or creation-date timestamp).
     * @param columnComment the column comment.
     * @return such condition.
     */
    @SuppressWarnings("unused")
    public boolean retrieveColumnReadOnly(@NotNull final String columnComment)
    {
        boolean result = false;

        if  (!isEmpty(columnComment))
        {
            try
            {
                @NotNull final PerCommentParser t_Parser = setUpParser(columnComment);

                @NotNull final ParseTree tree = t_Parser.columnComment();

                @NotNull final PerCommentVisitor<Boolean> visitor = new PerCommentColReadonlyVisitor();

                @Nullable final Boolean resultValue = visitor.visit(tree);

                if (resultValue != null)
                {
                    result = resultValue;
                }
            }
            catch  (@NotNull final RecognitionException recognitionException)
            {
                @Nullable final Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        Literals.INVALID_COLUMN_COMMENT + columnComment,
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
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<List<String>> retrieveColumnDiscriminatedTables(
        @NotNull final String columnComment)
    {
        @Nullable List<List<String>> result = null;

        if  (!isEmpty(columnComment))
        {
            try
            {
                @NotNull final PerCommentParser t_Parser = setUpParser(columnComment);

                @NotNull final ParseTree tree = t_Parser.columnComment();

                @NotNull final PerCommentVisitor<List<List<String>>> visitor = new PerCommentColIsarefsVisitor();

                @Nullable final List<List<String>> resultValue = visitor.visit(tree);

                if (resultValue != null)
                {
                    result = resultValue;
                }
            }
            catch  (@NotNull final RecognitionException recognitionException)
            {
                @Nullable final Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        Literals.INVALID_COLUMN_COMMENT + columnComment,
                        recognitionException);
                }
            }
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Retrieves whether the table is meant to be decorated.
     * @param tableComment the table comment.
     * @return such condition.
     */
    public boolean retrieveTableDecorator(@NotNull final String tableComment)
    {
        boolean result = false;

        if  (!isEmpty(tableComment))
        {
            try
            {
                @NotNull final PerCommentParser t_Parser = setUpParser(tableComment);

                @Nullable final ParseTree t_Tree = t_Parser.tableComment();

                if (t_Tree != null)
                {
                    @NotNull final PerCommentVisitor<Boolean> t_Visitor = new PerCommentTabDecoratorVisitor();

                    try
                    {
                        @Nullable final Boolean resultValue = t_Visitor.visit(t_Tree);

                        if (resultValue != null)
                        {
                            result = resultValue;
                        }
                    }
                    catch (@NotNull final Throwable npe)
                    {
                        @Nullable final Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                        if (t_Log != null)
                        {
                            t_Log.fatal(npe);
                        }
                        npe.printStackTrace(System.err);
                    }
                }
            }
            catch  (@NotNull final RecognitionException recognitionException)
            {
                @Nullable final Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        Literals.INVALID_TABLE_COMMENT + tableComment,
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
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<List<String>> retrieveTableRelationship(@NotNull final String tableComment)
    {
        @Nullable List<List<String>> result = null;

        if  (!isEmpty(tableComment))
        {
            try
            {
                @NotNull final PerCommentParser t_Parser = setUpParser(tableComment);

                @NotNull final ParseTree tree = t_Parser.tableComment();

                @NotNull final PerCommentVisitor<List<List<String>>> visitor = new PerCommentTabRelationshipVisitor();

                @Nullable final List<List<String>> resultValue = visitor.visit(tree);

                if (resultValue != null)
                {
                    result = resultValue;
                }
            }
            catch  (final RecognitionException recognitionException)
            {
                @Nullable final Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

                if  (t_Log != null)
                {
                    t_Log.error(
                        Literals.INVALID_TABLE_COMMENT + tableComment,
                        recognitionException);
                }

                result = new ArrayList<>();
            }
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }

        return result;
    }

    /**
     * Sets up the comment parser.
     * @param comment the comment to parse.
     * @return the {@link PerCommentParser} instance.
     */
    @SuppressWarnings("unchecked")
    protected PerCommentParser setUpParser(@NotNull final String comment)
        throws  RecognitionException
    {
        @NotNull final PerCommentParser result;

        @Nullable final Log t_Log = UniqueLogFactory.getLog(MetaLanguageUtils.class);

        if  (   (t_Log != null)
             && (t_Log.isDebugEnabled()))
        {
            t_Log.debug("Parsing '" + comment + "'");
        }

        @NotNull final PerCommentLexer t_Lexer =
            new PerCommentLexer(
                new ANTLRInputStream(comment));

        @NotNull final CommonTokenStream t_Tokens =
            new CommonTokenStream(t_Lexer);

        result = new PerCommentParser(t_Tokens);

        @NotNull final PerCommentListener listener = new PerCommentLoggingListener(comment);

        result.addParseListener(listener);

        return result;
    }

    /**
     * Checks whether given input is empty or not.
     * @param text the text.
     * @return <code>true</code> in such case.
     */
    protected boolean isEmpty(@Nullable final String text)
    {
        return isEmpty(text, StringValidator.getInstance());
    }

    /**
     * Checks whether given input is empty or not.
     * @param text the text.
     * @param stringValidator the {@link StringValidator} instance.
     * @return <code>true</code> in such case.
     */
    protected boolean isEmpty(
        @Nullable final String text, @NotNull final StringValidator stringValidator)
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
        boolean result = false;

        @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
            metadataManager.getTableDAO().findByName(tableName);

        if (t_Table != null)
        {
            result = t_Table.isStatic();
        }

        return result;
    }

    @Override
    @NotNull
    public String toString()
    {
        return
              "{ \"class\": \"" + MetaLanguageUtils.class.getName() + '"'
            + ", \"PARSER_CACHE\": " + PARSER_CACHE
            + " }";
    }
}
