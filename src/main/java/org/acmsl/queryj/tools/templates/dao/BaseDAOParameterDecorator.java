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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 *****************************************************************************
 *
 * Filename: BaseDAOParameterDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates parameters for BaseDAO template.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.customsql.Parameter;
import org.acmsl.queryj.tools.metadata.CachingParameterDecorator;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeUtils;

/**
 * Decorates parameters for BaseDAO template.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class BaseDAOParameterDecorator
    extends  CachingParameterDecorator
{
    /**
     * Creates a <code>BaseDAOParameterDecorator</code> with the
     * <code>Parameter</code> to decorate.
     * @param parameter the parameter.
     * @param metadataManager the metadata manager.
     * @precondition parameter != null
     * @precondition metadataManager != null
     */
    public BaseDAOParameterDecorator(
        final Parameter parameter,
        final MetadataManager metadataManager)
    {
        super(parameter, metadataManager);
    }

    /**
     * Retrieves the Java type of the parameter, which would be
     * only a primitive Java type if the parameter type matches,
     * and the column allows nulls.
     * @return such information.
     */
    public String getJavaType()
    {
        String result = getCachedJavaType();

        if  (result == null)
        {
            result = retrieveJavaType();
            setCachedJavaType(result);
        }

        return result;
    }
}
