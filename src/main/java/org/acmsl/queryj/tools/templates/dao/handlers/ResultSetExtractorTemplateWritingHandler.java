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
 * Filename: ResultSetExtractorTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes ResultSetExtractor templates.
 *
 */
package org.acmsl.queryj.tools.templates.dao.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.ResultSetExtractorTemplate;
import org.acmsl.queryj.tools.templates.dao.ResultSetExtractorTemplateGenerator;
import org.acmsl.queryj.tools.templates.dao.handlers.ResultSetExtractorTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.handlers.BasePerTableTemplateWritingHandler;
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
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * Writes ResultSetExtractor templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ResultSetExtractorTemplateWritingHandler
    extends    BasePerTableTemplateWritingHandler
{
    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    protected BasePerTableTemplateGenerator retrieveTemplateGenerator()
    {
        return ResultSetExtractorTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the templates from the attribute map.
     * @param parameters the parameter map.
     * @return the templates.
     * @throws BuildException if the template retrieval process if faulty.
     * @precondition parameters != null
     */
    protected BasePerTableTemplate[] retrieveTemplates(
        final Map parameters)
      throws  BuildException
    {
        return
            (BasePerTableTemplate[])
                parameters.get(
                    TemplateMappingManager.RESULTSET_EXTRACTOR_TEMPLATES);
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
    protected File retrieveOutputDir(
        final File projectFolder,
        final String projectPackage,
        final boolean useSubfolders,
        final String tableName,
        final String engineName,
        final Map parameters,
        final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveResultSetExtractorFolder(
                projectFolder,
                projectPackage,
                engineName,
                tableName,
                useSubfolders);
    }
}
