/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Indicates JUnit how to test PackageUtils class.
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
package unittests.org.acmsl.queryj.tools;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.queryj.tools.PackageUtils;

/*
 * Importing JUnit classes.
 */
import junit.framework.TestCase;
import junit.textui.TestRunner;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.LogFactory;

/**
 * Indicates JUnit how to test PackageUtils class.
 * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
 * @version $Revision$
 */
public class PackageUtilsTest
    extends  TestCase
{
    /**
     * The test file.
     */
    private File m__File;

    /**
     * Constructs a test case with the given name.
     * @param name the test case name.
     */
    public PackageUtilsTest(final String name)
    {
        super(name);
    }

    /**
     * Executes the tests from command line.
     * @param args the command-line arguments. Not needed so far.
     */
    public static void main(final String args[])
    {
        TestRunner.run(PackageUtilsTest.class);
    }

    /**
     * Specifies a new test file.
     * @param file a file just for testing.
     */
    protected void setTestFile(final File file)
    {
        m__File = file;
    }

    /**
     * Retrieves the test file
     * @return such file.
     */
    protected File getTestFile()
    {
        return m__File;
    }

    /**
     * Sets up the test fixture. (Called before every test case method.)
     */
    protected void setUp()
    {
        try 
        {
            File t_File = File.createTempFile("queryj", null);

            File t_TestFile =
                new File(t_File.getAbsolutePath() + "folder" + File.separator + "tmp");

            t_TestFile.mkdirs();

            setTestFile(t_TestFile);

            t_File.delete();
        }
        catch  (IOException ioException)
        {
            LogFactory.getLog(getClass()).error(
                "cannot create a temporary file",
                ioException);
        }
    }

    /**
     * Tears down the test fixture. (Called after every test case method.)
     */
    protected void tearDown()
    {
        File t_TestFile = getTestFile();

        if  (t_TestFile != null)
        {
            File t_Parent = t_TestFile.getParentFile();

            t_TestFile.delete();

            if  (t_Parent != null)
            {
                t_Parent.delete();
            }

            setTestFile(null);
        }
    }

    /**
     * Tests the retrieveBaseDAOPackage() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveBaseDAOPackage(String)
     */
    public void testRetrieveBaseDAOPackage()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        assertEquals(
            t_PackageUtils.retrieveBaseDAOPackage("com.foo.bar"),
            "com.foo.bar.dao");
    }

    /**
     * Tests the retrieveBaseDAOFolder() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveBaseDAOFolder(File,String)
     */
    public void testRetrieveBaseDAOFolder()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        File t_TestFile = getTestFile();

        assertNotNull(t_TestFile);

        File t_BaseDAOFolder = 
            t_PackageUtils.retrieveBaseDAOFolder(t_TestFile, "com.foo.bar");

        assertNotNull(t_BaseDAOFolder);

        assertEquals(
            t_BaseDAOFolder.getAbsolutePath(),
              t_TestFile.getAbsolutePath()
            + File.separator
            + "com"
            + File.separator
            + "foo"
            + File.separator
            + "bar"
            + File.separator
            + "dao");
    }

    /**
     * Tests the retrieveBaseDAOFactoryPackage() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveBaseDAOFactoryPackage(String)
     */
    public void testRetrieveBaseDAOFactoryPackage()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        assertEquals(
            t_PackageUtils.retrieveBaseDAOFactoryPackage("com.foo.bar"),
            "com.foo.bar.dao");
    }

    /**
     * Tests the retrieveBaseDAOFactoryFolder() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveBaseDAOFactoryFolder(File,String)
     */
    public void testRetrieveBaseDAOFactoryFolder()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        File t_TestFile = getTestFile();

        assertNotNull(t_TestFile);

        File t_BaseDAOFactoryFolder = 
            t_PackageUtils.retrieveBaseDAOFactoryFolder(t_TestFile, "com.foo.bar");

        assertNotNull(t_BaseDAOFactoryFolder);

        assertEquals(
            t_BaseDAOFactoryFolder.getAbsolutePath(),
              t_TestFile.getAbsolutePath()
            + File.separator
            + "com"
            + File.separator
            + "foo"
            + File.separator
            + "bar"
            + File.separator
            + PackageUtils.BASE_DAO_SUBPACKAGE);
    }

    /**
     * Tests the retrieveValueObjectPackage() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveValueObjectPackage(String)
     */
    public void testRetrieveValueObjectPackage()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        assertEquals(
            t_PackageUtils.retrieveValueObjectPackage("com.foo.bar"),
            "com.foo.bar." + PackageUtils.VALUE_OBJECT_SUBPACKAGE);
    }

    /**
     * Tests the retrieveValueObjectFolder() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveValueObjectFolder(File,String)
     */
    public void testRetrieveValueObjectFolder()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        File t_TestFile = getTestFile();

        assertNotNull(t_TestFile);

        File t_ValueObjectFolder = 
            t_PackageUtils.retrieveValueObjectFolder(t_TestFile, "com.foo.bar");

        assertNotNull(t_ValueObjectFolder);

        assertEquals(
            t_ValueObjectFolder.getAbsolutePath(),
              t_TestFile.getAbsolutePath()
            + File.separator
            + "com"
            + File.separator
            + "foo"
            + File.separator
            + "bar"
            + File.separator
            + PackageUtils.VALUE_OBJECT_SUBPACKAGE);
    }

    /**
     * Tests the retrieveValueObjectFactoryPackage() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveValueObjectFactoryPackage(String)
     */
    public void testRetrieveValueObjectFactoryPackage()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        assertEquals(
            t_PackageUtils.retrieveValueObjectFactoryPackage("com.foo.bar"),
            "com.foo.bar." + PackageUtils.VALUE_OBJECT_SUBPACKAGE);
    }

    /**
     * Tests the retrieveValueObjectFactoryFolder() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveValueObjectFactoryFolder(File,String)
     */
    public void testRetrieveValueObjectFactoryFolder()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        File t_TestFile = getTestFile();

        assertNotNull(t_TestFile);

        File t_ValueObjectFactoryFolder = 
            t_PackageUtils.retrieveValueObjectFactoryFolder(t_TestFile, "com.foo.bar");

        assertNotNull(t_ValueObjectFactoryFolder);

        assertEquals(
            t_ValueObjectFactoryFolder.getAbsolutePath(),
              t_TestFile.getAbsolutePath()
            + File.separator
            + "com"
            + File.separator
            + "foo"
            + File.separator
            + "bar"
            + File.separator
            + PackageUtils.VALUE_OBJECT_SUBPACKAGE);
    }

    /**
     * Tests the retrieveDataAccessManagerPackage() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveDataAccessManagerPackage(String)
     */
    public void testRetrieveDataAccessManagerPackage()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        assertEquals(
            t_PackageUtils.retrieveDataAccessManagerPackage("com.foo.bar"),
            "com.foo.bar." + PackageUtils.BASE_DAO_SUBPACKAGE);
    }

    /**
     * Tests the retrieveDataAccessManagerFolder() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveDataAccessManagerFolder(File,String)
     */
    public void testRetrieveDataAccessManagerFolder()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        File t_TestFile = getTestFile();

        assertNotNull(t_TestFile);

        File t_DataAccessManagerFolder = 
            t_PackageUtils.retrieveDataAccessManagerFolder(t_TestFile, "com.foo.bar");

        assertNotNull(t_DataAccessManagerFolder);

        assertEquals(
            t_DataAccessManagerFolder.getAbsolutePath(),
              t_TestFile.getAbsolutePath()
            + File.separator
            + "com"
            + File.separator
            + "foo"
            + File.separator
            + "bar"
            + File.separator
            + PackageUtils.BASE_DAO_SUBPACKAGE);
    }

    /**
     * Tests the retrieveJdbcDAOPackage() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveJdbcDAOPackage(String)
     */
    public void testRetrieveJdbcDAOPackage()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        assertEquals(
            t_PackageUtils.retrieveJdbcDAOPackage("com.foo.bar"),
              "com.foo.bar"
            + "." + PackageUtils.BASE_DAO_SUBPACKAGE
            + "." + PackageUtils.RDB_SUBPACKAGE);
    }

    /**
     * Tests the retrieveJdbcDAOFolder() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveJdbcDAOFolder(File,String)
     */
    public void testRetrieveJdbcDAOFolder()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        File t_TestFile = getTestFile();

        assertNotNull(t_TestFile);

        File t_JdbcDAOFolder = 
            t_PackageUtils.retrieveJdbcDAOFolder(t_TestFile, "com.foo.bar");

        assertNotNull(t_JdbcDAOFolder);

        assertEquals(
            t_JdbcDAOFolder.getAbsolutePath(),
              t_TestFile.getAbsolutePath()
            + File.separator
            + "com"
            + File.separator
            + "foo"
            + File.separator
            + "bar"
            + File.separator
            + PackageUtils.BASE_DAO_SUBPACKAGE
            + File.separator
            + PackageUtils.RDB_SUBPACKAGE);
    }

    /**
     * Tests the retrieveDAOPackage() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveDAOPackage(String,String)
     */
    public void testRetrieveDAOPackage()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        assertEquals(
            t_PackageUtils.retrieveDAOPackage("com.foo.bar", "mysql"),
              "com.foo.bar"
            + "." + PackageUtils.BASE_DAO_SUBPACKAGE
            + "." + PackageUtils.RDB_SUBPACKAGE
            + ".mysql");
    }

    /**
     * Tests the retrieveDAOFolder() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveDAOFolder(File,String,String)
     */
    public void testRetrieveDAOFolder()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        File t_TestFile = getTestFile();

        assertNotNull(t_TestFile);

        File t_DAOFolder = 
            t_PackageUtils.retrieveDAOFolder(t_TestFile, "com.foo.bar", "mysql");

        assertNotNull(t_DAOFolder);

        assertEquals(
            t_DAOFolder.getAbsolutePath(),
              t_TestFile.getAbsolutePath()
            + File.separator
            + "com"
            + File.separator
            + "foo"
            + File.separator
            + "bar"
            + File.separator
            + PackageUtils.BASE_DAO_SUBPACKAGE
            + File.separator
            + PackageUtils.RDB_SUBPACKAGE
            + File.separator
            + "mysql");
    }

    /**
     * Tests the retrieveDAOFactoryPackage() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveDAOFactoryPackage(String,String)
     */
    public void testRetrieveDAOFactoryPackage()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        assertEquals(
            t_PackageUtils.retrieveDAOFactoryPackage("com.foo.bar", "mysql"),
              "com.foo.bar"
            + "." + PackageUtils.BASE_DAO_SUBPACKAGE
            + "." + PackageUtils.RDB_SUBPACKAGE
            + ".mysql");
    }

    /**
     * Tests the retrieveDAOFactoryFolder() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveDAOFactoryFolder(File,String,String)
     */
    public void testRetrieveDAOFactoryFolder()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        File t_TestFile = getTestFile();

        assertNotNull(t_TestFile);

        File t_DAOFactoryFolder = 
            t_PackageUtils.retrieveDAOFactoryFolder(t_TestFile, "com.foo.bar", "mysql");

        assertNotNull(t_DAOFactoryFolder);

        assertEquals(
            t_DAOFactoryFolder.getAbsolutePath(),
              t_TestFile.getAbsolutePath()
            + File.separator
            + "com"
            + File.separator
            + "foo"
            + File.separator
            + "bar"
            + File.separator
            + PackageUtils.BASE_DAO_SUBPACKAGE
            + File.separator
            + PackageUtils.RDB_SUBPACKAGE
            + File.separator
            + "mysql");
    }


    /**
     * Tests the retrieveJdbcOperationsPackage() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveJdbcOperationsPackage(String,String,String)
     */
    public void testRetrieveJdbcOperationsPackage()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        assertEquals(
              "com.foo.bar"
            + "." + PackageUtils.BASE_DAO_SUBPACKAGE
            + "." + PackageUtils.RDB_SUBPACKAGE
            + ".mysql"
            + ".user",
            t_PackageUtils.retrieveJdbcOperationsPackage("com.foo.bar", "mysql", "user"));
    }

    /**
     * Tests the retrieveJdbcOperationsFolder() method
     * @see org.acmsl.queryj.tools.PackageUtils#retrieveDAOFactoryFolder(File,String,String,String)
     */
    public void testRetrieveJdbcOperationsFolder()
    {
        PackageUtils t_PackageUtils = PackageUtils.getInstance();

        assertNotNull(t_PackageUtils);

        File t_TestFile = getTestFile();

        assertNotNull(t_TestFile);

        File t_DAOFactoryFolder = 
            t_PackageUtils.retrieveJdbcOperationsFolder(t_TestFile, "com.foo.bar", "mysql", "user");

        assertNotNull(t_DAOFactoryFolder);

        assertEquals(
            t_DAOFactoryFolder.getAbsolutePath(),
              t_TestFile.getAbsolutePath()
            + File.separator
            + "com"
            + File.separator
            + "foo"
            + File.separator
            + "bar"
            + File.separator
            + PackageUtils.BASE_DAO_SUBPACKAGE
            + File.separator
            + PackageUtils.RDB_SUBPACKAGE
            + File.separator
            + "mysql"
            + File.separator
            + "user");
    }
}
