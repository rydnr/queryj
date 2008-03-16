//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2006  Jose San Leandro Armendariz
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
 * Filename: PackageUtils.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some useful methods for retrieving package
 *              information about the generated code.
 *
 */
package org.acmsl.queryj.tools;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.patterns.Utils;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Locale;

/**
 * Provides some useful methods for retrieving package information about
 * the generated code.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class PackageUtils
    implements  Singleton,
                Utils
{
    /**
     * The package prefix for unit tests.
     */
    public static final String UNITTESTS_PACKAGE_PREFIX = "unittests";

    /**
     * The "main" branch if using subfolders.
     */
    public static final String MAIN_BRANCH = "main";

    /**
     * The "test" branch if using subfolders.
     */
    public static final String TEST_BRANCH = "test";

    /**
     * The subpackage for BaseDAO entitites.
     */
    public static final String BASE_DAO_SUBPACKAGE = "dao";

    /**
     * The subpackage for ValueObject entities.
     */
    public static final String VALUE_OBJECT_SUBPACKAGE = "vo";

    /**
     * The subpackage for ValueObject entities.
     */
    public static final String BASE_VALUE_OBJECT_SUBPACKAGE =
        VALUE_OBJECT_SUBPACKAGE;

    /**
     * The subpackage for ValueObject implementations.
     */
    public static final String VALUE_OBJECT_IMPL_SUBPACKAGE =
        VALUE_OBJECT_SUBPACKAGE;

    /**
     * The subpackage for Relational Database common classes.
     */
    public static final String RDB_SUBPACKAGE = "rdb";

    /**
     * The subpackage for TableRepository.
     */
    public static final String TABLE_REPOSITORY_SUBPACKAGE = "tables";

    /**
     * The subpackage for Function entities.
     */
    public static final String FUNCTIONS_SUBPACKAGE = "functions";

    /**
     * The subpackage for MockDAO entities.
     */
    public static final String MOCK_DAO_SUBPACKAGE = "mock";

    /**
     * The subpackage for XmlDAO entities.
     */
    public static final String XML_DAO_SUBPACKAGE = "xml";

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class PackageUtilsSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final PackageUtils SINGLETON = new PackageUtils();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected PackageUtils() {};

    /**
     * Retrieves a PackageUtils instance.
     * @return such instance.
     */
    public static PackageUtils getInstance()
    {
        return PackageUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the package name for given subpackage.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @return the complate package information for given subpackage.
     * @precondition packageName != null
     */
    protected String retrievePackage(
        final String packageName, final String subpackage)
    {
        return
            retrievePackage(
                packageName,
                subpackage,
                StringValidator.getInstance());
    }

    /**
     * Retrieves the package name for given subpackage.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @return the complate package information for given subpackage.
     * @precondition packageName != null
     * @precondition stringValidator != null
     */
    protected String retrievePackage(
        final String packageName,
        final String subpackage,
        final StringValidator stringValidator)
    {
        String result = packageName;

        if  (!stringValidator.isEmpty(subpackage))
        {
            if  (!stringValidator.isEmpty(packageName))
            {
                result += ".";
            }
            else
            {
                result = "";
            }

            result += subpackage;
        }

        return result;
    }

    /**
     * Retrieves the package name for given subpackage.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @param subFolders whether to use subfolders or not.
     * @return the complate package information for given subpackage.
     * @precondition packageName != null
     * @precondition subpackage != null
     */
    protected String retrieveTestPackage(
        final String packageName,
        final String subpackage,
        final boolean subFolders)
    {
        return
            retrievePackage(
                retrievePackage(
                    (subFolders) ? "" : UNITTESTS_PACKAGE_PREFIX,
                    packageName),
                subpackage);
    }

    /**
     * Retrieves the package name for given subpackage.
     * @param packageName the original package.
     * @param subFolders whether to use subfolders or not.
     * @return the complate package information for given subpackage.
     * @precondition packageName != null
     */
    protected String retrieveTestPackage(
        final String packageName, final boolean subFolders)
    {
        return retrieveTestPackage(packageName, "", subFolders);
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @return the folder in which the associated class should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     */
    protected File retrieveFolder(
        final File parentFolder, final String packageName)
    {
        return retrieveFolder(parentFolder, packageName, false);
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated class should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     */
    protected File retrieveFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveFolder(parentFolder, packageName, useSubfolders, false);
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @param useSubfolders whether to use subfolders or not.
     * @param testPackage whether the package contains unit tests.
     * @return the folder in which the associated class should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     */
    protected File retrieveFolder(
        final File parentFolder,
        final String packageName,
        final String subpackage,
        final boolean useSubfolders,
        final boolean testPackage)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                subpackage,
                useSubfolders,
                testPackage,
                StringUtils.getInstance(),
                StringValidator.getInstance());
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @param useSubfolders whether to use subfolders or not.
     * @param testPackage whether the package contains unit tests.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param stringValidator the <code>StringValidator</code> instance.
     * @return the folder in which the associated DAO class should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     * @precondition subpackage != null
     * @precondition stringUtils != null
     * @precondition stringValidator != null
     */
    protected File retrieveFolder(
        final File parentFolder,
        final String packageName,
        final String subpackage,
        final boolean useSubfolders,
        final boolean testPackage,
        final StringUtils stringUtils,
        final StringValidator stringValidator)
    {
        File result = null;

        String t_strWholePackage = packageName;

        if  (!stringValidator.isEmpty(subpackage))
        {
            t_strWholePackage += "." + subpackage;
        }

        result =
            retrieveFolder(
                parentFolder,
                t_strWholePackage,
                useSubfolders,
                testPackage,
                stringUtils);

        return result;
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubfolders whether to use subfolders or not.
     * @param testPackage whether the package contains unit tests.
     * @return the folder in which the associated DAO class should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     */
    protected File retrieveFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders,
        final boolean testPackage)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                useSubfolders,
                testPackage,
                StringUtils.getInstance());
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubfolders whether to use subfolders or not.
     * @param testPackage whether the package contains unit tests.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the folder in which the associated DAO class should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     * @precondition stringUtils != null
     */
    protected File retrieveFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders,
        final boolean testPackage,
        final StringUtils stringUtils)
    {
        String t_strResult = parentFolder.getPath();

        if  (useSubfolders)
        {
            t_strResult += File.separator;

            if  (testPackage)
            {
                t_strResult += TEST_BRANCH;
            }
            else
            {
                t_strResult += MAIN_BRANCH;
            }
        }

        t_strResult +=
            File.separator + stringUtils.packageToFilePath(packageName);

        return new File(t_strResult);
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param useSubfolders whether to use subfolders or not.
     * @return the folder in which the associated class should be
     * generated.
     * @precondition parentFolder != null
     */
    protected File retrieveTestFolder(
        final File parentFolder, final boolean useSubfolders)
    {
        return
            retrieveFolder(
                parentFolder,
                (useSubfolders ? "" : UNITTESTS_PACKAGE_PREFIX),
                useSubfolders,
                true);
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @param useSunfolders whether to use subfolders.
     * @return the folder in which the associated class should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     */
    protected File retrieveTestFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                useSubfolders,
                true);
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @param useSunfolders whether to use subfolders.
     * @return the folder in which the associated class should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     */
    protected File retrieveTestFolder(
        final File parentFolder,
        final String packageName,
        final String subpackage,
        final boolean useSubfolders)
    {
        return
            retrieveFolder(
                retrieveTestFolder(parentFolder, useSubfolders),
                packageName,
                subpackage,
                useSubfolders,
                true);
    }

    /**
     * Retrieves the package name for base DAO templates.
     * @param packageName the original package.
     * @return the package for the associated base DAO interface.
     * @precondition packageName != null
     */
    public String retrieveBaseDAOPackage(final String packageName)
    {
        return retrievePackage(packageName, BASE_DAO_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for base DAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated DAO class should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     */
    public File retrieveBaseDAOFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                BASE_DAO_SUBPACKAGE,
                useSubfolders,
                false);
    }

    /**
     * Retrieves the package name for base DAO factory templates.
     * @param packageName the original package.
     * @return the package for the associated base DAO factory class.
     * @precondition packageName != null
     */
    public String retrieveBaseDAOFactoryPackage(
        final String packageName)
    {
        return retrieveBaseDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for base DAO factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which  the associated DAO factory should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     */
    public File retrieveBaseDAOFactoryFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveBaseDAOFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for value object templates.
     * @param packageName the original package.
     * @return the package for the associated value object class.
     * @precondition packageName != null
     */
    public String retrieveValueObjectPackage(String packageName)
    {
        return retrievePackage(packageName, VALUE_OBJECT_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for value object templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated Jdbc DAO should be
     * generated.
     */
    public File retrieveValueObjectFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                VALUE_OBJECT_SUBPACKAGE,
                useSubfolders,
                false);
    }

    /**
     * Retrieves the package name for value object factory
     * templates.
     * @param packageName the original package.
     * @return the package for the associated value object
     * factory class.
     */
    public String retrieveValueObjectFactoryPackage(final String packageName)
    {
        return retrieveValueObjectPackage(packageName);
    }

    /**
     * Retrieves the folder for value object factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated value object
     * factories should be generated.
     */
    public File retrieveValueObjectFactoryFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveValueObjectFolder(
                parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for baseValue object templates.
     * @param packageName the original package.
     * @return the package for the associated baseValue object class.
     * @precondition packageName != null
     */
    public String retrieveBaseValueObjectPackage(final String packageName)
    {
        return retrievePackage(packageName, BASE_VALUE_OBJECT_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for baseValue object templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated Jdbc DAO should be
     * generated.
     */
    public File retrieveBaseValueObjectFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                BASE_VALUE_OBJECT_SUBPACKAGE,
                useSubfolders,
                false);
    }

    /**
     * Retrieves the package name for value object implementation templates.
     * @param packageName the original package.
     * @return the package for the associated value object class.
     * @precondition packageName != null
     */
    public String retrieveValueObjectImplPackage(final String packageName)
    {
        return retrievePackage(packageName, VALUE_OBJECT_IMPL_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for value object implementation templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated Jdbc DAO should be
     * generated.
     */
    public File retrieveValueObjectImplFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                VALUE_OBJECT_IMPL_SUBPACKAGE,
                useSubfolders,
                false);
    }

    /**
     * Retrieves the package name for DataAccessManager templates.
     * @param packageName the original package.
     * @return the package for the associated manager class.
     */
    public String retrieveDataAccessManagerPackage(
        final String packageName)
    {
        return retrieveBaseDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for DataAccessManager templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated manager should be
     * generated.
     */
    public File retrieveDataAccessManagerFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveBaseDAOFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for base Relational Database common classes.
     * @param packageName the original package.
     * @return the package for the associated rdb classes.
     */
    public String retrieveRdbPackage(final String packageName)
    {
        return
            retrievePackage(
                retrieveBaseDAOPackage(packageName), RDB_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for base Relational Database common classes.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    public File retrieveRdbFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveFolder(
                retrieveBaseDAOFolder(
                    parentFolder, packageName, useSubfolders),
                RDB_SUBPACKAGE);
    }

    /**
     * Retrieves the package name for Jdbc DAO templates.
     * @param packageName the original package.
     * @return the package for the associated Jdbc DAO class.
     */
    public String retrieveJdbcDAOPackage(final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for Jdbc DAO factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated Jdbc DAO should be
     * generated.
     */
    public File retrieveJdbcDAOFolder(
        final File parentFolder, final String packageName, final boolean useSubfolders)
    {
        return retrieveRdbFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the subpackage name for DAO templates.
     * @param engineName the DAO engine.
     * @return the subpackage for the associated DAO class.
     * @precondition engineName != null
     */
    public String retrieveDAOSubpackage(final String engineName)
    {
        Locale t_Locale = Locale.getDefault();

        return engineName.toLowerCase(t_Locale);
    }

    /**
     * Retrieves the package name for DAO templates.
     * @param packageName the original package.
     * @param engineName the DAO engine.
     * @return the package for the associated DAO class.
     * @precondition packageName != null
     * @precondition engineName != null
     */
    public String retrieveDAOPackage(
        final String packageName, final String engineName)
    {
        return
            retrievePackage(
                retrieveRdbPackage(packageName),
                retrieveDAOSubpackage(engineName));
    }

    /**
     * Retrieves the folder for DAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param engineName the DAO engine.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which  the associated DAO class should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     * @precondition engineName != null
     */
    public File retrieveDAOFolder(
        final File parentFolder,
        final String packageName,
        final String engineName,
        final boolean useSubfolders)
    {
        Locale t_Locale = Locale.getDefault();

        return
            retrieveFolder(
                retrieveRdbFolder(
                    parentFolder, packageName, useSubfolders),
                engineName.toLowerCase(t_Locale));
    }

    /**
     * Retrieves the package name for DAO factory templates.
     * @param packageName the original package.
     * @param engineName the DAO engine.
     * @return the package for the associated DAO factory class.
     */
    public String retrieveDAOFactoryPackage(
        final String packageName, final String engineName)
    {
        return retrieveDAOPackage(packageName, engineName);
    }

    /**
     * Retrieves the folder for DAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param engineName the DAO engine.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated DAO factory
     * should be generated.
     */
    public File retrieveDAOFactoryFolder(
        final File parentFolder,
        final String packageName,
        final String engineName,
        final boolean useSubfolders)
    {
        return
            retrieveDAOFolder(
                parentFolder, packageName, engineName, useSubfolders);
    }

    /**
     * Retrieves the package name for repository templates.
     * @param packageName the original package.
     * @return the package for the associated repository class.
     */
    public String retrieveTableRepositoryPackage(
        final String packageName)
    {
        return retrievePackage(packageName, TABLE_REPOSITORY_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for repository templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which  the associated should be
     * generated.
     */
    public File retrieveTableRepositoryFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                TABLE_REPOSITORY_SUBPACKAGE,
                useSubfolders,
                false);
    }

    /**
     * Retrieves the package name for table templates.
     * @param packageName the original package.
     * @return the package for the associated table classes.
     */
    public String retrieveTablePackage(final String packageName)
    {
        return retrieveTableRepositoryPackage(packageName);
    }

    /**
     * Retrieves the folder for table templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated templates should be
     * generated.
     */
    public File retrieveTableFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveTableRepositoryFolder(
                parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for DAO test templates.
     * @param packageName the original package.
     * @param engineName the engine name.
     * @param subFolders whether to use subfolders or not.
     * @return the package for the associated DAO tests.
     */
    public String retrieveDAOTestPackage(
        final String packageName,
        final String engineName,
        final boolean subFolders)
    {
        return
            retrieveTestPackage(
                retrieveDAOPackage(packageName, engineName), subFolders);
    }

    /**
     * Retrieves the folder for DAO test templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param engineName the engine name.
     * @param useSubfolders whether to use subfolders or not.
     * @return the folder in which the associated test class should be
     * generated.
     */
    public File retrieveDAOTestFolder(
        final File parentFolder,
        final String packageName,
        final String engineName,
        final boolean useSubfolders)
    {
        return
            retrieveTestFolder(
                parentFolder,
                retrieveDAOTestPackage(packageName, engineName, useSubfolders),
                useSubfolders);
    }

    /**
     * Retrieves the package name for the base test suite template.
     * @param packageName the original package.
     * @param useSubfolders whether to use subfolders.
     * @return the package for the associated suite.
     */
    public String retrieveBaseTestSuitePackage(
        final String packageName, final boolean useSubfolders)
    {
        return retrieveTestPackage(packageName, useSubfolders);
    }

    /**
     * Retrieves the folder for the base test suite template.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated suite class should be
     * generated.
     */
    public File retrieveBaseTestSuiteFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveFolder(
                retrieveTestFolder(parentFolder, useSubfolders),
                packageName);
    }

    /**
     * Retrieves the package name for the functions templates.
     * @param packageName the original package.
     * @return the package for the associated functions classes.
     */
    public String retrieveFunctionsPackage(final String packageName)
    {
        return
            retrievePackage(packageName, FUNCTIONS_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for the functions templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated functions classes should be
     * generated.
     */
    public File retrieveFunctionsFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                FUNCTIONS_SUBPACKAGE,
                useSubfolders,
                false);
    }

    /**
     * Retrieves the package name for the functions test templates.
     * @param packageName the original package.
     * @param subFolders whether to use subfolders or not.
     * @return the package for the associated test classes.
     */
    public String retrieveTestFunctionsPackage(
        final String packageName, final boolean subFolders)
    {
        return
            retrieveTestPackage(
                retrieveFunctionsPackage(packageName), subFolders);
    }

    /**
     * Retrieves the folder for the functions test templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated functions classes should be
     * generated.
     */
    public File retrieveTestFunctionsFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveTestFolder(
                parentFolder,
                retrieveTestFunctionsPackage(packageName, useSubfolders),
                useSubfolders);
    }

    /**
     * Retrieves the package name for DAOChooser templates.
     * @param packageName the original package.
     * @return the package for the associated manager class.
     */
    public String retrieveDAOChooserPackage(
        final String packageName)
    {
        return retrieveBaseDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for DAOChooser templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated template should be
     * generated.
     */
    public File retrieveDAOChooserFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveBaseDAOFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for ProcedureRepository templates.
     * @param packageName the original package.
     * @return the package for the associated repository class.
     */
    public String retrieveProcedureRepositoryPackage(
        final String packageName)
    {
        return retrieveFunctionsPackage(packageName);
    }

    /**
     * Retrieves the folder for ProcedureRepository templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated template should be
     * generated.
     */
    public File retrieveProcedureRepositoryFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveFunctionsFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for KeywordRepository templates.
     * @param packageName the original package.
     * @return the package for the associated repository class.
     */
    public String retrieveKeywordRepositoryPackage(
        final String packageName)
    {
        return retrieveProcedureRepositoryPackage(packageName);
    }

    /**
     * Retrieves the folder for KeywordRepository templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated template should be
     * generated.
     */
    public File retrieveKeywordRepositoryFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveProcedureRepositoryFolder(
                parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for Mock DAO templates.
     * @param packageName the original package.
     * @return the package for the associated DAO class.
     */
    public String retrieveMockDAOPackage(final String packageName)
    {
        return
            retrievePackage(
                retrieveBaseDAOPackage(packageName),
                MOCK_DAO_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for Mock DAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which  the associated DAO class should be
     * generated.
     */
    public File retrieveMockDAOFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveFolder(
                retrieveBaseDAOFolder(parentFolder, packageName, useSubfolders),
                MOCK_DAO_SUBPACKAGE);
    }

    /**
     * Retrieves the package name for Mock DAO factory templates.
     * @param packageName the original package.
     * @return the package for the associated DAO factory class.
     */
    public String retrieveMockDAOFactoryPackage(final String packageName)
    {
        return retrieveMockDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for Mock DAO factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which  the associated DAO factory should be
     * generated.
     */
    public File retrieveMockDAOFactoryFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveMockDAOFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for Mock DAO test templates.
     * @param packageName the original package.
     * @param subFolders whether to use subfolders or not.
     * @return the package for the associated Mock DAO tests.
     */
    public String retrieveMockDAOTestPackage(
        final String packageName, final boolean subFolders)
    {
        return
            retrieveTestPackage(
                retrieveMockDAOPackage(packageName), subFolders);
    }

    /**
     * Retrieves the folder for Mock DAO test templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated test class should be
     * generated.
     */
    public File retrieveMockDAOTestFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveMockDAOFolder(
                retrieveTestFolder(parentFolder, useSubfolders),
                packageName,
                useSubfolders);
    }

    /**
     * Retrieves the package name for XML DAO templates.
     * @param packageName the original package.
     * @return the package for the associated DAO class.
     */
    public String retrieveXMLDAOPackage(final String packageName)
    {
        return
            retrievePackage(
                retrieveBaseDAOPackage(packageName),
                XML_DAO_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for XML DAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which  the associated DAO class should be
     * generated.
     */
    public File retrieveXMLDAOFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveFolder(
                retrieveBaseDAOFolder(
                    parentFolder, packageName, useSubfolders),
                XML_DAO_SUBPACKAGE);
    }

    /**
     * Retrieves the package name for XML DAO factory templates.
     * @param packageName the original package.
     * @return the package for the associated DAO factory class.
     */
    public String retrieveXmlDAOFactoryPackage(final String packageName)
    {
        return retrieveXMLDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for XML DAO factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which  the associated DAO factory should be
     * generated.
     */
    public File retrieveXmlDAOFactoryFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveXMLDAOFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for XML DAO test templates.
     * @param packageName the original package.
     * @param subFolders whether to use subfolders or not.
     * @return the package for the associated XML DAO tests.
     */
    public String retrieveXMLDAOTestPackage(
        final String packageName, final boolean subFolders)
    {
        return
            retrieveTestPackage(
                retrieveXMLDAOPackage(packageName), subFolders);
    }

    /**
     * Retrieves the folder for XML DAO test templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated test class should be
     * generated.
     */
    public File retrieveXMLDAOTestFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveXMLDAOFolder(
                retrieveTestFolder(parentFolder, useSubfolders),
                packageName,
                useSubfolders);
    }

    /**
     * Retrieves the package name for XMLValueObjectFactory templates.
     * @param packageName the original package.
     * @return the package for the associated factory class.
     */
    public String retrieveXMLValueObjectFactoryPackage(final String packageName)
    {
        return retrieveXMLDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for XMLValueObjectFactory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated factory class should be
     * generated.
     */
    public File retrieveXMLValueObjectFactoryFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveXMLDAOFolder(
                parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for the JDBC operation classes.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @return the package for the associated pointers.
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     */
    public String retrieveJdbcOperationsPackage(
        final String packageName,
        final String engineName,
        final String tableName)
    {
        return
            retrieveJdbcOperationsPackage(
                packageName,
                engineName,
                tableName,
                StringUtils.getInstance());
    }

    /**
     * Retrieves the package name for the JDBC operation classes.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the package for the associated pointers.
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     * @precondition stringUtils != null
     */
    public String retrieveJdbcOperationsPackage(
        final String packageName,
        final String engineName,
        final String tableName,
        final StringUtils stringUtils)
    {
        Locale t_Locale = Locale.getDefault();

        return
            retrievePackage(
                retrieveDAOPackage(packageName, engineName),
                stringUtils.capitalize(
                    tableName.toLowerCase(t_Locale), '_')
                    .toLowerCase(t_Locale));
    }

    /**
     * Retrieves the folder for the JDBC operation classes.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder for the associated pointers.
     * @precondition parentFolder != null
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     */
    public File retrieveJdbcOperationsFolder(
        final File parentFolder,
        final String packageName,
        final String engineName,
        final String tableName,
        final boolean useSubfolders)
    {
        return
            retrieveJdbcOperationsFolder(
                parentFolder,
                packageName,
                engineName,
                tableName,
                useSubfolders,
                StringUtils.getInstance());
    }

    /**
     * Retrieves the folder for the JDBC operation classes.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @param useSubfolders whether to use subfolders.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the folder for the associated pointers.
     * @precondition parentFolder != null
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     * @precondition stringUtils != null
     */
    public File retrieveJdbcOperationsFolder(
        final File parentFolder,
        final String packageName,
        final String engineName,
        final String tableName,
        final boolean useSubfolders,
        final StringUtils stringUtils)
    {
        Locale t_Locale = Locale.getDefault();

        return
            retrieveFolder(
                retrieveDAOFolder(
                    parentFolder, packageName, engineName, useSubfolders),
                stringUtils.capitalize(
                    tableName.toLowerCase(t_Locale), '_')
                .toLowerCase(t_Locale));
    }

    /**
     * Retrieves the package name for QueryPreparedStatementCreator class.
     * @param packageName the original package.
     * @return the package for such class.
     */
    public String retrieveQueryPreparedStatementCreatorPackage(
        final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for QueryPreparedStatementCreator class.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    public File retrieveQueryPreparedStatementCreatorFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveRdbFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for the ResultSetExtractor classes.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @return the package for the associated classes.
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     */
    public String retrieveResultSetExtractorPackage(
        final String packageName,
        final String engineName,
        final String tableName)
    {
        return
            retrieveJdbcOperationsPackage(
                packageName, engineName, tableName);
    }

    /**
     * Retrieves the folder for the ResultSetExtractor classes.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder for the associated pointers.
     * @precondition parentFolder != null
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     */
    public File retrieveResultSetExtractorFolder(
        final File parentFolder,
        final String packageName,
        final String engineName,
        final String tableName,
        final boolean useSubfolders)
    {
        return
            retrieveJdbcOperationsFolder(
                parentFolder,
                packageName,
                engineName,
                tableName,
                useSubfolders);
    }

    /**
     * Retrieves the package name for the ResultSetExtractor classes.
     * @param packageName the original package.
     * @param engineName the engine.
     * @return the package for the associated classes.
     * @precondition packageName != null
     * @precondition engineName != null
     */
    public String retrieveCustomResultSetExtractorPackage(
        final String packageName, final String engineName)
    {
        return retrieveDAOPackage(packageName, engineName);
    }

    /**
     * Retrieves the folder for the ResultSetExtractor classes.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param useSubfolders whether to use subfolders.
     * @return the folder for the associated pointers.
     * @precondition parentFolder != null
     * @precondition packageName != null
     * @precondition engineName != null
     */
    public File retrieveCustomResultSetExtractorFolder(
        final File parentFolder,
        final String packageName,
        final String engineName,
        final boolean useSubfolders)
    {
        return
            retrieveDAOFolder(
                parentFolder, packageName, engineName, useSubfolders);
    }

    /**
     * Retrieves the package name for the AttributesStatementSetter classes.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @return the package for the associated classes.
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     */
    public String retrieveAttributesStatementSetterPackage(
        final String packageName,
        final String engineName,
        final String tableName)
    {
        return
            retrieveJdbcOperationsPackage(
                packageName, engineName, tableName);
    }

    /**
     * Retrieves the folder for the AttributesStatementSetter classes.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder for the associated pointers.
     * @precondition parentFolder != null
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     */
    public File retrieveAttributesStatementSetterFolder(
        final File parentFolder,
        final String packageName,
        final String engineName,
        final String tableName,
        final boolean useSubfolders)
    {
        return
            retrieveJdbcOperationsFolder(
                parentFolder,
                packageName,
                engineName,
                tableName,
                useSubfolders);
    }

    /**
     * Retrieves the package name for the PkStatementSetter classes.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @return the package for the associated classes.
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     */
    public String retrievePkStatementSetterPackage(
        final String packageName,
        final String engineName,
        final String tableName)
    {
        return
            retrieveJdbcOperationsPackage(
                packageName, engineName, tableName);
    }

    /**
     * Retrieves the folder for the PkStatementSetter classes.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder for the associated pointers.
     * @precondition parentFolder != null
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     */
    public File retrievePkStatementSetterFolder(
        final File parentFolder,
        final String packageName,
        final String engineName,
        final String tableName,
        final boolean useSubfolders)
    {
        return
            retrieveJdbcOperationsFolder(
                parentFolder,
                packageName,
                engineName,
                tableName,
                useSubfolders);
    }

    /**
     * Retrieves the package name for the FkStatementSetter classes.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @return the package for the associated classes.
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     */
    public String retrieveFkStatementSetterPackage(
        final String packageName,
        final String engineName,
        final String tableName)
    {
        return
            retrieveJdbcOperationsPackage(
                packageName, engineName, tableName);
    }

    /**
     * Retrieves the folder for the FkStatementSetter classes.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder for the associated pointers.
     * @precondition parentFolder != null
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     */
    public File retrieveFkStatementSetterFolder(
        final File parentFolder,
        final String packageName,
        final String engineName,
        final String tableName,
        final boolean useSubfolders)
    {
        return
            retrieveJdbcOperationsFolder(
                parentFolder,
                packageName,
                engineName,
                tableName,
                useSubfolders);
    }

    /**
     * Retrieves the package name for RepositoryDAO templates.
     * @param packageName the original package.
     * @param engineName the engine.
     * @return the package for the associated DAO class.
     */
    public String retrieveRepositoryDAOPackage(
        final String packageName, final String engineName)
    {
        return retrieveDAOPackage(packageName, engineName);
    }

    /**
     * Retrieves the folder for RepositoryDAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param engineName the engine.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated DAO should be
     * generated.
     */
    public File retrieveRepositoryDAOFolder(
        final File parentFolder,
        final String packageName,
        final String engineName,
        final boolean useSubfolders)
    {
        return
            retrieveDAOFolder(
                parentFolder, packageName, engineName, useSubfolders);
    }

    /**
     * Retrieves the package name for BaseRepositoryDAO templates.
     * @param packageName the original package.
     * @return the package for the associated DAO class.
     */
    public String retrieveBaseRepositoryDAOPackage(
        final String packageName)
    {
        return retrieveBaseDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for BaseRepositoryDAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated DAO should be
     * generated.
     */
    public File retrieveBaseRepositoryDAOFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveBaseDAOFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for DAOListener templates.
     * @param packageName the original package.
     * @return the package for the associated DAO class.
     */
    public String retrieveDAOListenerPackage(final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for DAOListener templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated DAO should be
     * generated.
     */
    public File retrieveDAOListenerFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveRdbFolder(
                parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for DAOListenerImpl templates.
     * @param packageName the original package.
     * @return the package for the associated DAO class.
     */
    public String retrieveDAOListenerImplPackage(
        final String packageName)
    {
        return retrieveDAOListenerPackage(packageName);
    }

    /**
     * Retrieves the folder for DAOListenerImpl templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated DAO should be
     * generated.
     */
    public File retrieveDAOListenerImplFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return
            retrieveDAOListenerFolder(
                parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for JdbcTemplate class.
     * @param packageName the original package.
     * @return the package for such class.
     */
    public String retrieveJdbcTemplatePackage(
        final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for JdbcTemplate class.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    public File retrieveJdbcTemplateFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveRdbFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for ThreadAwareDataSourceWrapper class.
     * @param packageName the original package.
     * @return the package for such class.
     */
    public String retrieveThreadAwareDataSourceWrapperPackage(
        final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for ThreadAwareDataSourceWrapper class.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    public File retrieveThreadAwareDataSourceWrapperFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveRdbFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for ThreadLocalBag class.
     * @param packageName the original package.
     * @return the package for such class.
     */
    public String retrieveThreadLocalBagPackage(
        final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for ThreadLocalBag class.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    public File retrieveThreadLocalBagFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveRdbFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for TransactionManager class.
     * @param packageName the original package.
     * @return the package for such class.
     */
    public String retrieveTransactionManagerPackage(
        final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for TransactionManager class.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    public File retrieveTransactionManagerFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveRdbFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for JndiUtils class.
     * @param packageName the original package.
     * @return the package for such class.
     */
    public String retrieveJndiUtilsPackage(
        final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for JndiUtils class.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    public File retrieveJndiUtilsFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveRdbFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for DataSourceTransactionToken class.
     * @param packageName the original package.
     * @return the package for such class.
     */
    public String retrieveDataSourceTransactionTokenPackage(
        final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for DataSourceTransactionToken class.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    public File retrieveDataSourceTransactionTokenFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveRdbFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for StatisticsProvider class.
     * @param packageName the original package.
     * @return the package for such class.
     */
    public String retrieveStatisticsProviderPackage(
        final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for StatisticsProvider class.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    public File retrieveStatisticsProviderFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveRdbFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for StatisticsProviderMBeanPublisher class.
     * @param packageName the original package.
     * @return the package for such class.
     */
    public String retrieveStatisticsProviderMBeanPublisherPackage(
        final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for StatisticsProviderMBeanPublisher class.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    public File retrieveStatisticsProviderMBeanPublisherFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveRdbFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Retrieves the package name for BaseResultSetExtractor class.
     * @param packageName the original package.
     * @return the package for such class.
     */
    public String retrieveBaseResultSetExtractorPackage(
        final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for BaseResultSetExtractor class.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubfolders whether to use subfolders.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    public File retrieveBaseResultSetExtractorFolder(
        final File parentFolder,
        final String packageName,
        final boolean useSubfolders)
    {
        return retrieveRdbFolder(parentFolder, packageName, useSubfolders);
    }

    /**
     * Extracts the class name of given fully-qualified class.
     * @param fqcn such information.
     * @return the class name.
     * @precondition fqcn != null
     */
    public String extractClassName(final String fqdn)
    {
        String result = null;

        String[] t_astrPieces = split(fqdn, ".");

        int t_iCount = (t_astrPieces != null) ? t_astrPieces.length : 0;

        if  (t_iCount > 0)
        {
            result = t_astrPieces[t_iCount - 1];
        }

        return result;
    }

    /**
     * Splits given value into chunks separated by a separator.
     * @param value the value.
     * @param separator the separator.
     * @return the chunks.
     * @precondition value != null
     * @precondition separator != null
     */
    public String[] split(final String value, final String separator)
    {
        return split(value, separator, StringUtils.getInstance());
    }

    /**
     * Splits given value into chunks separated by a separator.
     * @param value the value.
     * @param separator the separator.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return the chunks.
     * @precondition value != null
     * @precondition separator != null
     * @precondition stringUtils != null
     */
    protected String[] split(
        final String value,
        final String separator,
        final StringUtils stringUtils)
    {
        return stringUtils.split(value, new String[] { separator });
    }
}
