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

 ******************************************************************************
 *
 * Filename: $RCSfile: $
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
import org.acmsl.queryj.tools.metadata.CachingRowDecorator;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataUtils;
import org.acmsl.queryj.tools.metadata.RowDecorator;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
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
import java.util.Iterator;
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
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the identifier quote string.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param header the header.
     */
    public BaseDAOTemplate(
        final String tableName,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final String header,
        final DecoratorFactory decoratorFactory,
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
            header,
            decoratorFactory,
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
                attributes,
                getMetadataManager(),
                getDecoratorFactory());
        }
    }

    /**
     * Queries the contents of given table.
     * @param input the input.
     * @param tableName the table name.
     * @param staticAttributeName the name of the static attribute.
     * @param attributes the attributes.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @throws InvalidTemplateException if the template is invalid.
     * @precondition tableName != null
     * @precondition staticAttributeName != null
     * @precondition attributes != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected void queryContents(
        final Map input,
        final String tableName,
        final String staticAttributeName,
        final Collection attributes,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
      throws  InvalidTemplateException
    {
        queryContents(
            input,
            tableName,
            staticAttributeName,
            attributes,
            metadataManager,
            metadataManager.getMetadataTypeManager(),
            decoratorFactory,
            metadataManager.getMetaData());
    }

    /**
     * Queries the contents of given table.
     * @param input the input.
     * @param tableName the table name.
     * @param staticAttributeName the name of the static attribute.
     * @param attributes the attributes.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metaData the metadata.
     * @throws InvalidTemplateException if the template is invalid.
     * @precondition tableName != null
     * @precondition staticAttributeName != null
     * @precondition attributes != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metaData != null
     */
    protected void queryContents(
        final Map input,
        final String tableName,
        final String staticAttributeName,
        final Collection attributes,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final DatabaseMetaData metaData)
      throws  InvalidTemplateException
    {
        try
        {
            queryContents(
                input,
                tableName,
                staticAttributeName,
                attributes,
                metadataManager,
                metadataTypeManager,
                decoratorFactory,
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
     * @param attributes the attributes.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @param connection the connection.
     * @throws InvalidTemplateException if the template is invalid.
     * @precondition tableName != null
     * @precondition staticAttributeName != null
     * @precondition attributes != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     * @precondition decoratorFactory != null
     * @precondition metadataUtils != null
     * @precondition connection != null
     */
    protected void queryContents(
        final Map input,
        final String tableName,
        final String staticAttributeName,
        final Collection attributes,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final DecoratorFactory decoratorFactory,
        final MetadataUtils metadataUtils,
        final Connection connection)
      throws  InvalidTemplateException
    {
        Log t_Log = UniqueLogFactory.getLog(BaseDAOTemplate.class);
        
        Collection t_cRows = new ArrayList();

        int t_iColumnCount = (attributes != null) ? attributes.size() : 0;

        ResultSet t_rsResults = null;

        PreparedStatement t_PreparedStatement = null;

        try
        {
            t_PreparedStatement =
                connection.prepareStatement(
                    "select * from " + tableName);

            t_rsResults = t_PreparedStatement.executeQuery();

            String[] t_astrColumnNames = new String[t_iColumnCount];

            String[] t_astrColumnValues = new String[t_iColumnCount];

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
                              t_iIndex <= t_iColumnCount;
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

                    reorderAttributes(
                        attributes, t_astrColumnNames, t_astrColumnValues);

                    t_cRows.add(
                        buildRow(
                            t_strRowName,
                            tableName,
                            metadataUtils.buildAttributes(
                                t_astrColumnNames,
                                t_astrColumnValues,
                                tableName,
                                metadataManager,
                                metadataTypeManager,
                                decoratorFactory),
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
            new CachingRowDecorator(
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

    /**
     * Reorders the attributes in the same order as the original ones.
     * @param attributes the original attributes.
     * @param names the retrieved attribute names.
     * @param values the retrieved attribute values.
     * @precondition attributes != null
     * @precondition names != null
     * @precondition values != null
     * @precondition attributes.size() == names.length
     * @precondition names.length == values.length
     */
    protected void reorderAttributes(
        final Collection attributes,
        final String[] names,
        final String[] values)
    {
        Iterator t_itAttributeIterator =
            (attributes != null) ? attributes.iterator() : null;

        if  (t_itAttributeIterator != null)
        {
            Object t_Item;
            Attribute t_CurrentAttribute;
            int t_iIndex = 0;
            int t_iPosition;
            int t_iCount = (attributes != null) ? attributes.size() : 0;

            String[] t_astrAuxNames = new String[t_iCount];
            String[] t_astrAuxValues = new String[t_iCount];

            while  (t_itAttributeIterator.hasNext())
            {
                t_Item = t_itAttributeIterator.next();

                if  (t_Item instanceof Attribute)
                {
                    t_CurrentAttribute = (Attribute) t_Item;

                    t_iPosition =
                        retrieveAttributeIndex(
                            t_CurrentAttribute.getName(), names);

                    if  (   (t_iPosition >= 0)
                         && (t_iPosition < t_iCount))
                    {
                        t_astrAuxNames[t_iIndex] = names[t_iPosition];
                        t_astrAuxValues[t_iIndex] = values[t_iPosition];
                    }

                    t_iIndex++;
                }
            }

            copyArray(t_astrAuxNames, names);
            copyArray(t_astrAuxValues, values);
        }
    }

    /**
     * Retrieves the index of the attribute in given collection.
     * @param name the attribute name.
     * @param attributes the attributes.
     * @return such index.
     * @precondition name != null
     * @precondition attributes != null
     */
    protected int retrieveAttributeIndex(
        final String name, final String[] attributes)
    {
        int result = -1;

        int t_iCount = (attributes != null) ? attributes.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            if  (name.equals(attributes[t_iIndex]))
            {
                result = t_iIndex;
                break;
            }
        }

        return result;
    }

    /**
     * Copies given array.
     * @param source the source.
     * @param target the target.
     * @precondition source != null
     * @precondition target != null
     * @precondition source.length <= target.lenght
     */
    protected void copyArray(final String[] source, final String[] target)
    {
        int t_iCount = (source != null) ? source.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            target[t_iIndex] = source[t_iIndex];
        }
    }
}
