//;-*- mode: java -*-
/*
                        QueryJ

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
 * Filename: AbstractResultDecorator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Decorates <result> elements in custom-sql models.
 *
 */
package org.acmsl.queryj.tools.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.CustomResultUtils;
import org.acmsl.queryj.tools.customsql.CustomSqlProvider;
import org.acmsl.queryj.tools.customsql.Property;
import org.acmsl.queryj.tools.customsql.PropertyElement;
import org.acmsl.queryj.tools.customsql.PropertyRefElement;
import org.acmsl.queryj.tools.customsql.Result;
import org.acmsl.queryj.tools.customsql.ResultElement;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/*
 * Importing Apache Commons Logging classes.
 */
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Decorates &lt;result&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractResultDecorator
    extends  ResultElement
    implements  ResultDecorator
{
    /**
     * The result element.
     */
    private Result m__Result;

    /**
     * The custom sql provider.
     * @todo remove this.
     */
    private CustomSqlProvider m__CustomSqlProvider;

    /**
     * The metadata manager.
     */
    private MetadataManager m__MetadataManager;

    /**
     * The decorator factory.
     */
    private DecoratorFactory m__DecoratorFactory;

    /**
     * Creates a <code>ResultElementDecorator</code> with given instance.
     * @param result the result element.
     * @param customSqlProvider the <code>CustomSqlProvider</code>, required
     * to decorate referred parameters.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @precondition result != null
     * @precondition customSqlProvider != null
     * @precondition decoratorFactory != null
     */
    public AbstractResultDecorator(
        @NotNull final Result result,
        final CustomSqlProvider customSqlProvider,
        final MetadataManager metadataManager,
        final DecoratorFactory decoratorFactory)
    {
        super(
            result.getId(),
            result.getClassValue(),
            result.getMatches());
        immutableSetResult(result);
        immutableSetPropertyRefs(result.getPropertyRefs());
        immutableSetCustomSqlProvider(customSqlProvider);
        immutableSetMetadataManager(metadataManager);
        immutableSetDecoratorFactory(decoratorFactory);
    }

    /**
     * Specifies the result.
     * @param result the result.
     */
    protected final void immutableSetResult(final Result result)
    {
        m__Result = result;
    }

    /**
     * Specifies the result.
     * @param result the result.
     */
    protected void setResult(final Result result)
    {
        immutableSetResult(result);
    }

    /**
     * Retrieves the result.
     * @return such element.
     */
    public Result getResult()
    {
        return m__Result;
    }

    /**
     * Specifies the custom SQL provider.
     * @param customSqlProvider such provider.
     */
    protected final void immutableSetCustomSqlProvider(
        final CustomSqlProvider customSqlProvider)
    {
        m__CustomSqlProvider = customSqlProvider;
    }

    /**
     * Specifies the custom SQL provider.
     * @param customSqlProvider such provider.
     */
    protected void setCustomSqlProvider(
        final CustomSqlProvider customSqlProvider)
    {
        immutableSetCustomSqlProvider(customSqlProvider);
    }

    /**
     * Retrieves the custom SQL provider.
     * @return such provider.
     */
    protected CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected final void immutableSetMetadataManager(
        final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected void setMetadataManager(
        final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such instance.
     */
    public MetadataManager getMetadataManager()
    {
        return m__MetadataManager;
    }

    /**
     * Retrieves the metadata type manager.
     * @return such manager.
     */
    protected MetadataTypeManager getMetadataTypeManager()
    {
        return getMetadataTypeManager(getMetadataManager());
    }

    /**
     * Retrieves the metadata type manager.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return such manager.
     * @precondition metadataManager != null
     */
    protected MetadataTypeManager getMetadataTypeManager(
        @NotNull final MetadataManager metadataManager)
    {
        return metadataManager.getMetadataTypeManager();
    }

    /**
     * Specifies the decorator factory.
     * @param factory such instance.
     */
    protected final void immutableSetDecoratorFactory(
        final DecoratorFactory factory)
    {
        m__DecoratorFactory = factory;
    }

    /**
     * Specifies the decorator factory.
     * @param factory such instance.
     */
    protected void setDecoratorFactory(
        final DecoratorFactory factory)
    {
        immutableSetDecoratorFactory(factory);
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
     * Retrieves the id, normalized.
     * @return such information.
     */
    public String getIdNormalized()
    {
        return normalize(getId(), DecorationUtils.getInstance());
    }

    /**
     * Retrieves the id capitalized.
     * @return such information.
     */
    public String getIdCapitalized()
    {
        return capitalize(getId(), DecorationUtils.getInstance());
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the value, after being processed.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String capitalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(value);
    }

    /**
     * Normalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such information.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalize(
        final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalize(value);
    }

    /**
     * Retrieves the id, normalized and upper-cased.
     * @return such information.
     */
    public String getIdNormalizedUppercased()
    {
        return normalizeUppercase(getId(), DecorationUtils.getInstance());
    }

    /**
     * Normalizes given value, in upper-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such information.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalizeUppercase(
        final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalizeUppercase(value);
    }

    /**
     * Retrieves whether the result matches a single entity or expects
     * a set of them.
     * @return such information.
     */
    public boolean isMultiple()
    {
        return isMultiple(getResult());
    }

    /**
     * Retrieves whether the result matches a single entity or expects
     * a set of them.
     * @param result the result element.
     * @return such information.
     * @precondition result != null
     */
    protected boolean isMultiple(@NotNull final Result result)
    {
        return Result.MULTIPLE.equalsIgnoreCase(result.getMatches());
    }

    /**
     * Retrieves the properties.
     * @return such information.
     */
    public Collection getProperties()
    {
        return
            getProperties(
                getPropertyRefs(),
                getResult(),
                getCustomSqlProvider(),
                getMetadataManager(),
                getDecoratorFactory(),
                CustomResultUtils.getInstance());
    }

    /**
     * Retrieves the properties.
     * @todo fix reference to customSqlProvider.
     * @param propertyRefs the property references.
     * @param resultElement the result element.
     * @param customSqlProvider the <code>CustomSqlProvider</code>.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @return such information.
     * @precondition customSqlProvider != null
     * @precondition metadataManager != null
     * @precondition decoratorFactory != null
     * @precondition customResultUtils != null
     */
    @NotNull
    public Collection getProperties(
        @Nullable final Collection propertyRefs,
        @NotNull final Result resultElement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomResultUtils customResultUtils)
    {
        @NotNull Collection result = new ArrayList();

        @Nullable Iterator t_PropertyRefIterator =
            (propertyRefs != null) ? propertyRefs.iterator() : null;

        if  (t_PropertyRefIterator != null)
        {
            @Nullable PropertyRefElement t_PropertyRef = null;
            @Nullable Property t_Property = null;

            while  (t_PropertyRefIterator.hasNext())
            {
                t_PropertyRef =
                    (PropertyRefElement) t_PropertyRefIterator.next();

                if  (t_PropertyRef != null)
                {
                    t_Property =
                            customSqlProvider.resolveReference(t_PropertyRef);

                    if  (t_Property != null)
                    {
                        result.add(
                            decoratorFactory.createDecorator(
                                t_Property, resultElement, customSqlProvider, metadataManager));
                    }
                    else
                    {
                        try
                        {
                            // todo throw something.
                            LogFactory.getLog(ResultDecorator.class).warn(
                                "Referenced property not found:"
                                + t_PropertyRef.getId());
                        }
                        catch  (@NotNull final Throwable throwable)
                        {
                            // class-loading problem.
                        }
                    }
                }
            }
        }
        else
        {
            @Nullable String t_strTable =
                customResultUtils.retrieveTable(
                    resultElement,
                    customSqlProvider,
                    metadataManager);

            MetadataTypeManager t_MetadataTypeManager =
                metadataManager.getMetadataTypeManager();
            
            if  (   (t_strTable != null)
                 && (t_MetadataTypeManager != null))
            {
                @NotNull String[] t_astrColumnNames =
                    metadataManager.getColumnNames(t_strTable);

                int t_iCount =
                    (t_astrColumnNames != null) ? t_astrColumnNames.length : 0;

                String t_strColumnName;
                boolean t_bAllowsNull;
                for  (int t_iIndex = 0; t_iIndex < t_iCount; t_iIndex++)
                {
                    t_strColumnName = t_astrColumnNames[t_iIndex];

                    t_bAllowsNull = 
                        metadataManager.allowsNull(
                            t_strTable, t_strColumnName);

                    result.add(
                        decoratorFactory.createDecorator(
                            new PropertyElement(
                                t_strTable + "." + t_strColumnName,
                                t_strColumnName,
                                t_iIndex + 1,
                                t_strColumnName,
                                t_MetadataTypeManager.getNativeType(
                                    metadataManager.getColumnType(
                                        t_strTable, t_strColumnName),
                                    t_bAllowsNull),
                                t_bAllowsNull),
                            metadataManager));
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the large-object-block properties.
     * @return such collection.
     */
    public Collection getLobProperties()
    {
        return
            filterLobProperties(
                getProperties(),
                getMetadataTypeManager(),
                MetadataUtils.getInstance());
    }

    /**
     * Retrieves the large-object-block properties.
     * @param properties the properties.
     * @param metadataTypeManager the <code>MetadataTypeManager</code> instance.
     * @param metadataUtils the <code>MetadataUtils</code> instance.
     * @return such collection.
     * @precondition properties != null
     * @precondition metadataTypeManager != null
     * @precondition metadataUtils != null
     */
    @NotNull
    protected Collection filterLobProperties(
        final Collection properties,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final MetadataUtils metadataUtils)
    {
        return
            metadataUtils.filterLobProperties(
                properties, metadataTypeManager);
    }

    /**
     * Provides a text representation of the information
     * contained in this instance.
     * @return such information.
     */
    @NotNull
    public String toString()
    {
        return toString(getResult());
    }

    /**
     * Provides a text representation of the information
     * contained in given instance.
     * @param result the decorated result.
     * @return such information.
     */
    @NotNull
    protected String toString(final Result result)
    {
        return "" + result;
    }

    /**
     * Retrieves the hashcode.
     * @return such value.
     */
    public final int hashCode()
    {
        return hashCode(getResult());
    }

    /**
     * Retrieves the hashcode of given instance.
     * @param result the decoreated result.
     * @return such value.
     * @precondition result != null
     */
    protected final int hashCode(@NotNull final Result result)
    {
        return result.hashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    public boolean equals(final Object object)
    {
        return equals(getResult(), object);
    }

    /**
     * Checks whether given object is semantically equal to given instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    protected boolean equals(@NotNull final Result result, final Object object)
    {
        return result.equals(object);
    }

    /**
     * Compares given object with this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    public int compareTo(final Object object)
        throws  ClassCastException
    {
        return compareTo(getResult(), object);
    }

    /**
     * Compares given object with given instance.
     * @param result the decorated result.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     * @precondition result != null
     */
    protected int compareTo(@NotNull final Result result, final Object object)
        throws  ClassCastException
    {
        return result.compareTo(object);
    }
}
