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

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Decorator;
import org.jetbrains.annotations.Nullable;

/**
 * Decorates &lt;parameter&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface ParameterDecorator
    extends  Parameter,
             Decorator
{
    /**
     * Retrieves the decorated parameter.
     * @return such instance.
     */
    public Parameter getParameter();

    /**
     * Retrieves the sql type of the parameter.
     * @return such information.
     * @see java.sql.Types
     */
    @Nullable
    public String getSqlType();

    /**
     * Retrieves the object type of the parameter.
     * @return such information.
     */
    @Nullable
    public String getObjectType();

    /**
     * Retrieves whether the parameter type is a class or not.
     * @return such information.
     */
    public boolean isObject();

    /**
     * Retrieves whether the parameter type is a String or not.
     * @return such information.
     */
    public boolean isString();

    /**
     * Retrieves the field type of the parameter.
     * @return such information.
     */
    public String getFieldType();
}
