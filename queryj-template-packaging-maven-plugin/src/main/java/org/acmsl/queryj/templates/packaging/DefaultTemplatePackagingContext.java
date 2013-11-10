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
 * Filename: DefaultTemplatePackagingContext.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Default implementation for TemplatePackagingContext.
 *
 * Date: 2013/08/17
 * Time: 19:05
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.io.File;

/**
 * Default implementation for TemplatePackagingContext.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/17 19:05
 */
@ThreadSafe
public class DefaultTemplatePackagingContext
    extends AbstractTemplatePackagingContext
    implements PerTemplateDefTemplateContext
{
    /**
     * The template def.
     */
    @NotNull
    private TemplateDef<String> m__TemplateDef;

    /**
     * Creates a new instance.
     * @param templateDef the template def.
     * @param templateName the template name.
     * @param fileName the file name.
     * @param outputDir the output dir.
     */
    public DefaultTemplatePackagingContext(
        @NotNull final TemplateDef<String> templateDef,
        @NotNull final String templateName,
        @NotNull final String fileName,
        @NotNull final String packageName,
        @NotNull final File rootDir,
        @NotNull final File outputDir)
    {
        super(templateName, fileName, packageName, rootDir, outputDir);
        immutableSetTemplateDef(templateDef);
    }

    /**
     * Specifies the template def.
     * @param templateDef the template def.
     */
    protected final void immutableSetTemplateDef(@NotNull final TemplateDef<String> templateDef)
    {
        this.m__TemplateDef = templateDef;
    }

    /**
     * Specifies the template def.
     * @param templateDef the template def.
     */
    @SuppressWarnings("unused")
    protected void setTemplateDef(@NotNull final TemplateDef<String> templateDef)
    {
        immutableSetTemplateDef(templateDef);
    }

    /**
     * Retrieves the template def.
     * @return such instance.
     */
    @Override
    @NotNull
    public TemplateDef<String> getTemplateDef()
    {
        return this.m__TemplateDef;
    }

    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + DefaultTemplatePackagingContext.class.getName() + "\""
            + "', \"templateDef\": \"" + m__TemplateDef + "\" }";
    }
}
