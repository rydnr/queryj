/*
                        QueryJ Maven

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
 * Filename: AntProjectAdapter.java
 *
 * Author: Jose San Leandro Armendariz/Jose Juan.
 *
 * Description: Adapts Ant's Project class to use Maven's Log mechanism.
 */
package org.acmsl.queryj.tools.maven;

/*
 * Importing some Maven classes.
 */
import org.apache.maven.plugin.logging.Log;

/*
 * Importing some Ant classes.
 */
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Executor;
import org.apache.tools.ant.input.InputHandler;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FilterSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Resource;

/*
 * Importing some JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;

/*
 * Importing some JDK classes.
 */
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

/*
 * Importing checkthread.org annotations.
 */
import org.checkthread.annotations.ThreadSafe;

/**
 * Adapts Ant {@link Project} to use Maven's Log mechanism.
 * @author <a href="mailto:chous@acm-sl.org">chous</a>
 */
@ThreadSafe
public class AntProjectAdapter
    extends  Project
{
    /**
     * The adaptee.
     */
    private Project m__Project;

    /**
     * The actual logging mechanism to use.
     */
    private Log m__Log;

    /**
     * Creates a {@link AntProjectAdapter} with given Ant {@link Project}
     * instance.
     * @param project the project.
     */
    public AntProjectAdapter(final Project project, final Log log)
    {
        immutableSetProject(project);
        immutableSetLog(log);
    }

    /**
     * Specifies the {@link Project} instance.
     * @param project such instance.
     */
    protected final void immutableSetProject(final Project project)
    {
        m__Project = project;
    }

    /**
     * Specifies the {@link Project} instance.
     * @param project such instance.
     */
    @SuppressWarnings("unused")
    protected void setProject(final Project project)
    {
        immutableSetProject(project);
    }

    /**
     * Retrieves the actual {@link Project} instance.
     * @return such instance.
     */
    protected final Project immutableGetProject()
    {
        return m__Project;
    }

    /**
     * Retrieves the actual {@link Project} instance.
     * @return such instance.
     */
    protected Project getProject()
    {
        return immutableGetProject();
    }

    /**
     * Specifies the {@link Log} instance.
     * @param log such instance.
     */
    protected final void immutableSetLog(final Log log)
    {
        m__Log = log;
    }

    /**
     * Specifies the {@link Log} instance.
     * @param log such instance.
     */
    @SuppressWarnings("unused")
    protected void setLog(final Log log)
    {
        immutableSetLog(log);
    }

    /**
     * Retrieves the actual {@link Log} instance.
     * @return such instance.
     */
    protected final Log immutableGetLog()
    {
        return m__Log;
    }

    /**
     * Retrieves the actual {@link Log} instance.
     * @return such instance.
     */
    protected Log getLog()
    {
        return immutableGetLog();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputHandler(final InputHandler handler)
    {
        setInputHandler(handler, getProject());
    }

    /**
     * Specifies the input handler.
     * @param handler such handler.
     * @param project the {@link Project} instance.
     */
    protected void setInputHandler(final InputHandler handler, @NotNull final Project project)
    {
        project.setInputHandler(handler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputHandler getInputHandler()
    {
        return getInputHandler(getProject());
    }

    /**
     * Retrieves the input handler.
     * @param project the {@link Project} instance.
     */
    protected InputHandler getInputHandler(@NotNull final Project project)
    {
        return project.getInputHandler();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDefaultInputStream(final InputStream defaultInputStream)
    {
        setDefaultInputStream(defaultInputStream, getProject());
    }

    /**
     * Specifies the default input stream.
     * @param defaultInputStream such input stream.
     * @param project the {@link Project} instance.
     */
    protected void setDefaultInputStream(
        final InputStream defaultInputStream, @NotNull final Project project)
    {
        project.setDefaultInputStream(defaultInputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getDefaultInputStream()
    {
        return getDefaultInputStream(getProject());
    }

    /**
     * Retrieves the default input stream.
     * @param project the {@link Project} instance.
     */
    protected InputStream getDefaultInputStream(@NotNull final Project project)
    {
        return project.getDefaultInputStream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project createSubProject()
    {
        return createSubProject(getProject());
    }

    /**
     * Creates a subproject.
     * @param project the {@link Project} instance.
     */
    protected Project createSubProject(@NotNull final Project project)
    {
        return project.createSubProject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initSubProject(final Project subProject)
    {
        initSubProject(subProject, getProject());
    }

    /**
     * Initializes given subproject.
     * @param subProject the subproject to initialize.
     * @param project the {@link Project} instance.
     */
    protected void initSubProject(final Project subProject, @NotNull final Project project)
    {
        project.initSubProject(subProject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init()
        throws BuildException
    {
        init(getProject());
    }

    /**
     * Initializes this project.
     * @param project the {@link Project} instance.
     * @throws BuildException if the initialization fails.
     */
    protected void init(@NotNull final Project project)
        throws BuildException
    {
        project.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initProperties()
        throws BuildException
    {
        initProperties(getProject());
    }

    /**
     * Initializes the project properties.
     * @param project the {@link Project} instance.
     * @throws BuildException if the initialization fails.
     */
    protected void initProperties(@NotNull final Project project)
        throws BuildException
    {
        project.initProperties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AntClassLoader createClassLoader(final Path path)
    {
        return createClassLoader(path, getProject());
    }

    /**
     * Creates a class loader.
     * @param path the path.
     * @param project the {@link Project} instance.
     */
    protected AntClassLoader createClassLoader(final Path path, @NotNull final Project project)
    {
        return project.createClassLoader(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AntClassLoader createClassLoader(final ClassLoader classLoader, final Path path)
    {
        return createClassLoader(classLoader, path, getProject());
    }

    /**
     * Creates a class loader.
     * @param classLoader the parent class loader.
     * @param path the path.
     * @param project the {@link Project} instance.
     */
    protected AntClassLoader createClassLoader(
        final ClassLoader classLoader, final Path path, @NotNull final Project project)
    {
        return project.createClassLoader(classLoader, path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCoreLoader(final ClassLoader coreLoader)
    {
        setCoreLoader(coreLoader, getProject());
    }

    /**
     * Specifies the core class loader.
     * @param coreLoader such class loader.
     * @param project the {@link Project} instance.
     */
    protected void setCoreLoader(final ClassLoader coreLoader, @NotNull final Project project)
    {
        project.setCoreLoader(coreLoader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClassLoader getCoreLoader()
    {
        return getCoreLoader(getProject());
    }

    /**
     * Retrieves the core class loader.
     * @param project the {@link Project} instance.
     * @return such class loader.
     */
    protected ClassLoader getCoreLoader(@NotNull final Project project)
    {
        return project.getCoreLoader();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBuildListener(final BuildListener listener)
    {
        addBuildListener(listener, getProject());
    }

    /**
     * Adds a new build listener.
     * @param listener the {@link BuildListener} instance to add.
     * @param project the {@link Project} instance.
     */
    protected void addBuildListener(final BuildListener listener, @NotNull final Project project)
    {
        project.addBuildListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeBuildListener(final BuildListener listener)
    {
        removeBuildListener(listener, getProject());
    }

    /**
     * Removes given build listener.
     * @param listener the {@link BuildListener} instance.
     * @param project the {@link Project} instance.
     */
    protected void removeBuildListener(final BuildListener listener, @NotNull final Project project)
    {
        project.removeBuildListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector getBuildListeners()
    {
        return getBuildListeners(getProject());
    }

    /**
     * Retrieves the build listeners.
     * @param project the {@link Project} instance.
     * @return such listeners.
     */
    protected Vector getBuildListeners(@NotNull final Project project)
    {
        return project.getBuildListeners();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilterSet getGlobalFilterSet()
    {
        return getGlobalFilterSet(getProject());
    }

    /**
     * Retrieves the global filter set.
     * @param project the {@link Project} instance.
     * @return such filter set.
     */
    protected FilterSet getGlobalFilterSet(@NotNull final Project project)
    {
        return project.getGlobalFilterSet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProperty(final String name, final String value)
    {
        setProperty(name, value, getProject());
    }

    /**
     * Specifies a property.
     * @param name the property name.
     * @param value the value.
     * @param project the {@link Project} instance.
     */
    protected void setProperty(final String name, final String value, @NotNull final Project project)
    {
        project.setProperty(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProperty(final String propertyName)
    {
        return getProperty(propertyName, getProject());
    }

    /**
     * Retrieves a property value.
     * @param propertyName the property name.
     * @param project the {@link Project} instance.
     * @return the property value.
     */
    public String getProperty(final String propertyName, @NotNull final Project project)
    {
        return project.getProperty(propertyName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNewProperty(final String name, final String value)
    {
        setNewProperty(name, value, getProject());
    }

    /**
     * Specifies a new property.
     * @param name the property name.
     * @param value the value.
     * @param project the {@link Project} instance.
     */
    protected void setNewProperty(final String name, final String value, @NotNull final Project project)
    {
        project.setNewProperty(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserProperty(final String name, final String value)
    {
        setUserProperty(name, value, getProject());
    }

    /**
     * Specifies an user property.
     * @param name the property name.
     * @param value the value.
     * @param project the {@link Project} instance.
     */
    protected void setUserProperty(final String name, final String value, @NotNull final Project project)
    {
        project.setUserProperty(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserProperty(final String propertyName)
    {
        return getUserProperty(propertyName, getProject());
    }

    /**
     * Retrieves an user property value.
     * @param propertyName the property name.
     * @param project the {@link Project} instance.
     * @return the property value.
     */
    public String getUserProperty(final String propertyName, @NotNull final Project project)
    {
        return project.getUserProperty(propertyName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInheritedProperty(final String name, final String value)
    {
        setInheritedProperty(name, value, getProject());
    }

    /**
     * Specifies a inherited property.
     * @param name the property name.
     * @param value the value.
     * @param project the {@link Project} instance.
     */
    protected void setInheritedProperty(
        final String name, final String value, @NotNull final Project project)
    {
        project.setInheritedProperty(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String replaceProperties(final String value)
        throws BuildException
    {
        return replaceProperties(value, getProject());
    }

    /**
     * (Taken from Apache Ant 1.8.1 Javadocs):
     *
     * Replaces ${} style constructions in the given value with the
     * string value of the corresponding data types.
     * @param value The string to be scanned for property references.
     *              May be <code>null</code>.
     * @param project the {@link Project} instance.
     * @return the given string with embedded property names replaced
     *         by values, or <code>null</code> if the given string is
     *         <code>null</code>.
     * @throws BuildException if the given value has an unclosed
     *                        property name, e.g. <code>${xxx</code>.
     */
    protected String replaceProperties(final String value, @NotNull final Project project)
        throws BuildException
    {
        return project.replaceProperties(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hashtable getProperties()
    {
        return getProperties(getProject());
    }

    /**
     * Retrieves the properties.
     * @param project the {@link Project} instance.
     * @return such properties.
     */
    protected Hashtable getProperties(@NotNull final Project project)
    {
        return project.getProperties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hashtable getUserProperties()
    {
        return getUserProperties(getProject());
    }

    /**
     * Retrieves the user properties.
     * @param project the {@link Project} instance.
     * @return such properties.
     */
    protected Hashtable getUserProperties(@NotNull final Project project)
    {
        return project.getUserProperties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hashtable getInheritedProperties()
    {
        return getInheritedProperties(getProject());
    }

    /**
     * Retrieves the inherited properties.
     * @param project the {@link Project} instance.
     * @return such properties.
     */
    protected Hashtable getInheritedProperties(@NotNull final Project project)
    {
        return project.getInheritedProperties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyUserProperties(final Project other)
    {
        copyUserProperties(other, getProject());
    }

    /**
     * Copies the user properties from one project to another.
     * @param other the other {@link Project}.
     * @param project the adapted {@link Project}.
     */
    protected void copyUserProperties(final Project other, @NotNull final Project project)
    {
        project.copyUserProperties(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyInheritedProperties(final Project other)
    {
        copyInheritedProperties(other, getProject());
    }

    /**
     * Copies the inherited properties from one project to another.
     * @param other the other {@link Project}.
     * @param project the adapted {@link Project}.
     */
    protected void copyInheritedProperties(final Project other, @NotNull final Project project)
    {
        project.copyInheritedProperties(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefaultTarget()
    {
        return getDefaultTarget(getProject());
    }

    /**
     * Retrieves the default target.
     * @param project the {@link Project} instance.
     * @return such target.
     */
    protected String getDefaultTarget(@NotNull final Project project)
    {
        return project.getDefaultTarget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDefault(final String defaultTarget)
    {
        setDefault(defaultTarget, getProject());
    }

    /**
     * Specifies the default target.
     * @param defaultTarget such target.
     * @param project the {@link Project} instance.
     */
    protected void setDefault(final String defaultTarget, @NotNull final Project project)
    {
        project.setDefault(defaultTarget);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setName(final String name)
    {
        setName(name, getProject());
    }

    /**
     * Specifies the name.
     * @param name the project name.
     * @param project the {@link Project} instance.
     */
    protected void setName(final String name, @NotNull final Project project)
    {
        project.setName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        return getName(getProject());
    }

    /**
     * Retrieves the project name.
     * @param project the {@link Project} instance.
     * @return the project name.
     */
    protected String getName(@NotNull final Project project)
    {
        return project.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDescription(final String description)
    {
        setDescription(description, getProject());
    }

    /**
     * Specifies the description.
     * @param project the {@link Project} instance.
     */
    protected void setDescription(final String description, @NotNull final Project project)
    {
        project.setDescription(description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription()
    {
        return getDescription(getProject());
    }

    /**
     * Retrieves the description.
     * @param project the {@link Project} project.
     * @return such information.
     */
    protected String getDescription(@NotNull final Project project)
    {
        return project.getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBasedir(final String baseD)
        throws BuildException
    {
        setBasedir(baseD, getProject());
    }

    /**
     * Specifies the base dir.
     * @param baseD the base dir.
     * @param project the {@link Project} instance.
     */
    protected void setBasedir(final String baseD, @NotNull final Project project)
        throws BuildException
    {
        project.setBasedir(baseD);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBaseDir(final File baseDir)
        throws BuildException 
    {
        setBaseDir(baseDir, getProject());
    }

    /**
     * Specifies the base dir.
     * @param baseDir the base dir.
     * @param project the {@link Project} instance.
     */
    protected void setBaseDir(final File baseDir, @NotNull final Project project)
        throws BuildException 
    {
        project.setBaseDir(baseDir);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getBaseDir()
    {
        return getBaseDir(getProject());
    }

    /**
     * Retrieves the base dir.
     * @param project the {@link Project} instance.
     * @return such dir.
     */
    protected File getBaseDir(@NotNull final Project project)
    {
        return project.getBaseDir();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setKeepGoingMode(final boolean keepGoingMode)
    {
        setKeepGoingMode(keepGoingMode, getProject());
    }

    /**
     * Specifies whether to keep going.
     * @param keepGoingMode such behavior.
     * @param project the {@link Project} instance.
     */
    protected void setKeepGoingMode(final boolean keepGoingMode, @NotNull final Project project)
    {
        project.setKeepGoingMode(keepGoingMode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isKeepGoingMode()
    {
        return isKeepGoingMode(getProject());
    }

    /**
     * Retrieves whether it'll keep going upon error.
     * @param project the {@link Project} instance.
     * @return such behavior.
     */
    protected boolean isKeepGoingMode(@NotNull final Project project)
    {
        return project.isKeepGoingMode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setJavaVersionProperty()
        throws BuildException
    {
        setJavaVersionProperty(getProject());
    }

    /**
     * (Copied from Apache Ant 1.8.1 javadoc):<br/>
     * Sets the <code>ant.java.version</code> property and tests for
     * unsupported JVM versions. If the version is supported,
     * verbose log messages are generated to record the Java version
     * and operating system name.
     *
     * @param project the {@link Project} instance.
     * @exception BuildException if this Java version is not supported.
     *
     * @see org.apache.tools.ant.util.JavaEnvUtils#getJavaVersion
     */
    protected void setJavaVersionProperty(@NotNull final Project project)
        throws BuildException
    {
        project.setJavaVersionProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSystemProperties()
    {
        setSystemProperties(getProject());
    }

    /**
     * Specifies all system properties.
     * @param project the {@link Project} instance.
     */
    protected void setSystemProperties(@NotNull final Project project)
    {
        project.setSystemProperties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTaskDefinition(final String taskName, final Class taskClass)
        throws BuildException
    {
        addTaskDefinition(taskName, taskClass, getProject());
    }

    /**
     * Adds a task definition.
     * @param taskName the task name.
     * @param taskClass the task class.
     * @param project the {@link Project} instance.
     * @throws BuildException if the operation fails.
     */
    protected void addTaskDefinition(
        final String taskName, final Class taskClass, @NotNull final Project project)
      throws BuildException
    {
        project.addTaskDefinition(taskName, taskClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkTaskClass(final Class taskClass)
        throws BuildException
    {
        checkTaskClass(taskClass, getProject());
    }

    /**
     * Checks given task class.
     * @param taskClass the task class.
     * @param project the {@link Project} instance.
     * @throws BuildException if the verification fails.
     */
    protected void checkTaskClass(final Class taskClass, @NotNull final Project project)
        throws BuildException
    {
        project.checkTaskClass(taskClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hashtable getTaskDefinitions()
    {
        return getTaskDefinitions(getProject());
    }

    /**
     * Retrieves the task definitions.
     * @param project the {@link Project} instance.
     */
    protected Hashtable getTaskDefinitions(@NotNull final Project project)
    {
        return project.getTaskDefinitions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map getCopyOfTaskDefinitions()
    {
        return getCopyOfTaskDefinitions(getProject());
    }

    /**
     * Retrieves a copy of the task definitions.
     * @param project the {@link Project} instance.
     * @return such information.
     */
    protected Map getCopyOfTaskDefinitions(@NotNull final Project project)
    {
        return project.getCopyOfTaskDefinitions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDataTypeDefinition(final String typeName, final Class typeClass)
    {
        addDataTypeDefinition(typeName, typeClass, getProject());
    }

    /**
     * Adds a data type definition.
     * @param typeName the type name.
     * @param typeClass the type class.
     * @param project the {@link Project} instance.
     */
    protected void addDataTypeDefinition(
        final String typeName, final Class typeClass, @NotNull final Project project)
    {
        project.addDataTypeDefinition(typeName, typeClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hashtable getDataTypeDefinitions()
    {
        return getDataTypeDefinitions(getProject());
    }

    /**
     * Retrieves the data type definitions.
     * @param project the {@link Project} instance.
     * @return such information.
     */
    protected Hashtable getDataTypeDefinitions(@NotNull final Project project)
    {
        return project.getDataTypeDefinitions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map getCopyOfDataTypeDefinitions()
    {
        return getCopyOfDataTypeDefinitions(getProject());
    }

    /**
     * Retrieves a copy of the data type definitions.
     * @param project the {@link Project} instance.
     * @return such information.
     */
    protected Map getCopyOfDataTypeDefinitions(@NotNull final Project project)
    {
        return project.getCopyOfDataTypeDefinitions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTarget(final Target target)
        throws BuildException
    {
        addTarget(target, getProject());
    }

    /**
     * Adds given target.
     * @param target the target.
     * @param project the {@link Project} instance.
     * @throws BuildException if the target cannot be added.
     */
    protected void addTarget(final Target target, @NotNull final Project project)
        throws BuildException
    {
        project.addTarget(target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTarget(final String targetName, final Target target)
        throws BuildException
    {
        addTarget(targetName, target, getProject());
    }

    /**
     * Adds given target.
     * @param targetName the target name.
     * @param target the target.
     * @param project the {@link Project} instance.
     * @throws BuildException if the target cannot be added.
     */
    protected void addTarget(
        final String targetName, final Target target, @NotNull final Project project)
        throws BuildException
    {
        project.addTarget(targetName, target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addOrReplaceTarget(final Target target)
        throws BuildException
    {
        addOrReplaceTarget(target, getProject());
    }

    /**
     * Adds given target.
     * @param target the target.
     * @param project the {@link Project} instance.
     * @throws BuildException if the target cannot be added.
     */
    protected void addOrReplaceTarget(final Target target, @NotNull final Project project)
        throws BuildException
    {
        project.addOrReplaceTarget(target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addOrReplaceTarget(final String targetName, final Target target)
        throws BuildException
    {
        addOrReplaceTarget(targetName, target, getProject());
    }

    /**
     * Adds given target.
     * @param targetName the target name.
     * @param target the target.
     * @param project the {@link Project} instance.
     * @throws BuildException if the target cannot be added.
     */
    protected void addOrReplaceTarget(
        final String targetName, final Target target, @NotNull final Project project)
        throws BuildException
    {
        project.addOrReplaceTarget(targetName, target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hashtable getTargets()
    {
        return getTargets(getProject());
    }

    /**
     * Retrieves the current targets.
     * @param project the {@link Project} instance.
     * @return such information.
     */
    protected Hashtable getTargets(@NotNull final Project project)
    {
        return project.getTargets();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map getCopyOfTargets()
    {
        return getCopyOfTargets(getProject());
    }

    /**
     * Retrieves a copy of the current targets.
     * @param project the {@link Project} instance.
     * @return such information.
     */
    protected Map getCopyOfTargets(@NotNull final Project project)
    {
        return project.getCopyOfTargets();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task createTask(final String taskType)
        throws BuildException
    {
        return createTask(taskType, getProject());
    }

    /**
     * Creates a new task.
     * @param taskType the task type.
     * @param project the {@link Project} instance.
     * @return the new task.
     * @throws BuildException if the task cannot be created.
     */
    protected Task createTask(final String taskType, @NotNull final Project project)
        throws BuildException
    {
        return project.createTask(taskType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object createDataType(final String typeName)
        throws BuildException
    {
        return createDataType(typeName, getProject());
    }

    /**
     * Creates a new data type.
     * @param typeName the name of the data type.
     * @param project the {@link Project} instance.
     * @return the new data type.
     * @throws BuildException if the data type cannot be created.
     */
    protected Object createDataType(final String typeName, @NotNull final Project project)
        throws BuildException
    {
        return project.createDataType(typeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExecutor(final Executor executor)
    {
        setExecutor(executor, getProject());
    }

    /**
     * Specifies the executor.
     * @param executor the new {@link Executor} instance to use.
     * @param project the {@link Project} instance.
     */
    protected void setExecutor(final Executor executor, @NotNull final Project project)
    {
        project.setExecutor(executor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Executor getExecutor()
    {
        return getExecutor(getProject());
    }

    /**
     * Retrieves the executor.
     * @param project the {@link Project} instance.
     * @return such instance.
     */
    protected Executor getExecutor(@NotNull final Project project)
    {
        return project.getExecutor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeTargets(final Vector names)
        throws BuildException
    {
        executeTargets(names, getProject());
    }

    /**
     * Executes given targets.
     * @param names the target names.
     * @param project the {@link Project} instance.
     */
    protected void executeTargets(final Vector names, @NotNull final Project project)
        throws BuildException
    {
        project.executeTargets(names);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void demuxOutput(final String output, final boolean isWarning)
    {
        demuxOutput(output, isWarning, getProject());
    }

    /**
     * @see {@link Project#demuxOutput(String, boolean)}
     * @param output the output.
     * @param isWarning whether the text represents a warning.
     * @param project the {@link Project} instance.
     */
    protected void demuxOutput(
        final String output, final boolean isWarning, @NotNull final Project project)
    {
        project.demuxOutput(output, isWarning);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int defaultInput(final byte[] buffer, final int offset, final int length)
        throws IOException
    {
        return defaultInput(buffer, offset, length, getProject());
    }

    /**
     * @see {@link Project#defaultInput(byte[], int, int)}
     * @param buffer the buffer.
     * @param offset the offset.
     * @param length the buffer length.
     * @param project the {@link Project} instance.
     * @return the number of bytes read.
     * @throws IOException in some cases.
     */
    protected int defaultInput(
        final byte[] buffer, final int offset, final int length, @NotNull final Project project)
        throws IOException
    {
        return project.defaultInput(buffer, offset, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int demuxInput(final byte[] buffer, final int offset, final int length)
        throws IOException
    {
        return demuxInput(buffer, offset, length, getProject());
    }

    /**
     * @see {@link Project#demuxInput(byte[], int, int)}
     * @param buffer the input buffer.
     * @param offset the offset.
     * @param length the buffer length.
     * @param project the {@link Project} instance.
     * @return the number of bytes processed.
     * @throws IOException in some cases.
     */
    protected int demuxInput(
        final byte[] buffer, final int offset, final int length, @NotNull final Project project)
        throws IOException
    {
        return project.demuxInput(buffer, offset, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void demuxFlush(final String output, final boolean isError)
    {
        demuxFlush(output, isError, getProject());
    }

    /**
     * @see {@link Project#demuxFlush(String, boolean)}.
     * @param output the output.
     * @param isError whether the output represents an error message.
     * @param project the {@link Project} instance.
     */
    protected void demuxFlush(
        final String output, final boolean isError, @NotNull final Project project)
    {
        project.demuxFlush(output, isError);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeTarget(final String targetName)
        throws BuildException
    {
        executeTarget(targetName, getProject());
    }

    /**
     * @see {@link Project#executeTarget(String)}.
     * @param targetName the target name.
     * @param project the {@link Project} instance.
     * @throws BuildException if the target fails.
     */
    protected void executeTarget(final String targetName, @NotNull final Project project)
        throws BuildException
    {
        project.executeTarget(targetName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeSortedTargets(final Vector sortedTargets)
        throws BuildException
    {
        executeSortedTargets(sortedTargets, getProject());
    }

    /**
     * @see {@link Project#executeSortedTargets(Vector).
     * @param sortedTargets the target list.
     * @param project the {@link Project} instance.
     * @throws BuildException if any of the targets fails.
     */
    protected void executeSortedTargets(final Vector sortedTargets, @NotNull final Project project)
        throws BuildException
    {
        project.executeSortedTargets(sortedTargets);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File resolveFile(final String fileName)
    {
        return resolveFile(fileName, getProject());
    }

    /**
     * @see {@link Project#resolveFile(String)}.
     * @param fileName the file name.
     * @param project the {@link Project} instance.
     * @return the file.
     */
    protected File resolveFile(final String fileName, @NotNull final Project project)
    {
        return project.resolveFile(fileName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void inheritIDReferences(final Project parent)
    {
        inheritIDReferences(parent, getProject());
    }

    /**
     * @see {@link Project#inheritIDReferences(Project)}.
     * @param parent the parent project.
     * @param project the {@link Project} instance.
     */
    protected void inheritIDReferences(final Project parent, @NotNull final Project project)
    {
        project.inheritIDReferences(parent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addIdReference(final String id, final Object value)
    {
        addIdReference(id, value, getProject());
    }

    /**
     * @see {@link Project#addIdReference(String, Object)}.
     * @param id the id.
     * @param value the reference.
     * @param project the {@link Project} instance.
     */
    protected void addIdReference(final String id, final Object value, @NotNull final Project project)
    {
        project.addIdReference(id, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addReference(final String referenceName, final Object value)
    {
        addReference(referenceName, value, getProject());
    }

    /**
     * @see {@link Project#addReference(String, Object)}.
     * @param referenceName the reference name.
     * @param value the value.
     * @param project the {@link Project} instance.
     */
    protected void addReference(
        final String referenceName, final Object value, @NotNull final Project project)
    {
        project.addReference(referenceName, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hashtable getReferences()
    {
        return getReferences(getProject());
    }

    /**
     * @see {@link Project#getReferences()}.
     * @param project the {@link Project} instance.
     * @return such references.
     */
    protected Hashtable getReferences(@NotNull final Project project)
    {
        return project.getReferences();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasReference(final String key)
    {
        return hasReference(key, getProject());
    }

    /**
     * @see {@link Project#hasReference(String)}.
     * @param key the reference key.
     * @param project the {@link Project} instance.
     * @return <code>true</code> in such case.
     */
    protected boolean hasReference(final String key, @NotNull final Project project)
    {
        return project.hasReference(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map getCopyOfReferences()
    {
        return getCopyOfReferences(getProject());
    }

    /**
     * @see {@link Project#getCopyOfReferences()}.
     * @param project the {@link Project} instance.
     * @return such references.
     */
    protected Map getCopyOfReferences(@NotNull final Project project)
    {
        return project.getCopyOfReferences();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getReference(final String key)
    {
        return getReference(key, getProject());
    }

    /**
     * @see {@link Project#getReference(String)}.
     * @param key the reference key.
     * @param project the {@link Project} instance.
     * @return the reference value.
     */
    protected Object getReference(final String key, @NotNull final Project project)
    {
        return project.getReference(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getElementName(final Object element)
    {
        return getElementName(element, getProject());
    }

    /**
     * @see {@link Project#getElementName(Object)}.
     * @param element the element.
     * @param project the {@link Project} instance.
     * @return the name.
     */
    protected String getElementName(final Object element, @NotNull final Project project)
    {
        return project.getElementName(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireBuildStarted()
    {
        fireBuildStarted(getProject());
    }

    /**
     * @see {@link Project#fireBuildStarted()}.
     * @param project the {@link Project} instance.
     */
    protected void fireBuildStarted(@NotNull final Project project)
    {
        project.fireBuildStarted();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireBuildFinished(final Throwable exception)
    {
        fireBuildFinished(exception, getProject());
    }

    /**
     * @see {@link Project#fireBuildFinished(Throwable)}.
     * @param exception the exception, should it occurs.
     * @param project the {@link Project} instance.
     */
    protected void fireBuildFinished(final Throwable exception, @NotNull final Project project)
    {
        project.fireBuildFinished(exception);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireSubBuildStarted()
    {
        fireSubBuildStarted(getProject());
    }

    /**
     * @see {@link Project#fireSubBuildStarted()}.
     * @param project the {@link Project} instance.
     */
    protected void fireSubBuildStarted(@NotNull final Project project)
    {
        project.fireSubBuildStarted();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireSubBuildFinished(final Throwable exception)
    {
        fireSubBuildFinished(exception, getProject());
    }

    /**
     * @see {@link Project#fireSubBuildFinished(Throwable)}.
     * @param exception the exception, should it occurs.
     * @param project the {@link Project} instance.
     */
    protected void fireSubBuildFinished(final Throwable exception, @NotNull final Project project)
    {
        project.fireSubBuildFinished(exception);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerThreadTask(final Thread thread, final Task task)
    {
        registerThreadTask(thread, task, getProject());
    }

    /**
     * {@inheritDoc}
     */
    protected void registerThreadTask(
        final Thread thread, final Task task, @NotNull final Project project)
    {
        project.registerThreadTask(thread, task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task getThreadTask(final Thread thread)
    {
        return getThreadTask(thread, getProject());
    }

    /**
     * @see {@link Project#getThreadTask(Thread)}.
     * @param thread the thread task.
     * @param project the {@link Project} instance.
     * @return the task.
     */
    protected Task getThreadTask(final Thread thread, @NotNull final Project project)
    {
        return project.getThreadTask(thread);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource getResource(final String name)
    {
        return getResource(name, getProject());
    }

    /**
     * @see {@link Project#getResource(String)}.
     * @param name the resource name.
     * @param project the {@link Project} instance.
     * @return the resource.
     */
    protected Resource getResource(final String name, @NotNull final Project project)
    {
        return project.getResource(name);
    }

// Logging methods

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(final String message)
    {
        log(message, getLog());
    }

    /**
     * Logs given message using Maven's mechanism.
     * @param message the message.
     * @param log the {@link Log} instance.
     */
    protected void log(final String message, @NotNull final Log log)
    {
        log.info(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(final String message, final int msgLevel)
    {
        log(message, msgLevel, getLog());
    }

    /**
     * @see {@link Project#log(String)}.
     * @param message the message.
     * @param msgLevel either {@link Project#MSG_ERR}, {@link Project#MSG_WARN},
     * {@link Project#MSG_INFO}, {@link Project#MSG_VERBOSE}, or {@link Project#MSG_DEBUG}.
     * @param log the {@link Log} instance.
     */
    protected void log(final String message, final int msgLevel, @NotNull final Log log)
    {
        switch (msgLevel)
        {
            case MSG_ERR:
                log.error(message);
                break;
            case MSG_WARN:
                log.warn(message);
                break;
            case MSG_VERBOSE:
            case MSG_DEBUG:
                log.debug(message);
                break;
            default:
                log.info(message);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(final String message, final Throwable throwable, final int msgLevel)
    {
        log(message, throwable, msgLevel, getLog());
    }

    /**
     * @see {@link #log(String, Throwable, int)}.
     * @param message the message.
     * @param throwable the error.
     * @param msgLevel either {@link Project#MSG_ERR}, {@link Project#MSG_WARN},
     * {@link Project#MSG_INFO}, {@link Project#MSG_VERBOSE}, or {@link Project#MSG_DEBUG}.
     * @param log the {@link Log} instance.
     */
    protected void log(
        final String message, final Throwable throwable, final int msgLevel, @NotNull final Log log)
    {
        switch (msgLevel)
        {
            case MSG_ERR:
                log.error(message, throwable);
                break;
            case MSG_WARN:
                log.warn(message, throwable);
                break;
            case MSG_VERBOSE:
            case MSG_DEBUG:
                log.debug(message, throwable);
                break;
            default:
                log.info(message, throwable);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(@NotNull final Task task, final String message, final int msgLevel)
    {
        log(task, message, msgLevel, getLog());
    }

    /**
     * @see {@link Project#log(Task, String, int)}.
     * @param task the task.
     * @param message the message.
     * @param msgLevel either {@link Project#MSG_ERR}, {@link Project#MSG_WARN},
     * {@link Project#MSG_INFO}, {@link Project#MSG_VERBOSE}, or {@link Project#MSG_DEBUG}.
     * @param log the {@link Log} instance.
     */
    protected void log(@NotNull final Task task, final String message, final int msgLevel, @NotNull final Log log)
    {
        @NotNull String t_strMessage = "[" + task.getTaskName() + "] " + message;

        switch (msgLevel)
        {
            case MSG_ERR:
                log.error(t_strMessage);
                break;
            case MSG_WARN:
                log.warn(t_strMessage);
                break;
            case MSG_VERBOSE:
            case MSG_DEBUG:
                log.debug(t_strMessage);
                break;
            default:
                log.info(t_strMessage);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(
        @NotNull final Task task, final String message, final Throwable throwable, final int msgLevel)
    {
        log(task, message, throwable, msgLevel, getLog());
    }

    /**
     * @see {@link Project#log(Task, String, int)}.
     * @param task the task.
     * @param message the message.
     * @param throwable the error.
     * @param msgLevel either {@link Project#MSG_ERR}, {@link Project#MSG_WARN},
     * {@link Project#MSG_INFO}, {@link Project#MSG_VERBOSE}, or {@link Project#MSG_DEBUG}.
     * @param log the {@link Log} instance.
     */
    protected void log(
        @NotNull final Task task,
        final String message,
        final Throwable throwable,
        final int msgLevel, @NotNull final Log log)
    {
        @NotNull String t_strMessage = "[" + task.getTaskName() + "] " + message;

        switch (msgLevel)
        {
            case MSG_ERR:
                log.error(t_strMessage, throwable);
                break;
            case MSG_WARN:
                log.warn(t_strMessage, throwable);
                break;
            case MSG_VERBOSE:
            case MSG_DEBUG:
                log.debug(t_strMessage, throwable);
                break;
            default:
                log.info(t_strMessage, throwable);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(@NotNull final Target target, final String message, final int msgLevel)
    {
        log(target, message, msgLevel, getLog());
    }

    /**
     * @see {@link Project#log(Target, String, int)}.
     * @param target the target.
     * @param message the message.
     * @param msgLevel either {@link Project#MSG_ERR}, {@link Project#MSG_WARN},
     * {@link Project#MSG_INFO}, {@link Project#MSG_VERBOSE}, or {@link Project#MSG_DEBUG}.
     * @param log the {@link Log} instance.
     */
    protected void log(
        @NotNull final Target target, final String message, final int msgLevel, @NotNull final Log log)
    {
        @NotNull String t_strMessage = "[" + target.getName() + "] " + message;

        switch (msgLevel)
        {
            case MSG_ERR:
                log.error(t_strMessage);
                break;
            case MSG_WARN:
                log.warn(t_strMessage);
                break;
            case MSG_VERBOSE:
            case MSG_DEBUG:
                log.debug(t_strMessage);
                break;
            default:
                log.info(t_strMessage);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(
        @NotNull final Target target, final String message, final Throwable throwable, final int msgLevel)
    {
        log(target, message, throwable, msgLevel, getLog());
    }

    /**
     * @see {@link Project#log(Target, String, int)}.
     * @param target the target.
     * @param message the message.
     * @param throwable the error.
     * @param msgLevel either {@link Project#MSG_ERR}, {@link Project#MSG_WARN},
     * {@link Project#MSG_INFO}, {@link Project#MSG_VERBOSE}, or {@link Project#MSG_DEBUG}.
     * @param log the {@link Log} instance.
     */
    protected void log(
        @NotNull final Target target,
        final String message,
        final Throwable throwable,
        final int msgLevel, @NotNull final Log log)
    {
        @NotNull String t_strMessage = "[" + target.getName() + "] " + message;

        switch (msgLevel)
        {
            case MSG_ERR:
                log.error(t_strMessage, throwable);
                break;
            case MSG_WARN:
                log.warn(t_strMessage, throwable);
                break;
            case MSG_VERBOSE:
                log.debug(t_strMessage, throwable);
                log.debug(t_strMessage, throwable);
                break;
            default:
                log.info(t_strMessage, throwable);
                break;
        }
    }
}
