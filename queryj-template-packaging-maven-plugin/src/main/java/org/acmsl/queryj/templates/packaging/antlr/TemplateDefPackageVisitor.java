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
 * Filename: TemplateDefPackageVisitor.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: ANTLR4 visitor to retrieve the package definition.
 *
 * Date: 2013/08/13
 * Time: 17:51
 *
 */
package org.acmsl.queryj.templates.packaging.antlr;

/*
 * Importing ANTLR-generated classes.
 */
import org.acmsl.queryj.templates.packaging.antlr.TemplateDefParser.PackageRuleContext;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.NotThreadSafe;

/**
 * ANTLR4 visitor to retrieve the package definition.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/13 17:51
 */
@NotThreadSafe
public class TemplateDefPackageVisitor
    extends TemplateDefBaseVisitor<String>
{
    /**
     * The package name.
     */
    private String m__strPackageName;

    /**
     * Specifies the package name.
     * @param packageName the package name.
     */
    protected final void immutableSetPackageName(@NotNull final String packageName)
    {
        this.m__strPackageName = packageName;
    }

    /**
     * Specifies the package name.
     * @param packageName the package name.
     */
    protected void setPackageName(@NotNull final String packageName)
    {
        immutableSetPackageName(packageName);
    }

    /**
     * Retrieves the package name.
     * @return such information.
     */
    @NotNull
    public String getPackageName()
    {
        return this.m__strPackageName;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override
    public String visitPackageRule(@NotNull final PackageRuleContext ctx)
    {
        setPackageName(ctx.getChild(2).getText());

        return super.visitPackageRule(ctx);
    }

    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"" + TemplateDefPackageVisitor.class.getName() + '"'
            + ", \"packageName\": \"" + m__strPackageName + "\" }";
    }
}
