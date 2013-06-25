/*
                        QueryJ

    Copyright (C) 2013-today  Jose San Leandro Armendariz
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
 * Filename: TemplatePackagingMojo.java
 *
 * Author: Jose San Leandro Armendariz.
 *
 * Description: Generates required QueryJ boilerplate from user-provided
 *              templates.
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing some ACM-SL classes.
 */

/*
 * Importing some Maven classes.
 */
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

/*
 * Importing some Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Generates required QueryJ boilerplate from user-provided templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 * @goal package-templates
 * @execute phase="generate-sources"
 * @threadSafe
 * @since 2013/06/16
 */
@SuppressWarnings("unused")
@ThreadSafe
public class TemplatePackagingMojo
    extends AbstractMojo
    implements Mojo
{
    /**
     * The location of pom.properties within the jar file.
     */
    protected static final String POM_PROPERTIES_LOCATION =
        "META-INF/maven/org.acmsl/queryj/templates/packaging/pom.properties";

    /**
     * Executes QueryJ via Maven2.
     * @throws MojoExecutionException if something goes wrong.
     */
    public void execute()
        throws MojoExecutionException
    {
        execute(getLog());
    }

    /**
     * Executes QueryJ via Maven2.
     * @param log the Maven log.
     * @throws MojoExecutionException if something goes wrong.
     */
    protected void execute(@NotNull final Log log)
        throws MojoExecutionException
    {
        execute(log, retrieveVersion(retrievePomProperties(log)));
    }

    /**
     * Retrieves the pom.properties bundled within the QueryJ jar.
     * @param log the Maven log.
     * @return such information.
     */
    @Nullable
    protected Properties retrievePomProperties(@NotNull final Log log)
    {  
        @Nullable Properties result = null;

        try
        {  
            InputStream pomProperties =
                getClass().getClassLoader().getResourceAsStream(POM_PROPERTIES_LOCATION);

            result = new Properties();

            result.load(pomProperties);
        }
        catch (@NotNull final IOException ioException)
        {  
            log.warn(
                "Strange... Cannot read my own " + POM_PROPERTIES_LOCATION,
                ioException);
        }

        return result;
    }

    /**
     * Retrieves the version of Template Packaging currently running.
     * @param properties the pom.properties information.
     * @return the version entry.
     */
    protected String retrieveVersion(@Nullable final Properties properties)
    {
        String result = "(unknown)";

        if (   (properties != null)
            && (properties.containsKey("version")))
        {
            result = properties.getProperty("version");
        }

        return result;
    }

    /**
     * Executes QueryJ via Maven2.
     * @param log the Maven log.
     * @param version the QueryJ version.
     * @throws MojoExecutionException if something goes wrong.
     */
    protected void execute(@NotNull final Log log, final String version)
        throws MojoExecutionException
    {
        // TODO
    }
}
