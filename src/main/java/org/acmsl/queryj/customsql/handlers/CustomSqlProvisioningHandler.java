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
 * Filename: CustomSqlProvisioningHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Adds additional custom-sql information to avoid
 *              the need to manually specify elements derived from the model.
 *
 */
package org.acmsl.queryj.customsql.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.SqlPropertyDAO;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/*
 * Importing some JetBrains annotations.
 */
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Table;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.List;
import java.util.Map;

/**
 * Adds additional custom-sql information to avoid
 * the need to manually specify elements derived from the model.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
public class CustomSqlProvisioningHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * Creates a <code>CustomSqlProvisioningHandler</code> instance.
     */
    @SuppressWarnings("unused")
    public CustomSqlProvisioningHandler() {}

    /**
     * Handles given parameters.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        boolean result = true;

        MetadataManager t_MetadataManager =
            retrieveMetadataManager(parameters);

        if (t_MetadataManager != null)
        {
            result =
                handle(
                    retrieveCustomSqlProvider(parameters),
                    t_MetadataManager);
        }

        return result;
    }

    /**
     * Handles given parameters.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return <code>true</code> if the chain should be stopped.
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     */
    protected boolean handle(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager)
      throws  QueryJBuildException
    {
        return
            handle(
                customSqlProvider.getSqlPropertyDAO(),
                metadataManager,
                metadataManager.getMetadataTypeManager());
    }

    /**
     * Handles given parameters.
     * @param sqlPropertyDAO the {@link SqlPropertyDAO} instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    protected boolean handle(
        @NotNull final SqlPropertyDAO sqlPropertyDAO,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
      throws  QueryJBuildException
    {
        boolean result = false;

        List<Table> t_lTables = metadataManager.getTableDAO().findAllTables();

        String t_strTableName;
        String t_strResultName;
        String[] t_astrAttributeNames;
        String t_strAttributeName;
        String t_strPropertyName;
        int t_iAttributeCount;

        for  (@Nullable Table t_Table : t_lTables)
        {
            if (t_Table != null)
            {
                for (@Nullable Attribute t_Attribute : t_Table.getAttributes())
                {
                    if (t_Attribute != null)
                    {
                        t_strPropertyName =
                            buildPropertyName(t_Attribute.getName());

                        sqlPropertyDAO.insert(
                            t_strPropertyName,
                            t_Table.getName(),
                            metadataTypeManager.getFieldType(
                                t_Attribute.getTypeId()));
                    }
                }
            }
        }

        return result;
    }

    /**
     * Builds the result name.
     * @param name the attribute name.
     * @return such result.
     */
    protected String buildResultName(final String name)
    {
        // TODO
        return name;
    }

    /**
     * Builds the property name.
     * @param name the attribute name.
     * @return such property.
     */
    protected String buildPropertyName(final String name)
    {
        // TODO
        return name;
    }
}
