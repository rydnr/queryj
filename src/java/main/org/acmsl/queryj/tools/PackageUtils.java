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
 * Description: Provides some useful methods for retrieving package
 *              information about the generated code.
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
package org.acmsl.queryj.tools;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.lang.ref.WeakReference;
import java.sql.Types;
import java.util.Map;
import java.util.HashMap;

/**
 * Provides some useful methods for retrieving package information about
 * the generated code.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public abstract class PackageUtils
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected PackageUtils() {};

    /**
     * Specifies a new weak reference.
     * @param utils the utils instance to use.
     */
    protected static void setReference(PackageUtils utils)
    {
        singleton = new WeakReference(utils);
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
     * Retrieves a PackageUtils instance.
     * @return such instance.
     */
    public static PackageUtils getInstance()
    {
        PackageUtils result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (PackageUtils) reference.get();
        }

        if  (result == null) 
        {
            result = new PackageUtils() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Retrieves the package name for base DAO templates.
     * @param packageName the original package.
     * @return the package for the associated base DAO interface.
     */
    public String retrieveBaseDAOPackage(String packageName)
    {
        return packageName + ".dao";
    }

    /**
     * Retrieves the folder for base DAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @return the folder in which the associated DAO class should be
     * generated.
     */
    public File retrieveBaseDAOFolder(File parentFolder, String packageName)
    {
        File result = parentFolder;

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (   (result != null)
             && (t_StringUtils != null))
        {
            result =
                new File(
                      parentFolder.getPath()
                    + File.separator
                    + t_StringUtils.packageToFilePath(packageName)
                    + File.separator
                    + "dao");
        }

        return result;
    }

    /**
     * Retrieves the package name for base DAO factory templates.
     * @param packageName the original package.
     * @return the package for the associated base DAO factory class.
     */
    public String retrieveBaseDAOFactoryPackage(
        String packageName)
    {
        return retrieveBaseDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for base DAO factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which  the associated DAO factory should be
     * generated.
     */
    public File retrieveBaseDAOFactoryFolder(
        File parentFolder, String packageName)
    {
        return retrieveBaseDAOFolder(parentFolder, packageName);
    }

    /**
     * Retrieves the package name for value object templates.
     * @param packageName the original package.
     * @return the package for the associated value object class.
     */
    public String retrieveValueObjectPackage(String packageName)
    {
        return retrieveBaseDAOPackage(packageName);
    }


    /**
     * Retrieves the folder for value object templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which the associated Jdbc DAO should be
     * generated.
     */
    public File retrieveValueObjectFolder(
        File parentFolder, String packageName)
    {
        return retrieveBaseDAOFolder(parentFolder, packageName);
    }

    /**
     * Retrieves the package name for value object factory
     * templates.
     * @param packageName the original package.
     * @return the package for the associated value object
     * factory class.
     */
    public String retrieveValueObjectFactoryPackage(String packageName)
    {
        return retrieveValueObjectPackage(packageName);
    }

    /**
     * Retrieves the folder for value object factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which the associated value object
     * factories should be generated.
     */
    public File retrieveValueObjectFactoryFolder(
        File parentFolder, String packageName)
    {
        return retrieveValueObjectFolder(parentFolder, packageName);
    }

    /**
     * Retrieves the package name for DataAccessManager templates.
     * @param packageName the original package.
     * @return the package for the associated manager class.
     */
    public String retrieveDataAccessManagerPackage(
        String packageName)
    {
        return retrieveBaseDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for DataAccessManager templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which the associated manager should be
     * generated.
     */
    public File retrieveDataAccessManagerFolder(
        File parentFolder, String packageName)
    {
        return retrieveBaseDAOFolder(parentFolder, packageName);
    }

    /**
     * Retrieves the package name for Jdbc DAO templates.
     * @param packageName the original package.
     * @return the package for the associated Jdbc DAO class.
     */
    public String retrieveJdbcDAOPackage(String packageName)
    {
        return retrieveBaseDAOPackage(packageName) + ".rdb";
    }

    /**
     * Retrieves the folder for Jdbc DAO factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which the associated Jdbc DAO should be
     * generated.
     */
    public File retrieveJdbcDAOFolder(File parentFolder, String packageName)
    {
        File result = parentFolder;

        if  (result != null)
        {
            result =
                new File(
                      retrieveBaseDAOFolder(parentFolder, packageName)
		          .getPath()
                    + File.separator
                    + "rdb");
        }

        return result;
    }

    /**
     * Retrieves the package name for DAO templates.
     * @param packageName the original package.
     * @param engineName the DAO engine.
     * @return the package for the associated DAO class.
     */
    public String retrieveDAOPackage(String packageName, String engineName)
    {
        String result = retrieveJdbcDAOPackage(packageName);

        if  (engineName != null)
        {
            result += "." + engineName.toLowerCase();
        }

        return result;
    }

    /**
     * Retrieves the folder for DAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param engineName the DAO engine.
     * @return the folder in which  the associated DAO class should be
     * generated.
     */
    public File retrieveDAOFolder(
        File parentFolder, String packageName, String engineName)
    {
        File result = parentFolder;

        if  (result != null)
        {
            String t_strPath =
                retrieveJdbcDAOFolder(parentFolder, packageName).getPath();

            if  (engineName != null)
            {
                t_strPath += File.separator + engineName.toLowerCase();
            }

            result = new File(t_strPath);
        }

        return result;
    }

    /**
     * Retrieves the package name for DAO factory templates.
     * @param packageName the original package.
     * @param engineName the DAO engine.
     * @return the package for the associated DAO factory class.
     */
    public String retrieveDAOFactoryPackage(
        String packageName, String engineName)
    {
        return retrieveDAOPackage(packageName, engineName);
    }

    /**
     * Retrieves the folder for DAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param engineName the DAO engine.
     * @return the folder in which the associated DAO factory
     * should be generated.
     */
    public File retrieveDAOFactoryFolder(
        File parentFolder, String packageName, String engineName)
    {
        return retrieveDAOFolder(parentFolder, packageName, engineName);
    }

    /**
     * Retrieves the package name for repository templates.
     * @param packageName the original package.
     * @return the package for the associated repository class.
     */
    public String retrieveTableRepositoryPackage(
        String packageName)
    {
        return packageName + ".tables";
    }

    /**
     * Retrieves the folder for repository templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which  the associated should be
     * generated.
     */
    public File retrieveTableRepositoryFolder(
        File parentFolder, String packageName)
    {
        File result = parentFolder;

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (   (result        != null)
             && (t_StringUtils != null))
        {
            result =
                new File(
                      parentFolder.getPath()
                    + File.separator
                    + t_StringUtils.packageToFilePath(packageName)
                    + File.separator
                    + "tables");
        }

        return result;
    }

    /**
     * Retrieves the package name for table templates.
     * @param packageName the original package.
     * @return the package for the associated table classes.
     */
    public String retrieveTablePackage(String packageName)
    {
        return retrieveTableRepositoryPackage(packageName);
    }

    /**
     * Retrieves the folder for table templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which the associated templates should be
     * generated.
     */
    public File retrieveTableFolder(
        File parentFolder, String packageName)
    {
        return retrieveTableRepositoryFolder(parentFolder, packageName);
    }

    /**
     * Retrieves the package name for DAO test templates.
     * @param packageName the original package.
     * @param engineName the engine name.
     * @return the package for the associated DAO tests.
     */
    public String retrieveDAOTestPackage(String packageName, String engineName)
    {
        return "unittests." + retrieveDAOPackage(packageName, engineName);
    }

    /**
     * Retrieves the folder for DAO test templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param engineName the engine name.
     * @return the folder in which the associated test class should be
     * generated.
     */
    public File retrieveDAOTestFolder(
        File parentFolder, String packageName, String engineName)
    {
        File result = parentFolder;

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (   (result != null)
             && (t_StringUtils != null))
        {
            result =
                retrieveDAOFolder(
                    new File(
                          parentFolder.getPath()
                        + File.separator
                        + "unittests"),
                    packageName,
                    engineName);
        }

        return result;
    }

    /**
     * Retrieves the package name for the base test suite template.
     * @param packageName the original package.
     * @return the package for the associated suite.
     */
    public String retrieveBaseTestSuitePackage(String packageName)
    {
        return "unittests." + packageName;
    }

    /**
     * Retrieves the folder for the base test suite template.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @return the folder in which the associated suite class should be
     * generated.
     */
    public File retrieveBaseTestSuiteFolder(
        File parentFolder, String packageName)
    {
        File result = parentFolder;

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (   (result != null)
             && (t_StringUtils != null))
        {
            result =
                new File(
                      parentFolder.getPath()
                    + File.separator
                    + "unittests"
                    + File.separator
                    + t_StringUtils.packageToFilePath(packageName));
        }

        return result;
    }

    /**
     * Retrieves the package name for the functions templates.
     * @param packageName the original package.
     * @return the package for the associated functions classes.
     */
    public String retrieveFunctionsPackage(String packageName)
    {
        return packageName + ".functions";
    }

    /**
     * Retrieves the folder for the functions templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @return the folder in which the associated functions classes should be
     * generated.
     */
    public File retrieveFunctionsFolder(
        File parentFolder, String packageName)
    {
        File result = parentFolder;

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (   (result        != null)
             && (t_StringUtils != null))
        {
            result =
                new File(
                      parentFolder.getPath()
                    + File.separator
                    + t_StringUtils.packageToFilePath(packageName)
                    + File.separator
                    + "functions");
        }

        return result;
    }

    /**
     * Retrieves the package name for the functions test templates.
     * @param packageName the original package.
     * @return the package for the associated test classes.
     */
    public String retrieveTestFunctionsPackage(String packageName)
    {
        return "unittests." + retrieveFunctionsPackage(packageName);
    }

    /**
     * Retrieves the folder for the functions test templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @return the folder in which the associated functions classes should be
     * generated.
     */
    public File retrieveTestFunctionsFolder(
        File parentFolder, String packageName)
    {
        File result = parentFolder;

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (   (result        != null)
             && (t_StringUtils != null))
        {
            result =
                retrieveFunctionsFolder(
                    new File(
                          parentFolder.getPath()
                        + File.separator
                        + "unittests"),
                    packageName);
        }

        return result;
    }

    /**
     * Retrieves the package name for DAOChooser templates.
     * @param packageName the original package.
     * @return the package for the associated manager class.
     */
    public String retrieveDAOChooserPackage(
        String packageName)
    {
        return retrieveBaseDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for DAOChooser templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which the associated template should be
     * generated.
     */
    public File retrieveDAOChooserFolder(
        File parentFolder, String packageName)
    {
        return retrieveBaseDAOFolder(parentFolder, packageName);
    }

    /**
     * Retrieves the package name for ProcedureRepository templates.
     * @param packageName the original package.
     * @return the package for the associated repository class.
     */
    public String retrieveProcedureRepositoryPackage(
        String packageName)
    {
        return retrieveFunctionsPackage(packageName);
    }

    /**
     * Retrieves the folder for ProcedureRepository templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which the associated template should be
     * generated.
     */
    public File retrieveProcedureRepositoryFolder(
        File parentFolder, String packageName)
    {
        return retrieveFunctionsFolder(parentFolder, packageName);
    }

    /**
     * Retrieves the package name for KeywordRepository templates.
     * @param packageName the original package.
     * @return the package for the associated repository class.
     */
    public String retrieveKeywordRepositoryPackage(
        String packageName)
    {
        return retrieveProcedureRepositoryPackage(packageName);
    }

    /**
     * Retrieves the folder for KeywordRepository templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which the associated template should be
     * generated.
     */
    public File retrieveKeywordRepositoryFolder(
        File parentFolder, String packageName)
    {
        return retrieveProcedureRepositoryFolder(parentFolder, packageName);
    }

    /**
     * Retrieves the package name for Mock DAO templates.
     * @param packageName the original package.
     * @return the package for the associated DAO class.
     */
    public String retrieveMockDAOPackage(String packageName)
    {
        return retrieveBaseDAOPackage(packageName) + ".mock";
    }

    /**
     * Retrieves the folder for Mock DAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which  the associated DAO class should be
     * generated.
     */
    public File retrieveMockDAOFolder(
        File parentFolder, String packageName)
    {
        File result = parentFolder;

        if  (result != null)
        {
            String t_strPath =
                retrieveBaseDAOFolder(parentFolder, packageName).getPath();

            t_strPath += File.separator + "mock";

            result = new File(t_strPath);
        }

        return result;
    }

    /**
     * Retrieves the package name for Mock DAO factory templates.
     * @param packageName the original package.
     * @return the package for the associated DAO factory class.
     */
    public String retrieveMockDAOFactoryPackage(String packageName)
    {
        return retrieveMockDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for Mock DAO factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which  the associated DAO factory should be
     * generated.
     */
    public File retrieveMockDAOFactoryFolder(
        File parentFolder, String packageName)
    {
        return retrieveMockDAOFolder(parentFolder, packageName);
    }

    /**
     * Retrieves the package name for Mock DAO test templates.
     * @param packageName the original package.
     * @return the package for the associated Mock DAO tests.
     */
    public String retrieveMockDAOTestPackage(String packageName)
    {
        return "unittests." + retrieveMockDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for Mock DAO test templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @return the folder in which the associated test class should be
     * generated.
     */
    public File retrieveMockDAOTestFolder(
        final File parentFolder, final String packageName)
    {
        File result = parentFolder;

        StringUtils t_StringUtils = StringUtils.getInstance();

        if  (   (result != null)
             && (t_StringUtils != null))
        {
            result =
                retrieveMockDAOFolder(
                    new File(
                          parentFolder.getPath()
                        + File.separator
                        + "unittests"),
                    packageName);
        }

        return result;
    }
}
