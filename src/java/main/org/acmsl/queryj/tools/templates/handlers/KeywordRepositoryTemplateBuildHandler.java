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
 * Description: Builds a keyword repository from declared keyword definition.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.AntExternallyManagedFieldsElement;
import org.acmsl.queryj.tools.AntFieldElement;
import org.acmsl.queryj.tools.DatabaseMetaDataManager;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.MetaDataUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.KeywordRepositoryTemplate;
import org.acmsl.queryj.tools.templates.KeywordRepositoryTemplateFactory;
import org.acmsl.queryj.tools.templates.KeywordRepositoryTemplateGenerator;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Builds a keyword repository from declared keyword definition.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class KeywordRepositoryTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The keyword repository template attribute name.
     */
    public static final String KEYWORD_REPOSITORY_TEMPLATE =
        "keyword.repository.template";

    /**
     * Creates a KeywordRepositoryTemplateBuildHandler.
     */
    public KeywordRepositoryTemplateBuildHandler() {};

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
                command.getTask(),
                MetaDataUtils.getInstance(),
                StringValidator.getInstance());
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition project != null
     * @precondition task != null
     * @precondition metaDataUtils != null
     * @precondition stringValidator != null
     */
    protected boolean handle(
        final Map parameters,
        final Project project,
        final Task task,
        final MetaDataUtils metaDataUtils,
        final StringValidator stringValidator)
        throws  BuildException
    {
        boolean result = false;

        KeywordRepositoryTemplate t_KeywordRepositoryTemplate =
            buildKeywordRepositoryTemplate(
                parameters, project, task);

        if  (t_KeywordRepositoryTemplate == null)
        {
            throw new BuildException("Cannot build keyword repository");
        }
        else 
        {
            storeKeywordRepositoryTemplate(
                t_KeywordRepositoryTemplate, parameters);

            Collection t_cFieldElements = null;

            DatabaseMetaDataManager t_MetaDataManager =
                retrieveDatabaseMetaDataManager(parameters);

            AntExternallyManagedFieldsElement
                t_ExternallyManagedFieldsElement =
                retrieveExternallyManagedFieldsElement(parameters);

            if  (t_MetaDataManager == null)
            {
                throw new BuildException(
                      "Cannot continue: "
                    + "database metadata manager not available");
            }
            else if  (t_ExternallyManagedFieldsElement != null)
            {
                t_cFieldElements =
                    t_ExternallyManagedFieldsElement.getFields();

                if  (   (t_cFieldElements != null)
                     && (t_cFieldElements.size() > 0))
                {
                    Iterator t_itFieldIterator =
                        t_cFieldElements.iterator();

                    while  (   (t_itFieldIterator != null)
                            && (t_itFieldIterator.hasNext()))
                    {
                        AntFieldElement t_Field =
                            (AntFieldElement) t_itFieldIterator.next();

                        if  (t_Field != null)
                        {
                            if  (!stringValidator.isEmpty(t_Field.getKeyword()))
                            {
                                t_KeywordRepositoryTemplate.addKeyword(
                                    t_Field.getKeyword(),
                                    metaDataUtils.getQueryJFieldType(
                                        t_MetaDataManager.getColumnType(
                                            t_Field.getTableName(),
                                            t_Field.getName()),
                                        project,
                                        task));
                            }
                        }
                    }
                }
            }
        }
        
        return result;
    }

    /**
     * Retrieves the database metadata manager from the attribute map.
     * @param parameters the parameter map.
     * @return the manager instance.
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
     * Retrieves the externally-managed-fields XML element stored in the
     * attribute map.
     * @param parameters the parameter map.
     * @return the externally-managed-fields information.
     * @throws BuildException if the retrieval process cannot be performed.
     * @precondition parameters != null
     */
    protected AntExternallyManagedFieldsElement
        retrieveExternallyManagedFieldsElement(final Map parameters)
      throws  BuildException
    {
        return
            (AntExternallyManagedFieldsElement)
                parameters.get(
                    ParameterValidationHandler.EXTERNALLY_MANAGED_FIELDS);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrieveProjectPackage(final Map parameters)
        throws  BuildException
    {
        return(String) parameters.get(ParameterValidationHandler.PACKAGE);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition parameters != null
     */
    protected String retrievePackage(final Map parameters)
        throws  BuildException
    {
        return retrievePackage(parameters, PackageUtils.getInstance());
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
            packageUtils.retrieveKeywordRepositoryPackage(
                retrieveProjectPackage(parameters));
    }

    /**
     * Retrieves the repository name from the attribute map.
     * @param parameters the parameter map.
     * @return the repository name.
     * @throws BuildException if the repository retrieval process is faulty.
     * @precondition parameters != null
     */
    protected String retrieveRepository(final Map parameters)
        throws  BuildException
    {
        return (String) parameters.get(ParameterValidationHandler.REPOSITORY);
    }

    /**
     * Builds a procedure repository template using the information stored
     * in the attribute map.
     * @param parameters the parameter map.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the ProcedureRepositoryTemplate instance.
     * @throws BuildException if the repository cannot be created.
     * @precondition parameters != null
     */
    protected KeywordRepositoryTemplate buildKeywordRepositoryTemplate(
        final Map parameters, final Project project, final Task task)
      throws  BuildException
    {
        return
            buildKeywordRepositoryTemplate(
                retrievePackage(parameters),
                retrieveRepository(parameters),
                project,
                task,
                KeywordRepositoryTemplateGenerator.getInstance());
    }

    /**
     * Builds a procedure repository template using given information.
     * @param packageName the package name.
     * @param repository the repository.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @param templateFactory the template factory.
     * @return such template.
     * @throws org.apache.tools.ant.BuildException whenever the repository
     * information is not valid.
     * @precondition packageName != null
     * @precondition repository != null
     * @precondition templateFactory != null
     */
    protected KeywordRepositoryTemplate buildKeywordRepositoryTemplate(
        final String packageName,
        final String repository,
        final Project project,
        final Task task,
        final KeywordRepositoryTemplateFactory templateFactory)
      throws  BuildException
    {
        return
            templateFactory.createKeywordRepositoryTemplate(
                packageName, repository, project, task);
    }

    /**
     * Stores the keyword repository template in given attribute map.
     * @param keywordRepositoryTemplate the table repository template.
     * @param parameters the parameter map.
     * @throws BuildException if the repository cannot be stored for any reason.
     * @precondition keywordRepositoryTemplate != null
     * @precondition parameters != null
     */
    protected void storeKeywordRepositoryTemplate(
        final KeywordRepositoryTemplate keywordRepositoryTemplate,
        final Map parameters)
      throws  BuildException
    {
        parameters.put(KEYWORD_REPOSITORY_TEMPLATE, keywordRepositoryTemplate);
    }
}
