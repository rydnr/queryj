/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
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
 * Description: Builds a value object factory template using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.templates.handlers.TableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.valueobject.ValueObjectFactoryTemplate;
import org.acmsl.queryj.tools.templates.valueobject.ValueObjectFactoryTemplateFactory;
import org.acmsl.queryj.tools.templates.valueobject
    .ValueObjectFactoryTemplateGenerator;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * Builds a value object template using database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 */
public class ValueObjectFactoryTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * A cached empty table template array.
     */
    public static final TableTemplate[] EMPTY_TABLE_TEMPLATE_ARRAY =
        new TableTemplate[0];

    /**
     * Creates a ValueObjectFactoryTemplateBuildHandler.
     */
    public ValueObjectFactoryTemplateBuildHandler() {};

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
        return
            handle(
                command.getAttributeMap(),
                command.getProject(),
                command.getTask());
    }

    /**
     * Handles given command.
     * @param attributes the attributes.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition command != null
     */
    protected boolean handle(
        final Map attributes, final Project project, final Task task)
        throws  BuildException
    {
        return
            handle(
                attributes,
                retrieveDatabaseMetaData(attributes),
                retrieveDatabaseMetaDataManager(attributes),
                retrievePackage(attributes),
                retrieveTableTemplates(attributes),
                ValueObjectFactoryTemplateGenerator.getInstance(),
                project,
                task);
    }

    /**
     * Handles given command.
     * @param attributes the attributes.
     * @param databaseMetaData the database metadata.
     * @param metaDataManager the database metadata manager.
     * @param packageName the package name.
     * @param tableTemplates the table templates.
     * @param factory the template factory.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition attributes != null
     * @precondition databaseMetaData != null
     * @precondition metaDataManager != null
     * @precondition packageName != null
     * @precondition tableTemplates != null
     * @precondition factory != null
     */
    protected boolean handle(
        final Map attributes,
        final DatabaseMetaData metaData,
        final DatabaseMetaDataManager metaDataManager,
        final String packageName,
        final TableTemplate[] tableTemplates,
        final ValueObjectFactoryTemplateFactory factory,
        final Project project,
        final Task task)
        throws  BuildException
    {
        boolean result = false;

        try
        {
            ValueObjectFactoryTemplate[] t_aValueObjectFactoryTemplates =
                new ValueObjectFactoryTemplate[tableTemplates.length];

            for  (int t_iValueObjectFactoryIndex = 0;
                  t_iValueObjectFactoryIndex < t_aValueObjectFactoryTemplates.length;
                  t_iValueObjectFactoryIndex++) 
            {
                String t_strQuote = metaData.getIdentifierQuoteString();

                if  (t_strQuote == null)
                {
                    t_strQuote = "\"";
                }

                if  (t_strQuote.equals("\""))
                {
                    t_strQuote = "\\\"";
                }

                t_aValueObjectFactoryTemplates[t_iValueObjectFactoryIndex] =
                    factory.createValueObjectFactoryTemplate(
                        packageName,
                        tableTemplates[t_iValueObjectFactoryIndex],
                        metaDataManager,
                        project,
                        task);
            }

            storeValueObjectFactoryTemplates(t_aValueObjectFactoryTemplates, attributes);
        }
        catch  (final SQLException sqlException)
        {
            throw new BuildException(sqlException);
        }
        catch  (final QueryJException queryjException)
        {
            throw new BuildException(queryjException);
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata from the attribute map.
     * @param parameters the parameter map.
     * @return the metadata.
     * @throws BuildException if the metadata retrieval process if faulty.
     * @precondition parameters != null
     */
    protected DatabaseMetaData retrieveDatabaseMetaData(
        final Map parameters)
      throws  BuildException
    {
        return
            (DatabaseMetaData)
                parameters.get(
                    DatabaseMetaDataRetrievalHandler.DATABASE_METADATA);
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager.
     * @throws BuildException if the manager retrieval process if faulty.
     * @precondition parameters != null
     */
    protected DatabaseMetaDataManager retrieveDatabaseMetaDataManager(
        final Map parameters)
      throws  BuildException
    {
        return
            (DatabaseMetaDataManager)
                parameters.get(
                    DatabaseMetaDataRetrievalHandler.DATABASE_METADATA_MANAGER);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrievePackage(final Map parameters)
        throws  BuildException
    {
        return
            retrievePackage(parameters, PackageUtils.getInstance());
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final Map parameters, final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveValueObjectFactoryPackage(
                (String) parameters.get(ParameterValidationHandler.PACKAGE));
    }

    /**
     * Stores the value object factory template collection in given attribute map.
     * @param valueObjectFactoryTemplates the value object factory templates.
     * @param parameters the parameter map.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition valueObjectFactoryTemplates != null
     * @precondition parameters != null
     */
    protected void storeValueObjectFactoryTemplates(
        final ValueObjectFactoryTemplate[] valueObjectFactoryTemplates,
        final Map parameters)
      throws  BuildException
    {
        parameters.put(
            TemplateMappingManager.VALUE_OBJECT_FACTORY_TEMPLATES,
            valueObjectFactoryTemplates);
    }

    /**
     * Retrieves the table templates.
     * @param parameters the parameter map.
     * @return such templates.
     * @throws BuildException if the templates cannot be stored for any reason.
     * @precondition parameters != null
     */
    protected TableTemplate[] retrieveTableTemplates(
        final Map parameters)
      throws  BuildException
    {
        return
            (TableTemplate[])
                parameters.get(TableTemplateBuildHandler.TABLE_TEMPLATES);
    }
}
