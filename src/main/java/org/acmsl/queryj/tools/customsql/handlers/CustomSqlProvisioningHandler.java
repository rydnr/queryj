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
package org.acmsl.queryj.tools.customsql.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
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
        return
            handle(
                retrieveCustomSqlProvider(parameters),
                retrieveMetadataManager(parameters));
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
                customSqlProvider,
                metadataManager,
                metadataManager.getMetadataTypeManager());
    }

    /**
     * Handles given parameters.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    protected boolean handle(
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
      throws  QueryJBuildException
    {
        boolean result = false;

        String[] t_astrTableNames = metadataManager.getTableNames();

        int t_iTableCount =
            (t_astrTableNames != null) ? t_astrTableNames.length : 0;

        String t_strTableName;
        String t_strResultName;
        String[] t_astrAttributeNames;
        String t_strAttributeName;
        String t_strPropertyName;
        int t_iAttributeCount;

        for  (int t_iTableIndex = 0;
                  t_iTableIndex < t_iTableCount;
                  t_iTableIndex++)
        {
            if (t_astrTableNames != null)
            {
                t_strTableName = t_astrTableNames[t_iTableIndex];

                t_astrAttributeNames =
                    metadataManager.getColumnNames(t_strTableName);

                t_iAttributeCount =
                    (t_astrAttributeNames != null)
                    ?  t_astrAttributeNames.length
                    :  0;

                for  (int t_iAttributeIndex = 0;
                          t_iAttributeIndex < t_iAttributeCount;
                          t_iAttributeIndex++)
                {
                    if (t_astrAttributeNames != null)
                    {
                        t_strAttributeName =
                            t_astrAttributeNames[t_iAttributeIndex];

                        t_strPropertyName =
                            buildPropertyName(t_strAttributeName);

                        customSqlProvider.addProperty(
                            t_strPropertyName,
                            t_strTableName,
                            metadataTypeManager.getFieldType(
                                metadataManager.getColumnType(
                                    t_strTableName,
                                    t_strAttributeName),
                                metadataManager.allowsNull(
                                    t_strTableName,
                                    t_strAttributeName),
                                metadataManager.isBoolean(t_strTableName, t_strAttributeName)));
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
