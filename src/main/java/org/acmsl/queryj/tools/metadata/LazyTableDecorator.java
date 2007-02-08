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
     * The decorator factory.
     */
    private DecoratorFactory m__DecoratorFactory;
    
    /**
     * The child's attributes.
     */
    private List m__lChildAttributes;

    /**
     * A flag indicating whether the attributes have been cleaned up.
     */
    private boolean m__bAttributesCleanedUp = false;
    
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
     * Specifies the decorator factory.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     */
    protected final void immutableSetDecoratorFactory(
        final DecoratorFactory decoratorFactory)
    {
        m__DecoratorFactory = decoratorFactory;
    }
    
    /**
     * Specifies the decorator factory.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     */
    protected void setDecoratorFactory(final DecoratorFactory decoratorFactory)
    {
        immutableSetDecoratorFactory(decoratorFactory);
    }
    
    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    public DecoratorFactory getDecoratorFactory()
    {
        return m__DecoratorFactory;
    }
    
    /**
     * Specifies the child attributes.
     * @param childAttributes the child attributes.
     */
    protected final void immutableSetChildAttributes(final List childAttributes)
    {
        m__lChildAttributes = childAttributes;
    }

    /**
     * Specifies the child attributes.
     * @param childAttributes the child attributes.
     */
    protected void setChildAttributes(final List childAttributes)
    {
        immutableSetChildAttributes(childAttributes);
    }

    /**
     * Retrieves the child's attributes.
     * @return such list.
     */
    public List getChildAttributes()
    {
        return m__lChildAttributes;
    }

    /**
     * Specifies whether the attributes have been cleaned up.
     * @param flag such flag.
     */
    protected final void immutableSetAttributesCleanedUp(final boolean flag)
    {
        m__bAttributesCleanedUp = flag;
    }
    
    /**
     * Specifies whether the attributes have been cleaned up.
     * @param flag such flag.
     */
    protected void setAttributesCleanedUp(final boolean flag)
    {
        immutableSetAttributesCleanedUp(flag);
    }
    
    /**
     * Retrieves whether the attributes have been cleaned up.
     * @return such flag.
     */
    protected boolean getAttributesCleanedUp()
    {
        return m__bAttributesCleanedUp;
    }
    
    /**
     * Retrieves the attributes.
     * @return such information.
     */
    public List getAllAttributes()
    {
        return
            getAllAttributes(
                getName(), getMetadataManager(), getDecoratorFactory());
    }

    /**
     * Retrieves the attributes.
     * @param table the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such information.
     * @precondition table != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected List getAllAttributes(
        final String table,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        List result = new ArrayList();

        Table t_ParentTable = getParentTable();

        if  (t_ParentTable != null)
        {
            LazyTableDecorator t_ParentDecorator =
                new LazyTableDecorator(
                    t_ParentTable, metadataManager, decoratorFactory);
            
            result.addAll(t_ParentDecorator.getAllAttributes());
        }
        
        result.addAll(
            decorateAttributes(
                table,
                metadataManager,
                decoratorFactory));

        return result;
    }

    /**
     * Decorates the attributes.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated attributes.
     * @precondition decoratorFactory != null
     */
    protected List decorateAttributes()
    {
        return
            decorateAttributes(
                getName(), getMetadataManager(), getDecoratorFactory());
    }
    
    /**
     * Decorates the attributes.
     * @param name the table name.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return the decorated attributes.
     * @precondition name != null
     * @precondition decoratorFactory != null
     * @precondition metadataManager != null
     */
    protected List decorateAttributes(
        final String name,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        return decoratorFactory.decorateAttributes(name, metadataManager);
    }
    
    /**
     * Retrieves the attributes.
     * @return such information.
     */
    public List getAttributes()
    {
        return
            getAttributes(
                getName(),
                getChildAttributes(),
                getMetadataManager(),
                getDecoratorFactory(),
                getAttributesCleanedUp());
    }

    /**
     * Retrieves the attributes.
     * @param table the table name.
     * @param childAttributes the child's attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param attributesCleanedUp whether the child attributes have been removed
     * from the attribute list already.
     * @return such information.
     * @precondition table != null
     * @precondition childAttributes != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected List getAttributes(
        final String table,
        final List childAttributes,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory,
        final boolean attributesCleanedUp)
    {
        List result = super.getAttributes();

        if  (   (result == null)
             || (result.size() == 0))
        {
            result =
                decorateAttributes(
                    table, metadataManager, decoratorFactory);
        }
        
        if  (   (result != null)
             && (!attributesCleanedUp))
        {
            result = removeOverridden(childAttributes, result);
            
            super.setAttributes(result);
            setAttributesCleanedUp(true);
        }

        return result;
    }

    /**
     * Retrieves the parent table.
     * @return such information.
     */
    public Table getParentTable()
    {
        return getParentTable(getMetadataManager(), getDecoratorFactory());
    }
    
    /**
     * Retrieves the parent table.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such information.
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected Table getParentTable(
        final MetadataManager metadataManager, 
        final DecoratorFactory decoratorFactory)
    {
        Table result = super.getParentTable();

        if  (result == null)
        {
            String t_strParentTable =
                metadataManager.getParentTable(getName());
            
            if  (t_strParentTable != null)
            {
                List t_lAttributes = getAttributes();
                
                super.setParentTable(
                    new LazyTableDecorator(
                        t_strParentTable, 
                        sumUpParentAndChildAttributes(
                            t_strParentTable,
                            t_lAttributes,
                            metadataManager,
                            decoratorFactory),
                        metadataManager,
                        t_lAttributes,
                        decoratorFactory));

                result = super.getParentTable();
            }
        }

        return result;
    }

    /**
     * Removes the duplicated attributes.
     * @param childAttributes the child attributes.
     * @param parentAttributes the parent attributes.
     * @return the cleaned-up attributes.
     * @precondition childAttributes != null
     * @preconditoin parentAttributes != null
     */
    protected List removeOverridden(
        final List childAttributes, final List parentAttributes)
    {
        List result = new ArrayList();

        int t_iCount = (parentAttributes != null) ? parentAttributes.size() : 0;

        Attribute t_ParentAttribute;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_ParentAttribute = (Attribute) parentAttributes.get(t_iIndex);

            if  (    (t_ParentAttribute != null)
                     && (!contains(childAttributes, t_ParentAttribute)))
            {
                result.add(parentAttributes.get(t_iIndex));
            }
        }

        return result;
    }

    /**
     * Checks whether given list contains a concrete attribute.
     * @param attributes the attributes.
     * @param attribute the attribute to check.
     * @return <code>true</code> in such case.
     */
    protected boolean contains(final List attributes, final Attribute attribute)
    {
        boolean result = false;

        int t_iCount = (attributes != null) ? attributes.size() : 0;

        String t_strName = (attribute != null) ? attribute.getName() : null;

        if  (t_strName != null)
        {
            Attribute t_CurrentAttribute;

            for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
            {
                t_CurrentAttribute = (Attribute) attributes.get(t_iIndex);

                if  (   (t_CurrentAttribute != null)
                        && (t_strName.equals(t_CurrentAttribute.getName())))
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Sums up parent and child's attributes.
     * @return such collection.
     */
    protected List sumUpParentAndChildAttributes()
    {
        return sumUpParentAndChildAttributes(getAttributes(), getChildAttributes());
    }

    /**
     * Sums up parent and child's attributes.
     * @param parentTable the parent table.
     * @param attributes the attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such collection.
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    protected List sumUpParentAndChildAttributes(
        final String parentTable,
        final List attributes,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        return
            sumUpParentAndChildAttributes(
                decoratorFactory.decorateAttributes(parentTable, metadataManager),
                attributes);
    }
    
    /**
     * Sums up parent and child's attributes.
     * @param attributes the attributes.
     * @param childAttributes the child attributes.
     * @return such collection.
     */
    protected List sumUpParentAndChildAttributes(
        final List attributes, final List childAttributes)
    {
        List result = new ArrayList();

        result.addAll(attributes);

        if  (childAttributes != null)
        {
            result.removeAll(childAttributes);

            result.addAll(childAttributes);
        }
        
        return result;
    }
}
