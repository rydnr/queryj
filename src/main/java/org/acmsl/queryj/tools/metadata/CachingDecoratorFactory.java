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
 * Filename: CachingDecoratorFactory.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Abstract factory for template-specific decorators.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Property;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.metadata.vo.AttributeValueObject;
import org.acmsl.queryj.tools.metadata.vo.Table;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract factory for template-specific decorators.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CachingDecoratorFactory
    implements  DecoratorFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class CachingDecoratorFactorySingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final CachingDecoratorFactory SINGLETON =
            new CachingDecoratorFactory();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected CachingDecoratorFactory() {};

    /**
     * Retrieves a <code>CachingDecoratorFactory</code> instance.
     * @return such instance.
     */
    public static CachingDecoratorFactory getInstance()
    {
        return CachingDecoratorFactorySingletonContainer.SINGLETON;
    }

    /**
     * Creates an <code>AttributeDecorator</code> for given
     * attribute instance.
     * @param attribute the attribute.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated attribute for the concrete template.
     */
    public AttributeDecorator createDecorator(
        final Attribute attribute, final MetadataManager metadataManager)
    {
        return new CachingAttributeDecorator(attribute, metadataManager);
    }

    /**
     * Creates a <code>PropertyDecorator</code> for given
     * property instance.
     * @param property the property.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated property for the concrete template.
     */
    public PropertyDecorator createDecorator(
        final Property property, final MetadataManager metadataManager)
    {
        return new CachingPropertyDecorator(property, metadataManager);
    }

    /**
     * Creates a <code>ResultDecorator</code> for given
     * result instance.
     * @param result the result.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated result for the concrete template.
     */
    public ResultDecorator createDecorator(
        final Result result,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
    {
        return
            new CachingResultDecorator(
                result, customSqlProvider, metadataManager, this);
    }

    /**
     * Creates a <code>SqlDecorator</code>.
     * @param sql the custom sql.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated sql for the concrete template.
     */
    public SqlDecorator createDecorator(
        final Sql sql,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager)
    {
        return
            new CachingSqlDecorator(
                sql, customSqlProvider, metadataManager);
    }

    /**
     * Creates a <code>TableDecorator</code>.
     * @param table the table.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated sql for the concrete template.
     */
    public TableDecorator createTableDecorator(
        final String table, final MetadataManager metadataManager)
    {
        TableDecorator result = null;

        Table t_ParentTable = null;

        List t_lAttributes =
            decorateAttributes(
                table,
                metadataManager,
                metadataManager.getMetadataTypeManager());

        String t_strParentTable = metadataManager.getParentTable(table);

        if  (t_strParentTable != null)
        {
            t_ParentTable =
                createLazyDecorator(
                    t_strParentTable, t_lAttributes, metadataManager);
        }

        result =
            new CachingTableDecorator(
                table,
                t_lAttributes,
                t_ParentTable,
                metadataManager.isTableDecorated(table),
                metadataManager);

        return result;
    }

    /**
     * Retrieves the decorated list of attributes of given table.
     * @param table the table.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return the attribute list
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    public List decorateAttributes(
        final String table, final MetadataManager metadataManager)
    {
        return
            decorateAttributes(
                table, metadataManager, metadataManager.getMetadataTypeManager());
    }

    /**
     * Retrieves the decorated list of attributes of given table.
     * @param table the table.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param metadataTypeManager the <code>MetadataTypeManager</code>
     * instance.
     * @return the attribute list
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition metadataTypeManager != null
     */
    protected List decorateAttributes(
        final String table,
        final MetadataManager metadataManager,
        final MetadataTypeManager metadataTypeManager)
    {
        List result = new ArrayList();

        String[] t_astrColumnNames =
            metadataManager.getColumnNames(table);

        String t_strColumnName;
        int t_iColumnType;
        boolean t_bAllowsNull;
        boolean t_bIsBool;
        String t_strBooleanTrue;
        String t_strBooleanFalse;
        String t_strBooleanNull;

        int t_iCount =
            (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_strColumnName = t_astrColumnNames[t_iIndex];

            t_iColumnType =
                metadataManager.getColumnType(table, t_strColumnName);

            t_bIsBool = metadataManager.isBoolean(table, t_strColumnName);

            t_bAllowsNull =
                metadataManager.allowsNull(table, t_strColumnName);

            t_strBooleanTrue =
                metadataManager.getBooleanTrue(
                    table, t_strColumnName);

            t_strBooleanFalse =
                metadataManager.getBooleanFalse(
                    table, t_strColumnName);

            t_strBooleanNull =
                metadataManager.getBooleanNull(
                    table, t_strColumnName);

            result.add(
                createDecorator(
                    new AttributeValueObject(
                        t_strColumnName,
                        t_iColumnType,
                        metadataTypeManager.getNativeType(
                            t_iColumnType, t_bAllowsNull, t_bIsBool),
                        metadataTypeManager.getFieldType(
                            t_iColumnType, t_bAllowsNull, t_bIsBool),
                        table,
                        metadataManager.getColumnComment(
                            table, t_strColumnName),
                        metadataManager.isManagedExternally(
                            table, t_strColumnName),
                        t_bAllowsNull,
                        null,
                        metadataManager.isReadOnly(
                            table, t_strColumnName),
                        t_bIsBool,
                        t_strBooleanTrue,
                        t_strBooleanFalse,
                        t_strBooleanNull),
                    metadataManager));
        }

        return result;
    }

    /**
     * Creates a lazy decorator for given table.
     * @param table the table.
     * @param childAttributes the child attributes (which take precedence
     * over parent's).
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such decorator.
     * @precondition table != null
     * @precondition childAttributes != null
     * @precondition metadataManager != null
     */
    protected TableDecorator createLazyDecorator(
        final String table,
        final List childAttributes,
        final MetadataManager metadataManager)
    {
        return
            new CachingTableDecorator(
                table,
                metadataManager.isTableDecorated(table),
                metadataManager,
                childAttributes,
                this);
    }
}
