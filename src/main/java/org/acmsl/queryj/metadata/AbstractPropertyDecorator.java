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
 * Filename: AbstractPropertyDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates <property> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Decorates &lt;property&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractPropertyDecorator
    extends  PropertyElement
    implements  PropertyDecorator
{
    /**
     * The property element.
     */
    private Property m__Property;

    /**
     * The metadata manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * The metadata type manager.
     */
    private MetadataTypeManager m__MetadataTypeManager;

    /**
     * Creates an <code>AbstractPropertyDecorator</code> to decorate given property.
     * @param property the property to decorate.
     * @precondition property != null
     * @precondition metadataManager != null
     */
    public AbstractPropertyDecorator(
        @NotNull final Property property, @NotNull final MetadataManager metadataManager)
    {
        super(
            property.getId(),
            property.getColumnName(),
            property.getIndex(),
            property.getName(),
            property.getType(),
            property.isNullable());

        immutableSetProperty(property);
        immutableSetMetadataManager(metadataManager);
        immutableSetMetadataTypeManager(
            metadataManager.getMetadataTypeManager());

        if  (property.getName() == null)
        {
            immutableSetName(property.getColumnName());
        }
    }

    /**
     * Specifies the <code>Property</code> to decorate.
     * @param property such property.
     */
    protected final void immutableSetProperty(final Property property)
    {
        m__Property = property;
    }

    /**
     * Specifies the <code>Property</code> to decorate.
     * @param property such property.
     */
    @SuppressWarnings("unused")
    protected void setProperty(@NotNull final Property property)
    {
        immutableSetProperty(property);
    }

    /**
     * Retrieves the decorated <code>Property</code> instance.
     * @return such property.
     */
    @NotNull
    public Property getProperty()
    {
        return m__Property;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected final void immutableSetMetadataManager(
        @NotNull final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    @SuppressWarnings("unused")
    protected void setMetadataManager(
        @NotNull final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such instance.
     */
    @NotNull
    protected MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Specifies the metadata type manager.
     * @param metadataTypeManager such instance.
     */
    protected final void immutableSetMetadataTypeManager(
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        m__MetadataTypeManager = metadataTypeManager;
    }

    /**
     * Specifies the metadata type manager.
     * @param metadataTypeManager such instance.
     */
    @SuppressWarnings("unused")
    protected void setMetadataTypeManager(
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Retrieves the metadata type manager.
     * @return such instance.
     */
    @NotNull
    protected MetadataTypeManager getMetadataTypeManager()
    {
        return m__MetadataTypeManager;
    }

    /**
     * Retrieves the Java type of the property.
     * @return such information.
     */
    @NotNull
    @Override
    public String getType()
    {
        return retrieveType(super.getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves the type of the property.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return such information.
     */
    @NotNull
    protected String retrieveType(
        @NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        int t_iJavaType = metadataTypeManager.getJavaType(type);

        // TODO: support boolean properties.
        return metadataTypeManager.getFieldType(t_iJavaType, isNullable(), false);

    }

    /**
     * Retrieves the Object type of the property.
     * @return such information.
     */
    @NotNull
    public String getObjectType()
    {
        return
            retrieveObjectType(
                getType(), getMetadataManager(), isNullable());
    }

    /**
     * Retrieves the Object type of the property.
     * @param type the declared type.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param allowsNull whether it allows nulls.
     * @return such information.
     */
    protected String retrieveObjectType(
        @NotNull final String type,
        @NotNull final MetadataManager metadataManager,
        final boolean allowsNull)
    {
        return
            retrieveObjectType(
                type,
                metadataManager.getMetadataTypeManager(),
                allowsNull,
                MetadataTypeUtils.getInstance());
    }

    /**
     * Retrieves the Object type of the property.
     * @param type the declared type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param allowsNull whether it allows nulls.
     * @param metadataTypeUtils the <code>MetadataTypeUtils</code> instance.
     * @return such information.
     */
    @SuppressWarnings("unused")
    protected String retrieveObjectType(
        @NotNull final String type,
        @NotNull final MetadataTypeManager metadataTypeManager,
        final boolean allowsNull,
        @NotNull final MetadataTypeUtils metadataTypeUtils)
    {
        return metadataTypeUtils.getWrapperClass(type);
    }

    /**
     * Retrieves the name, in lower case.
     * @return such information.
     */
    @NotNull
    public String getNameLowercased()
    {
        return lowercase(getName());
    }

    /**
     * Retrieves the name, in upper case.
     * @return such information.
     */
    @NotNull
    public String getNameUppercased()
    {
        return uppercase(getName());
    }

    /**
     * Retrieves the name, capitalized.
     * @return such information.
     */
    @NotNull
    public String getNameCapitalized()
    {
        return capitalize(getName());
    }

    /**
     * Lowers the case of given value.
     * @param value the value.
     * @return the value, after being processed.
     */
    @NotNull
    protected String lowercase(@NotNull final String value)
    {
        return value.toLowerCase(Locale.US);
    }

    /**
     * Uppers the case of given value.
     * @param value the value.
     * @return the value, after being processed.
     */
    @NotNull
    protected String uppercase(@NotNull final String value)
    {
        return value.toUpperCase(Locale.US);
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @return the alternate version of the value.
     */
    @NotNull
    protected String capitalize(@NotNull final String value)
    {
        return capitalize(value, DecorationUtils.getInstance());
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     */
    @NotNull
    protected String capitalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(value);
    }

    /**
     * Normalizes and capitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     */
    @NotNull
    protected String normalizeCapitalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(decorationUtils.normalize(value));
    }

    /**
     * Normalizes and uncapitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     */
    @NotNull
    protected String normalizeUncapitalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.uncapitalize(decorationUtils.normalize(value));
    }

    /**
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @return <code>false</code> if no primitive matches.
     */
    @SuppressWarnings("unused")
    @NotNull
    public Boolean isPrimitive()
    {
        return isPrimitive(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @param type the attribute type.
     * @param metadataTypeManager the metadata type manager.
     * @return <code>false</code> if no primitive matches.
     */
    @NotNull
    protected Boolean isPrimitive(
        @NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isPrimitive(type);
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
     */
    protected boolean isNumberSmallerThanInt(
        @NotNull final String type, @NotNull final MetadataManager metadataManager)
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
     */
    protected boolean isNumberSmallerThanInt(
        @NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isNumberSmallerThanInt(type);
    }

    /**
     * Retrieves the capitalized column name.
     * @return such name.
     */
    @NotNull
    public String getColumnNameCapitalized()
    {
        return
            capitalize(
                getColumnName(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the capitalized normalized column name.
     * @return such name.
     */
    @NotNull
    public String getColumnNameNormalizedCapitalized()
    {
        return
            normalizeCapitalize(
                getColumnName(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the uncapitalized normalized column name.
     * @return such name.
     */
    @NotNull
    public String getColumnNameNormalizedUncapitalized()
    {
        return
            normalizeUncapitalize(
                getColumnName(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves whether the attribute is a date or not.
     * @return such information.
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
     * @return such information.
     */
    protected boolean isDate(
        @NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.isDate(
                metadataTypeManager.getJavaType(type));
    }

    /**
     * Retrieves whether the attribute is a timestamp or not.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isTimestamp()
    {
        return isTimestamp(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the attribute is a timestamp or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return such information.
     */
    protected boolean isTimestamp(
        @NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.isTimestamp(
                metadataTypeManager.getJavaType(type));
    }

    /**
     * Retrieves whether the attribute is boolean or not.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isBoolean()
    {
        return isBoolean(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the attribute is boolean or not.
     * @param type the attribute type.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return such information.
     */
    protected boolean isBoolean(@NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isBoolean(metadataTypeManager.getJavaType(type));
    }

    /**
     * Retrieves whether the property allows null or not.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean getAllowsNull()
    {
        return isNullable();
    }

    /**
     * Retrieves whether the property is a binary LOB or not.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isBlob()
    {
        return isBlob(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the property is a binary LOB or not.
     * @param type the attribute type.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return such information.
     */
    @SuppressWarnings("unused")
    protected boolean isBlob(@NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isBlob(type);
    }

    /**
     * Retrieves whether the property is a binary LOB or not.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isNumeric()
    {
        return isNumeric(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the property is a binary LOB or not.
     * @param type the attribute type.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return such information.
     */
    @SuppressWarnings("unused")
    protected boolean isNumeric(@NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isNumeric(type);
    }

    /**
     * Retrieves the property name.
     * @return such information.
     */
    @NotNull
    public String toString()
    {
        return "" + getProperty();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    public final int hashCode()
    {
        return hashCode(getProperty());
    }

    /**
     * Retrieves the hash code associated to given property.
     * @param property the property.
     * @return such information.
     * @precondition property != null
     */
    protected final int hashCode(@NotNull final Property property)
    {
        return property.hashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    public boolean equals(final Object object)
    {
        boolean result = false;

        if (object instanceof AbstractPropertyDecorator)
        {
            result = equals(getProperty(), object);
        }

        return result;
    }

    /**
     * Checks whether given object is semantically equal to given instance.
     * @param property the decorated property.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    protected boolean equals(@NotNull final Property property, final Object object)
    {
        return property.equals(object);
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    public int compareTo(final Property object)
        throws  ClassCastException
    {
        return compareTo(getProperty(), object);
    }

    /**
     * Compares given object with given instance.
     * @param property the decorated property.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    @SuppressWarnings("unchecked")
    protected int compareTo(@NotNull final Property property, final Property object)
        throws  ClassCastException
    {
        int result = 1;

        if (property instanceof Comparable)
        {
            result = ((Comparable<Property>) property).compareTo(object);
        }

        return result;
    }
}
