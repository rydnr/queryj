//;-*- mode: java -*-
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

 *****************************************************************************
 *
 * Filename: ForeignKey.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents foreign keys.
 *
 */
package org.acmsl.queryj.tools.metadata.vo;

/*
 * Importing JDK classes.
 */
import java.io.Serializable;
import java.util.Collection;

/**
 * Represents foreign keys.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface ForeignKey
    extends  java.lang.Comparable,
             Serializable
{
    /**
     * Retrieves the source table name.
     * @return such table name.
     */
    public String getSourceTableName();
    
    /**
     * Retrieves the attributes.
     * @return such information.
     */
    public Collection getAttributes();
    
    /**
     * Retrieves the target table name.
     * @return such table name.
     */
    public String getTargetTableName();

    /**
     * Retrieves wheter the foreign key can take null values.
     * @return such information.
     */
    public boolean getAllowsNull();
}
