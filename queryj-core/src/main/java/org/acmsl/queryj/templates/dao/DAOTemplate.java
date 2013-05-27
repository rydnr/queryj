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
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.customsql.Result;
import org.acmsl.queryj.customsql.Sql;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.metadata.MetadataTypeManager;
import org.acmsl.queryj.metadata.MetadataUtils;
import org.acmsl.queryj.metadata.vo.Attribute;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.metadata.vo.Row;
import org.acmsl.queryj.templates.BasePerTableTemplate;

/*
 * Importing StringTemplate classes.
 */
import org.acmsl.queryj.templates.BasePerTableTemplateContext;
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
import java.util.List;
import java.util.Map;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to create engine-specific DAO interfaces for each
 * table in the persistence model.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
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
        @NotNull final List<Attribute> primaryKeyAttributes,
        @NotNull final List<Attribute> nonPrimaryKeyAttributes,
        @NotNull final List<ForeignKey> foreignKeyAttributes,
        @NotNull final Map<String,List<ForeignKey>> referringKeys,
        @NotNull final List<Attribute> attributes,
        @NotNull final List<Attribute> externallyManagedAttributes,
        @NotNull final List<Attribute> allButExternallyManagedAttributes,
        @NotNull final List<Attribute> lobAttributes,
        @NotNull final List<Attribute> allButLobAttributes,
        @NotNull final List<ForeignKey> foreignKeys,
        @NotNull final List<Sql> customSelects,
        @NotNull final List<Sql> customUpdatesOrInserts,
        @NotNull final List<Sql> customSelectsForUpdate,
        @NotNull final List<Result> customResults,
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
            
