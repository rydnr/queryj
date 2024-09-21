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
 * Filename: ProcedureMetadata.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents procedure metadata.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.Literals;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents procedure metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ProcedureMetadata
{
    /**
     * The name.
     */
    private String m__strName;

    /**
     * The type.
     */
    private int m__iType;

    /**
     * The comment.
     */
    private String m__strComment;

    /**
     * Builds a ProcedureMetaData using given information.
     * @param name the procedure name.
     * @param type the type.
     * @param comment the procedure comment.
     */
    public ProcedureMetadata(
        @NotNull final String name, final int type, @Nullable final String comment)
    {
        immutableSetName(name);
        immutableSetType(type);
        if (comment != null)
        {
            immutableSetComment(comment);
        }
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    private void immutableSetName(final String name)
    {
        m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    protected void setName(final String name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the name.
     * @return such name.
     */
    public String getName()
    {
        return m__strName;
    }

    /**
     * Specifies the type.
     * @param type the type.
     */
    private void immutableSetType(final int type)
    {
        m__iType = type;
    }

    /**
     * Specifies the type.
     * @param type the type.
     */
    protected void setType(final int type)
    {
        immutableSetType(type);
    }

    /**
     * Retrieves the type.
     * @return such type.
     */
    public int getType()
    {
        return m__iType;
    }

    /**
     * Specifies the comment.
     * @param comment the comment.
     */
    private void immutableSetComment(final String comment)
    {
        m__strComment = comment;
    }

    /**
     * Specifies the comment.
     * @param comment the comment.
     */
    protected void setComment(@NotNull final String comment)
    {
        immutableSetComment(comment);
    }

    /**
     * Retrieves the comment.
     * @return such comment.
     */
    @Nullable
    public String getComment()
    {
        return m__strComment;
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    @NotNull
    @Override
    public String toString()
    {
        return
            new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("name", getName())
                .append("type", getType())
                .append(Literals.COMMENT, getComment())
                .toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return
            new org.apache.commons.lang.builder.HashCodeBuilder(-2052006161, 638986823)
                .appendSuper(super.hashCode())
                .append(m__strName)
                .append(m__iType)
                .append(m__strComment)
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
        boolean result = false;

        if  (object instanceof ProcedureMetadata)
        {
            @NotNull final ProcedureMetadata t_OtherInstance =
                (ProcedureMetadata) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(t_OtherInstance))
                    .append(
                        getName(),
                        t_OtherInstance.getName())
                    .append(
                        getType(),
                        t_OtherInstance.getType())
                    .append(
                        getComment(),
                        t_OtherInstance.getComment())
                .isEquals();
        }

        return result;
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * object prevents it from being compared to this Object.
     */
    public int compareTo(final Object object)
        throws  ClassCastException
    {
        int result = 1;

        @Nullable ClassCastException exceptionToThrow = null;

        if  (object instanceof ProcedureMetadata)
        {
            @NotNull final ProcedureMetadata t_OtherInstance =
                (ProcedureMetadata) object;

            result =
                new org.apache.commons.lang.builder.CompareToBuilder()
                .append(
                    getName(),
                    t_OtherInstance.getName())
                .append(
                    getType(),
                    t_OtherInstance.getType())
                .append(
                    getComment(),
                    t_OtherInstance.getComment())
                .toComparison();
        }
        else
        {
            exceptionToThrow =
                new ClassCastException(
                    Literals.CANNOT_COMPARE
                    + object
                    + " with "
                    + toString());
        }

        if  (exceptionToThrow != null)
        {
            throw  exceptionToThrow;
        }

        return result;
    }
}
