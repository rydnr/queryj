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
 * Filename: DataAccessContextLocalTestHelper.java
 *
 * Author: Jose San Leandro Armendariz (chous)
 *
 * Description: Provides some logic for testing DataAccessContextLocal
 *              templates.
 *
 * Date: 5/25/13
 * Time: 8:50 PM
 *
 */
package cucumber.templates.xml;

/*
 * Importing project classes.
 */
import org.acmsl.queryj.customsql.xml.UntrimmedCallMethodRule;

/*
 * Importing ACM S.L. classes.
 */
import org.acmsl.commons.patterns.Singleton;

/*
 * Importing Digester classes
 */
import org.apache.commons.digester.Digester;

/*
 * Importing Jetbrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing SAX classes.
 */
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*
 * Importing JDK classes.
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Provides some logic for testing DataAccessContextLocal templates.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro</a>
 * @since 2013/05/25
 */
public class DataAccessContextLocalTestHelper
    implements Singleton
{
    /**
     * Singleton implementation to avoid double-locking check.
     */
    protected static final class DataAccessContextLocalTestHelperSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final DataAccessContextLocalTestHelper SINGLETON = new DataAccessContextLocalTestHelper();
    }

    /**
     * Retrieves the singleton instance.
     * @return such instance.
     */
    @NotNull
    public static DataAccessContextLocalTestHelper getInstance()
    {
        return DataAccessContextLocalTestHelperSingletonContainer.SINGLETON;
    }

    /**
     * Creates and configures a new Digester instance.
     * @return such instance.
     */
    @NotNull
    public Digester buildDataAccessContextLocalDigester()
    {
        @NotNull final Digester result = new Digester();

        // To avoid fetching external DTDs
        result.setEntityResolver(
            new EntityResolver()
            {
                @Override
                public InputSource resolveEntity(final String publicId, final String systemId)
                    throws SAXException, IOException
                {
                    return new InputSource(new ByteArrayInputStream("".getBytes()));
                }
            }
        );
        // <beans>

        //   <bean>
        result.addFactoryCreate(
            "beans/bean",
            new BeanElementFactory());

        //     <property>
        result.addFactoryCreate(
            "beans/bean/property",
            new PropertyElementFactory());

        //     <value>
        result.addRule(
            "beans/bean/property/value",
            new UntrimmedCallMethodRule("setValue", 0));
        //     </value>

        //     <ref>
        result.addFactoryCreate(
            "beans/bean/property/ref",
            new RefElementFactory());
        //     </ref>

        result.addSetNext("beans/bean/property", "add");

        //   </property>

        result.addSetNext("beans/bean", "add");
        // </beans>

        return result;
    }
}
