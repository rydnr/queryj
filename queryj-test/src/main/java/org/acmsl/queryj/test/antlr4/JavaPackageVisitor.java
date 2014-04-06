/*
                        QueryJ Test

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
 *
 * Filename: JavaPackageVisitor.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Visits packageDeclaration rules in Java.g4 grammar.
 *
 * Date: 7/8/13
 * Time: 5:20 AM
 *
 */
package org.acmsl.queryj.test.antlr4;

/*
 * Importing QueryJ Test classes.
 */
import org.acmsl.queryj.test.antlr4.JavaParser.PackageDeclarationContext;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Visits packageDeclaration rules in Java.g4 grammar.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/07/06
 */
public class JavaPackageVisitor
    extends JavaBaseVisitor<String>
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
     * @return the package name.
     */
    @Nullable
    protected String immutableGetPackageName()
    {
        return this.m__strPackageName;
    }

    /**
     * Retrieves the package name.
     * @return the package name.
     */
    @Nullable
    public String getPackageName()
    {
        return immutableGetPackageName();
    }

    /**
     * Visits the parser tree within the <pre>packageDeclaration</pre> rule.
     * @param context the parse context.
     * @return the package name.
     */
    @NotNull
    @Override
    public String visitPackageDeclaration(@NotNull final PackageDeclarationContext context)
    {
        setPackageName(context.getChild(1).getText());

        return super.visitPackageDeclaration(context);
    }

    @NotNull
    @Override
    public String toString()
    {
        return "{ 'class': 'JavaPackageVisitor', " +
               "'packageName': '" + m__strPackageName + "' }";
    }
}
