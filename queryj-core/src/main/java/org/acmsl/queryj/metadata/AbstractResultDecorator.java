/*
                        QueryJ Core

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
 * Importing QueryJ Core classes.
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
import org.acmsl.queryj.tools.DebugUtils;

/*
 * Importing ACM S.L. Java Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing Apache Commons Logging classes.
 */
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
 * @param <V> the result type.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public abstract class AbstractResultDecorator<V>
    extends  ResultElement<DecoratedString>
    implements  Result<DecoratedString>,
                ResultDecorator<DecoratedString>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 5436116921553920618L;

    /**
     * The result element.
     */
    private Result<V> m__Result;

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
     * Creates a {@code AbstractResultDecorator} with given instance.
     * @param result the result element.
     * @param customSqlProvider the {@link CustomSqlProvider}, required
     * to decorate referred parameters.
     * @param metadataManager the {@link MetadataManager} instance.
     * @param decoratorFactory the {@link  DecoratorFactory} instance.
     */
    public AbstractResultDecorator(
        @NotNull final Result<V> result,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory)
    {
        super(
            new DecoratedString("" + result.getId()),
            result.getClassValue() != null ? new DecoratedString("" + result.getClassValue()) : null);
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
    protected final void immutableSetResult(@NotNull final Result<V> result)
    {
        m__Result = result;
    }

    /**
     * Specifies the result.
     * @param result the result.
     */
    @SuppressWarnings("unused")
    protected void setResult(@NotNull final Result<V> result)
    {
        immutableSetResult(result);
    }

    /**
     * Retrieves the result.
     * @return such element.
     */
    @NotNull
    protected final Result<V> immutableGetResult()
    {
        return m__Result;
    }

    /**
     * Retrieves the result.
     * @return such element.
     */
    @Override
    @NotNull
    public Result<DecoratedString> getResult()
    {
        return decorate(immutableGetResult());
    }

    /**
     * Decorates given result.
     * @param customResult the {@link Result} to decorate.
     * @return such element.
     */
    @SuppressWarnings("unchecked")
    @NotNull
    protected Result<DecoratedString> decorate(@NotNull final Result<V> customResult)
    {
        @NotNull final Result<DecoratedString> result;

        if (customResult.getId() instanceof DecoratedString)
        {
            result = (Result<DecoratedString>) customResult;
        }
        else
        {
            result =
                new ResultElement<>(
                    new DecoratedString("" + customResult.getId()),
                    new DecoratedString("" + customResult.getClassValue()));

            for (@Nullable final PropertyRef propertyRef : customResult.getPropertyRefs())
            {
                if (propertyRef != null)
                {
                    result.add(propertyRef);
                }
            }
        }

        return result;
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
                immutableGetResult(),
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
        @NotNull final Result<V> resultElement,
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
     * @param metadataManager the {@link }MetadataManager} instance.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @param customResultUtils the {@link CustomResultUtils} instance.
     * @return such information.
     */
    @NotNull
    public List<Property<DecoratedString>> getProperties(
        @NotNull final List<PropertyRef> propertyRefs,
        @NotNull final Result<V> resultElement,
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
                    customResultUtils.retrieveTable(resultElement, metadataManager);

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
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param metadataUtils the {@link MetadataUtils} instance.
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
                immutableGetResult(),
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
        @NotNull final Result<V> sqlResult,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final MetadataManager metadataManager,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final CustomResultUtils customResultUtils)
    {
        @Nullable List<Property<DecoratedString>> result = null;

        @Nullable final String t_strTable =
            customResultUtils.retrieveTable(sqlResult, metadataManager);

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
        @NotNull final Result<V> sqlResult,
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
     * Retrieves the ordered list of the fully-qualified attribute types.
     * @return such list.
     */
    @Override
    @NotNull
    public List<DecoratedString> getPropertyTypes()
    {
        return
            getPropertyTypes(
                getProperties(),
                getMetadataManager().getMetadataTypeManager(),
                ResultDecoratorHelper.getInstance());
    }

    /**
     * Retrieves the ordered list of the fully-qualified types of given attributes.
     * @param properties such attributes.
     * @param typeManager the {@link MetadataTypeManager} instance.
     * @param resultDecoratorHelper the {@link org.acmsl.queryj.metadata.ResultDecoratorHelper} instance.
     * @return such list.
     */
    @NotNull
    protected List<DecoratedString> getPropertyTypes(
        @NotNull final List<Property<DecoratedString>> properties,
        @NotNull final MetadataTypeManager typeManager,
        @NotNull final ResultDecoratorHelper resultDecoratorHelper)
    {
        return resultDecoratorHelper.getPropertyTypes(properties, typeManager);
    }

    /**
     * Checks whether some of the properties are nullable or not.
     * @return {@code true} in such case.
     */
    @SuppressWarnings("unused")
    public boolean getContainsNullableProperties()
    {
        return containNullableProperties(getProperties(), ResultDecoratorHelper.getInstance());
    }

    /**
     * Checks whether some of the properties are nullable or not.
     * @return {@code true} in such case.
     */
    @SuppressWarnings("unused")
    public boolean getContainsNullableItems()
    {
        return getContainsNullableProperties();
    }

    /**
     * Checks whether some of the given properties are nullable or not.
     * @param properties the {@link Property properties}.
     * @param resultDecoratorHelper the {@link ResultDecoratorHelper} instance.
     * @return {@code true} in such case.
     */
    protected boolean containNullableProperties(
        @NotNull final List<Property<DecoratedString>> properties,
        @NotNull final ResultDecoratorHelper resultDecoratorHelper)
    {
        return resultDecoratorHelper.containNullableProperties(properties);
    }

    /**
     * Checks whether some of the properties cannot be null.
     * @return {@code true} in such case.
     */
    @SuppressWarnings("unused")
    public boolean getContainsNotNullProperties()
    {
        return
            containNotNullProperties(
                getProperties(), getMetadataManager().getMetadataTypeManager(), ResultDecoratorHelper.getInstance());
    }

    /**
     * Checks whether some of the properties cannot be null.
     * @return {@code true} in such case.
     */
    @SuppressWarnings("unused")
    public boolean getContainsNotNullItems()
    {
        return getContainsNotNullProperties();
    }

    /**
     * Checks whether some of the given properties cannot be null.
     * @param properties the {@link Property properties}.
     * @param metadataTypeManager the {@link MetadataTypeManager} instance.
     * @param resultDecoratorHelper the {@link ResultDecoratorHelper} instance.
     * @return {@code true} in such case.
     */
    protected boolean containNotNullProperties(
        @NotNull final List<Property<DecoratedString>> properties,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @NotNull final ResultDecoratorHelper resultDecoratorHelper)
    {
        return resultDecoratorHelper.containNotNullProperties(properties, metadataTypeManager);
    }

    /**
     * Retrieves the nullable properties.
     * @return such properties.
     */
    @NotNull
    public List<Property<DecoratedString>> getNullableProperties()
    {
        return filterNullableProperties(getProperties());
    }

    /**
     * Filters the nullable properties.
     * @param properties the {@link Property properties}.
     * @return such properties.
     */
    @NotNull
    protected List<Property<DecoratedString>> filterNullableProperties(
        @NotNull final List<Property<DecoratedString>> properties)
    {
        @NotNull final List<Property<DecoratedString>> result = new ArrayList<>(properties.size());

        for (@Nullable final Property<DecoratedString> property : properties)
        {
            if (   (property != null)
                && (property.isNullable()))
            {
                result.add(property);
            }
        }

        return result;
    }


    /**
     * Retrieves the class name, with no package information.
     * @return such information.
     */
    @NotNull
    public DecoratedString getSimpleClassValue()
    {
        return getSimpleClassValue(getClassValue(), StringUtils.getInstance());
    }


    /**
     * Retrieves the class name, with no package information.
     * @param classValue the class value.
     * @param stringUtils the {@link StringUtils} instance.
     * @return such information.
     */
    @NotNull
    protected DecoratedString getSimpleClassValue(
        @Nullable final DecoratedString classValue, @NotNull final StringUtils stringUtils)
    {
        @NotNull final DecoratedString result;

        if (classValue == null)
        {
            result = new DecoratedString("");
        }
        else
        {
            result = new DecoratedString(stringUtils.extractPackageName(classValue.getValue()));
        }

        return result;
    }

    /**
     * Retrieves the package information from the class name.
     * @return such information.
     */
    @NotNull
    public DecoratedString getPackage()
    {
        return getPackage(getClassValue(), StringUtils.getInstance());
    }


    /**
     * Retrieves the package information from the class name.
     * @param classValue the class value.
     * @param stringUtils the {@link StringUtils} instance.
     * @return such information.
     */
    @NotNull
    protected DecoratedString getPackage(
        @Nullable final DecoratedString classValue, @NotNull final StringUtils stringUtils)
    {
        @NotNull final DecoratedString result;

        if (classValue == null)
        {
            result = new DecoratedString("");
        }
        else
        {
            result = new DecoratedString(stringUtils.extractClassName(classValue.getValue()));
        }

        return result;
    }

    /**
     * Retrieves the hashCode.
     * @return such value.
     */
    @Override
    public int hashCode()
    {
        return hashCode(immutableGetResult());
    }

    /**
     * Retrieves the hashCode of given instance.
     * @param result the decorated result.
     * @return such value.
     */
    protected final int hashCode(@NotNull final Result<V> result)
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
            result = equals(immutableGetResult(), object);
        }

        return result;
    }

    /**
     * Checks whether given object is semantically equal to given instance.
     * @param result the {@link Result}.
     * @param object the object to compare to.
     * @return the result of such comparison.
     */
    protected boolean equals(@NotNull final Result<V> result, @Nullable final Object object)
    {
        return result.equals(object);
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
        return
              "{ \"result\": " + m__Result
            + ", \"customSqlProvider\": " + m__CustomSqlProvider.hashCode()
            + ", \"metadataManager\": " + m__MetadataManager.hashCode()
            + ", \"decoratorFactory\": " + m__DecoratorFactory.hashCode()
            + ", \"class\": \"AbstractResultDecorator\""
            + ", \"package\": \"org.acmsl.queryj.metadata\""
            + " }";
    }

}
