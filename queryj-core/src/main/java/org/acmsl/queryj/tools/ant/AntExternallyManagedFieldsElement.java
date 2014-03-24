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
 * Filename: AntExternallyManagedFieldsElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains all information regarding the declaration of
 *              externally managed fields in QueryJ task.
 *
 */
package org.acmsl.queryj.tools.ant;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicConfigurator;

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
 * Contains all information regarding the declaration of
 * externally managed fields in QueryJ task.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class AntExternallyManagedFieldsElement
    implements  DynamicConfigurator
{
    /**
     * String literal: "field".
     */
    public static final String FIELD_LITERAL = "field";

    /**
     * The field collection.
     */
    private Collection<AntFieldElement> m__cFields;

    /**
     * Specifies the field collection.
     * @param fields the collection
     */
    private void immutableSetFields(@NotNull final Collection<AntFieldElement> fields)
    {
        m__cFields = fields;
    }

    /**
     * Specifies the field collection.
     * @param fields the collection
     */
    private void setFields(@NotNull final Collection<AntFieldElement> fields)
    {
        immutableSetFields(fields);
    }

    /**
     * Retrieves the field collection.
     * @return such collection.
     */
    @Nullable
    protected final Collection<AntFieldElement> immutableGetFields()
    {
        return m__cFields;
    }

    /**
     * Retrieves the field collection.
     * @return such collection.
     */
    @NotNull
    public Collection<AntFieldElement> getFields()
    {
        @Nullable final Collection<AntFieldElement> result;

        @Nullable final Collection<AntFieldElement> t_cFields = immutableGetFields();

        if (t_cFields == null)
        {
            result = new ArrayList<>();
            setFields(result);
        }
        else
        {
            result = t_cFields;
        }

        return result;
    }

    /**
     * Specifies a dynamic attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     */
    @Override
    public void setDynamicAttribute(@NotNull final String name, @NotNull final String value)
    {
        throw new BuildException("Attributes are not supported");
    }

    /**
     * Creates a dynamic element.
     * @param name the element's name.
     * @return the object.
     */
    @Nullable
    @Override
    public Object createDynamicElement(@NotNull final String name)
    {
        @Nullable final AntFieldElement result;

        if  (FIELD_LITERAL.equals(name))
        {
            result = new AntFieldElement();

            @NotNull final Collection<AntFieldElement> t_cFields = getFields();

            t_cFields.add(result);
        }
        else 
        {
            throw new BuildException(name + QueryJTask.ELEMENTS_ARE_NOT_SUPPORTED);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return "AntExternallyManagedFieldsElement{ fields=" + m__cFields + " }";
    }
}
