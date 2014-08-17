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
 * Filename: Literals.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Literals all over QueryJ Core.
 *
 * Date: 2013/11/21
 * Time: 19:41
 *
 */
package org.acmsl.queryj;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Literals all over QueryJ Core.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/11/21 19:41
 */
@ThreadSafe
public interface Literals
{
    String QUERYJ_L = "queryj";
    String DRIVER = "driver";
    String URL = "url";
    String USERNAME = "username";
    String PASSWORD = "password";
    String OUTPUT_DIR = "outputDir";
    String JNDI_DATASOURCE = "jndiDataSource";
    String GENERATE_MOCK_IMPLEMENTATION = "generateMockImplementation";
    String GENERATE_XML_IMPLEMENTATION = "generateXmlImplementation";
    String GENERATE_TESTS = "generateTests";
    String ALLOW_EMPTY_REPOSITORY_DAO = "allowEmptyRepositoryDAO";
    String IMPLEMENT_MARKER_INTERFACES = "implementMarkerInterfaces";
    String SQL_XML_FILE = "sqlXmlFile";
    String GRAMMAR_FOLDER = "grammarFolder";
    String GRAMMAR_NAME = "grammarName";
    String GRAMMAR_SUFFIX = "grammarSuffix";
    String HEADER_FILE = "headerFile";
    String DISABLE_CUSTOM_SQL_VALIDATION = "disableCustomSqlValidation";
    String ENCODING = "encoding";
    String DISABLE_CACHING = "disableCaching";
    String THREAD_COUNT = "threadCount";
    String DISABLE_TIMESTAMPS = "disableTimestamps";
    String DISABLE_NOTNULL_ANNOTATIONS = "disableNotNullAnnotations";
    String DISABLE_CHECKTHREAD_ANNOTATIONS = "disableCheckthreadAnnotations";
    String JMX = "jmx";
    String CACHING = "caching";
    String VERSION = "version";
    String JSON_PARENT_ATTR = " 'parent': ";
    String TEMPLATE = "Template";
    String GENERATING = "Generating ";
    String CONTEXT = "Context";
    String FILL_TEMPLATE_CHAIN_FACTORY = "FillTemplateChainFactory";
    String DEFAULT_PLACEHOLDER_PACKAGE = "org.acmsl.queryj.api.placeholders";
    String SOURCE = "source";
    String INVALID_TABLE_COMMENT = "Invalid table comment: ";
    String INVALID_COLUMN_COMMENT = "Invalid column comment: ";
    String REFERENCED_RESULT_NOT_FOUND = "Referenced result not found: ";
    String RESULT_SUFFIX = ".result";
    String TABLE_NOT_FOUND = "Table not found: ";
    String REPOSITORY = "repository";
    String PACKAGE = "package";
    String HEADER = "header";
    String CATALOG = "catalog";
    String SCHEMA = "schema";
    String TIMESTAMP = "Timestamp";
    String CUSTOM_SQL = "custom-sql";
    String DECIMAL = "DECIMAL";
    String BIG_DECIMAL = "BigDecimal";
    String TINYINT_U = "TINYINT";
    String INTEGER = "Integer";
    String INTEGER_U = "INTEGER";
    String NUMERIC_U = "NUMERIC";
    String FLOAT_U = "FLOAT";
    String DOUBLE_U = "DOUBLE";
    String TIMESTAMP_U = "TIMESTAMP";
    String VARCHAR_U = "VARCHAR";
    String LONGVARCHAR_U = "LONGVARCHAR";
    String VARBINARY_U = "VARBINARY";
    String STRING = "String";
    String DOUBLE_C = "Double";
    String FALSE_L = org.acmsl.commons.Literals.FALSE;
    String FLOAT_C = "Float";
    String CANNOT_COMPARE = "Cannot compare ";
    String EXTRACTED = " extracted";
    String TABLE_NAME = "tableName";
    String TABLE_NAME_U = "TABLE_NAME";
    String COLUMN_NAME_U = "COLUMN_NAME";
    String POSITION_U = "POSITION";
    String USER_CONSTRAINTS_U = "USER_CONSTRAINTS";
    String CONSTRAINT_NAME_U = "CONSTRAINT_NAME";
    String BIGINT_U = "BIGINT";
    String BINARY_U = "BINARY";
    String LONGVARBINARY_U = "LONGVARBINARY";
    String SMALLINT_U = "SMALLINT";
    String NUMBER_U = "NUMBER";
    String COMMENT = "comment";
    String LENGTH = "length";
    String INDEX = "index";
    String QUERY_J = "QueryJ";
    String CANNOT_READ_THE_CONFIGURATION_PROPERTIES_FILE = "Cannot read the configuration properties file.";
    String ORACLE = "Oracle";
    String NULLABLE = "NULLABLE";
    String DATA_TYPE1 = "DATA_TYPE";
    String COLUMN_ID1 = "COLUMN_ID";
    String ORG_ACMSL_QUERYJ_TEMPLATES = "org/acmsl/queryj/templates";
    String PACKAGE_NAME = "packageName";
    String ENGINE = "engine";
    String UNKNOWN = "unknown";
    String BOOLEAN = "boolean";
    String FLOAT = "float";
    String DOUBLE = "double";
    String SMALLINT = "smallint";
    String TINYINT_L = "tinyint";
    String SMALLINT_L = "smallint";
    String VARCHAR2 = "VARCHAR2";
    String SHORT_L = "short";
    String NUMBER_L = "number";
    String OBJECT_C = "Object";
    String RESULT_NAME = "resultName";
    String TEMPLATE_NAME = "templateName";
    String FILE_NAME = "fileName";
    String RESULT = "result";
    String FOREIGN_KEY = "foreignKey";
    String NOTHING = "nothing";
    String UNKNOWN_REMARK = "(unknown)";
}
