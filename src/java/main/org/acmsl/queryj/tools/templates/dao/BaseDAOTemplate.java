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

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create DAO interfaces for each table in the
 *              persistence model.
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataUtils;
import org.acmsl.queryj.tools.metadata.RowDecorator;
import org.acmsl.queryj.tools.metadata.vo.Row;
import org.acmsl.queryj.tools.templates.InvalidTemplateException;
import org.acmsl.queryj.tools.templates.TableTemplate;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Is able to create DAO interfaces for each table in the
 * persistence model.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class BaseDAOTemplate
    extends  DAOTemplate
{
    /**
     * Builds a <code>BaseDAOTemplate</code> using given information.
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
    public BaseDAOTemplate(
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
        return retrieveGroup("/org/acmsl/queryj/dao/BaseDAO.stg");
    }
    
    /**
     * Fills the parameters required by <code>class</code> rule.
     * @param input the input.
     * @param voName the name of the value object.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param timestamp the timestamp.
     * @param customResults the custom results.
     * @param staticTable whether the table is static or not.
     * @param tableRepositoryName the table repository name.
     * @param tableName the table name.
     * @param pkAttributes the primary key attributes.
     * @param nonPkAttributes the ones not part of the primary key.
     * @param fkAttributes the foreign key attributes.
     * @param referingKeys the foreign keys of other tables pointing
     * to this one. It's expected to be
     * a map of "fk_"referringTableName -> foreign_keys (list of attribute
     * lists).
     * @param attributes the attributes.
     * @param externallyManagedAttributes the attributes which are
     * managed externally.
     * @param allButExternallyManagedAttributes all but the attributes which
     * are managed externally.
     * @param lobAttributes all but the attributes whose type is
     * Clob or Blob.
     * @param allButLobAttributes all but the attributes whose type is
     * Clob or Blob.
     * @param foreignKeys the entities pointing to this instance's table.
     * @param staticAttributeName the name of the static attribute, or
     * <code>null</code> for non-static tables.
     * @param staticAttributeType the type of the static attribute, or
     * <code>null</code> for non-static tables.
     * @param customSelects the custom selects.
     * @param customUpdatesOrInserts the custom updates and inserts.
     * @param customSelectsForUpdate the custom selects for update.
     * @param customResults the custom results.
     * @param metadataManager the database metadata manager.
     * @precondition input != null
     * @precondition voName != null
     * @precondition engineName != null
     * @precondition engineVersion != null
     * @precondition timestamp != null
     * @precondition tableRepositoryName != null
     * @precondition tableName != null
     * @precondition pkAttributes != null
     * @precondition nonPkAttributes != null
     * @precondition fkAttributes != null
     * @precondition attributes != null
     * @precondition externallyManagedAttributes != null
     * @precondition allButExternallyManagedAttributes != null
     * @precondition lobAttributes != null
     * @precondition allButLobAttributes != null
     * @precondition foreignKeys != null
     * @precondition customSelects != null
     * @precondition customUpdatesOrInserts != null
     * @precondition customSelectsForUpdate != null
     * @precondition customResults != null
     * @precondition metadataManager != null
     */
    protected void fillClassParameters(
        final Map input,
        final String voName,
        final String engineName,
        final String engineVersion,
        final String timestamp,
        final boolean staticTable,
        final String tableRepositoryName,
        final String tableName,
        final Collection pkAttributes,
        final Collection nonPkAttributes,
        final Collection fkAttributes,
        final Map referingKeys,
        final Collection attributes,
        final Collection externallyManagedAttributes,
        final Collection allButExternallyManagedAttributes,
        final Collection lobAttributes,
        final Collection allButLobAttributes,
        final Collection foreignKeys,
        final String staticAttributeName,
        final String staticAttributeType,
        final Collection customSelects,
        final Collection customUpdatesOrInserts,
        final Collection customSelectsForUpdate,
        final Collection customResults,
        final MetadataManager metadataManager)
    {
        super.fillClassParameters(
            input,
            voName,
            engineName,
            engineVersion,
            timestamp,
            staticTable,
            tableRepositoryName,
            tableName,
            pkAttributes,
            nonPkAttributes,
            fkAttributes,
            referingKeys,
            attributes,
            externallyManagedAttributes,
            allButExternallyManagedAttributes,
            lobAttributes,
            allButLobAttributes,
            foreignKeys,
            staticAttributeName,
            staticAttributeType,
            customSelects,
            customUpdatesOrInserts,
            customSelectsForUpdate,
            customResults,
            metadataManager);

        if  (staticTable) 
        {
            queryContents(
                input,
                tableName,
                staticAttributeName,
                attributes.size(),
                getMetadataManager());
        }
    }

    /**
     * Queries the contents of given table.
     * @param input the input.
     * @param tableName the table name.
     * @param staticAttributeName the name of the static attribute.
     * @param columnCount the number of columns.
     * @param metadataManager the metadata manager.
     * @throws InvalidTemplateException if the template is invalid.
     * @precondition tableName != null
     * @precondition staticAttributeName != null
     * @precondition columnCount > 0
     * @precondition metadataManager != null
     */
    protected void queryContents(
        final Map input,
        final String tableName,
        final String staticAttributeName,
        final int columnCount,
        final MetadataManager metadataManager)
      throws  InvalidTemplateException
    {
        queryContents(
            input,
            tableName,
            staticAttributeName,
            columnCount,
            metadataManager,
            metadataManager.getMetadataTypeManager(),
            metadataManager.getMetaData());
    }

    /**
     * Queries the contents of given table.
     * @param input the input.
     * @param tableName the table name.
     * @param staticAttributeName the name of the static attribute.
     * @param columnCount the number of columns.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param metaData the metadata.
     * @throws InvalidTemplateException if the template is invalid.
     * @precondition tableName != null
     * @precondition staticAttributeName != null
     * @precondition columnCount > 0
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition metaData != null
     */
    protected void queryContents(
        final Map input,
        final String tableName,
        final String staticAttributeName,
        final int columnCount,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DatabaseMetaData metaData)
      throws  InvalidTemplateException
    {
        try
        {
            queryContents(
                input,
                tableName,
                staticAttributeName,
                columnCount,
                metadataManager,
                metadataTypeManager,
                MetadataUtils.getInstance(),
                metaData.getConnection());
        }
        catch  (final SQLException sqlException)
        {
            throw
                new InvalidTemplateException(
                    "cannot.retrieve.connection",
                    new Object[0],
                    sqlException);
        }
    }

    /**
     * Queries the contents of given table.
     * @param input the input.
     * @param tableName the table name.
     * @param staticAttributeName the static attribute name.
     * @param columnCount the number of columns.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @param connection the connection.
     * @throws InvalidTemplateException if the template is invalid.
     * @precondition tableName != null
     * @precondition staticAttributeName != null
     * @precondition columnCount > 0
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition metadataUtils != null
     * @precondition connection != null
     */
    protected void queryContents(
        final Map input,
        final String tableName,
        final String staticAttributeName,
        final int columnCount,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final MetadataUtils metadataUtils,
        final Connection connection)
      throws  InvalidTemplateException
    {
        Log t_Log = UniqueLogFactory.getLog(getClass());
        
        Collection t_cRows = new ArrayList();

        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            t_PreparedStatement =
                connection.prepareStatement(
                    "select * from " + tableName);

            t_rsResults = t_PreparedStatement.executeQuery();

            String[] t_astrColumnNames = new String[columnCount];

            String[] t_astrColumnValues = new String[columnCount];

            String t_strRowName = null;

            if  (t_rsResults != null)
            {
                while  (t_rsResults.next())
                {
                    t_strRowName = null;

                    ResultSetMetaData t_rsMetaData =
                        t_rsResults.getMetaData();

                    int t_iArrayIndex = 0;

                    for  (int t_iIndex = 1;
                              t_iIndex <= columnCount;
                              t_iIndex++)
                    {
                        t_iArrayIndex = t_iIndex - 1;

                        t_astrColumnNames[t_iArrayIndex] =
                            t_rsMetaData.getColumnName(t_iIndex);

                        t_astrColumnValues[t_iArrayIndex] =
                            t_rsResults.getString(t_iIndex);

                        if  (staticAttributeName.equalsIgnoreCase(
                                 t_astrColumnNames[t_iArrayIndex]))
                        {
                            t_strRowName = t_astrColumnValues[t_iArrayIndex];
                        }
                    }

                    t_cRows.add(
                        buildRow(
                            t_strRowName,
                            tableName,
                            metadataUtils.buildAttributes(
                                t_astrColumnNames,
                                t_astrColumnValues,
                                tableName,
                                metadataManager,
                                metadataTypeManager),
                            metadataTypeManager));
                }
            }
        }
        catch  (final SQLException sqlException)
        {
            throw
                new InvalidTemplateException(
                    "cannot.retrieve.fixed.table.contents",
                    new Object[] { tableName },
                    sqlException);
        }
        finally 
        {
            try 
            {
                if  (t_rsResults != null)
                {
                    t_rsResults.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the result set.",
                        sqlException);
                }
            }

            try 
            {
                if  (t_PreparedStatement != null)
                {
                    t_PreparedStatement.close();
                }
            }
            catch  (final SQLException sqlException)
            {
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot close the statement.",
                        sqlException);
                }
            }
        }

        input.put("cached_rows", t_cRows);
    }

    /**
     * Builds a row with given information.
     * @param rowName the row name.
     * @param tableName the table name.
     * @param attributes the attributes.
     * @param metadataTypeManager the metadata type manager.
     * @return the row.
     * @precondition rowName != null
     * @precondition tableName != null
     * @precondition attributes != null
     * @precondition metadatTypeManager != null
     */
    protected Row buildRow(
        final String rowName,
        final String tableName,
        final Collection attributes,
        final MetadataTypeManager metadataTypeManager)
    {
        return
            new RowDecorator(
                rowName, tableName, attributes, metadataTypeManager);
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    public String getTemplateName()
    {
        return "BaseDAO";
    }
}
