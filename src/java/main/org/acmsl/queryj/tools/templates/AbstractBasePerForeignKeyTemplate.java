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
 * Filename: $RCSfile: $
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
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
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
 * Base logic for all per-table templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractBasePerForeignKeyTemplate
    extends  AbstractTemplate
{
    /**
     * The foreign key.
     */
    private ForeignKey m__ForeignKey;
    
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
     * The quote.
     */
    private String m__strQuote;

    /**
     * The base package name.
     */
    private String m__strBasePackageName;

    /**
     * The repository name.
     */
    private String m__strRepositoryName;

    /**
     * Builds a <code>AbstractBasePerForeignKeyTemplate</code> using
     * given information.
     * @param foreignKey the foreign key.
     * @param metadataManager the database metadata manager.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     */
    public AbstractBasePerForeignKeyTemplate(
        final ForeignKey foreignKey,
        final MetadataManager metadataManager,
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName)
    {
        super(header, decoratorFactory);
        immutableSetForeignKey(foreignKey);
        immutableSetMetadataManager(metadataManager);
        immutableSetPackageName(packageName);
        immutableSetEngineName(engineName);
        immutableSetEngineVersion(engineVersion);
        immutableSetQuote(quote);
        immutableSetBasePackageName(basePackageName);
        immutableSetRepositoryName(repositoryName);
    }

    /**
     * Specifies the foreign key.
     * @param the foreign key.
     */
    protected final void immutableSetForeignKey(final ForeignKey foreignKey)
    {
        m__ForeignKey = foreignKey;
    }
    
    /**
     * Specifies the foreign key.
     * @param foreign_key the foreign key.
     */
    protected void setForeignKey(final ForeignKey foreignKey)
    {
        immutableSetForeignKey(foreignKey);
    }
    
    /**
     * Retrieves the foreign key.
     * @return such information.
     */
    public ForeignKey getForeignKey()
    {
        return m__ForeignKey;
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
     * Specifies the identifier quote string.
     * @param quote such identifier.
     */
    private void immutableSetQuote(final String quote)
    {
        m__strQuote = quote;
    }

    /**
     * Specifies the identifier quote string.
     * @param quote such identifier.
     */
    protected void setQuote(final String quote)
    {
        immutableSetQuote(quote);
    }

    /**
     * Retrieves the identifier quote string.
     * @return such identifier.
     */
    public String getQuote()
    {
        return m__strQuote;
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
        return buildHeader(getTemplateName(), getForeignKey());
    }

    /**
     * Builds the header for logging purposes.
     * @param templateName the template name.
     * @param foreignKey the foreign key.
     * @return such header.
     * @precondition templateName != null
     * @precondition foreignKey != null
     */
    protected String buildHeader(
        final String templateName, final ForeignKey foreignKey)
    {
        return
              "Generating " + templateName + " for "
            + foreignKey.getSourceTableName()
            + "(" + concat(foreignKey.getAttributes(), ",") + ")"
            + "->"
            + foreignKey.getTargetTableName();
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    public abstract String getTemplateName();

    /**
     * Concatenates given list.
     * @param list the list.
     * @param separator the separator.
     * @precondition list != null
     */
    protected String concat(final Collection list, final String separator)
    {
        return concat(list, separator, StringUtils.getInstance());
    }

    /**
     * Concatenates given list.
     * @param list the list.
     * @param separator the separator.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition list != null
     * @precondition stringUtils != null
     */
    protected String concat(
        final Collection list,
        final String separator,
        final StringUtils stringUtils)
    {
        return stringUtils.concatenate(list, separator);
    }
}
