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

import java.util.Locale;

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
    public static final String JDBC_DRIVER = PREFIX + Literals.DRIVER;

    /**
     * The JDBC url.
     */
    public static final String JDBC_URL = PREFIX + "url";

    /**
     * The JDBC username.
     */
    public static final String JDBC_USERNAME = PREFIX + Literals.USERNAME;

    /**
     * The JDBC password.
     */
    public static final String JDBC_PASSWORD = PREFIX + Literals.PASSWORD;

    /**
     * The JDBC catalog.
     */
    public static final String JDBC_CATALOG = PREFIX + Literals.CATALOG;

    /**
     * The JDBC schema.
     */
    public static final String JDBC_SCHEMA = PREFIX + Literals.SCHEMA;

    /**
     * The repository.
     */
    public static final String REPOSITORY = PREFIX + Literals.REPOSITORY;

    /**
     * The output package of the generated classes.
     */
    public static final String PACKAGE_NAME = PREFIX + Literals.PACKAGE_NAME;

    /**
     * The output folder.
     */
    public static final String OUTPUT_DIR = PREFIX + Literals.OUTPUT_DIR;

    /**
     * The header file.
     */
    public static final String HEADER_FILE = PREFIX + Literals.HEADER_FILE;

    /**
     * The JNDI location of the DataSource.
     */
    public static final String JNDI_DATASOURCE = PREFIX + Literals.JNDI_DATASOURCE;

    /**
     * Whether to generate Mock DAO implementations.
     */
    @SuppressWarnings("unused")
    public static final String GENERATE_MOCK_DAO_IMPLEMENTATION =
        PREFIX + Literals.GENERATE_MOCK_IMPLEMENTATION;

    /**
     * Whether to generate Xml DAO implementations.
     */
    @SuppressWarnings("unused")
    public static final String GENERATE_XML_DAO_IMPLEMENTATION =
        PREFIX + Literals.GENERATE_XML_IMPLEMENTATION;

    /**
     * Whether to generate test cases.
     */
    @SuppressWarnings("unused")
    public static final String GENERATE_TESTS = PREFIX + Literals.GENERATE_TESTS;

    /**
     * Whether to allow empty repository DAO or not.
     */
    public static final String ALLOW_EMPTY_REPOSITORY_DAO = PREFIX + Literals.ALLOW_EMPTY_REPOSITORY_DAO;

    /**
     * Whether to implement ACM-SL Commons marker interfaces.
     */
    public static final String IMPLEMENT_MARKER_INTERFACES = PREFIX + Literals.IMPLEMENT_MARKER_INTERFACES;

    /**
     * The custom SQL XML file.
     */
    public static final String SQL_XML_FILE = PREFIX + Literals.SQL_XML_FILE;

    /**
     * The grammar folder.
     */
    public static final String GRAMMAR_FOLDER = PREFIX + Literals.GRAMMAR_FOLDER;

    /**
     * The grammar name.
     */
    public static final String GRAMMAR_NAME = PREFIX + Literals.GRAMMAR_NAME;

    /**
     * The grammar suffix.
     */
    public static final String GRAMMAR_SUFFIX = PREFIX + Literals.GRAMMAR_SUFFIX;

    /**
     * Whether to disable custom sql validation completely.
     */
    public static final String DISABLE_CUSTOM_SQL_VALIDATION = PREFIX + Literals.DISABLE_CUSTOM_SQL_VALIDATION;

    /**
     * The encoding.
     */
    public static final String ENCODING = PREFIX + Literals.ENCODING;

    /**
     * Whether to use template caching.
     */
    @SuppressWarnings("unused")
    public static final String DISABLE_CACHING = PREFIX + Literals.DISABLE_CACHING;

    /**
     * The thread count.
     */
    public static final String THREAD_COUNT = PREFIX + Literals.THREAD_COUNT;

    /**
     * Whether to use timestamps to indicate when the file was generated.
     */
    public static final String DISABLE_TIMESTAMPS = PREFIX + Literals.DISABLE_TIMESTAMPS;

    /**
     * Whether to use notnull annotations.
     */
    public static final String DISABLE_NOTNULL_ANNOTATIONS = PREFIX + Literals.DISABLE_NOTNULL_ANNOTATIONS;

    /**
     * Whether to use checkthread.org annotations.
     */
    public static final String DISABLE_CHECKTHREAD_ANNOTATIONS = PREFIX + Literals.DISABLE_CHECKTHREAD_ANNOTATIONS;

    /**
     * Whether to generate JMX support or not.
     */
    public static final String JMX = PREFIX + Literals.JMX;

    /**
     * Whether to enable caching.
     */
    public static final String CACHING = PREFIX + Literals.CACHING;

    /**
     * The version.
     */
    public static final String VERSION = PREFIX + Literals.VERSION;

    /**
     * The default locale.
     */
    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
}
