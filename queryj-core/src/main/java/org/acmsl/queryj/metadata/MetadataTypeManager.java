/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Filename: MetadataTypeManager.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides the translation and management services related
 *              to database attribute types.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Manager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides some useful methods when working with database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface MetadataTypeManager
    extends  Manager
{
    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
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
     * @param allowsNull whether to allow null or not.
     * @param isBool whether the attribute is marked as boolean.
     * @return the associated native type.
     */
    public String getNativeType(
        final int dataType, final boolean allowsNull, final boolean isBool);

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @param allowsNull whether to allow null or not.
     * @param isBool whether the attribute is marked as boolean.
     * @param precision the precision.
     * @return the associated native type.
     */
    @SuppressWarnings("unused")
    public String getNativeType(
        final int dataType,
        final boolean allowsNull,
        final boolean isBool,
        final int precision);

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @return the associated native type.
     */
    public int getJavaType(final String dataType);

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @param precision the precision.
     * @return the associated native type.
     */
    public int getJavaType(final String dataType, final int precision);

    /**
     * Retrieves the QueryJ type of given data type.
     * @param dataType the data type.
     * @param isBool whether the attribute is marked as boolean.
     * @return the QueryJ type.
     */
    public String getQueryJFieldType(final int dataType, final boolean isBool);

    /**
     * Retrieves the type of given data type.
     * @param dataType the data type.
     * @return the QueryJ type.
     */
    @NotNull
    public String getStatementSetterFieldType(final int dataType);

    /**
     * Retrieves the type of given data type.
     * @param dataType the data type.
     * @return the QueryJ type.
     */
    @NotNull
    public String getFieldType(final int dataType);

    /**
     * Retrieves the type of given data type.
     * @param dataType the data type.
     * @param allowsNull whether the field allows null or not.
     * @param isBool whether the attribute is marked as boolean.
     * @return the QueryJ type.
     */
    @NotNull
    public String getFieldType(
        final int dataType, final boolean allowsNull, final boolean isBool);

    /**
     * Retrieves the setter method name.
     * @param dataType the data type.
     * @param paramIndex the parameter index.
     * @param paramName the parameter name.
     * @return the associated setter method name.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getSetterMethod(
        final int dataType, final int paramIndex, final String paramName);

    /**
     * Retrieves the getter method name.
     * @param dataType the data type.
     * @param paramIndex the parameter index.
     * @return the associated getter method name.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getGetterMethod(final int dataType, final int paramIndex);

    /**
     * Retrieves the getter method name.
     * @param dataType the data type.
     * @return the associated getter method name.
     */
    @NotNull
    public String getGetterMethod(final int dataType);

    /**
     * Retrieves the getter method name.
     * @param dataType the data type.
     * @param param the parameter.
     * @return the associated getter method name.
     */
    @NotNull
    public String getGetterMethod(final int dataType, final String param);

    /**
     * Retrieves the result type.
     * @param dataType the data type.
     * @param isBool whether the attribute is marked as boolean.
     * @return the associated result type.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getProcedureResultType(final int dataType, final boolean isBool);

    /**
     * Retrieves the procedure's default value.
     * @param dataType the data type.
     * @param isBool whether the attribute is marked as boolean.
     * @return the associated default value.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getProcedureDefaultValue(final int dataType, final boolean isBool);

    /**
     * Retrieves the object type of given data type.
     * @param dataType the data type.
     * @param isBool whether the attribute is marked as boolean.
     * @return the associated object type.
     */
    @NotNull
    public String getObjectType(final int dataType, final boolean isBool);

    /**
     * Retrieves the object type of given data type.
     * @param dataType the data type.
     * @param isBool whether the attribute is marked as boolean.
     * @return the associated object type.
     */
    @NotNull
    public String getObjectType(final String dataType, final boolean isBool);

    /**
     * Retrieves the object type of given data type.
     * @param dataType the data type.
     * @param isBool whether the attribute is marked as boolean.
     * @return the associated object type.
     */
    @NotNull
    public String getSmartObjectType(final int dataType, final boolean isBool);

    /**
     * Retrieves the object type of given data type when retrieving information.
     * @param dataType the data type.
     * @param isBool whether the attribute is marked as boolean.
     * @return the associated object type.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getSmartObjectRetrievalType(final int dataType, final boolean isBool);

    /**
     * Retrieves the default value of given data type.
     * @param dataType the data type.
     * @param isBool whether the attribute is marked as boolean.
     * @return the associated default value.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getDefaultValue(final int dataType, final boolean isBool);

    /**
     * Retrieves the constant name of given data type.
     * @param dataType the data type.
     * @return the associated constant name.
     */
    @NotNull
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
    @SuppressWarnings("unused")
    public boolean isInteger(final int dataType);

    /**
     * Checks if given data type represents dates.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * date.
     */
    public boolean isDate(final int dataType);

    /**
     * Checks if given data type represents dates.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * date.
     */
    public boolean isDate(final String dataType);

    /**
     * Checks if given data type represents timestamps.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * timestamp.
     */
    public boolean isTimestamp(final int dataType);

    /**
     * Checks if given data type represents timestamps.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * timestamp.
     */
    @SuppressWarnings("unused")
    public boolean isTimestamp(final String dataType);

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
     * Checks if given data type represents primitives.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * primitive.
     */
    public boolean isPrimitive(final String dataType);

    /**
     * Checks if given data type represents primitive wrappers.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * primitive wrapper.
     */
    @SuppressWarnings("unused")
    public boolean isPrimitiveWrapper(final String dataType);

    /**
     * Checks if given data type represents clobs.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * clob.
     */
    public boolean isClob(final int dataType);

    /**
     * Checks if given data type represents clobs.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * clob.
     */
    public <T> boolean isClob(@NotNull final T dataType);

    /**
     * Checks if given data type represents blobs.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * blob.
     */
    public boolean isBlob(final int dataType);

    /**
     * Checks if given data type represents blobs.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * blob.
     */
    public boolean isBlob(@NotNull final String dataType);

    /**
     * Checks if given data type represents numbers smaller than int.
     * @param dataType the data type.
     * @return <code>true</code> is such data type is smallint, tinyint
     * or similar.
     */
    public boolean isNumberSmallerThanInt(final int dataType);

    /**
     * Checks if given data type represents numbers smaller than int.
     * @param dataType the data type.
     * @return <code>true</code> is such data type is smallint, tinyint
     * or similar.
     */
    public boolean isNumberSmallerThanInt(@NotNull final String dataType);

    /**
     * Checks if given data type represents numbers.
     * @param dataType the data type.
     * @return <code>true</code> in such case.
     */
    public boolean isNumeric(final int dataType);

    /**
     * Checks if given data type represents numbers.
     * @param dataType the data type.
     * @return <code>true</code> in such case.
     */
    public boolean isNumeric(@NotNull final String dataType);

    /**
     * Checks if given data type represents large objects of any kind.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * Lob.
     */
    @SuppressWarnings("unused")
    boolean isLob(final int dataType);

    /**
     * Checks if given data type represents large objects of any kind.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * Lob.
     */
    @SuppressWarnings("unused")
    boolean isLob(@NotNull final String dataType);

    /**
     * Retrieves the object type of given data type when retrieving information.
     * @param dataType the data type.
     * @param isBool whether the type represents boolean values.
     * @return the associated object type.
     */
    @NotNull
    public String getFullyQualifiedType(final int dataType, final boolean isBool);

    /**
     * Checks whether given type belongs to <code>java.lang</code> package or not.
     * @param type the type.
     * @return <code>true</code> in such case.
     */
    boolean inJavaLang(@NotNull final String type);

    /**
     * Retrieves the import type of given data type.
     * @param dataType the data type.
     * @return the associated type for using in import statements.
     */
    @Nullable
    public String getImport(final int dataType);

    /**
     * Retrieves the JDBC type.
     * @param type the type.
     * @param length the length.
     * @param precision the precision.
     * @return the associated {@link java.sql.Types} constant.
     */
    public int toJdbcType(@NotNull final String type, final int length, final int precision);

}
