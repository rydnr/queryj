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
 * Filename: ProcedureRepositoryTemplateGenerator.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Is able to generate procedure repository templates according to
 *              database metadata.
 *
 */
package org.acmsl.queryj.tools.templates;

/*
 * Importing some project-specific classes.
 */
import org.acmsl.queryj.tools.metadata.CachingDecoratorFactory;
import org.acmsl.queryj.tools.metadata.MetadataTypeManager;

/*
 * Importing some ACM-SL classes.
 */
import org.acmsl.commons.patterns.Singleton;
import org.acmsl.commons.utils.io.FileUtils;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Is able to generate procedure repositories template according to
 * database metadata.
 * @author <a href="mailto:chous@acm-sl.org">Jose San Leandro Armendariz</a>
 */
public class ProcedureRepositoryTemplateGenerator<T extends ProcedureRepositoryTemplate>
    extends  AbstractTemplateGenerator<T>
    implements  ProcedureRepositoryTemplateFactory,
                Singleton
{
    /**
     * Singleton implemented to avoid the double-checked locking.
     */
    private static class ProcedureRepositoryTemplateGeneratorSingletonContainer
    {
        /**
         * The actual singleton.
         */
        public static final ProcedureRepositoryTemplateGenerator SINGLETON =
            new ProcedureRepositoryTemplateGenerator();
    }

    /**
     * Protected constructor to avoid accidental instantiation.
     */
    protected ProcedureRepositoryTemplateGenerator() {}

    /**
     * Retrieves a {@link ProcedureRepositoryTemplateGenerator} instance.
     * @return such instance.
     */
    @NotNull
    public static ProcedureRepositoryTemplateGenerator getInstance()
    {
        return ProcedureRepositoryTemplateGeneratorSingletonContainer.SINGLETON;
    }

    /**
     * Generates a procedure repository template.
     * @param packageName the package name.
     * @param repository the repository.
     * @param metadataTypeManager the metadata type manager instance.
     * @param header the header.
     * @return such template.
     * @precondition packageName != null
     * @precondition repository != null
     * @precondition metadataTypeManager != null
     */
    @NotNull
    public ProcedureRepositoryTemplate createProcedureRepositoryTemplate(
        final String packageName,
        final String repository,
        final MetadataTypeManager metadataTypeManager,
        final String header)
    {
        return
            new ProcedureRepositoryTemplate(
                header,
                CachingDecoratorFactory.getInstance(),
                packageName,
                repository,
                metadataTypeManager);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    public String retrieveTemplateFileName(@NotNull final T template)
    {
        return retrieveTemplateFileName(template, ProcedureRepositoryTemplateUtils.getInstance());
    }

    /**
     * Retrieves given template's file name.
     * @param template the template.
     * @param procedureRepositoryTemplateUtils the {@link ProcedureRepositoryTemplateUtils} instance.
     * @return such name.
     */
    protected String retrieveTemplateFileName(
        @NotNull final T template, @NotNull final ProcedureRepositoryTemplateUtils procedureRepositoryTemplateUtils)
    {
       return
            procedureRepositoryTemplateUtils.retrieveProcedureRepositoryClassName(
                    template.getRepository());
    }

    /**
     * Writes a table template to disk.
     * @param template the table template to write.
     * @param fileName the template's file name.
     * @param outputDir the output folder.
     * @param charset the file encoding.
     * @param fileUtils the {@link FileUtils} instance.
     * @throws IOException if the file cannot be created.
     * @precondition tableTemplate != null
     * @precondition outputDir != null
     * @precondition fileUtils != null
     * @precondition tableTemplateUtils != null
     */
    @Override
    protected void write(
        @NotNull final T template,
        @NotNull final String fileName,
        @NotNull final File outputDir,
        @NotNull final Charset charset,
        @NotNull final FileUtils fileUtils)
        throws  IOException
    {
        if  (!template.isEmpty())
        {
            @NotNull File t_FinalOutputDir =
                new File(
                      outputDir.getAbsolutePath()
                    + File.separator
                    + fileUtils.packageToPath(
                          template.getPackageName()));

            super.write(template, fileName, t_FinalOutputDir, charset, fileUtils);
        }
    }
}
