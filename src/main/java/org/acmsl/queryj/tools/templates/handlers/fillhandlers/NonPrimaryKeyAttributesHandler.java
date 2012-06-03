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
 * Filename: NonPrimaryKeyAttributesHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves "non_pk_attributes" placeholders.
 *
 * Date: 6/3/12
 * Time: 11:24 AM
 *
 */
package org.acmsl.queryj.tools.templates.handlers.fillhandlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataUtils;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateContext;

/*
 * Importing some Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/**
 * Resolves "non_pk_attributes" placeholders.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/03
 */
@SuppressWarnings("unused")
public class NonPrimaryKeyAttributesHandler
    extends AbstractTemplateContextFillHandler<BasePerTableTemplateContext, List<Attribute>>
{
    /**
     * Creates a {@link NonPrimaryKeyAttributesHandler} with given {@link BasePerTableTemplateContext}
     * @param context the context.
     */
    public NonPrimaryKeyAttributesHandler(@NotNull final BasePerTableTemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "non_pk_attributes".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "non_pk_attributes";
    }

    /**
     * Retrieves the attributes not part of the primary key.
     * @param context the {@link BasePerTableTemplateContext context}.
     * @return such value.
     */
    @NotNull
    @Override
    protected List<Attribute> getValue(@NotNull final BasePerTableTemplateContext context)
    {
        return
            retrieveNonPkAttributes(
                context.getTableName(),
                context.getMetadataManager(),
                context.getDecoratorFactory(),
                MetadataUtils.getInstance());
    }

    /**
     * Retrieves the attributes not part of the primary key.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param metadataUtils the {@link MetadataUtils} instance.
     */
    @NotNull
    protected List<Attribute> retrieveNonPkAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final MetadataUtils metadataUtils)
    {
        return
            metadataUtils.retrieveNonPrimaryKeyAttributes(
                tableName,
                metadataManager,
                metadataManager.getMetadataTypeManager(),
                decoratorFactory);
    }
}
