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
 * Filename: BasePerCustomSqlTemplateWritingHandler.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Writes per-custom-sql templates.
 *
 */
package org.acmsl.queryj.api.handlers;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.api.PerCustomSqlTemplateContext;
import org.acmsl.queryj.api.PerCustomSqlTemplateGenerator;
import org.acmsl.queryj.api.PerCustomSqlTemplate;

/**
 * Writes <i>per-custom-sql</i> templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@SuppressWarnings("unused")
public abstract class BasePerCustomSqlTemplateWritingHandler
       <T extends PerCustomSqlTemplate<C>,
        C extends PerCustomSqlTemplateContext,
        TG extends PerCustomSqlTemplateGenerator<T, C>>
extends    AbstractQueryJTemplateWritingHandler<T, C, TG>
    implements TemplateWritingHandler
{
    /**
     * Creates a <code>BasePerCustomSqlTemplateWritingHandler</code> instance.
     */
    public BasePerCustomSqlTemplateWritingHandler() {}
}
