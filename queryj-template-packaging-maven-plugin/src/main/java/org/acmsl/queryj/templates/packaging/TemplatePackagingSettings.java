/*
                        QueryJ Template Packaging

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
 * Filename: TemplatePackagingSettings.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the settings used by template packaging.
 *
 * Date: 2013/08/11
 * Time: 08:43
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Defines the settings used by template packaging.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/11 08:43
 */
@ThreadSafe
public interface TemplatePackagingSettings
{
    /**
     * The source folders.
     */
    String SOURCES = "sources";

    /**
     * The def files.
     */
    String DEF_FILES = "def_files";

    /**
     * The template defs.
     */
    String TEMPLATE_DEFS = "template_defs";

    /**
     * The template packaging group.
     */
    String TEMPLATE_PACKAGING_GROUP = "org/acmsl/queryj/templates/packaging/";

    /**
     * The key to access the output dir.
     */
    String OUTPUT_DIR = "output-dir";

    /**
     * The key to access the template templates.
     */
    String TEMPLATE_TEMPLATES = "template_templates";

    /**
     * The key to access the template factory templates.
     */
    String TEMPLATE_FACTORY_TEMPLATES = "template_factory_templates";

    /**
     * The key to access the template generator templates.
     */
    String TEMPLATE_GENERATOR_TEMPLATES = "template_generator_templates";

    /**
     * The key to access the template build handler templates.
     */
    String TEMPLATE_BUILD_HANDLER_TEMPLATES = "template_build_handler_templates";

    /**
     * The key to access the template writing handler templates.
     */
    String TEMPLATE_WRITING_HANDLER_TEMPLATES = "template_writing_handler_templates";

    /**
     * The key to access the template handler bundle templates.
     */
    String TEMPLATE_HANDLER_BUNDLE_TEMPLATES = "template_handler_bundle_templates";

    /*
     * The base package for the QueryJ-specific output files.
     */
    String OUTPUT_PACKAGE = "org.acmsl.queryj.templates";

    /**
     * The key to access the DefaultTemplateChainProvider template.
     */
    String DEFAULT_TEMPLATE_CHAIN_PROVIDER_TEMPLATE = "default_template_chain_provider_template";
}
