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
 * Filename: TemplateHandlerBundle.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Bundles a pair of template build and writing handlers.
 *
 */
package org.acmsl.queryj.tools.templates.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.handlers.CompositeQueryJCommandHandler;

/*
 * Importing jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.Arrays;

/**
 * Bundles a pair of template build and writing handlers.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class TemplateHandlerBundle
    extends    CompositeQueryJCommandHandler
    implements TemplateHandler
{
    /**
     * The system property prefix to disable concrete (or all, with *) template handlers.
     */
    public static final String TEMPLATES_DISABLED = "queryj.templates.disabled";

    /**
     * The system property to enable concrete (or all, with * or missing property) template handlers.
     */
    public static final String TEMPLATES_ENABLED = "queryj.templates.enabled";

    /**
     * Builds a bundle with given handlers.
     * @param buildHandler the template build handler.
     * @param writingHandler the writing handler.
     * @precondition buildHandler != null
     * @precondition writingHandler != null
     */
    public TemplateHandlerBundle(
        @NotNull final TemplateBuildHandler buildHandler,
        @NotNull final TemplateWritingHandler writingHandler)
    {
        if (isTemplateHandlingEnabled())
        {
            immutableAddHandler(buildHandler);
            immutableAddHandler(writingHandler);
        }
    }

    /**
     * Builds a bundle with given ones.
     * @param bundles the first bundle.
     * @precondition bundles != null
     */
    public TemplateHandlerBundle(@Nullable final TemplateHandlerBundle[] bundles)
    {
        if (bundles != null)
        {
            for (TemplateHandlerBundle t_Bundle : bundles)
            {
                if  (t_Bundle != null)
                {
                    if (isTemplateHandlingEnabled(retrieveTemplateName(t_Bundle.getClass().getName())))
                    {
                        immutableAddHandler(t_Bundle);
                    }
                }
            }
        }
    }

    /**
     * Builds a bundle with given two.
     * @param firstBundle the first bundle.
     * @param secondBundle the second bundle, typically
     * related to test templates associated to the first one's.
     */
    public TemplateHandlerBundle(
        final TemplateHandlerBundle firstBundle,
        final TemplateHandlerBundle secondBundle)
    {
        this(new TemplateHandlerBundle[] { firstBundle, secondBundle });
    }

    /**
     * Adds a new handler to the collection.
     * @param handler the new handler to add.
     */
    private void immutableAddHandler(final TemplateHandler handler)
    {
        super.addHandler(handler);
    }

    /**
     * Checks whether this template bundle is enabled or not.
     * @return such behavior.
     */
    protected boolean isTemplateHandlingEnabled()
    {
        return
            isTemplateHandlingEnabled(
                retrieveTemplateName(getClass().getName()));
    }

    /**
     * Checks whether given template is enabled or not.
     * @param templateName the template name.
     * @return such behavior.
     */
    protected final boolean isTemplateHandlingEnabled(@NotNull final String templateName)
    {
        return
            isTemplateHandlingEnabled(
                System.getProperty(TEMPLATES_DISABLED),
                System.getProperty(TEMPLATES_ENABLED),
                templateName);
    }

    /**
     * Retrieves the template name for given class name.
     * @param handlerName the handler name.
     * @return the template name, by removing trailing "TemplateHandlerBundle"
     */
    @NotNull
    protected final String retrieveTemplateName(@NotNull final String handlerName)
    {
        return handlerName.replaceAll("TemplateHandlerBundle$", "").replaceAll(".*\\.", "");
    }

    /**
     * Checks whether this template bundle is enabled or not.
     * @param templatesDisabled the environment property for disabled templates.
     * @param templatesEnabled the environment property for enabled templates.
     * @param handlerName the handler name.
     * @return such behavior.
     */
    protected final boolean isTemplateHandlingEnabled(
        @Nullable final String templatesDisabled,
        @Nullable final String templatesEnabled,
        @NotNull final String handlerName)
    {
        boolean result = true;

        boolean t_bExplicitlyEnabled = false;

        String[] t_astrEnabled;

        if (templatesEnabled != null)
        {
            t_astrEnabled = templatesEnabled.split(",");

            t_bExplicitlyEnabled = Arrays.asList(t_astrEnabled).contains(handlerName);

            result = t_bExplicitlyEnabled;
        }
        else
        {
            t_astrEnabled = new String[0];
        }

        if (!t_bExplicitlyEnabled)
        {
            if (   ("*".equals(templatesDisabled))
                || (t_astrEnabled.length > 1)) // explicitly-enabled templates imply
                // the others are disabled implicitly.
            {
                result = false;
            }
            else if (templatesDisabled != null)
            {
                String[] t_astrDisabled = templatesDisabled.split(",");

                result = !Arrays.asList(t_astrDisabled).contains(handlerName);
            }
        }

//        if (result)
//        {
            // for debugging purposes
//            int a = -5;
//        }

        return result;
    }
}
