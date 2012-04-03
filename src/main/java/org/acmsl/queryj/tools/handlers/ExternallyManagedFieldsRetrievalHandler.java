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
 * Filename: ExternallyManagedFieldsRetrievalHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Retrieves the externally-managed fields declaration and
 *              configures DatabaseMetaDataManager accordingly.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.ant.AntExternallyManagedFieldsElement;
import org.acmsl.queryj.tools.ant.AntFieldElement;
import org.acmsl.queryj.tools.ant.AntTableElement;
import org.acmsl.queryj.tools.ant.AntTablesElement;
import org.acmsl.queryj.tools.QueryJBuildException;
import org.acmsl.queryj.tools.QueryJCommand;
import org.acmsl.queryj.tools.handlers.AbstractQueryJCommandHandler;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.patterns.Command;
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing some Commons-Logging classes.
 */
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

/**
 * Retrieves the externally-managed fields declaration and
 * configures MetadataManager accordingly.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class ExternallyManagedFieldsRetrievalHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * Creates an <code>ExternallyManagedFieldsRetrievalHandler</code>
     * instance.
     */
    public ExternallyManagedFieldsRetrievalHandler() {};

    /**
     * Handles given parameters.
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition parameters != null
     */
    @Override
    protected boolean handle(@NotNull final Map parameters)
        throws  QueryJBuildException
    {
        return
            handle(
                retrieveMetadataManager(parameters),
                retrieveExternallyManagedFieldsElement(parameters),
                StringValidator.getInstance());
    }
                
    /**
     * Handles given parameters.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param externallyManagedFields information about the
     * externally-managed fields.
     * @param stringValidator the {@link StringValidator} instance.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     * @precondition metadataManager != null
     */
    protected boolean handle(
        @NotNull final MetadataManager metadataManager,
        @Nullable final AntExternallyManagedFieldsElement externallyManagedFields,
        @NotNull final StringValidator stringValidator)
      throws  QueryJBuildException
    {
        boolean result = false;

        if  (externallyManagedFields != null)
        {
            Collection t_cFieldElements =
                externallyManagedFields.getFields();

            @Nullable Iterator t_itFieldIterator =
                (t_cFieldElements != null)
                ?  t_cFieldElements.iterator()
                :  null;

            if  (t_itFieldIterator != null)
            {
                AntFieldElement t_Field;

                while  (t_itFieldIterator.hasNext())
                {
                    t_Field = (AntFieldElement) t_itFieldIterator.next();

                    if  (t_Field != null)
                    {
                        if  (stringValidator.isEmpty(t_Field.getName()))
                        {
                            throw
                                new QueryJBuildException(
                                    "Field name not specified.");
                        }
                        else if  (stringValidator.isEmpty(
                                      t_Field.getTableName()))
                        {
                            throw
                                new QueryJBuildException(
                                    "Field name not specified.");
                        }
                        else 
                        {
                            metadataManager.addExternallyManagedField(
                                t_Field.getTableName(),
                                t_Field.getName(),
                                t_Field.getKeyword(),
                                t_Field.getRetrievalQuery());
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the externally-managed-fields XML element stored in the
     * attribute map.
     * @param parameters the parameter map.
     * @return the externally-managed-fields information.
     * @precondition parameters != null
     */
    @NotNull
    protected AntExternallyManagedFieldsElement
        retrieveExternallyManagedFieldsElement(@NotNull final Map parameters)
    {
        return
            (AntExternallyManagedFieldsElement)
                parameters.get(
                    ParameterValidationHandler.EXTERNALLY_MANAGED_FIELDS);
    }
}
