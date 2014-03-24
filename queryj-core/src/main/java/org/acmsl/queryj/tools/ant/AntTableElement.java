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
 * Filename: AntTableElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains all information inside a "table" XML element in Ant scripts,
 *              under QueryJ task.
 *
 */
package org.acmsl.queryj.tools.ant;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicConfigurator;

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

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Contains all information inside a "table" XML element in Ant scripts,
 * under QueryJ task.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class AntTableElement
    implements  DynamicConfigurator
{
    /**
     * The table name.
     * @parameter property="name"
     */
    private String m__strTableName;

    /**
     * The field collection.
     * @parameter property="fields"
     */
    private List<AntFieldElement> m__lFields;

    /**
     * Specifies the table name.
     * @param name the table name.
     */
    protected final void immutableSetName(@NotNull final String name)
    {
        m__strTableName = name;
    }

    /**
     * Specifies the table name.
     * @param name the table name.
     */
    public void setName(@NotNull final String name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the table name.
     * @return such name.
     */
    @Nullable
    public String getName()
    {
        return m__strTableName;
    }

    /**
     * Specifies the field collection.
     * @param fields the collection
     */
    protected final void immutableSetFields(@NotNull final List<AntFieldElement> fields)
    {
        m__lFields = fields;
    }

    /**
     * Specifies the field collection.
     * @param fields the collection
     */
    public void setFields(@NotNull final List<AntFieldElement> fields)
    {
        immutableSetFields(fields);
    }

    /**
     * Retrieves the field collection.
     * @return such collection.
     */
    @Nullable
    public List<AntFieldElement> getFields()
    {
        return m__lFields;
    }

    /**
     * Specifies a dynamic attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     */
    @Override
    public void setDynamicAttribute(@NotNull final String name, @NotNull final String value)
    {
        if  ("name".equals(name))
        {
            setName(value);
        }
        else 
        {
            throw new BuildException("Attribute are supported");
        }
    }

    /**
     * Creates a dynamic element.
     * @param name the element's name.
     * @return the object.
     */
    @Override
    @Nullable
    public Object createDynamicElement(@NotNull final String name)
    {
        @Nullable final AntFieldElement result;

        if  (AntExternallyManagedFieldsElement.FIELD_LITERAL.equals(name))
        {
            result = new AntFieldElement();

            List<AntFieldElement> t_lFields = getFields();

            if  (t_lFields == null)
            {
                t_lFields = new ArrayList<>();
                setFields(t_lFields);
            }

            t_lFields.add(result);
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
        return "{ 'class': 'AntTableElement', 'fields': '" + m__lFields +
               "', 'tableName': '" + m__strTableName + "' }";
    }
}
