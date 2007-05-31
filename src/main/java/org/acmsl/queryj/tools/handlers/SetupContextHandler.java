//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: SetupContextHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Sets up some common stuff required by remaining handlers.
 *
 */
package org.acmsl.queryj.tools.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AbstractAntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.SingularPluralFormConverter;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.EnglishGrammarUtils;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.Map;

/**
 * Sets up some common stuff required by remaining handlers.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class SetupContextHandler
    extends  AbstractAntCommandHandler
{
    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(final AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            handle(command.getAttributeMap());
        }

        return result;
    }

    /**
     * Handles given command (i.e. its parameters).
     * @param parameters the parameters to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    protected boolean handle(final Map parameters)
        throws  BuildException
    {
        boolean result = false;

        if  (parameters != null) 
        {
            setUpContext(parameters);
            setQueryJVersion(parameters);
        }
        
        return result;
    }

    /**
     * Sets up the flow context.
     * @param parameters the parameter map.
     * @return <code>false</code> if the chain should be stopped because
     * of invalid parameters.
     */
    public void setUpContext(final Map parameters)
    {
        if  (parameters != null) 
        {
            SingularPluralFormConverter.setGrammarBundle(
                (File)
                    parameters.get(
                        ParameterValidationHandler.GRAMMAR_BUNDLE_NAME));
        }
    }

    /**
     * Retrieves the QueryJ version and annotates it into the map.
     * @param parameters the parameters.
     * @return such version.
     * @throws BuildException if the version cannot be retrieved.
     * @precondition parameters != null
     */
    protected String setQueryJVersion(final Map parameters)
        throws  BuildException
    {
        String result = null;

        try
        {
            InputStream t_isInputStream =
                getManifestForClass(SetupContextHandler.class);

            if  (t_isInputStream != null)
            {
                Manifest t_Manifest = new Manifest(t_isInputStream);

                Attributes t_Attribute = t_Manifest.getAttributes("common");

                if  (t_Attribute != null)
                {
                    result = t_Attribute.getValue("Implementation-Version");
                }

                if  (result != null)
                {
                    parameters.put(
                        ParameterValidationHandler.QUERYJ_VERSION,
                        result);
                }
            }
        }
        catch  (final IOException ioException)
        {
            Log t_Log =
                UniqueLogFactory.getLog(SetupContextHandler.class);
                
            if  (t_Log != null)
            {
                t_Log.error(
                    "Cannot parse QueryJ's manifest file.",
                    ioException);
            }

            throw new BuildException(ioException);
        }

        return result;
    }

    /**
     * Retrieves the Manifest input stream for given class.
     * @param clazz such class.
     * @return the InputStream, or <tt>null</tt> otherwise.
     * @precondition clazz != null
     */
    private static InputStream getManifestForClass(final Class clazz)
        throws IOException
    {
        InputStream result = null;

        String clazzResName = clazz.getName().replace('.', '/') + ".class";

        URL urlToClass = clazz.getClassLoader().getResource(clazzResName);

        String strUrl = urlToClass.toString();

        int index = strUrl.lastIndexOf('!');

        if  (index != -1)
        {
            //...class was actually loaded from a JAR file...
            String strUrlBase = strUrl.substring(0, index + 1);

            URL urlToManifest = null;

            try
            {
                urlToManifest = new URL(strUrlBase + "/META-INF/MANIFEST.MF");
                result = urlToManifest.openStream();
            }
            catch (final MalformedURLException malformedURLException)
            {
                //...really shouldn't happen since we built new URL from an
                //   existing valid one...
                Log t_Log =
                    UniqueLogFactory.getLog(SetupContextHandler.class);
                
                if  (t_Log != null)
                {
                    t_Log.error(
                        "Cannot retrieve QueryJ's manifest file.",
                        malformedURLException);
                }
            }
        }

        return result;
    }
}
