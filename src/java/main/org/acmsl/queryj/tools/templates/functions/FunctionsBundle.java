/*
                        QueryJ

    Copyright (C) 2002  Jose San Leandro Armendariz
                        jsanleandro@yahoo.es
                        chousz@yahoo.com

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
    Contact info: jsanleandro@yahoo.es
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Bundles the complete set of handlers related to TextFunctions
 *              templates.
 *
 */
package org.acmsl.queryj.tools.templates.functions;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.tools.templates.functions.numeric.handlers.NumericFunctionsBundle;
import org.acmsl.queryj.tools.templates.functions.system.handlers.SystemFunctionsBundle;
import org.acmsl.queryj.tools.templates.functions.text.handlers.TextFunctionsBundle;
import org.acmsl.queryj.tools.templates.functions.time.handlers.TimeFunctionsBundle;
import org.acmsl.queryj.tools.templates.handlers.TemplateHandlerBundle;

/**
 * Bundles the complete set of handlers related to TextFunctions templates.
 * @author <a href="mailto:jsanleandro@yahoo.es">Jose San Leandro</a>
 */
public class FunctionsBundle
    extends  TemplateHandlerBundle
{
    /**
     * Builds a bundle with TestFunctions-related handlers.
     */
    public FunctionsBundle()
    {
        super(
            new TemplateHandlerBundle[]
            {
                new TimeFunctionsBundle(),
                new NumericFunctionsBundle(),
                new TextFunctionsBundle(),
                new SystemFunctionsBundle()
            });
    }
}
