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
 * Filename: $RCSfile: $
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

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;

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
        return new CachingTableDecorator(table, metadataManager);
    }


    /**
     * Creates a <code>ForeignKeyDecorator</code>.
     * @param sourceTableName the name of the source table.
     * @param attributes the foreign key attributes.
     * @param targetTableName the name of the target table.
     * @param allowsNull whether the fk can be null as a whole.
     * @return the decorator instance.
     */
    public ForeignKeyDecorator createDecorator(
        final String sourceTableName,
        final Collection attributes,
        final String targetTableName,
        final boolean allowsNull)
    {
        return
            new CachingForeignKeyDecorator(
                sourceTableName,
                attributes,
                targetTableName,
                allowsNull);
    }
}
