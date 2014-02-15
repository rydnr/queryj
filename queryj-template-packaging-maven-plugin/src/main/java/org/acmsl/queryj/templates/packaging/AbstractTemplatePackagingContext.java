/*
                        QueryJ-Template-Packaging-Plugin

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
 * Filename: AbstractTemplatePackagingContext.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base context class.
 *
 * Date: 2013/09/15
 * Time: 06:53
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.tools.maven.Literals;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.io.File;
import java.io.Serializable;

/**
 * Base context class.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/15 06:53
 */
@ThreadSafe
public class AbstractTemplatePackagingContext
    implements Serializable
{
    private static final long serialVersionUID = -7291939701431286380L;

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
     * The JDBC url.
     */
    @NotNull
    private String m__strJdbcUrl;

    /**
     * The JDBC username.
     */
    @NotNull
    private String m__strJdbcUsername;

    /**
     * The JDBC password.
     */
    @NotNull
    private String m__strJdbcPassword;

    /**
     * Creates a new instance.
     * @param templateName the template name.
     * @param packageName the package name.
     * @param rootDir the root dir.
     * @param fileName the file name.
     * @param outputDir the output dir.
     * @param jdbcUrl the JDBC url.
     * @param jdbcUsername the JDBC username.
     * @param jdbcPassword the JDBC password.
     */
    public AbstractTemplatePackagingContext(
        @NotNull final String templateName,
        @NotNull final String fileName,
        @NotNull final String packageName,
        @NotNull final File rootDir,
        @NotNull final File outputDir,
        @NotNull final String jdbcUrl,
        @NotNull final String jdbcUsername,
        @NotNull final String jdbcPassword)
    {
        immutableSetTemplateName(templateName);
        immutableSetFileName(fileName);
        immutableSetPackageName(packageName);
        immutableSetRootDir(rootDir);
        immutableSetOutputDir(outputDir);
        immutableSetJdbcUrl(jdbcUrl);
        immutableSetJdbcUsername(jdbcUsername);
        immutableSetJdbcPassword(jdbcPassword);
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
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
    @NotNull
    public File getOutputDir()
    {
        return this.m__OutputDir;
    }

    /**
     * Specifies the JDBC url.
     * @param jdbcUrl the JDBC url.
     */
    protected final void immutableSetJdbcUrl(@NotNull final String jdbcUrl)
    {
        this.m__strJdbcUrl = jdbcUrl;
    }

    /**
     * Specifies the JDBC url.
     * @param jdbcUrl the JDBC url.
     */
    @SuppressWarnings("unused")
    protected void setJdbcUrl(@NotNull final String jdbcUrl)
    {
        immutableSetJdbcUrl(jdbcUrl);
    }

    /**
     * Retrieves the JDBC url.
     * @return the JDBC url.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getJdbcUrl()
    {
        return this.m__strJdbcUrl;
    }

    /**
     * Specifies the JDBC user name.
     * @param jdbcUsername the JDBC user name.
     */
    protected final void immutableSetJdbcUsername(@NotNull final String jdbcUsername)
    {
        this.m__strJdbcUsername = jdbcUsername;
    }

    /**
     * Specifies the JDBC user name.
     * @param jdbcUsername the JDBC user name.
     */
    @SuppressWarnings("unused")
    protected void setJdbcUsername(@NotNull final String jdbcUsername)
    {
        immutableSetJdbcUsername(jdbcUsername);
    }

    /**
     * Retrieves the JDBC user name.
     * @return the JDBC user name.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getJdbcUsername()
    {
        return this.m__strJdbcUsername;
    }

    /**
     * Specifies the JDBC password.
     * @param jdbcPassword the JDBC password.
     */
    protected final void immutableSetJdbcPassword(@NotNull final String jdbcPassword)
    {
        this.m__strJdbcPassword = jdbcPassword;
    }

    /**
     * Specifies the JDBC url.
     * @param jdbcPassword the JDBC password.
     */
    @SuppressWarnings("unused")
    protected void setJdbcPassword(@NotNull final String jdbcPassword)
    {
        immutableSetJdbcPassword(jdbcPassword);
    }

    /**
     * Retrieves the JDBC url.
     * @return the JDBC url.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getJdbcPassword()
    {
        return this.m__strJdbcPassword;
    }

    @NotNull
    @Override
    public String toString()
    {
        return "{ 'class': 'DefaultTemplatePackagingContext' " +
               ", 'fileName': '" + m__strFileName + '\'' +
               ", 'templateName': '" + m__strTemplateName + '\'' +
               Literals.PACKAGE_NAME + m__strPackageName + '\'' +
               ", 'rootDir': '" + m__RootDir.getAbsolutePath() + '\'' +
               Literals.OUTPUT_DIR + m__OutputDir.getAbsolutePath() + '\'' +
                ", 'jdbcUrl': '" + m__strJdbcUrl + '\'' +
                ", 'jdbcUsername': '" + m__strJdbcUsername + '\'' +
                ", 'jdbcPassword': '" + m__strJdbcPassword + "' }";
    }
}
