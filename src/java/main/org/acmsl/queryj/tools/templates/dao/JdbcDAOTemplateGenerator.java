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
 * Filename: $RCSfile$
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate JdbcDAO implementations.
 *
 */
package org.acmsl.queryj.tools.templates.dao;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.QueryJException;
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplate;
import org.acmsl.queryj.tools.templates.dao.JdbcDAOTemplateFactory;
import org.acmsl.queryj.tools.templates.TemplateMappingManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.utils.io.FileUtils;
import org.acmsl.commons.utils.StringUtils;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Is able to generate JdbcDAO implementations.
 * @author <a href="mailto:chous@acm-sl.org"
           >Jose San Leandro</a>
 */
public class JdbcDAOTemplateGenerator
    implements  JdbcDAOTemplateFactory
{
    /**
     * Singleton implemented as a weak reference.
     */
    private static WeakReference singleton;

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected JdbcDAOTemplateGenerator() {};

    /**
     * Specifies a new weak reference.
     * @param generator the generator instance to use.
     */
    protected static void setReference(
        final JdbcDAOTemplateGenerator generator)
    {
        singleton = new WeakReference(generator);
    }

    /**
     * Retrieves the weak reference.
     * @return such reference.
     */
    protected static WeakReference getReference()
    {
        return singleton;
    }

    /**
     * Retrieves a JdbcDAOTemplateGenerator instance.
     * @return such instance.
     */
    public static JdbcDAOTemplateGenerator getInstance()
    {
        JdbcDAOTemplateGenerator result = null;

        WeakReference reference = getReference();

        if  (reference != null) 
        {
            result = (JdbcDAOTemplateGenerator) reference.get();
        }

        if  (result == null) 
        {
            result = new JdbcDAOTemplateGenerator() {};

            setReference(result);
        }

        return result;
    }

    /**
     * Adds a new template factory class.
     * @param templateFactoryClass the template factory.
     * @precondition templateFactoryClass != null
     */
    public void addTemplateFactoryClass(
        final String templateFactoryClass)
    {
        addTemplateFactoryClass(
            templateFactoryClass, TemplateMappingManager.getInstance());
    }

    /**
     * Adds a new template factory class.
     * @param templateFactoryClass the template factory.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @precondition templateFactoryClass != null
     * @precondition templateMappingManager != null
     */
    public void addTemplateFactoryClass(
        final String templateFactoryClass,
        final TemplateMappingManager templateMappingManager)
    {
        templateMappingManager.addDefaultTemplateFactoryClass(
            TemplateMappingManager.JDBC_DAO_TEMPLATE_FACTORY,
            templateFactoryClass);
    }

    /**
     * Retrieves the template factory class.
     * @return the template factory class name.
     */
    protected String getTemplateFactoryClass()
    {
        return getTemplateFactoryClass(TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory class.
     * @param templateMappingManager the
     * <code>TemplateMappingManager</code> instance.
     * @return the template factory class name.
     * @precondition templateMappingManager != null
     */
    protected String getTemplateFactoryClass(
        final TemplateMappingManager templateMappingManager)
    {
        return
            templateMappingManager.getDefaultTemplateFactoryClass(
                TemplateMappingManager.JDBC_DAO_TEMPLATE_FACTORY);
    }

    /**
     * Retrieves the template factory instance.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     */
    protected JdbcDAOTemplateFactory getTemplateFactory()
        throws  QueryJException
    {
        return getTemplateFactory(TemplateMappingManager.getInstance());
    }

    /**
     * Retrieves the template factory instance.
     * @param templateMappingManager the <code>TemplateMappingManager</code>
     * instance.
     * @return the template factory class name.
     * @throws QueryJException if the factory class is invalid.
     * @precondition templateMappingManager != null
     */
    protected JdbcDAOTemplateFactory getTemplateFactory(
        final TemplateMappingManager templateMappingManager)
      throws  QueryJException
    {
        JdbcDAOTemplateFactory result = null;

        Object t_TemplateFactory =
            templateMappingManager.getDefaultTemplateFactoryClass(
                TemplateMappingManager.JDBC_DAO_TEMPLATE_FACTORY);
    
        if  (t_TemplateFactory != null)
        {
            if  (!(t_TemplateFactory instanceof JdbcDAOTemplateFactory))
            {
                throw
                    new QueryJException(
                        "invalid.jdbc.dao.template.factory");
            }
            else 
            {
                result = (JdbcDAOTemplateFactory) t_TemplateFactory;
            }
        }

        return result;
    }

    /**
     * Generates a JDBC DAO template.
     * @param packageName the package name.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition packageName != null
     */
    public JdbcDAOTemplate createJdbcDAOTemplate(
        final String packageName)
      throws  QueryJException
    {
        return
            createJdbcDAOTemplate(
                packageName, getTemplateFactory());
    }

    /**
     * Generates a JDBC DAO template.
     * @param packageName the package name.
     * @param templateFactory the template factory.
     * @return a template.
     * @throws QueryJException if the factory class is invalid.
     * @precondition packageName != null
     */
    public JdbcDAOTemplate createJdbcDAOTemplate(
        final String packageName,
        final JdbcDAOTemplateFactory templateFactory)
      throws  QueryJException
    {
        JdbcDAOTemplate result = null;

        if  (templateFactory != null)
        {
            result =
                templateFactory.createJdbcDAOTemplate(
                    packageName);
        }
        else 
        {
            result =
                new JdbcDAOTemplate(packageName);
        }

        return result;
    }

    /**
     * Writes a JDBC DAO template to disk.
     * @param jdbcDAOTemplate the JDBC DAO template to write.
     * @param outputDir the output folder.
     * @throws IOException if the file cannot be created.
     * @precondition jdbcDAOTemplate != null
     * @precondition outputDir != null
     */
    public void write(
        final JdbcDAOTemplate jdbcDAOTemplate,
        final File outputDir)
      throws  IOException
    {
        write(
            jdbcDAOTemplate,
            outputDir,
            StringUtils.getInstance(),
            FileUtils.getInstance());
    }

    /**
     * Writes a JDBC DAO template to disk.
     * @param jdbcDAOTemplate the JDBC DAO template to write.
     * @param outputDir the output folder.
     * @param stringUtils the <code>StringUtils</code> instance.
     * @param fileUtils the <code>FileUtils</code> instance.
     * @throws IOException if the file cannot be created.
     * @precondition jdbcDAOTemplate != null
     * @precondition outputDir != null
     * @precondition stringUtils != null
     * @precondition fileUtils != null
     */
    protected void write(
        final JdbcDAOTemplate jdbcDAOTemplate,
        final File outputDir,
        final StringUtils stringUtils,
        final FileUtils fileUtils)
      throws  IOException
    {
        outputDir.mkdirs();

        fileUtils.writeFile(
              outputDir.getAbsolutePath()
            + File.separator
            + "JdbcDAO.java",
            jdbcDAOTemplate.generate());
    }
}
