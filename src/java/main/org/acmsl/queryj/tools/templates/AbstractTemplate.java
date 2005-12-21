/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
 * Filename: $RCSfile$
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
import org.acmsl.queryj.tools.templates.DefaultThemeConstants;
import org.acmsl.queryj.tools.templates.InvalidTemplateException;
import org.acmsl.queryj.tools.templates.Template;

/*
 * Importing some ACM-SL Commons classes.
 */
import org.acmsl.commons.logging.UniqueLogFactory;

/*
 * Importing Commons-Logging classes.
 */
import org.apache.commons.logging.Log;

/*
 * Importing StringTemplate classes.
 */
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;

/*
 * Importing some JDK classes.
 */
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents generic templates.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public abstract class AbstractTemplate
    implements  Template,
                DefaultThemeConstants
{
    /**
     * Builds an empty <code>AbstractTemplate</code>.
     */
    protected AbstractTemplate()
    {
    }

    /**
     * Retrieves the string template group.
     * @param path the path.
     * @return such instance.
     * @precondition path != null
     */
    protected StringTemplateGroup retrieveGroup(final String path)
    {
        return retrieveGroup(path, "/org/acmsl/queryj/queryj.stg");
    }
    
    /**
     * Retrieves the string template group.
     * @param path the path.
     * @param theme the theme.
     * @return such instance.
     * @precondition path != null
     * @precondition theme != null
     */
    protected StringTemplateGroup retrieveGroup(
        final String path, final String theme)
    {
        StringTemplateGroup result = null;
        
        InputStream themeInput =
            getClass().getResourceAsStream(theme);

        InputStream groupInput =
            getClass().getResourceAsStream(path);

        StringTemplateGroup themeGroup =
            new StringTemplateGroup(
                new InputStreamReader(themeInput),
                AngleBracketTemplateLexer.class);

        result =
            new StringTemplateGroup(
                new InputStreamReader(groupInput),
                AngleBracketTemplateLexer.class);

        result.setSuperGroup(themeGroup);

        return result;
    }

    /**
     * Configures given <code>StringTemplate</code> instance.
     * @param stringTemplate such template.
     * @precondition stringTemplate != null
     */
    protected void configure(final StringTemplate stringTemplate)
    {
        stringTemplate.setPassThroughAttributes(true);
        stringTemplate.setLintMode(true);
    }

    /**
     * Retrieves the template in given group.
     * @param group the StringTemplate group.
     * @return the template.
     * @precondition group != null
     */
    protected StringTemplate retrieveTemplate(final StringTemplateGroup group)
    {
        StringTemplate result = null;
        
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
    protected int retrieveYear(final Calendar calendar)
    {
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Creates a timestamp.
     * @return such timestamp.
     */
    protected String createTimestamp()
    {
        return createTimestamp(new Date(), TIMESTAMP_FORMATTER);
    }
    
    /**
     * Formats given date.
     * @param date the date.
     * @param formatter the formatter.
     * @return the formatted date.
     * @precondition date != null
     * @precondition formatter != null
     */
    protected String createTimestamp(
        final Date date, final DateFormat formatter)
    {
        return formatter.format(date);
    }

    /**
     * Generates the source code.
     * @return such output.
     * @throws InvalidTemplateException if the template cannot be generated.
     */
    public String generate()
      throws  InvalidTemplateException
    {
        logHeader(buildHeader());

        return generateOutput();
    }

    /**
     * Logs a custom header.
     * @param header the header.
     */
    protected void logHeader(final String header)
    {
        Log t_Log = UniqueLogFactory.getLog(getClass());
        
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
     * Generates the actual source code.
     * @return such output. 
     * @throws InvalidTemplateException if the template cannot be generated.
     */
    protected abstract String generateOutput()
      throws  InvalidTemplateException;

    /**
     * Normalizes given value, in lower-case.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such output.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String normalizeLowercase(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.normalizeLowercase(value);
    }

    /**
     * Capitalizes given value.
     * @param value the value.
     * @param decorationUtils the <code>DecorationUtils</code> instance.
     * @return such output.
     * @precondition value != null
     * @precondition decorationUtils != null
     */
    protected String capitalize(
        final String value, final DecorationUtils decorationUtils)
    {
        return decorationUtils.capitalize(value);
    }
}
