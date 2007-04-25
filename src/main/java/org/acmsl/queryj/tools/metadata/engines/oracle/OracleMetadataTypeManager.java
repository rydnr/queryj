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
 * Filename: OracleMetadataTypeManager.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Customizes JdbcMetadataTypeManager for Oracle databases.
 *
 */
package org.acmsl.queryj.tools.metadata.engines.oracle;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.engines.JdbcMetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.sql.Types;
import java.util.Map;
import java.util.HashMap;

/**
 * Customizes <code>JdbcMetadataTypeManager</code> for Oracle databases.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class OracleMetadataTypeManager
    extends  JdbcMetadataTypeManager
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class OracleMetadataTypeManagerSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final OracleMetadataTypeManager SINGLETON =
            new OracleMetadataTypeManager();
    }

    /**
     * Creates an empty <code>OracleMetadataTypeManager</code>.
     */
    public OracleMetadataTypeManager() {};
    
    /**
     * Retrieves an <code>OracleMetadataTypeManager</code> instance.
     * @return such instance.
     */
    public static JdbcMetadataTypeManager getInstance()
    {
        return OracleMetadataTypeManagerSingletonContainer.SINGLETON;
    }

    /**
     * Checks if given data type represents clobs.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as a clob.
     */
    public boolean isClob(final int dataType)
    {
        boolean result = false;

        switch (dataType)
        {
            case Types.CLOB:
            case Types.OTHER:
                result = true;
                break;
            default:
                result = false;
                break;
        }

        return result;
    }

    /**
     * Retrieves the native type of given data type.
     * @param dataType the data type.
     * @param allowsNull whether to allow null or not.
     * @param isBool whether the attribute is marked as boolean.
     * @return the associated native type.
     */
    public String getNativeType(
        final int dataType, final boolean allowsNull, final boolean isBool)
    {
        String result;

        if  (dataType == Types.OTHER)
        {
            result = "String";
        }
        else
        {
            result = super.getNativeType(dataType, allowsNull, isBool);
        }

        return result;
    }

    /**
     * Retrieves the type of given data type.
     * @param dataType the data type.
     * @param allowsNull whether the field allows null or not.
     * @param isBool whether the attribute is marked as boolean.
     * @return the QueryJ type.
     */
    public String getFieldType(
        final int dataType, final boolean allowsNull, final boolean isBool)
    {
        String result;

        if  (dataType == Types.OTHER)
        {
            result = "Clob";
        }
        else
        {
            result = super.getFieldType(dataType, allowsNull, isBool);
        }

        return result;
    }

    /**
     * Retrieves the type of given data type.
     * @param dataType the data type.
     * @return the QueryJ type.
     */
    public String getStatementSetterFieldType(final int dataType)
    {
        String result = null;

        switch (dataType)
        {
            case Types.TIME:
            case Types.DATE:
            case Types.TIMESTAMP:
            case 11:
                result = "Timestamp";
                break;

            default:
                result = super.getStatementSetterFieldType(dataType);
                break;
        }

        return result;
    }

}
