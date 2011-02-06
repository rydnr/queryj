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
 * Filename: ProcedureRepositoryTemplateUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some useful methods when generating
 *              ProcedureRepository class.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;
import org.acmsl.commons.utils.StringUtils;

/**
 * Provides some useful methods when generating ProcedureRepository class.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class ProcedureRepositoryTemplateUtils
    implements  Singleton,
                Utils
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class ProcedureRepositoryTemplateUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final ProcedureRepositoryTemplateUtils SINGLETON =
            new ProcedureRepositoryTemplateUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected ProcedureRepositoryTemplateUtils() {};

    /**
     * Retrieves a <code>ProcedureRepositoryTemplateUtils</ocde> instance.
     * @return such instance.
     */
    public static ProcedureRepositoryTemplateUtils getInstance()
    {
        return ProcedureRepositoryTemplateUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the name of the <code>ProcedureRepository</code> instance.
     * @param repository the repository name.
     * @return such name.
     * @precondition repository != null
     */
    public String retrieveProcedureRepositoryClassName(final String repository)
    {
        return
            retrieveProcedureRepositoryClassName(
                repository, StringUtils.getInstance());
    }

    /**
     * Retrieves the name of the <code>ProcedureRepository</code> instance.
     * @param repository the repository name.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such name.
     * @precondition repository != null
     * @precondition stringUtils != null
     */
    protected String retrieveProcedureRepositoryClassName(
        final String repository, final StringUtils stringUtils)
    {
        return stringUtils.normalize(repository, '_') + "ProcedureRepository";
    }
}
