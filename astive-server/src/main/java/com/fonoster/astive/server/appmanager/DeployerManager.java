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
package com.fonoster.astive.server.appmanager;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import com.fonoster.astive.AstiveException;
import com.fonoster.astive.astivlet.Astivlet;
import com.fonoster.astive.server.AbstractAstiveServer;
import com.fonoster.astive.server.AstDB;
import com.fonoster.astive.server.AstObj;
import com.fonoster.astive.server.MyAstDB;
import com.fonoster.astive.util.AppLocale;
import org.xeustechnologies.jcl.exception.JclException;
import org.xeustechnologies.jcl.proxy.CglibProxyProvider;
import org.xeustechnologies.jcl.proxy.ProxyProviderFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Final implementation of interface {@link Deployer}.
 *
 * @since 1.0
 * @see Deployer
 */
public final class DeployerManager implements Deployer, AstDB {
    private static final Logger LOG = Logger.getLogger(DeployerManager.class);
    private static final DeployerManager INSTANCE = new DeployerManager();
    private static AstDB astDB;

    /**
     * Create a new DeployerManager object.
     */
    private DeployerManager() {
        ProxyProviderFactory.setDefaultProxyProvider(new CglibProxyProvider());
        astDB = MyAstDB.getInstance();

        try {
            deployApps();
        } catch (AstiveException ex) {
            LOG.error(AppLocale.getI18n("errorUnexpectedFailure", new Object[]{ex.getMessage()}));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addApp(AstObj astObj) throws AstiveException {
        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageAddingApp", new Object[]{astObj.getDeploymentId()}));
        }

        astDB.addApp(astObj);

        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageDone"));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean appExist(String deploymentId) {
        return astDB.appExist(deploymentId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deploy(String appPath) throws AstiveException {
        try {
            if (LOG.isInfoEnabled()) {
                LOG.info(AppLocale.getI18n("messageDeployingApp", new Object[]{appPath}));
            }

            File srcFile = new File(appPath);
            File appsFolder = new File(AbstractAstiveServer.ASTIVE_APPS);

            // The name of the file must be use to undeploy the apps.
            AstObj app = new AstObj(srcFile.getName(), srcFile.getCanonicalPath());

            if (!appExist(app.getDeploymentId())) {
                addApp(app);

                File f = new File(AbstractAstiveServer.ASTIVE_APPS + srcFile.getName());
                
                if (!f.exists()) {               
                    FileUtils.copyFileToDirectory(srcFile, appsFolder);
                }               
            } else {
                LOG.warn(AppLocale.getI18n("errorAppAlreadyExist",
                        new Object[]{appPath, AbstractAstiveServer.ASTIVE_APPS}));

                return;
            }

            if (LOG.isInfoEnabled()) {
                LOG.info(AppLocale.getI18n("messageAppDeployed",
                        new Object[]{app.getInfo().getName()}));
            }
        } catch (IOException ex) {
            throw new AstiveException(ex);
        } catch (JclException ex) {
            throw new AstiveException(ex);
        } catch (AstiveException ex) {
           throw new AstiveException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void deployApps() throws AstiveException {
        if (LOG.isDebugEnabled()) {
            LOG.debug(AppLocale.getI18n("messageDeployingApps", new Object[]{AbstractAstiveServer.ASTIVE_APPS}));
        }

        //String apps = ASTIVE_APPS;
        File f = new File(AbstractAstiveServer.ASTIVE_APPS);

        if (f.isDirectory()) {
            File[] files = f.listFiles();

            for (int i = 0; i < files.length; i++) {
                String file = files[i].getAbsolutePath();

                if (file.toLowerCase().endsWith(".jar")) {
                    deploy(file);
                } else {
                    LOG.warn(AppLocale.getI18n("errorInvalidApp", new Object[]{file}));
                }
            }
        } else {
            LOG.error(AppLocale.getI18n("errorUnexpectedFailure",
                    new Object[]{AbstractAstiveServer.ASTIVE_APPS}));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AstObj getApp(String deploymentId) throws AstiveException {
        AstObj result = astDB.getApp(deploymentId);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AstObj> getApps() throws AstiveException {
        return astDB.getApps();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Astivlet getAstivlet(String urlStr) throws AstiveException {
        return astDB.getAstivlet(urlStr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getAstivletsURLs() throws AstiveException {
        return astDB.getAstivletsURLs();
    }

    /**
     * {@inheritDoc}
     */
    public static DeployerManager getInstance() {
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeApp(AstObj astObj) throws AstiveException {
        astDB.removeApp(astObj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undeploy(String app) throws AstiveException {
        if (LOG.isInfoEnabled()) {
            LOG.info(AppLocale.getI18n("messageUndeployingApp", new Object[]{app}));
        }

        try {
            File f = new File(AbstractAstiveServer.ASTIVE_APPS + app);
            String file = AbstractAstiveServer.ASTIVE_APPS + app;

            AstObj astObj = new AstObj(app, file);

            if (appExist(astObj.getDeploymentId())) {
                astDB.removeApp(astObj);

                if (f.exists()) {
                    f.delete();

                    if (LOG.isInfoEnabled()) {
                        LOG.info(AppLocale.getI18n("messageAppUndeployed", new Object[]{app}));
                    }
                } else {
                    LOG.warn(AppLocale.getI18n("errorAppFileDoesNotExist", new Object[]{app}));
                }
            } else {
                LOG.warn(AppLocale.getI18n("errorAppDoesNotExist", new Object[]{app}));
            }
        } catch (org.xeustechnologies.jcl.exception.JclException ex) {
            LOG.warn(AppLocale.getI18n("errorAppDoesNotExist", new Object[]{app}));
            throw new AstiveException(ex);
        } catch (AstiveException ex) {
            LOG.warn(AppLocale.getI18n("errorAppDoesNotExist", new Object[]{app}));
            throw new AstiveException(ex);
        }
    }
}
