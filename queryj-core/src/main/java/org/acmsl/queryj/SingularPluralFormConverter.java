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
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-307  USA


    Thanks to ACM S.L. for distributing this library under the LGPL license.
    Contact info: jose.sanleandro@acm-sl.com
    Postal Address: c/Playa de Lagoa, 1
                    Urb. Valdecabanas
                    Boadilla del monte

 ******************************************************************************
 *
 * Filename: SingularPluralFormConverter.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Provides some grammar rules for converting plural and singular
 *              forms (using english rules by default).
 *
 */
package org.acmsl.queryj;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.BundleI14able;
import org.acmsl.commons.CachingBundleI14able;
import org.acmsl.commons.utils.EnglishGrammarUtils;
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;
import org.jetbrains.annotations.Nullable;

/**
 * Provides some grammar rules for converting plural and singular
 * forms (using english rules by default).
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
@ThreadSafe
public class SingularPluralFormConverter
    extends  EnglishGrammarUtils
{
    /**
     * The default cache TTL.
     */
    public static final long DEFAULT_CACHE_TTL = 24 * 60 * 60 * 100;

    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class SingularPluralFormConverterSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final SingularPluralFormConverter SINGLETON =
            new SingularPluralFormConverter();
    }

    /**
     * The optional bundle name.
     */
    private static File m__GrammarBundle;
    
    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected SingularPluralFormConverter()
    {
        super();
    }

    /**
     * Specifies the specific bundle name.
     * @param bundleName the bundle name.
     */
    protected static void immutableSetGrammarBundle(
        @NotNull final File bundleName)
    {
        m__GrammarBundle = bundleName;
    }
    
    /**
     * Specifies the specific bundle name.
     * @param bundleName the bundle name.
     */
    @SuppressWarnings("unused")
    public static void setGrammarBundle(@NotNull final File bundleName)
    {
        immutableSetGrammarBundle(bundleName);
    }

    /**
     * Retrieves the bundle name.
     * @return such information.
     */
    @Nullable
    public static File getGrammarBundle()
    {
        return m__GrammarBundle;
    }
    
    /**
     * Retrieves a <code>SingularPluralFormConverter</code> instance.
     * @return such instance.
     */
    @NotNull
    public static EnglishGrammarUtils getInstance()
    {
        return SingularPluralFormConverterSingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the words bundle.
     * @return such bundle name.
     */
    @NotNull
    protected String retrieveGrammarBundleName()
    {
        @Nullable String result = null;

        final File grammar = getGrammarBundle();

        if  (grammar != null)
        {
            result = grammar.getAbsolutePath();
        }

        if  (result == null)
        {
            result = super.retrieveGrammarBundleName();
        }
        
        return result;
    }

    /**
     * Retrieves the cache TTL.
     * @return such information.
     */
    public long getCacheTtl()
    {
        return DEFAULT_CACHE_TTL;
    }
    
    /**
     * Creates a <code>BundleI14able</code> with given information.
     * @param messageKey the message key.
     * @param bundleProperty the bundle property.
     * @param bundleName the bundle name.
     * @return the <code>BundleI14able</code> instance.
     */
    @NotNull
    protected BundleI14able createBundleI14able(
        @NotNull final String messageKey,
        @NotNull final String bundleProperty,
        @NotNull final String bundleName)
    {
        return createBundleI14able(messageKey, bundleProperty, bundleName, getCacheTtl());
    }
    
    /**
     * Creates a <code>BundleI14able</code> with given information.
     * @param messageKey the message key.
     * @param bundleProperty the bundle property.
     * @param bundleName the bundle name.
     * @param cacheTtl the cache TTL.
     * @return the <code>BundleI14able</code> instance.
     */
    @NotNull
    protected BundleI14able createBundleI14able(
        @NotNull final String messageKey,
        @NotNull final String bundleProperty,
        @NotNull final String bundleName,
        final long cacheTtl)
    {
        return
            new CachingBundleI14able(
                messageKey,
                bundleProperty,
                bundleName,
                cacheTtl);
    }
}
