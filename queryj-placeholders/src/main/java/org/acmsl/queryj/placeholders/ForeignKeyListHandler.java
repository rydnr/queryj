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
 * Filename: ForeignKeyAttributesHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves "foreign_keys" placeholders.
 *
 * Date: 6/3/12
 * Time: 11:13 AM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataUtils;
import org.acmsl.queryj.metadata.vo.ForeignKey;
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
* Resolves "foreign_keys placeholders.
* @author <a href="mailto:chous@acm-sl.org">chous@acm-sl.org</a>
* @since 2012/06/03
*/
@SuppressWarnings("unused")
@ThreadSafe
public class ForeignKeyListHandler
    extends AbstractTemplateContextFillHandler<PerTableTemplateContext, List<ForeignKey>>
{

    private static final long serialVersionUID = 7359967862456047176L;

    /**
     * Creates a {@link ForeignKeyListHandler} with given {@link org.acmsl.queryj.api.PerTableTemplateContext context}.
     * @param context the context.
     */
    public ForeignKeyListHandler(@NotNull final PerTableTemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "foreign_keys".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "foreign_keys";
    }

    /**
     * Retrieves the list of {@link ForeignKey foreign keys} for a given table.
     * @return such value.
     */
    @NotNull
    @Override
    protected List<ForeignKey> getValue(@NotNull final PerTableTemplateContext context)
    {
        return
            retrieveForeignKeys(
                context.getTableName(),
                context.getMetadataManager(),
                MetadataUtils.getInstance());
    }

    /**
     * Retrieves the list of {@link ForeignKey foreign keys} for a given table.
     * @param tableName the name of the table.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param metadataUtils the {@link MetadataUtils} instance.
     * @return such value.
     */
    @NotNull
    protected List<ForeignKey> retrieveForeignKeys(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataUtils metadataUtils)
    {
        return
            metadataUtils.retrieveForeignKeyAttributes(
                tableName, metadataManager);
    }

}
