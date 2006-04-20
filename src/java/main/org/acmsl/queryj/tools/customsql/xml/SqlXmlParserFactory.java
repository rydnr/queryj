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
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create sql.xml parsers.
 *
 */
package org.acmsl.queryj.tools.customsql.xml;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.customsql.xml.SqlXmlParser;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/*
 * Importing some commons-logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Is able to create <b>sql.xml</b> parsers.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class SqlXmlParserFactory
{
    /**
     * The default file location.
     */
    protected static final String FILE_PATH = "sql.xml";

    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     * @param alias the table alias.
     */
    protected SqlXmlParserFactory() { };

    /**
     * Specifies a new weak reference.
     * @param factory the new factory instance.
     */
    protected static void setReference(final SqlXmlParserFactory factory)
    {
        singleton = new WeakReference(factory);
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
     * Retrieves a SqlXmlParserFactory instance.
     * @return such instance.
     */
    public static SqlXmlParserFactory getInstance()
    {
        SqlXmlParserFactory result = null;

        WeakReference t_Reference = getReference();

        if  (t_Reference != null) 
        {
            result = (SqlXmlParserFactory) t_Reference.get();
        }

        if  (result == null) 
        {
            result = new SqlXmlParserFactory() { };

            setReference(result);
        }

        return result;
    }

    /**
     * Creates a <b>sql.xml</b> parser.
     * @return such parser.
     */
    public SqlXmlParser createSqlXmlParser()
    {
        return createSqlXmlParser(FILE_PATH);
    }

    /**
     * Creates a <b>sql.xml</b> parser.
     * @param inputFilePath the file path (retrieved via Class.getResourceAsStream() or
     * new FileInputStream())).
     * @return such parser.
     * @precondition inputFilePath != null
     */
    public SqlXmlParser createSqlXmlParser(final String inputFilePath)
    {
        SqlXmlParser result = null;

        InputStream t_isInput = getClass().getResourceAsStream(inputFilePath);

        if  (t_isInput == null)
        {
            t_isInput = getClass().getResourceAsStream("/" + inputFilePath);
        }

        if  (t_isInput == null)
        {
            try
            {
                t_isInput = new FileInputStream(inputFilePath);
            }
            catch  (final FileNotFoundException fileNotFoundException)
            {
                try
                {
                    LogFactory.getLog(getClass()).error(
                        "Specified sql.xml file does not exist " + inputFilePath,
                        fileNotFoundException);
                }
                catch  (final Throwable throwable)
                {
                    // class-loading problem.
                }
            }
        }

        if  (t_isInput == null)
        {
            try
            {
                LogFactory.getLog(getClass()).error(
                    "No sql.xml information found at " + inputFilePath);
            }
            catch  (final Throwable throwable)
            {
                // class-loading problem.
            }
        }
        else
        {
            result = new SqlXmlParser(t_isInput) { };
        }

        return result;
    }

    /**
     * Creates a <b>sql.xml</b> parser.
     * @param file the file.
     * @return such parser.
     * @precondition file != null
     */
    public SqlXmlParser createSqlXmlParser(final File file)
    {
        SqlXmlParser result = null;

        try
        {
            result = new SqlXmlParser(new FileInputStream(file)) {};
        }
        catch  (final FileNotFoundException fileNotFoundException)
        {
            try
            {
                LogFactory.getLog(getClass()).error(
                    "no sql.xml information found at " + file,
                    fileNotFoundException);
            }
            catch  (final Throwable throwable)
            {
                // class-loading problem.
            }
        }

        return result;
    }

}

