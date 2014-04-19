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
 * Filename: GlobalTemplateContextImpl.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Straightforward implementation of GlobalTemplateContext.
 *
 * Date: 2013/09/15
 * Time: 06:49
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.QueryJCommand;

/*
 * Importing QueryJ Template Packaging classes.
 */
import org.acmsl.queryj.templates.packaging.exceptions.TemplateDefNotAvailableException;

/*
 * Importing Apache Commons Lang classes.
 */
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.util.List;

/**
 * Straightforward implementation of {@link GlobalTemplateContext}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/09/15 06:49
 */
@ThreadSafe
public class GlobalTemplateContextImpl
    extends AbstractTemplatePackagingContext
    implements GlobalTemplateContext
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -1563568480106519552L;

    /**
     * Creates a new global context.
     * @param fileName the file name.
     * @param outputDir the output dir.
     * @param templateDefs the template definitions.
     */
    public GlobalTemplateContextImpl(
        @NotNull final String templateName,
        @NotNull final String packageName,
        @NotNull final String fileName,
        @NotNull final File outputDir,
        @NotNull final List<TemplateDef<String>> templateDefs,
        @NotNull final QueryJCommand command)
    {
        super(fileName, command);
        immutableSetValue(buildTemplateNameKey(), templateName, command);
        immutableSetValue(buildPackageNameKey(), packageName, command);
        immutableSetValue(buildTemplateDefsKey(), templateDefs, command);
        immutableSetValue(buildFileNameKey(), fileName, command);
        immutableSetValue(buildOutputDirKey(), outputDir, command);
    }

    /**
     * Retrieves the list of {@link TemplateDef}s.
     * @return such list.
     */
    @NotNull
    @Override
    public List<TemplateDef<String>> getTemplateDefs()
    {
        return getListValue(buildTemplateDefsKey(), getCommand(), new TemplateDefNotAvailableException());
    }

    /**
     * Builds a key to retrieve the {@link TemplateDef}s.
     * @return such key.
     */
    @NotNull
    protected String buildTemplateDefsKey()
    {
        return "templateDefs@" + hashCode();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return
            new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(GlobalTemplateContextImpl.class.getName())
                .append(this.getCommand())
                .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable final Object obj)
    {
        final boolean result;

        if (obj == null)
        {
            result = false;
        }
        else if (getClass() != obj.getClass())
        {
            result = false;
        }
        else if (obj instanceof GlobalTemplateContextImpl)
        {
            final GlobalTemplateContextImpl other = (GlobalTemplateContextImpl) obj;

            result =
                new EqualsBuilder()
                    .appendSuper(super.equals(obj))
                    .append(this.getCommand(), other.getCommand())
                    .isEquals();
        }
        else
        {
            result = false;
        }

        return result;
    }
}
