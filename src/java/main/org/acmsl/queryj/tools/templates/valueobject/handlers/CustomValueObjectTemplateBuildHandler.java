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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a ValueObject template.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplate;
import org.acmsl.queryj.tools.templates.BasePerCustomResultTemplateFactory;
import org.acmsl.queryj.tools.templates.valueobject.CustomValueObjectTemplate;
import org.acmsl.queryj.tools.templates.valueobject.CustomValueObjectTemplateGenerator;
import org.acmsl.queryj.tools.templates.TableTemplate;
import org.acmsl.queryj.tools.templates.handlers.BasePerCustomResultTemplateBuildHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Builds custom ValueObject templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CustomValueObjectTemplateBuildHandler
    extends  BasePerCustomResultTemplateBuildHandler
{
    /**
     * An empty template array.
     */
    protected static final CustomValueObjectTemplate[]
        EMPTY_CUSTOMRESULTSETEXTRACTOR_TEMPLATE_ARRAY =
            new CustomValueObjectTemplate[0];

    /**
     * Creates a CustomValueObjectTemplateBuildHandler.
     */
    public CustomValueObjectTemplateBuildHandler() {};

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    protected BasePerCustomResultTemplateFactory retrieveTemplateFactory()
    {
        return CustomValueObjectTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the package name.
     * @param customResult the custom result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the database metadata manager.
     * @param engineName the engine name.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrievePackage(
        final Result customResult,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final String engineName,
        final String projectPackage,
        final PackageUtils packageUtils)
      throws BuildException
    {
        return
            packageUtils.retrieveValueObjectPackage(
                projectPackage);
    }

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    protected void storeTemplates(
        final BasePerCustomResultTemplate[] templates, final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.CUSTOM_VALUEOBJECT_TEMPLATES,
            templates);
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param projectPackage the project package.
     * @param packageUtils the <code>PackageUtils</code> instance.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     * @precondition projectPackage != null
     * @precondition packageUtils != null
     */
    protected String retrievePackage(
        final String projectPackage,
        final PackageUtils packageUtils)
      throws  BuildException
    {
        return
            packageUtils.retrieveValueObjectPackage(
                projectPackage);
    }
}
