//;-*- mode: java -*-
/*
                        QueryJ

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
 * Author: Jose San Leandro Armendariz
 *
 * Description: Defines QueryJ settings.
 *
 */
package org.acmsl.queryj;

/**
 * Defines QueryJ settings.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public interface QueryJSettings
{
    /**
     * The common prefix.
     */
    public static final String PREFIX = "queryj.";

    /**
     * The JDBC driver.
     */
    public static final String JDBC_DRIVER = PREFIX + "driver";

    /**
     * The JDBC url.
     */
    public static final String JDBC_URL = PREFIX + "url";

    /**
     * The JDBC username.
     */
    public static final String JDBC_USERNAME = PREFIX + "username";

    /**
     * The JDBC password.
     */
    public static final String JDBC_PASSWORD = PREFIX + "password";

    /**
     * The JDBC catalog.
     */
    public static final String JDBC_CATALOG = PREFIX + "catalog";

    /**
     * The JDBC schema.
     */
    public static final String JDBC_SCHEMA = PREFIX + "schema";

    /**
     * The repository.
     */
    public static final String REPOSITORY = PREFIX + "repository";

    /**
     * The output package of the generated classes.
     */
    public static final String PACKAGE = PREFIX + "package";

    /**
     * The output folder.
     */
    public static final String OUTPUT_FOLDER = PREFIX + "outputdir";

    /**
     * The header file.
     */
    public static final String HEADER_FILE = PREFIX + "header";

    /**
     * Whether to generate main/ and test/ subfolders or not.
     */
    public static final String OUTPUT_DIR_SUBFOLDERS =
        PREFIX + "outputdirsubfolders";

    /**
     * Whether to extract the tables or not.
     */
    public static final String EXTRACT_TABLES = PREFIX + "extractTables";

    /**
     * Whether to extract the procedures or not.
     */
    public static final String EXTRACT_PROCEDURES =
        PREFIX + "extractProcedures";

    /**
     * Whether to extract the functions or not.
     */
    public static final String EXTRACT_FUNCTIONS = PREFIX + "extractFunctions";

    /**
     * The JNDI location of the DataSource.
     */
    public static final String JNDI_DATASOURCE = PREFIX + "jndiDataSource";

    /**
     * Whether to generate Mock DAO implementations.
     */
    public static final String GENERATE_MOCK_DAO_IMPLEMENTATION =
        PREFIX + "generateMockImplementation";

    /**
     * Whether to generate Xml DAO implementations.
     */
    public static final String GENERATE_XML_DAO_IMPLEMENTATION =
        PREFIX + "generateXmlImplementation";

    /**
     * Whether to generate test cases.
     */
    public static final String GENERATE_TESTS =
        PREFIX + "generateTests";

    /**
     * Whether to allow empty repository DAO or not.
     */
    public static final String ALLOW_EMPTY_REPOSITORY_DAO =
        PREFIX + "allowEmptyRepositoryDAO";

    /**
     * Whether to implement ACM-SL Commons marker interfaces.
     */
    public static final String IMPLEMENT_MARKER_INTERFACES =
        PREFIX + "implementMarkerInterfaces";

    /**
     * The model of the custom SQL.
     */
    public static final String CUSTOM_SQL_MODEL = PREFIX + "customSqlModel";

    /**
     * The custom SQL XML file.
     */
    public static final String SQL_XML_FILE = PREFIX + "sqlXml";

    /**
     * The grammar folder.
     */
    public static final String GRAMMAR_FOLDER = PREFIX + "grammarFolder";

    /**
     * The grammar name.
     */
    public static final String GRAMMAR_NAME = PREFIX + "grammarName";

    /**
     * The grammar suffix.
     */
    public static final String GRAMMAR_SUFFIX = PREFIX + "grammarSuffix";

    /**
     * Whether to disable custom sql validation completely.
     */
    public static final String DISABLE_CUSTOM_SQL_VALIDATION =
        PREFIX + "disableCustomSqlValidation";

    /**
     * The encoding.
     */
    public static final String ENCODING = PREFIX + "encoding";

    /**
     * Whether to use template caching.
     */
    public static final String DISABLE_CACHING = PREFIX + "disableCaching";

    /**
     * The thread count.
     */
    public static final String THREAD_COUNT = PREFIX + "threadCount";

    /**
     * Whether to use timestamps to indicate when the file was generated.
     */
    public static final String DISABLE_TIMESTAMPS = PREFIX + "timestamps";

    /**
     * Whether to use notnull annotations.
     */
    public static final String DISABLE_NOTNULL_ANNOTATIONS = PREFIX + "disableNotNullAnnotations";

    /**
     * Whether to use checkthread.org annotations.
     */
    public static final String DISABLE_CHECKTHREAD_ANNOTATIONS = PREFIX + "disableCheckthreadAnnotations";
}
