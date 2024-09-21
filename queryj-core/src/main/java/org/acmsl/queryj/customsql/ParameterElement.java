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
 * Filename: ParameterElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models <parameter> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.customsql;

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
 * Models &lt;parameter&gt; elements in <i>custom-sql</i> models, which
 * satisfy the following DTD extract (to describe the model even in
 * non-xml implementations):
 *  &lt;!ELEMENT parameter EMPTY&gt;
 *  &lt;!ATTLIST parameter
 *    id ID #REQUIRED
 *    index CDATA #IMPLIED
 *    type CDATA #REQUIRED&gt;
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro Armendariz</a>
 * @param <T> the type.
 * @param <V> the type of the value.
 */
@ThreadSafe
public class ParameterElement<T, V>
    extends  AbstractParameterElement<T>
    implements Parameter<T, V>,
               Comparable<Parameter<T, V>>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 106725100966627608L;

    /**
     * The parameter name.
     */
    private T m__strName;

    /**
     * The validation value.
     */
    private V m__strValidationValue;

    /**
     * Creates a ParameterElement with given information.
     * @param id the <i>id</i> attribute.
     * @param index the <i>index</i> attribute.
     * @param name the <i>name</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param validationValue the <i>validation-value</i> attribute.
     */
    public ParameterElement(
        @NotNull final T id,
        final int index,
        @NotNull final T name,
        @NotNull final T type,
        @Nullable final V validationValue)
    {
        super(id, index, type);

        immutableSetName(name);

        if (validationValue != null)
        {
            immutableSetValidationValue(validationValue);
        }
    }

    /**
     * Specifies the <i>name</i> attribute.
     * @param name such value.
     */
    protected final void immutableSetName(@NotNull final T name)
    {
        m__strName = name;
    }

    /**
     * Specifies the <i>name</i> attribute.
     * @param name such value.
     */
    protected void setName(@NotNull final T name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the <i>name</i> attribute.
     * @return such value.
     */
    @Override
    @NotNull
    public T getName()
    {
        return m__strName;
    }

    /**
     * Specifies the validation value.
     * @param validationValue the validation value.
     */
    protected final void immutableSetValidationValue(
        @NotNull final V validationValue)
    {
        m__strValidationValue = validationValue;
    }

    /**
     * Specifies the validation value.
     * @param validationValue the validation value.
     */
    @SuppressWarnings("unused")
    protected void setValidationValue(
        @NotNull final V validationValue)
    {
        immutableSetValidationValue(validationValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(@Nullable final Parameter<T, V> parameter)
    {
        return super.<IdentifiableElement<T>>compareTo(parameter);
    }

    /**
     * Retrieves the validation value.
     * @return such value.
     */
    @Override
    @Nullable
    public V getValidationValue()
    {
        return m__strValidationValue;
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    @Override
    @NotNull
    public String toString()
    {
        return
            new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(getName())
                .append(getValidationValue())
                .toString();
    }
}
