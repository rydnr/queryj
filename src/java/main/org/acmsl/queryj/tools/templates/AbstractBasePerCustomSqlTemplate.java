//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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

 *****************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Base logic for all per-table templates.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.SqlElement;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;
import java.util.Iterator;

/**
 * Logicless container for all templates to be processed once per custom SQL.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractBasePerCustomSqlTemplate
    extends  AbstractTemplate
{
    /**
     * The sql.
     */
    private SqlElement m__Sql;

    /**
     * The custom SQL provider.
     */
    private CustomSqlProvider m__CustomSqlProvider;
    
    /**
     * The database metadata manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * The package name.
     */
    private String m__strPackageName;

    /**
     * The engine name.
     */
    private String m__strEngineName;

    /**
     * The engine's version.
     */
    private String m__strEngineVersion;

    /**
     * The base package name.
     */
    private String m__strBasePackageName;

    /**
     * The repository name.
     */
    private String m__strRepositoryName;

    /**
     * Builds a <code>AbstractBasePerCustomSqlTemplate</code> using
     * given information.
     * @param sql the sql.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     */
    public AbstractBasePerCustomSqlTemplate(
        final SqlElement sql,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String repositoryName)
    {
        immutableSetSql(sql);
        immutableSetCustomSqlProvider(customSqlProvider);
        immutableSetMetadataManager(metadataManager);
        immutableSetPackageName(packageName);
        immutableSetEngineName(engineName);
        immutableSetEngineVersion(engineVersion);
        immutableSetBasePackageName(basePackageName);
        immutableSetRepositoryName(repositoryName);
    }

    /**
     * Specifies the sql.
     * @param sql the custom sql.
     */
    protected final void immutableSetSql(final SqlElement sql)
    {
        m__Sql = sql;
    }
    
    /**
     * Specifies the sql.
     * @param sql the custom sql.
     */
    protected void setSql(final SqlElement sql)
    {
        immutableSetSql(sql);
    }
    
    /**
     * Retrieves the sql.
     * @return such information.
     */
    public SqlElement getSql()
    {
        return m__Sql;
    }

    /**
     * Specifies the custom sql provider.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     */
    protected final void immutableSetCustomSqlProvider(
        final CustomSqlProvider customSqlProvider)
    {
        m__CustomSqlProvider = customSqlProvider;
    }
    
    /**
     * Specifies the custom sql provider.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     */
    protected void setCustomSqlProvider(
        final CustomSqlProvider customSqlProvider)
    {
        immutableSetCustomSqlProvider(customSqlProvider);
    }

    /**
     * Retrieves the custom sql provider.
     * @return such instance.
     */
    public CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
    }
    
    /**
     * Specifies the metadata manager.
     * @param metadataManager the metadata manager.
     */
    private void immutableSetMetadataManager(
        final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager the metadata manager.
     */
    protected void setMetadataManager(
        final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such manager.
     */
    public MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    private void immutableSetPackageName(final String packageName)
    {
        m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the new package name.
     */
    protected void setPackageName(final String packageName)
    {
        immutableSetPackageName(packageName);
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    public String getPackageName() 
    {
        return m__strPackageName;
    }

    /**
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    private void immutableSetEngineName(final String engineName)
    {
        m__strEngineName = engineName;
    }

    /**
     * Specifies the engine name.
     * @param engineName the new engine name.
     */
    protected void setEngineName(final String engineName)
    {
        immutableSetEngineName(engineName);
    }

    /**
     * Retrieves the engine name.
     * @return such information.
     */
    public String getEngineName() 
    {
        return m__strEngineName;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    private void immutableSetEngineVersion(final String engineVersion)
    {
        m__strEngineVersion = engineVersion;
    }

    /**
     * Specifies the engine version.
     * @param engineVersion the new engine version.
     */
    protected void setEngineVersion(final String engineVersion)
    {
        immutableSetEngineVersion(engineVersion);
    }

    /**
     * Retrieves the engine version.
     * @return such information.
     */
    public String getEngineVersion()
    {
        return m__strEngineVersion;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    private void immutableSetBasePackageName(final String basePackageName)
    {
        m__strBasePackageName = basePackageName;
    }

    /**
     * Specifies the base package name.
     * @param basePackageName the new base package name.
     */
    protected void setBasePackageName(final String basePackageName)
    {
        immutableSetBasePackageName(basePackageName);
    }

    /**
     * Retrieves the base package name.
     * @return such information.
     */
    public String getBasePackageName() 
    {
        return m__strBasePackageName;
    }

    /**
     * Specifies the repository name.
     * @param repositoryName the new repository name.
     */
    private void immutableSetRepositoryName(final String repositoryName)
    {
        m__strRepositoryName = repositoryName;
    }

    /**
     * Specifies the repository name.
     * @param repositoryName the new repository name.
     */
    protected void setRepositoryName(final String repositoryName)
    {
        immutableSetRepositoryName(repositoryName);
    }

    /**
     * Retrieves the repository name.
     * @return such information.
     */
    public String getRepositoryName()
    {
        return m__strRepositoryName;
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return buildHeader(getTemplateName(), getSql());
    }

    /**
     * Builds the header for logging purposes.
     * @param templateName the template name.
     * @param sql the sql.
     * @return such header.
     * @precondition templateName != null
     * @precondition sql != null
     */
    protected String buildHeader(
        final String templateName, final SqlElement sql)
    {
        return
              "Generating " + templateName + " for " + sql.getName();
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    public abstract String getTemplateName();
}
