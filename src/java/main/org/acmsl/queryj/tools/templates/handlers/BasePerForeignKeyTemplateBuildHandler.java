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
 * Description: Builds all templates to generate sources for each
 *              foreign key.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.AttributeDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.metadata.vo.ForeignKeyValueObject;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerForeignKeyTemplate;
import org.acmsl.queryj.tools.templates.BasePerForeignKeyTemplateFactory;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Builds all templates to generate sources for each foreign key.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class BasePerForeignKeyTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty ForeignKey array.
     */
    public static final ForeignKey[] EMPTY_FOREIGNKEY_ARRAY =
        new ForeignKey[0];

    /**
     * The key for storing the foreign keys in the parameter map.
     */
    public static final String FOREIGN_KEYS = "..FOreignKeY:::";
    
    /**
     * Creates a <code>BasePerForeignKeyTemplateBuildHandler</code> instance.
     */
    public BasePerForeignKeyTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition command != null
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        return handle(command.getAttributeMap());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(final Map parameters)
        throws  BuildException
    {
        return handle(parameters, retrieveDatabaseMetaData(parameters));
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param metaData the database metadata.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition metaData != null
     */
    protected boolean handle(
        final Map parameters, final DatabaseMetaData metaData)
      throws  BuildException
    {
        boolean result = false;

        try
        {
            result =
                handle(
                    parameters,
                    metaData.getDatabaseProductName(),
                    metaData.getDatabaseProductVersion(),
                    fixQuote(metaData.getIdentifierQuoteString()));
        }
        catch  (final SQLException sqlException)
        {
            throw new BuildException(sqlException);
        }

        return result;
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition quote != null
     */
    protected boolean handle(
        final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote)
      throws  BuildException
    {
        return
            handle(
                parameters,
                engineName,
                engineVersion,
                quote,
                retrieveMetadataManager(parameters));
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     */
    protected boolean handle(
        final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager)
      throws  BuildException
    {
        return
            handle(
                parameters,
                engineName,
                engineVersion,
                quote,
                metadataManager,
                retrieveCustomSqlProvider(parameters),
                retrieveTemplateFactory(),
                retrieveProjectPackage(parameters),
                retrieveTableRepositoryName(parameters),
                retrieveForeignKeys(parameters, metadataManager));
    }
    
    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    protected abstract BasePerForeignKeyTemplateFactory retrieveTemplateFactory();

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @param engineVersion the engine version.
     * @param quote the quote character.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the custom sql provider.
     * @param templateFactory the template factory.
     * @param projectPackage the project package.
     * @param repository the repository.
     * @param foreignKeys the foreign keys.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition templateFactory != null
     * @precondition projectPackage != null
     * @precondition repository != null
     * @precondition foreignKeys != null
     */
    protected boolean handle(
        final Map parameters,
        final String engineName,
        final String engineVersion,
        final String quote,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        final BasePerForeignKeyTemplateFactory templateFactory,
        final String projectPackage,
        final String repository,
        final ForeignKey[] foreignKeys)
      throws  BuildException
    {
        boolean result = false;

        int t_iLength = (foreignKeys != null) ? foreignKeys.length : 0;
        
        BasePerForeignKeyTemplate[] t_aTemplates =
            new BasePerForeignKeyTemplate[t_iLength];

        try
        {
            ForeignKey t_ForeignKey = null;
            
            for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++) 
            {
                t_ForeignKey = foreignKeys[t_iIndex];
                
                t_aTemplates[t_iIndex] =
                    templateFactory.createTemplate(
                        t_ForeignKey,
                        metadataManager,
                        retrievePackage(
                            t_ForeignKey.getSourceTableName(),
                            engineName,
                            parameters),
                        engineName,
                        engineVersion,
                        quote,
                        projectPackage,
                        repository);
            }

            storeTemplates(t_aTemplates, parameters);
        }
        catch  (final QueryJException queryjException)
        {
            throw new BuildException(queryjException);
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrievePackage(
        final String tableName, final String engineName, final Map parameters)
      throws  BuildException
    {
        return
            retrievePackage(
                tableName,
                engineName,
                retrieveProjectPackage(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected abstract String retrievePackage(
        final String tableName,
        final String engineName,
        final String projectPackage,
        final PackageUtils packageUtils);

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected abstract void storeTemplates(
        final BasePerForeignKeyTemplate[] templates, final Map parameters);

    /**
     * Retrieves the foreign keys.
     * @param parameters the parameter map.
     * @param metadataManager the database metadata manager.
     * @return such templates.
     * @throws BuildException if the templates cannot be retrieved for any
     * reason.
     * @precondition parameters != null
     * @precondition metadataManager != null
     */
    protected ForeignKey[] retrieveForeignKeys(
        final Map parameters, final MetadataManager metadataManager)
        throws  BuildException
    {
        ForeignKey[] result = (ForeignKey[]) parameters.get(FOREIGN_KEYS);

        if  (result == null)
        {
            Collection t_cForeignKeys = new ArrayList();
            
            ForeignKey[] t_aFks = retrieveForeignKeys(metadataManager);

            int t_iLength = (t_aFks != null) ? t_aFks.length : 0;
            
            for  (int t_iIndex = 0; t_iIndex < t_iLength; t_iIndex++)
            {
                t_cForeignKeys.add(t_aFks[t_iIndex]);
            }

            result =
                (ForeignKey[]) t_cForeignKeys.toArray(EMPTY_FOREIGNKEY_ARRAY);
        }

        return result;
    }

    /**
     * Retrieves the foreign keys.
     * @param metadataManager the database metadata manager.
     * @return such foreign keys.
     * @precondition metadataManager != null
     */
    protected ForeignKey[] retrieveForeignKeys(
        final MetadataManager metadataManager)
    {
        ForeignKey[] result = EMPTY_FOREIGNKEY_ARRAY;

        Collection t_cResult = null;

        String[] t_astrTableNames = metadataManager.getTableNames();

        int t_iTableLength =
            (t_astrTableNames != null) ? t_astrTableNames.length : 0;

        String t_strSourceTable = null;
        String[] t_astrReferredTables = null;
        int t_iReferredTableLength = 0;
        String[][] t_aastrForeignKeys = null;
        int t_iForeignKeyLength = 0;
        String t_strReferredTable = null;
        
        for  (int t_iTableIndex = 0;
                  t_iTableIndex < t_iTableLength;
                  t_iTableIndex++)
        {
            t_strSourceTable = t_astrTableNames[t_iTableIndex];

            t_astrReferredTables =
                metadataManager.getReferingTables(t_strSourceTable);

            t_iReferredTableLength =
                (t_astrReferredTables != null)
                ?  t_astrReferredTables.length
                :  0;

            t_strReferredTable = null;
            t_aastrForeignKeys = null;
            
            for  (int t_iReferredTableIndex = 0;
                      t_iReferredTableIndex < t_iReferredTableLength;
                      t_iReferredTableIndex++)
            {
                t_strReferredTable =
                    t_astrReferredTables[t_iReferredTableIndex];

                t_aastrForeignKeys =
                    metadataManager.getReferredKeys(
                        t_strSourceTable, t_strReferredTable);

                if  (t_cResult == null)
                {
                    t_cResult = new ArrayList();
                }

                t_iForeignKeyLength =
                    (t_aastrForeignKeys != null)
                    ?  t_aastrForeignKeys.length
                    :  0;
                
                for  (int t_iForeignKeyIndex = 0;
                          t_iForeignKeyIndex < t_iForeignKeyLength;
                          t_iForeignKeyIndex++)
                {
                    t_cResult.add(
                        buildForeignKey(
                            t_aastrForeignKeys[t_iForeignKeyIndex],
                            t_strSourceTable,
                            t_strReferredTable,
                            metadataManager));
                }
            }
        }

        if  (t_cResult != null)
        {
            result = (ForeignKey[]) t_cResult.toArray(EMPTY_FOREIGNKEY_ARRAY);
        }

        return result;
    }

    /**
     * Builds a foreign key.
     * @param attributes the attributes.
     * @param sourceTable the source table.
     * @param targetTable the target table.
     * @param metadataManager the database metadata manager.
     * @return the foreign key.
     * @precondition attributes != null
     * @precondition sourceTable != null
     * @precondition targetTable != null
     * @precondition metadataManager != null
     */
    protected ForeignKey buildForeignKey(
        final String[] attributes,
        final String sourceTable,
        final String targetTable,
        final MetadataManager metadataManager)
    {
        ForeignKey result = null;

        Collection t_cAttributes = new ArrayList();

        int t_iAttributeLength = (attributes != null) ? attributes.length : 0;

        MetadataTypeManager t_TypeManager =
            metadataManager.getMetadataTypeManager();

        Attribute t_Attribute = null;
        boolean t_bAllowsNull = false;

        for  (int t_iIndex = 0; t_iIndex < t_iAttributeLength; t_iIndex++)
        {
            t_Attribute =
                buildAttribute(
                    attributes[t_iIndex],
                    sourceTable,
                    metadataManager,
                    t_TypeManager);

            if  (t_Attribute != null)
            {
                t_cAttributes.add(t_Attribute);

                if  (   (!t_bAllowsNull)
                     && (t_Attribute.getAllowsNull()))
                {
                    t_bAllowsNull = true;
                }
            }
        }

        result =
            new ForeignKeyValueObject(
                sourceTable, t_cAttributes, targetTable, t_bAllowsNull);

        return result;
    }

    /**
     * Builds an attribute.
     * @param attributeName the name.
     * @param tableName the source table.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @return the attribute.
     * @precondition attributeName != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    protected Attribute buildAttribute(
        final String attributeName,
        final String tableName,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager)
    {
        Attribute result = null;

        int t_iType = metadataManager.getColumnType(tableName, attributeName);

        String t_strNativeType = metadataTypeManager.getNativeType(t_iType);

        boolean t_bAllowsNull =
            metadataManager.allowsNull(tableName, attributeName);
        
        String t_strFieldType =
            metadataTypeManager.getFieldType(t_iType, t_bAllowsNull);

        boolean t_bManagedExternally =
            metadataManager.isManagedExternally(tableName, attributeName);
        
        result =
            new AttributeDecorator(
                attributeName,
                t_iType,
                t_strNativeType,
                t_strFieldType,
                tableName,
                t_bManagedExternally,
                t_bAllowsNull,
                null, // value
                metadataManager,
                metadataTypeManager);
        
        return result;
    }
}
