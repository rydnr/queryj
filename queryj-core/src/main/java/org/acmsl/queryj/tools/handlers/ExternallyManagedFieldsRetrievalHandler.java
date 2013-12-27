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
import org.acmsl.queryj.QueryJCommand;
import org.acmsl.queryj.QueryJCommandWrapper;
import org.acmsl.queryj.api.exceptions.MissingExternallyManagedFieldNameException;
import org.acmsl.queryj.api.exceptions.MissingExternallyManagedFieldTableNameException;
import org.acmsl.queryj.api.exceptions.QueryJBuildException;
import org.acmsl.queryj.tools.ant.AntExternallyManagedFieldsElement;
import org.acmsl.queryj.tools.ant.AntFieldElement;
import org.acmsl.queryj.tools.exceptions.MissingExternallyManagedFieldsAtRuntimeException;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.vo.Attribute;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing some Commons-Logging classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Retrieves the externally-managed fields declaration and
 * configures MetadataManager accordingly.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ExternallyManagedFieldsRetrievalHandler
    extends  AbstractQueryJCommandHandler
{
    /**
     * Creates an <code>ExternallyManagedFieldsRetrievalHandler</code>
     * instance.
     */
    public ExternallyManagedFieldsRetrievalHandler() {}

    /**
     * Handles given parameters.
     *
     *
     * @param parameters the parameters.
     * @return <code>true</code> if the chain should be stopped.
     * @throws QueryJBuildException if the build process cannot be performed.
     */
    @Override
    public boolean handle(@NotNull final QueryJCommand parameters)
        throws QueryJBuildException
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
     */
    protected boolean handle(
        @Nullable final MetadataManager metadataManager,
        @Nullable final AntExternallyManagedFieldsElement externallyManagedFields,
        @NotNull final StringValidator stringValidator)
      throws  QueryJBuildException
    {
        final boolean result = false;

        if  (   (metadataManager != null)
             && (externallyManagedFields != null))
        {
            for (@Nullable final AntFieldElement t_Field : externallyManagedFields.getFields())
            {
                if  (t_Field != null)
                {
                    if  (stringValidator.isEmpty(t_Field.getName()))
                    {
                        throw new MissingExternallyManagedFieldNameException(t_Field);
                    }
                    else
                    {
                        if (stringValidator.isEmpty(
                            t_Field.getTableName()))
                        {
                            throw new MissingExternallyManagedFieldTableNameException(t_Field);
                        }
                        else
                        {
                            @Nullable final Attribute<String> t_Attribute =
                                metadataManager.getColumnDAO().findColumn(
                                    t_Field.getTableName(),
                                    t_Field.getName());

                            if (t_Attribute != null)
                            {
                                metadataManager.getColumnDAO().update(
                                    t_Field.getTableName(),
                                    t_Field.getName(),
                                    t_Attribute.getTypeId(),
                                    t_Attribute.getType(),
                                    t_Attribute.getComment(),
                                    t_Attribute.getOrdinalPosition(),
                                    t_Attribute.getLength(),
                                    t_Attribute.getPrecision(),
                                    t_Attribute.getValue(),
                                    t_Field.getKeyword(),
                                    t_Field.getRetrievalQuery(),
                                    t_Attribute.isReadOnly(),
                                    t_Attribute.isNullable(),
                                    t_Attribute.isBoolean(),
                                    t_Attribute.getBooleanTrue(),
                                    t_Attribute.getBooleanFalse(),
                                    t_Attribute.getBooleanNull());

                            }
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
     */
    @NotNull
    protected AntExternallyManagedFieldsElement
        retrieveExternallyManagedFieldsElement(
        @NotNull final QueryJCommand parameters)
    {
        @Nullable final AntExternallyManagedFieldsElement result;

        @Nullable final AntExternallyManagedFieldsElement aux =
            new QueryJCommandWrapper<AntExternallyManagedFieldsElement>(parameters)
                .getSetting(ParameterValidationHandler.EXTERNALLY_MANAGED_FIELDS);

        if (aux == null)
        {
            throw new MissingExternallyManagedFieldsAtRuntimeException();
        }
        else
        {
            result = aux;
        }

        return result;
    }
}
