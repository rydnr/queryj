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
 * Filename: AbstractParameterDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates <parameter> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.customsql.Parameter;
import org.acmsl.queryj.customsql.ParameterElement;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Decorates &lt;parameter&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro Armendariz</a>
 * @param <V> the type.
 */
@ThreadSafe
public abstract class AbstractParameterDecorator<V>
    extends  ParameterElement<DecoratedString, V>
    implements  ParameterDecorator<V>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -5249556677408053026L;

    /**
     * The decorated parameter.
     */
    private Parameter<String, V> m__Parameter;

    /**
     * The metadata type manager.
     */
    private MetadataTypeManager m__MetadataTypeManager;

    /**
     * Creates an <code>AbstractParameterDecorator</code> with given parameter.
     * @param parameter the parameter to decorate.
     * @param metadataTypeManager the metadata type manager.
     */
    public AbstractParameterDecorator(
        @NotNull final Parameter<String, V> parameter,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        super(
            new DecoratedString(parameter.getId()),
            parameter.getIndex(),
            new DecoratedString(parameter.getName()),
            new DecoratedString(parameter.getType()),
            parameter.getValidationValue());

        immutableSetParameter(parameter);
        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Specifies the parameter to decorate.
     * @param parameter the parameter.
     */
    protected final void immutableSetParameter(
        @NotNull final Parameter<String, V> parameter)
    {
        m__Parameter = parameter;
    }

    /**
     * Specifies the parameter to decorate.
     * @param parameter the parameter.
     */
    @SuppressWarnings("unused")
    protected void setParameter(@NotNull final Parameter<String, V> parameter)
    {
        immutableSetParameter(parameter);
    }

    /**
     * Retrieves the decorated parameter.
     * @return such instance.
     */
    @NotNull
    @Override
    public Parameter<String, V> getParameter()
    {
        return m__Parameter;
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
     * Retrieves the sql type of the parameter.
     * @return such information.
     * @see java.sql.Types
     */
    @Override
    @Nullable
    public DecoratedString getSqlType()
    {
        return getSqlType(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves the sql type of the parameter.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     */
    @Nullable
    protected DecoratedString getSqlType(
        @NotNull final DecoratedString type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            new DecoratedString(
                metadataTypeManager.getConstantName(
                    metadataTypeManager.getJavaType(type.getValue())));
    }

    /**
     * Retrieves the object type of the parameter.
     * @return such information.
     */
    @Nullable
    @Override
    public DecoratedString getObjectType()
    {
        return getObjectType(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves the object type of the parameter.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     */
    @Nullable
    public DecoratedString getObjectType(
        @NotNull final DecoratedString type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        // TODO; support boolean parameters
        return
            new DecoratedString(
                metadataTypeManager.getObjectType(
                    metadataTypeManager.getJavaType(type.getValue()), false));
    }

    /**
     * Retrieves whether the parameter type is a class or not.
     * @return such information.
     */
    @Override
    public boolean isObject()
    {
        return isObject(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the parameter type is a class or not.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     */
    protected boolean isObject(
        @NotNull final DecoratedString type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.isObject(
                metadataTypeManager.getJavaType(type.getValue()));
    }

    /**
     * Retrieves whether the parameter type is a String or not.
     * @return such information.
     */
    @Override
    public boolean isString()
    {
        return isString(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the parameter type is a String or not.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     */
    protected boolean isString(
        @NotNull final DecoratedString type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.isString(
                metadataTypeManager.getJavaType(type.getValue()));
    }

    /**
     * Retrieves the field type of the parameter.
     * @return such information.
     */
    @NotNull
    @Override
    public DecoratedString getFieldType()
    {
        return getFieldType(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves the field type of the parameter.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     */
    public DecoratedString getFieldType(
        @NotNull final DecoratedString type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            new DecoratedString(
                metadataTypeManager.getFieldType(
                    metadataTypeManager.getJavaType(type.getValue())));
    }

    /**
     * Retrieves whether the attribute is a clob or not.
     * @return such information.
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
     * @return such information.
     */
    protected boolean isClob(
        @NotNull final DecoratedString type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.isClob(metadataTypeManager.getJavaType(type.getValue()));
    }

    /**
     * Retrieves the Java type of the parameter.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public DecoratedString getJavaType()
    {
        return getType();
    }

    /**
     * Checks whether this parameter is nullable or not.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isNullable()
    {
        return false;
    }

    /**
     * Checks whether this parameter is primitive or not.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isPrimitive()
    {
        return isPrimitive(getType(), getMetadataTypeManager());
    }

    /**
     * Checks whether this parameter is primitive or not.
     * @param type the type.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return such information.
     */
    protected boolean isPrimitive(@NotNull final DecoratedString type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isPrimitive(type.getValue());
    }

    /**
     * Checks whether this parameter is a primitive or not.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isPrimitiveWrapper()
    {
        return isPrimitiveWrapper(getType(), getMetadataTypeManager());
    }

    /**
     * Checks whether this parameter is primitive or not.
     * @param type the type.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return such information.
     */
    protected boolean isPrimitiveWrapper(
        @NotNull final DecoratedString type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isPrimitiveWrapper(type.getValue());
    }

    /**
     * Retrieves the comment.
     * @return such information.
     */
    @NotNull
    public String getComment()
    {
        return "";
    }

    /**
     * Checks whether this parameter is a Date.
     * @return {@code true} in such case.
     */
    public boolean isDate()
    {
        return isDate(getType(), getMetadataTypeManager());
    }

    /**
     * Checks whether this parameter is a Date.
     * @param type the type.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return {@code true} in such case.
     */
    protected boolean isDate(
        @NotNull final DecoratedString type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isDate(type.getValue());
    }

    /**
     * Provides a text representation of the information
     * contained in given instance.
     * @return such information.
     */
    @Override
    @NotNull
    public String toString()
    {
        return toString(getParameter(), getMetadataTypeManager());
    }

    /**
     * Provides a text representation of the information
     * contained in given instance.
     * @param parameter the decorated parameter.
     * @param typeManager the {@link MetadataTypeManager} instance.
     * @return such information.
     */
    @NotNull
    protected String toString(
        @NotNull final Parameter<String, V> parameter, @NotNull final MetadataTypeManager typeManager)
    {
        return "" + parameter + typeManager.hashCode();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    @Override
    public int hashCode()
    {
        return hashCode(getParameter());
    }

    /**
     * Retrieves the hash code associated to given instance.
     * @param parameter the decorated parameter.
     * @return such information.
     */
    protected int hashCode(@NotNull final Parameter<String, V> parameter)
    {
        return parameter.hashCode();
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

        @Nullable final Parameter<String, V> parameter = getParameter();

        if (object instanceof Parameter)
        {
            result = parameter.equals(object);
        }

        return result;
    }
}
