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
 * Description: Provides some useful methods when generating DAOChooser class.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;

/**
 * Provides some useful methods when generating DAOChooser class.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class DAOChooserTemplateUtils
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DAOChooserTemplateUtils() {};

    /**
     * Specifies a new weak reference.
     * @param utils the utils instance to use.
     */
    protected static void setReference(final DAOChooserTemplateUtils utils)
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
     * Retrieves a <code>DAOChooserTemplateUtils</code> instance.
     * @return such instance.
     */
    public static DAOChooserTemplateUtils getInstance()
    {
        DAOChooserTemplateUtils result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (DAOChooserTemplateUtils) reference.get();
        }

        if  (result == null) 
        {
            result = new DAOChooserTemplateUtils();

            setReference(result);
        }

        return result;
    }

    /**
     * Retrieves the name of the <code>DAOChooser</code> properties
     * file.
     * @param repository the repository name.
     * @return such name.
     * @precondition repository != null
     */
    public String retrievePropertiesFileName(final String repository)
    {
        return repository.toLowerCase() + "-queryj.properties";
    }
}
