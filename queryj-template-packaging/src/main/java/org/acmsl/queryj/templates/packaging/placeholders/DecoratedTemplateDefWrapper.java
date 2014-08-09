/*
                        QueryJ Template Packaging

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
 * Filename: DecoratedTemplateDefWrapper.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Wraps a TemplateDef to provide decorated versions of its
 *              information, to be used in templates.
 *
 * Date: 2013/09/02
 * Time: 07:41
 *
 */
package org.acmsl.queryj.templates.packaging.placeholders;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.TemplateDef;
import org.acmsl.queryj.templates.packaging.TemplateDefOutput;
import org.acmsl.queryj.templates.packaging.TemplateDefType;

/*
 * Importing QueryJ-Placeholders classes.
 */
import org.acmsl.queryj.metadata.DecoratedString;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Wraps a {@link TemplateDef} to provide decorated versions of its information, to be used in templates.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/02 07:41
 */
@ThreadSafe
public class DecoratedTemplateDefWrapper
    implements TemplateDef<DecoratedString>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 1996073167613801985L;

    /**
     * The wrapped instance.
     */
    private TemplateDef<String> m__TemplateDef;

    /**
     * Creates a new wrapper for given instance.
     * @param templateDef the instance to wrap.
     */
    public DecoratedTemplateDefWrapper(@NotNull final TemplateDef<String> templateDef)
    {
        immutableSetTemplateDef(templateDef);
    }

    /**
     * Specifies the instance to wrap.
     * @param def such instance.
     */
    protected final void immutableSetTemplateDef(@NotNull final TemplateDef<String> def)
    {
        this.m__TemplateDef = def;
    }

    /**
     * Specifies the instance to wrap.
     * @param def such instance.
     */
    @SuppressWarnings("unused")
    protected void setTemplateDef(@NotNull final TemplateDef<String> def)
    {
        immutableSetTemplateDef(def);
    }

    /**
     * Retrieves the wrapped instance.
     * @return such instance.
     */
    @NotNull
    public TemplateDef<String> getTemplateDef()
    {
        return this.m__TemplateDef;
    }

    /**
     * Retrieves a decorated version of the template def name.
     * @return such information.
     */
    @NotNull
    @Override
    public DecoratedString getName()
    {
        return getName(getTemplateDef());
    }

    /**
     * Retrieves a decorated version of the template def name.
     * @param def the {@link TemplateDef}.
     * @return such information.
     */
    @NotNull
    protected DecoratedString getName(@NotNull final TemplateDef<String> def)
    {
        return new DecoratedString(def.getName()).getNoExtension();
    }

    /**
     * Retrieves a decorated version of the template def name.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @NotNull
    public DecoratedString getRawName()
    {
        return getRawName(getTemplateDef());
    }

    /**
     * Retrieves a decorated version of the template def name.
     * @param def the {@link TemplateDef}.
     * @return such information.
     */
    @NotNull
    protected DecoratedString getRawName(@NotNull final TemplateDef<String> def)
    {
        return new DecoratedString(def.getName());
    }

    /**
     * Retrieves the actual template definition file.
     * @return the file.
     */
    @Nullable
    @Override
    public File getFile()
    {
        return getFile(getTemplateDef());
    }

    /**
     * Retrieves the actual template definition file.
     * @param def the {@link TemplateDef}.
     * @return the file.
     */
    @Nullable
    protected File getFile(@NotNull final TemplateDef<String> def)
    {
        return def.getFile();
    }

    /**
     * Retrieves the name of the template def itself.
     * @return such information.
     */
    @Override
    @NotNull
    public String getDefName()
    {
        return getDefName(getTemplateDef());
    }

    /**
     * Retrieves the name of the template def itself.
     * @param templateDef the {@link TemplateDef}.
     * @return such information.
     */
    @NotNull
    protected String getDefName(@NotNull final TemplateDef<String> templateDef)
    {
        return templateDef.getDefName();
    }

    /**
     * Retrieves the filename builder expression.
     * @return such expression.
     */
    @NotNull
    @Override
    public DecoratedString getFilenameBuilder()
    {
        return getFilenameBuilder(getTemplateDef());
    }

    /**
     * Retrieves the filename builder expression.
     * @param def the {@link TemplateDef}.
     * @return such expression.
     */
    @NotNull
    protected DecoratedString getFilenameBuilder(@NotNull final TemplateDef<String> def)
    {
        return new DecoratedString(def.getFilenameBuilder());
    }

    /**
     * Retrieves the output.
     * @return such information.
     */
    @NotNull
    @Override
    public TemplateDefOutput getOutput()
    {
        return getOutput(getTemplateDef());
    }

    /**
     * Retrieves the output.
     * @param def the {@link TemplateDef}.
     * @return such information.
     */
    @NotNull
    protected TemplateDefOutput getOutput(@NotNull final TemplateDef<String> def)
    {
        return def.getOutput();
    }

    /**
     * Retrieves the package name.
     * @return such name.
     */
    @NotNull
    @Override
    public DecoratedString getPackageName()
    {
        return getPackageName(getTemplateDef());
    }

    /**
     * Retrieves the package name.
     * @param def the {@link TemplateDef}.
     * @return such name.
     */
    @NotNull
    protected DecoratedString getPackageName(@NotNull final TemplateDef<String> def)
    {
        return new DecoratedString(def.getPackageName());
    }

    /**
     * Retrieves the type.
     * @return such information.
     */
    @NotNull
    @Override
    public TemplateDefType getType()
    {
        return getType(getTemplateDef());
    }

    /**
     * Retrieves the type.
     * @param def the {@link TemplateDef}.
     * @return such information.
     */
    @NotNull
    protected TemplateDefType getType(@NotNull final TemplateDef<String> def)
    {
        return def.getType();
    }

    /**
     * Checks whether given template def is disabled.
     * @return {@code true} in such case.
     */
    @Override
    public boolean isDisabled()
    {
        return isDisabled(getTemplateDef());
    }

    /**
     * Checks whether given template def is disabled.
     * @param templateDef the wrapped {@link TemplateDef}.
     * @return {@code true} in such case.
     */
    protected boolean isDisabled(@NotNull final TemplateDef<String> templateDef)
    {
        return templateDef.isDisabled();
    }

    /**
     * Checks whether given template def is marked as being debugged or not.
     * @return {@code true} in such case.
     */
    @Override
    public boolean isDebug()
    {
        return isDebug(getTemplateDef());
    }

    /**
     * Checks whether given template def is marked as being debugged or not.
     * @param templateDef the wrapped {@link TemplateDef}.
     * @return {@code true} in such case.
     */
    protected boolean isDebug(@NotNull final TemplateDef<String> templateDef)
    {
        return templateDef.isDebug();
    }

    /**
     * Retrieves the filename rule.
     * @return such rule.
     */
    @NotNull
    @Override
    public String getFilenameRule()
    {
        return getFilenameRule(getTemplateDef());
    }

    /**
     * Retrieves the filename rule.
     * @param templateDef the wrapped {@link TemplateDef}.
     * @return such rule.
     */
    @NotNull
    protected String getFilenameRule(@NotNull final TemplateDef<String> templateDef)
    {
        return templateDef.getFilenameRule();
    }

    /**
     * Retrieves additional metadata.
     * @return such information.
     */
    @NotNull
    @Override
    public Map<String, DecoratedString> getMetadata()
    {
        return getMetadata(getTemplateDef());
    }

    /**
     * Retrieves additional metadata.
     * @param templateDef the wrapped {@link TemplateDef}.
     * @return such information.
     */
    @NotNull
    protected Map<String, DecoratedString> getMetadata(@NotNull final TemplateDef<String> templateDef)
    {
        @NotNull final Map<String, DecoratedString> result;

        @NotNull final Map<String, String> metadata = templateDef.getMetadata();

        result = new HashMap<>(metadata.size());

        for (@NotNull final Entry<String, String> entry : metadata.entrySet())
        {
            result.put(entry.getKey(), new DecoratedString(entry.getValue()));
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"templateDef\": " + this.m__TemplateDef
            + ", \"class\": \"" + DecoratedTemplateDefWrapper.class.getName() +  "\" }";
    }
}
