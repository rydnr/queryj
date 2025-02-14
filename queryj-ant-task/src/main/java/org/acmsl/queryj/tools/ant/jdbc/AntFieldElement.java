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
package org.acmsl.queryj.tools.ant.jdbc;

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
        switch (Literals.valueOf(name.toUpperCase()))
        {
            case NAME:
                setName(value);
                break;
            case TYPE:
                setType(value);
                break;
            case PK:
                setPk(value);
                break;
            case TABLE_NAME:
                setTableName(value);
                break;
            case KEYWORD:
                setKeyword(value);
                break;
            case RETRIEVAL_QUERY:
                setRetrievalQuery(value);
                break;
            default:
                // TODO: Use a custom exception
                throw
                    new BuildException(Literals.ATTRIBUTE_IS_NOT_SUPPORTED.format(name));
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

        switch (Literals.valueOf(name.toUpperCase()))
        {
            case FK:
                result = null; // TODO: new Field();

                @Nullable List<Field> t_cFieldFks = getFieldFks();

                if  (t_cFieldFks == null)
                {
                    t_cFieldFks = new ArrayList<>();
                    setFieldFks(t_cFieldFks);
                }

                t_cFieldFks.add(result);
                break;
            default:
                throw new BuildException(Literals.NO_ELEMENTS_BUT_FK_ARE_SUPPORTED.format(name));
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
