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
 * Filename: CustomResultTypeImportsHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Resolves "property_type_imports" placeholders, to avoid declaring
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
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyRefElement;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataTypeManager;
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.templates.BasePerCustomResultTemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Resolves "property_type_imports" placeholders, to avoid declaring
 * unused imports.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2012/06/20
 */
public class CustomResultTypeImportsHandler
    extends AbstractTemplateContextFillHandler<BasePerCustomResultTemplateContext, List<String>>
{
    /**
     * Creates a {@link CustomResultTypeImportsHandler} using given {@link BasePerCustomResultTemplateContext context}
     * @param context the context.
     */
    public CustomResultTypeImportsHandler(@NotNull final BasePerCustomResultTemplateContext context)
    {
        super(context);
    }

    /**
     * Retrieves "property_type_imports".
     * @return such value.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return "property_type_imports";
    }

    /**
     * Retrieves the list of JDK imports associated to the result properties.
     * @return such list.
     */
    @NotNull
    @Override
    protected List<String> getValue(@NotNull final BasePerCustomResultTemplateContext context)
    {
        return retrieveImports(context.getResult(), context.getCustomSqlProvider(), context.getMetadataManager());
    }

    /**
     * Retrieves the list of JDK imports associated to the result properties.
     * @param customResult the custom result.
     * @param metadataManager the {@link MetadataManager} instance.
     * @return such list.
     */
    @NotNull
    protected List<String> retrieveImports(
        @NotNull final Result customResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
    {
        return
            retrieveImports(
                customResult.getPropertyRefs(),
                customSqlProvider.getSqlPropertyDAO(),
                metadataManager.getMetadataTypeManager());
    }

    /**
     * Retrieves the list of JDK imports associated to the result properties.
     * @param propertyRefs the references to the properties.
     * @param sqlPropertyDAO the {@link SqlPropertyDAO} instance.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return such list.
     */
    @NotNull
    protected List<String> retrieveImports(
        @NotNull final List<PropertyRefElement> propertyRefs,
        @NotNull final SqlPropertyDAO sqlPropertyDAO,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        List<String> result = new ArrayList<String>();

        String t_strImport;

        Property t_Property;

        for (@Nullable PropertyRefElement t_PropertyRef : propertyRefs)
        {
            if (t_PropertyRef != null)
            {
                t_Property = sqlPropertyDAO.findByPrimaryKey(t_PropertyRef.getId());

                if (t_Property != null)
                {
                    t_strImport = t_Property.getType();

                    if (   (!metadataTypeManager.inJavaLang(t_strImport))
                        && (!result.contains(t_strImport)))
                    {
                        result.add(t_strImport);
                    }
                }
            }
        }

        return result;
    }
}
