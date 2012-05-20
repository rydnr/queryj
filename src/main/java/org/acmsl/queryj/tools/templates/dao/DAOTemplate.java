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

 *****************************************************************************
 *
 * Filename: DAOTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to create engine-specific DAO interfaces for each
 *              table in the persistence model.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.ResultElement;
import org.acmsl.queryj.tools.customsql.Sql;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;
import org.acmsl.queryj.tools.metadata.MetadataUtils;
import org.acmsl.queryj.tools.metadata.vo.Attribute;
import org.acmsl.queryj.tools.metadata.vo.ForeignKey;
import org.acmsl.queryj.tools.metadata.vo.Row;
import org.acmsl.queryj.tools.templates.BasePerTableTemplate;

/*
 * Importing StringTemplate classes.
 */
import org.acmsl.queryj.tools.templates.BasePerTableTemplateContext;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Is able to create engine-specific DAO interfaces for each
 * table in the persistence model.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class DAOTemplate
    extends  BasePerTableTemplate<BasePerTableTemplateContext>
{
    private static final long serialVersionUID = 8937585277018883623L;

    /**
     * Builds a {@link DAOTemplate} using given context.
     * @param context the {@link BasePerTableTemplateContext} instance.
     */
    public DAOTemplate(@NotNull final BasePerTableTemplateContext context)
    {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unused,unchecked")
    @Override
    protected void fillParameters(
        @NotNull final Map input,
        @NotNull final StringTemplate template,
        @NotNull final Integer[] copyrightYears,
        @NotNull final String tableName,
        @NotNull final String voName,
        @NotNull final String engineName,
        @NotNull final String engineVersion,
        @NotNull final String basePackageName,
        @NotNull final String subpackageName,
        @NotNull final String timestamp,
        @NotNull final String className,
        @NotNull final String baseDAOClassName,
        @NotNull final String baseDAOPackageName,
        @NotNull final Collection<Attribute> primaryKeyAttributes,
        @NotNull final Collection<Attribute> nonPrimaryKeyAttributes,
        @NotNull final Collection<ForeignKey> foreignKeyAttributes,
        @NotNull final Map<String,ForeignKey[]> referringKeys,
        @NotNull final Collection<Attribute> attributes,
        @NotNull final Collection<Attribute> externallyManagedAttributes,
        @NotNull final Collection<Attribute> allButExternallyManagedAttributes,
        @NotNull final Collection<Attribute> lobAttributes,
        @NotNull final Collection<Attribute> allButLobAttributes,
        @NotNull final ForeignKey[] foreignKeys,
        @NotNull final Collection<Sql> customSelects,
        @NotNull final Collection<Sql> customUpdatesOrInserts,
        @NotNull final Collection<Sql> customSelectsForUpdate,
        @NotNull final Collection<ResultElement> customResults,
        @Nullable final String staticAttributeName,
        @Nullable final String staticAttributeType,
        @NotNull final String tableRepositoryName,
        @NotNull final MetadataManager metadataManager,
        @NotNull final MetadataTypeManager metadataTypeManager,
        @Nullable final String header,
        final boolean implementMarkerInterfaces,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final MetadataUtils metadataUtils)
    {
        List<Row> t_lStaticValues = getTemplateContext().getStaticValues();

        if  (   (t_lStaticValues != null)
             && (t_lStaticValues.size() > 0))
        {
            input.put("cached_rows", t_lStaticValues);
        }

        super.fillClassParameters(
            input,
            voName,
            engineName,
            engineVersion,
            timestamp,
            staticAttributeName != null,
            tableRepositoryName,
            tableName,
            primaryKeyAttributes,
            nonPrimaryKeyAttributes,
            foreignKeyAttributes,
            referringKeys,
            attributes,
            externallyManagedAttributes,
            allButExternallyManagedAttributes,
            lobAttributes,
            allButLobAttributes,
            foreignKeys,
            staticAttributeName,
            staticAttributeType,
            customSelects,
            customUpdatesOrInserts,
            customSelectsForUpdate,
            customResults,
            metadataManager);

    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/dao/" + getTemplateName() + ".stg");
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    public String getTemplateName()
    {
        return "DAO";
    }
}
            
