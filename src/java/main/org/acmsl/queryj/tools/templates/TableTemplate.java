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
 * Description: Is able to generate tables according to database
 *              metadata.
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
package org.acmsl.queryj.tools.templates;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Is able to generate tables according to database metadata.
 * @author <a href="mailto:jsanleandro@yahoo.es"
 *         >Jose San Leandro</a>
 * @version $Revision$
 */
public class TableTemplate
    extends  AbstractTableTemplate
    implements  TableTemplateDefaults
{
    /**
     * Builds a <code>TableTemplate</code> using given information.
     * @param packageName the package name.
     * @param tableName the table name.
     */
    public TableTemplate(
        final String packageName, final String tableName)
    {
        super(
            DEFAULT_HEADER,
            DEFAULT_PACKAGE_DECLARATION,
            packageName,
            tableName,
            DEFAULT_ACMSL_IMPORTS,
            DEFAULT_JDK_IMPORTS,
            DEFAULT_JAVADOC,
            DEFAULT_CLASS_DEFINITION,
            DEFAULT_CLASS_START,
            DEFAULT_SINGLETON_BODY,
            DEFAULT_FIELD_JAVADOC,
            DEFAULT_FIELD_DEFINITION,
            DEFAULT_CLASS_CONSTRUCTOR,
            DEFAULT_GETTABLENAME_METHOD,
            DEFAULT_EMPTY_GETALL_METHOD,
            DEFAULT_GETALL_METHOD_START,
            DEFAULT_GETALL_METHOD_FIELD_SEPARATOR,
            DEFAULT_GETALL_METHOD_END,
            DEFAULT_CLASS_END);
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @return such source code.
     */
    public String toString()
    {
        return toString(TableUtils.getInstance(), StringUtils.getInstance());
    }

    /**
     * Retrieves the source code of the generated field tableName.
     * @param tableUtils the <code>TableUtils</code> instance.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @return such source code.
     * @precondition tableUtils != null
     * @precondition stringUtils != null
     */
    protected String toString(
        final TableUtils tableUtils, final StringUtils stringUtils)
    {
        StringBuffer t_sbResult = new StringBuffer();

        Object[] t_aTableName =
            new Object[]
            {
                stringUtils.capitalize(getTableName().toLowerCase(), '_')
            };

        Object[] t_aPackageName = new Object[]{getPackageName()};

        MessageFormat t_Formatter = new MessageFormat(getHeader());
        t_sbResult.append(t_Formatter.format(t_aTableName));

        t_Formatter = new MessageFormat(getPackageDeclaration());
        t_sbResult.append(t_Formatter.format(t_aPackageName));

        t_sbResult.append(getAcmslImports());
        t_sbResult.append(getJdkImports());

        t_Formatter = new MessageFormat(getJavadoc());
        t_sbResult.append(t_Formatter.format(t_aTableName));

        t_Formatter = new MessageFormat(getClassDefinition());
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    tableUtils.retrieveTableClassName(getTableName())
                }));

        t_sbResult.append(getClassStart());

        List t_lFields = getFields();

        if  (t_lFields != null)
        {
            Iterator t_itFields = t_lFields.iterator();

            MessageFormat t_JavadocFormatter =
                new MessageFormat(getFieldJavadoc());

            MessageFormat t_DefinitionFormatter =
                new MessageFormat(getFieldDefinition());

            while  (t_itFields.hasNext()) 
            {
                String t_strField = (String) t_itFields.next();

                t_sbResult.append(
                    t_JavadocFormatter.format(
                        new Object[]{
                            getTableName(),
                            t_strField}));

                t_sbResult.append(
                    t_DefinitionFormatter.format(
                        new Object[]{
                            getFieldType(t_strField),
                            t_strField.toUpperCase(),
                            t_strField}));
            }
        }

        t_Formatter = new MessageFormat(getSingletonBody());
        t_sbResult.append(
            t_Formatter.format(
                new Object[]
                {
                    stringUtils.capitalize(getTableName().toLowerCase(), '_'),
                    getTableName()
                }));

        t_Formatter = new MessageFormat(getGetTableNameMethod());
        t_sbResult.append(t_Formatter.format(new Object[]{getTableName()}));

        if  (t_lFields != null)
        {
            if  (t_lFields.size() == 0)
            {
                t_sbResult.append(getEmptyGetAllMethod());
            }
            else 
            {
                Iterator t_itFields = t_lFields.iterator();

                if  (t_itFields.hasNext()) 
                {
                    t_sbResult.append(getGetAllMethodStart());

                    t_sbResult.append(
                        ((String) t_itFields.next()).toUpperCase());
                }
                
                while  (t_itFields.hasNext()) 
                {
                    t_sbResult.append(getGetAllMethodFieldSeparator());
                    t_sbResult.append(
                        ((String) t_itFields.next()).toUpperCase());
                }

                t_sbResult.append(getGetAllMethodEnd());
            }
        }

        t_sbResult.append(getClassEnd());

        return t_sbResult.toString();
    }
}
