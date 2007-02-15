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
 * Filename: LazyTableDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Lazy-loaded table decorator.
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
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * A lazy-loaded <code>TableDecorator</code> implementation.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @version $Revision: 1702 $
 */
public class LazyTableDecorator
    extends  CachingTableDecorator
{
    /**
     * Creates a <code>LazyTableDecorator</code> instance.
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected LazyTableDecorator(
        final Table table,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        super(table, metadataManager);
        immutableSetDecoratorFactory(decoratorFactory);
    }
    
    /**
     * Creates a <code>LazyTableDecorator</code> instance.
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected LazyTableDecorator(
        final String table,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        super(table, null, null, metadataManager);
        immutableSetDecoratorFactory(decoratorFactory);
    }
    
    /**
     * Creates a <code>LazyTableDecorator</code> instance.
     * <code>Table</code> to decorate.
     * @param table the table.
     * @param metadataManager the metadata manager.
     * @param childAttributes the child attributes.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected LazyTableDecorator(
        final String table,
        final MetadataManager metadataManager,
        final List childAttributes,
        final DecoratorFactory decoratorFactory)
    {
        super(table, null, null, metadataManager);
        immutableSetChildAttributes(childAttributes);
        immutableSetDecoratorFactory(decoratorFactory);
    }
    
    /**
     * Creates a <code>LazyTableDecorator</code> instance.
     * @param table the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param childAttributes the child attributes.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    public LazyTableDecorator(
        final Table table,
        final MetadataManager metadataManager,
        final List childAttributes,
        final DecoratorFactory decoratorFactory)
    {
        super(table, metadataManager);
        immutableSetChildAttributes(childAttributes);
        immutableSetDecoratorFactory(decoratorFactory);
    }

    /**
     * Creates a <code>LazyTableDecorator</code> instance.
     * @param table the table name.
     * @param attributes the attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param childAttributes the child attributes.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition decoratorFactory != null
     */
    public LazyTableDecorator(
        final String table,
        final List attributes,
        final MetadataManager metadataManager,
        final List childAttributes,
        final DecoratorFactory decoratorFactory)
    {
        super(table, attributes, null, metadataManager);
        immutableSetChildAttributes(childAttributes);
        immutableSetDecoratorFactory(decoratorFactory);
    }

    /**
     * Creates a <code>LazyTableDecorator</code> with the following
     * information.
     * @param name the name.
     * @param attributes the attributes.
     * @param parentTable the parent table.
     * @param metadataManager the metadata manager.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition name != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected LazyTableDecorator(
        final String name,
        final List attributes,
        final Table parentTable,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        super(name, attributes, parentTable, metadataManager);

        immutableSetMetadataManager(metadataManager);
        immutableSetDecoratorFactory(decoratorFactory);
    }

    /**
     * Creates a table decorator.
     * @param name the table name.
     * @param attributes the attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such decorator.
     * @precondition name != null
     * @precondition attributes != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected TableDecorator createTableDecorator(
        final String parentTable,
        final List attributes,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        return
            new LazyTableDecorator(
                parentTable, 
                sumUpParentAndChildAttributes(
                    parentTable,
                    attributes,
                    metadataManager,
                    decoratorFactory,
                    TableDecoratorHelper.getInstance()),
                metadataManager,
                attributes,
                decoratorFactory);
    }
}
