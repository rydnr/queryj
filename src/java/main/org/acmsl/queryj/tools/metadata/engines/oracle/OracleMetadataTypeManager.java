/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile$
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
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;
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
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Creates an empty <code>OracleMetadataTypeManager</code>.
     */
    public OracleMetadataTypeManager() {};
    
    /**
     * Specifies a new weak reference.
     * @param manager the manager instance to use.
     */
    protected static void setReference(
        final OracleMetadataTypeManager manager)
    {
        singleton = new WeakReference(manager);
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
     * Retrieves an <code>OracleMetadataTypeManager< instance.
     * @return such instance.
     */
    public static JdbcMetadataTypeManager getInstance()
    {
        JdbcMetadataTypeManager result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (OracleMetadataTypeManager) reference.get();
        }

        if  (result == null) 
        {
            result = new OracleMetadataTypeManager();

            setReference(result);
        }

        return result;
    }

    /**
     * Checks if given data type represents integers.
     * @param dataType the data type.
     * @return <code>true</code> if such data type can be managed as an
     * integer.
     */
    public boolean isClob(final int dataType)
    {
        boolean result = false;

        switch (dataType)
        {
            case Types.OTHER:
                result = true;
                break;
            case Types.CLOB:
                result = true;
                break;
            default:
                result = false;
                break;
        }

        return result;
    }
}
