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
package org.acmsl.queryj.metadata;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.metadata.vo.Attribute;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    protected TableDecoratorHelper() {}

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
     */
    @NotNull
    public List<Attribute> removeOverridden(
        @NotNull final List<Attribute> firstAttributes,
        @NotNull final List<Attribute> secondAttributes,
        @Nullable final String parentTableName,
        @NotNull final MetadataManager metadataManager)
    {
        List<Attribute> result = new ArrayList<Attribute>();

        for  (@Nullable Attribute t_Attribute : secondAttributes)
        {
            if  (t_Attribute != null)
            {
                if  (   (!isOverridden(t_Attribute, metadataManager, parentTableName))
                     || (!contains(firstAttributes, t_Attribute)))
                {
                    result.add(t_Attribute);
                }
            }
        }

        return result;
    }

    /**
     * Checks whether given attribute is considered overridden.
     * @param attribute the attribute.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param parentTableName the parent table name.
     * @return <tt>true</tt> in such case.
     */
    @SuppressWarnings("unused")
    protected boolean isOverridden(
        @NotNull final Attribute attribute,
        @NotNull final MetadataManager metadataManager,
        @Nullable final String parentTableName)
    {
        boolean result = false;

        String t_strName = attribute.getName();
        String t_strTableName = attribute.getTableName();

        // TODO
        /*
        result =
            (   (metadataManager.isPartOfPrimaryKey(
                     t_strTableName, t_strName))
             && (!metadataManager.isPartOfPrimaryKey(
                     parentTableName, t_strName))
             && (!metadataManager.pointsToPrimaryKey(
                     t_strTableName,
                     new String[] { t_strName },
                     parentTableName)));
          */

        return result;
    }

    /**
     * Removes the duplicated attributes from <code>secondAttributes</code>. plus the primary key.
     * @param firstAttributes the first attributes.
     * @param secondAttributes the second attributes.
     * @param primaryKey the primary key to prevail.
     * @param parentTableName the parent table name of the second attributes.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the cleaned-up attributes.
     */
    @NotNull
    public List<Attribute> removeOverriddenPlusPk(
        @NotNull final List<Attribute> firstAttributes,
        @NotNull final List<Attribute> secondAttributes,
        @NotNull final List<Attribute> primaryKey,
        @NotNull final String parentTableName,
        @NotNull final MetadataManager metadataManager)
    {
        List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : secondAttributes)
        {
            if  (t_Attribute != null)
            {
                if  (   (isOverridden(t_Attribute, metadataManager, parentTableName))
                     || (!contains(firstAttributes, t_Attribute))
                     || (contains(primaryKey, t_Attribute)))
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
     */
    public boolean contains(
        @NotNull final List<Attribute> attributes, @NotNull final Attribute attribute)
    {
        boolean result = false;

        for  (@Nullable Attribute t_Attribute : attributes)
        {
            if (    (t_Attribute != null)
                 && (attribute.getName().equals(t_Attribute.getName())))
            {
                result = true;
                break;
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
     */
    @NotNull
    public List<Attribute> sumUpParentAndChildAttributes(
        @NotNull final String parentTable,
        @NotNull final List<Attribute> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
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
    @NotNull
    protected List<Attribute> sumUpParentAndChildAttributes(
        @NotNull final List<Attribute> attributes, @Nullable final List<Attribute> childAttributes)
    {
        List<Attribute> result = new ArrayList<Attribute>();

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
     */
    @NotNull
    public List<Attribute> removeReadOnly(@NotNull final List<Attribute> attributes)
    {
        List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : attributes)
        {
            if  (   (t_Attribute != null)
                 && (!t_Attribute.isReadOnly()))
            {
                result.add(t_Attribute);
            }
        }

        return result;
    }

    /**
     * Removes the externally-managed attributes from given list.
     * @param attributes the attributes.
     * @return the list without the externally-managed attributes.
     */
    @NotNull
    public List<Attribute> removeExternallyManaged(@NotNull final List<Attribute> attributes)
    {
        List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : attributes)
        {
            if  (   (t_Attribute != null)
                 && (!t_Attribute.isManagedExternally()))
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
     */
    @NotNull
    public List<Attribute> removeNonReadOnlyAttributes(@NotNull final List<Attribute> attributes)
    {
        List<Attribute> result = new ArrayList<Attribute>();

        for (@Nullable Attribute t_Attribute : attributes)
        {
            if  (   (t_Attribute != null)
                 && (t_Attribute.isReadOnly()))
            {
                result.add(t_Attribute);
            }
        }

        return result;
    }
}
