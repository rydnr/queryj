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
 * Filename: ExternallyManagedField.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models a field managed outside QueryJ.
 */
package org.acsml.queryj.tools.maven;

/**
 * Models an externally-managed field.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class ExternallyManagedField
{
    /**
     * The name.
     */
    private String m__strName;
    
    /**
     * The table name.
     */
    private String m__strTableName;
    
    /**
     * The keyword.
     */
    private String m__strKeyword;
    
    /**
     * The retrieval query.
     */
    private String m__strRetrievalQuery;
    
    /**
     * Returns the name.
     * @return such value.
     */
    protected final String immutableGetName()
    {
        return m__strName;
    }

    /**
     * Returns the name.
     * @return such value.
     */
    protected String getName()
    {
        return immutableGetName();
    }

    /**
     * Returns the table name.
     * @return such value.
     */
    protected final String immutableGetTableName()
    {
        return m__strTableName;
    }
    
    /**
     * Returns the table name.
     * @return such value.
     */
    protected String getTableName()
    {
        return immutableGetTableName();
    }
    
    /**
     * Returns the keyword.
     * @return such value.
     */
    protected final String immutableGetKeyword() 
    {
        return m__strKeyword;
    }
    
    /**
     * Returns the keyword.
     * @return such value.
     */
    protected String getKeyword() 
    {
        return immutableGetKeyword();
    }
    
    /**
     * Returns the retrieval query.
     * @return such value.
     */
    protected final String immutableGetRetrievalQuery()
    {
        return m__strRetrievalQuery;
    }
    
    /**
     * Returns the retrieval query.
     * @return such value.
     */
    protected String getRetrievalQuery()
    {
        return immutableGetRetrievalQuery();
    }
}
