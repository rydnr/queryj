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
 * Filename: CustomValueObjectImplTemplateBuildHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Builds a custom ValueObjectImpl template.
 *
 */
package org.acmsl.queryj.tools.templates.valueobject.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.templates.valueobject.CustomValueObjectImplTemplate;
import org.acmsl.queryj.tools.templates.valueobject.CustomValueObjectImplTemplateGenerator;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Builds custom ValueObjectImpl templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomValueObjectImplTemplateBuildHandler
    extends  CustomValueObjectTemplateBuildHandler<CustomValueObjectImplTemplate, CustomValueObjectImplTemplateGenerator>
{
    /**
     * Creates a CustomValueObjectImplTemplateBuildHandler.
     */
    public CustomValueObjectImplTemplateBuildHandler() {}

    /**
     * Retrieves the template factory.
     * @return such instance.
     */
    @Override
    @NotNull
    protected CustomValueObjectImplTemplateGenerator retrieveTemplateFactory()
    {
        return new CustomValueObjectImplTemplateGenerator();
    }

    /**
     * Stores the template collection in given attribute map.
     * @param templates the templates.
     * @param parameters the parameter map.
     * @precondition templates != null
     * @precondition parameters != null
     */
    @Override
    protected void storeTemplates(
        @NotNull final CustomValueObjectImplTemplate[] templates, @NotNull final Map parameters)
    {
        parameters.put(
            TemplateMappingManager.CUSTOM_VALUE_OBJECT_IMPL_TEMPLATES,
            templates);
    }
}
