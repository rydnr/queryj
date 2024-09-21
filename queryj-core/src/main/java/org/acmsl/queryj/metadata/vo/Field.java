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

 ******************************************************************************
 *
 * Filename: Field.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Models the definition of table fields declared by the user.
 *
 */
package org.acmsl.queryj.metadata.vo;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

/**
 * Models the definition of table fields declared by the user.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface Field
    extends  Attribute<String>
{
    /**
     * Retrieves if the field is part of the primary key.
     * @return such information.
     */
    public boolean isPk();

    /**
     * Retrieves the field fk collection.
     * @return such collection.
     */
    public Collection<Field> getFieldFks();
}
