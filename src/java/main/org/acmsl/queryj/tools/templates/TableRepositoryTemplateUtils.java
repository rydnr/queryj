/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
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
 *              TableRepository class.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;

/**
 * Provides some useful methods when generating TableRepository class.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 */
public class TableRepositoryTemplateUtils
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TableRepositoryTemplateUtils() {};

    /**
     * Specifies a new weak reference.
     * @param utils the utils instance to use.
     */
    protected static void setReference(
        final TableRepositoryTemplateUtils utils)
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
     * Retrieves a <code>TableRepositoryTemplateUtils</code> instance.
     * @return such instance.
     */
    public static TableRepositoryTemplateUtils getInstance()
    {
        TableRepositoryTemplateUtils result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (TableRepositoryTemplateUtils) reference.get();
        }

        if  (result == null) 
        {
            result = new TableRepositoryTemplateUtils();

            setReference(result);
        }

        return result;
    }

    /**
     * Retrieves the name of the <code>TableRepository</code> instance.
     * @param repository the repository name.
     * @return such name.
     * @precondition repository != null
     */
    public String retrieveTableRepositoryClassName(final String repository)
    {
        return
            retrieveTableRepositoryClassName(
                repository, StringUtils.getInstance());
    }

    /**
     * Retrieves the name of the <code>TableRepository</code> instance.
     * @param repository the repository name.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such name.
     * @precondition repository != null
     * @precondition stringUtils != null
     */
    protected String retrieveTableRepositoryClassName(
        final String repository, final StringUtils stringUtils)
    {
        return stringUtils.normalize(repository, '_') + "TableRepository";
    }
}
