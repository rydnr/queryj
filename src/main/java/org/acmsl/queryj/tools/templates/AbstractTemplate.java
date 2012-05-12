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
 * Filename: AbstractTemplate.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents generic templates.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.tools.metadata.DecorationUtils;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;
import org.acmsl.commons.utils.ClassLoaderUtils;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateErrorListener;
import org.antlr.stringtemplate.StringTemplateGroup;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents generic templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public abstract class AbstractTemplate
    implements  STTemplate,
                DefaultThemeConstants,
                Serializable
{
    private static final long serialVersionUID = -3708579868912707138L;

    /**
     * The default StringTemplate error listener.
     */
    @Nullable
    protected static final StringTemplateErrorListener
        DEFAULT_ST_ERROR_LISTENER =
            new StringTemplateErrorListener()
            {
                /**
                 * Receives a warning message.
                 * @param message the warning message.
                 */
                public void warning(final String message)
                {
                    Log t_Log =
                        UniqueLogFactory.getLog(AbstractTemplate.class);

                    if  (t_Log != null)
                    {
                        t_Log.warn(message);
                    }
                }

                /**
                 * Receives the error.
                 * @param message the error message.
                 * @param cause the cause.
                 */
                public void error(final String message, final Throwable cause)
                {
                    Log t_Log =
                        UniqueLogFactory.getLog(AbstractTemplate.class);

                    if  (t_Log != null)
                    {
                        t_Log.fatal(message, cause);
                    }
                }
            };

    /**
     * Caches the StringTemplateGroup for each template class.
     */
    private static Map m__mSTCache;

    /**
     * The optional header.
     */
    private String m__strHeader;

    /**
     * The cached processed header.
     */
    private String m__strCachedProcessedHeader;
    
    /**
     * The decorator factory.
     */
    private DecoratorFactory m__DecoratorFactory;

    /**
     * A singleton container to avoid the double-checking lock idiom.
     */
    protected static final class FinalizingThreadSingletonContainer
    {
        /**
         * The singleton instance.
         */
        protected static final FinalizingThread THREAD =
            new FinalizingThread();

        /**
         * Retrieves the thread.
         * @return such information.
         */
        @NotNull
        public static FinalizingThread getInstance()
        {
            return THREAD;
        }
    }

    /**
     * Builds a {@link AbstractTemplate} with given
     * decorator factory.
     * @param header the optional header.
     * @param decoratorFactory the {@link DecoratorFactory} instance.
     * @precondition decoratorFactory != null
     */
    protected AbstractTemplate(
        final String header,
        final DecoratorFactory decoratorFactory)
    {
        immutableSetHeader(header);
        immutableSetDecoratorFactory(decoratorFactory);
        immutableSetSTCache(new HashMap());
    }

    /**
     * Specifies the header.
     * @param header the header.
     */
    protected final void immutableSetHeader(final String header)
    {
        m__strHeader = header;
    }
    
    /**
     * Specifies the header.
     * @param header the header.
     */
    protected void setHeader(final String header)
    {
        immutableSetHeader(header);
    }
    
    /**
     * Retrieves the header.
     * @return the header.
     */
    protected String getHeader()
    {
        return m__strHeader;
    }

    /**
     * Specifies the cached processed header.
     * @param header such value.
     */
    protected final void immutableSetCachedProcessedHeader(final String header)
    {
        m__strCachedProcessedHeader = header;
    }
    
    /**
     * Specifies the cached processed header.
     * @param header such value.
     */
    protected void setCachedProcessedHeader(final String header)
    {
        immutableSetCachedProcessedHeader(header);
    }
    
    /**
     * Retrieves the cached processed header.
     * @returnsuch value.
     */
    protected String getCachedProcessedHeader()
    {
        return m__strCachedProcessedHeader;
    }

    /**
     * Retrieves the processed header.
     * @param input the input.
     * @return such information.
     * @precondition input != null
     */
    @Nullable
    public String getProcessedHeader(final Map input)
    {
        @Nullable String result = getCachedProcessedHeader();
        
        if  (result == null)
        {
            result = processHeader(input, getHeader());
        }
        
        return result;
    }

    /**
     * Retrieves the processed header.
     * @param input the input.
     * @return such information.
     * @precondition input != null
     */
    @Nullable
    public String processHeader(final Map input, final String header)
    {
        @Nullable String result = processInnerTemplate(header, input);

        setCachedProcessedHeader(result);
        
        return result;
    }

    /**
     * Specifies the decorator factory.
     * @param factory the {@link DecoratorFactory} instance.
     */
    protected final void immutableSetDecoratorFactory(
        final DecoratorFactory factory)
    {
        m__DecoratorFactory = factory;
    }

    /**
     * Specifies the decorator factory.
     * @param factory the {@link DecoratorFactory} instance.
     */
    protected void setDecoratorFactory(
        final DecoratorFactory factory)
    {
        immutableSetDecoratorFactory(factory);
    }

    /**
     * Retrieves the {@link DecoratorFactory} instance.
     * @return such instance.
     */
    public DecoratorFactory getDecoratorFactory()
    {
        return m__DecoratorFactory;
    }

    /**
     * Specifies the ST cache.
     * @param map the map to use as cache.
     */
    protected static final void immutableSetSTCache(final Map map)
    {
        m__mSTCache = map;
    }

    /**
     * Retrieves the ST cache.
     * @return the map being used as cache.
     */
    protected static final Map immutableGetSTCache()
    {
        return m__mSTCache;
    }

    /**
     * Retrieves the string template group.
     * @return such instance.
     */
    @Nullable
    public abstract StringTemplateGroup retrieveGroup();

    /**
     * Retrieves the string template group.
     * @param path the path.
     * @return such instance.
     * @precondition path != null
     */
    @Nullable
    protected StringTemplateGroup retrieveGroup(final String path)
    {
        return
            retrieveGroup(
                path,
                "/org/acmsl/queryj/queryj.stg",
                immutableGetSTCache());
    }
    
    /**
     * Retrieves the string template group.
     * @param path the path.
     * @param theme the theme.
     * @param cache the ST cache.
     * @return such instance.
     * @precondition path != null
     * @precondition theme != null
     * @precondition cache != null
     */
    @Nullable
    protected StringTemplateGroup retrieveGroup(
        final String path, final String theme, @NotNull final Map cache)
    {
        @Nullable StringTemplateGroup result = null;
        
        @NotNull Object t_Key = buildSTGroupKey(path, theme);

        result = (StringTemplateGroup) cache.get(t_Key);

        if  (result == null)
        {
            result =
                retrieveGroup(
                    path,
                    theme,
                    retrieveStErrorListener(),
                    STUtils.getInstance());

            if  (result != null)
            {
                cache.put(t_Key, result);
            }
        }
        
        return result;
    }

    /**
     * Retrieves the StringTemplate error listener.
     * @return such instance.
     */
    @Nullable
    protected StringTemplateErrorListener retrieveStErrorListener()
    {
        return DEFAULT_ST_ERROR_LISTENER;
    }

    /**
     * Builds a key to store the ST group associated to
     * given path and theme.
     * @param path the ST path.
     * @param theme the ST theme.
     * @return such key.
     * @precondition path != null
     * @precondition theme != null
     */
    @NotNull
    protected final Object buildSTGroupKey(
        final String path, final String theme)
    {
        return ".:\\AbstractTemplate//STCACHE//" + path + "//" + theme;
    }

    /**
     * Retrieves the string template group.
     * @param path the path.
     * @param theme the theme.
     * @param errorListener the {@link StringTemplateErrorListener}
     * instance.
     * @param stUtils the {@link STUtils} instance.
     * @return such instance.
     * @precondition path != null
     * @precondition theme != null
     * @precondition stUtils != null
     */
    @Nullable
    protected StringTemplateGroup retrieveGroup(
        final String path,
        final String theme,
        final StringTemplateErrorListener errorListener,
        @NotNull final STUtils stUtils)
    {
        return stUtils.retrieveGroup(path, theme, errorListener);
    }

    /**
     * Configures given {@link StringTemplate} instance.
     * @param stringTemplate such template.
     * @precondition stringTemplate != null
     */
    protected void configure(@NotNull final StringTemplate stringTemplate)
    {
        stringTemplate.setPassThroughAttributes(true);
        stringTemplate.setLintMode(true);
        stringTemplate.setErrorListener(retrieveStErrorListener());
    }

    /**
     * Retrieves the template in given group.
     * @param group the StringTemplate group.
     * @return the template.
     * @precondition group != null
     */
    @Nullable
    protected StringTemplate retrieveTemplate(@Nullable final StringTemplateGroup group)
    {
        @Nullable StringTemplate result = null;
        
        if  (group != null)
        {
            result = group.getInstanceOf(TEMPLATE_NAME);
        }

        return result;
    }
    
    /**
     * Retrieves the current year.
     * @return such value.
     */
    protected int retrieveCurrentYear()
    {
        return retrieveYear(Calendar.getInstance());
    }
    
    /**
     * Retrieves the year defined in given date.
     * @param calendar the calendar.
     * @return such value.
     * @precondition calendar != null
     */
    protected int retrieveYear(@NotNull final Calendar calendar)
    {
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Creates a timestamp.
     * @return such timestamp.
     */
    protected String createTimestamp()
    {
        return createTimestamp(new Date(), TIMESTAMP_FORMAT);
    }
    
    /**
     * Formats given date.
     * @param date the date.
     * @param format the formatter.
     * @return the formatted date.
     */
    protected String createTimestamp(
        @NotNull final Date date, @NotNull final String format)
    {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Generates the source code.
     * @return such output.
     * @throws InvalidTemplateException if the template cannot be generated.
     */
    public String generate()
      throws  InvalidTemplateException
    {
        String result;

        logHeader(buildHeader());

        traceClassLoaders();

        result = generateOutput(getHeader());

        cleanUpClassLoaderTracing();

        return result;
    }

    /**
     * Logs a custom header.
     * @param header the header.
     */
    protected void logHeader(final String header)
    {
        Log t_Log = UniqueLogFactory.getLog(AbstractTemplate.class);
        
        if  (t_Log != null)
        {
            t_Log.trace(header);
        }
    }

    /**
     * Builds the header for logging purposes.
     * @return such header.
     */
    protected String buildHeader()
    {
        return "Generating " + getClass().getName() + ".";
    }

    /**
     * Processes given text as a template.
     * @param template the template.
     * @param input the input.
     * @return the processed text.
     * @precondition template != null
     * @precondition input != null
     */
    @Nullable
    protected String processInnerTemplate(
        @Nullable final String template, final Map input)
    {
        @Nullable String result = null;
        
        if  (template != null)
        {
            @NotNull StringTemplate t_strInnerTemplate =
                new StringTemplate(
                    template, AngleBracketTemplateLexer.class);

            t_strInnerTemplate.setAttribute("input", input);
        
            result = t_strInnerTemplate.toString();
        }
        
        return result;
    }

    /**
     * Prints a log message displaying ClassLoader issues related
     * to ANTLR.jar and StringTemplate.jar.
     */
    protected void traceClassLoaders()
    {
        @NotNull FinalizingThread t_FinalizingThread =
            FinalizingThreadSingletonContainer.getInstance();

        synchronized (t_FinalizingThread)
        {
            Runtime t_Runtime = Runtime.getRuntime();

            if  (t_Runtime != null)
            {
                t_Runtime.addShutdownHook(t_FinalizingThread);
            }
        }
    }

    /**
     * Cleans up the thread to trace class loaders on shutdown.
     */
    protected void cleanUpClassLoaderTracing()
    {
        @NotNull FinalizingThread t_FinalizingThread =
            FinalizingThreadSingletonContainer.getInstance();

        synchronized (t_FinalizingThread)
        {
            Runtime t_Runtime = Runtime.getRuntime();

            if  (t_Runtime != null)
            {
                t_Runtime.removeShutdownHook(t_FinalizingThread);
            }
        }
    }

    /**
     * Thread to provide some information about ANTLR class loaders,
     * since it's likely to have triggered the VM shutdown.
     * @author <a href="mailto:jose.sanleandro@ventura24.es"
     * >Jose San Leandro</a>
     * @version $Revision$
     */
    protected static class FinalizingThread
        extends  Thread
    {
        /**
         * Whether the thread is new or not.
         */
        private boolean m__bNew = true;

        /**
         * Retrieves whether this thread has just been created or not.
         * @return such information.
         */
        public boolean isNew()
        {
            boolean result = m__bNew;

            m__bNew = false;

            return result;
        }

        /**
         * Runs the thread.
         */
        public void run()
        {
            traceANTLRClassLoadingIssues();
        }
    }

    /**
     * Prints a log message displaying ClassLoader issues related
     * to ANTLR.jar and StringTemplate.jar.
     */
    protected static void traceANTLRClassLoadingIssues()
    {
        // CharScanner; panic: ClassNotFoundException:
        // org.antlr.stringtemplate.language.ChunkToken
        // can happen if ANTLR gets loaded by a ClassLoader
        // with no reference to StringTemplate classes.
        Log t_Log =
            UniqueLogFactory.getLog(AbstractTemplate.class);

        ClassLoaderUtils t_ClassLoaderUtils =
            ClassLoaderUtils.getInstance();

        if  (   (t_Log != null)
             && (t_ClassLoaderUtils != null))
        {
            @NotNull StringBuilder t_sbMessage = new StringBuilder();

            String t_strAntlrLocation =
                t_ClassLoaderUtils.findLocation(
                    antlr.ANTLRParser.class);

            String t_strStringTemplateLocation =
                t_ClassLoaderUtils.findLocation(
                    StringTemplate.class);

            t_sbMessage.append(
                  "A fatal error in StringTemplate-based generation "
                + "has stopped QueryJ build process.\n"
                + "If you see error messages from StringTemplate, "
                + "review your templates. Otherwise, if the VM "
                + "exited due to ClassNotFoundException or "
                + "NoClassDefFoundException regarding ANTLR or StringTemplate "
                + "classes (i.e. ChunkToken), then "
                + "antlr-X.Y.jar has been loaded by a class loader "
                + "with no idea of where StringTemplate classes "
                + "are, and therefore ANTLR's reflection-based "
                + "instantiation fails. Check your classpath or the way "
                + "it's defined by Ant or any other tool you might be "
                + "using. ");
            boolean t_bAntlrLocationFound =
                (   (t_strAntlrLocation != null)
                 && (t_strAntlrLocation.trim().length() > 0));
            boolean t_bStringTemplateLocationFound =
                (   (t_strStringTemplateLocation != null)
                 && (t_strStringTemplateLocation.trim().length() > 0));

            if  (   (t_bAntlrLocationFound)
                 || (t_bStringTemplateLocationFound))
            {
                t_sbMessage.append("Hint: ");

                if  (   (t_strAntlrLocation != null)
                     && (t_strAntlrLocation.trim().length() > 0))
                {
                    t_sbMessage.append("ANTLR is loaded from ");
                    t_sbMessage.append(t_strAntlrLocation);
                }

                if  (t_bStringTemplateLocationFound)
                {
                    if (t_bAntlrLocationFound)
                    {
                        t_sbMessage.append(
                            " whereas ");
                    }
                    t_sbMessage.append(
                        "StringTemplate is loaded from ");
                    t_sbMessage.append(
                        t_strStringTemplateLocation);
                }
            }

            t_Log.fatal(t_sbMessage.toString());
        }
    }

    /**
     * Generates the actual source code.
     * @param header the header.
     * @return such output. 
     * @throws InvalidTemplateException if the template cannot be generated.
     */
    protected abstract String generateOutput(final String header)
      throws  InvalidTemplateException;

    /**
     * Converts given value to lower-case.
     * @param value the value.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such output.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String lowercase(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.lowerCase(value);
    }

    /**
     * Normalizes given value.
     * @param value the value.
     * @return such output.
     * @precondition value != null
     */
    protected String normalize(final String value)
    {
        return normalize(value, DecorationUtils.getInstance());
    }

    /**
     * Normalizes given value.
     * @param value the value.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such output.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalize(
        final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalize(value);
    }

    /**
     * Normalizes given value, in lower-case.
     * @param value the value.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such output.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalizeLowercase(
        final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalizeLowercase(value);
    }

    /**
     * Normalizes given value, in upper-case.
     * @param value the value.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such output.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalizeUppercase(
        final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalizeUppercase(value);
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @return such output.
     * @precondition value != null
     */
    protected String capitalize(@NotNull final String value)
    {
        return capitalize(value, DecorationUtils.getInstance());
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @param decorationUtils the {@link DecorationUtils} instance.
     * @return such output.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String capitalize(
        @NotNull final String value, @NotNull final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(value);
    }
}
