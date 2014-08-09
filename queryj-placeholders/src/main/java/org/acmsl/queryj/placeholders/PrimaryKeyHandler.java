/*
                        QueryJ Placeholders

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
 * Filename: PrimaryKeyHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves "pk_attributes" placeholders.
 *
 * Date: 5/31/12
 * Time: 2:14 AM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataUtils;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.api.PerTableTemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Resolves "pk_attributes" placeholders with the primary key information from given {@link org.acmsl.queryj.api.PerTableTemplateContext}
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/05/31
 */
@SuppressWarnings("unused")
@ThreadSafe
public class PrimaryKeyHandler
    extends AbstractTemplateContextFillHandler<PerTableTemplateContext, List<Attribute<String>>>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -8692058693572483344L;

    /**
     *  Creates a {@link PrimaryKeyHandler} with given {@link org.acmsl.queryj.api.PerTableTemplateContext context}.
     * @param context the context
     */
    @SuppressWarnings("unused")
    public PrimaryKeyHandler(@NotNull final PerTableTemplateContext context)
    {
        super(context);
    }

    /**
     * Retrieves the template value for this placeholder.
     * @param context the context.
     * @return such value.
     */
    @NotNull
    @Override
    protected List<Attribute<String>> getValue(@NotNull final PerTableTemplateContext context)
    {
        return
            retrievePrimaryKeyAttributes(
                context.getTableName(),
                context.getMetadataManager(),
                MetadataUtils.getInstance());
    }

    /**
     * Returns "pk_attributes".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "pk_attributes";
    }

    /**
     * Retrieves the primary key attributes.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataUtils the {@link MetadataUtils} instance.
     * @return such attributes.
     */
    @NotNull
    protected List<Attribute<String>> retrievePrimaryKeyAttributes(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataUtils metadataUtils)
    {
        return
            metadataUtils.retrievePrimaryKeyAttributes(
                tableName, metadataManager);
    }

}
