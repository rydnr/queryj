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
 * Filename: TemplateDef.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Represents template definitions: all additional metadata
 *              associated to a template, needed to generate sources.
 *
 * Date: 2013/08/14
 * Time: 09:01
 *
 */
package org.acmsl.queryj.templates.packaging;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.io.Serializable;
import java.util.Map;

/**
 * Represents template definitions: all additional metadata associated to
 * a template, needed to generate sources.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2013/08/14 09:01
 */
@ThreadSafe
public interface TemplateDef<S>
    extends Serializable
{
    /**
     * Retrieves the name.
     * @return such name.
     */
    @NotNull
    S getName();

    /**
     * Retrieves the type.
     * @return such information.
     */
    @NotNull
    TemplateDefType getType();

    /**
     * Retrieves the output.
     * @return such information.
     */
    @NotNull
    TemplateDefOutput getOutput();

    /**
     * Retrieves the filename builder expression.
     * @return such expression.
     */
    @NotNull
    S getFilenameBuilder();

    /**
     * Retrieves the package name.
     * @return such name.
     */
    @NotNull
    S getPackageName();

    /**
     * Retrieves the actual template definition file.
     * @return the file.
     */
    @Nullable
    File getFile();

    /**
     * Checks whether this template def is disabled or not.
     * @return such information.
     */
    boolean isDisabled();

    /**
     * Checks whether this template def is being debugged or not.
     * @return such information.
     */
    boolean isDebug();

    /**
     * Retrieves the filename rule.
     * @return such rule.
     */
    @NotNull
    public String getFilenameRule();

    /**
     * Retrieves additional metadata.
     * @return such information.
     */
    @NotNull
    public Map<String, S> getMetadata();

    /**
     * Retrieves the name of the template def itself.
     * @return such information.
     */
    @NotNull
    public String getDefName();
}
