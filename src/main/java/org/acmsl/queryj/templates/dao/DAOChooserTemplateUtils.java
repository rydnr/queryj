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
 * Filename: DAOChooserTemplateUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some useful methods when generating DAOChooser class.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Provides some useful methods when generating DAOChooser class.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DAOChooserTemplateUtils
    implements  Singleton,
                Utils
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class DAOChooserTemplateUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final DAOChooserTemplateUtils SINGLETON =
            new DAOChooserTemplateUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected DAOChooserTemplateUtils() {}

    /**
     * Retrieves a <code>DAOChooserTemplateUtils</code> instance.
     * @return such instance.
     */
    @NotNull
    public static DAOChooserTemplateUtils getInstance()
    {
        return DAOChooserTemplateUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the name of the <code>DAOChooser</code> properties
     * file.
     * @param repository the repository name.
     * @return such name.
     * @precondition repository != null
     */
    @NotNull
    public String retrievePropertiesFileName(@NotNull final String repository)
    {
        return repository.toLowerCase(Locale.US) + "-queryj.properties";
    }
}
