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
    implements TemplatePackagingContext
{
    /**
     * The template def.
     */
    @NotNull
    private TemplateDef m__TemplateDef;

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

    /**
     * The root dir.
     */
    @NotNull
    private File m__RootDir;

    /**
     * The output dir.
     */
    @NotNull
    private File m__OutputDir;

    /**
     * Creates a new instance.
     * @param templateDef the template def.
     * @param templateName the template name.
     * @param fileName the file name.
     * @param outputDir the output dir.
     */
    public DefaultTemplatePackagingContext(
        @NotNull final TemplateDef templateDef,
        @NotNull final String templateName,
        @NotNull final String fileName,
        @NotNull final String packageName,
        @NotNull final File rootDir,
        @NotNull final File outputDir)
    {
        immutableSetTemplateDef(templateDef);
        immutableSetTemplateName(templateName);
        immutableSetFileName(fileName);
        immutableSetPackageName(packageName);
        immutableSetRootDir(rootDir);
        immutableSetOutputDir(outputDir);
    }

    /**
     * Specifies the template def.
     * @param templateDef the template def.
     */
    protected final void immutableSetTemplateDef(@NotNull final TemplateDef templateDef)
    {
        this.m__TemplateDef = templateDef;
    }

    /**
     * Specifies the template def.
     * @param templateDef the template def.
     */
    @SuppressWarnings("unused")
    protected void setTemplateDef(@NotNull final TemplateDef templateDef)
    {
        immutableSetTemplateDef(templateDef);
    }

    /**
     * Retrieves the template def.
     * @return such instance.
     */
    @Override
    @NotNull
    public TemplateDef getTemplateDef()
    {
        return this.m__TemplateDef;
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
     * @param packageName such information.
     */
    protected void immutableSetPackageName(@NotNull final String packageName)
    {
        this.m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName such information.
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
        return m__strPackageName;
    }

    /**
     * Specifies the root dir.
     * @param dir such dir.
     */
    protected final void immutableSetRootDir(@NotNull final File dir)
    {
        this.m__RootDir = dir;
    }

    /**
     * Specifies the root dir.
     * @param dir such dir.
     */
    @SuppressWarnings("unused")
    protected void setRootDir(@NotNull final File dir)
    {
        immutableSetRootDir(dir);
    }

    /**
     * Retrieves the root dir.
     * @return such folder.
     */
    @Override
    @NotNull
    public File getRootDir()
    {
        return this.m__RootDir;
    }

    /**
     * Specifies the output dir.
     * @param dir such dir.
     */
    protected final void immutableSetOutputDir(@NotNull final File dir)
    {
        this.m__OutputDir = dir;
    }

    /**
     * Specifies the output dir.
     * @param dir such dir.
     */
    @SuppressWarnings("unused")
    protected void setOutputDir(@NotNull final File dir)
    {
        immutableSetOutputDir(dir);
    }

    /**
     * Retrieves the output dir.
     * @return such folder.
     */
    @Override
    @NotNull
    public File getOutputDir()
    {
        return this.m__OutputDir;
    }

    @NotNull
    @Override
    public String toString()
    {
        return "{ 'class': 'DefaultTemplatePackagingContext' " +
               ", 'fileName': '" + m__strFileName + '\'' +
               ", 'templateName': '" + m__strTemplateName + '\'' +
               ", 'packageName': '" + m__strPackageName + '\'' +
               ", 'templateDef': " + m__TemplateDef +
               ", 'rootDir': '" + m__RootDir.getAbsolutePath() + '\'' +
               ", 'outputDir': '" + m__OutputDir.getAbsolutePath() + "' }";
    }
}
