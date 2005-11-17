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
 * Description: Provides some useful methods when generating
 *              Table class.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;

/**
 * Provides some useful methods when generating Table class.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class TableTemplateUtils
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TableTemplateUtils() {};

    /**
     * Specifies a new weak reference.
     * @param utils the utils instance to use.
     */
    protected static void setReference(final TableTemplateUtils utils)
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
     * Retrieves a <code>TableTemplateUtils</code> instance.
     * @return such instance.
     */
    public static TableTemplateUtils getInstance()
    {
        TableTemplateUtils result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (TableTemplateUtils) reference.get();
        }

        if  (result == null) 
        {
            result = new TableTemplateUtils();

            setReference(result);
        }

        return result;
    }

    /**
     * Retrieves the name of the <code>Table</code> instance.
     * @param table the table name.
     * @return such name.
     * @precondition table != null
     */
    public String retrieveTableClassName(final String table)
    {
        return
            retrieveTableClassName(
                table,
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves the name of the <code>Table</code> instance.
     * @param table the  table name.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @return such name.
     * @precondition table != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     */
    protected String retrieveTableClassName(
        final String table,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils)
    {
        return
              stringUtils.capitalize(
                  englishGrammarUtils.getSingular(
                      table.toLowerCase()))
            + "Table";
    }
}
