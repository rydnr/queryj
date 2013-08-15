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
import org.apache.maven.model.License;
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
    public static final String SOURCES = "sources";

    /**
     * The def files.
     */
    public static final String DEF_FILES = "def_files";

    /**
     * The template defs.
     */
    public static final String TEMPLATE_DEFS = "template_defs";

    /**
     * The header.
     */
    public static final String TEMPLATE_PACKAGING_HEADER =
          "/*\n"
        + "                        QueryJ Template Packaging\n"
        + "\n"
        + "    Copyright (C) 2002-today  Jose San Leandro Armendariz\n"
        + "                     queryj-template-packaging@acm-sl.org\n"
        + "    This library is free software; you can redistribute it and/or\n"
        + "    modify it under the terms of the GNU General Public\n"
        + "    License as published by the Free Software Foundation; either\n"
        + "    version 2 of the License, or any later version.\n"
        + "\n"
        + "    This library is distributed in the hope that it will be useful,\n"
        + "    but WITHOUT ANY WARRANTY; without even the implied warranty of\n"
        + "    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU\n"
        + "    General Public License for more details.\n"
        + "\n"
        + "    You should have received a copy of the GNU General Public\n"
        + "    License along with this library; if not, write to the Free Software\n"
        + "    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA\n"
        + "\n"
        + "    Thanks to ACM S.L. for distributing this library under the GPL license.\n"
        + "    Contact info: jose.sanleandro@acm-sl.com\n";
}
