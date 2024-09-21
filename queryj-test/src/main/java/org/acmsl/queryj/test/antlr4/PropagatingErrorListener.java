/*
                        QueryJ Test

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
 * Filename: PropagatingErrorListener.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Makes ANTLR4 parser to throw an exception if the parsed file
 *              contains errors.
 *
 * Date: 2014/05/03
 * Time: 07:48
 *
 */
package org.acmsl.queryj.test.antlr4;

/*
 * Importing JetBrains annotations.
 */
import org.acmsl.queryj.test.InvalidJavaOutputException;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.Nullable;
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

import java.io.File;
import java.io.Serializable;

/**
 * Makes ANTLR4 parser to throw an exception if the parsed file contains errors.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/05/03 07:48
 */
@ThreadSafe
public class PropagatingErrorListener
    extends BaseErrorListener
    implements Serializable
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -576525682622721105L;

    /**
     * The Java file.
     */
    private File m__File;

    /**
     * Creates an error listener for given file.
     * @param file the file.
     */
    public PropagatingErrorListener(@NotNull final File file)
    {
        immutableSetFile(file);
    }

    /**
     * Specifies the file.
     * @param file the file.
     */
    protected final void immutableSetFile(@NotNull final File file)
    {
        this.m__File = file;
    }

    /**
     * Specifies the file.
     * @param file the file.
     */
    @SuppressWarnings("unused")
    protected void setFile(@NotNull final File file)
    {
        immutableSetFile(file);
    }

    /**
     * Retrieves the file.
     * @return such file.
     */
    @NotNull
    public File getFile()
    {
        return this.m__File;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void syntaxError(
        @org.antlr.v4.runtime.misc.NotNull final Recognizer<?, ?> recognizer,
        @org.antlr.v4.runtime.misc.Nullable final Object offendingSymbol,
        final int line,
        final int charPositionInLine,
        @org.antlr.v4.runtime.misc.NotNull final String msg,
        @org.antlr.v4.runtime.misc.Nullable final RecognitionException error)
    {
        syntaxError(line, charPositionInLine, msg, error, getFile());
    }

    /**
     * Manages a syntax error and throws a runtime exception.
     * @param line the line number in the file.
     * @param column the column.
     * @param message the error message.
     * @param error the error.
     * @param file the parsed file.
     */
    protected void syntaxError(
        final int line,
        final int column,
        @NotNull final String message,
        @Nullable final RecognitionException error,
        @NotNull final File file)
    {
        @NotNull final InvalidJavaOutputException exception;

        if (error == null)
        {
            exception = new InvalidJavaOutputException(file, line, column, message);
        }
        else
        {
            exception = new InvalidJavaOutputException(file, line, column, message, error);
        }

        throw exception;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"PropagatingErrorListener\""
            + ", \"file\": \"" + m__File.getAbsolutePath()
            + ", \"package\": \"org.acmsl.queryj.test.antlr4\" }";
    }
}
