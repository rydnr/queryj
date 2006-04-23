//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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

 *****************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides the common logic for foreign key implementations.
 */
package org.acmsl.queryj.tools.metadata.vo;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.vo.Attribute;

/*
 * Importing JDK classes.
 */
import java.util.Collection;

/**
 * Provides the common logic for foreign key implementations.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractForeignKey
    implements  ForeignKey
{
    /**
     * The source table name.
     */
    private String m__strSourceTableName;
    
    /**
     * The attributes.
     */
    private Collection m__cAttributes;
    
    /**
     * The target table name.
     */
    private String m__strTargetTableName;
    
    /**
     * Whether the foreign key allows null values.
     */
    private boolean m__bAllowsNull;

    /**
     * Creates an <code>AbstractForeignKey</code> with given information.
     * @param sourceTableName the source table name.
     * @param attributes the attributes.
     * @param targetTableName the target table name.
     * @param allowsNull whether the foreign key can take null values.
     * @precondition sourceTableName the source table name.
     * @precondition attributes != null
     * @precondition targetTableName != null
     */
    protected AbstractForeignKey(
        final String sourceTableName,
        final Collection attributes,
        final String targetTableName,
        final boolean allowsNull)
    {
        immutableSetSourceTableName(sourceTableName);
        immutableSetAttributes(attributes);
        immutableSetTargetTableName(targetTableName);
        immutableSetAllowsNull(allowsNull);
    }

    /**
     * Specifies the source table name.
     * @param tableName such table name.
     */
    protected final void immutableSetSourceTableName(final String tableName)
    {
        m__strSourceTableName = tableName;
    }
    
    /**
     * Specifies the source table name.
     * @param tableName such table name.
     */
    protected void setSourceTableName(final String tableName)
    {
        immutableSetSourceTableName(tableName);
    }
    
    /**
     * Retrieves the source table name.
     * @return such table name.
     */
    public String getSourceTableName()
    {
        return m__strSourceTableName;
    }
    
    /**
     * Specifies the attributes.
     * @param attributes such attributes.
     */
    protected final void immutableSetAttributes(final Collection attributes)
    {
        m__cAttributes = attributes;
    }
    
    /**
     * Specifies the attributes.
     * @param attributes the attributes.
     */
    protected void setAttributes(final Collection attributes)
    {
        immutableSetAttributes(attributes);
    }
    
    /**
     * Retrieves the attributes.
     * @return such information.
     */
    public Collection getAttributes()
    {
        return m__cAttributes;
    }

    /**
     * Specifies the target table name.
     * @param tableName such table name.
     */
    protected final void immutableSetTargetTableName(final String tableName)
    {
        m__strTargetTableName = tableName;
    }
    
    /**
     * Specifies the target table name.
     * @param tableName such table name.
     */
    protected void setTargetTableName(final String tableName)
    {
        immutableSetTargetTableName(tableName);
    }
    
    /**
     * Retrieves the target table name.
     * @return such table name.
     */
    public String getTargetTableName()
    {
        return m__strTargetTableName;
    }

    /**
     * Specifies whether the foreign key can take null values.
     * @param allowsNull whether it allows null.
     */
    protected final void immutableSetAllowsNull(final boolean allowsNull)
    {
        m__bAllowsNull = allowsNull;
    }

    /**
     * Specifies whether the foreign key can take null values.
     * @param allowsNull whether it allows null.
     */
    protected void setAllowsNull(final boolean allowsNull)
    {
        immutableSetAllowsNull(allowsNull);
    }

    /**
     * Retrieves wheter the foreign key can take null values.
     * @return such information.
     */
    public boolean getAllowsNull()
    {
        return m__bAllowsNull;
    }
}
