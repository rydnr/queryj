//;-*- mode: jde -*-
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
 * Filename: BasePerTableTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes per-table templates.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateGenerator;
import org.acmsl.queryj.tools.templates.handlers.BasePerTableTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.TemplateWritingHandler;
import org.acmsl.queryj.tools.templates.MetaLanguageUtils;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * Writes <i>per-table</i> templates.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public abstract class BasePerTableTemplateWritingHandler
    extends    AbstractAntCommandHandler
    implements TemplateWritingHandler
{
    /**
     * The relative weight per item.
     */
    public static final double RELATIVE_SIZE_WEIGHT =
        BasePerTableTemplateBuildHandler.RELATIVE_SIZE_WEIGHT * 2.0d;

    /**
     * Creates a <code>BasePerTableTemplateWritingHandler</code> instance.
     */
    public BasePerTableTemplateWritingHandler() {};

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
        handle(command.getAttributeMap());

        return false;
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected void handle(final Map parameters)
      throws  BuildException
    {
        handle(parameters, retrieveDatabaseProductName(parameters));
    }

    /**
     * Handles given information.
     * @param parameters the parameters.
     * @param engineName the engine name.
     * @throws BuildException if the build process cannot be performed.
     * @precondition parameters != null
     * @precondition engineName != null
     */
    protected void handle(final Map parameters, final String engineName)
      throws  BuildException
    {
        handle(
            retrieveTemplates(parameters),
            engineName,
            parameters,
            retrieveTemplateGenerator());
    }
            
    /**
     * Handles given information.
     * @param templates the templates.
     * @param engineName the engine name.
     * @param parameters the parameters.
     * @param templateGenerator the template generator.
     * @throws BuildException if the build process cannot be performed.
     * @precondition templates != null
     * @precondition parameters != null
     * @precondition templateGenerator != null
     */
    protected void handle(
        final BasePerTableTemplate[] templates,
        final String engineName,
        final Map parameters,
        final BasePerTableTemplateGenerator templateGenerator)
      throws  BuildException
    {
        try 
        {
            BasePerTableTemplate t_Template;

            Log t_Log =
                UniqueLogFactory.getLog(
                    BasePerTableTemplateWritingHandler.class);
        
            for  (int t_iIndex = 0; t_iIndex < templates.length; t_iIndex++)
            {
                t_Template = templates[t_iIndex];

                if  (shouldWrite(t_Template))
                {
                    templateGenerator.write(
                        t_Template,
                        retrieveOutputDir(
                            t_Template.getTableName(),
                            engineName,
                            parameters));
                }
                else
                {
                    if  (t_Log != null)
                    {
                        t_Log.trace(
                              "(Skipping template " + t_Template.getClass().getName()
                            + " for " + t_Template.getTableName() + ")");
                    }
                }
            }
        }
        catch  (final IOException ioException)
        {
            throw new BuildException(ioException);
        }
    }

    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    protected abstract BasePerTableTemplateGenerator retrieveTemplateGenerator();

    /**
     * Retrieves the templates from the attribute map.
     * @param parameters the parameter map.
     * @return the template.
     * @throws BuildException if the template retrieval process if faulty.
     */
    protected abstract BasePerTableTemplate[] retrieveTemplates(
        final Map parameters)
      throws  BuildException;

    /**
     * Retrieves the output dir from the attribute map.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     * @precondition parameters != null
     */
    protected File retrieveOutputDir(
        final String tableName, final String engineName, final Map parameters)
      throws  BuildException
    {
        return
            retrieveOutputDir(
                retrieveProjectOutputDir(parameters),
                retrieveProjectPackage(parameters),
                retrieveUseSubfoldersFlag(parameters),
                tableName,
                engineName,
                parameters,
                PackageUtils.getInstance());
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param projectFolder the project folder.
     * @param projectPackage the project base package.
     * @param useSubfolders whether to use subfolders for tests, or
     * using a different package naming scheme.
     * @param tableName the table name.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     * @precondition engineName != null
     * @precondition parameters != null
     * @precondition packageUtils != null
     */
    protected abstract File retrieveOutputDir(
        final File projectFolder,
        final String projectPackage,
        final boolean useSubfolders,
        final String tableName,
        final String engineName,
        final Map parameters,
        final PackageUtils packageUtils)
      throws  BuildException;

    /**
     * Checks whether given template should be generated.
     * @param template such template.
     * @return <tt>true</tt> in such case.
     */
    protected boolean shouldWrite(final BasePerTableTemplate template)
    {
        boolean result = (template != null);

        if  (result)
        {
            result =
                shouldWrite(
                    template,
                    template.getMetadataManager(),
                    MetaLanguageUtils.getInstance());
        }

        return result;
    }

    /**
     * Checks whether given template should be generated.
     * @param template such template.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metaLanguageUtils the <code>MetaLanguageUtils</code> instance.
     * @return <tt>true</tt> in such case.
     * @precondition metadataManager != null
     * @precondition metaLanguageUtils != null
     */
    protected boolean shouldWrite(
        final BasePerTableTemplate template,
        final MetadataManager metadataManager,
        final MetaLanguageUtils metaLanguageUtils)
    {
        boolean result = (template != null);

        String t_strComment =
            metadataManager.getTableComment(template.getTableName());

        String[][] t_aastrRelationship =
            metaLanguageUtils.retrieveTableRelationship(t_strComment);

        result =
            (   (t_aastrRelationship == null)
             || (t_aastrRelationship.length == 0));

        return result;
    }

    /**
     * Retrieves the relative weight of this handler.
     * @param parameters the parameters.
     * @return a value between <code>MIN_WEIGHT</code>
     * and <code>MAX_WEIGHT</code>.
     */
    public double getRelativeWeight(final Map parameters)
    {
        return
            BasePerTableTemplateBuildHandler.getRelativeWeight(
                BasePerTableTemplateBuildHandler.retrieveTableTemplates(
                    parameters),
                RELATIVE_SIZE_WEIGHT);
    }

}
