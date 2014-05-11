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
 * Description: Visits classDeclaration rules in Java.g4 grammar.
 *
 * Date: 7/8/13
 * Time: 5:26 AM
 *
 */
package org.acmsl.queryj.test.antlr4;

/*
 * Importing QueryJ Test classes.
 */
import gherkin.lexer.Ja;
import org.acmsl.queryj.test.antlr4.JavaParser.ClassDeclarationContext;

/*
 * Importing checkthread.org annotations.
 */
import org.acmsl.queryj.test.antlr4.JavaParser.NormalInterfaceDeclarationContext;
import org.checkthread.annotations.ThreadSafe;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Visits classDeclaration rules in Java.g4 grammar.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/07/06
 */
@ThreadSafe
public class JavaRootClassNameVisitor
    extends JavaBaseVisitor<String>
{
    /**
     * The name of the root class in the file.
     */
    @Nullable
    private String m__strRootClass;

    /**
     * Specifies the root class.
     * @param value the root class name.
     */
    protected final void immutableSetRootClass(@NotNull final String value)
    {
        this.m__strRootClass = value;
    }

    /**
     * Specifies the name of the root class.
     * @param value the root class name.
     */
    @SuppressWarnings("unused")
    protected void setRootClass(@NotNull final String value)
    {
        immutableSetRootClass(value);
    }

    /**
     * Retrieves the name of the root class.
     * @return such name.
     */
    @Nullable
    protected final String immutableGetRootClass()
    {
        return this.m__strRootClass;
    }

    /**
     * Retrieves the name of the root class.
     * @return such name.
     */
    @Nullable
    public String getRootClass()
    {
        return immutableGetRootClass();
    }

    /**
     * Visits the parser tree within the <pre>classDeclaration</pre> rule.
     * @param context the parse context.
     * @return the package name.
     */
    @NotNull
    @Override
    public String visitClassDeclaration(@NotNull final ClassDeclarationContext context)
    {
        setRootClass(context.getChild(1).getText());

        return super.visitClassDeclaration(context);
    }

    /**
     * Visits the parser tree within the <pre>packageDeclaration</pre> rule.
     * @param context the parse context.
     * @return the package name.
     */
    @NotNull
    @Override
    public String visitNormalInterfaceDeclaration(@NotNull final NormalInterfaceDeclarationContext context)
    {
        setRootClass(context.getChild(1).getText());

        return super.visitNormalInterfaceDeclaration(context);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return
              "{ \"class\": \"JavaRootClassNameVisitor\""
            + ", \"rootClass\": \"" + m__strRootClass + '\''
            + ", \"package\": \"" + JavaRootClassNameVisitor.class.getPackage().getName()
            + "\" }";
    }
}
