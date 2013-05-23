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
 * Filename: OtherBundle.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Bundles the complete set of handlers in package "other".
 *
 * Date: 5/23/13
 * Time: 6:50 PM
 *
 */
package org.acmsl.queryj.templates.other;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.templates.handlers.TemplateHandlerBundle;
import org.acmsl.queryj.templates.other.handlers.CucumberFeatureTemplateHandlerBundle;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Bundles the complete set of handlers in package "other".
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @since 2013/05/23
 */
@ThreadSafe
public class OtherBundle
    extends TemplateHandlerBundle
{
    /**
     * Builds a bundle with all handlers under package "other".
     */
    public OtherBundle()
    {
        super(
            new TemplateHandlerBundle[]
                {
                    new CucumberFeatureTemplateHandlerBundle()
                });
    }

    /**
     * Displays information about this bundle.
     * @return such information.
     */
    @NotNull
    @Override
    public String toString()
    {
        return "(Other bundle)";
    }
}
