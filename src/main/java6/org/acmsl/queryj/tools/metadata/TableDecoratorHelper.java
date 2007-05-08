//;-*- mode: java -*-
/*
                        QueryJ

    Copyright (C) 2002-2007  Jose San Leandro Armendariz
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
 * Filename: TableDecoratorHelper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some logic related to table decorators.
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.vo.Attribute;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/**
 * Provides some logic related to table decorators.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class TableDecoratorHelper
    implements  Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class TableDecoratorHelperSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final TableDecoratorHelper SINGLETON =
            new TableDecoratorHelper();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected TableDecoratorHelper() {};

    /**
     * Retrieves a <code>TableDecoratorHelper</code> instance.
     * @return such instance.
     */
    public static TableDecoratorHelper getInstance()
    {
        return TableDecoratorHelperSingletonContainer.SINGLETON;
    }

    /**
     * Removes the duplicated attributes from <code>secondAttributes</code>.
     * @param firstAttributes the first attributes.
     * @param secondAttributes the second attributes.
     * @param parentTableName the parent table name of the second attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the cleaned-up attributes.
     * @precondition firstAttributes != null
     * @preconditoin secondAttributes != null
     * @precondition metadataManager != null
     */
    public List removeOverridden(
        final List firstAttributes,
        final List secondAttributes,
        final String parentTableName,
        final MetadataManager metadataManager)
    {
        List result = new ArrayList();

        int t_iCount = (secondAttributes != null) ? secondAttributes.size() : 0;

        Attribute t_Attribute;
        String t_strName;
        
        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_Attribute = (Attribute) secondAttributes.get(t_iIndex);

            if  (t_Attribute != null)
            {
                t_strName = t_Attribute.getName();
                
                if  (   (   (metadataManager.isPartOfPrimaryKey(
                                 t_Attribute.getTableName(), t_strName))
                         && (!metadataManager.isPartOfPrimaryKey(
                                 parentTableName, t_strName))
                         && (!metadataManager.pointsToPrimaryKey(
                                 t_Attribute.getTableName(),
                                 new String[] { t_strName },
                                 parentTableName)))
                     || (!contains(firstAttributes, t_Attribute)))
                {
                    result.add(t_Attribute);
                }
            }
        }

        return result;
    }

    /**
     * Checks whether given list contains a concrete attribute.
     * @param attributes the attributes.
     * @param attribute the attribute to check.
     * @return <code>true</code> in such case.
     * @precondition attributes != null
     * @precondition attribute != null
     */
    public boolean contains(final List attributes, final Attribute attribute)
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
     * @param parentTable the parent table.
     * @param attributes the attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @return such collection.
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     */
    public List sumUpParentAndChildAttributes(
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

    /**
     * Removes the read-only attributes from given list.
     * @param attributes the attributes.
     * @return the list without the read-only attributes.
     * @precondition attributes != null
     */
    public List removeReadOnly(final List attributes)
    {
        List result = new ArrayList();

        int t_iCount = (attributes != null) ? attributes.size() : 0;

        Attribute t_Attribute;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_Attribute = (Attribute) attributes.get(t_iIndex);

            if  (   (t_Attribute != null)
                 && (!t_Attribute.isReadOnly()))
            {
                result.add(t_Attribute);
            }
        }

        return result;
    }

    /**
     * Checks whether the table contains read-only attributes.
     * @param attributes the attributes.
     * @return such information.
     * @precondition attributes != null
     */
    public List removeNonReadOnlyAttributes(final List attributes)
    {
        List result = new ArrayList();

        int t_iCount = (attributes != null) ? attributes.size() : 0;

        Attribute t_Attribute;

        for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
        {
            t_Attribute = (Attribute) attributes.get(t_iIndex);

            if  (   (t_Attribute != null)
                 && (t_Attribute.isReadOnly()))
            {
                result.add(t_Attribute);
            }
        }

        return result;
    }
}
