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
 * Description: Decorates <parameter> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.ParameterElement;
import org.acmsl.queryj.tools.MetaDataUtils;

/**
 * Decorates &lt;parameter&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ParameterDecorator
    extends  ParameterElement
{
    /**
     * Creates a <code>ParameterDecorator</code> with given parameter.
     * @param parameter the parameter to decorate.
     * @precondition parameter != null
     */
    public ParameterDecorator(final ParameterElement parameter)
    {
        super(
            parameter.getId(),
            parameter.getColumnName(),
            parameter.getIndex(),
            parameter.getName(),
            parameter.getType(),
            parameter.getValidationValue());
    }

    /**
     * Retrieves the sql type of the parameter.
     * @return such information.
     * @see java.sql.Types
     */
    public String getSqlType()
    {
        return getSqlType(getType(), MetaDataUtils.getInstance());
    }

    /**
     * Retrieves the sql type of the parameter.
     * @param type the type.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return such information.
     * @precondition metaDataUtils != null
     */
    protected String getSqlType(
        final String type, final MetaDataUtils metaDataUtils)
    {
        return metaDataUtils.getConstantName(metaDataUtils.getJavaType(type));
    }

    /**
     * Retrieves the object type of the parameter.
     * @return such information.
     */
    public String getObjectType()
    {
        return getObjectType(getType(), MetaDataUtils.getInstance());
    }

    /**
     * Retrieves the object type of the parameter.
     * @param type the type.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return such information.
     * @precondition metaDataUtils != null
     */
    public String getObjectType(
        final String type, final MetaDataUtils metaDataUtils)
    {
        return metaDataUtils.getObjectType(metaDataUtils.getJavaType(type));
    }

    /**
     * Retrieves the field type of the parameter.
     * @return such information.
     */
    public String getFieldType()
    {
        return getFieldType(getType(), MetaDataUtils.getInstance());
    }

    /**
     * Retrieves the field type of the parameter.
     * @param type the type.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return such information.
     * @precondition metaDataUtils != null
     */
    public String getFieldType(
        final String type, final MetaDataUtils metaDataUtils)
    {
        return metaDataUtils.getFieldType(metaDataUtils.getJavaType(type));
    }
}
