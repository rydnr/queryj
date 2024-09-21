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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: ForeignKey.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents foreign keys.
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing JDK classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * Represents foreign keys.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public interface ForeignKey<V>
    extends Comparable<ForeignKey<V>>,
            Serializable
{
    /**
     * Retrieves the foreign key name (optional).
     * @return such name.
     */
    @Nullable
    public V getFkName();

    /**
     * Retrieves the source table name.
     * @return such table name.
     */
    @NotNull
    public V getSourceTableName();
    
    /**
     * Retrieves the attributes.
     * @return such information.
     */
    @NotNull
    public List<Attribute<V>> getAttributes();
    
    /**
     * Retrieves the target table name.
     * @return such table name.
     */
    @NotNull
    public V getTargetTableName();

    /**
     * Retrieves whether the foreign key can take null values.
     * @return such information.
     */
    public boolean isNullable();
}
