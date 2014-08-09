/*
                        QueryJ Template Packaging Plugin

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
 * Description: Parses the template definition files and provides the
 *              template definition information.
 *
 * Date: 2013/08/12
 * Time: 14:33
 *
 */
package org.acmsl.queryj.templates.packaging.handlers;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.Literals;
import org.acmsl.queryj.templates.packaging.TemplateDef;
import org.acmsl.queryj.templates.packaging.TemplateDefImpl;
import org.acmsl.queryj.templates.packaging.TemplateDefOutput;
import org.acmsl.queryj.templates.packaging.TemplateDefType;
import org.acmsl.queryj.templates.packaging.TemplatePackagingSettings;
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefDebugVisitor;
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefDisabledVisitor;
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefFilenameBuilderVisitor;
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefLexer;
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefMetadataVisitor;
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefNameVisitor;
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefOutputVisitor;
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefPackageVisitor;
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefParser;
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefTypeVisitor;
import org.acmsl.queryj.templates.packaging.exceptions.CannotProcessTemplateDefException;
import org.acmsl.queryj.templates.packaging.exceptions.CannotSetUpTemplateDefParserException;
import org.acmsl.queryj.templates.packaging.exceptions.InvalidTemplateDefException;
import org.acmsl.queryj.templates.packaging.exceptions.TemplateAssociatedToTemplateDefDoesNotExist;
import org.acmsl.queryj.templates.packaging.exceptions.TemplatePackagingCheckedException;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/*
 * Importing ANTLR classes.
 */
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;

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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Parses the template definition files and provides the template
 * definition information.
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
     * @return {@code true} if the handler actually process the command,
     *         or maybe because it's not desirable to continue the chain.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand command)
        throws QueryJBuildException
    {
        return handle(command, StringUtils.getInstance());
    }

    /**
     * Asks the handler to process the command. The idea is that each
     * command handler decides if such command is suitable of being
     * processed, and if so perform the concrete actions the command
     * represents.
     * @param command the command to process (or not).
     * @param stringUtils the {@link StringUtils} instance.
     * @return {@code true} if the handler actually process the command,
     *         or maybe because it's not desirable to continue the chain.
     * @throws QueryJBuildException if the template defs cannot be parsed.
     */
    protected boolean handle(@NotNull final QueryJCommand command, @NotNull final StringUtils stringUtils)
        throws QueryJBuildException
    {
        @Nullable final List<File> templateDefFiles =
            new QueryJCommandWrapper<File>(command).getListSetting(DEF_FILES);

        if (templateDefFiles != null)
        {
            @Nullable List<TemplateDef<String>> templateDefs =
                new QueryJCommandWrapper<TemplateDef<String>>(command).getListSetting(TEMPLATE_DEFS);

            if (templateDefs == null)
            {
                templateDefs = new ArrayList<>(templateDefFiles.size());
            }

            for (@Nullable final File defFile : templateDefFiles)
            {
                if (defFile != null)
                {
                    @NotNull final TemplateDef<String> templateDef = parseDefFile(defFile);

                    if (isValid(templateDef, stringUtils))
                    {
                        if (!templateDef.isDisabled())
                        {
                            templateDefs.add(templateDef);
                        }
                    }
                    else
                    {
                        @Nullable final File templateFile = retrieveTemplateFile(templateDef, stringUtils);

                        if (templateFile != null)
                        {
                            throw
                                new TemplateAssociatedToTemplateDefDoesNotExist(templateDef, templateFile);
                        }
                        else
                        {
                            throw
                                new TemplateAssociatedToTemplateDefDoesNotExist(templateDef);
                        }
                    }

//                    if (templateDefs.size() == 1)
//                    {
                        new QueryJCommandWrapper<List<TemplateDef<String>>>(command).setSetting(
                            TEMPLATE_DEFS, templateDefs);
//                    }
                }
            }
        }

        return false;
    }

    /**
     * Retrieves the template file.
     * @param templateDef the {@link TemplateDef}.
     * @param stringUtils the {@link StringUtils} instance.
     * @return the template {@link File file}.
     */
    @Nullable
    protected File retrieveTemplateFile(
        @NotNull final TemplateDef<String> templateDef, @NotNull final StringUtils stringUtils)
    {
        @Nullable final File result;

        @Nullable final File templateDefFile = templateDef.getFile();

        if (templateDefFile != null)
        {
            result =
                new File(
                    stringUtils.extractClassName(
                        templateDefFile.getAbsolutePath().replaceAll("\\.", "\126").replaceAll("\\.stg\\.def$", "").replaceAll(File.separator, "."))
                    .replaceAll("\\.", File.separator).replaceAll("\126", ".")
                    + File.separator
                    + templateDef.getName());
        }
        else
        {
            result = null;
        }

        return result;
    }

    /**
     * Checks whether the template def is valid.
     * @param templateDef the {@link TemplateDef} to check.
     * @param stringUtils the {@link StringUtils} instance.
     * @return {@code true} in such case.
     */
    protected boolean isValid(@NotNull final TemplateDef<String> templateDef, @NotNull final StringUtils stringUtils)
    {
        final boolean result;

        @Nullable final File templateFile = retrieveTemplateFile(templateDef, stringUtils);

        if (templateFile != null)
        {
            result = templateFile.exists();
        }
        else
        {
            result = true;
        }

        return result;
    }

    /**
     * Parses a template def file.
     * @param file the file to parse.
     * @return the template def.
     * @throws TemplatePackagingCheckedException if the template def file cannot be parsed.
     */
    @NotNull
    public TemplateDef<String> parseDefFile(@NotNull final File file)
        throws TemplatePackagingCheckedException
    {
        @Nullable final TemplateDefParser t_Parser;

        try
        {
            t_Parser = setUpParser(file);
        }
        catch (final IOException missingFile)
        {
            throw new CannotSetUpTemplateDefParserException(file, missingFile);
        }

        return parseDef(t_Parser, file);
    }

    /**
     * Parses a template def stream.
     * @param inputStream the stream to parse.
     * @return the template def.
     * @throws TemplatePackagingCheckedException if the template def stream cannot be parsed.
     */
    @NotNull
    public TemplateDef<String> parseDefStream(@NotNull final InputStream inputStream)
        throws TemplatePackagingCheckedException
    {
        @Nullable final TemplateDefParser t_Parser;

        try
        {
            t_Parser = setUpParser(inputStream);
        }
        catch (final IOException invalidStream)
        {
            throw new CannotSetUpTemplateDefParserException(invalidStream);
        }

        return parseDef(t_Parser);
    }

    /**
     * Parses a template def file.
     * @param parser the parser.
     * @return the {@link TemplateDef}.
     * @throws TemplatePackagingCheckedException if the template def cannot be parsed.
     */
    @NotNull
    public TemplateDef<String> parseDef(@NotNull final TemplateDefParser parser)
        throws TemplatePackagingCheckedException
    {
        return parseDef(parser, null);
    }

    /**
     * Parses a template def file.
     * @param parser the parser.
     * @param file the file.
     * @return the {@link TemplateDef}.
     * @throws TemplatePackagingCheckedException if the template def cannot be parsed.
     */
    @NotNull
    public TemplateDef<String> parseDef(
        @NotNull final TemplateDefParser parser, @Nullable final File file)
        throws TemplatePackagingCheckedException
    {
        @NotNull final TemplateDef<String> result;

        @Nullable final ParseTree tree;

        try
        {
            tree = parser.templateDef();
        }
        catch (@NotNull final Throwable invalidClass)
        {
            if (file == null)
            {
                throw new CannotProcessTemplateDefException(invalidClass);
            }
            else
            {
                throw new CannotProcessTemplateDefException(file, invalidClass);
            }
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
            if (file == null)
            {
                throw new InvalidTemplateDefException("name", invalidClass);
            }
            else
            {
                throw new InvalidTemplateDefException("name", file, invalidClass);
            }
        }

        @NotNull final TemplateDefTypeVisitor typeVisitor = new TemplateDefTypeVisitor();

        @Nullable final String defType;

        try
        {
            typeVisitor.visit(tree);

            defType = typeVisitor.getType();
        }
        catch (@NotNull final Throwable invalidClass)
        {
            if (file == null)
            {
                throw new InvalidTemplateDefException("type", invalidClass);
            }
            else
            {
                throw new InvalidTemplateDefException("type", file, invalidClass);
            }
        }

        @NotNull final TemplateDefOutputVisitor outputVisitor = new TemplateDefOutputVisitor();

        @Nullable final String defOutput;

        try
        {
            outputVisitor.visit(tree);

            defOutput = outputVisitor.getOutput();
        }
        catch (@NotNull final Throwable invalidClass)
        {
            if (file == null)
            {
                throw new InvalidTemplateDefException("output", invalidClass);
            }
            else
            {
                throw new InvalidTemplateDefException("output", file, invalidClass);
            }
        }

        @NotNull final TemplateDefFilenameBuilderVisitor filenameBuilderVisitor =
            new TemplateDefFilenameBuilderVisitor();

        @Nullable final String defFilenameBuilder;

        try
        {
            filenameBuilderVisitor.visit(tree);

            defFilenameBuilder = filenameBuilderVisitor.getFilenameBuilder();
        }
        catch (@NotNull final Throwable invalidClass)
        {
            if (file == null)
            {
                throw new InvalidTemplateDefException("filenameBuilder", invalidClass);
            }
            else
            {
                throw new InvalidTemplateDefException("filenameBuilder", file, invalidClass);
            }
        }

        @NotNull final TemplateDefPackageVisitor packageVisitor = new TemplateDefPackageVisitor();

        @Nullable final String defPackage;

        try
        {
            packageVisitor.visit(tree);

            defPackage = packageVisitor.getPackageName();
        }
        catch (@NotNull final Throwable invalidClass)
        {
            if (file == null)
            {
                throw new InvalidTemplateDefException(Literals.PACKAGE, invalidClass);
            }
            else
            {
                throw new InvalidTemplateDefException(Literals.PACKAGE, file, invalidClass);
            }
        }

        @NotNull final TemplateDefMetadataVisitor metadataVisitor = new TemplateDefMetadataVisitor();
        @NotNull final Map<String, String> metadata;

        try
        {
            metadataVisitor.visit(tree);

            metadata = metadataVisitor.getMetadata();
        }
        catch (@NotNull final Throwable invalidClass)
        {
            if (file == null)
            {
                throw new InvalidTemplateDefException("metadata", invalidClass);
            }
            else
            {
                throw new InvalidTemplateDefException("metadata", file, invalidClass);
            }
        }

        @NotNull final TemplateDefDisabledVisitor disabledVisitor = new TemplateDefDisabledVisitor();

        @Nullable final boolean disabled;

        try
        {
            disabledVisitor.visit(tree);

            disabled = disabledVisitor.isDisabled();
        }
        catch (@NotNull final Throwable invalidClass)
        {
            if (file == null)
            {
                throw new InvalidTemplateDefException("disabled", invalidClass);
            }
            else
            {
                throw new InvalidTemplateDefException("disabled", file, invalidClass);
            }
        }

        @NotNull final TemplateDefDebugVisitor debugVisitor = new TemplateDefDebugVisitor();

        @Nullable final boolean debug;

        try
        {
            debugVisitor.visit(tree);

            debug = debugVisitor.isDebug();
        }
        catch (@NotNull final Throwable invalidClass)
        {
            if (file == null)
            {
                throw new InvalidTemplateDefException("debug", invalidClass);
            }
            else
            {
                throw new InvalidTemplateDefException("debug", file, invalidClass);
            }
        }

        result =
            new TemplateDefImpl(
                defName,
                TemplateDefType.getEnumFromString(defType),
                TemplateDefOutput.getEnumFromString(defOutput),
                defFilenameBuilder,
                defPackage,
                file,
                metadata,
                disabled,
                debug);

        return result;
    }

    /**
     * Sets up the template definition parser.
     * @param file the template def contents to parse.
     * @return the {@link TemplateDefParser} instance.
     * @throws RecognitionException if the format is invalid.
     * @throws IOException if the source cannot be read.
     */
    @NotNull
    protected TemplateDefParser setUpParser(@NotNull final File file)
    throws RecognitionException,
           IOException
    {
        return setUpParser(new ANTLRFileStream(file.getAbsolutePath()));
    }

    /**
     * Sets up the template definition parser.
     * @param inputStream the template def contents to parse.
     * @return the {@link TemplateDefParser} instance.
     * @throws RecognitionException if the format is invalid.
     * @throws IOException if the source cannot be read.
     */
    @NotNull
    protected TemplateDefParser setUpParser(@NotNull final InputStream inputStream)
        throws RecognitionException,
               IOException
    {
        return setUpParser(new ANTLRInputStream(inputStream));
    }

    /**
     * Sets up the template definition parser.
     * @param stream the {@link ANTLRInputStream}.
     * @return the {@link TemplateDefParser} instance.
     * @throws RecognitionException if the format is invalid.
     * @throws IOException if the source cannot be read.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected TemplateDefParser setUpParser(@NotNull final ANTLRInputStream stream)
        throws RecognitionException,
               IOException
    {
        @NotNull final TemplateDefParser result;

        @NotNull final TemplateDefLexer t_Lexer = new TemplateDefLexer(stream);

        @NotNull final CommonTokenStream t_Tokens = new CommonTokenStream(t_Lexer);

        result = new TemplateDefParser(t_Tokens);

        return result;
    }
}
