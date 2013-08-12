/*
                        queryj

    Copyright (C) 2002-today  Jose San Leandro Armendariz
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
    Contact info: jose.sanleandro@acm-sl.com

 ******************************************************************************
 *
 * Filename: ParseTemplateDefsHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: 
 *
 * Date: 2013/08/12
 * Time: 14:33
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.templates.packaging.TemplatePackagingSettings;
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefLexer;
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefNameVisitor;
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefParser;
import org.acmsl.queryj.templates.packaging.exceptions.TemplatePackagingCheckedException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/*
 * Importing ANTLR classes.
 */
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 */
@ThreadSafe
public class ParseTemplateDefsHandler
    extends AbstractQueryJCommandHandler
    implements TemplatePackagingSettings
{

    /**
     * Asks the handler to process the command. The idea is that each
     * command handler decides if such command is suitable of being
     * processed, and if so perform the concrete actions the command
     * represents.
     *
     * @param command the command to process (or not).
     * @return <code>true</code> if the handler actually process the command,
     *         or maybe because it's not desirable to continue the chain.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        @Nullable final List<File> templateDefFiles =
            new QueryJCommandWrapper<File>(command).getListSetting(DEF_FILES);

        if (templateDefFiles != null)
        {
            for (@Nullable final File defFile : templateDefFiles)
            {
                if (defFile != null)
                {
                    parseDefFile(defFile);
                }
            }
        }
        return false;
    }

    /**
     * Parses a template def file.
     * @param file the file to parse.
     * @throws TemplatePackagingCheckedException if the parsing fails.
     */
    protected void parseDefFile(@NotNull final File file)
        throws TemplatePackagingCheckedException
    {
        @Nullable final TemplateDefParser t_Parser;

        try
        {
            t_Parser = setUpParser(file);
        }
        catch (final IOException missingFile)
        {
            // TODO
            throw new RuntimeException("Cannot set up the parser", missingFile);
        }

        @Nullable final ParseTree tree;

        try
        {
            tree = t_Parser.def();
        }
        catch (@NotNull final Throwable invalidClass)
        {
            // TODO
            throw new RuntimeException("Cannot process the template definition", invalidClass);
        }

        @NotNull final TemplateDefNameVisitor nameVisitor = new TemplateDefNameVisitor();

        @Nullable final String defName;

        try
        {
            nameVisitor.visit(tree);

            defName = nameVisitor.getName();
        }
        catch (@NotNull final Throwable invalidClass)
        {
            // TODO
            throw new RuntimeException("Invalid template definition", invalidClass);
        }

        System.out.println("Name found: " + defName);
    }

    /**
     * Sets up the template definition parser.
     * @param file the templated def contents to parse.
     * @return the {@link TemplateDefParser} instance.
     * @throws org.antlr.v4.runtime.RecognitionException if the comment cannot be parsed.
     * @throws IOException if the file could not be read.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected TemplateDefParser setUpParser(@NotNull final File file)
    throws RecognitionException,
            IOException
    {
        @NotNull final TemplateDefParser result;

        @NotNull final TemplateDefLexer t_Lexer =
            new TemplateDefLexer(new ANTLRFileStream(file.getAbsolutePath()));

        @NotNull final CommonTokenStream t_Tokens = new CommonTokenStream(t_Lexer);

        result = new TemplateDefParser(t_Tokens);

        return result;
    }
}
