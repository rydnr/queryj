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
 * Filename: UntrimmedCallMethodRule.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Custom implementation of Digester's CallMethodRule, so that
 *              inner text body is not trimmed.
 *
 */
package org.acmsl.queryj.customsql.xml;

/*
 * Importing some Digester classes.
 */
import org.apache.commons.digester.CallMethodRule;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;

/**
 * Custom implementation of Digester's CallMethodRule, so that
 * inner text body is not trimmed.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class UntrimmedCallMethodRule
    extends  CallMethodRule
{
    /**
     * Constructs a <i>call method</i> rule with the specified method name.
     * The parameter types (if any) default to java.lang.String.
     * @param methodName method name of the parent method to call.
     * @param paramCount the number of parameters to collect, or
     * zero for a single argument from the body of this element.
     */
    public UntrimmedCallMethodRule(@NotNull final String methodName, final int paramCount)
    {
        super(methodName, paramCount);
    }

    /**
     * Constructs a <i>call method</i> rule with the specified method name.  
     * The method must accept no parameters.
     * @param methodName method name of the parent method to call.
     */
    public UntrimmedCallMethodRule(@NotNull final String methodName)
    {
        super(methodName);
    }

    /**
     * Constructs a <i>call method</i> rule with the specified method name and
     * parameter types. If <code>paramCount</code> is set to zero the rule
     * will use the body of this element as the single argument of the
     * method, unless <code>paramTypes</code> is null or empty, in this
     * case the rule will call the specified method with no arguments.
     * @param methodName method name of the parent method to call
     * @param paramCount the number of parameters to collect, or
     * zero for a single argument from the body of ths element
     * @param paramTypes the Java class names of the arguments
     * (if you wish to use a primitive type, specify the corresponding
     * Java wrapper class instead, such as <code>java.lang.Boolean</code>
     * for a <code>boolean</code> parameter)
     */
    @SuppressWarnings("unused")
    public UntrimmedCallMethodRule(
        @NotNull final String methodName,
        final int paramCount, 
        @NotNull final String paramTypes[])
    {
        super(methodName, paramCount, paramTypes);
    }

    /**
     * Constructs a <i>call method</i> rule with the specified method name and
     * parameter types. If <code>paramCount</code> is set to zero the rule
     * will use the body of this element as the single argument of the
     * method, unless <code>paramTypes</code> is null or empty, in this
     * case the rule will call the specified method with no arguments.
     * @param methodName method name of the parent method to call
     * @param paramCount the number of parameters to collect, or
     * zero for a single argument from the body of ths element
     * @param paramTypes the Java classes that represent the
     * parameter types of the method arguments
     * (if you wish to use a primitive type, specify the corresponding
     * Java wrapper class instead, such as <code>java.lang.Boolean.TYPE</code>
     * for a <code>boolean</code> parameter)
     */
    @SuppressWarnings("unused")
    public UntrimmedCallMethodRule(
        @NotNull final String methodName,
        final int paramCount, 
        @NotNull final Class paramTypes[])
    {
        super(methodName, paramCount, paramTypes);
    }

    /**
     * Specifies the parameter count.
     * @param count such count.
     */
    @SuppressWarnings("unused")
    protected void setParamCount(final int count)
    {
        super.paramCount = count;
    }

    /**
     * Retrieves the parameter count.
     * @return such value.
     */
    public int getParamCount()
    {
        return super.paramCount;
    }

    /**
     * Specifies the body text.
     * @param text the text.
     */
    @SuppressWarnings("unused")
    protected void setBodyText(final String text)
    {
        super.bodyText = text;
    }

    /**
     * Retrieves the body text.
     * @return such information.
     */
    public String getBodyText()
    {
        return super.bodyText;
    }

    /**
     * Processes the body text of this element.
     * @param body the body text of this element
     */
    @SuppressWarnings("unused")
    public void body(@NotNull final String body)
    {
        body(body, getParamCount());
    }

    /**
     * Processes the body text of this element.
     * @param body the body text of this element
     * @param paramCount the parameter count.
     */
    @SuppressWarnings("unused")
    protected void body(@NotNull final String body, final int paramCount)
    {
        if (paramCount == 0)
        {
            super.bodyText = body;
        }
    }
}
