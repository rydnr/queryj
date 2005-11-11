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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates 'Attribute' instances to provide required
 *              alternate representations of the information stored therein.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.vo.AbstractAttribute;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.MetaDataUtils;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;

/**
 * Decorates 'Attribute' instances to provide required alternate
 * representations of the information stored therein.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class AttributeDecorator
    extends AbstractAttribute
{
    /**
     * Creates an <code>AttributeDecorator</code> with the
     * <code>Attribute</code> to decorate.
     * @param attribute the attribute.
     * @precondition attribute != null
     */
    public AttributeDecorator(final Attribute attribute)
    {
        this(
            attribute.getName(),
            attribute.getType(),
            attribute.getNativeType(),
            attribute.getFieldType(),
            attribute.getTableName(),
            attribute.getManagedExternally(),
            attribute.getAllowsNull());
    }

    /**
     * Creates an <code>AttributeValueObject</code> with the following
     * information.
     * @param name the name.
     * @param type the type.
     * @param nativeType the native type.
     * @param fieldType the field type.
     * @param tableName the table name.
     * @param managedExternally whether the attribute is managed externally.
     * @param allowsNull whether the attribute allows null values or not.
     * @precondition name != null
     * @precondition type != null
     * @precondition nativeType != null
     * @precondition fieldType != null
     * @precondition tableName != null
     */
    public AttributeDecorator(
        final String name,
        final int type,
        final String nativeType,
        final String fieldType,
        final String tableName,
        final boolean managedExternally,
        final boolean allowsNull)
    {
        super(
            name,
            type,
            nativeType,
            fieldType,
            tableName,
            managedExternally,
            allowsNull);
    }

    /**
     * Retrieves the name, in upper case.
     * @return such value.
     */
    public String getNameUppercased()
    {
        return upperCase(getName(), DecorationUtils.getInstance());
    }
    
    /**
     * Retrieves the capitalized name.
     * @return such name.
     */
    public String getNameCapitalized()
    {
        return capitalize(getName(), DecorationUtils.getInstance());
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String capitalize(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(value);
    }
    
    /**
     * Converts given value to upper-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String upperCase(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.upperCase(value);
    }
    
    /**
     * Retrieves the name, in lower case.
     * @return such value.
     */
    public String getNameLowercased()
    {
        return lowerCase(getName(), DecorationUtils.getInstance());
    }
    
    /**
     * Converts given value to lower-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String lowerCase(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.lowerCase(value);
    }

    /**
     * Retrieves the table name, uncapitalized.
     * @return such value.
     */
    public String getUncapitalizedTableName()
    {
        return uncapitalize(getTableName(), DecorationUtils.getInstance());
    }

    /**
     * Uncapitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the modified version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String uncapitalize(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.uncapitalize(value);
        
    }

    /**
     * Retrieves the value-object name associated to the table name.
     * @return such name.
     */
    public String getVoName()
    {
        return capitalize(getTableName(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the attribute's Java name.
     * @return such information.
     */
    public String getJavaName()
    {
        return upperCase(getName(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the attribute's associated getter method.
     * @return such information.
     */
    public String getGetterMethod()
    {
        return getGetterMethod(getType(), MetaDataUtils.getInstance());
    }

    /**
     * Retrieves the attribute's associated getter method.
     * @param type the attribute type.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return such information.
     * @precondition metaDataUtils != null
     */
    protected String getGetterMethod(
        final int type, final MetaDataUtils metaDataUtils)
    {
        return metaDataUtils.getGetterMethod(type);
    }

    /**
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @return <code>false</code> if no primitive matches.
     */
    public boolean isObject()
    {
        return isObject(getType(), MetaDataUtils.getInstance());
    }

    /**
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @param type the attribute type.
     * @param metaDataUtils the <code>MetaDataUtils</code> instance.
     * @return <code>false</code> if no primitive matches.
     * @precondition metaDataUtils != null
     */
    protected boolean isObject(
        final int type, final MetaDataUtils metaDataUtils)
    {
        return metaDataUtils.isObject(type);
    }
}

