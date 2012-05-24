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
 * Filename: DAOSubpackageNameHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Is able to resolve "DAO_subpackage_name" placeholders in
 *              templates.
 *
 * Date: 5/24/12
 * Time: 4:02 AM
 *
 */
package org.acmsl.queryj.tools.templates.handlers.fillhandlers;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.PackageUtils;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.templates.TemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Is able to resolve "DAO_subpackage_name" placeholders in templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/24
 */
@SuppressWarnings("unused")
public class DAOSubpackageNameHandler
    extends AbstractTemplateContextFillHandler<TemplateContext, String>
{
    /**
     * Creates a {@link DAOSubpackageNameHandler} using given {@link TemplateContext context}.
     * @param context the {@link TemplateContext context}.
     */
    @SuppressWarnings("unused")
    public DAOSubpackageNameHandler(@NotNull final TemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "dao_subpackage_name".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "dao_subpackage_name";
    }

    /**
     * Retrieves the subpackage name for the DAO classes.
     * @return such value.
     */
    @NotNull
    @Override
    protected String getValue(@NotNull final TemplateContext context)
    {
        return
            retrieveDAOSubpackageName(
                context, context.getMetadataManager(), PackageUtils.getInstance());
    }

    /**
     * Retrieves the subpackage name for the DAO classes.
     * @param context the {@link TemplateContext context}.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param packageUtils the {@link PackageUtils} instance.
     * @return such value.
     */
    @NotNull
    protected String retrieveDAOSubpackageName(
        @NotNull final TemplateContext context,
        @NotNull final MetadataManager metadataManager,
        @NotNull final PackageUtils packageUtils)
    {
        return
            packageUtils.retrieveDAOPackage(context.getBasePackageName(), metadataManager.getEngineName());
    }
}
