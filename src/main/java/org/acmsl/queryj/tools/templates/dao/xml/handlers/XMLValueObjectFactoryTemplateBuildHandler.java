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
 * Filename: XMLValueObjectFactoryTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a XML value object factory template using database
 *              metadata.
 *
 */
package org.acmsl.queryj.tools.templates.dao.xml.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.dao.xml.XMLValueObjectFactoryTemplate;
import org.acmsl.queryj.tools.templates.dao.xml.XMLValueObjectFactoryTemplateGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds a XML value object template using database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class XMLValueObjectFactoryTemplateBuildHandler
    extends    AbstractQueryJCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty table template array.
     */
    public static final TableTemplate[] EMPTY_TABLE_TEMPLATE_ARRAY =
        new TableTemplate[0];

    /**
     * Creates a <code>XMLValueObjectFactoryTemplateBuildHandler</code>
     * instance.
     */
    public XMLValueObjectFactoryTemplateBuildHandler() {};

    /**
     * Handles given parameters.
     *
     *
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        buildTemplates(
            retrieveMetadataManager(parameters),
            retrievePackage(parameters),
            retrieveValueObjectPackage(parameters),
            retrieveHeader(parameters),
            retrieveTableTemplates(parameters),
            XMLValueObjectFactoryTemplateGenerator.getInstance(),
            parameters);

        return false;
    }

    /**
     * Builds the <code>XMLValueObjectFactory</code> templates.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param packageName the name of the package.
     * @param valueObjectPackage the package for the value objects.
     * @param header the header.
     * @param tableTemplates the table templates.
     * @param generator the <code>XMLValueObjectFactoryTemplateGenerator</code>
     * instance.
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition metadataManager != null
     * @precondition packageName != null
     * @precondition valueObjectPackage != null
     * @precondition header != null
     * @precondition tableTemplates != null
     * @precondition generator != null
     * @precondition parameters != null
     */
    protected void buildTemplates(
        final MetadataManager metadataManager,
        final String packageName,
        final String valueObjectPackage,
        final String header,
        @Nullable final TableTemplate[] tableTemplates,
        @NotNull final XMLValueObjectFactoryTemplateGenerator generator,
        @NotNull final Map parameters)
      throws  QueryJBuildException
    {
        int t_iCount =
            (tableTemplates != null) ? tableTemplates.length : 0;

        @NotNull XMLValueObjectFactoryTemplate[] t_aValueObjectFactoryTemplates =
            new XMLValueObjectFactoryTemplate[t_iCount];

        for  (int t_iValueObjectFactoryIndex = 0;
                  t_iValueObjectFactoryIndex < t_iCount;
                  t_iValueObjectFactoryIndex++) 
        {
            t_aValueObjectFactoryTemplates[t_iValueObjectFactoryIndex] =
                generator.createXMLValueObjectFactoryTemplate(
                    packageName,
                    valueObjectPackage,
                    tableTemplates[t_iValueObjectFactoryIndex],
                    metadataManager,
                    header);
        }

        storeXMLValueObjectFactoryTemplates(
            t_aValueObjectFactoryTemplates, parameters);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     */
    protected String retrievePackage(@NotNull final Map parameters)
    {
        return
            retrievePackage(
                retrieveProjectPackage(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition packageName != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String projectPackage, @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveXMLValueObjectFactoryPackage(projectPackage);
    }

    /**
     * Retrieves the value object package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @precondition parameters != null
     */
    protected String retrieveValueObjectPackage(@NotNull final Map parameters)
    {
        return
            retrieveValueObjectPackage(
                retrieveProjectPackage(parameters),
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the value object package name from the attribute map.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrieveValueObjectPackage(
        final String projectPackage, @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveValueObjectFactoryPackage(projectPackage);
    }

    /**
     * Stores the value object factory template collection in given attribute
     * map.
     * @param valueObjectFactoryTemplates the value object factory templates.
     * @param parameters the parameter map.
     * @precondition valueObjectFactoryTemplates != null
     * @precondition parameters != null
     */
    protected void storeXMLValueObjectFactoryTemplates(
        final XMLValueObjectFactoryTemplate[] valueObjectFactoryTemplates,
        @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.XML_VALUE_OBJECT_FACTORY_TEMPLATES,
            valueObjectFactoryTemplates);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     */
    @NotNull
    protected TableTemplate[] retrieveTableTemplates(
        @Nullable final Map parameters)
    {
        @NotNull TableTemplate[] result = EMPTY_TABLE_TEMPLATE_ARRAY;

        if  (parameters != null)
        {
            result =
                (TableTemplate[])
                    parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
        }

        return result;
    }
}
