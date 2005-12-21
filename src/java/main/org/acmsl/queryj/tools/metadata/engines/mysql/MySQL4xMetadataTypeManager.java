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
 * Description: Provides the basic translation and management services
 *              related to database attribute types, according to strict
 *              JDBC specification.
 *
 */
package org.acmsl.queryj.tools.metadata.engines.mysql;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.engines.JdbcMetadataTypeManager;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;
import java.sql.Types;
import java.util.Map;
import java.util.HashMap;

/**
 * Provides the core methods when working with database metadata.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class MySQL4xMetadataTypeManager
    extends  JdbcMetadataTypeManager
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Creates an empty <code>MySQL4xMetadataTypeManager</code>.
     */
    public MySQL4xMetadataTypeManager() {};

    /**
     * Specifies a new weak reference.
     * @param manager the manager instance to use.
     */
    private static void setReference(final MySQL4xMetadataTypeManager manager)
    {
        singleton = new WeakReference(manager);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    private static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a <code>MySQL4xMetadataTypeManager</code> instance.
     * @return such instance.
     */
    public static JdbcMetadataTypeManager getInstance()
    {
        MySQL4xMetadataTypeManager result = null;

        WeakReference t_Reference = getReference();

        if  (t_Reference != null)
        {
            result = (MySQL4xMetadataTypeManager) t_Reference.get();
        }

        if  (result == null)
        {
            result = new MySQL4xMetadataTypeManager();

            setReference(result);
        }

        return result;
    }

    /**
     * Retrieves the object type of given data type.
     * @param dataType the data type.
     * @return the associated object type.
     */
    public String getSmartObjectType(final int dataType)
    {
        String result = null;

        switch (dataType)
        {
            case Types.INTEGER:
                result = "Long";
                break;

            default:
                result = super.getSmartObjectType(dataType);
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
            case Types.INTEGER:
                result = "Long";
                break;

            default:
                result = super.getObjectType(dataType);
                break;
        }

        return result;
    }
}
