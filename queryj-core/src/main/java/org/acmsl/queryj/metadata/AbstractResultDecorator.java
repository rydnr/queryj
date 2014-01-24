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
package org.acmsl.queryj.metadata;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.api.exceptions.NullAttributeWhenConvertingToPropertyException;
import org.acmsl.queryj.customsql.CustomResultUtils;
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.customsql.Property;
import org.acmsl.queryj.customsql.PropertyElement;
import org.acmsl.queryj.customsql.PropertyRef;
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.ResultElement;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.Table;

/*
 * Importing Apache Commons Logging classes.
 */
import org.acmsl.queryj.tools.DebugUtils;
import org.apache.commons.logging.LogFactory;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Decorates &lt;result&gt; elements in <i>custom-sql</i> models.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public abstract class AbstractResultDecorator
    extends  ResultElement<DecoratedString>
    implements  ResultDecorator
{
    /**
     * The result element.
     */
    private Result<String> m__Result;

    /**
     * The custom sql provider.
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
     */
    public AbstractResultDecorator(
        @NotNull final Result<String> result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(
            new DecoratedString(result.getId()),
            result.getClassValue() != null ? new DecoratedString(result.getClassValue()) : null,
            new DecoratedString(result.getMatches()));
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
    protected final void immutableSetResult(@NotNull final Result<String> result)
    {
        m__Result = result;
    }

    /**
     * Specifies the result.
     * @param result the result.
     */
    @SuppressWarnings("unused")
    protected void setResult(@NotNull final Result<String> result)
    {
        immutableSetResult(result);
    }

    /**
     * Retrieves the result.
     * @return such element.
     */
    @NotNull
    public Result<String> getResult()
    {
        return m__Result;
    }

    /**
     * Specifies the custom SQL provider.
     * @param customSqlProvider such provider.
     */
    protected final void immutableSetCustomSqlProvider(
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        m__CustomSqlProvider = customSqlProvider;
    }

    /**
     * Specifies the custom SQL provider.
     * @param customSqlProvider such provider.
     */
    @SuppressWarnings("unused")
    protected void setCustomSqlProvider(
        @NotNull final CustomSqlProvider customSqlProvider)
    {
        immutableSetCustomSqlProvider(customSqlProvider);
    }

    /**
     * Retrieves the custom SQL provider.
     * @return such provider.
     */
    @NotNull
    protected CustomSqlProvider getCustomSqlProvider()
    {
        return m__CustomSqlProvider;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected final void immutableSetMetadataManager(
        @NotNull final MetadataManager metadataManager)
    {
        m__MetadataManager = metadataManager;
    }

    /**
     * Specifies the metadata manager.
     * @param metadataManager such instance.
     */
    protected void setMetadataManager(
        @NotNull final MetadataManager metadataManager)
    {
        immutableSetMetadataManager(metadataManager);
    }

    /**
     * Retrieves the metadata manager.
     * @return such instance.
     */
    @NotNull
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
    protected final void immutableSetDecoratorFactory(@NotNull final DecoratorFactory factory)
    {
        m__DecoratorFactory = factory;
    }

    /**
     * Specifies the decorator factory.
     * @param factory such instance.
     */
    @SuppressWarnings("unused")
    protected void setDecoratorFactory(@NotNull final DecoratorFactory factory)
    {
        immutableSetDecoratorFactory(factory);
    }

    /**
     * Retrieves the decorator factory.
     * @return such instance.
     */
    @NotNull
    public DecoratorFactory getDecoratorFactory()
    {
        return m__DecoratorFactory;
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
     */
    protected boolean isMultiple(@NotNull final Result<String> result)
    {
        return Result.MULTIPLE.equalsIgnoreCase(result.getMatches());
    }

    /**
     * Retrieves the properties.
     * @return such information.
     */
    @Override
    @NotNull
    public List<Property<DecoratedString>> getProperties()
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
     * @param propertyRefs the property references.
     * @param resultElement the result element.
     * @param customSqlProvider the <code>CustomSqlProvider</code>.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @return such information.
     */
    @NotNull
    public List<Property<DecoratedString>> getProperties(
        @NotNull final List<PropertyRef> propertyRefs,
        @NotNull final Result<String> resultElement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomResultUtils customResultUtils)
    {
        return
            getProperties(
                propertyRefs,
                resultElement,
                customSqlProvider,
                customSqlProvider.getSqlPropertyDAO(),
                metadataManager,
                decoratorFactory,
                customResultUtils);
    }

    /**
     * Retrieves the properties.
     * @param propertyRefs the property references.
     * @param resultElement the result element.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param sqlPropertyDAO the {@link SqlPropertyDAO} instance.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param customResultUtils the <code>CustomResultUtils</code> instance.
     * @return such information.
     */
    @NotNull
    public List<Property<DecoratedString>> getProperties(
        @NotNull final List<PropertyRef> propertyRefs,
        @NotNull final Result<String> resultElement,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final SqlPropertyDAO sqlPropertyDAO,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomResultUtils customResultUtils)
    {

        @NotNull final List<Property<DecoratedString>> result = new ArrayList<>();

        @Nullable Property<String> t_Property ;

        for (@Nullable final PropertyRef t_PropertyRef : propertyRefs)
        {
            if  (t_PropertyRef != null)
            {
                t_Property =
                    sqlPropertyDAO.findByPrimaryKey(t_PropertyRef.getId());

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
            else
            {
                @Nullable final String t_strTable =
                    customResultUtils.retrieveTable(
                        resultElement,
                        customSqlProvider,
                        metadataManager);

                if  (t_strTable != null)
                {
                    for (@Nullable final Attribute<String> t_Attribute :
                             metadataManager.getColumnDAO().findAllColumns(t_strTable))
                    {
                        if (t_Attribute != null)
                        {
                            result.add(
                                decoratorFactory.createDecorator(
                                    new PropertyElement<>(
                                        t_strTable + "." + t_Attribute.getName(),
                                        t_Attribute.getName(),
                                        t_Attribute.getOrdinalPosition(),
                                        t_Attribute.getType(),
                                        t_Attribute.isNullable()),
                                    resultElement,
                                    customSqlProvider,
                                    metadataManager));
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Retrieves the large-object-block properties.
     * @return such collection.
     */
    @Override
    @NotNull
    public List<Property<DecoratedString>> getLobProperties()
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
     */
    @NotNull
    protected List<Property<DecoratedString>> filterLobProperties(
        final List<Property<DecoratedString>> properties,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final MetadataUtils metadataUtils)
    {
        return metadataUtils.filterLobProperties(properties, metadataTypeManager);
    }

    /**
     * Checks whether the result is 'implicit' (associated to a table) or not.
     * @return such information.
     */
    @SuppressWarnings("unused")
    public boolean isImplicit()
    {
        boolean result = true;

        if (DebugUtils.getInstance().debugEnabledForResultId(getId()))
        {
            @SuppressWarnings("unused") final int a = 1;
        }

        @NotNull final List<PropertyRef> t_lExplicitProperties =
            getPropertyRefs();

        if (t_lExplicitProperties.size() > 0)
        {
            result = false;
        }

        return result;
    }

    /**
     * Retrieves the implicit properties.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public List<Property<DecoratedString>> getImplicitProperties()
    {
        return
            getImplicitProperties(
                getResult(),
                getCustomSqlProvider(),
                getMetadataManager(),
                getDecoratorFactory(),
                CustomResultUtils.getInstance());
    }

    /**
     * Retrieves the implicit properties.
     * @param sqlResult the {@link Result} instance.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param customResultUtils the {@link CustomResultUtils} instance.
     * @return such information.
     */
    @NotNull
    public List<Property<DecoratedString>> getImplicitProperties(
        @NotNull final Result<String> sqlResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomResultUtils customResultUtils)
    {
        @Nullable List<Property<DecoratedString>> result = null;

        @Nullable final String t_strTable =
            customResultUtils.retrieveTable(sqlResult, customSqlProvider, metadataManager);

        if (t_strTable != null)
        {
            @Nullable final Table<String, Attribute<String>, List<Attribute<String>>> t_Table =
                metadataManager.getTableDAO().findByName(t_strTable);

            if (t_Table != null)
            {
                result =
                    convert(
                        t_Table.getAttributes(),
                        sqlResult,
                        customSqlProvider,
                        metadataManager,
                        decoratorFactory);
            }
        }

        if (result == null)
        {
            result = new ArrayList<>(0);
        }
        else
        {
            Collections.sort(result);
        }

        return result;
    }

    /**
     * Converts given {@link Attribute attributes} to a list of {@link Property properties}.
     * @param attributes the attributes to convert.
     * @param sqlResult the {@link Result} element.
     * @param customSqlProvider the {@link CustomSqlProvider} instance.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @return the resulting properties.
     */
    @NotNull
    protected List<Property<DecoratedString>> convert(
        @NotNull final List<Attribute<String>> attributes,
        @NotNull final Result<String> sqlResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        @NotNull final List<Property<DecoratedString>> result = new ArrayList<>(attributes.size());

        for (@Nullable final Attribute<String> t_Attribute : attributes)
        {
            if (t_Attribute != null)
            {
                result.add(
                    decoratorFactory.createDecorator(
                        new PropertyElement<>(
                            "implicit." + t_Attribute.getTableName() + "." + t_Attribute.getName(),
                            t_Attribute.getName(),
                            t_Attribute.getOrdinalPosition(),
                            t_Attribute.getType(),
                            t_Attribute.isNullable()),
                        sqlResult,
                        customSqlProvider,
                        metadataManager));
            }
            else
            {
                throw new NullAttributeWhenConvertingToPropertyException(sqlResult);
            }
        }

        Collections.sort(result);

        return result;
    }

    /**
     * Retrieves whether this {@link Result} is wrapping a single {@link Property}.
     * @return <code>true</code> in such case.
     */
    @Override
    public boolean isWrappingASingleProperty()
    {
        return isWrappingASingleProperty(getProperties());
    }

    /**
     * Retrieves whether this {@link Result} is wrapping a single {@link Property}.
     * @param properties the list of {@link Property properties}.
     * @return <code>true</code> in such case.
     */
    protected boolean isWrappingASingleProperty(@NotNull final List<Property<DecoratedString>> properties)
    {
        return properties.size() == 1;
    }

    /**
     * Provides a text representation of the information
     * contained in given instance.
     * @return such information.
     */
    @Override
    @NotNull
    public String toString()
    {
        return "AbstractResultDecorator{" +
               "customSqlProvider=" + m__CustomSqlProvider +
               ",result=" + m__Result +
               ",metadataManager=" + m__MetadataManager +
               ",decoratorFactory=" + m__DecoratorFactory +
               '}';
    }

    /**
     * Retrieves the hashcode.
     * @return such value.
     */
    @Override
    public final int hashCode()
    {
        return hashCode(getResult());
    }

    /**
     * Retrieves the hashcode of given instance.
     * @param result the decoreated result.
     * @return such value.
     */
    protected final int hashCode(@NotNull final Result<String> result)
    {
        return result.hashCode();
    }

    /**
     * Checks whether given object is semantically equal to this instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    @Override
    public boolean equals(@Nullable final Object object)
    {
        boolean result = false;

        if (object instanceof Result)
        {
            result = equals(getResult(), object);
        }

        return result;
    }

    /**
     * Checks whether given object is semantically equal to given instance.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    protected boolean equals(@NotNull final Result<String> result, @Nullable final Object object)
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
    @Override
    public int compareTo(@Nullable final Result<DecoratedString> object)
        throws  ClassCastException
    {
        return compareTo(getResult(), object);
    }

    /**
     * Compares given object with given instance.
     * @param resultElement the decorated result.
     * @param object the object to compare to.
     * @return the result of such comparison.
     * @throws ClassCastException if the type of the specified
     * object prevents it from being compared to this Object.
     */
    @SuppressWarnings("unchecked")
    protected int compareTo(@NotNull final Result<String> resultElement, @Nullable final Result<DecoratedString> object)
        throws  ClassCastException
    {
        final int result;

        if (object == null)
        {
            result = 1;
        }
        else
        {
            result =
                resultElement.compareTo(
                    new ResultElement<>(
                        object.getId().getValue(),
                        (object.getClassValue() != null ? object.getClassValue().getValue() : null),
                        object.getMatches().getValue()));
        }

        return result;
    }
}
