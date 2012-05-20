//;-*- mode: java -*-
/*
                        QueryJ

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
 * Filename: FkStatementSetterTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a FkStatementSetter template.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataUtils;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerForeignKeyTemplateFactory;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateContext;
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplate;
import org.acmsl.queryj.tools.templates.dao.FkStatementSetterTemplateGenerator;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds a FkStatementSetter template.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class FkStatementSetterTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty String array.
     */
    protected final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * Creates a <code>FkStatementSetterTemplateBuildHandler</code> instance.
     */
    public FkStatementSetterTemplateBuildHandler() {}

    /**
     * Handles given information.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        return
            handle(
                parameters,
                FkStatementSetterTemplateGenerator.getInstance());
    }
    
    /**
     * Handles given information.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     */
    protected boolean handle(
        @NotNull final Map parameters,
        @NotNull final BasePerForeignKeyTemplateFactory<FkStatementSetterTemplate> templateFactory)
      throws  QueryJBuildException
    {
        buildTemplates(
            parameters,
            retrieveMetadataManager(parameters),
            retrieveCustomSqlProvider(parameters),
            retrieveProjectPackage(parameters),
            retrieveTableRepositoryName(parameters),
            retrieveHeader(parameters),
            retrieveImplementMarkerInterfaces(parameters),
            retrieveJmx(parameters),
            templateFactory,
            templateFactory.getDecoratorFactory(),
            retrieveTableTemplates(parameters),
            MetadataUtils.getInstance());

        return false;
    }

    /**
     * Builds the <code>FkStatementSetter</code> templates.
     * @param parameters the parameters.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param basePackageName the base package name.
     * @param repositoryName the repository.
     * @param header the header.
     * @param implementMarkerInterfaces whether to implement marker interfaces.
     * @param jmx whether to include JMX support.
     * @param templateFactory the template factory.
     * @param tableTemplates the table templates.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected void buildTemplates(
        @NotNull final Map parameters,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final String basePackageName,
        @NotNull final String repositoryName,
        @NotNull final String header,
        final boolean implementMarkerInterfaces,
        final boolean jmx,
        @NotNull final BasePerForeignKeyTemplateFactory<FkStatementSetterTemplate> templateFactory,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final TableTemplate[] tableTemplates,
        @NotNull final MetadataUtils metadataUtils)
      throws  QueryJBuildException
    {
        @NotNull Collection<FkStatementSetterTemplate> t_cTemplates = new ArrayList<FkStatementSetterTemplate>();

        String t_strTableName;
        String t_strPackageName;
        ForeignKey[] t_aForeignKeys;
        BasePerTableTemplateContext t_Context;

        for  (TableTemplate t_Template : tableTemplates)
        {
            if (t_Template != null)
            {
                t_Context = t_Template.getTemplateContext();

                t_strTableName = t_Context.getTableName();
                
                t_strPackageName =
                    retrievePackage(metadataManager.getEngineName(), t_strTableName, parameters);

                t_aForeignKeys =
                    metadataUtils.retrieveForeignKeys(
                        t_strTableName, metadataManager, decoratorFactory);
                
                for  (ForeignKey t_ForeignKey: t_aForeignKeys)
                {
                    t_cTemplates.add(
                        templateFactory.createTemplate(
                            metadataManager,
                            customSqlProvider,
                            t_strPackageName,
                            basePackageName,
                            repositoryName,
                            header,
                            implementMarkerInterfaces,
                            jmx,
                            t_ForeignKey));
                }
            }
        }

        storeTemplates(
            t_cTemplates.toArray(
                new FkStatementSetterTemplate[t_cTemplates.size()]),
            parameters);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param engineName the engine name.
     * @param tableName the table name.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     * @precondition engineName != null
     * @precondition tableName != null
     */
    protected String retrievePackage(
        @NotNull final String engineName,
        @NotNull final String tableName,
        @NotNull final Map parameters)
    {
        return
            retrievePackage(
                retrieveProjectPackage(parameters),
                engineName,
                tableName,
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param projectPackage the project package.
     * @param engineName the engine name.
     * @param tableName the table name.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition engineName != null
     * @precondition projectPackage != null
     * @precondition tableName != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String projectPackage,
        @NotNull final String engineName,
        @NotNull final String tableName,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveFkStatementSetterPackage(
                projectPackage,
                engineName,
                tableName);
    }

    /**
     * Retrieves the repository name.
     * @param parameters the parameters.
     * @return the repository's name.
     * @precondition parameters != null
     */
    @NotNull
    protected String retrieveTableRepositoryName(@NotNull final Map parameters)
    {
        return
            (String)
                parameters.get(
                    ParameterValidationHandler.REPOSITORY);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @precondition parameters != null
     */
    @NotNull
    protected TableTemplate[] retrieveTableTemplates(@NotNull final Map parameters)
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }

    /**
     * Stores the templates in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     */
    @SuppressWarnings("unchecked")
    protected void storeTemplates(
        @NotNull final FkStatementSetterTemplate[] templates,
        @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.FK_STATEMENT_SETTER_TEMPLATES,
            templates);
    }

    /**
     * Checks whether the table associated to given template contains foreign keys.
     * @param tableTemplate the table template.
     * @param metadataManager the database metadata manager.
     * @return <code>true</code> in such case.
     */
    @SuppressWarnings("unused")
    protected boolean containsForeignKeys(
        @NotNull final TableTemplate tableTemplate,
        @NotNull final MetadataManager metadataManager)
    {
        return containsForeignKeys(tableTemplate.getTemplateContext(), metadataManager);
    }

    /**
     * Checks whether the table associated to given template contains foreign keys.
     * @param context the {@link BasePerTableTemplateContext} instance.
     * @param metadataManager the database metadata manager.
     * @return <code>true</code> in such case.
     */
    protected boolean containsForeignKeys(
        @NotNull final BasePerTableTemplateContext context,
        @NotNull final MetadataManager metadataManager)
    {
        return
            metadataManager.containsForeignKeys(context.getTableName());
    }

    /**
     * Retrieves the simple foreign keys.
     * @param allFks the complete list of foreign keys.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     */
    @SuppressWarnings("unused,unchecked")
    @NotNull
    protected String[] retrieveSimpleFks(
        @NotNull final String[] allFks,
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager)
    {
        @NotNull Collection<String> t_cResult;

        @NotNull Map<String, String> t_mAux = new HashMap<String, String>();

        for  (String t_strFk: allFks)
        {
            // TODO: FIXME!!
            String t_strReferredTable =
                metadataManager.getReferredTable(
                    tableName, new String[] { t_strFk });

            if  (t_mAux.containsKey(t_strReferredTable))
            {
                t_mAux.remove(t_strReferredTable);
            }
            else
            {
                t_mAux.put(t_strReferredTable, t_strFk);
            }
        }

        t_cResult = t_mAux.values();

        return t_cResult.toArray(new String[t_cResult.size()]);
    }

    /**
     * Retrieves the tables referred by compound foreign keys.
     * @param tableName the table name.
     * @param metadataManager the database metadata manager.
     * @precondition tableName != null
     * @precondition metadataManager != null
     */
    @SuppressWarnings("unused,unchecked")
    @NotNull
    protected String[] retrieveReferredTablesByCompoundFks(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager)
    {
        @NotNull Collection t_cResult = new ArrayList();

        @NotNull String[] t_astrReferredTables =
            metadataManager.getReferredTables(tableName);

        for  (String t_strReferredTable: t_astrReferredTables)
        {
            String[][] t_astrForeignKeys =
                metadataManager.getForeignKeys(
                    tableName, t_strReferredTable);

            if  (   (t_astrForeignKeys != null)
                 && (t_astrForeignKeys.length > 0))
            {
                t_cResult.add(t_strReferredTable);
            }
        }

        return (String[]) t_cResult.toArray(EMPTY_STRING_ARRAY);
    }
}
