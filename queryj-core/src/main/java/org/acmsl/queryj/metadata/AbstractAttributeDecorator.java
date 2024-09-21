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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

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
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.vo.AbstractAttribute;
import org.acmsl.queryj.metadata.vo.Attribute;

/*
 * Importing Apache Commons Lang classes.
 */
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotation.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Decorates <code>Attribute</code> instances to provide required alternate
 * representations of the information stored therein.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
@ThreadSafe
public abstract class AbstractAttributeDecorator
    extends  AbstractAttribute<DecoratedString>
    implements  AttributeDecorator
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -5926089876321230076L;

    /**
     * The decorated attribute.
     */
    private Attribute<String> m__Attribute;

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
     */
    public AbstractAttributeDecorator(
        @NotNull final Attribute<String> attribute, @NotNull final MetadataManager metadataManager)
    {
        this(
            attribute.getName(),
            attribute.getTypeId(),
            attribute.getType(),
            attribute.getTableName(),
            attribute.getComment(),
            attribute.getOrdinalPosition(),
            attribute.getLength(),
            attribute.getPrecision(),
            attribute.getKeyword(),
            attribute.getRetrievalQuery(),
            attribute.getSequence(),
            attribute.isNullable(),
            attribute.getValue(),
            attribute.isReadOnly(),
            attribute.isBoolean(),
            attribute.getBooleanTrue(),
            attribute.getBooleanFalse(),
            attribute.getBooleanNull(),
            attribute,
            metadataManager,
            metadataManager.getMetadataTypeManager());
    }

    /**
     * Creates an <code>AttributeDecorator</code> with the following
     * information.
     * @param name the name.
     * @param typeId the type id.
     * @param type the type.
     * @param tableName the table name.
     * @param comment the attribute comment.
     * @param ordinalPosition the ordinal position.
     * @param length the length.
     * @param precision the precision.
     * @param keyword the keyword used to retrieve the value, if any.
     * @param retrievalQuery the query used to retrieve the value, if any.
     * @param sequence the sequence (for Oracle engines).
     * @param allowsNull whether the attribute allows null values or not.
     * @param value the optional attribute value.
     * @param readOnly whether the attribute is marked as read-only.
     * @param isBool whether the attribute is marked as boolean.
     * @param attribute the attribute.
     * @param booleanTrue the symbol for {@code true} values in boolean attributes.
     * @param booleanFalse the symbol for {@code false} values in boolean attributes.
     * @param booleanNull the symbol for {@code null} values in boolean attributes.
     * @param metadataManager the {@link MetadataManager metadata manager}.
     * @param metadataTypeManager the {@link MetadataTypeManager metadata type manager}.
     */
    public AbstractAttributeDecorator(
        @NotNull final String name,
        final int typeId,
        @NotNull final String type,
        @NotNull final String tableName,
        @Nullable final String comment,
        final int ordinalPosition,
        final int length,
        final int precision,
        @Nullable final String keyword,
        @Nullable final String retrievalQuery,
        @Nullable final String sequence,
        final boolean allowsNull,
        @Nullable final String value,
        final boolean readOnly,
        final boolean isBool,
        @Nullable final String booleanTrue,
        @Nullable final String booleanFalse,
        @Nullable final String booleanNull,
        @NotNull final Attribute<String> attribute,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
         super(
            new DecoratedString(name),
            typeId,
            new DecoratedString(type),
            new DecoratedString(tableName),
            isNullOrEmpty(comment) ? null : new DecoratedString("" + comment),
            ordinalPosition,
            length,
            precision,
            isNullOrEmpty(keyword) ? null : new DecoratedString("" + keyword),
            isNullOrEmpty(retrievalQuery) ? null : new DecoratedString("" + retrievalQuery),
            isNullOrEmpty(sequence) ? null : new DecoratedString("" + sequence),
            allowsNull,
            isNullOrEmpty(value) ? null : new DecoratedString("" + value),
            readOnly,
            isBool,
            isNullOrEmpty(booleanTrue) ? null : new DecoratedString("" + booleanTrue),
            isNullOrEmpty(booleanFalse) ? null : new DecoratedString("" + booleanFalse),
            isNullOrEmpty(booleanNull) ? null : new DecoratedString("" + booleanNull));

        immutableSetAttribute(attribute);
        immutableSetMetadataManager(metadataManager);
        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Specifies the attribute to decorate.
     * @param attribute such attribute.
     */
    protected final void immutableSetAttribute(@NotNull final Attribute<String> attribute)
    {
        m__Attribute = attribute;
    }

    /**
     * Specifies the attribute to decorate.
     * @param attribute such attribute.
     */
    @SuppressWarnings("unused")
    protected void setAttribute(@NotNull final Attribute<String> attribute)
    {
        immutableSetAttribute(attribute);
    }

    /**
     * Retrieves the decorated attribute.
     * @return such attribute.
     */
    @Override
    @NotNull
    public Attribute<String> getAttribute()
    {
        return m__Attribute;
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
     * Retrieves the attribute's associated getter method.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getGetterMethod()
    {
        return getGetterMethod(getTypeId(), getMetadataTypeManager());
    }

    /**
     * Retrieves the attribute's associated getter method.
     * @param type the attribute type.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     */
    @NotNull
    protected String getGetterMethod(
        final int type, final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getGetterMethod(type);
    }

    /**
     * Checks whether the attribute is strictly primitive.
     * @return such information.
     */
    public boolean isStrictlyPrimitive()
    {
        return isStrictlyPrimitive(getTypeId(), isNullable(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @param typeId the attribute type.
     * @param nullable whether the attribute allows nulls.
     * @param metadataTypeManager the metadata type manager.
     * @return <code>false</code> if no primitive matches.
     */
    protected boolean isStrictlyPrimitive(
        final int typeId,
        final boolean nullable,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return !nullable && metadataTypeManager.isPrimitive(typeId);
    }

    /**
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @return <code>false</code> if no primitive matches.
     */
    public boolean isPrimitive()
    {
        return isPrimitive(getTypeId(), retrieveType(), isNullable(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether this attribute can be modelled as a primitive or not.
     * @param typeId the id of the type.
     * @param type the attribute type.
     * @param nullable whether the attribute accepts nulls.
     * @param metadataTypeManager the metadata type manager.
     * @return <code>false</code> if no primitive matches.
     */
    protected boolean isPrimitive(
        final int typeId,
        @NotNull final String type,
        final boolean nullable,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        final boolean result;

        if (isStrictlyPrimitive(typeId, nullable, metadataTypeManager))
        {
            result = true;
        }
        else if (   !(nullable)
                 && (metadataTypeManager.isPrimitiveWrapper(type)))
        {
            result = true;
        }
        else
        {
            result = false;
        }

        return result;
    }

    /**
     * Retrieves whether the type of this attribute is a primitive wrapper or not.
     * @return {@code true} in such case.
     */
    public boolean isPrimitiveWrapper()
    {
        return isPrimitiveWrapper(retrieveType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether given type is a primitive wrapper or not.
     * @param type the type.
     * @param metadataTypeManager the {@link MetadataTypeManager}.
     * @return {@code true} in such case.
     */
    protected boolean isPrimitiveWrapper(final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isPrimitiveWrapper(type);
    }

    /**
     * Retrieves the object type.
     * @return such information.
     */
    @NotNull
    public String getObjectType()
    {
        return getObjectType(getTypeId(), getMetadataTypeManager());
    }

    /**
     * Retrieves the attribute's object type.
     * @param type the attribute type.
     * @param metadataTypeManager the metadata type manager.
     * @return such type.
     */
    @NotNull
    protected String getObjectType(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getSmartObjectType(type, isBoolean());
    }

    /**
     * Retrieves whether the attribute is a blob or not.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isBlob()
    {
        return isBlob(getTypeId(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the attribute is a blob or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return such information.
     */
    protected boolean isBlob(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isBlob(type);
    }

    /**
     * Retrieves whether the attribute is a clob or not.
     * @return such information.
     */
    @Override
    public boolean isClob()
    {
        return isClob(getTypeId(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the attribute is a clob or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return such information.
     */
    protected boolean isClob(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isClob(type);
    }

    /**
     * Retrieves whether the attribute is a string or not.
     * @return such information.
     */
    @Override
    public boolean isString()
    {
        return isString(getTypeId(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the attribute is a string or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return such information.
     */
    protected boolean isString(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isString(type);
    }

    /**
     * Retrieves whether the attribute is a date or not.
     * @return such information.
     */
    @Override
    public boolean isDate()
    {
        return isDate(getAttribute().getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the attribute is a date or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return such information.
     */
    protected boolean isDate(
        final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isDate(type);
    }

    /**
     * Retrieves whether the attribute is a timestamp or not.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isTimestamp()
    {
        return isTimestamp(getTypeId(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the attribute is a timestamp or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return such information.
     */
    protected boolean isTimestamp(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isTimestamp(type);
    }

    /**
     * Retrieves the query to retrieve the externally-managed value.
     * @return such information.
     */
    @Nullable
    public String getQuery()
    {
        return getQuery(getAttribute(), isExternallyManaged());
    }

    /**
     * Retrieves the query to retrieve the externally-managed value.
     * @param attribute the {@link Attribute}.
     * @param managedExternally whether the attribute is managed externally.
     * @return such information.
     */
    @Nullable
    protected String getQuery(final Attribute<String> attribute, final boolean managedExternally)
    {
        String result = "";

        if  (managedExternally)
        {
            result =
                getQuery(attribute.getName(), attribute.getTableName(), getMetadataManager());
        }

        return result;
    }

    /**
     * Retrieves the query to retrieve the externally-managed value.
     * @param name the field name.
     * @param tableName the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    protected String getQuery(
        @NotNull final String name,
        @NotNull final String tableName,
        @NotNull final MetadataManager metadataManager)
    {
        return null;
        // TODO    metadataManager. getExternallyManagedFieldRetrievalQuery(
        //    tableName, name);

    }

    /**
     * Retrieves the QueryJ type.
     * @return the QueryJ type.
     */
    @NotNull
    public String getQueryJFieldType()
    {
        return getQueryJFieldType(getTypeId(), getMetadataTypeManager());
    }

    /**
     * Retrieves the QueryJ type.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return the QueryJ type.
     */
    @NotNull
    protected String getQueryJFieldType(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getQueryJFieldType(type, isBoolean());
    }

    /**
     * Retrieves the QueryJ type for statement setters.
     * @return the QueryJ type.
     */
    @NotNull
    public String getStatementSetterFieldType()
    {
        return
            getStatementSetterFieldType(
                getTypeId(), getMetadataTypeManager());
    }

    /**
     * Retrieves the QueryJ type for statement setters.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return the QueryJ type.
     */
    @NotNull
    protected String getStatementSetterFieldType(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getStatementSetterFieldType(type);
    }

    /**
     * Retrieves whether the type means the attribute is a
     * number smaller than an int.
     * @return such condition.
     */
    @Override
    public boolean isNumberSmallerThanInt()
    {
        return isNumberSmallerThanInt(getTypeId(), getMetadataManager());
    }

    /**
     * Retrieves whether the type means the attribute is a
     * number smaller than an int.
     * @param type the type.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such condition.
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
     */
    protected boolean isNumberSmallerThanInt(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isNumberSmallerThanInt(type);
    }

    /**
     * Retrieves whether the attribute is numeric or not.
     * @return such information.
     */
    @Override
    public boolean isNumeric()
    {
        return isNumeric(getTypeId(), getMetadataManager());
    }

    /**
     * Retrieves whether the attribute is numeric or not.
     * @param type the type.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     */
    protected boolean isNumeric(final int type, @NotNull final MetadataManager metadataManager)
    {
        return isNumeric(type, metadataManager.getMetadataTypeManager());
    }

    /**
     * Retrieves whether the attribute is numeric or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such information.
     */
    protected boolean isNumeric(
        final int type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isNumeric(type);
    }

    /**
     * Retrieves the Java type of the attribute.
     * @return such information.
     */
    @Deprecated
    @SuppressWarnings("unused")
    @NotNull
    public String getJavaType()
    {
        return getType(getTypeId(), getMetadataManager());
    }

    /**
     * Retrieves the primitive type of the attribute, converting to a primitive if possible, even if it's nullable.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getPrimitiveType()
    {
        return
            getPrimitiveType(
                getTypeId(),
                retrieveType(),
                isBoolean(),
                getPrecision(),
                getMetadataTypeManager());
    }

    /**
     * Retrieves the primitive type of the attribute, converting to a primitive if possible, even if it's nullable.
     * @param typeId the id of the type.
     * @param type the type.
     * @param isBool whether is a boolean attribute.
     * @param precision the precision.
     * @param metadataTypeManager the {@link MetadataTypeManager}.
     * @return such information.
     */
    @NotNull
    protected String getPrimitiveType(
        final int typeId,
        @NotNull final String type,
        final boolean isBool,
        final int precision,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        @Nullable final String result;

        if (isPrimitive(typeId, type, false, metadataTypeManager))
        {
            result = metadataTypeManager.getNativeType(typeId, false, isBool, precision);
        }
        else
        {
            result = getObjectType();
        }

        return result;
    }

    /**
     * Retrieves the Java type of the property.
     * @param type the type.
     * @param metadataManager the {@link MetadataManager}
     * instance.
     * @return such information.
     */
    @NotNull
    protected String getType(
        final int type, @NotNull final MetadataManager metadataManager)
    {
        return
            retrieveType(type, metadataManager, isNullable(), isBoolean());
    }

    /**
     * Retrieves the Java type of the attribute, which would be
     * only a primitive Java type if the attribute type matches,
     * and the column allows nulls.
     * @return such information.
     */
    @NotNull
    protected String retrieveType()
    {
        return
            retrieveType(
                getTypeId(),
                getMetadataManager(),
                isNullable(),
                isBoolean());
    }

    /**
     * Retrieves the Java type of the attribute, which would be
     * only a primitive Java type if the attribute type matches,
     * and the column allows nulls.
     * @param type the type.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param allowsNull whether the attribute allows null.
     * @param isBool whether the attribute is declared as boolean.
     * @return such information.
     */
    @NotNull
    protected String retrieveType(
        final int type,
        final MetadataManager metadataManager,
        final boolean allowsNull,
        final boolean isBool)
    {
        return
            retrieveType(
                type,
                metadataManager.getMetadataTypeManager(),
                allowsNull,
                isBool,
                MetadataTypeUtils.getInstance());
    }

    /**
     * Retrieves the Java type of the attribute, which would be
     * only a primitive Java type if the attribute type matches,
     * and the column allows nulls.
     * @param type the type.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param allowsNull whether the attribute allows null.
     * @param isBool whether the attribute is declared as boolean.
     * @param metadataTypeUtils the {@link MetadataTypeUtils} instance.
     * @return such information.
     */
    @NotNull
    protected String retrieveType(
        final int type,
        @NotNull final MetadataTypeManager metadataTypeManager,
        final boolean allowsNull,
        final boolean isBool,
        @NotNull final MetadataTypeUtils metadataTypeUtils)
    {
        String result = metadataTypeManager.getNativeType(type, allowsNull, isBool);

        if  (allowsNull)
        {
            result = metadataTypeUtils.getWrapperClass(result);
        }

        return result;
    }

    /**
     * Retrieves the attribute type, as a {@link java.sql.Types}'s constant value.
     * @return such information.
     */
    @Override
    public int getJavaSqlType()
    {
        return getJavaSqlType(getAttribute(), getMetadataTypeManager());
    }

    /**
     * Retrieves the attribute type, as a {@link java.sql.Types}'s constant value.
     * @param attribute the {@link Attribute attribute}.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return the {@link java.sql.Types}'s constant value.
     */
    protected int getJavaSqlType(
        @NotNull final Attribute<String> attribute, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getJavaType(attribute.getType());
    }

    /**
     * Retrieves the attribute type, as a {@link java.sql.Types}'s constant name.
     * @return such information.
     */
    @Override
    @NotNull
    public String getConstantName()
    {
        return getConstantName(getAttribute(), getMetadataTypeManager());
    }

    /**
     * Retrieves the attribute type, as a {@link java.sql.Types}'s constant value.
     * @param attribute the {@link Attribute attribute}.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return the {@link java.sql.Types}'s constant value.
     */
    @NotNull
    protected String getConstantName(
        @NotNull final Attribute<String> attribute, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.getConstantName(attribute.getTypeId());
    }

    /**
     * Checks whether the value is null or not.
     * @return such check.
     */
    @SuppressWarnings("unused")
    public boolean isValueNull()
    {
        return isNull(getAttribute().getValue());
    }

    /**
     * Checks whether the value is null or not.
     * @param value the value.
     * @return such check.
     */
    protected boolean isNull(@Nullable final String value)
    {
        return value == null;
    }

    /**
     * Checks whether this attribute is a float.
     * @return {@code true} in such case.
     */
    public boolean isFloat()
    {
        return isFloat(getTypeId(), getMetadataTypeManager());
    }

    /**
     * Checks whether this attribute is a float.
     * @param typeId the type id.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return {@code true} in such case.
     */
    protected boolean isFloat(final int typeId, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isFloat(typeId);
    }

    /**
     * Checks whether this attribute is a double.
     * @return {@code true} in such case.
     */
    public boolean isDouble()
    {
        return isDouble(getTypeId(), getMetadataTypeManager());
    }

    /**
     * Checks whether this attribute is a double.
     * @param typeId the type id.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return {@code true} in such case.
     */
    protected boolean isDouble(final int typeId, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isDouble(typeId);
    }

    /**
     * Checks whether this attribute is decimal or not.
     * @return {@code true} in such case.
     */
    public boolean isDecimal()
    {
        return isDecimal(getTypeId(), getMetadataTypeManager());
    }

    /**
     * Checks whether this attribute is decimal or not.
     * @param typeId the type id.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return {@code true} in such case.
     */
    protected boolean isDecimal(final int typeId, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isDecimal(typeId);
    }

    /**
     * Checks whether this attribute is int or not.
     * @return {@code true} in such case.
     */
    public boolean isInt()
    {
        return isInt(getTypeId(), getMetadataTypeManager());
    }

    /**
     * Checks whether this attribute is int or not.
     * @param typeId the type id.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return {@code true} in such case.
     */
    protected boolean isInt(final int typeId, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isInt(typeId);
    }

    /**
     * Checks whether this attribute is long or not.
     * @return {@code true} in such case.
     */
    public boolean isLong()
    {
        return isLong(getTypeId(), getMetadataTypeManager());
    }

    /**
     * Checks whether this attribute is long or not.
     * @param typeId the type id.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return {@code true} in such case.
     */
    protected boolean isLong(final int typeId, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isLong(typeId);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + AbstractAttributeDecorator.class.getName() + "\""
            + ", \"attribute\": " + this.m__Attribute
            + ", \"metadataManager\": " + this.m__MetadataManager.hashCode()
            + ", \"metadataTypeManager\": " + this.m__MetadataTypeManager.hashCode()
            + " }";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.m__Attribute).toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable final Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final AbstractAttributeDecorator other = (AbstractAttributeDecorator) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj)).append(this.m__Attribute, other.m__Attribute)
            .isEquals();
    }
}
