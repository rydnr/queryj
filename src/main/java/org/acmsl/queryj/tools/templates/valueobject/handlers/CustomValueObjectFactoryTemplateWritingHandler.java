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
 * Filename: CustomValueObjectFactoryTemplateWritingHandler.java
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
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.templates.valueobject.CustomValueObjectFactoryTemplateGenerator;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.valueobject.CustomValueObjectTemplate;
import org.acmsl.queryj.tools.templates.valueobject.CustomValueObjectTemplateGenerator;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;
import java.util.Map;

/**
 * Writes ValueObject templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomValueObjectFactoryTemplateWritingHandler
    extends  CustomValueObjectTemplateWritingHandler
{
    /**
     * Creates a CustomValueObjectFactoryTemplateWritingHandler.
     */
    public CustomValueObjectFactoryTemplateWritingHandler() {}

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    protected CustomValueObjectTemplateGenerator retrieveTemplateGenerator()
    {
        return CustomValueObjectFactoryTemplateGenerator.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    protected List<CustomValueObjectTemplate> retrieveTemplates(
        @NotNull final Map parameters)
        throws QueryJBuildException
    {
        return
            (List<CustomValueObjectTemplate>)
                parameters.get(
                    TemplateMappingManager
                        .CUSTOM_VALUE_OBJECT_FACTORY_TEMPLATES);
    }
}
