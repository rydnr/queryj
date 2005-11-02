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
    Contact info: chous@acm-sl.org
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
 * Description: Provides insight about the meta-language used in model
 *              descriptions.
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Utils;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;

/**
 * Provides insight about the meta-language used in model descriptions.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class MetaLanguageUtils
    implements Utils
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected MetaLanguageUtils() {};

    /**
     * Specifies a new weak reference.
     * @param utils the utils instance to use.
     */
    protected static void setReference(final MetaLanguageUtils utils)
    {
        singleton = new WeakReference(utils);
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
     * Retrieves a <code>MetaLanguageUtils< instance.
     * @return such instance.
     */
    public static MetaLanguageUtils getInstance()
    {
        MetaLanguageUtils result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (MetaLanguageUtils) reference.get();
        }

        if  (result == null) 
        {
            result = new MetaLanguageUtils();

            setReference(result);
        }

        return result;
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
     * @precondition tableCOmment != null
     */
    public String retrieveStaticAttribute(final String tableComment)
    {
        String result = null;
        
        int t_iKeyIndex = tableComment.lastIndexOf("@static");

        if  (t_iKeyIndex >= 0)
        {
            result =
                tableComment.substring(
                    t_iKeyIndex + "@static".length()).trim();
        }

        return result;
    }
}
