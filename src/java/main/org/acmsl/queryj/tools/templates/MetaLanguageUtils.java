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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides insight about the meta-language used in model
 *              descriptions.
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;

/**
 * Provides insight about the meta-language used in model descriptions.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class MetaLanguageUtils
    implements  Singleton,
                Utils
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class MetaLanguageUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final MetaLanguageUtils SINGLETON =
            new MetaLanguageUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected MetaLanguageUtils() {};

    /**
     * Retrieves a <code>MetaLanguageUtils</code> instance.
     * @return such instance.
     */
    public static MetaLanguageUtils getInstance()
    {
        return MetaLanguageUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Checks whether given table is static.
     * @param tableComment the table's comment.
     * @return <code>true</code> in such case.
     * @precondition tableComment != null
     */
    public boolean isStatic(final String tableComment)
    {
        return (retrieveStaticAttribute(tableComment) != null);
    }

    /**
     * Retrieves the attribute used to identify table content's
     * staticly.
     * @param tableComment the table's comment.
     * @return such attribute.
     */
    public String retrieveStaticAttribute(final String tableComment)
    {
        String result = null;
        
        int t_iKeyIndex = -1;

        if  (tableComment != null)
        {
            t_iKeyIndex = tableComment.lastIndexOf("@static");
        }

        if  (t_iKeyIndex >= 0)
        {
            result =
                tableComment.substring(
                    t_iKeyIndex + "@static".length()).trim();
        }

        return result;
    }
}
