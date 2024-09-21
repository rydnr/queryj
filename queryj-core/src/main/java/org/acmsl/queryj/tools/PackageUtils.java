/*
                        QueryJ Core

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
 * Importing some QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJSettings;
import org.acmsl.queryj.tools.handlers.ParameterValidationHandler;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.util.Locale;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Provides some useful methods for retrieving package information about
 * the generated code.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
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
     * The subpackage for Relational Database common classes.
     */
    public static final String RDB_SUBPACKAGE = "rdb";

    /**
     * The subpackage for TableRepository.
     */
    public static final String TABLE_REPOSITORY_SUBPACKAGE = ParameterValidationHandler.TABLES;

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
    protected PackageUtils() {}

    /**
     * Retrieves a PackageUtils instance.
     * @return such instance.
     */
    @NotNull
    public static PackageUtils getInstance()
    {
        return PackageUtilsSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the package name for given subpackage.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @return the complate package information for given subpackage.
     */
    @NotNull
    protected String retrievePackage(
        @NotNull final String packageName, @NotNull final String subpackage)
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
     * @param stringValidator the {@link StringValidator} instance.
     * @return the complete package information for given subpackage.
     */
    @NotNull
    protected String retrievePackage(
        @NotNull final String packageName,
        @NotNull final String subpackage,
        @NotNull final StringValidator stringValidator)
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
     * @param subFolders whether to use sub folders or not.
     * @return the complete package information for given subpackage.
     */
    @NotNull
    protected String retrieveTestPackage(
        @NotNull final String packageName,
        @NotNull final String subpackage,
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
     * @param subFolders whether to use sub folders or not.
     * @return the complete package information for given subpackage.
     */
    @NotNull
    protected String retrieveTestPackage(
        @NotNull final String packageName, final boolean subFolders)
    {
        return retrieveTestPackage(packageName, "", subFolders);
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @return the folder in which the associated class should be
     * generated.
     */
    @NotNull
    protected File retrieveFolder(
        @NotNull final File parentFolder, @NotNull final String packageName)
    {
        return retrieveFolder(parentFolder, packageName, false);
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated class should be
     * generated.
     */
    @NotNull
    protected File retrieveFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return retrieveFolder(parentFolder, packageName, useSubFolders, false);
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @param useSubFolders whether to use sub folders or not.
     * @param testPackage whether the package contains unit tests.
     * @return the folder in which the associated class should be
     * generated.
     */
    @NotNull
    protected File retrieveFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        @NotNull final String subpackage,
        final boolean useSubFolders,
        final boolean testPackage)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                subpackage,
                useSubFolders,
                testPackage,
                StringUtils.getInstance(),
                StringValidator.getInstance());
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @param useSubFolders whether to use sub folders or not.
     * @param testPackage whether the package contains unit tests.
     * @param stringUtils the {@link StringUtils} instance.
     * @param stringValidator the {@link StringValidator} instance.
     * @return the folder in which the associated DAO class should be
     * generated.
     */
    @NotNull
    protected File retrieveFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        @NotNull final String subpackage,
        final boolean useSubFolders,
        final boolean testPackage,
        @NotNull final StringUtils stringUtils,
        @NotNull final StringValidator stringValidator)
    {
        @NotNull final File result;

        String t_strWholePackage = packageName;

        if  (!stringValidator.isEmpty(subpackage))
        {
            t_strWholePackage += "." + subpackage;
        }

        result =
            retrieveFolder(
                parentFolder,
                t_strWholePackage,
                useSubFolders,
                testPackage,
                stringUtils);

        return result;
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubFolders whether to use sub folders or not.
     * @param testPackage whether the package contains unit tests.
     * @return the folder in which the associated DAO class should be
     * generated.
     */
    @NotNull
    protected File retrieveFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders,
        final boolean testPackage)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                useSubFolders,
                testPackage,
                StringUtils.getInstance());
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubFolders whether to use sub folders or not.
     * @param testPackage whether the package contains unit tests.
     * @param stringUtils the {@link StringUtils} instance.
     * @return the folder in which the associated DAO class should be
     * generated.
     */
    @NotNull
    protected File retrieveFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders,
        final boolean testPackage,
        @NotNull final StringUtils stringUtils)
    {
        String t_strResult = parentFolder.getPath();

        if  (useSubFolders)
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
     * @param useSubFolders whether to use subfolders or not.
     * @return the folder in which the associated class should be
     * generated.
     */
    @NotNull
    protected File retrieveTestFolder(
        @NotNull final File parentFolder, final boolean useSubFolders)
    {
        return
            retrieveFolder(
                parentFolder,
                (useSubFolders ? "" : UNITTESTS_PACKAGE_PREFIX),
                useSubFolders,
                true);
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated class should be
     * generated.
     */
    @NotNull
    protected File retrieveTestFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                useSubFolders,
                true);
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated class should be
     * generated.
     */
    @SuppressWarnings("unused")
    @NotNull
    protected File retrieveTestFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        @NotNull final String subpackage,
        final boolean useSubFolders)
    {
        return
            retrieveFolder(
                    retrieveTestFolder(parentFolder, useSubFolders),
                    packageName,
                    subpackage,
                    useSubFolders,
                    true);
    }

    /**
     * Retrieves the package name for base DAO templates.
     * @param packageName the original package.
     * @return the package for the associated base DAO interface.
     */
    @NotNull
    public String retrieveBaseDAOPackage(@NotNull final String packageName)
    {
        return retrievePackage(packageName, BASE_DAO_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for base DAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubFolders whether to use subfolders.
     * @return the folder in which the associated DAO class should be
     * generated.
     */
    @NotNull
    public File retrieveBaseDAOFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                BASE_DAO_SUBPACKAGE,
                useSubFolders,
                false);
    }

    /**
     * Retrieves the package name for base DAO factory templates.
     * @param packageName the original package.
     * @return the package for the associated base DAO factory class.
     */
    @NotNull
    public String retrieveBaseDAOFactoryPackage(
        @NotNull final String packageName)
    {
        return retrieveBaseDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for base DAO factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which  the associated DAO factory should be
     * generated.
     */
    @NotNull
    public File retrieveBaseDAOFactoryFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveBaseDAOFolder(parentFolder, packageName, useSubFolders);
    }

    /**
     * Retrieves the package name for value object templates.
     * @param packageName the original package.
     * @return the package for the associated value object class.
     */
    @NotNull
    public String retrieveValueObjectPackage(@NotNull final String packageName)
    {
        return retrievePackage(packageName, VALUE_OBJECT_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for value object templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated Jdbc DAO should be
     * generated.
     */
    @NotNull
    public File retrieveValueObjectFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                VALUE_OBJECT_SUBPACKAGE,
                useSubFolders,
                false);
    }

    /**
     * Retrieves the package name for value object factory
     * templates.
     * @param packageName the original package.
     * @return the package for the associated value object
     * factory class.
     */
    @NotNull
    public String retrieveValueObjectFactoryPackage(
        @NotNull final String packageName)
    {
        return retrieveValueObjectPackage(packageName);
    }

    /**
     * Retrieves the folder for value object factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated value object
     * factories should be generated.
     */
    @NotNull
    public File retrieveValueObjectFactoryFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveValueObjectFolder(
                parentFolder, packageName, useSubFolders);
    }

    /**
     * Retrieves the package name for baseValue object templates.
     * @param packageName the original package.
     * @return the package for the associated baseValue object class.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String retrieveBaseValueObjectPackage(
        @NotNull final String packageName)
    {
        return retrievePackage(packageName, BASE_VALUE_OBJECT_SUBPACKAGE);
    }

    /**
     * Retrieves the package name for DataAccessManager templates.
     * @param packageName the original package.
     * @return the package for the associated manager class.
     */
    @NotNull
    public String retrieveDataAccessManagerPackage(
        @NotNull final String packageName)
    {
        return retrieveBaseDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for DataAccessManager templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated manager should be
     * generated.
     */
    @NotNull
    public File retrieveDataAccessManagerFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return retrieveBaseDAOFolder(parentFolder, packageName, useSubFolders);
    }

    /**
     * Retrieves the package name for base Relational Database common classes.
     * @param packageName the original package.
     * @return the package for the associated rdb classes.
     */
    @NotNull
    public String retrieveRdbPackage(@NotNull final String packageName)
    {
        return
            retrievePackage(
                retrieveBaseDAOPackage(packageName), RDB_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for base Relational Database common classes.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    @NotNull
    public File retrieveRdbFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveFolder(
                retrieveBaseDAOFolder(
                    parentFolder, packageName, useSubFolders),
                RDB_SUBPACKAGE);
    }

    /**
     * Retrieves the package name for Jdbc DAO templates.
     * @param packageName the original package.
     * @return the package for the associated Jdbc DAO class.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String retrieveJdbcDAOPackage(@NotNull final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for Jdbc DAO factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated Jdbc DAO should be
     * generated.
     */
    @SuppressWarnings("unused")
    @NotNull
    public File retrieveJdbcDAOFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return retrieveRdbFolder(parentFolder, packageName, useSubFolders);
    }

    /**
     * Retrieves the subpackage name for DAO templates.
     * @param engineName the DAO engine.
     * @return the subpackage for the associated DAO class.
     */
    @NotNull
    public String retrieveDAOSubpackage(@NotNull final String engineName)
    {
        return engineName.toLowerCase(Locale.US);
    }

    /**
     * Retrieves the package name for DAO templates.
     * @param packageName the original package.
     * @param engineName the DAO engine.
     * @return the package for the associated DAO class.
     */
    @NotNull
    public String retrieveDAOPackage(
            @NotNull final String packageName, @NotNull final String engineName)
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
     */
    @NotNull
    public File retrieveDAOFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        @NotNull final String engineName,
        final boolean useSubfolders)
    {
        return
            retrieveFolder(
                retrieveRdbFolder(
                    parentFolder, packageName, useSubfolders),
                engineName.toLowerCase(Locale.US));
    }

    /**
     * Retrieves the package name for DAO factory templates.
     * @param packageName the original package.
     * @param engineName the DAO engine.
     * @return the package for the associated DAO factory class.
     */
    @NotNull
    public String retrieveDAOFactoryPackage(
        @NotNull final String packageName, @NotNull final String engineName)
    {
        return retrieveDAOPackage(packageName, engineName);
    }

    /**
     * Retrieves the folder for DAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param engineName the DAO engine.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated DAO factory
     * should be generated.
     */
    @NotNull
    public File retrieveDAOFactoryFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        @NotNull final String engineName,
        final boolean useSubFolders)
    {
        return
            retrieveDAOFolder(
                parentFolder, packageName, engineName, useSubFolders);
    }

    /**
     * Retrieves the package name for repository templates.
     * @param packageName the original package.
     * @return the package for the associated repository class.
     */
    @NotNull
    public String retrieveTableRepositoryPackage(
        @NotNull final String packageName)
    {
        return retrievePackage(packageName, TABLE_REPOSITORY_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for repository templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which  the associated should be
     * generated.
     */
    @NotNull
    public File retrieveTableRepositoryFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                TABLE_REPOSITORY_SUBPACKAGE,
                useSubFolders,
                false);
    }

    /**
     * Retrieves the package name for table templates.
     * @param packageName the original package.
     * @return the package for the associated table classes.
     */
    @NotNull
    @SuppressWarnings("unused")
    public String retrieveTablePackage(@NotNull final String packageName)
    {
        return retrieveTableRepositoryPackage(packageName);
    }

    /**
     * Retrieves the folder for table templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated templates should be
     * generated.
     */
    @SuppressWarnings("unused")
    @NotNull
    public File retrieveTableFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveTableRepositoryFolder(
                parentFolder, packageName, useSubFolders);
    }

    /**
     * Retrieves the package name for DAO test templates.
     * @param packageName the original package.
     * @param engineName the engine name.
     * @param subFolders whether to use subfolders or not.
     * @return the package for the associated DAO tests.
     */
    @NotNull
    public String retrieveDAOTestPackage(
        @NotNull final String packageName,
        @NotNull final String engineName,
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
     * @param useSubFolders whether to use sub folders or not.
     * @return the folder in which the associated test class should be
     * generated.
     */
    @SuppressWarnings("unused")
    @NotNull
    public File retrieveDAOTestFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        @NotNull final String engineName,
        final boolean useSubFolders)
    {
        return
            retrieveTestFolder(
                parentFolder,
                retrieveDAOTestPackage(packageName, engineName, useSubFolders),
                useSubFolders);
    }

    /**
     * Retrieves the package name for the base test suite template.
     * @param packageName the original package.
     * @param useSubFolders whether to use sub folders.
     * @return the package for the associated suite.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String retrieveBaseTestSuitePackage(
        @NotNull final String packageName, final boolean useSubFolders)
    {
        return retrieveTestPackage(packageName, useSubFolders);
    }

    /**
     * Retrieves the folder for the base test suite template.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated suite class should be
     * generated.
     */
    @SuppressWarnings("unused")
    @NotNull
    public File retrieveBaseTestSuiteFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveFolder(
                retrieveTestFolder(parentFolder, useSubFolders),
                packageName);
    }

    /**
     * Retrieves the package name for the functions templates.
     * @param packageName the original package.
     * @return the package for the associated functions classes.
     */
    @NotNull
    public String retrieveFunctionsPackage(@NotNull final String packageName)
    {
        return
            retrievePackage(packageName, FUNCTIONS_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for the functions templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated functions classes should be
     * generated.
     */
    @NotNull
    public File retrieveFunctionsFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                FUNCTIONS_SUBPACKAGE,
                useSubFolders,
                false);
    }

    /**
     * Retrieves the package name for the functions test templates.
     * @param packageName the original package.
     * @param subFolders whether to use sub folders or not.
     * @return the package for the associated test classes.
     */
    @NotNull
    public String retrieveTestFunctionsPackage(
        @NotNull final String packageName, final boolean subFolders)
    {
        return
            retrieveTestPackage(
                retrieveFunctionsPackage(packageName), subFolders);
    }

    /**
     * Retrieves the folder for the functions test templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated functions classes should be
     * generated.
     */
    @SuppressWarnings("unused")
    @NotNull
    public File retrieveTestFunctionsFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveTestFolder(
                parentFolder,
                retrieveTestFunctionsPackage(packageName, useSubFolders),
                useSubFolders);
    }

    /**
     * Retrieves the package name for ProcedureRepository templates.
     * @param packageName the original package.
     * @return the package for the associated repository class.
     */
    @NotNull
    public String retrieveProcedureRepositoryPackage(
        @NotNull final String packageName)
    {
        return retrieveFunctionsPackage(packageName);
    }

    /**
     * Retrieves the folder for ProcedureRepository templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated template should be
     * generated.
     */
    @NotNull
    public File retrieveProcedureRepositoryFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return retrieveFunctionsFolder(parentFolder, packageName, useSubFolders);
    }

    /**
     * Retrieves the package name for KeywordRepository templates.
     * @param packageName the original package.
     * @return the package for the associated repository class.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String retrieveKeywordRepositoryPackage(
        @NotNull final String packageName)
    {
        return retrieveProcedureRepositoryPackage(packageName);
    }

    /**
     * Retrieves the folder for KeywordRepository templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use subFolders.
     * @return the folder in which the associated template should be
     * generated.
     */
    @SuppressWarnings("unused")
    @NotNull
    public File retrieveKeywordRepositoryFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveProcedureRepositoryFolder(
                parentFolder, packageName, useSubFolders);
    }

    /**
     * Retrieves the package name for Mock DAO templates.
     * @param packageName the original package.
     * @return the package for the associated DAO class.
     */
    @NotNull
    public String retrieveMockDAOPackage(@NotNull final String packageName)
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
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which  the associated DAO class should be
     * generated.
     */
    @NotNull
    public File retrieveMockDAOFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveFolder(
                retrieveBaseDAOFolder(parentFolder, packageName, useSubFolders),
                MOCK_DAO_SUBPACKAGE);
    }

    /**
     * Retrieves the package name for Mock DAO factory templates.
     * @param packageName the original package.
     * @return the package for the associated DAO factory class.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String retrieveMockDAOFactoryPackage(@NotNull final String packageName)
    {
        return retrieveMockDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for Mock DAO factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which  the associated DAO factory should be
     * generated.
     */
    @SuppressWarnings("unused")
    @NotNull
    public File retrieveMockDAOFactoryFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return retrieveMockDAOFolder(parentFolder, packageName, useSubFolders);
    }

    /**
     * Retrieves the package name for Mock DAO test templates.
     * @param packageName the original package.
     * @param subFolders whether to use sub folders or not.
     * @return the package for the associated Mock DAO tests.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String retrieveMockDAOTestPackage(
        @NotNull final String packageName, final boolean subFolders)
    {
        return
            retrieveTestPackage(
                retrieveMockDAOPackage(packageName), subFolders);
    }

    /**
     * Retrieves the folder for Mock DAO test templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated test class should be
     * generated.
     */
    @SuppressWarnings("unused")
    @NotNull
    public File retrieveMockDAOTestFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveMockDAOFolder(
                retrieveTestFolder(parentFolder, useSubFolders),
                packageName,
                useSubFolders);
    }

    /**
     * Retrieves the package name for XML DAO templates.
     * @param packageName the original package.
     * @return the package for the associated DAO class.
     */
    @NotNull
    public String retrieveXMLDAOPackage(@NotNull final String packageName)
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
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated DAO class should be
     * generated.
     */
    @NotNull
    public File retrieveXMLDAOFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveFolder(
                retrieveBaseDAOFolder(
                    parentFolder, packageName, useSubFolders),
                XML_DAO_SUBPACKAGE);
    }

    /**
     * Retrieves the package name for XML DAO factory templates.
     * @param packageName the original package.
     * @return the package for the associated DAO factory class.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String retrieveXmlDAOFactoryPackage(@NotNull final String packageName)
    {
        return retrieveXMLDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for XML DAO factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which  the associated DAO factory should be
     * generated.
     */
    @SuppressWarnings("unused")
    @NotNull
    public File retrieveXmlDAOFactoryFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return retrieveXMLDAOFolder(parentFolder, packageName, useSubFolders);
    }

    /**
     * Retrieves the package name for XML DAO test templates.
     * @param packageName the original package.
     * @param subFolders whether to use sub folders or not.
     * @return the package for the associated XML DAO tests.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String retrieveXMLDAOTestPackage(
        @NotNull final String packageName, final boolean subFolders)
    {
        return
            retrieveTestPackage(
                retrieveXMLDAOPackage(packageName), subFolders);
    }

    /**
     * Retrieves the folder for XML DAO test templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated test class should be
     * generated.
     */
    @SuppressWarnings("unused")
    @NotNull
    public File retrieveXMLDAOTestFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveXMLDAOFolder(
                retrieveTestFolder(parentFolder, useSubFolders),
                packageName,
                useSubFolders);
    }

    /**
     * Retrieves the package name for XMLValueObjectFactory templates.
     * @param packageName the original package.
     * @return the package for the associated factory class.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String retrieveXMLValueObjectFactoryPackage(
        @NotNull final String packageName)
    {
        return retrieveXMLDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for XMLValueObjectFactory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated factory class should be
     * generated.
     */
    @SuppressWarnings("unused")
    @NotNull
    public File retrieveXMLValueObjectFactoryFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return
            retrieveXMLDAOFolder(
                parentFolder, packageName, useSubFolders);
    }

    /**
     * Retrieves the package name for the JDBC operation classes.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @return the package for the associated pointers.
     */
    @NotNull
    public String retrieveJdbcOperationsPackage(
        @NotNull final String packageName,
        @NotNull final String engineName,
        @NotNull final String tableName)
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
     * @param stringUtils the {@link StringUtils} instance.
     * @return the package for the associated pointers.
     */
    @NotNull
    public String retrieveJdbcOperationsPackage(
        @NotNull final String packageName,
        @NotNull final String engineName,
        @NotNull final String tableName,
        @NotNull final StringUtils stringUtils)
    {
        return
            retrievePackage(
                retrieveDAOPackage(packageName, engineName),
                stringUtils.capitalize(
                    tableName, QueryJSettings.DEFAULT_LOCALE, '_').toLowerCase(QueryJSettings.DEFAULT_LOCALE));
    }

    /**
     * Retrieves the folder for the JDBC operation classes.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder for the associated pointers.
     */
    @NotNull
    public File retrieveJdbcOperationsFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        @NotNull final String engineName,
        @NotNull final String tableName,
        final boolean useSubFolders)
    {
        return
            retrieveJdbcOperationsFolder(
                parentFolder,
                packageName,
                engineName,
                tableName,
                useSubFolders,
                StringUtils.getInstance());
    }

    /**
     * Retrieves the folder for the JDBC operation classes.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @param useSubFolders whether to use sub folders.
     * @param stringUtils the {@link StringUtils} instance.
     * @return the folder for the associated pointers.
     */
    @NotNull
    public File retrieveJdbcOperationsFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        @NotNull final String engineName,
        @NotNull final String tableName,
        final boolean useSubFolders,
        @NotNull final StringUtils stringUtils)
    {
        return
            retrieveFolder(
                retrieveDAOFolder(
                    parentFolder, packageName, engineName, useSubFolders),
                stringUtils.capitalize(
                    tableName, QueryJSettings.DEFAULT_LOCALE, '_').toLowerCase(QueryJSettings.DEFAULT_LOCALE));
    }

    /**
     * Retrieves the package name for QueryPreparedStatementCreator class.
     * @param packageName the original package.
     * @return the package for such class.
     */
    @SuppressWarnings("unused")
    @NotNull
    public String retrieveQueryPreparedStatementCreatorPackage(
        @NotNull final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for QueryPreparedStatementCreator class.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param useSubFolders whether to use sub folders.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    @SuppressWarnings("unused")
    @NotNull
    public File retrieveQueryPreparedStatementCreatorFolder(
        @NotNull final File parentFolder,
        @NotNull final String packageName,
        final boolean useSubFolders)
    {
        return retrieveRdbFolder(parentFolder, packageName, useSubFolders);
    }

    /**
     * Splits given value into chunks separated by a separator.
     * @param value the value.
     * @param separator the separator.
     * @return the chunks.
     */
    @NotNull
    public String[] split(
        @NotNull final String value, @NotNull final String separator)
    {
        return split(value, separator, StringUtils.getInstance());
    }

    /**
     * Splits given value into chunks separated by a separator.
     * @param value the value.
     * @param separator the separator.
     * @param stringUtils the {@link StringUtils} instance.
     * @return the chunks.
     */
    @NotNull
    protected String[] split(
        @NotNull final String value,
        @NotNull final String separator,
        @NotNull final StringUtils stringUtils)
    {
        return stringUtils.split(value, new String[] { separator });
    }
}
