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
 * Filename: DAOChooserTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate the configuration file for configuring
 *              DAOChooser.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.CachingTableDecorator;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.TableDecorator;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerRepositoryTemplate;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Is able to generate the configuration file for configuring
 * DAOChooser.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DAOChooserTemplate
    extends  BasePerRepositoryTemplate
{
    private static final long serialVersionUID = 7102196328163453291L;

    /**
     * Builds a <code>DAOChooserTemplate</code> using given
     * information.
     * @param metadataManager the database metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param header the header.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param packageName the package name.
     * @param basePackageName the base package name.
     * @param repositoryName the repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     */
    public DAOChooserTemplate(
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager,
        final CustomSqlProvider customSqlProvider,
        final String header,
        final DecoratorFactory decoratorFactory,
        final String packageName,
        final String basePackageName,
        final String repositoryName,
        final String engineName,
        final Collection tables)
    {
        super(
            metadataManager,
            metadataTypeManager,
            customSqlProvider,
            header,
            decoratorFactory,
            packageName,
            basePackageName,
            repositoryName,
            engineName,
            tables);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @NotNull
    @Override
    public StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/DAOChooser.stg");
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    public String getTemplateName()
    {
        return "DAOChooser";
    }

    /**
     * Fills the core parameters.
     * @param input the input.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param basePackageName the base package name.
     * @param subpackageName the subpackage name.
     * @param tableRepositoryName the table repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     * @param timestamp the timestamp.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @precondition input != null
     * @precondition metadataManager != null
     * @precondition customSqlProvider != null
     * @precondition decoratorFactory != null
     * @precondition subpackageName != null
     * @precondition basePackageName != null
     * @precondition tableRepositoryName != null
     * @precondition tables != null
     * @precondition timestamp != null
     * @precondition stringUtils != null
     */
    protected void fillCoreParameters(
        @NotNull final Map input,
        final MetadataManager metadataManager,
        final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        final String basePackageName,
        final String subpackageName,
        @NotNull final String tableRepositoryName,
        @NotNull final String engineName,
        @NotNull final Collection tables,
        final String timestamp,
        final StringUtils stringUtils)
    {
        super.fillCoreParameters(
            input,
            metadataManager,
            customSqlProvider,
            decoratorFactory,
            basePackageName,
            subpackageName,
            tableRepositoryName,
            engineName,
            tables,
            timestamp,
            stringUtils);

        @NotNull DecorationUtils decorationUtils = DecorationUtils.getInstance();

        input.put(
            "tr_name_uppercased",
            normalizeUppercase(tableRepositoryName, decorationUtils));
        input.put(
            "tr_name_lowercased",
            lowercase(tableRepositoryName, decorationUtils));
        input.put(
            "tr_name_normalized",
            normalize(tableRepositoryName, decorationUtils));
        input.put(
            "tr_name_capitalized",
            capitalize(tableRepositoryName, decorationUtils));
        input.put(
            "properties_file_name",
            retrievePropertiesFileName(tableRepositoryName));
    }

    /**
     * Retrieves the properties file for given repository.
     * @param repository the repository.
     * @return the properties file.
     * @precondition repository != null
     */
    @NotNull
    protected String retrievePropertiesFileName(@NotNull final String repository)
    {
        return retrievePropertiesFileName(repository, DAOChooserTemplateUtils.getInstance());
    }

    /**
     * Retrieves the properties file for given repository.
     * @param repository the repository.
     * @param daoChooserTemplateUtils the <code>DAOChooserTemplateUtils</code> instance.
     * @return the properties file.
     * @precondition repository != null
     * @precondition daoChooserTemplateUtils != null
     */
    @NotNull
    protected String retrievePropertiesFileName(
        @NotNull final String repository, @NotNull final DAOChooserTemplateUtils daoChooserTemplateUtils)
    {
        return daoChooserTemplateUtils.retrievePropertiesFileName(repository);
    }
}
