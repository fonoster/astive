/* 
 * Copyright (C) 2017 by Fonoster Inc (http://fonoster.com)
 * http://github.com/fonoster/astive
 *
 * This file is part of Astive
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fonoster.astive.server;

import com.sun.corba.se.impl.util.Version;
import org.apache.commons.cli.*;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import com.fonoster.astive.AstiveException;
import com.fonoster.astive.server.admin.AdminCommand;
import com.fonoster.astive.server.admin.AdminDaemon;
import com.fonoster.astive.server.admin.AdminDaemonClient;
import com.fonoster.astive.server.appmanager.DeployerManager;
import com.fonoster.astive.server.monitor.ConnectionMonitor;
import com.fonoster.astive.server.monitor.FastAgiConnectionMonitor;
import com.fonoster.astive.server.security.AstPolicy;
import com.fonoster.astive.server.utils.InitOutput;
import com.fonoster.astive.telnet.TelnetServer;
import com.fonoster.astive.util.AppLocale;
import com.fonoster.astive.util.NetUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;

/**
 * Default implementation of {@link AbstractAstiveServer}.
 *
 * @since 1.0
 * @see AbstractAstiveServer
 */
public class AstiveServer extends AbstractAstiveServer {
    private static final Logger LOG = Logger.getLogger(AstiveServer.class);
    private static ServiceProperties adminDaemonSP;
    private static ServiceProperties astivedSP;
    private static ServiceProperties telnedSP;
    private static String ASTIVED_PROPERTIES =
            AbstractAstiveServer.ASTIVE_HOME + "/conf/astived.properties";
    private static String ADMIN_DAEMON_PROPERTIES =
            AbstractAstiveServer.ASTIVE_HOME + "/conf/admin.properties";
    private static String TELNED_PROPERTIES =
            AbstractAstiveServer.ASTIVE_HOME + "/conf/telned.properties";
    private ExecutorService executorService;
    private ConnectionMonitor monitor;
    
    public AstiveServer(int port, int backlog, InetAddress bindAddr)
            throws SystemException, IOException {
        super(port, backlog, bindAddr);
        // A separate thread for services: Admin , Astive  and Telnet.
        executorService = Executors.newFixedThreadPool(3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void launchConnectionMonitor() {
        monitor = new FastAgiConnectionMonitor(this, astivedSP.getBacklog());
        executorService.execute((Runnable) monitor);
    }

    public static void main(String[] args) throws Exception {

        DOMConfigurator.configure(AbstractAstiveServer.ASTIVE_HOME + "/conf/log4j.xml");

        astivedSP = getServiceProperties(ASTIVED_PROPERTIES, "astived");
        adminDaemonSP = getServiceProperties(ADMIN_DAEMON_PROPERTIES, "admin thread");
        telnedSP = getServiceProperties(TELNED_PROPERTIES, "telned");

        ArrayList<ServiceProperties> serviceProperties = new ArrayList();
        serviceProperties.add(astivedSP);

        // Adding security measure
        AstPolicy ap = AstPolicy.getInstance();
        ap.addPermissions(astivedSP);
        ap.addPermissions(adminDaemonSP);
        ap.addPermissions(telnedSP);

        if (!adminDaemonSP.isDisabled()) {
            serviceProperties.add(adminDaemonSP);
        }

        if (!telnedSP.isDisabled()) {
            serviceProperties.add(telnedSP);
        }

        if ((args.length == 0) || args[0].equals("-h") || args[0].equals("--help")) {
            printUsage();
            System.exit(1);
        }

        // Create a Parser
        CommandLineParser parser = new BasicParser();

        Options start = new Options();

        start.addOption("h", "help", false, AppLocale.getI18n("optionHelp"));
        start.addOption("v", "version", false, AppLocale.getI18n("optionVersion"));
        start.addOption("d", "debug", false, AppLocale.getI18n("optionDebug"));
        start.addOption("q", "quiet", false, AppLocale.getI18n("optionQuiet"));

        start.addOption(OptionBuilder.hasArg(true).withArgName("host")
                .withLongOpt("admin-bind")
                .withDescription(AppLocale.getI18n("optionBind",
                new Object[]{"admin"})).create());

        start.addOption(OptionBuilder.hasArg(true).withArgName("port")
                .withLongOpt("admin-port")
                .withDescription(AppLocale.getI18n("optionPort",
                new Object[]{"admin"})).create());

        start.addOption(OptionBuilder.hasArg(true).withArgName("port")
                .withLongOpt("astived-port")
                .withDescription(AppLocale.getI18n("optionPort",
                new Object[]{"astived"})).create());

        start.addOption(OptionBuilder.hasArg(true).withArgName("host")
                .withLongOpt("astived-host")
                .withDescription(AppLocale.getI18n("optionBind",
                new Object[]{"astived"})).create());

        start.addOption(OptionBuilder.hasArg(true).withArgName("port")
                .withLongOpt("telned-port")
                .withDescription(AppLocale.getI18n("optionPort",
                new Object[]{"telned"})).create());

        start.addOption(OptionBuilder.hasArg(true).withArgName("host")
                .withLongOpt("telned-host")
                .withDescription(AppLocale.getI18n("optionBind",
                new Object[]{"telned"})).create());

        Options stop = new Options();
        stop.addOption(OptionBuilder.hasArg(true).withArgName("host")
                .withLongOpt("host")
                .withDescription(AppLocale.getI18n("optionHelp")).create());

        stop.addOption("h", "host", false,
                AppLocale.getI18n("optionStopHost",
                new Object[]{DEFAULT_AGI_SERVER_BIND_ADDR}));

        stop.addOption("p", "port", false,
                AppLocale.getI18n("optionStopPort",
                new Object[]{DEFAULT_AGI_SERVER_PORT}));

        Options deploy = new Options();
        deploy.addOption("h", "help", false, AppLocale.getI18n("optionHelp"));

        Options undeploy = new Options();
        undeploy.addOption("h", "help", false, AppLocale.getI18n("optionHelp"));

        if (args.length == 0) {
            printUsage();
            System.exit(1);
        } else if (!isCommand(args[0])) {
            printUnavailableCmd(args[0]);
            System.exit(1);
        }

        AdminCommand cmd = AdminCommand.get(args[0]);

        // Parse the program arguments
        try {
            if (cmd.equals(AdminCommand.START)) {

                CommandLine commandLine = parser.parse(start, args);

                Logger root = LogManager.getRootLogger();
                Enumeration allLoggers = root.getLoggerRepository().getCurrentLoggers();

                if (commandLine.hasOption('q')) {
                    root.setLevel(Level.ERROR);
                    while (allLoggers.hasMoreElements()) {
                        Category tmpLogger = (Category) allLoggers.nextElement();
                        tmpLogger.setLevel(Level.ERROR);
                    }
                } else if (commandLine.hasOption('d')) {
                    root.setLevel(Level.DEBUG);
                    while (allLoggers.hasMoreElements()) {
                        Category tmpLogger = (Category) allLoggers.nextElement();
                        tmpLogger.setLevel(Level.DEBUG);
                    }
                } else {
                    root.setLevel(Level.INFO);
                    while (allLoggers.hasMoreElements()) {
                        Category tmpLogger = (Category) allLoggers.nextElement();
                        tmpLogger.setLevel(Level.INFO);
                    }
                }

                if (commandLine.hasOption('h')) {
                    printUsage(cmd, start);
                    System.exit(0);
                }

                if (commandLine.hasOption('v')) {
                    out.println(AppLocale.getI18n("astivedVersion", new String[]{
                        Version.VERSION, Version.BUILD_TIME}));
                    System.exit(0);
                }

                if (commandLine.hasOption("astived-bind")) {
                    astivedSP.setBindAddr(InetAddress.getByName(commandLine.getOptionValue("astived-port")));
                }

                if (commandLine.hasOption("astived-port")) {
                    astivedSP.setPort(Integer.parseInt(commandLine.getOptionValue("astived-port")));
                }

                if (commandLine.hasOption("admin-bind")) {
                    adminDaemonSP.setBindAddr(InetAddress.getByName(commandLine.getOptionValue("admin-bind")));
                }

                if (commandLine.hasOption("admin-port")) {
                    adminDaemonSP.setPort(Integer.parseInt(commandLine.getOptionValue("admin-port")));
                }

                if (commandLine.hasOption("telned-bind")) {
                    telnedSP.setBindAddr(InetAddress.getByName(commandLine.getOptionValue("telned-bind")));
                }

                if (commandLine.hasOption("telned-port")) {
                    telnedSP.setPort(Integer.parseInt(commandLine.getOptionValue("telned-port")));
                }

                if (!NetUtil.isPortAvailable(astivedSP.getPort())) {
                    out.println(AppLocale.getI18n("errorCantStartFastAgiServerSocket",
                            new Object[]{astivedSP.getBindAddr().getHostAddress(),
                        astivedSP.getPort()}));
                    System.exit(-1);
                }

                if (!NetUtil.isPortAvailable(adminDaemonSP.getPort())) {
                    adminDaemonSP.setUnableToOpen(true);
                }

                if (!NetUtil.isPortAvailable(telnedSP.getPort())) {
                    telnedSP.setUnableToOpen(true);
                }

                new InitOutput().printInit(serviceProperties);

                AstiveServer server =
                        new AstiveServer(astivedSP.getPort(), astivedSP.getBacklog(), astivedSP.getBindAddr());
                server.start();
            }

            if (!cmd.equals(AdminCommand.START) && adminDaemonSP.isDisabled()) {
                LOG.warn("errorUnableToAccessAdminDaemon");
            }

            if (cmd.equals(AdminCommand.STOP)) {
                CommandLine commandLine = parser.parse(stop, args);

                if (commandLine.hasOption("--help")) {
                    printUsage(cmd, stop);
                    System.exit(0);
                }

                if (commandLine.hasOption('h')) {
                    if (commandLine.getOptionValue('h') == null) {
                        printUsage(cmd, stop);
                        System.exit(0);
                    }

                    astivedSP.setBindAddr(InetAddress.getByName(commandLine.getOptionValue('h')));
                }

                if (commandLine.hasOption('p')) {
                    if (commandLine.getOptionValue('p') == null) {
                        printUsage(cmd, stop);
                        System.exit(0);
                    }

                    astivedSP.setPort(Integer.parseInt(commandLine.getOptionValue('p')));
                }

                AdminDaemonClient adClient = new AdminDaemonClient(
                        adminDaemonSP.getBindAddr(), adminDaemonSP.getPort());
                adClient.stop();
            }

            // TODO: This needs to be researched before a full implementation.
            // for now is only possible to do deployments into a local server.
            if (cmd.equals(AdminCommand.DEPLOY)) {
                CommandLine commandLine = parser.parse(deploy, args);

                if (args.length < 2) {
                    printUsage(cmd, deploy);
                    System.exit(1);
                } else if (commandLine.hasOption('h')) {
                    printUsage(cmd, deploy);
                    System.exit(0);
                }

                AdminDaemonClient adClient = new AdminDaemonClient(
                        adminDaemonSP.getBindAddr(), adminDaemonSP.getPort());
                adClient.deploy(args[1]);
            }

            if (cmd.equals(AdminCommand.UNDEPLOY)) {

                CommandLine commandLine = parser.parse(undeploy, args);

                if (args.length < 2) {
                    printUsage(cmd, undeploy);
                    System.exit(1);
                } else if (commandLine.hasOption('h')) {
                    printUsage(cmd, undeploy);
                    System.exit(0);
                }

                AdminDaemonClient adClient =
                        new AdminDaemonClient(adminDaemonSP.getBindAddr(), adminDaemonSP.getPort());
                adClient.undeploy(args[1]);
            }
        } catch (java.net.ConnectException ex) {
            LOG.error(AppLocale.getI18n("errorServerNotRunning"));
        } catch (Exception ex) {
            LOG.error(AppLocale.getI18n("errorUnexpectedFailure", new Object[]{ex.getMessage()}));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Support methods">
    private static ServiceProperties getServiceProperties(String propPath, String serviceName)
            throws SystemException, IOException {
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream(propPath));

            return new ServicePropertiesImpl(prop, serviceName);
        } catch (FileNotFoundException ex) {
            throw new SystemException(AppLocale.getI18n("errorUnableToReadFile",
                    new Object[]{propPath, ex.getMessage()}));
        }
    }

    private static boolean isCommand(String cmd) {
        AdminCommand ac = AdminCommand.get(cmd);

        return ac != null;
    }

    private static boolean isFileJar(String file) {
        if (file.endsWith(".jar")) {
            return true;
        }

        return false;
    }

    private static void printUnavailableCmd(String cmd) {
        out.println(AppLocale.getI18n("errorUnavailableCommand", new Object[]{cmd}));
        out.println(AppLocale.getI18n("astivedCommands"));
    }

    private static void printUsage() {
        out.println(AppLocale.getI18n("astivedUsage"));
        out.println(AppLocale.getI18n("astivedCommands"));
        out.println(AppLocale.getI18n("cliHelp"));
        out.println(AppLocale.getI18n("cliFooter"));
    }

    private static void printUsage(AdminCommand ac, Options options) {
        String command = ac.getCommand();
        // capitalize command
        command = Character.toUpperCase(command.charAt(0)) + command.substring(1);
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.setWidth(80);
        helpFormatter.printHelp(AppLocale.getI18n("command" + command + "Usage"),
                AppLocale.getI18n("cliHeader"), options,
                AppLocale.getI18n("cliFooter"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() throws SystemException, AstiveException {
        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageStartingAstiveServer"));
        }
        
        super.start();

        // Load properties for admin daemon
        InetAddress adminBindAddr = adminDaemonSP.getBindAddr();
        int adminPort = adminDaemonSP.getPort();
        int adminBacklog = adminDaemonSP.getBacklog();

        // Load properties for telnet
        InetAddress telnedBindAddr = telnedSP.getBindAddr();
        int telnedPort = telnedSP.getPort();
        int telnedBacklog = telnedSP.getBacklog();

        // Load apps already in "apps"
        DeployerManager.getInstance();

        try {
            if (!adminDaemonSP.isDisabled()) {
                AdminDaemon admin = new AdminDaemon(adminPort, adminBacklog, adminBindAddr, this);
                executorService.execute(admin);
            }

            if (!telnedSP.isDisabled()) {
                final AstiveServer server = this;

                TelnetServer ts =
                        new TelnetServer(telnedPort, telnedBacklog, telnedBindAddr) {
                    @Override
                    public void stop() {
                        try {
                            server.stop();
                        } catch (SystemException ex) {
                            LOG.error(AppLocale.getI18n("errorUnexpectedFailure", new String[]{ex.getMessage()}));
                        }
                    }

                    @Override
                    public List<String> lookup() {
                        List<String> apps = new ArrayList();
                        AstDB astDB = MyAstDB.getInstance();

                        try {
                            for (AstObj astObj : astDB.getApps()) {
                                StringBuilder sb = new StringBuilder("@App(name=");
                                sb.append(astObj.getInfo().getName());
                                sb.append(" ");
                                sb.append("deploymentId=");
                                sb.append(astObj.getDeploymentId());
                                apps.add(sb.toString());
                            }
                        } catch (AstiveException ex) {
                            LOG.error(AppLocale.getI18n("errorUnexpectedFailure", new String[]{ex.getMessage()}));
                        }

                        return apps;
                    }

                    @Override
                    public String version() {
                        return server.getVersion();
                    }

                    @Override
                    public String system() {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                };

                executorService.execute(ts);
            }
            
            if (LOG.isDebugEnabled()) {
                LOG.debug(AppLocale.getI18n("messageDone"));
            }
        } catch (IOException ex) {
            LOG.warn(AppLocale.getI18n("errorUnexpectedFailure", new Object[]{ex.getMessage()}));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() throws SystemException {
        super.stop();
        executorService.shutdown();
        LOG.info(AppLocale.getI18n("messageShutdown"));
    }
    // </editor-fold>
}
