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
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.acmsl.commons.utils.StringUtils;
import org.acmsl.commons.utils.StringValidator;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Provides some useful methods for retrieving package information about
 * the generated code.
 * @author <a href="mailto:jsanleandro@yahoo.es"
           >Jose San Leandro</a>
 * @version $Revision$
 */
public class PackageUtils
{
    /**
     * The package prefix for unit tests.
     */
    public static final String UNITTESTS_PACKAGE_PREFIX = "unittests";

    /**
     * The subpackage for BaseDAO entitites.
     */
    public static final String BASE_DAO_SUBPACKAGE = "dao";

    /**
     * The subpackage for ValueObject entities.
     */
    public static final String VALUE_OBJECT_SUBPACKAGE = "vo";

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
    protected static void setReference(final PackageUtils utils)
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
            result = new PackageUtils();

            setReference(result);
        }

        return result;
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
                packageName, subpackage, StringValidator.getInstance());
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
            result += "." + subpackage;
        }

             return result;
    }

    /**
     * Retrieves the package name for given subpackage.
     * @param packageName the original package.
     * @param subpackage the subpackage.
     * @return the complate package information for given subpackage.
     * @precondition packageName != null
     * @precondition subpackage != null
     */
    protected String retrieveTestPackage(
        final String packageName, final String subpackage)
    {
        return
            retrievePackage(
                retrievePackage(UNITTESTS_PACKAGE_PREFIX, packageName),
                subpackage);
    }

    /**
     * Retrieves the package name for given subpackage.
     * @param packageName the original package.
     * @return the complate package information for given subpackage.
     * @precondition packageName != null
     */
    protected String retrieveTestPackage(final String packageName)
    {
        return retrieveTestPackage(packageName, "");
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
        final File parentFolder,
        final String packageName)
    {
        return retrieveFolder(parentFolder, packageName, "");
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
        final File parentFolder,
        final String packageName,
        final String subpackage)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                subpackage,
                StringUtils.getInstance(),
                StringValidator.getInstance());
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @return the folder in which the associated class should be
     * generated.
     * @precondition parentFolder != null
     */
    protected File retrieveTestFolder(final File parentFolder)
    {
        return retrieveFolder(parentFolder, UNITTESTS_PACKAGE_PREFIX);
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
    protected File retrieveTestFolder(
        final File parentFolder,
        final String packageName,
        final String subpackage)
    {
        return
            retrieveFolder(
                retrieveTestFolder(parentFolder),
                packageName,
                subpackage);
    }

    /**
     * Retrieves the folder for given subpackage.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param subpackage the subpackage.
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
        final StringUtils stringUtils,
        final StringValidator stringValidator)
    {
        String t_strResult =
              parentFolder.getPath()
            + File.separator
            + stringUtils.packageToFilePath(packageName);

        if  (!stringValidator.isEmpty(subpackage))
        {
            t_strResult +=
                  File.separator
                + subpackage;
        }

        return new File(t_strResult);
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
     * @return the folder in which the associated DAO class should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     */
    public File retrieveBaseDAOFolder(
        final File parentFolder, final String packageName)
    {
        return
            retrieveFolder(
                parentFolder, packageName, BASE_DAO_SUBPACKAGE);
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
     * @return the folder in which  the associated DAO factory should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     */
    public File retrieveBaseDAOFactoryFolder(
        final File parentFolder, final String packageName)
    {
        return retrieveBaseDAOFolder(parentFolder, packageName);
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
     * @return the folder in which the associated Jdbc DAO should be
     * generated.
     */
    public File retrieveValueObjectFolder(
        final File parentFolder, final String packageName)
    {
        return
            retrieveFolder(
                parentFolder, packageName, VALUE_OBJECT_SUBPACKAGE);
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
     * @return the folder in which the associated value object
     * factories should be generated.
     */
    public File retrieveValueObjectFactoryFolder(
        final File parentFolder, final String packageName)
    {
        return retrieveValueObjectFolder(parentFolder, packageName);
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
     * @return the folder in which the associated manager should be
     * generated.
     */
    public File retrieveDataAccessManagerFolder(
        final File parentFolder, final String packageName)
    {
        return retrieveBaseDAOFolder(parentFolder, packageName);
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
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    public File retrieveRdbFolder(
        final File parentFolder, final String packageName)
    {
        return
            retrieveFolder(
                retrieveBaseDAOFolder(
                    parentFolder, packageName),
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
     * @return the folder in which the associated Jdbc DAO should be
     * generated.
     */
    public File retrieveJdbcDAOFolder(
        final File parentFolder, final String packageName)
    {
        return retrieveRdbFolder(parentFolder, packageName);
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
                retrieveRdbPackage(packageName), engineName.toLowerCase());
    }

    /**
     * Retrieves the folder for DAO templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @param engineName the DAO engine.
     * @return the folder in which  the associated DAO class should be
     * generated.
     * @precondition parentFolder != null
     * @precondition packageName != null
     * @precondition engineName != null
     */
    public File retrieveDAOFolder(
        final File parentFolder,
        final String packageName,
        final String engineName)
    {
        return
            retrieveFolder(
                retrieveRdbFolder(parentFolder, packageName),
                engineName.toLowerCase());
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
     * @return the folder in which the associated DAO factory
     * should be generated.
     */
    public File retrieveDAOFactoryFolder(
        final File parentFolder,
        final String packageName,
        final String engineName)
    {
        return retrieveDAOFolder(parentFolder, packageName, engineName);
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
     * @return the folder in which  the associated should be
     * generated.
     */
    public File retrieveTableRepositoryFolder(
        final File parentFolder, final String packageName)
    {
        return
            retrieveFolder(
                parentFolder, packageName, TABLE_REPOSITORY_SUBPACKAGE);
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
     * @return the folder in which the associated templates should be
     * generated.
     */
    public File retrieveTableFolder(
        final File parentFolder, final String packageName)
    {
        return retrieveTableRepositoryFolder(parentFolder, packageName);
    }

    /**
     * Retrieves the package name for DAO test templates.
     * @param packageName the original package.
     * @param engineName the engine name.
     * @return the package for the associated DAO tests.
     */
    public String retrieveDAOTestPackage(
        final String packageName, final String engineName)
    {
        return
            retrieveTestPackage(
                retrieveDAOPackage(packageName, engineName));
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
        final File parentFolder,
        final String packageName,
        final String engineName)
    {
        return
            retrieveDAOFolder(
                retrieveTestFolder(parentFolder),
                packageName,
                engineName);
    }

    /**
     * Retrieves the package name for the base test suite template.
     * @param packageName the original package.
     * @return the package for the associated suite.
     */
    public String retrieveBaseTestSuitePackage(final String packageName)
    {
        return retrieveTestPackage(packageName);
    }

    /**
     * Retrieves the folder for the base test suite template.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @return the folder in which the associated suite class should be
     * generated.
     */
    public File retrieveBaseTestSuiteFolder(
        final File parentFolder, final String packageName)
    {
        return
            retrieveFolder(
                retrieveTestFolder(parentFolder),
                packageName);
    }

    /**
     * Retrieves the package name for the functions templates.
     * @param packageName the original package.
     * @return the package for the associated functions classes.
     */
    public String retrieveFunctionsPackage(final String packageName)
    {
        return retrievePackage(packageName, FUNCTIONS_SUBPACKAGE);
    }

    /**
     * Retrieves the folder for the functions templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @return the folder in which the associated functions classes should be
     * generated.
     */
    public File retrieveFunctionsFolder(
        final File parentFolder, final String packageName)
    {
        return
            retrieveFolder(
                parentFolder,
                packageName,
                FUNCTIONS_SUBPACKAGE);
    }

    /**
     * Retrieves the package name for the functions test templates.
     * @param packageName the original package.
     * @return the package for the associated test classes.
     */
    public String retrieveTestFunctionsPackage(final String packageName)
    {
        return
            retrieveTestPackage(retrieveFunctionsPackage(packageName));
    }

    /**
     * Retrieves the folder for the functions test templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @return the folder in which the associated functions classes should be
     * generated.
     */
    public File retrieveTestFunctionsFolder(
        final File parentFolder, final String packageName)
    {
        return
            retrieveFunctionsFolder(
                retrieveTestFolder(parentFolder),
                packageName);
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
     * @return the folder in which the associated template should be
     * generated.
     */
    public File retrieveDAOChooserFolder(
        final File parentFolder, final String packageName)
    {
        return retrieveBaseDAOFolder(parentFolder, packageName);
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
     * @return the folder in which the associated template should be
     * generated.
     */
    public File retrieveProcedureRepositoryFolder(
        final File parentFolder, final String packageName)
    {
        return retrieveFunctionsFolder(parentFolder, packageName);
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
     * @return the folder in which the associated template should be
     * generated.
     */
    public File retrieveKeywordRepositoryFolder(
        final File parentFolder, final String packageName)
    {
        return retrieveProcedureRepositoryFolder(parentFolder, packageName);
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
     * @return the folder in which  the associated DAO class should be
     * generated.
     */
    public File retrieveMockDAOFolder(
        final File parentFolder, final String packageName)
    {
        return
            retrieveFolder(
                retrieveBaseDAOFolder(parentFolder, packageName),
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
     * @return the folder in which  the associated DAO factory should be
     * generated.
     */
    public File retrieveMockDAOFactoryFolder(
        final File parentFolder, final String packageName)
    {
        return retrieveMockDAOFolder(parentFolder, packageName);
    }

    /**
     * Retrieves the package name for Mock DAO test templates.
     * @param packageName the original package.
     * @return the package for the associated Mock DAO tests.
     */
    public String retrieveMockDAOTestPackage(final String packageName)
    {
        return retrieveTestPackage(retrieveMockDAOPackage(packageName));
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
        return
            retrieveMockDAOFolder(
                retrieveTestFolder(parentFolder),
                packageName);
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
     * @return the folder in which  the associated DAO class should be
     * generated.
     */
    public File retrieveXMLDAOFolder(
        final File parentFolder, final String packageName)
    {
        return
            retrieveFolder(parentFolder, packageName, XML_DAO_SUBPACKAGE);
    }

    /**
     * Retrieves the package name for XML DAO factory templates.
     * @param packageName the original package.
     * @return the package for the associated DAO factory class.
     */
    public String retrieveXMLDAOFactoryPackage(final String packageName)
    {
        return retrieveXMLDAOPackage(packageName);
    }

    /**
     * Retrieves the folder for XML DAO factory templates.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which  the associated DAO factory should be
     * generated.
     */
    public File retrieveXMLDAOFactoryFolder(
        final File parentFolder, final String packageName)
    {
        return retrieveXMLDAOFolder(parentFolder, packageName);
    }

    /**
     * Retrieves the package name for XML DAO test templates.
     * @param packageName the original package.
     * @return the package for the associated XML DAO tests.
     */
    public String retrieveXMLDAOTestPackage(final String packageName)
    {
        return 
            retrieveTestPackage(retrieveXMLDAOPackage(packageName));
    }

    /**
     * Retrieves the folder for XML DAO test templates.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @return the folder in which the associated test class should be
     * generated.
     */
    public File retrieveXMLDAOTestFolder(
        final File parentFolder, final String packageName)
    {
        return
            retrieveXMLDAOFolder(
                retrieveTestFolder(parentFolder),
                packageName);
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
     * @return the folder in which the associated factory class should be
     * generated.
     */
    public File retrieveXMLValueObjectFactoryFolder(
        final File parentFolder, final String packageName)
    {
        return retrieveXMLDAOFolder(parentFolder, packageName);
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
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves the package name for the JDBC operation classes.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @return the package for the associated pointers.
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     */
    public String retrieveJdbcOperationsPackage(
        final String packageName,
        final String engineName,
        final String tableName,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils)
    {
        return
            retrievePackage(
                retrieveDAOPackage(
                    packageName, engineName),
                stringUtils.capitalize(
                    tableName.toLowerCase(),
                    '_').toLowerCase());
    }

    /**
     * Retrieves the folder for the JDBC operation classes.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
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
        final String tableName)
    {
        return
            retrieveJdbcOperationsFolder(
                parentFolder,
                packageName,
                engineName,
                tableName,
                StringUtils.getInstance(),
                EnglishGrammarUtils.getInstance());
    }

    /**
     * Retrieves the folder for the JDBC operation classes.
     * @param parentFolder the parent folder.
     * @param packageName the original package.
     * @param engineName the engine.
     * @param tableName the table name.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param englishGrammarUtils the <code>EnglishGrammarUtils</code>
     * instance.
     * @return the folder for the associated pointers.
     * @precondition parentFolder != null
     * @precondition packageName != null
     * @precondition engineName != null
     * @precondition tableName != null
     * @precondition stringUtils != null
     * @precondition englishGrammarUtils != null
     */
    public File retrieveJdbcOperationsFolder(
        final File parentFolder,
        final String packageName,
        final String engineName,
        final String tableName,
        final StringUtils stringUtils,
        final EnglishGrammarUtils englishGrammarUtils)
    {
        return
            retrieveFolder(
                retrieveDAOFolder(
                    parentFolder, packageName, engineName),
                stringUtils.capitalize(
                    tableName.toLowerCase(),
                    '_').toLowerCase());
    }

    /**
     * Retrieves the package name for QueryPreparedStatementCreator class.
     * @param packageName the original package.
     * @return the package for such class.
     */
    public String retrieveQueryPreparedStatementCreatorPackage(final String packageName)
    {
        return retrieveRdbPackage(packageName);
    }

    /**
     * Retrieves the folder for QueryPreparedStatementCreator class.
     * @param parentFolder the parent folder.
     * @param packageName the package name.
     * @return the folder in which the associated rdb classes should be
     * generated.
     */
    public File retrieveQueryPreparedStatementCreatorFolder(
        final File parentFolder, final String packageName)
    {
        return retrieveRdbFolder(parentFolder, packageName);
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
        final String tableName)
    {
        return
            retrieveJdbcOperationsFolder(
                parentFolder, packageName, engineName, tableName);
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
        final String tableName)
    {
        return
            retrieveJdbcOperationsFolder(
                parentFolder, packageName, engineName, tableName);
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
        final String tableName)
    {
        return
            retrieveJdbcOperationsFolder(
                parentFolder, packageName, engineName, tableName);
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
        final String tableName)
    {
        return
            retrieveJdbcOperationsFolder(
                parentFolder, packageName, engineName, tableName);
    }
}
