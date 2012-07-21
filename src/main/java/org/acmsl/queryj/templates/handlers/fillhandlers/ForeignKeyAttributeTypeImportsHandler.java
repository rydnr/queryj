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
 * Filename: ForeignKeyAttributeTypeImportsHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves "foreign_key_type_imports" placeholders, to avoid declaring
 * unused imports
 *
 * Date: 6/20/12
 * Time: 2:54 AM
 *
 */
package org.acmsl.queryj.templates.handlers.fillhandlers;

/*
 * Importing some project classes.
 */

import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.templates.BasePerForeignKeyTemplateContext;

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
 * Resolves "foreign_key_type_imports" placeholders, to avoid declaring
 * unused imports.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/07/08
 */
@ThreadSafe
public class ForeignKeyAttributeTypeImportsHandler
    extends AbstractTemplateContextFillHandler<BasePerForeignKeyTemplateContext, List<String>>
{
    /**
     * Creates a {@link ForeignKeyAttributeTypeImportsHandler} using
     * given {@link BasePerForeignKeyTemplateContext context}
     * @param context the context.
     */
    public ForeignKeyAttributeTypeImportsHandler(@NotNull final BasePerForeignKeyTemplateContext context)
    {
        super(context);
    }

    /**
     * Retrieves "foreign_key_type_imports".
     * @return such value.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "foreign_key_type_imports";
    }

    /**
     * Retrieves the list of JDK imports associated to the table attributes.
     * @return such list.
     */
    @NotNull
    @Override
    protected List<String> getValue(@NotNull final BasePerForeignKeyTemplateContext context)
    {
        return retrieveImports(context.getForeignKey(), context.getMetadataManager());
    }

    /**
     * Retrieves the list of JDK imports associated to the table attributes.
     * @param foreignKey the foreign key.
     * @param metadataManager the {@link org.acmsl.queryj.metadata.MetadataManager} instance.
     * @return such list.
     */
    @NotNull
    protected List<String> retrieveImports(
        @NotNull final ForeignKey foreignKey, @NotNull final MetadataManager metadataManager)
    {
        return
            TableAttributeTypeImportsHandler.retrieveImports(
                foreignKey.getSourceTableName(),
                metadataManager.getTableDAO(),
                metadataManager.getMetadataTypeManager());
    }
}
