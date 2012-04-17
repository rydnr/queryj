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
 * Filename: CustomBaseValueObjectTemplateWritingHandler.java
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
import org.acmsl.queryj.tools.templates.TemplateGenerator;
import org.acmsl.queryj.tools.templates.valueobject.CustomBaseValueObjectTemplate;
import org.acmsl.queryj.tools.templates.valueobject.CustomBaseValueObjectTemplateGenerator;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.Map;

/**
 * Writes ValueObject templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class CustomBaseValueObjectTemplateWritingHandler<T extends CustomBaseValueObjectTemplate>
    extends  CustomValueObjectTemplateWritingHandler<T>
{
    /**
     * Creates a CustomBaseValueObjectTemplateWritingHandler.
     */
    public CustomBaseValueObjectTemplateWritingHandler() {}

    /**
     * Retrieves the template generator.
     * @return such instance.
     */
    @NotNull
    @Override
    protected TemplateGenerator<T> retrieveTemplateGenerator()
    {
        return CustomBaseValueObjectTemplateGenerator.getInstance();
    }

    /**
     * Retrieves the templates from the attribute map.
     * @param parameters the parameter map.
     * @return the templates.
     * @throws BuildException if the template retrieval process if faulty.
     */
    @NotNull
    @Override
    protected T[] retrieveTemplates(
        @NotNull final Map parameters)
        throws  BuildException
    {
        return
            (T[])
                parameters.get(
                    TemplateMappingManager.CUSTOM_BASE_VALUE_OBJECT_TEMPLATES);
    }
}
