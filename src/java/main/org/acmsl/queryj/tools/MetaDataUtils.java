/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some useful methods when working with database metadata.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing Ant classes.
 */
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;
import java.sql.Types;
import java.util.Map;
import java.util.HashMap;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Provides some useful methods when working with database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class MetaDataUtils
{
    /**
     * The native to Java type mapping.
     */
    private Map m__mNative2JavaTypeMapping;

    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected MetaDataUtils() {};

    /**
     * Specifies a new weak reference.
     * @param utils the utils instance to use.
     */
    protected static void setReference(final MetaDataUtils utils)
    {
        singleton = new WeakReference(utils);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a MetaDataUtils instance.
     * @return such instance.
     */
    public static MetaDataUtils getInstance()
    {
        MetaDataUtils result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (MetaDataUtils) reference.get();
        }

        if  (result == null) 
        {
            result = new MetaDataUtils();

            setReference(result);
        }

        return result;
    }

    /**
     * Specifies the native to Java type mapping.
     * @param map such mapping.
     */
    protected void setNative2JavaTypeMapping(final Map map)
    {
        m__mNative2JavaTypeMapping = map;
    }

    /**
     * Retrieves the native to Java type mapping.
     * @return such mapping.
     */
    protected Map getNative2JavaTypeMapping()
    {
        return m__mNative2JavaTypeMapping;
    }

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @return the associated native type.
     */
    public String getNativeType(final int dataType)
    {
        String result = null;

        switch (dataType)
        {
            case Types.DECIMAL:
                result = "BigDecimal";
                break;

            case Types.BIT:
            case Types.TINYINT:
            case Types.SMALLINT:
                result = "int";
                break;

            case Types.INTEGER:
            case Types.BIGINT:
            case Types.NUMERIC:
                result = "long";
                break;

            case Types.REAL:
            case Types.FLOAT:
            case Types.DOUBLE:
                result = "double";
                break;

            case Types.TIME:
            case Types.DATE:
            case Types.TIMESTAMP:
            case 11:
//                result = "Calendar";
                result = "Date";
                break;

            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                result = "String";
                break;

            default:
                result = "Object";
                break;
        }

        return result;
    }

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @return the associated native type.
     */
    public int getJavaType(final String dataType)
    {
        int result = Types.NULL;

        if  (dataType != null) 
        {
            result = Types.OTHER;
        
            Map t_mNative2JavaTypesMap = getNative2JavaTypeMapping();

            if  (t_mNative2JavaTypesMap == null)
            {
                t_mNative2JavaTypesMap = buildNative2JavaTypeMap();
                setNative2JavaTypeMapping(t_mNative2JavaTypesMap);
            }
        
            Object t_Result = 
                t_mNative2JavaTypesMap.get(dataType);

            if  (   (t_Result == null)
                 || !(t_Result instanceof Integer))
            {
                t_Result = 
                    t_mNative2JavaTypesMap.get(dataType.toUpperCase());
            }

            if  (   (t_Result != null)
                 && (t_Result instanceof Integer))
            {
                result = ((Integer) t_Result).intValue();
            }
        }

        return result;
    }

    /**
     * Builds the native to java type mapping.
     * @return such mapping.
     */
    protected Map buildNative2JavaTypeMap()
    {
        Map result = new HashMap();

        Integer t_Numeric = new Integer(Types.NUMERIC);
        Integer t_Integer = new Integer(Types.INTEGER);
        Integer t_Long = new Integer(Types.BIGINT);
        Integer t_Double = new Integer(Types.REAL);
        Integer t_Time = new Integer(Types.TIME);
        Integer t_TimeStamp = new Integer(Types.TIMESTAMP);
        Integer t_Text = new Integer(Types.VARCHAR);

        result.put("DECIMAL"    , t_Numeric);
        result.put("BigDecimal" , t_Numeric);
        result.put("BIT"        , t_Integer);
        result.put("TINYINT"    , t_Integer);
        result.put("SMALLINT"   , t_Integer);
        result.put("int"        , t_Integer);
        result.put("Integer"    , t_Integer);
        result.put("INTEGER"    , t_Long);
        result.put("NUMERIC"    , t_Long);
        result.put("Number"     , t_Long);
        result.put("number"     , t_Long);
        result.put("NUMBER"     , t_Long);
        result.put("BIGINT"     , t_Long);
        result.put("Long"       , t_Long);
        result.put("long"       , t_Long);
        result.put("REAL"       , t_Double);
        result.put("FLOAT"      , t_Double);
        result.put("DOUBLE"     , t_Double);
        result.put("float"      , t_Double);
        result.put("double"     , t_Double);
        result.put("TIME"       , t_Time);
        result.put("DATE"       , t_Time);
        result.put("Date"       , t_Time);
        result.put("TIMESTAMP"  , t_TimeStamp);
        result.put("Timestamp"  , t_TimeStamp);
        result.put("CHAR"       , t_Text);
        result.put("VARCHAR"    , t_Text);
        result.put("VARCHAR2"   , t_Text);
        result.put("LONGVARCHAR", t_Text);
        result.put("BINARY"     , t_Text);
        result.put("VARBINARY"  , t_Text);
        result.put("String"     , t_Text);

        return result;
    }

    /**
     * Retrieves the QueryJ type of given data type.
     * @param dataType the data type.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the QueryJ type.
     */
    public String getQueryJFieldType(
        final int dataType,
        final Project project,
        final Task task)
    {
        return
            getQueryJFieldType(
                dataType, project, task, StringUtils.getInstance());
    }

    /**
     * Retrieves the QueryJ type of given data type.
     * @param dataType the data type.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the QueryJ type.
     * @precondition stringUtils != null
     */
    protected String getQueryJFieldType(
        final int dataType,
        final Project project,
        final Task task,
        final StringUtils stringUtils)
    {
        return
            stringUtils.capitalize(
                getFieldType(dataType, project, task), '_')  + "Field";
    }

    /**
     * Retrieves the type of given data type.
     * @param dataType the data type.
     * @param project the project, for logging purposes.
     * @param task the task, for logging purposes.
     * @return the QueryJ type.
     */
    public String getFieldType(
        final int dataType,
        final Project project,
        final Task task)
    {
        String result = "";

        switch (dataType)
        {
            case Types.DECIMAL:
                result = "BigDecimal";
                break;

            case Types.BIT:
            case Types.TINYINT:
            case Types.SMALLINT:
                result = "int";
                break;

            case Types.NUMERIC:
            case Types.INTEGER:
            case Types.BIGINT:
                result = "long";
                break;

            case Types.REAL:
            case Types.FLOAT:
            case Types.DOUBLE:
                result = "double";
                break;

            case Types.TIME:
            case Types.DATE:
            case 11:
                result = "Date";
                break;

            case Types.TIMESTAMP:
                result = "Timestamp";
                break;

            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                result = "String";
                break;

            default:
                if  (project != null)
                {
                    project.log(
                        task,
                        "Warning, column type unknown: " + dataType,
                        Project.MSG_ERR);
                }
                break;
        }

        return result;
    }

    /**
     * Retrieves the setter method name.
     * @param dataType the data type.
     * @param paramIndex the parameter index.
     * @param paramName the parameter name.
     * @return the associated setter method name.
     */
    public String getSetterMethod(
        final int dataType, final int paramIndex, final String paramName)
    {
        String result = getObjectType(dataType);

        switch  (dataType)
        {
            case Types.TIME:
            case Types.DATE:
            case Types.TIMESTAMP:
            case 11:

                result +=
                    "(" + paramIndex
                    + ", new Timestamp(" + paramName + ".getTimeInMillis()));";
                break;

            default:
                result += "(" + paramIndex + ", " + paramName + ")";
                break;
        }

        result = "set" + result;

        return result;
    }

    /**
     * Retrieves the getter method name.
     * @param dataType the data type.
     * @param paramIndex the parameter index.
     * @return the associated getter method name.
     */
    public String getGetterMethod(final int dataType, final int paramIndex)
    {
        return getGetterMethod(dataType, "" + paramIndex);
    }

    /**
     * Retrieves the getter method name.
     * @param dataType the data type.
     * @param param the parameter.
     * @return the associated getter method name.
     */
    public String getGetterMethod(final int dataType, final String param)
    {
        String result = getObjectType(dataType);

        switch  (dataType)
        {
            case Types.BIT:
                result = "Integer";
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:

                result = "Int(" + param + ")";
                break;

            default:
                result += "(" + param + ")";
                break;
        }

        result = "get" + result;

        return result;
    }

    /**
     * Retrieves the result type.
     * @param dataType the data type.
     * @return the associated result type.
     */
    public String getProcedureResultType(final int dataType)
    {
        String result = getObjectType(dataType);

        switch  (dataType)
        {
            case Types.BIT:
            case Types.TINYINT:
            case Types.SMALLINT:

                result = "int";
                break;

            case Types.INTEGER:
            case Types.NUMERIC:
            case Types.BIGINT:

                result = "long";
                break;

            case Types.REAL:
            case Types.FLOAT:
            case Types.DOUBLE:
                result = "double";
                break;

            default:
                break;
        }

        return result;
    }

    /**
     * Retrieves the procedure's default value.
     * @param dataType the data type.
     * @return the associated default value.
     */
    public String getProcedureDefaultValue(final int dataType)
    {
        String result = "null";

        switch  (dataType)
        {
            case Types.BIT:
            case Types.TINYINT:
            case Types.SMALLINT:

                result = "-1";
                break;

            case Types.INTEGER:
            case Types.NUMERIC:
            case Types.BIGINT:

                result = "-1L";
                break;

            case Types.REAL:
            case Types.FLOAT:
            case Types.DOUBLE:
                result = "-0.0d";
                break;

            default:
                break;
        }

        return result;
    }

    /**
     * Retrieves the object type of given data type.
     * @param dataType the data type.
     * @return the associated object type.
     */
    public String getObjectType(final int dataType)
    {
        String result = null;

        switch (dataType)
        {
            case Types.NUMERIC:
            case Types.DECIMAL:
                result = "BigDecimal";
                break;

            case Types.BIT:
                result = "Integer";
                break;

            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
                result = "Integer";
                break;

            case Types.BIGINT:
                result = "Long";
                break;

            case Types.REAL:
            case Types.FLOAT:
            case Types.DOUBLE:
                result = "Double";
                break;

            case Types.TIME:
            case Types.DATE:
            case 11:
                result = "Date";
                break;

            case Types.TIMESTAMP:
                result = "Timestamp";
                break;

            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
            default:
                result = "String";
                break;
        }

        return result;
    }

    /**
     * Retrieves the default value of given data type.
     * @param dataType the data type.
     * @return the associated default value.
     */
    public String getDefaultValue(final int dataType)
    {
        String result = "null";

        switch (dataType)
        {
            case Types.BIGINT:
            case Types.BIT:
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
                result = "-1";
                break;

            case Types.REAL:
            case Types.FLOAT:
            case Types.DOUBLE:
                result = "-1.0d";
                break;

            default:
                result = "null";
                break;
        }

        return result;
    }

    /**
     * Retrieves the constant name of given data type.
     * @param dataType the data type.
     * @return the associated constant name.
     */
    public String getConstantName(final int dataType)
    {
        String result = null;

        switch (dataType)
        {
            case Types.BIT:
                    result = "BIT";
                    break;

            case Types.TINYINT:
                    result = "TINYINT";
                    break;

            case Types.SMALLINT:
                    result = "SMALLINT";
                    break;

            case Types.INTEGER:
                    result = "INTEGER";
                    break;

            case Types.BIGINT:
                    result = "BIGINT";
                    break;

            case Types.NUMERIC:
                    result = "NUMERIC";
                    break;

            case Types.DECIMAL:
                    result = "DECIMAL";
                    break;

            case Types.REAL:
                    result = "REAL";
                    break;

            case Types.FLOAT:
                    result = "FLOAT";
                    break;

            case Types.DOUBLE:
                    result = "DOUBLE";
                    break;

            case Types.TIME:
                    result = "TIME";
                    break;

            case Types.DATE:
                    result = "DATE";
                    break;

            case Types.TIMESTAMP:
                    result = "TIMESTAMP";
                    break;

            case Types.CHAR:
                    result = "CHAR";
                    break;

            case Types.VARCHAR:
                    result = "VARCHAR";
                    break;

            case Types.LONGVARCHAR:
                    result = "LONGVARCHAR";
                    break;

            case Types.BINARY:
                    result = "BINARY";
                    break;

            case Types.VARBINARY:
                    result = "VARBINARY";
                    break;

            case Types.LONGVARBINARY:
                    result = "LONGVARBINARY";
                    break;

            default:
                    result = "OTHER";
                    break;
       }

        return result;
    }

    /**
     * Checks if given data type refers to a String.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * String.
     */
    public boolean isString(final int dataType)
    {
        boolean result = false;

        switch (dataType)
        {
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                result = true;
                break;
        }

        return result;
    }

    /**
     * Checks if given data type represents integers.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as an
     * integer.
     */
    public boolean isInteger(final int dataType)
    {
        boolean result = false;

        switch (dataType)
        {
            case Types.NUMERIC:
            case Types.DECIMAL:
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
                result = true;
                break;
        }

        return result;
    }

    /**
     * Checks if given data type represents timestamps.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * timestamp.
     */
    public boolean isTimestamp(final int dataType)
    {
        boolean result = false;

        switch (dataType)
        {
            case 11:
            case Types.TIME:
            case Types.DATE:
            case Types.TIMESTAMP:
                result = true;
                break;
        }

        return result;
    }

    /**
     * Checks if given data type represents objects.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as an
     * object.
     */
    public boolean isObject(int dataType)
    {
        boolean result = false;

        switch (dataType)
        {
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                result = true;
                break;
        }

        return result;
    }

    /**
     * Checks if given data type represents booleans.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a
     * boolean.
     */
    public boolean isBoolean(final int dataType)
    {
        boolean result = false;

        switch (dataType)
        {
            case Types.BIT:
                result = true;
                break;
        }

        return result;
    }
}
