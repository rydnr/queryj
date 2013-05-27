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
 * Filename: PropertyElement.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents <property> information in XML files.
 *
 * Date: 5/25/13
 * Time: 8:39 PM
 *
 */
package cucumber.templates.xml;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents &lt;property&gt; information in XML files.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/25
 */
public class PropertyElement
{
    /**
     * The name.
     */
    private String m__strName;

    /**
     * The value.
     */
    private String m__strValue;

    /**
     * Creates a PropertyElement with the following name.
     * @param name the name.
     */
    public PropertyElement(@NotNull final String name)
    {
        immutableSetName(name);
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    protected final void immutableSetName(@NotNull final String name)
    {
        this.m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    @SuppressWarnings("unused")
    protected void setName(@NotNull final String name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the name.
     * @return the name.
     */
    @NotNull
    public String getName()
    {
        return this.m__strName;
    }

    /**
     * Specifies the value.
     * @param value the value.
     */
    protected final void immutableSetValue(@NotNull final String value)
    {
        this.m__strValue = value;
    }

    /**
     * Specifies the value.
     * @param value the value.
     */
    @SuppressWarnings("unused")
    public void setValue(@NotNull final String value)
    {
        immutableSetValue(value);
    }

    /**
     * Retrieves the value.
     * @return the value.
     */
    @NotNull
    public String getValue()
    {
        return this.m__strValue;
    }

    @Override
    public boolean equals(final Object o)
    {
        boolean result = false;

        if (this == o)
        {
            result = true;
        }
        else if (o instanceof PropertyElement)
        {
            @NotNull final PropertyElement that = (PropertyElement) o;

            if (   (getName().equals(that.getName()))
                   && (getName().equals(that.getValue())))
            {
                result = true;
            }
        }

        return result;
    }

    @Override
    public int hashCode()
    {
        int result = getName().hashCode();
        @Nullable final String t_strValue = getValue();

        if (t_strValue != null)
        {
            result = 31 * result + t_strValue.hashCode();
        }

        return result;
    }

    @Override
    public String toString()
    {
        @NotNull final StringBuilder result = new StringBuilder("PropertyElement{name='");

        result.append(getName());
        @Nullable final String t_strValue = getValue();
        if (t_strValue != null)
        {
            result.append(", value='");
            result.append(t_strValue);
            result.append("'");
        }
        result.append("'}");

        return result.toString();
    }
}
