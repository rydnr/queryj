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
 * Filename: AbstractParameterDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates <parameter> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.metadata;

/*
 * Importing project-specific classes.
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
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public abstract class AbstractParameterDecorator
    extends  ParameterElement
    implements  ParameterDecorator
{
    /**
     * The decorated parameter.
     */
    private Parameter m__Parameter;

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
        @NotNull final Parameter parameter,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        super(
            parameter.getId(),
            parameter.getIndex(),
            parameter.getName(),
            parameter.getType(),
            parameter.getValidationValue());

        immutableSetParameter(parameter);
        immutableSetMetadataTypeManager(metadataTypeManager);
    }

    /**
     * Specifies the parameter to decorate.
     * @param parameter the parameter.
     */
    protected final void immutableSetParameter(
        @NotNull final Parameter parameter)
    {
        m__Parameter = parameter;
    }

    /**
     * Specifies the parameter to decorate.
     * @param parameter the parameter.
     */
    @SuppressWarnings("unused")
    protected void setParameter(@NotNull final Parameter parameter)
    {
        immutableSetParameter(parameter);
    }

    /**
     * Retrieves the decorated parameter.
     * @return such instance.
     */
    @NotNull
    @Override
    public Parameter getParameter()
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
    public String getSqlType()
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
    protected String getSqlType(
        @NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.getConstantName(
                metadataTypeManager.getJavaType(type));
    }

    /**
     * Retrieves the object type of the parameter.
     * @return such information.
     */
    @Nullable
    @Override
    public String getObjectType()
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
    public String getObjectType(
        @NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        // TODO; support boolean parameters
        return
            metadataTypeManager.getObjectType(
                metadataTypeManager.getJavaType(type), false);
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
        @NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.isObject(
                metadataTypeManager.getJavaType(type));
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
        @NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.isString(
                metadataTypeManager.getJavaType(type));
    }

    /**
     * Retrieves the field type of the parameter.
     * @return such information.
     */
    @NotNull
    @Override
    public String getFieldType()
    {
        return getFieldType(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves the field type of the parameter.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     */
    public String getFieldType(
        @NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.getFieldType(
                metadataTypeManager.getJavaType(type));
    }

    /**
     * Retrieves the name, in lower case.
     * @return such value.
     */
    @NotNull
    public String getNameLowercased()
    {
        return lowerCase(getName(), DecorationUtils.getInstance());
    }
    
    /**
     * Converts given value to lower-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     */
    protected String lowerCase(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.lowerCase(value);
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
     */
    protected boolean isClob(
        @NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.isClob(metadataTypeManager.getJavaType(type));
    }

    /**
     * Retrieves the Java type of the parameter.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String getJavaType()
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
    protected boolean isPrimitive(@NotNull final String type, @NotNull final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isPrimitive(type);
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
        return toString(getParameter());
    }

    /**
     * Provides a text representation of the information
     * contained in given instance.
     * @param parameter the decorated parameter.
     * @return such information.
     */
    @NotNull
    protected String toString(@NotNull final Parameter parameter)
    {
        return "" + parameter;
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
    protected int hashCode(@NotNull final Parameter parameter)
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

        @Nullable final Parameter parameter = getParameter();

        if (   (parameter != null)
            && (object instanceof Parameter))
        {
            result = parameter.equals(object);
        }

        return result;
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    @Override
    public int compareTo(@Nullable final Parameter object)
        throws  ClassCastException
    {
        return compareTo(getParameter(), object);
    }

    /**
     * Compares given object with given instance.
     * @param parameter the decorated parameter.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    @SuppressWarnings("unchecked")
    protected int compareTo(@NotNull final Parameter parameter, @Nullable final Parameter object)
        throws  ClassCastException
    {
        int result = 1;

        if (parameter instanceof Comparable)
        {
            result = ((Comparable<Parameter>) parameter).compareTo(object);
        }

        return result;
    }
}
