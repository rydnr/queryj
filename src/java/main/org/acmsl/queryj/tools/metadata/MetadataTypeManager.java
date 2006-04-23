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
    Contact info: jose.sanleandro@acm-sl.com
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides the translation and management services related
 *              to database attribute types.
 *
 */
package org.acmsl.queryj.tools.metadata;

/**
 * Provides some useful methods when working with database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface MetadataTypeManager
{
    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @param allowsNull whether to allow null or not.
     * @return the associated native type.
     */
    public String getNativeType(final int dataType);

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @param allowsNull whether to allow null or not.
     * @return the associated native type.
     */
    public String getNativeType(
        final int dataType, final boolean allowsNull);

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @return the associated native type.
     */
    public int getJavaType(final String dataType);

    /**
     * Retrieves the QueryJ type of given data type.
     * @param dataType the data type.
     * @return the QueryJ type.
     */
    public String getQueryJFieldType(final int dataType);

    /**
     * Retrieves the type of given data type.
     * @param dataType the data type.
     * @return the QueryJ type.
     */
    public String getStatementSetterFieldType(final int dataType);

    /**
     * Retrieves the type of given data type.
     * @param dataType the data type.
     * @return the QueryJ type.
     */
    public String getFieldType(final int dataType);

    /**
     * Retrieves the type of given data type.
     * @param dataType the data type.
     * @param allowsNull whether the field allows null or not.
     * @return the QueryJ type.
     */
    public String getFieldType(final int dataType, final boolean allowsNull);

    /**
     * Retrieves the setter method name.
     * @param dataType the data type.
     * @param paramIndex the parameter index.
     * @param paramName the parameter name.
     * @return the associated setter method name.
     */
    public String getSetterMethod(
        final int dataType, final int paramIndex, final String paramName);

    /**
     * Retrieves the getter method name.
     * @param dataType the data type.
     * @param paramIndex the parameter index.
     * @return the associated getter method name.
     */
    public String getGetterMethod(final int dataType, final int paramIndex);

    /**
     * Retrieves the getter method name.
     * @param dataType the data type.
     * @param paramIndex the parameter index.
     * @return the associated getter method name.
     */
    public String getGetterMethod(final int dataType);

    /**
     * Retrieves the getter method name.
     * @param dataType the data type.
     * @param param the parameter.
     * @return the associated getter method name.
     */
    public String getGetterMethod(final int dataType, final String param);

    /**
     * Retrieves the result type.
     * @param dataType the data type.
     * @return the associated result type.
     */
    public String getProcedureResultType(final int dataType);

    /**
     * Retrieves the procedure's default value.
     * @param dataType the data type.
     * @return the associated default value.
     */
    public String getProcedureDefaultValue(final int dataType);

    /**
     * Retrieves the object type of given data type.
     * @param dataType the data type.
     * @return the associated object type.
     */
    public String getObjectType(final int dataType);

    /**
     * Retrieves the object type of given data type.
     * @param dataType the data type.
     * @return the associated object type.
     */
    public String getSmartObjectType(final int dataType);

    /**
     * Retrieves the object type of given data type when retrieving information.
     * @param dataType the data type.
     * @return the associated object type.
     */
    public String getSmartObjectRetrievalType(final int dataType);

    /**
     * Retrieves the default value of given data type.
     * @param dataType the data type.
     * @return the associated default value.
     */
    public String getDefaultValue(final int dataType);

    /**
     * Retrieves the constant name of given data type.
     * @param dataType the data type.
     * @return the associated constant name.
     */
    public String getConstantName(final int dataType);

    /**
     * Checks if given data type refers to a String.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * String.
     */
    public boolean isString(final int dataType);

    /**
     * Checks if given data type represents integers.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as an
     * integer.
     */
    public boolean isInteger(final int dataType);

    /**
     * Checks if given data type represents dates.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * date.
     */
    public boolean isDate(final int dataType);

    /**
     * Checks if given data type represents timestamps.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * timestamp.
     */
    public boolean isTimestamp(final int dataType);

    /**
     * Checks if given data type represents objects.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as an
     * object.
     */
    public boolean isObject(int dataType);

    /**
     * Checks if given data type represents booleans.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * boolean.
     */
    public boolean isBoolean(final int dataType);

    /**
     * Checks if given data type represents primitives.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * primitive.
     */
    public boolean isPrimitive(final int dataType);

    /**
     * Checks if given data type represents primitive wrappers.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * primitive wrapper.
     */
    public boolean isPrimitiveWrapper(final String dataType);

    /**
     * Checks if given data type represents clobs.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * clob.
     */
    public boolean isClob(final int dataType);

    /**
     * Checks if given data type represents numbers smaller than int.
     * @param dataType the data type.
     * @return <code>true</code> is fuch data type is smallint, tinyint
     * or similar.
     */
    public boolean isNumberSmallerThanInt(final int dataType);
}
