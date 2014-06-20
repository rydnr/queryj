/*
                        QueryJ Debugging

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
 * Filename: NettyServerDebuggingServiceTest.java
 *
 * Author: Jose San Leandro Armendariz
 *
 * Description: Tests for NettyServerDebuggingService.
 *
 * Date: 2014/06/26
 * Time: 18:36
 *
 */
package org.acmsl.queryj.debugging.netty;

/*
 * Importing Netty classes.
 */
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/*
 * Importing ACM-SL Java Commons classes.
 */
import org.acmsl.commons.utils.io.FileUtils;

/*
 * Importing QueryJ-Core classes.
 */
import org.acmsl.queryj.api.AbstractTemplateGenerator;
import org.acmsl.queryj.api.TemplateContext;
import org.acmsl.queryj.metadata.DecoratorFactory;
import org.acmsl.queryj.tools.debugging.TemplateDebuggingService;

/*
 * Importing QueryJ Template Debugging classes.
 */
import org.acmsl.queryj.debugging.AbstractTemplateTest;
import org.acmsl.queryj.debugging.AbstractTemplateTest.MyTestableAbstractTemplate;

/*
 * Importing Apache Commons Logging annotations.
 */
import org.apache.commons.logging.LogFactory;

/*
 * Importing JetBrains annotations.
 */
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Importing JUnit/EasyMock classes.
 */
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/*
 * Importing JDK classes.
 */
import java.io.File;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Tests for {@link NettyServerDebuggingService}.
 * @author <a href="mailto:queryj@acm-sl.org">Jose San Leandro</a>
 * @since 3.0
 * Created: 2014/06/26 18:36
 */
@RunWith(JUnit4.class)
public class NettyServerDebuggingServiceTest
{
    /**
     * Checks a reload makes the server request
     * a template reload.
     */
    public void reload_makes_returns_a_reload_command()
    {
        // Start the service
        @NotNull final NettyServerDebuggingService instance =
            new NettyServerDebuggingService();

        // setup an AbstractTemplateGenerator
        @NotNull final TemplateContext templateContext = EasyMock.createNiceMock(TemplateContext.class);
        EasyMock.expect(templateContext.getFileName()).andReturn("foo.java").anyTimes();
        EasyMock.expect(templateContext.isDebugEnabled()).andReturn(true);
        EasyMock.replay(templateContext);

        @NotNull final DecoratorFactory decoratorFactory = EasyMock.createNiceMock(DecoratorFactory.class);

        @SuppressWarnings("unchecked")
        @NotNull final AbstractTemplateGenerator<MyTestableAbstractTemplate<TemplateContext>, TemplateContext> generator =
            new AbstractTemplateGenerator(false, 1)
            {
                /**
                 * {@inheritDoc}
                 */
                @NotNull
                @Override
                public DecoratorFactory getDecoratorFactory()
                {
                    return decoratorFactory;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected boolean isInDevMode(@NotNull final String templateFileName)
                {
                    return true;
                }

                /**
                 * {@inheritDoc}
                 */
                @Nullable
                @Override
                public TemplateDebuggingService resolveTemplateDebuggingService()
                {
                    return instance;
                }

                /**
                 * {@inheritDoc}
                 */
                @Nullable
                @Override
                protected String retrieveHash(
                    @NotNull final String fileName,
                    @NotNull final File outputDir,
                    @NotNull final File rootFolder,
                    @NotNull final Charset charset,
                    @NotNull final FileUtils fileUtils)
                {
                    return "";
                }
            };

        @NotNull final AbstractTemplateTest.MyTestableAbstractTemplate<TemplateContext> template =
            new AbstractTemplateTest.MyTestableAbstractTemplate<>(templateContext);

        @NotNull final File outputDir = EasyMock.createNiceMock(File.class);
        EasyMock.expect(outputDir.getAbsolutePath()).andReturn("/tmp").anyTimes();
        EasyMock.expect(outputDir.exists()).andReturn(true).anyTimes();
        EasyMock.replay(outputDir);
        @NotNull final File rootFolder = EasyMock.createNiceMock(File.class);
        EasyMock.expect(rootFolder.getAbsolutePath()).andReturn("/tmp").anyTimes();
        EasyMock.expect(rootFolder.exists()).andReturn(true).anyTimes();
        EasyMock.replay(rootFolder);

        // Simulate the server receives a reload command:
        // Create a client
        // Send the "reload" command

        // check the generator restarts the generation for that template.
        EasyMock.verify(template.template);
    }

    /**
     * Sends a text to the server.
     * @param host the host.
     * @param port the port.
     * @param msg the text to send.
     */
    protected void sendTextToServer(@NotNull final String host, final int port, @NotNull final String msg)
      throws Exception
    {
        new NettyClient(host, port, msg).connect();
    }

    /**
     * Checks the reload command gets received successfully.
     */
    @Test
    public void reload_command_gets_received_correctly()
        throws Exception
    {
        // TODO
        final int port = 7777;

        System.setProperty(MyNettyServerDebuggingService.QUERYJ_TEMPLATE_DEBUG_PORT, String.valueOf(port));

        @NotNull final MyNettyServerDebuggingService instance =
            new MyNettyServerDebuggingService();

        @NotNull final ChannelFuture server = instance.launchServer();

        sendTextToServer("localhost", port, "reload");

        // Wait until the connections are closed.
        Assert.assertTrue(server.isDone());

        Assert.assertTrue(instance.m__bReloadReceived);

    }

    /**
     * A testable debugging service.
     */
    public static class MyNettyServerDebuggingService
        extends NettyServerDebuggingService
    {
        /**
         * Whether the reload has been received.
         */
        private boolean m__bReloadReceived;

        /**
         * Gets notified whenever a "reload" operation has been
         * requested.
         */
        @Override
        public void reloadRequested()
        {
            this.m__bReloadReceived = true;
            super.reloadRequested();
        }

        /**
         * {@inheritDoc}
         */
        @NotNull
        @Override
        public String toString()
        {
            return
                  "{ \"reloadReceived\": " + m__bReloadReceived + '"'
                + ", \"super\": " + super.toString()
                + ", \"class\": \"" + MyNettyServerDebuggingService.class.getSimpleName() + '"'
                + ", \"package\": \"" + MyNettyServerDebuggingService.class.getPackage().getName() + "\" }";
        }
    }

    /**
     * A test client.
     */
    public static class NettyClient
    {
        /**
         * The host.
         */
        private String m__strHost;

        /**
         * The port.
         */
        private int m__iPort;

        /**
         * The message.
         */
        private String m__strMessage;

        /**
         * Creates a new instance.
         * @param host the host.
         * @param port the port.
         * @param msg the message.
         */
        public NettyClient(@NotNull final String host, final int port, @NotNull final String msg)
        {
            immutableSetHost(host);
            immutableSetPort(port);
            immutableSetMessage(msg);
        }

        /**
         * Specifies the host.
         * @param host the host.
         */
        protected final void immutableSetHost(@NotNull final String host)
        {
            this.m__strHost = host;
        }

        /**
         * Specifies the host.
         * @param host the host.
         */
        @SuppressWarnings("unused")
        protected void setHost(@NotNull final String host)
        {
            immutableSetHost(host);
        }

        /**
         * Retrieves the host.
         * @return the host.
         */
        @NotNull
        public String getHost()
        {
            return m__strHost;
        }

        /**
         * Specifies the port.
         * @param port the port.
         */
        protected final void immutableSetPort(final int port)
        {
            this.m__iPort = port;
        }

        /**
         * Specifies the port.
         * @param port the port.
         */
        @SuppressWarnings("unused")
        protected void setPort(final int port)
        {
            this.m__iPort = port;
        }

        /**
         * Retrieves the port.
         * @return such port.
         */
        public int getPort()
        {
            return m__iPort;
        }

        /**
         * Specifies the message.
         * @param message the message.
         */
        protected final void immutableSetMessage(@NotNull final String message)
        {
            this.m__strMessage = message;
        }

        /**
         * Specifies the message.
         * @param message the message.
         */
        @SuppressWarnings("unused")
        protected void setMessage(@NotNull final String message)
        {
            immutableSetMessage(message);
        }

        /**
         * Retrieves the message.
         * @return the message.
         */
        @NotNull
        public String getMessage()
        {
            return m__strMessage;
        }

        /**
         * Connects to the server.
         */
        public void connect()
            throws Exception
        {
            connect(getHost(), getPort(), getMessage());
        }

        /**
         * Connects to the server.
         * @param host the host.
         * @param port the port.
         * @param message the message.
         */
        protected void connect(@NotNull final String host, final int port, @NotNull final String message)
            throws Exception
        {
            @NotNull final EventLoopGroup group = new NioEventLoopGroup();
            try
            {
                @NotNull final Bootstrap b = new Bootstrap();
                b.group(group)
                 .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port))
                .handler(new ChannelInitializer<SocketChannel>()
                {
                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public void initChannel(@NotNull final SocketChannel ch)
                        throws Exception
                    {
                        ch.pipeline().addLast(
                            new NettyClientHandler(message));
                    }
                });
                @NotNull final ChannelFuture f = b.connect().sync();
                f.channel().closeFuture().sync();
            }
            finally
            {
                group.shutdownGracefully().sync();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NotNull
        public String toString()
        {
            return
                  "{ \"host\": \"" + m__strHost + '"'
                + ", \"port\": " + m__iPort
                + ", \"message\": \"" + m__strMessage + '"'
                + ", \"class\": \"" + NettyClient.class.getSimpleName() + '"'
                + ", \"package\": \"" + NettyClient.class.getPackage() + "\" }";
        }
    }

    /**
     * The netty client.
     */
    @Sharable
    public static class NettyClientHandler
        extends SimpleChannelInboundHandler<ByteBuf>
    {
        /**
         * The message.
         */
        private String m__strMessage;

        /**
         * Creates a new instance.
         * @param msg the message.
         */
        public NettyClientHandler(@NotNull final String msg)
        {
            immutableSetMessage(msg);
        }


        /**
         * Specifies the message.
         * @param message the message.
         */
        protected final void immutableSetMessage(@NotNull final String message)
        {
            this.m__strMessage = message;
        }

        /**
         * Specifies the message.
         * @param message the message.
         */
        @SuppressWarnings("unused")
        protected void setMessage(@NotNull final String message)
        {
            immutableSetMessage(message);
        }

        /**
         * Retrieves the message.
         * @return the message.
         */
        @NotNull
        public String getMessage()
        {
            return m__strMessage;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void channelActive(final ChannelHandlerContext ctx) throws Exception
        {
            ctx.writeAndFlush(Unpooled.copiedBuffer(getMessage(), CharsetUtil.UTF_8));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void messageReceived(final ChannelHandlerContext ctx, final ByteBuf msg) throws Exception
        {
            LogFactory.getLog(NettyServerDebuggingServiceTest.NettyClientHandler.class).info(
                "Client received: " + msg);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @NotNull
        public String toString()
        {
            return
                  "{ \"message\": \"" + m__strMessage + '"'
                + ", \"class\": \"" + NettyClientHandler.class.getSimpleName() + '"'
                + ", \"package\": \"" + NettyClientHandler.class.getPackage() + "\" }";
        }
    }
}
