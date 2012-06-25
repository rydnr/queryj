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
 * Filename: VOBundle.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Wraps all ValueObject-related templates.
 *
 * Date: 6/20/12
 * Time: 4:06 AM
 *
 */
package org.acmsl.queryj.templates.valueobject;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.templates.handlers.TemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.BaseValueObjectTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.CustomBaseValueObjectTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.CustomValueObjectFactoryTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.CustomValueObjectImplTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.CustomValueObjectTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.ValueObjectFactoryTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.ValueObjectImplTemplateHandlerBundle;
import org.acmsl.queryj.templates.valueobject.handlers.ValueObjectTemplateHandlerBundle;

/**
 * Wraps all ValueObject-related templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 2012/06/20
 */
public class VOBundle
    extends TemplateHandlerBundle
{
    /**
     * Builds a bundle with ValueObject-related handlers.
     * @param includeMock whether to include Mock implementations.
     * @param includeXML whether to include XML implementations.
     */
    @SuppressWarnings("unused")
    public VOBundle(final boolean includeMock, final boolean includeXML)
    {
        super(
            new TemplateHandlerBundle[]
            {
                new ValueObjectTemplateHandlerBundle(),
                new BaseValueObjectTemplateHandlerBundle(),
                new ValueObjectFactoryTemplateHandlerBundle(),
                new ValueObjectImplTemplateHandlerBundle(),
                new CustomValueObjectTemplateHandlerBundle(),
                new CustomBaseValueObjectTemplateHandlerBundle(),
                new CustomValueObjectImplTemplateHandlerBundle(),
                new CustomValueObjectFactoryTemplateHandlerBundle()
        });
    }
}
