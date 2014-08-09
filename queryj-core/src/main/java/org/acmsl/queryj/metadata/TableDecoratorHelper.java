/*
                        QueryJ Core

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
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.metadata.vo.Attribute;

/*
 * Importing ACM-SL Commons classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Provides some logic related to table decorators.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
@ThreadSafe
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
    public List<Attribute<DecoratedString>> removeOverridden(
        @NotNull final List<Attribute<DecoratedString>> firstAttributes,
        @NotNull final List<Attribute<DecoratedString>> secondAttributes,
        @Nullable final String parentTableName,
        @NotNull final MetadataManager metadataManager)
    {
        @NotNull final List<Attribute<DecoratedString>> result = new ArrayList<Attribute<DecoratedString>>();

        for  (final @Nullable Attribute<DecoratedString> t_Attribute : secondAttributes)
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
        @NotNull final Attribute<DecoratedString> attribute,
        @NotNull final MetadataManager metadataManager,
        @Nullable final String parentTableName)
    {
        final boolean result = false;


        // TODO
        /*
        String t_strName = attribute.getName();
        String t_strTableName = attribute.getTableName();
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
    public List<Attribute<DecoratedString>> removeOverriddenPlusPk(
        @NotNull final List<Attribute<DecoratedString>> firstAttributes,
        @NotNull final List<Attribute<DecoratedString>> secondAttributes,
        @NotNull final List<Attribute<DecoratedString>> primaryKey,
        @NotNull final String parentTableName,
        @NotNull final MetadataManager metadataManager)
    {
        final List<Attribute<DecoratedString>> result = new ArrayList<Attribute<DecoratedString>>();

        for (@Nullable final Attribute<DecoratedString> t_Attribute : secondAttributes)
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
        @NotNull final List<Attribute<DecoratedString>> attributes, @NotNull final Attribute<DecoratedString> attribute)
    {
        boolean result = false;

        for  (@Nullable final Attribute<DecoratedString> t_Attribute : attributes)
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
     * @param <V> the type.
     */
    @NotNull
    public <V> List<Attribute<DecoratedString>> sumUpParentAndChildAttributes(
        @NotNull final String parentTable,
        @NotNull final List<Attribute<V>> attributes,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        return
            sumUpParentAndChildAttributes(
                decoratorFactory.decorateAttributes(parentTable, metadataManager),
                decoratorFactory.decorateAttributes(attributes, metadataManager));
    }
    
    /**
     * Sums up parent and child's attributes.
     * @param attributes the attributes.
     * @param childAttributes the child attributes.
     * @return such collection.
     */
    @NotNull
    protected List<Attribute<DecoratedString>> sumUpParentAndChildAttributes(
        @NotNull final List<Attribute<DecoratedString>> attributes,
        @Nullable final List<Attribute<DecoratedString>> childAttributes)
    {
        final List<Attribute<DecoratedString>> result = new ArrayList<>();

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
    public List<Attribute<DecoratedString>> removeReadOnly(@NotNull final List<Attribute<DecoratedString>> attributes)
    {
        final List<Attribute<DecoratedString>> result = new ArrayList<Attribute<DecoratedString>>();

        for (@Nullable final Attribute<DecoratedString> t_Attribute : attributes)
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
    public List<Attribute<DecoratedString>> removeExternallyManaged(
        @NotNull final List<Attribute<DecoratedString>> attributes)
    {
        final List<Attribute<DecoratedString>> result = new ArrayList<Attribute<DecoratedString>>();

        for (@Nullable final Attribute<DecoratedString> t_Attribute : attributes)
        {
            if  (   (t_Attribute != null)
                 && (!t_Attribute.isExternallyManaged()))
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
    public List<Attribute<DecoratedString>> removeNonReadOnlyAttributes(
        @NotNull final List<Attribute<DecoratedString>> attributes)
    {
        final List<Attribute<DecoratedString>> result = new ArrayList<>();

        for (@Nullable final Attribute<DecoratedString> t_Attribute : attributes)
        {
            if  (   (t_Attribute != null)
                 && (t_Attribute.isReadOnly()))
            {
                result.add(t_Attribute);
            }
        }

        return result;
    }

    /**
     * Retrieves the ordered list of the fully-qualified types of given attributes.
     * @param attrs such attributes.
     * @param typeManager the {@link MetadataTypeManager} instance.
     * @return such list.
     */
    @NotNull
    protected List<DecoratedString> getAttributeTypes(
        @NotNull final List<Attribute<DecoratedString>> attrs,
        @NotNull final MetadataTypeManager typeManager)
    {
        @NotNull final List<DecoratedString> result = new ArrayList<>(attrs.size());

        for (@Nullable final Attribute<DecoratedString> attr: attrs)
        {
            if (attr != null)
            {
                @Nullable final String importType =
                    typeManager.getImport(
                        typeManager.getJavaType(attr.getType().getValue()));

                if (importType != null)
                {
                    @NotNull final DecoratedString value = new DecoratedString(importType);

                    if (!result.contains(value))
                    {
                        result.add(value);
                    }
                }
            }
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Checks whether any attribute is a clob.
     * @param attributes the {@link Attribute}s.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @return {@code true} in such case.
     */
    public boolean containClobs(
        @NotNull final List<Attribute<DecoratedString>> attributes,
        @NotNull final MetadataTypeManager metadataTypeManager)
    {
        boolean result = false;

        for (@Nullable final Attribute<DecoratedString> attribute : attributes)
        {
            if (   (attribute != null)
                && (metadataTypeManager.isClob(attribute.getTypeId())))
            {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Checks whether any attribute is nullable.
     * @param attributes the {@link Attribute}s.
     * @return {@code true} in such case.
     */
    public boolean containNullableAttributes(@NotNull final ListDecorator<Attribute<DecoratedString>> attributes)
    {
        boolean result = false;

        for (@Nullable final Attribute<DecoratedString> attribute : attributes)
        {
            if (   (attribute != null)
                && (attribute.isNullable()))
            {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Checks whether any attribute cannot be null.
     * @param attributes the {@link Attribute}s.
     * @param typeManager the {@link MetadataTypeManager} instance.
     * @return {@code true} in such case.
     */
    public boolean containNotNullAttributes(
        @NotNull final ListDecorator<Attribute<DecoratedString>> attributes,
        @NotNull final MetadataTypeManager typeManager)
    {
        boolean result = false;

        for (@Nullable final Attribute<DecoratedString> attribute : attributes)
        {
            if (   (attribute != null)
                && (!typeManager.isPrimitive(attribute.getTypeId()))
                && (!attribute.isNullable()))
            {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Retrieves the nullable attributes from given list.
     * @param attributes the attribute list.
     * @return the nullable subset.
     */
    @NotNull
    public List<Attribute<DecoratedString>> filterNullableAttributes(
        @NotNull final List<Attribute<DecoratedString>> attributes)
    {
        @NotNull final List<Attribute<DecoratedString>> result = new ArrayList<>(0);

        for (@Nullable final Attribute<DecoratedString> t_Attribute : attributes)
        {
            if (   (t_Attribute != null)
                   && (t_Attribute.isNullable()))
            {
                result.add(t_Attribute);
            }
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Checks whether given list is composed of {@link org.acmsl.queryj.metadata.vo.Attribute attributes} or not.
     * @param <T> the type of the items.
     * @param list such list.
     * @return {@code true} in such case.
     */
    protected <T> boolean isListOfAttributes(@NotNull final List<T> list)
    {
        final boolean result;

        if (list.size() > 0)
        {
            @Nullable final T item = list.get(0);

            if (item instanceof Attribute)
            {
                result = true;
            }
            else
            {
                result = false;
            }
        }
        else
        {
            result = true;
        }

        return result;
    }

    /**
     * Checks whether given list is composed of {@link Attribute attributes} or not.
     * @param <T> the type of the items.
     * @param list such list.
     * @return {@code true} in such case.
     */
    protected <T> boolean isListOfCustomResults(@NotNull final List<T> list)
    {
        final boolean result;

        if (list.size() > 0)
        {
            @Nullable final T item = list.get(0);

            if (item instanceof Result)
            {
                result = true;
            }
            else
            {
                result = false;
            }
        }
        else
        {
            result = true;
        }

        return result;
    }

}
