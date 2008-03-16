//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: ParameterDecorator.java
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
import org.acmsl.queryj.tools.customsql.Parameter;
import org.acmsl.queryj.tools.customsql.ParameterElement;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;

/*
 * Importing some JDK classes.
 */
import java.util.Locale;

/**
 * Decorates &lt;parameter&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractParameterDecorator
    extends  ParameterElement
    implements ParameterDecorator
{
    /**
     * The parameter.
     */
    private Parameter m__Parameter;

    /**
     * The metadata manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * Creates a <code>ParameterDecorator</code> with given parameter.
     * @param parameter the parameter to decorate.
     * @param metadataManager the metadata manager.
     * @precondition parameter != null
     * @precondition metadataManager != null
     */
    public AbstractParameterDecorator(
        final Parameter parameter,
        final MetadataManager metadataManager)
    {
        super(
            parameter.getId(),
            parameter.getColumnName(),
            parameter.getIndex(),
            parameter.getName(),
            parameter.getType(),
            parameter.getValidationValue());

        immutableSetParameter(parameter);
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Specifies the parameter.
     * @param parameter such parameter.
     */
    protected final void immutableSetParameter(
        final Parameter parameter)
    {
        m__Parameter = parameter;
    }

    /**
     * Specifies the parameter.
     * @param parameter such parameter.
     */
    protected void setParameter(
        final Parameter parameter)
    {
        immutableSetParameter(parameter);
    }

    /**
     * Retrieves the parameter.
     * @return such information.
     */
    public Parameter getParameter()
    {
        return m__Parameter;
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
     * Retrieves the metadata type manager.
     * @return such instance.
     */
    protected MetadataTypeManager getMetadataTypeManager()
    {
        return getMetadataTypeManager(getMetadataManager());
    }

    /**
     * Retrieves the metadata type manager.
     * @return such instance.
     */
    protected MetadataTypeManager getMetadataTypeManager(
        final MetadataManager metadataManager)
    {
        return metadataManager.getMetadataTypeManager();
    }

    /**
     * Checks whether the parameter is boolean or not.
     * @return such information.
     */
    public boolean isBoolean()
    {
        return "boolean".equalsIgnoreCase(getType());
    }

    /**
     * Checks whether the parameter allows null or not.
     * @return such information.
     */
    public boolean getAllowsNull()
    {
        return getAllowsNull(getType(), getObjectType());
    }

    /**
     * Checks whether the parameter allows null or not.
     * @param type the parameter type.
     * @param objectType the parameter's object type.
     * @return such information.
     */
    protected boolean getAllowsNull(
        final String type, final String objectType)
    {
        return type.equals(objectType);
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
        Locale t_Locale = Locale.getDefault();

        return decorationUtils.capitalize(value.toLowerCase(t_Locale));
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
        final String value, final DecorationUtils decorationUtils)
    {
        Locale t_Locale = Locale.getDefault();

        return decorationUtils.uncapitalize(value.toLowerCase(t_Locale));
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
     * Normalizes given value to lower-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the alternate version of the value.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalizeLowercase(
        final String value, final DecorationUtils decorationUtils)
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
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.lowerCase(value);
    }

    /**
     * Retrieves the parameter's Java name.
     * @return such information.
     */
    public String getJavaName()
    {
        return upperCase(getName(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves whether this parameter can be modelled as a primitive or not.
     * @return <code>false</code> if no primitive matches.
     */
    public Boolean isPrimitive()
    {
        return isPrimitive(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether this parameter can be modelled as a primitive or not.
     * @param type the parameter type.
     * @param metadataTypeManager the metadata type manager.
     * @return <code>false</code> if no primitive matches.
     * @precondition metadataTypeManager != null
     */
    protected Boolean isPrimitive(
        final String type, final MetadataTypeManager metadataTypeManager)
    {
        return
            (metadataTypeManager.isPrimitive(type))
            ?  Boolean.TRUE : Boolean.FALSE;
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
                metadataTypeManager.getJavaType(type), false);
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
     * Retrieves whether the parameter is a blob or not.
     * return such information.
     */
    public boolean isBlob()
    {
        return isBlob(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the parameter is a blob or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * return such information.
     * @precondition metadataTypeManager != null
     */
    protected boolean isBlob(
        final String type, final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isBlob(type);
    }

    /**
     * Retrieves whether the parameter is a clob or not.
     * return such information.
     */
    public boolean isClob()
    {
        return isClob(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the parameter is a clob or not.
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
     * Retrieves whether the parameter is a date or not.
     * return such information.
     */
    public boolean isDate()
    {
        return isDate(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the parameter is a date or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * return such information.
     * @precondition metadataTypeManager != null
     */
    protected boolean isDate(
        final String type, final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isDate(type);
    }

    /**
     * Retrieves whether the parameter is a timestamp or not.
     * return such information.
     */
    public boolean isTimestamp()
    {
        return isTimestamp(getType(), getMetadataTypeManager());
    }

    /**
     * Retrieves whether the parameter is a timestamp or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * return such information.
     * @precondition metadataTypeManager != null
     */
    protected boolean isTimestamp(
        final String type, final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isTimestamp(type);
    }

    /**
     * Retrieves whether the type means the parameter is a
     * number smaller than an int.
     * @return such condition.
     */
    public boolean isNumberSmallerThanInt()
    {
        return isNumberSmallerThanInt(getType(), getMetadataManager());
    }

    /**
     * Retrieves whether the type means the parameter is a
     * number smaller than an int.
     * @param type the type.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such condition.
     * @precondition metadataManager != null
     */
    protected boolean isNumberSmallerThanInt(
        final String type, final MetadataManager metadataManager)
    {
        return
            isNumberSmallerThanInt(
                type, metadataManager.getMetadataTypeManager());
    }

    /**
     * Retrieves whether the type means the parameter is a
     * number smaller than an int.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such condition.
     * @precondition metadataTypeManager != null
     */
    protected boolean isNumberSmallerThanInt(
        final String type, final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isNumberSmallerThanInt(type);
    }

    /**
     * Retrieves whether the parameter is numeric or not.
     * @return such information.
     */
    public boolean isNumeric()
    {
        return isNumeric(getType(), getMetadataManager());
    }

    /**
     * Retrieves whether the parameter is numeric or not.
     * @param type the type.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such information.
     * @precondition metadataManager != null
     */
    protected boolean isNumeric(
        final String type, final MetadataManager metadataManager)
    {
        return isNumeric(type, metadataManager.getMetadataTypeManager());
    }

    /**
     * Retrieves whether the parameter is numeric or not.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @return such information.
     * @precondition metadataTypeManager != null
     */
    protected boolean isNumeric(
        final String type, final MetadataTypeManager metadataTypeManager)
    {
        return metadataTypeManager.isNumeric(type);
    }

    /**
     * Retrieves the Java type of the parameter.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return such information.
     * @precondition type != null
     * @precondition metadataTypeManager != null
     */
    public String getJavaType()
    {
        return getType();
    }


    /**
     * Retrieves the Java type of the parameter, which would be
     * only a primitive Java type if the parameter type matches,
     * and the column allows nulls.
     * @return such information.
     */
    protected String retrieveJavaType()
    {
        return
            retrieveJavaType(
                getType(),
                getMetadataManager(),
                getAllowsNull(),
                isBoolean());
    }

    /**
     * Retrieves the Java type of the parameter, which would be
     * only a primitive Java type if the parameter type matches,
     * and the column allows nulls.
     * @param type the type.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param allowsNull whether the parameter allows null.
     * @param isBool whether the parameter is declared as boolean.
     * @return such information.
     * @precondition metadataManager != null
     */
    protected String retrieveJavaType(
        final String type,
        final MetadataManager metadataManager,
        final boolean allowsNull,
        final boolean isBool)
    {
        return
            retrieveJavaType(
                type,
                metadataManager.getMetadataTypeManager(),
                allowsNull,
                isBool,
                MetadataTypeUtils.getInstance());
    }

    /**
     * Retrieves the Java type of the parameter, which would be
     * only a primitive Java type if the parameter type matches,
     * and the column allows nulls.
     * @param type the type.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @param allowsNull whether the parameter allows null.
     * @param isBool whether the parameter is declared as boolean.
     * @param metadataTypeUtils the <code>MetadataTypeUtils</code> instance.
     * @return such information.
     * @precondition metadataTypeManager != null
     * @precondition metadataTypeUtils != null
     */
    protected String retrieveJavaType(
        final String type,
        final MetadataTypeManager metadataTypeManager,
        final boolean allowsNull,
        final boolean isBool,
        final MetadataTypeUtils metadataTypeUtils)
    {
        String result = type;

        if  (allowsNull)
        {
            result = metadataTypeUtils.getWrapperClass(result);
        }

        return result;
    }

    /**
     * Retrieves the parameter name.
     * @return such information.
     */
    public String toString()
    {
        return toString(getParameter());
    }

    /**
     * Retrieves the parameter name.
     * @param parameter the parameter.
     * @return such information.
     * @precondition parameter != null
     */
    protected String toString(final Parameter parameter)
    {
        return parameter.toString();
    }
}
