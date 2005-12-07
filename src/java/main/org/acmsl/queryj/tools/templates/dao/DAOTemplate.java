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
 * Description: Is able to create engine-specific DAO interfaces for each
 *              table in the persistence model.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataUtils;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;
import java.util.Map;

/**
 * Is able to create engine-specific DAO interfaces for each
 * table in the persistence model.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class DAOTemplate
    extends  BasePerTableTemplate
{
    /**
     * Builds a <code>DAOTemplate</code> using given information.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the CustomSqlProvider instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     */
    public DAOTemplate(
        final String tableName,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final String packageName,
        final String engineName,
        final String engineVersion,
        final String quote,
        final String basePackageName,
        final String repositoryName)
    {
        super(
            tableName,
            metadataManager,
            customSqlProvider,
            packageName,
            engineName,
            engineVersion,
            quote,
            basePackageName,
            repositoryName);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    protected StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/DAO.stg");
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    public String getTemplateName()
    {
        return "DAO";
    }

    /**
     * Fills the template parameters.
     * @param input the parameter container.
     * @param template the template.
     * @param copyrightYears the copyright years.
     * @param tableName the table name.
     * @param voName the name of the value object.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param basePackageName the base package name.
     * @param subpackageName the subpackage.
     * @param timestamp the timestamp.
     * @param className the class name of the DAO.
     * @param baseDAOClassName the class name of the DAO interface.
     * @param baseDAOPackageName the DAO interface package.
     * @param primaryKeyAttributes the primary key attributes.
     * @param nonPrimaryKeyAttributes the ones not part of the primary
     * key..
     * @param foreignKeyAttributes the foreign key attributes.
     * @param referingKeys the foreign keys of other tables pointing
     * to this one. It's expected to be
     * a map of "fk_"referringTableName -> foreign_keys (list of attribute
     * lists).
     * @param attributes the attributes.
     * @param externallyManagedAttributes the attributes which are
     * managed externally.
     * @param allButExternallyManagedAttributes all but the attributes which
     * are managed externally.
     * @param allButLobAttributes all but the attributes whose type is
     * Clob or Blob.
     * @param foreignKeys the entities pointing to this instance's table.
     * @param customSelects the custom selects.
     * @param customResults the custom results.
     * @param staticAttributeName the name of the static attribute, or
     * <code>null</code> for non-static tables.
     * @param staticAttributeType the type of the static attribute, or
     * <code>null</code> for non-static tables.
     * @param tableRepositoryName the table repository.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition input != null
     * @precondition template != null
     * @precondition copyrightYears != null
     * @precondition tableName != null
     * @precondition voName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition basePackageName != null
     * @precondition subpackageName != null
     * @precondition timestamp != null
     * @precondition className != null
     * @precondition baseDAOClassName != null
     * @precondition baseDAOPackageName != null
     * @precondition primaryKeyAttributes != null
     * @precondition nonPrimaryKeyAttributes != null
     * @precondition foreignKeyAttributes != null
     * @precondition attributes != null
     * @precondition externallyManagedAttributes != null
     * @precondition allButExternallyManagedAttributes != null
     * @precondition allButLobAttributes != null
     * @precondition foreignKeys != null
     * @precondition customSelects != null
     * @precondition customResults != null
     * @precondition tableRepositoryName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition metadataUtils != null
     * @precondition stringUtils != null
     */
    protected void fillParameters(
        final Map input,
        final StringTemplate template,
        final Integer[] copyrightYears,
        final String tableName,
        final String voName,
        final String engineName,
        final String engineVersion,
        final String basePackageName,
        final String subpackageName,
        final String timestamp,
        final String className,
        final String baseDAOClassName,
        final String baseDAOPackageName,
        final Collection primaryKeyAttributes,
        final Collection nonPrimaryKeyAttributes,
        final Collection foreignKeyAttributes,
        final Map referingKeys,
        final Collection attributes,
        final Collection externallyManagedAttributes,
        final Collection allButExternallyManagedAttributes,
        final Collection allButLobAttributes,
        final Collection foreignKeys,
        final Collection customSelects,
        final Collection customResults,
        final String staticAttributeName,
        final String staticAttributeType,
        final String tableRepositoryName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final MetadataUtils metadataUtils,
        final StringUtils stringUtils)
    {
        super.fillParameters(
            input,
            template,
            copyrightYears,
            tableName,
            voName,
            engineName,
            engineVersion,
            basePackageName,
            subpackageName,
            timestamp,
            className,
            baseDAOClassName,
            baseDAOPackageName,
            primaryKeyAttributes,
            nonPrimaryKeyAttributes,
            foreignKeyAttributes,
            referingKeys,
            attributes,
            externallyManagedAttributes,
            allButExternallyManagedAttributes,
            allButLobAttributes,
            foreignKeys,
            customSelects,
            customResults,
            staticAttributeName,
            staticAttributeType,
            tableRepositoryName,
            metadataManager,
            metadataTypeManager,
            metadataUtils,
            stringUtils);

        // Check for CLOB stuff.
        if  (   (metadataManager.requiresCustomClobHandling())
             && (containsClobs(
                     tableName, metadataManager, metadataTypeManager)))
        {
            input.put("clobHandling", metadataManager.getName());
        }
    }

    /**
     * Checks whether given table contains Clob attributes or not.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @return <code>true</code> in such case.
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    protected boolean containsClobs(
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager)
    {
        boolean result = false;

        String[] t_astrColumnNames = metadataManager.getColumnNames(tableName);

        int t_iLength =
            (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
        {
            if  (metadataTypeManager.isClob(
                     metadataManager.getColumnType(
                         tableName, 
                         t_astrColumnNames[t_iIndex])))
            {
                result = true;
                break;
            }
        }

        return result;
    }
}
            
