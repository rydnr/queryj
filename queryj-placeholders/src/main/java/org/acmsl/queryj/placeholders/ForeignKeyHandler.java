/*
                        QueryJ Placeholders

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
 * Filename: ForeignKeyHandler.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Is able to resolve ForeignKey placeholders in templates.
 *
 * Date: 5/24/12
 * Time: 3:56 AM
 *
 */
package org.acmsl.queryj.placeholders;

/*
 * Importing QueryJ Core classes.
 */
import org.acmsl.queryj.metadata.CachingForeignKeyDecorator;
import org.acmsl.queryj.metadata.ForeignKeyDecorator;
import org.acmsl.queryj.metadata.vo.ForeignKey;
import org.acmsl.queryj.api.PerForeignKeyTemplateContext;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Is able to resolve {@link ForeignKey} placeholders in templates.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 * @since 3.0
 * Created: 2012/05/24
 */
@SuppressWarnings("unused")
@ThreadSafe
public class ForeignKeyHandler
    extends AbstractTemplateContextFillHandler<PerForeignKeyTemplateContext, ForeignKeyDecorator>
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = -8241292451651391465L;

    /**
     * Creates a new {@link ForeignKeyHandler} to resolve "foreign_key" placeholders using
     * given {@link org.acmsl.queryj.api.PerForeignKeyTemplateContext context}.
     * @param context the {@link org.acmsl.queryj.api.PerForeignKeyTemplateContext context}
     */
    @SuppressWarnings("unused")
    public ForeignKeyHandler(@NotNull final PerForeignKeyTemplateContext context)
    {
        super(context);
    }

    /**
     * Returns "foreign_key".
     * @return such placeholder.
     */
    @NotNull
    @Override
    public String getPlaceHolder()
    {
        return Literals.FOREIGN_KEY;
    }

    /**
     * Retrieves the {@link ForeignKey} from given {@link org.acmsl.queryj.api.PerForeignKeyTemplateContext context}.
     * @return such value.
     */
    @NotNull
    @Override
    protected ForeignKeyDecorator getValue(@NotNull final PerForeignKeyTemplateContext context)
    {
        return
            new CachingForeignKeyDecorator(
                context.getForeignKey(),
                context.getMetadataManager(),
                context.getDecoratorFactory(),
                context.getCustomSqlProvider());
    }
}
