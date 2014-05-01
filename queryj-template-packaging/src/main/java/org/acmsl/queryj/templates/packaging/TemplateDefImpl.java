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
 * Filename: TemplateDefImpl.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Trivial TemplateDef implementation as a pojo.
 *
 * Date: 2013/08/14
 * Time: 09:25
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing QueryJ Core classes.
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

/*
 * Importing JDK classes.
 */
import java.io.File;

/**
 * Trivial TemplateDef implementation as a pojo.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/14 09:25
 */
@ThreadSafe
public class TemplateDefImpl
    implements TemplateDef<String>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 3072766515558433392L;

    /**
     * The name.
     */
    private String m__strName;

    /**
     * The type.
     */
    private TemplateDefType m__Type;

    /**
     * The output.
     */
    private TemplateDefOutput m__Output;

    /**
     * The filename builder.
     */
    private String m__strFilenameBuilder;

    /**
     * The package name.
     */
    private String m__strPackageName;

    /**
     * The file.
     */
    private File m__File;

    /**
     * Whether the template def is disabled or not.
     */
    private boolean m__bDisabled;

    /**
     * Creates an instance using given information.
     * @param name the name.
     * @param type the type.
     * @param output the output.
     * @param filenameBuilder the builder.
     * @param packageName the package name.
     * @param file the file.
     * @param disabled whether the template def is marked as disabled.
     */
    public TemplateDefImpl(
        @NotNull final String name,
        @NotNull final TemplateDefType type,
        @NotNull final TemplateDefOutput output,
        @NotNull final String filenameBuilder,
        @NotNull final String packageName,
        @NotNull final File file,
        final boolean disabled)
    {
        immutableSetName(name);
        immutableSetType(type);
        immutableSetOutput(output);
        immutableSetFilenameBuilder(filenameBuilder);
        immutableSetPackageName(packageName);
        immutableSetFile(file);
        immutableSetDisabled(disabled);
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    protected final void immutableSetName(@NotNull final String name)
    {
        this.m__strName = name;
    }

    /**
     * Specifies the name.
     * @param name the name.
     */
    @SuppressWarnings("unused")
    protected void setName(@NotNull final String name)
    {
        immutableSetName(name);
    }

    /**
     * Retrieves the name.
     * @return such name.
     */
    @NotNull
    @Override
    public String getName()
    {
        return this.m__strName;
    }

    /**
     * Specifies the type.
     * @param type the template def type.
     */
    protected final void immutableSetType(@NotNull final TemplateDefType type)
    {
        this.m__Type = type;
    }

    /**
     * Specifies the type.
     * @param type the template def type.
     */
    @SuppressWarnings("unused")
    protected void setType(@NotNull final TemplateDefType type)
    {
        immutableSetType(type);
    }

    /**
     * Retrieves the type.
     * @return such information.
     */
    @NotNull
    @Override
    public TemplateDefType getType()
    {
        return this.m__Type;
    }

    /**
     * Specifies the output.
     * @param output the template def output.
     */
    protected final void immutableSetOutput(@NotNull final TemplateDefOutput output)
    {
        this.m__Output = output;
    }

    /**
     * Specifies the output.
     * @param output the template def output.
     */
    @SuppressWarnings("unused")
    protected void setOutput(@NotNull final TemplateDefOutput output)
    {
        immutableSetOutput(output);
    }

    /**
     * Retrieves the output.
     * @return such information.
     */
    @NotNull
    @Override
    public TemplateDefOutput getOutput()
    {
        return this.m__Output;
    }

    /**
     * Specifies the filename builder expression.
     * @param expression the expression.
     */
    protected final void immutableSetFilenameBuilder(@NotNull final String expression)
    {
        this.m__strFilenameBuilder = expression;
    }

    /**
     * Specifies the filename builder expression.
     * @param expression the expression.
     */
    @SuppressWarnings("unused")
    protected void setFilenameBuilder(@NotNull final String expression)
    {
        immutableSetFilenameBuilder(expression);
    }

    /**
     * Retrieves the filename builder expression.
     * @return such expression.
     */
    @NotNull
    @Override
    public String getFilenameBuilder()
    {
        return this.m__strFilenameBuilder;
    }

    /**
     * Specifies the package name.
     * @param name such name.
     */
    protected final void immutableSetPackageName(@NotNull final String name)
    {
        this.m__strPackageName = name;
    }

    /**
     * Specifies the package name.
     * @param name such name.
     */
    @SuppressWarnings("unused")
    protected void setPackageName(@NotNull final String name)
    {
        immutableSetPackageName(name);
    }

    /**
     * Retrieves the package name.
     * @return such name.
     */
    @NotNull
    @Override
    public String getPackageName()
    {
        return this.m__strPackageName;
    }

    /**
     * Specifies the file.
     * @param file the file.
     */
    protected final void immutableSetFile(@NotNull final File file)
    {
        this.m__File = file;
    }

    /**
     * Specifies the file.
     * @param file the file.
     */
    @SuppressWarnings("unused")
    protected void setFile(@NotNull final File file)
    {
        immutableSetFile(file);
    }

    /**
     * Retrieves the file.
     * @return such file.
     */
    @Override
    @NotNull
    public File getFile()
    {
        return this.m__File;
    }

    /**
     * Specifies whether the template def is disabled.
     * @param disabled whether the template def is disabled.
     */
    protected final void immutableSetDisabled(final boolean disabled)
    {
        this.m__bDisabled = disabled;
    }

    /**
     * Specifies whether the template def is disabled.
     * @param disabled whether the template def is disabled.
     */
    @SuppressWarnings("unused")
    protected void setDisabled(final boolean disabled)
    {
        immutableSetDisabled(disabled);
    }

    /**
     * Checks whether the template def is disabled.
     * @return such condition.
     */
    @Override
    public boolean isDisabled()
    {
        return this.m__bDisabled;
    }

    /**
     * Retrieves the filename rule.
     * @return such rule.
     */
    @Override
    @NotNull
    public String getFilenameRule()
    {
        return new DecoratedString(getName()).getNormalized().getLowercased().getValue();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + TemplateDefImpl.class.getName() + '"'
            + ", \"output\": \"" + m__Output + '"'
            + ", \"name\": \"" + m__strName + '"'
            + ", \"type\": \"" + m__Type  + '"'
            + ", \"filenameBuilder\": \"" + m__strFilenameBuilder + '"'
            + ", \"packageName\": \"" + m__strPackageName + '"'
            + ", \"file\": \"" + m__File.getAbsolutePath() + '"'
            + ", \"disabled\": " + m__bDisabled + " }";
    }
}
