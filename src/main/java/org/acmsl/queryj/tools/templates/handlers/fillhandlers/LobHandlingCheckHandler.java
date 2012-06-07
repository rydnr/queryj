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
 * Filename: LobHandlingCheckHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves "lob_handling_required" placeholders.
 *
 * Date: 6/3/12
 * Time: 10:39 AM
 *
 */
package org.acmsl.queryj.tools.templates.handlers.fillhandlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.templates.BasePerTableTemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Resolves "lob_handling_required" placeholders.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/03
 */
@SuppressWarnings("unused")
public class LobHandlingCheckHandler
    extends AbstractTemplateContextFillHandler<BasePerTableTemplateContext, Boolean>
{
    /**
     * Creates a {@link LobHandlingCheckHandler} instance.
     * @param context the context.
     */
    @SuppressWarnings("unused")
    public LobHandlingCheckHandler(@NotNull final BasePerTableTemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "lob_handling_required".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "lob_handling_required";
    }

    /**
     * Retrieves the template value for this placeholder.
     * @return such value.
     */
    @NotNull
    @Override
    protected Boolean getValue(@NotNull final BasePerTableTemplateContext context)
    {
        return isLobHandlingRequired(context.getTableName(), context.getMetadataManager());
    }

    /**
     * Checks whether processing current table requires taking care of clobs/blobs.
     * @param tableName the table name.
     * @param metadataManager the {@link MetadataManager} instance.
     */
    protected boolean isLobHandlingRequired(
        @NotNull final String tableName, @NotNull final MetadataManager metadataManager)
    {
        return
            (   (metadataManager.requiresCustomClobHandling())
             && (containsLobs(
                     tableName, metadataManager, metadataManager.getMetadataTypeManager())));
    }

    /**
     * Checks whether given table contains Lob attributes or not.
     * @param tableName the table name.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @return <code>true</code> in such case.
     */
    protected boolean containsLobs(
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        boolean result = false;

        for (@Nullable Attribute t_Column : metadataManager.getColumnDAO().findColumns(tableName, null, null))
        {
            if (t_Column != null)
            {
                if  (metadataTypeManager.isClob(t_Column.getTypeId()))
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }
}
