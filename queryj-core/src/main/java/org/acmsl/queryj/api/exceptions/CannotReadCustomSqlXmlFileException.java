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
 * Filename: CannotReadCustomSqlXmlFileException.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents the error when XML file with the custom SQL cannot
 *              be read.
 *
 * Date: 6/13/13
 * Time: 9:54 PM
 *
 */
package org.acmsl.queryj.api.exceptions;

/*
 * Importing NotNull annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.io.File;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Represents the error when XML file with the custom SQL cannot be read.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/06/13
 */
@ThreadSafe
public class CannotReadCustomSqlXmlFileException
    extends QueryJBuildException
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -8064000429461780536L;

    protected static final String CANNOT_READ_CUSTOM_SQL_XML_FILE = "cannot.read.custom-sql.xml.file";

    /**
     * Creates an instance with given context.
     * @param file the file.
     */
    public CannotReadCustomSqlXmlFileException(@Nullable final File file)
    {
        super(CANNOT_READ_CUSTOM_SQL_XML_FILE, new Object[]{file != null ? file.getAbsolutePath() : "(null)"});
    }

    /**
     * Creates an instance with given context.
     * @param file the custom SQL file.
     * @param cause the exception to wrap.
     */
    public CannotReadCustomSqlXmlFileException(
        @Nullable final File file, @NotNull final Throwable cause)
    {
        super(CANNOT_READ_CUSTOM_SQL_XML_FILE, new Object[]{file != null ? file.getAbsolutePath() : "(null)"}, cause);
    }
}
