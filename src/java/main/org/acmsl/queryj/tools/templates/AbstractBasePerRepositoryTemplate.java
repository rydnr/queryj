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
 * Description: Top-level base logic for all per-repository templates.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Top-level Base logic for all per-repository templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractBasePerRepositoryTemplate
    extends  AbstractTemplate
{
    /**
     * The <code>MetadataManager</code> instance.
     */
    private MetadataManager m__MetadataManager;
    
    /**
     * The subpackage name.
     */
    private String m__strSubpackageName;
    
    /**
     * The base package name.
     */
    private String m__strBasePackageName;
    
    /**
     * The repository name.
     */
    private String m__strRepositoryName;
    
    /**
     * The engine name.
     */
    private String m__strEngineName;
    
    /**
     * The tables.
     */
    private Collection m__cTables;
    
    /**
     * Builds an <code>AbstractBasePerRepositoryTemplate</code> using given
     * information.
     * @param metadataManager the database metadata manager.
     * @param subpackageName the subpackage name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     */
    public AbstractBasePerRepositoryTemplate(
        final MetadataManager metadataManager,
        final String subpackageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName,
        final Collection tables)
    {
        immutableSetMetadataManager(metadataManager);
        immutableSetSubpackageName(subpackageName);
        immutableSetBasePackageName(basePackageName);
        immutableSetRepositoryName(repositoryName);
        immutableSetEngineName(engineName);
        immutableSetTables(tables);
    }

    /**
     * Specifies the <code>MetadataManager</code> instance.
     * @param metadataManager such instance.
     */
    protected final void immutableSetMetadataManager(
        final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the <code>MetadataManager</code> instance.
     * @param metadataManager such instance.
     */
    protected void setMetadataManager(final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the <code>MetadataManager</code> instance.
     * @return such instance.
     */
    public MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Specifies the subpackage name.
     * @param subpackageName such information.
     */
    protected final void immutableSetSubpackageName(
        final String subpackageName)
    {
        m__strSubpackageName = subpackageName;
    }
    
    /**
     * Specifies the subpackage name.
     * @param subpackageName such information.
     */
    protected void setSubpackageName(final String subpackageName)
    {
        immutableSetSubpackageName(subpackageName);
    }
    
    /**
     * Retrieves the subpackage name.
     * @return such information.
     */
    public String getSubpackageName()
    {
        return m__strSubpackageName;
    }
    
    /**
     * Specifies the base package name.
     * @param basePackageName such information.
     */
    protected final void immutableSetBasePackageName(
        final String basePackageName)
    {
        m__strBasePackageName = basePackageName;
    }
    
    /**
     * Specifies the base package name.
     * @param basePackageName such information.
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
     * @param repositoryName such information.
     */
    protected final void immutableSetRepositoryName(
        final String repositoryName)
    {
        m__strRepositoryName = repositoryName;
    }
    
    /**
     * Specifies the repository name.
     * @param repositoryName such information.
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
     * Specifies the engine name.
     * @param engineName such information.
     */
    protected final void immutableSetEngineName(
        final String engineName)
    {
        m__strEngineName = engineName;
    }
    
    /**
     * Specifies the engine name.
     * @param engineName such information.
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
     * Specifies the tables.
     * @param tables such tables.
     */
    protected final void immutableSetTables(final Collection tables)
    {
        m__cTables = tables;
    }
    
    /**
     * Specifies the tables.
     * @param tables such tables.
     */
    protected void setTables(final Collection tables)
    {
        immutableSetTables(tables);
    }
    
    /**
     * Specifies the tables.
     * @param tables such tables.
     */
    public Collection getTables()
    {
        return m__cTables;
    }
}
