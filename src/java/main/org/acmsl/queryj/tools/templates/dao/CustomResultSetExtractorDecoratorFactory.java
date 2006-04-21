/*
                        QueryJ

    Copyright (C) 2002-2005  Jose San Leandro Armendariz
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
    Contact info: chous@acm-sl.org
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte
                    28660 Madrid
                    Spain

 ******************************************************************************
 *
 * Filename: $RCSfile: $
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides custom decorators for the CustomResultSetExtractor
 *              template.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing project-specific classes.
 */
import org.acmsl.queryj.tools.customsql.Property;
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataManager;
import org.acmsl.queryj.tools.metadata.AttributeDecorator;
import org.acmsl.queryj.tools.metadata.PropertyDecorator;
import org.acmsl.queryj.tools.metadata.vo.Attribute;

/*
 * Importing some JDK classes.
 */
import java.lang.ref.WeakReference;

/**
 * Provides custom decorators for the CustomResultSetExtractor template.
 * @author <a href="mailto:chous@acm-sl.org"
 *         >Jose San Leandro</a>
 */
public class CustomResultSetExtractorDecoratorFactory
    extends  CachingDecoratorFactory
{
    /**
     * A singleton implemented as a weak reference.
     */
    private static WeakReference m__Singleton;
    
    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected CustomResultSetExtractorDecoratorFactory() {};

    /**
     * Specifies a new weak reference.
     * @param factory the factory instance to use.
     */
    protected static void setReference(
        final CustomResultSetExtractorDecoratorFactory factory)
    {
        m__Singleton = new WeakReference(factory);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return m__Singleton;
    }

    /**
     * Retrieves a <code>CustomResultSetExtractorDecoratorFactory</code>
     * instance.
     * @return such instance.
     */
    public static CachingDecoratorFactory getInstance()
    {
        CachingDecoratorFactory result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (CachingDecoratorFactory) reference.get();
        }

        if  (result == null) 
        {
            result = new CustomResultSetExtractorDecoratorFactory();

            setReference(result);
        }

        return result;
    }

    /**
     * Creates a <code>PropertyDecorator</code> for given
     * property instance.
     * @param property the attribute.
     * @param metadataManager the <code>MetadataManager</code> instance.
     * @return the decorated property for the concrete template.
     */
    public PropertyDecorator createDecorator(
        final Property property, final MetadataManager metadataManager)
    {
        return new CustomResultSetExtractorPropertyDecorator(property, metadataManager);
    }
}
