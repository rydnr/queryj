//;-*- mode: java -*-
/*
                        QueryJ Core

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
 * Filename: ProcedureParameterMetadata.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents procedure parameter metadata.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.Literals;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Represents procedure parameter metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ProcedureParameterMetadata
    extends  ProcedureMetadata
{
    /**
     * The data type.
     */
    private int m__iDataType;

    /**
     * The parameter length.
     */
    private int m__iLength;

    /**
     * The nullable setting.
     */
    private int m__iNullable;

    /**
     * Builds a ProcedureParameterMetaData using given information.
     * @param name the name.
     * @param type the type.
     * @param comment the comment.
     * @param dataType the data type.
     * @param length the length.
     * @param nullable the nullable flag.
     */
    public ProcedureParameterMetadata(
        final String name,
        final int type,
        @Nullable final String comment,
        final int dataType,
        final int length,
        final int nullable)
    {
        super(name, type, comment);
        immutableSetDataType(dataType);
        immutableSetLength(length);
        immutableSetNullable(nullable);

        try
        {
            LogFactory.getLog(ProcedureParameterMetadata.class).info(
                  "Parameter (name, type, comment, dataType, length, nullable) =\n"
                + name + ", " + type + ", " + comment + ", " + dataType
                + ", " + length + ", " + nullable);
        }
        catch  (@NotNull final Throwable throwable)
        {
            // class-loading problem.
        }
    }

    /**
     * Specifies the data type.
     * @param dataType the data type.
     */
    private void immutableSetDataType(final int dataType)
    {
        m__iDataType = dataType;
    }

    /**
     * Specifies the data type.
     * @param dataType the data type.
     */
    @SuppressWarnings("unused")
    protected void setDataType(final int dataType)
    {
        immutableSetDataType(dataType);
    }

    /**
     * Retrieves the data type.
     * @return such type.
     */
    public int getDataType()
    {
        return m__iDataType;
    }

    /**
     * Specifies the length.
     * @param length the length.
     */
    private void immutableSetLength(final int length)
    {
        m__iLength = length;
    }

    /**
     * Specifies the length.
     * @param length the length.
     */
    protected void setLength(final int length)
    {
        immutableSetLength(length);
    }

    /**
     * Retrieves the length.
     * @return such length.
     */
    public int getLength()
    {
        return m__iLength;
    }

    /**
     * Specifies the nullable setting.
     * @param nullable the nullable setting.
     */
    private void immutableSetNullable(final int nullable)
    {
        m__iNullable = nullable;
    }

    /**
     * Specifies the nullable setting.
     * @param nullable the nullable setting.
     */
    protected void setNullable(final int nullable)
    {
        immutableSetNullable(nullable);
    }

    /**
     * Retrieves the nullable setting.
     * @return such setting.
     */
    public int getNullable()
    {
        return m__iNullable;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
            new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("dataType", getDataType())
                .append(Literals.LENGTH, getLength())
                .append("nullable", getNullable())
                .toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-2052006179, 772726967)
                .appendSuper(super.hashCode())
                .append(getDataType())
                .append(getLength())
                .append(getNullable())
                .toHashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    public boolean equals(final Object object)
    {
        final boolean result;

        if  (object instanceof ProcedureParameterMetadata)
        {
            @NotNull final ProcedureParameterMetadata t_OtherInstance =
                (ProcedureParameterMetadata) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(t_OtherInstance))
                    .append(
                        getDataType(),
                        t_OtherInstance.getDataType())
                    .append(
                        getLength(),
                        t_OtherInstance.getLength())
                    .append(
                        getNullable(),
                        t_OtherInstance.getNullable())
                .isEquals();
        }
        else
        {
            result = super.equals(object);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final Object object)
        throws  ClassCastException
    {
        final int result;

        if  (object instanceof ProcedureParameterMetadata)
        {
            @NotNull final ProcedureParameterMetadata t_OtherInstance =
                (ProcedureParameterMetadata) object;

            result =
                new org.apache.commons.lang.builder.CompareToBuilder()
                .append(
                    getDataType(),
                    t_OtherInstance.getDataType())
                .append(
                    getLength(),
                    t_OtherInstance.getLength())
                .append(
                    getNullable(),
                    t_OtherInstance.getNullable())
                .toComparison();
        }
        else
        {
            result = super.compareTo(object);
        }

        return result;
    }
}
