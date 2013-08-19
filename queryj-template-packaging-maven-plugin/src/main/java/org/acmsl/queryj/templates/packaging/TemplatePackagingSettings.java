/*
                        QueryJ Template Packaging

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
 * Filename: TemplatePackagingSettings.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines the settings used by template packaging.
 *
 * Date: 2013/08/11
 * Time: 08:43
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Defines the settings used by template packaging.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/11 08:43
 */
@ThreadSafe
public interface TemplatePackagingSettings
{
    /**
     * The source folders.
     */
    String SOURCES = "sources";

    /**
     * The def files.
     */
    String DEF_FILES = "def_files";

    /**
     * The template defs.
     */
    String TEMPLATE_DEFS = "template_defs";

    /**
     * The template packaging group.
     */
    String TEMPLATE_PACKAGING_GROUP = "org/acmsl/queryj/templates/packaging/";
}
