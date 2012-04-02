//;-*- mode: java -*-
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

 *****************************************************************************
 *
 * Filename: AbstractAttributeDecorator.java
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

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Decorates <code>Attribute</code> instances to provide required alternate
 * representations of the information stored therein.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractAttributeDecorator
    extends  AbstractAttribute
    implements  AttributeDecorator
{
    /**
     * The decorated attribute.
     */
    private Attribute m__Attribute;

    /**
     * The metadata type manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * The metadata type manager.
     */
    private MetadataTypeManager m__MetadataTypeManager;

    /**
     * Creates an <code>AttributeDecorator</code> with the
     * <code>Attribute</code> to decorate.
     * @param attribute the attribute.
     * @param metadataManager the metadata manager.
     * @precondition attribute != null
     * @precondition metadataManager != null
     */
    public AbstractAttributeDecorator(
        @NotNull final Attribute attribute, @NotNull final MetadataManager metadataManager)
    {
        this(
            attribute.getName(),
            attribute.getType(),
            attribute.getNativeType(),
            attribute.getFieldType(),
            attribute.getTableName(),
            attribute.getManagedExternally(),
            attribute.getAllowsNull(),
            attribute.getValue(),
            attribute,
            metadataManager,
            metadataManager.getMetadataTypeManager());
    }

    /**
     * Creates an <code>AttributeDecorator</code> with the following
     * information.
     * @param name the name.
     * @param type the type.
     * @param nativeType the native type.
     * @param fieldType the field type.
     * @param tableName the table name.
     * @param managedExternally whether the attribute is managed externally.
     * @param allowsNull whether the attribute allows null values or not.
     * @param value the optional attribute value.
     * @param attribute the attribute.
     * @param metadataManager the metadata manager.
     * @param metadataTypeManager the metadata type manager.
     * @precondition name != null
     * @precondition type != null
     * @precondition nativeType != null
     * @precondition fieldType != null
     * @precondition tableName != null
     * @precondition attribute != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    public AbstractAttributeDecorator(
        final String name,
        final int type,
        final String nativeType,
        final String fieldType,
        final String tableName,
        final boolean managedExternally,
        final boolean allowsNull,
        final String value,
        final Attribute attribute,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager)
    {
        super(
            name,
            type,
            nativeType,
            fieldType,
            tableName,
            managedExternally,
            allowsNull,
            value);

        immutableSetAttribute(attribute);
        immutableSetMetadataManager(metadataManager);
        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Specifies the attribute to decorate.
     * @param attribute such attribute.
     */
    protected final void immutableSetAttribute(final Attribute attribute)
    {
        m__Attribute = attribute;
    }

    /**
     * Specifies the attribute to decorate.
     * @param attribute such attribute.
     */
    protected void setAttribute(final Attribute attribute)
    {
        immutableSetAttribute(attribute);
    }

    /**
     * Retrieves the decorated attribute.
     * @return such attribute.
     */
    public Attribute getAttribute()
    {
        return m__Attribute;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected final void immutableSetMetadataManager(
        final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected void setMetadataManager(
        final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such instance.
     */
    protected MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Specifies the metadata type manager.
     * @param metadataTypeManager such instance.
     */
    protected final void immutableSetMetadataTypeManager(
        final MetadataTypeManager metadataTypeManager)
    {
        m__MetadataTypeManager = metadataTypeManager;
    }

    /**
     * Specifies the metadata type manager.
     * @param metadataTypeManager such instance.
     */
    protected void setMetadataTypeManager(
        final MetadataTypeManager metadataTypeManager)
    {
        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Retrieves the metadata type manager.
     * @return such instance.
     */
    protected MetadataTypeManager getMetadataTypeManager()
    {
        return m__MetadataTypeManager;
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
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(value.toLowerCase());
    }

    /**
     * Retrieves the uncapitalized name.
     * @return such name.
     */
    public String getNameUncapitalized()
    {
        return uncapitalize(getName(), DecorationUtils.getInstance());
    }

    /**
     * Uncapitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String uncapitalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.uncapitalize(value.toLowerCase());
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
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.upperCase(value);
    }

    /**
     * Normalizes given value to lower-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalizeLowercase(
        final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalizeLowercase(value);
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
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
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
    @Nullable
    public String getGetterMethod()
    {
        return getGetterMethod(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves the attribute's associated getter method.
     * @param type the attribute type.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     * @precondition metadataTypeManager != null
     */
    @Nullable
    protected String getGetterMethod(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getGetterMethod(type);
    }

    /**
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @return <code>false</code> if no primitive matches.
     */
    public Boolean isPrimitive()
    {
        return isPrimitive(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @param type the attribute type.
     * @param metadataTypeManager the metadata type manager.
     * @return <code>false</code> if no primitive matches.
     * @precondition metadataTypeManager != null
     */
    protected Boolean isPrimitive(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            (metadataTypeManager.isPrimitive(type)
             ?  Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Retrieves the object type.
     * @return such information.
     */
    @Nullable
    public String getObjectType()
    {
        return getObjectType(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves the attribute's object type.
     * @param type the attribute type.
     * @param metadataTypeManager the metadata type manager.
     * @return such type.
     * @precondition metadataTypeManager != null
     */
    @Nullable
    protected String getObjectType(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getSmartObjectType(type);
    }

    /**
     * Retrieves whether the attribute is a clob or not.
     * return such information.
     */
    public boolean isClob()
    {
        return isClob(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the attribute is a clob or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * return such information.
     * @precondition metadataTypeManager != null
     */
    protected boolean isClob(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isClob(type);
    }

    /**
     * Retrieves whether the attribute is a string or not.
     * return such information.
     */
    public boolean isString()
    {
        return isString(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the attribute is a string or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * return such information.
     * @precondition metadataTypeManager != null
     */
    protected boolean isString(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isString(type);
    }

    /**
     * Retrieves whether the attribute is a date or not.
     * return such information.
     */
    public boolean isDate()
    {
        return isDate(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the attribute is a date or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * return such information.
     * @precondition metadataTypeManager != null
     */
    protected boolean isDate(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isDate(type);
    }

    /**
     * Retrieves the query to retrieve the externally-managed value.
     * @return such information.
     */
    @Nullable
    public String getQuery()
    {
        return getQuery(getManagedExternally());
    }

    /**
     * Retrieves the query to retrieve the externally-managed value.
     * @param managedExternally whether the attribute is managed externally.
     * @return such information.
     */
    @Nullable
    protected String getQuery(final boolean managedExternally)
    {
        @Nullable String result = "";

        if  (managedExternally)
        {
            result =
                getQuery(getName(), getTableName(), getMetadataManager());
        }

        return result;
    }

    /**
     * Retrieves the query to retrieve the externally-managed value.
     * @param name the field name.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition name != null
     * @precondition tableName != null
     * @precondition metadataManager != null
     */
    @Nullable
    protected String getQuery(
        final String name,
        final String tableName,
        @NotNull final MetadataManager metadataManager)
    {
        return
            metadataManager.getExternallyManagedFieldRetrievalQuery(
                tableName, name);
    }

    /**
     * Retrieves the QueryJ type.
     * @return the QueryJ type.
     */
    public String getQueryJFieldType()
    {
        return getQueryJFieldType(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves the QueryJ type.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return the QueryJ type.
     * @precondition metadataTypeManager != null
     */
    protected String getQueryJFieldType(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getQueryJFieldType(type);
    }

    /**
     * Retrieves the QueryJ type for statement setters.
     * @return the QueryJ type.
     */
    @Nullable
    public String getStatementSetterFieldType()
    {
        return
            getStatementSetterFieldType(
                getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves the QueryJ type for statement setters.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return the QueryJ type.
     * @precondition metadataTypeManager != null
     */
    @Nullable
    protected String getStatementSetterFieldType(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getStatementSetterFieldType(type);
    }

    /**
     * Retrieves the attribute's table in upper-case.
     * @return such information.
     */
    public String getTableNameUppercased()
    {
        return upperCase(getTableName(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the attribute's table in upper-case.
     * @return such information.
     */
    public String getTableNameNormalizedLowercased()
    {
        return
            normalizeLowercase(getTableName(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves whether the type means the attribute is a
     * number smaller than an int.
     * @return such condition.
     */
    public boolean isNumberSmallerThanInt()
    {
        return isNumberSmallerThanInt(getType(), getMetadataManager());
    }

    /**
     * Retrieves whether the type means the attribute is a
     * number smaller than an int.
     * @param type the type.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such condition.
     * @precondition metadataManager != null
     */
    protected boolean isNumberSmallerThanInt(
        final int type, @NotNull final MetadataManager metadataManager)
    {
        return
            isNumberSmallerThanInt(
                type, metadataManager.getMetadataTypeManager());
    }

    /**
     * Retrieves whether the type means the attribute is a
     * number smaller than an int.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such condition.
     * @precondition metadataTypeManager != null
     */
    protected boolean isNumberSmallerThanInt(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isNumberSmallerThanInt(type);
    }

    /**
     * Retrieves the Java type of the attribute.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return such information.
     * @precondition type != null
     * @precondition metadataTypeManager != null
     */
    @Nullable
    public String getJavaType()
    {
        return getJavaType(getType(), getMetadataTypeManager());
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
    @Nullable
    protected String getJavaType(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getObjectType(type);
    }

    /**
     * Retrieves the Java type of the attribute, which would be
     * only a primitive Java type if the attribute type matches,
     * and the column allows nulls.
     * @return such information.
     */
    @Nullable
    protected String retrieveJavaType()
    {
        return
            retrieveJavaType(getType(), getMetadataManager(), getAllowsNull());
    }

    /**
     * Retrieves the Java type of the attribute, which would be
     * only a primitive Java type if the attribute type matches,
     * and the column allows nulls.
     * @param type the type.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param allowsNull whether the attribute allows null.
     * @return such information.
     * @precondition metadataManager != null
     */
    @Nullable
    protected String retrieveJavaType(
        final int type,
        @NotNull final MetadataManager metadataManager,
        final boolean allowsNull)
    {
        return
            retrieveJavaType(
                type,
                metadataManager.getMetadataTypeManager(),
                allowsNull,
                MetadataTypeUtils.getInstance());
    }

    /**
     * Retrieves the Java type of the attribute, which would be
     * only a primitive Java type if the attribute type matches,
     * and the column allows nulls.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param allowsNull whether the attribute allows null.
     * @param metadataTypeUtils the <code>MetadataTypeUtils</code> instance.
     * @return such information.
     * @precondition metadataTypeManager != null
     * @precondition metadataTypeUtils != null
     */
    @Nullable
    protected String retrieveJavaType(
        final int type,
        @NotNull final MetadataTypeManager metadataTypeManager,
        final boolean allowsNull,
        @NotNull final MetadataTypeUtils metadataTypeUtils)
    {
        @Nullable String result = metadataTypeManager.getNativeType(type);

        if  (allowsNull)
        {
            result = metadataTypeUtils.getWrapperClass(result);
        }

        return result;
    }

    /**
     * Retrieves the attribute name.
     * @return such information.
     */
    @NotNull
    public String toString()
    {
        return "" + getAttribute();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    public final int hashCode()
    {
        return hashCode(getAttribute());
    }

    /**
     * Retrieves the hash code associated to given attribute.
     * @param attribute the attribute.
     * @return such information.
     * @precondition attribute != null
     */
    protected final int hashCode(@NotNull final Attribute attribute)
    {
        return attribute.hashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    public boolean equals(final Object object)
    {
        return equals(getAttribute(), object);
    }

    /**
     * Checks whether given object is semantically equal to given attribute.
     * @param attribute the decorated attribute.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @precondition attribute != null
     */
    protected boolean equals(@NotNull final Attribute attribute, final Object object)
    {
        return attribute.equals(object);
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    public int compareTo(final Object object)
        throws  ClassCastException
    {
        return compareTo(getAttribute(), object);
    }

    /**
     * Compares given object with this instance.
     * @param attribute the decorated attribute.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     * @precondition attribute != null
     */
    protected int compareTo(@NotNull final Attribute attribute, final Object object)
        throws  ClassCastException
    {
        return attribute.compareTo(object);
    }
}
