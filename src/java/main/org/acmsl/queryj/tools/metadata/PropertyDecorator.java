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

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates <property> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.metadata.engines.JdbcMetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;

/**
 * Decorates &lt;property&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class PropertyDecorator
    extends  PropertyElement
{
    /**
     * Creates a PropertyDecorator to decorate given property..
     * @param property the property to decorate.
     * @precondition property != null
     */
    public PropertyDecorator(final PropertyElement property)
    {
        super(
            property.getId(),
            property.getColumnName(),
            property.getIndex(),
            property.getName(),
            property.getType(),
            property.isNullable());
    }

    /**
     * Retrieves the Java type of the property.
     * @return such information.
     */
    public String getJavaType()
    {
        return getJavaType(getType(), JdbcMetadataTypeManager.getInstance());
    }
    
    /**
     * Retrieves the Java type of the property.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return such information.
     * @precondition type != null
     * @precondition metadataTypeManager != null
     */
    protected String getJavaType(
        final String type, final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.getObjectType(
                metadataTypeManager.getJavaType(type));
    }

    /**
     * Retrieves the name, in lower case.
     * @return such information.
     */
    public String getNameLowercased()
    {
        return lowercase(getName());
    }

    /**
     * Lowers the case of given value.
     * @param value the value.
     * @return the value, after being processed.
     * @precondition value != null
     */
    protected String lowercase(final String value)
    {
        return value.toLowerCase();
    }
}
