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

/**
 * Default implementation for TemplatePackagingContext.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/17 19:05
 */
@ThreadSafe
public class DefaultTemplatePackagingContext
    implements TemplatePackagingContext
{
    /**
     * The template name.
     */
    @NotNull
    private String m__strTemplateName;

    /**
     * The file name.
     */
    @NotNull
    private String m__strFileName;

    /**
     * The package name.
     */
    @NotNull
    private String m__strPackageName;

    public DefaultTemplatePackagingContext(
        @NotNull final String templateName,
        @NotNull final String fileName,
        @NotNull final String packageName)
    {
        immutableSetTemplateName(templateName);
        immutableSetFileName(fileName);
        immutableSetPackageName(packageName);
    }

    /**
     * Specifies the name of the template.
     * @param templateName such name.
     */
    protected final void immutableSetTemplateName(@NotNull final String templateName)
    {
        this.m__strTemplateName = templateName;
    }

    /**
     * Specifies the name of the template.
     * @param templateName such name.
     */
    @SuppressWarnings("unused")
    protected void setTemplateName(@NotNull final String templateName)
    {
        immutableSetTemplateName(templateName);
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @Override
    @NotNull
    public String getTemplateName()
    {
        return this.m__strTemplateName;
    }

    /**
     * Specifies the file name.
     * @param fileName such file name.
     */
    protected final void immutableSetFileName(@NotNull final String fileName)
    {
        this.m__strFileName = fileName;
    }

    /**
     * Specifies the file name.
     * @param fileName such file name.
     */
    @SuppressWarnings("unused")
    protected void setFileName(@NotNull final String fileName)
    {
        immutableSetFileName(fileName);
    }

    /**
     * Retrieves the file name.
     * @return such information.
     */
    @Override
    @NotNull
    public String getFileName()
    {
        return this.m__strFileName;
    }

    /**
     * Specifies the package name.
     * @param packageName such value.
     */
    protected final void immutableSetPackageName(@NotNull final String packageName)
    {
        this.m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName such value.
     */
    @SuppressWarnings("unused")
    protected void setPackageName(@NotNull final String packageName)
    {
        immutableSetPackageName(packageName);
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    @Override
    @NotNull
    public String getPackageName()
    {
        return this.m__strPackageName;
    }

    @NotNull
    @Override
    public String toString()
    {
        return "{ 'class': 'DefaultTemplatePackagingContext' " +
               ", 'fileName': '" + m__strFileName + '\'' +
               ", 'templateName': '" + m__strTemplateName + '\'' +
               ", 'packageName': '" + m__strPackageName + "' }";
    }
}
