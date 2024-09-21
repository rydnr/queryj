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
 * Filename: RefElement.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Represents <ref> information in XML files.
 *
 * Date: 5/25/13
 * Time: 8:45 PM
 *
 */
package cucumber.templates.xml;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/**
 * Represents &lt;ref&gt; information in XML files.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/25
 */
public class RefElement
{
    /**
     * The local information.
     */
    private String m__strLocal;

    /**
     * Creates a RefElement with the following local reference name.
     * @param local the reference name.
     */
    public RefElement(@NotNull final String local)
    {
        immutableSetLocal(local);
    }

    /**
     * Specifies the local reference name.
     * @param local the reference name.
     */
    protected final void immutableSetLocal(@NotNull final String local)
    {
        this.m__strLocal = local;
    }

    /**
     * Specifies the local reference name.
     * @param local the reference name.
     */
    @SuppressWarnings("unused")
    protected void setLocal(@NotNull final String local)
    {
        immutableSetLocal(local);
    }

    /**
     * Retrieves the local reference name.
     * @return the reference name.
     */
    @NotNull
    public String getLocal()
    {
        return this.m__strLocal;
    }

    @Override
    public String toString()
    {
        return "RefElement{local='" + getLocal() + "'}";
    }
}

