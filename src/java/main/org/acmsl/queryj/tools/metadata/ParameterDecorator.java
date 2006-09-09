//;-*- mode: java -*-
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
 * Filename: $RCSfile: $
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
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;

/**
 * Decorates &lt;parameter&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class ParameterDecorator
    extends  ParameterElement
{
    /**
     * The decorated parameter.
     */
    private ParameterElement m__Parameter;

    /**
     * The metadata type manager.
     */
    private MetadataTypeManager m__MetadataTypeManager;

    /**
     * Creates a <code>ParameterDecorator</code> with given parameter.
     * @param parameter the parameter to decorate.
     * @param metadataTypeManager the metadata type manager.
     * @precondition parameter != null
     * @precondition metadataTypeManager != null
     */
    public ParameterDecorator(
        final ParameterElement parameter,
        final MetadataTypeManager metadataTypeManager)
    {
        super(
            parameter.getId(),
            parameter.getColumnName(),
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
        final ParameterElement parameter)
    {
        m__Parameter = parameter;
    }

    /**
     * Specifies the parameter to decorate.
     * @param parameter the parameter.
     */
    protected void setParameter(final ParameterElement parameter)
    {
        immutableSetParameter(parameter);
    }

    /**
     * Retrieves the decorated parameter.
     * @return such instance.
     */
    public ParameterElement getParameter()
    {
        return m__Parameter;
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
     * Retrieves the sql type of the parameter.
     * @return such information.
     * @see java.sql.Types
     */
    public String getSqlType()
    {
        return getSqlType(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves the sql type of the parameter.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     * @precondition metadataTypeManager != null
     */
    protected String getSqlType(
        final String type, final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.getConstantName(
                metadataTypeManager.getJavaType(type));
    }

    /**
     * Retrieves the object type of the parameter.
     * @return such information.
     */
    public String getObjectType()
    {
        return getObjectType(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves the object type of the parameter.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     * @precondition metadataTypeManager != null
     */
    public String getObjectType(
        final String type, final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.getObjectType(
                metadataTypeManager.getJavaType(type));
    }

    /**
     * Retrieves whether the parameter type is a class or not.
     * @return such information.
     */
    public boolean isObject()
    {
        return isObject(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the parameter type is a class or not.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     * @precondition metadataTypeManager != null
     */
    protected boolean isObject(
        final String type, final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.isObject(
                metadataTypeManager.getJavaType(type));
    }

    /**
     * Retrieves whether the parameter type is a String or not.
     * @return such information.
     */
    public boolean isString()
    {
        return isString(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the parameter type is a String or not.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     * @precondition metadataTypeManager != null
     */
    protected boolean isString(
        final String type, final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.isString(
                metadataTypeManager.getJavaType(type));
    }

    /**
     * Retrieves the field type of the parameter.
     * @return such information.
     */
    public String getFieldType()
    {
        return getFieldType(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves the field type of the parameter.
     * @param type the type.
     * @param metadataTypeManager the metadata type manager.
     * @return such information.
     * @precondition metadataTypeManager != null
     */
    public String getFieldType(
        final String type, final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.getFieldType(
                metadataTypeManager.getJavaType(type));
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
     * @precondition type != null
     * @precondition metadataTypeManager != null
     */
    protected boolean isClob(
        final String type, final MetadataTypeManager metadataTypeManager)
    {
        return
            metadataTypeManager.isClob(metadataTypeManager.getJavaType(type));
    }


    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    public String toString()
    {
        return
            new org.apache.commons.lang.builder.ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("metadataTypeManager", getMetadataTypeManager())
                .toString();
    }

    /**
     * Retrieves the hash code associated to this instance.
     * @return such information.
     */
    public int hashCode()
    {
        return hashCode(getParameter());
    }

    /**
     * Retrieves the hash code associated to given instance.
     * @param parameter the parameter.
     * @return such information.
     * @precondition parameter != null
     */
    protected int hashCode(final ParameterElement parameter)
    {
        return parameter.hashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    public boolean equals(final Object object)
    {
        boolean result = false;

        if  (object instanceof ParameterDecorator)
        {
            final ParameterDecorator t_OtherInstance =
                (ParameterDecorator) object;

            result =
                new org.apache.commons.lang.builder.EqualsBuilder()
                    .appendSuper(super.equals(t_OtherInstance))
                    .append(
                        getMetadataTypeManager(),
                        t_OtherInstance.getMetadataTypeManager())
                    .append(
                        getSqlType(),
                        t_OtherInstance.getSqlType())
                    .append(
                        getObjectType(),
                        t_OtherInstance.getObjectType())
                    .append(
                        isObject(),
                        t_OtherInstance.isObject())
                    .append(
                        isString(),
                        t_OtherInstance.isString())
                    .append(
                        getFieldType(),
                        t_OtherInstance.getFieldType())
                    .append(
                        getNameLowercased(),
                        t_OtherInstance.getNameLowercased())
                    .append(
                        isClob(),
                        t_OtherInstance.isClob())
                .isEquals();
        }
        else
        {
            result = super.equals(object);
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
    public int compareTo(final Object object)
        throws  ClassCastException
    {
        int result = 1;

        if  (object instanceof ParameterDecorator)
        {
            final ParameterDecorator t_OtherInstance =
                (ParameterDecorator) object;

            result =
                new org.apache.commons.lang.builder.CompareToBuilder()
                .append(
                    getMetadataTypeManager(),
                    t_OtherInstance.getMetadataTypeManager())
                .append(
                    getSqlType(),
                    t_OtherInstance.getSqlType())
                .append(
                    getObjectType(),
                    t_OtherInstance.getObjectType())
                .append(
                    isObject(),
                    t_OtherInstance.isObject())
                .append(
                    isString(),
                    t_OtherInstance.isString())
                .append(
                    getFieldType(),
                    t_OtherInstance.getFieldType())
                .append(
                    getNameLowercased(),
                    t_OtherInstance.getNameLowercased())
                .append(
                    isClob(),
                    t_OtherInstance.isClob())
                .toComparison();
        }
        else
        {
            result = super.compareTo(object);
        }

        return result;
    }
}
