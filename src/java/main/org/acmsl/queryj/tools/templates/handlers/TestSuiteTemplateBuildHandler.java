/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendáriz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

    Thanks to ACM S.L. for distributing this library under the GPL license.
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabañas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendáriz
 *
 * Description: Builds a test suite template.
 *
 * Last modified by: $Author$ at $Date$
 *
 * File version: $Revision$
 *
 * Project version: $Name$
 *
 * $Id$
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.AntCommand;
import org.acmsl.queryj.tools.handlers.AntCommandHandler;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;
import org.acmsl.queryj.tools.templates.TestSuiteTemplate;
import org.acmsl.queryj.tools.templates.TestSuiteTemplateGenerator;
import org.acmsl.queryj.tools.templates.TestTemplate;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Command;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.commons.version.Version;
import org.acmsl.commons.version.VersionFactory;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.BuildException;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/*
 * Importing Jakarta Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Builds a test suite template.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class TestSuiteTemplateBuildHandler
    implements  AntCommandHandler
{
    /**
     * The test suite template attribute name.
     */
    public static final String TEST_SUITE_TEMPLATE = "test.suite.templates";

    /**
     * Creates a TestSuiteTemplateBuildHandler.
     */
    public TestSuiteTemplateBuildHandler() {};

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     */
    public boolean handle(Command command)
    {
        boolean result = false;

        if  (command instanceof AntCommand) 
        {
            try 
            {
                result = handle((AntCommand) command);
            }
            catch  (BuildException buildException)
            {
                LogFactory.getLog(getClass()).error(
                    "unhandled.exception",
                    buildException);
            }
        }
        
        return result;
    }

    /**
     * Handles given command.
     * @param command the command to handle.
     * @return <code>true</code> if the chain should be stopped.
     * @throws BuildException if the build process cannot be performed.
     */
    public boolean handle(AntCommand command)
        throws  BuildException
    {
        boolean result = false;

        if  (command != null) 
        {
            Map attributes = command.getAttributeMap();

            String t_strPackage = retrievePackage(attributes);

            TestSuiteTemplateGenerator t_TestSuiteTemplateGenerator =
                TestSuiteTemplateGenerator.getInstance();

            StringUtils t_StringUtils = StringUtils.getInstance();

            if  (   (t_StringUtils                != null)
                 && (t_TestSuiteTemplateGenerator != null))
            {
                TestSuiteTemplate t_TestSuiteTemplate =
                    t_TestSuiteTemplateGenerator.createTestSuiteTemplate(
                        t_strPackage,
                        t_StringUtils.extractPackageName(t_strPackage));

                Collection t_cTestTemplates =
                    retrieveTestTemplates(attributes);

                if  (t_cTestTemplates != null)
                {
                    Iterator t_itTestTemplates = t_cTestTemplates.iterator();

                    while  (   (t_itTestTemplates != null)
                            && (t_itTestTemplates.hasNext()))
                    {
                        TestTemplate t_TestTemplate =
                            (TestTemplate) t_itTestTemplates.next();

                        if  (t_TestTemplate != null)
                        {
                            t_TestSuiteTemplate.addTestCase(
                                t_TestTemplate);
                        }
                    }
                }
                
                storeTestSuiteTemplate(t_TestSuiteTemplate, attributes);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the package name from the attribute map.
     * @param parameters the parameter map.
     * @return the package name.
     * @throws BuildException if the package retrieval process if faulty.
     */
    protected String retrievePackage(Map parameters)
        throws  BuildException
    {
        String result = null;

        if  (parameters != null)
        {
            result =
                (String) parameters.get(ParameterValidationHandler.PACKAGE);
        }
        
        return result;
    }

    /**
     * Retrieves the test template collection.
     * @param parameters the parameter map.
     * @return the test templates.
     * @throws BuildException if the test template retrieval process if faulty.
     */
    protected Collection retrieveTestTemplates(Map parameters)
        throws  BuildException
    {
        Collection result = null;

        if  (parameters != null)
        {
            result =
                (Collection)
                    parameters.get(
                        TemplateMappingManager.TEST_TEMPLATES);
        }
        
        return result;
    }

    /**
     * Stores a new test suite template.
     * @param testSuiteTemplate the test suite template.
     * @param parameters the parameter map.
     * @throws BuildException if the test template retrieval process if faulty.
     */
    protected void storeTestSuiteTemplate(TestSuiteTemplate testSuiteTemplate, Map parameters)
        throws  BuildException
    {
        if  (   (testSuiteTemplate != null)
             && (parameters        != null))
        {
            parameters.put(TEST_SUITE_TEMPLATE, testSuiteTemplate);
        }
    }

    /**
     * Concrete version object updated everytime it's checked-in in a
     * CVS testTemplate.
     */
    public static final Version VERSION =
        VersionFactory.createVersion("$Revision$");

    /**
     * Retrieves the current version of this object.
     * @return the version object with such information.
     */
    public Version getVersion()
    {
        return VERSION;
    }

    /**
     * Retrieves the current version of this class. It's defined because
     * this is a utility class that cannot be instantiated.
     * @return the object with class version information.
     */
    public static Version getClassVersion()
    {
        return VERSION;
    }
}
