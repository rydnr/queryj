/*
                        QueryJ Test

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
 * Filename: BeanElementFactory.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents <bean> tags in XML files.
 *
 * Date: 5/25/13
 * Time: 8:25 PM
 *
 */
package org.acmsl.queryj.test.xml;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Represents &lt;bean&gt; information in XML files.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/05/25
 */
public class BeanElement
{
    /**
     * The id.
     */
    private String m__strId;

    /**
     * The Class.
     */
    private String m__strClass;

    /**
     * The properties.
     */
    private List<PropertyElement> m__lProperties;

    /**
     * Creates a new BeanElement.
     * @param id the id.
     * @param clazz the class value.
     */
    public BeanElement(@NotNull final String id, @NotNull final String clazz)
    {
        immutableSetId(id);
        immutableSetClassValue(clazz);
        immutableSetProperties(new ArrayList<PropertyElement>(1));
    }

    /**
     * Specifies the id.
     * @param value the id value.
     */
    protected final void immutableSetId(@NotNull final String value)
    {
        this.m__strId = value;
    }

    /**
     * Specifies the id.
     * @param value the id value.
     */
    @SuppressWarnings("unused")
    protected void setId(@NotNull final String value)
    {
        immutableSetId(value);
    }

    /**
     * Retrieves the id.
     * @return such value.
     */
    @NotNull
    public String getId()
    {
        return this.m__strId;
    }

    /**
     * Specifies the Class.
     * @param value the Class value.
     */
    protected final void immutableSetClassValue(@NotNull final String value)
    {
        this.m__strClass = value;
    }

    /**
     * Specifies the Class.
     * @param value the Class value.
     */
    @SuppressWarnings("unused")
    protected void setClassValue(@NotNull final String value)
    {
        immutableSetClassValue(value);
    }

    /**
     * Retrieves the Class.
     * @return such value.
     */
    @NotNull
    public String getClassValue()
    {
        return this.m__strClass;
    }

    /**
     * Specifies the properties.
     * @param properties the properties.
     */
    protected final void immutableSetProperties(@NotNull final List<PropertyElement> properties)
    {
        this.m__lProperties = properties;
    }

    /**
     * Specifies the properties.
     * @param properties the properties.
     */
    @SuppressWarnings("unused")
    protected void setProperties(@NotNull final List<PropertyElement> properties)
    {
        immutableSetProperties(properties);
    }

    /**
     * Retrieves the properties.
     * @return such information.
     */
    @NotNull
    protected final List<PropertyElement> immutableGetProperties()
    {
        return this.m__lProperties;
    }

    /**
     * Retrieves the properties.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<PropertyElement> getProperties()
    {
        return new ArrayList<PropertyElement>(immutableGetProperties());
    }

    /**
     * Adds a new property.
     * @param property the property.
     */
    public void add(@NotNull final PropertyElement property)
    {
        immutableGetProperties().add(property);
    }

    @Override
    public boolean equals(@Nullable final Object o)
    {
        boolean result = false;

        if (this == o)
        {
            result = true;
        }
        else if (o instanceof BeanElement)
        {
            @NotNull final BeanElement that = (BeanElement) o;

            if (   (immutableGetProperties().equals(that.immutableGetProperties()))
                   && (getClassValue().equals(that.getClassValue()))
                   && (getId().equals(that.getId())))
            {
                result = true;
            }
        }

        return result;
    }

    @Override
    public int hashCode()
    {
        int result = getId().hashCode();
        result = 31 * result + getClassValue().hashCode();
        result = 31 * result + immutableGetProperties().hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return
              "BeanElement{"
            + " id='" + getId()
            + "', class='" + getClassValue() + "'"
            + "', properties=" + immutableGetProperties()
            + "'}";
    }
}
