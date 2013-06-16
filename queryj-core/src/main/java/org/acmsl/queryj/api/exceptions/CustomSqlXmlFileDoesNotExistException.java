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
 * Filename: CustomSqlXmlFileDoesNotExistException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents an error when the XML file with the custom SQL
 * sentences does not exist.
 *
 * Date: 6/16/13
 * Time: 3:28 PM
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.io.File;

/**
 * Represents an error when the XML file with the custom SQL sentences
 * does not exist.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/16
 */
@ThreadSafe
public class CustomSqlXmlFileDoesNotExistException
    extends QueryJBuildException
{
    /**
     * Creates an instance with given context.
     * @param file the file.
     */
    public CustomSqlXmlFileDoesNotExistException(@NotNull final File file)
    {
        super("custom-sql.xml.file.does.not.exist", new Object[] { file.getAbsolutePath() });
    }
}
