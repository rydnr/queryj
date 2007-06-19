//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: ProcedureRepositoryTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a procedure repository using database metadata.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.DatabaseMetaDataRetrievalHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.metadata.ProcedureMetadata;
import org.acmsl.queryj.tools.metadata.ProcedureParameterMetadata;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.templates.handlers.TemplateBuildHandler;
import org.acmsl.queryj.tools.templates.ProcedureRepositoryTemplate;
import org.acmsl.queryj.tools.templates.ProcedureRepositoryTemplateGenerator;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds a procedure repository using database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class ProcedureRepositoryTemplateBuildHandler
    extends    AbstractAntCommandHandler
    implements TemplateBuildHandler
{
    /**
     * The procedure repository template attribute name.
     */
    public static final String PROCEDURE_REPOSITORY_TEMPLATE =
        "procedure.repository.template";

    /**
     * Creates a ProcedureRepositoryTemplateBuildHandler.
     */
    public ProcedureRepositoryTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            Map attributes = command.getAttributeMap();

            MetadataManager t_MetadataManager =
                retrieveMetadataManager(attributes);

            ProcedureMetadata[] t_aProceduresMetadata = null;
            ProcedureRepositoryTemplate t_ProcedureRepositoryTemplate = null;
            
            if  (t_MetadataManager != null)
            {
                t_aProceduresMetadata = 
                    t_MetadataManager.getProceduresMetadata();

                t_ProcedureRepositoryTemplate =
                    buildProcedureRepositoryTemplate(
                        attributes,
                        t_MetadataManager.getMetadataTypeManager());
            }

            if  (t_ProcedureRepositoryTemplate != null)
            {
                int t_iCount =
                    (t_aProceduresMetadata != null)
                    ? t_aProceduresMetadata.length : 0;
                
                for  (int t_iIndex = 0;
                          t_iIndex < t_iCount;
                          t_iIndex++) 
                {
                    ProcedureParameterMetadata[] t_aProcedureParametersMetadata =
                        t_MetadataManager.getProcedureParametersMetadata(
                            t_aProceduresMetadata[t_iIndex]);

                    t_ProcedureRepositoryTemplate.addProcedureMetadata(
                        t_aProceduresMetadata[t_iIndex]);

                    t_ProcedureRepositoryTemplate
                        .addProcedureParametersMetadata(
                            t_aProceduresMetadata[t_iIndex],
                            t_aProcedureParametersMetadata);
                }

                storeProcedureRepositoryTemplate(
                    t_ProcedureRepositoryTemplate, attributes);
            }
        }
        
        return result;
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
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.PACKAGE);
        }
        
        return result;
    }

    /**
     * Builds a procedure repository template using the information stored
     * in the attribute map.
     * @param parameters the parameter map.
     * @param metadataTypeManager the metadata type manager.
     * @return the ProcedureRepositoryTemplate instance.
     * @throws BuildException if the repository cannot be created.
     */
    protected ProcedureRepositoryTemplate buildProcedureRepositoryTemplate(
        final Map parameters, final MetadataTypeManager metadataTypeManager)
      throws  BuildException
    {
        ProcedureRepositoryTemplate result = null;

        if  (parameters != null) 
        {
            result =
                buildProcedureRepositoryTemplate(
                    (String)
                        parameters.get(
                            ParameterValidationHandler.PACKAGE),
                    (String)
                        parameters.get(
                            ParameterValidationHandler.REPOSITORY),
                    metadataTypeManager,
                    retrieveHeader(parameters));

            /*
            if  (result != null) 
            {
                String[] t_astrProcedureNames =
                    (String[])
                        parameters.get(
                            PROCE.TABLE_NAMES);

                if  (t_astrTableNames != null)
                {
                    for  (int t_iTableIndex = 0;
                              t_iTableIndex < t_astrTableNames.length;
                              t_iTableIndex++)
                    {
                        result.addTable(t_astrTableNames[t_iTableIndex]);
                    }                    
                }
            }
            */
        }

        return result;
    }

    /**
     * Builds a procedure repository template using given information.
     * @param packageName the package name.
     * @param repository the repository.
     * @param metadataTypeManager the metadata type manager.
     * @param header the header.
     * @return such template.
     * @throws org.apache.tools.ant.BuildException whenever the repository
     * information is not valid.
     */
    protected ProcedureRepositoryTemplate buildProcedureRepositoryTemplate(
        final String packageName,
        final String repository,
        final MetadataTypeManager metadataTypeManager,
        final String header)
      throws  BuildException
    {
        ProcedureRepositoryTemplate result = null;

        if  (   (packageName != null)
             && (repository != null)
             && (metadataTypeManager != null))
        {
            ProcedureRepositoryTemplateGenerator t_ProcedureRepositoryTemplateGenerator =
                ProcedureRepositoryTemplateGenerator.getInstance();

            if  (t_ProcedureRepositoryTemplateGenerator != null)
            {
                result =
                    t_ProcedureRepositoryTemplateGenerator.createProcedureRepositoryTemplate(
                        packageName, repository, metadataTypeManager, header);
            }
        }

        return result;
    }

    /**
     * Stores the procedure repository template in given attribute map.
     * @param procedureRepositoryTemplate the table repository template.
     * @param parameters the parameter map.
     * @throws BuildException if the repository cannot be stored for any reason.
     */
    protected void storeProcedureRepositoryTemplate(
        final ProcedureRepositoryTemplate procedureRepositoryTemplate,
        final Map parameters)
      throws  BuildException
    {
        if  (   (procedureRepositoryTemplate != null)
             && (parameters != null))
        {
            parameters.put(
                PROCEDURE_REPOSITORY_TEMPLATE, procedureRepositoryTemplate);
        }
    }
}
