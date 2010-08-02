//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Description: Writes ValueObject templates.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.customsql.CustomResultUtils;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplate;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplateGenerator;
import org.acmsl.queryj.tools.templates.valueobject.CustomValueObjectTemplate;
import org.acmsl.queryj.tools.templates.valueobject.CustomValueObjectTemplateGenerator;
import org.acmsl.queryj.tools.templates.valueobject.handlers.CustomValueObjectTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.BasePerCustomResultTemplateWritingHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Writes ValueObject templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CustomValueObjectTemplateWritingHandler
    extends  BasePerCustomResultTemplateWritingHandler
{
    /**
     * Creates a CustomValueObjectTemplateWritingHandler.
     */
    public CustomValueObjectTemplateWritingHandler() {};

    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    protected BasePerCustomResultTemplateGenerator retrieveTemplateGenerator()
    {
        return CustomValueObjectTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the templates from the attribute map.
     * @param parameters the parameter map.
     * @return the templates.
     * @throws BuildException if the template retrieval process if faulty.
     */
    protected BasePerCustomResultTemplate[] retrieveTemplates(
        final Map parameters)
        throws  BuildException
    {
        return
            (BasePerCustomResultTemplate[])
                parameters.get(
                    TemplateMappingManager.CUSTOM_VALUE_OBJECT_TEMPLATES);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param resultElement the result element.
     * @param customSqlProvider the custom sql provider.
     * @param metadataManager the metadata manager.
     * @param projectFolder the project folder.
     * @param projectPackage the project base package.
     * @param useSubfolders whether to use subfolders for tests, or
     * using a different package naming scheme.
     * @param engineName the engine name.
     * @param parameters the parameter map.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     */
    protected File retrieveOutputDir(
        final Result result,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final File projectFolder,
        final String projectPackage,
        final boolean useSubfolders,
        final String engineName,
        final Map parameters,
        final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            retrieveOutputDir(
                projectFolder,
                projectPackage,
                useSubfolders,
                packageUtils);
    }

    /**
     * Retrieves the output dir from the attribute map.
     * @param projectOutputDir the project output dir.
     * @param projectPackage the project package.
     * @param useSubfolders whether to use subfolders or not.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return such folder.
     * @throws BuildException if the output-dir retrieval process if faulty.
     * @precondition projectOutputDir != null
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected File retrieveOutputDir(
        final File projectOutputDir,
        final String projectPackage,
        final boolean useSubfolders,
        final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveValueObjectFolder(
                projectOutputDir,
                projectPackage,
                useSubfolders);
    }
}
