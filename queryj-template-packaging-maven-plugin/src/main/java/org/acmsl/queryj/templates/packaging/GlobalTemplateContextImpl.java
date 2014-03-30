/*
                        QueryJ Template Packaging

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
 * Filename: GlobalTemplateContextImpl.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Straightforward implementation of GlobalTemplateContext.
 *
 * Date: 2013/09/15
 * Time: 06:49
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.templates.packaging.exceptions.TemplateDefNotAvailableException;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.util.List;

/**
 * Straightforward implementation of {@link GlobalTemplateContext}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/15 06:49
 */
@ThreadSafe
public class GlobalTemplateContextImpl
    extends AbstractTemplatePackagingContext
    implements GlobalTemplateContext
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -1563568480106519552L;

    /**
     * Creates a new global context.
     * @param fileName the file name.
     * @param outputDir the output dir.
     * @param templateDefs the template definitions.
     */
    public GlobalTemplateContextImpl(
        @NotNull final String fileName,
        @NotNull final File outputDir,
        @NotNull final List<TemplateDef<String>> templateDefs,
        @NotNull final QueryJCommand command)
    {
        super(command);
        immutableSetTemplateDefs(templateDefs, command);
        immutableSetFileName(fileName, command);
        immutableSetOutputDir(outputDir, command);
    }

    /**
     * Specifies the file name.
     * @param fileName the file name.
     * @param command the command.
     */
    protected final void immutableSetFileName(@NotNull final String fileName, @NotNull final QueryJCommand command)
    {
        new QueryJCommandWrapper<String>(command).setSetting("fileName", fileName);
    }

    /**
     * Specifies the output dir.
     * @param outputDir the output dir.
     * @param command the command.
     */
    protected final void immutableSetOutputDir(@NotNull final File outputDir, @NotNull final QueryJCommand command)
    {
        new QueryJCommandWrapper<File>(command).setSetting("outputDir", outputDir);
    }

    /**
     * Specifies the template defs.
     * @param templateDefs the list of {@link TemplateDef}s.
     */
    protected final void immutableSetTemplateDefs(
        @NotNull final List<TemplateDef<String>> templateDefs, @NotNull final QueryJCommand command)
    {
        new QueryJCommandWrapper<List<TemplateDef<String>>>(command).setSetting("templateDefs", templateDefs);
    }

    /**
     * Retrieves the list of {@link TemplateDef}s.
     * @return such list.
     */
    @NotNull
    @Override
    public List<TemplateDef<String>> getTemplateDefs()
    {
        return getTemplateDefs(getCommand());
    }

    /**
     * Retrieves the list of {@link TemplateDef}s.
     * @param command the command.
     * @return such list.
     */
    @NotNull
    protected List<TemplateDef<String>> getTemplateDefs(@NotNull final QueryJCommand command)
    {
        @Nullable final List<TemplateDef<String>> result =
            new QueryJCommandWrapper<TemplateDef<String>>(command).getListSetting("templateDefs");

        if (result == null)
        {
            throw new TemplateDefNotAvailableException();
        }

        return result;
    }
}
