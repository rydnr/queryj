/*
                        QueryJ Core

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
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.Literals;
import org.acmsl.queryj.customsql.IdentifiableElement;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Decorates &lt;property&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractPropertyDecorator
    extends  PropertyElement<DecoratedString>
    implements  PropertyDecorator,
                Comparable<Property<DecoratedString>>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -7923608984348177416L;

    /**
     * The property element.
     */
    private Property<String> m__Property;

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
     * @param metadataManager the {@link MetadataTypeManager} instance.
     */
    public AbstractPropertyDecorator(
        @NotNull final Property<String> property, @NotNull final MetadataManager metadataManager)
    {
        super(
            new DecoratedString(property.getId()),
            new DecoratedString(property.getColumnName()),
            property.getIndex(),
            new DecoratedString(property.getType()),
            property.isNullable());

        immutableSetProperty(property);
        immutableSetMetadataManager(metadataManager);
        immutableSetMetadataTypeManager(
            metadataManager.getMetadataTypeManager());
    }

    /**
     * Specifies the <code>Property</code> to decorate.
     * @param property such property.
     */
    protected final void immutableSetProperty(final Property<String> property)
    {
        m__Property = property;
    }

    /**
     * Specifies the <code>Property</code> to decorate.
     * @param property such property.
     */
    @SuppressWarnings("unused")
    protected void setProperty(@NotNull final Property<String> property)
    {
        immutableSetProperty(property);
    }

    /**
     * Retrieves the decorated <code>Property</code> instance.
     * @return such property.
     */
    @NotNull
    public Property<String> getProperty()
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
    public DecoratedString getType()
    {
        return new DecoratedString(retrieveType(super.getType().getValue(), getMetadataTypeManager()));
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
        final int t_iJavaType = metadataTypeManager.getJavaType(type);

        return metadataTypeManager.getFieldType(t_iJavaType, isNullable(), isBoolean(type));
    }

    /**
     * Retrieves the Java type.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    @Override
    public DecoratedString getJavaType()
    {
        return getType();
    }

    /**
     * Retrieves the Object type of the property.
     * @return such information.
     */
    @NotNull
    @Override
    public DecoratedString getObjectType()
    {
        return
            new DecoratedString(
                retrieveObjectType(
                    getType().getValue(), getMetadataManager(), isNullable()));
    }

    /**
     * Retrieves the Object type of the property.
     * @param type the declared type.
     * @param metadataManager the {@link MetadataManager} instance.
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
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param allowsNull whether it allows nulls.
     * @param metadataTypeUtils the {@link MetadataTypeUtils} instance.
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
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @return <code>false</code> if no primitive matches.
     */
    @SuppressWarnings("unused")
    public boolean isPrimitive()
    {
        return isPrimitive(getType().getValue(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @param type the attribute type.
     * @param metadataTypeManager the metadata type manager.
     * @return <code>false</code> if no primitive matches.
     */
    protected boolean isPrimitive(
        @NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isPrimitive(type);
    }

    /**
     * Retrieves whether the type means the attribute is a
     * number smaller than an int.
     * @return such condition.
     */
    @Override
    public boolean isNumberSmallerThanInt()
    {
        return isNumberSmallerThanInt(getType().getValue(), getMetadataManager());
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
     * Retrieves whether the attribute is a date or not.
     * @return such information.
     */
    public boolean isDate()
    {
        return isDate(getType().getValue(), getMetadataTypeManager());
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
        return isTimestamp(getType().getValue(), getMetadataTypeManager());
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
        return isBoolean(getType().getValue());
    }

    /**
     * Retrieves whether the attribute is boolean or not.
     * @param type the attribute type.
     * @return such information.
     */
    protected boolean isBoolean(@NotNull final String type)
    {
        return Literals.BOOLEAN.equalsIgnoreCase(type);
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
        return isBlob(getType().getValue(), getMetadataTypeManager());
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
    public boolean isClob()
    {
        return isClob(getType().getValue(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the property is a binary LOB or not.
     * @param type the attribute type.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return such information.
     */
    @SuppressWarnings("unused")
    protected boolean isClob(@NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isClob(type);
    }

    /**
     * Retrieves whether the property is a binary LOB or not.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isNumeric()
    {
        return isNumeric(getType().getValue(), getMetadataTypeManager());
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
     * Checks whether the attribute is strictly primitive.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isStrictlyPrimitive()
    {
        return isStrictlyPrimitive(getType().getValue(), isNullable(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @param type the attribute type.
     * @param nullable whether the attribute allows nulls.
     * @param metadataTypeManager the metadata type manager.
     * @return <code>false</code> if no primitive matches.
     */
    protected boolean isStrictlyPrimitive(
        @NotNull final String type,
        final boolean nullable,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return !nullable && metadataTypeManager.isPrimitive(type);
    }

    /**
     * Checks whether its type is supported out-of-the-box.
     * @return {@code true} in such case.
     */
    @Override
    public boolean isTypeSupportedOutOfTheBox()
    {
        return isPrimitive() || isDate() || isNumeric();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NotNull
    public String toString()
    {
        return
              "{ \"class\": \"AbstractPropertyDecorator\""
            + ", \"property\": " + this.m__Property
            + ", \"metadataManager\": " + this.m__MetadataManager.hashCode()
            + ", \"metadataTypeManager\": " + this.m__MetadataTypeManager.hashCode()
            + ", \"package\": \"org.acmsl.queryj.metadata\""
            + " }";
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public final int hashCode()
    {
        return hashCode(getProperty());
    }

    /**
     * Retrieves the hash code associated to given property.
     * @param property the property.
     * @return such information.
     */
    protected final int hashCode(@NotNull final Property<String> property)
    {
        return property.hashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    public boolean equals(@Nullable final Object object)
    {
        boolean result = false;

        if (object instanceof Property)
        {
            result = equals(getProperty(), (Property<?>) object);
        }

        return result;
    }

    /**
     * Checks whether given object is semantically equal to given instance.
     * @param property the decorated property.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    protected boolean equals(@NotNull final Property<String> property, @NotNull final Property<?> object)
    {
        return property.equals(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final Property<DecoratedString> property)
    {
        return super.compareTo((IdentifiableElement<DecoratedString>) property);
    }
}
