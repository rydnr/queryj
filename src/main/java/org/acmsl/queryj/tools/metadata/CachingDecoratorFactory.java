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

        List t_lAttributes =
            decorateAttributes(
                table,
                metadataManager,
                metadataManager.getMetadataTypeManager());

        String t_strParentTable = metadataManager.getParentTable(table);

        Table t_ParentTable = null;

        if  (t_strParentTable != null)
        {
            t_ParentTable =
                createLazyDecorator(t_strParentTable, metadataManager);
        }

        result =
            new CachingTableDecorator(
                table,
                t_lAttributes,
                t_ParentTable,
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

        int t_iCount =
            (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_strColumnName = t_astrColumnNames[t_iIndex];

            t_iColumnType =
                metadataManager.getColumnType(table, t_strColumnName);

            result.add(
                createDecorator(
                    new AttributeValueObject(
                        t_strColumnName,
                        t_iColumnType,
                        metadataTypeManager.getNativeType(t_iColumnType),
                        metadataTypeManager.getFieldType(t_iColumnType),
                        table,
                        metadataManager.getColumnComment(
                            table, t_strColumnName),
                        metadataManager.isManagedExternally(
                            table, t_strColumnName),
                        metadataManager.allowsNull(
                            table, t_strColumnName),
                        null),
                    metadataManager));
        }

        return result;
    }

    /**
     * Creates a lazy decorator for given table.
     * @param table the table.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such decorator.
     * @precondition table != null
     * @precondition metadataManager != null
     */
    protected TableDecorator createLazyDecorator(
        final String table,
        final MetadataManager metadataManager)
    {
        return new LazyLoadingTableDecorator(table, metadataManager);
    }

    /**
     * A lazy-loaded <code>TableDecorator</code> implementation.
     * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
     * @version $Revision$
     */
    protected class LazyLoadingTableDecorator
        extends  CachingTableDecorator
    {
        /**
         * Creates a <code>LazyLoadingTableDecorator</code> instance.
         * @param table the table name.
         * @param metadataManager the <code>MetadataManager</code> instance.
         */
        public LazyLoadingTableDecorator(
            final String table, final MetadataManager metadataManager)
        {
            super(table, null, null, metadataManager);
        }

        /**
         * Retrieves the attributes.
         * @return such information.
         */
        public List getAttributes()
        {
            return getAttributes(getName(), getMetadataManager());
        }

        /**
         * Retrieves the attributes.
         * @param table the table name.
         * @param metadataManager the <code>MetadataManager</code> instance.
         * @return such information.
         * @precondition table != null
         * @precondition metadataManager != null
         */
        protected List getAttributes(
            final String table, final MetadataManager metadataManager)
        {
            List result = super.getAttributes();

            if  (   (result == null)
                 || (result.size() == 0))
            {
                super.setAttributes(
                    decorateAttributes(
                        table,
                        metadataManager,
                        metadataManager.getMetadataTypeManager()));

                result = super.getAttributes();
            }

            return result;
        }

        /**
         * Retrieves the parent table.
         * @return such information.
         */
        public Table getParentTable()
        {
            Table result = super.getParentTable();

            if  (result == null)
            {
                super.setParentTable(
                    createLazyDecorator(
                        getName(), 
                        getMetadataManager()));

                result = super.getParentTable();
            }

            return result;
        }
    }
}
