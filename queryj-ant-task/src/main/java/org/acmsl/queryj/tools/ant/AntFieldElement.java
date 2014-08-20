//;-*- mode: java -*-
/*
                        QueryJ Core

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
 * Filename: AntFieldElement.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Contains all information inside a "field" XML element in Ant
 *              scripts, under QueryJ task.
 *
 */
package org.acmsl.queryj.tools.ant;

/*
 * Importing some project classes.
 */
import org.acmsl.queryj.metadata.vo.AbstractField;

/*
 * Importing some Ant classes.
 */
import org.acmsl.queryj.metadata.vo.Field;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DynamicConfigurator;

/*
* Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing some JDK classes.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Contains all information inside a "field" XML element in Ant scripts,
 * under QueryJ task.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class AntFieldElement
    extends  AbstractField
    implements  DynamicConfigurator
{
    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 5709131443641728342L;

    /**
     * String literal: "table-name".
     */
    public static final String TABLE_NAME_LITERAL = "table-name";

    /**
     * String literal: "keyword".
     */
    public static final String KEYWORD_LITERAL = "keyword";

    /**
     * String literal: "retrieval-query".
     */
    public static final String RETRIEVAL_QUERY_LITERAL = "retrieval-query";

    /**
     * String literal: "Attribute ".
     */
    static final String ATTRIBUTE_LITERAL = "Attribute ";

    /**
     * The field pk nature.
     */
    @Nullable
    private String m__strPk;

    /**
     * Creates an empty {@code AntFieldElement}.
     */
    public AntFieldElement()
    {
        super("", -1, "", "", "", 1, -1, -1, "", "", "", false, "", false, false, "", "", "", false);
    }

    /**
     * Specifies if the field is a primary key.
     * @param pk such information.
     */
    public void setPk(@Nullable final String pk)
    {
        m__strPk = pk;

        setPk(
            (   (pk != null)
             && (   (pk.trim().toLowerCase().equals("yes")
                 || (pk.trim().toLowerCase().equals("true"))))));
    }

    /**
     * Retrieves if the field is a primary key.
     * @return such information.
     */
    @SuppressWarnings("unused")
    @Nullable
    public String getPk()
    {
        return m__strPk;
    }

    /**
     * Specifies a dynamic attribute.
     * @param name the attribute name.
     * @param value the attribute value.
     */
    @Override
    public void setDynamicAttribute(@NotNull final String name, @NotNull final String value)
    {
        if  ("name".equals(name))
        {
            setName(value);
        }
        else if  ("type".equals(name))
        {
            setType(value);
        }
        else if  ("pk".equals(name))
        {
            setPk(value);
        }
        else if  (TABLE_NAME_LITERAL.equals(name))
        {
            setTableName(value);
        }
        else if  (KEYWORD_LITERAL.equals(name))
        {
            setKeyword(value);
        }
        else if  (RETRIEVAL_QUERY_LITERAL.equals(name))
        {
            setRetrievalQuery(value);
        }
        else 
        {
            // TODO: Use a custom exception
            throw
                new BuildException(ATTRIBUTE_LITERAL + name + " is not supported");
        }
    }

    /**
     * Creates a dynamic element.
     * @param name the element's name.
     * @return the object.
     */
    @Override
    @Nullable
    public Object createDynamicElement(final String name)
    {
        final @Nullable Field result;

        if  ("fk".equals(name)) 
        {
            result = null; //new AntFieldFkElement();

            @Nullable List<Field> t_cFieldFks = getFieldFks();

            if  (t_cFieldFks == null)
            {
                t_cFieldFks = new ArrayList<>();
                setFieldFks(t_cFieldFks);
            }

            t_cFieldFks.add(result);
        }
        else 
        {
            throw new BuildException(name + QueryJTask.ELEMENTS_ARE_NOT_SUPPORTED);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString()
    {
        return "AntFieldElement{ pk='" + m__strPk + "' }";
    }
}
