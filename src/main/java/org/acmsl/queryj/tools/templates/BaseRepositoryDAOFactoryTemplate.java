//;-*- mode: java -*-
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
 * Filename: BaseRepositoryDAOFactoryTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate base repository DAO factories  according
 *              to database metadata.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Is able to generate base repository DAO factories according to database
 * metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class BaseRepositoryDAOFactoryTemplate
    extends  RepositoryDAOTemplate<BasePerRepositoryTemplateContext>
{
    private static final long serialVersionUID = -607618292235033006L;

    /**
     * Builds a <code>BaseRepositoryDAOFactoryTemplate</code> using given
     * information.
     * @param context thre {@link BasePerRepositoryTemplateContext} instance.
     */
    public BaseRepositoryDAOFactoryTemplate(@NotNull final BasePerRepositoryTemplateContext context)
    {
        super(context);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Override
    @Nullable
    public StringTemplateGroup retrieveGroup()
    {
        return
            retrieveGroup(
                "/org/acmsl/queryj/dao/BaseRepositoryDAOFactory.stg");
    }

    /**
     * Returns "BaseRepositoryDAOFactory".
     * @return such information.
     */
    @NotNull
    @Override
    public String getTemplateName()
    {
        return "BaseRepositoryDAOFactory";
    }
}
