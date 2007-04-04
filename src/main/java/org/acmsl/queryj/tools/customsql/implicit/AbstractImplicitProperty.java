/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: AbstractImplicitProperty.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents common stuff in implicit properties.
 *
 */
package org.acmsl.queryj.tools.customsql.implicit;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.AbstractParameterElement;
import org.acmsl.queryj.tools.customsql.Property;

/**
 * Represents common stuff in implicit properties.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractImplicitProperty
    extends  AbstractParameterElement
    implements  Property
{
    /**
     * Whether the property allows nulls or not.
     */
    private boolean m__bNullable;
    
    /**
     * Creates an <code>AbstractImplicitProperty</code> with given information.
     * @param id the <i>id</i> attribute.
     * @param type the <i>type</i> attribute.
     * @param nullable the <i>nullable</i> attribute.
     * @precondition id != null
     * @precondition type != null
     */
    public AbstractImplicitProperty(
        final String id, final String type, final boolean nullable)
    {
        super(id, null, 1, null, type);
        immutableSetNullable(nullable);
    }

    /**
     * Specifies whether the property is nullable or not.
     * @param flag such condition.
     */
    protected final void immutableSetNullable(final boolean flag)
    {
        m__bNullable = flag;
    }
    
    /**
     * Specifies whether the property is nullable or not.
     * @param flag such condition.
     */
    protected void setNullable(final boolean flag)
    {
        immutableSetNullable(flag);
    }
    
    /**
     * Retrieves ehether the property is nullable or not.
     * @return such condition.
     */
    public boolean isNullable()
    {
        return m__bNullable;
    }

    /**
     * Retrieves whether the attribute is marked as read-only.
     * @return such condition.
     */
    public boolean isReadOnly()
    {
        return false;
    }

    /**
     * Retrieves whether the attribute is marked as boolean.
     * @return such condition.
     */
    public boolean isBoolean()
    {
        return false;
    }

    /**
     * Retrieves the symbol for <code>true</code> values.
     * @return such information.
     */
    public String getBooleanTrue()
    {
        return null;
    }
    
    /**
     * Retrieves the symbol for <code>false</code> values.
     * @return such information.
     */
    public String getBooleanFalse()
    {
        return null;
    }

    /**
     * Retrieves the symbol for <code>null</code> values.
     * @return such information.
     */
    public String getBooleanNull()
    {
        return null;
    }

    /**
     * Checks whether the property is implicit or not.
     * @return such condition.
     */
    public boolean isImplicit()
    {
        return true;
    }
}
