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
 * Filename: ConfigurationPropertiesTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate the configuration file for configuring
 *              DAOChooser.
 *
 */
package org.acmsl.queryj.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.customsql.CustomSqlProvider;
import org.acmsl.queryj.metadata.DecorationUtils;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.metadata.MetadataManager;
import org.acmsl.queryj.templates.BasePerRepositoryTemplate;
import org.acmsl.queryj.templates.BasePerRepositoryTemplateContext;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JetBrains annotations.
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
 * Is able to generate the configuration file for configuring
 * DAOChooser.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class ConfigurationPropertiesTemplate
    extends  BasePerRepositoryTemplate<BasePerRepositoryTemplateContext>
{
    private static final long serialVersionUID = -4951603298977943791L;

    /**
     * Builds a <code>ConfigurationPropertiesTemplate</code> using given
     * information.
     * @param context the {@link BasePerRepositoryTemplateContext} instance.
     */
    public ConfigurationPropertiesTemplate(@NotNull final BasePerRepositoryTemplateContext context)
    {
        super(context);
    }

    /**
     * Fills the core parameters.
     * @param input the input.
     * @param metadataManager the database metadata manager.
     * @param customSqlProvider the <code>CustomSqlProvider</code> instance.
     * @param decoratorFactory the <code>DecoratorFactory</code> instance.
     * @param basePackageName the base package name.
     * @param subpackageName the subpackage name.
     * @param tableRepositoryName the table repository name.
     * @param engineName the engine name.
     * @param tables the tables.
     * @param timestamp the timestamp.
     * @param stringUtils the <code>StringUtils</code> instance.
     */
    @SuppressWarnings("unchecked,unused")
    protected void fillCoreParameters(
        @NotNull final Map input,
        @NotNull final MetadataManager metadataManager,
        @NotNull final CustomSqlProvider customSqlProvider,
        @NotNull final DecoratorFactory decoratorFactory,
        @NotNull final String basePackageName,
        @NotNull final String subpackageName,
        @NotNull final String tableRepositoryName,
        @NotNull final String engineName,
        @NotNull final List<String> tables,
        @NotNull final String timestamp,
        @NotNull final StringUtils stringUtils)
    {
        input.put("engine_name", engineName);
        input.put("engine_name_lowercased", engineName.toLowerCase());

        @Nullable String t_strProcessedHeader = getProcessedHeader(input);
        if  (t_strProcessedHeader != null)
        {
            input.put("splitted_header", split(t_strProcessedHeader));
        }
    }

    /**
     * Splits given value into multiple lines.
     * @param value the text.
     * @return the splitted text.
     */
    protected String[] split(final String value)
    {
        return split(value, DecorationUtils.getInstance());
    }
    
    /**
     * Splits given value into multiple lines.
     * @param value the text.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return the splitted text.
     */
    protected String[] split(
        final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.split(value);
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    @Override
    public StringTemplateGroup retrieveGroup()
    {
        return retrieveGroup("/org/acmsl/queryj/" + getTemplateName() + ".stg");
    }

    /**
     * Retrieves the template name.
     * @return such information.
     */
    @NotNull
    public String getTemplateName()
    {
        return "ConfigurationProperties";
    }

}
