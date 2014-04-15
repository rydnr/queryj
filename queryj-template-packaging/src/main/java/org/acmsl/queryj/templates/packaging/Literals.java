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
 * Filename: Literals.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Literals used in QueryJ Template Packaging.
 *
 * Date: 2013/12/08
 * Time: 20:19
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Literals used in QueryJ Template Packaging.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/12/08 20:19
 */
@ThreadSafe
public interface Literals
{
    String REPOSITORY = org.acmsl.queryj.Literals.REPOSITORY;
    String CUCUMBER_TEMPLATES = "cucumber.templates";
    String PER_TABLE_TEMPLATES_TEST = "PerTableTemplatesTest";
    String PER_FOREIGN_KEY_TEMPLATES_TEST = "PerForeignKeyTemplatesTest";
    String TEMPLATE_BUILD_HANDLER = "TemplateBuildHandler";
    String DOT_HANDLERS = ".handlers";
    String TEMPLATE_FACTORY = "TemplateFactory";
    String TEMPLATE_GENERATOR = "TemplateGenerator";
    String TEMPLATE_HANDLER_BUNDLE = "TemplateHandlerBundle";
    String TEMPLATE_WRITING_HANDLER = "TemplateWritingHandler";
    String PER_TABLE_TEMPLATES_FEATURE = "PerTableTemplatesFeature";
    String PACKAGE = org.acmsl.queryj.Literals.PACKAGE;
    String ORG_ACMSL_QUERYJ_TEMPLATES_PACKAGING = "org/acmsl/queryj/templates/packaging/";
}
