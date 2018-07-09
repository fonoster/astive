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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import com.fonoster.astive.util.AppLocale;

/**
 * Final implementation of {@link ServiceProperties} use by {@link Service}.
 *
 * @see ServiceProperties, Service
 * @since 1.0
 */
public final class ServicePropertiesImpl implements ServiceProperties {
    private InetAddress bindAddr;
    private List<String> onlyFrom;
    private Properties properties;
    private String server;
    private String serviceName;
    private boolean disabled;
    private boolean unableToOpen;
    private int backlog;
    private int port;

    /**
     * Creates a new ServicePropertiesImpl object.
     */
    public ServicePropertiesImpl() {
    }

    /**
     * Creates a new ServicePropertiesImpl object with the parameters indicated
     * in properties and the name of service.
     *
     * @param properties represent the properties file for a particular service.
     * @param serviceName the name to be displayed by the server.
     *
     * @throws SystemException if the service cannot be binded to the bind
     * address.
     */
    public ServicePropertiesImpl(final Properties properties, final String serviceName)
            throws SystemException {
        this.properties = properties;

        setDisabled(Boolean.parseBoolean(properties.get("disabled").toString().trim()));
        setPort(Integer.valueOf(properties.get("port").toString()));
        setBacklog(Integer.valueOf(properties.get("threads").toString().trim()));

        try {
            setBindAddr(InetAddress.getByName(properties.get("bind").toString().trim()));
        } catch (UnknownHostException ex) {
            throw new SystemException(AppLocale.getI18n("errorUnknownHost",
                    new Object[]{
                properties.get("bind").toString().trim()
            }));
        }

        final List<String> onlyFromList = new ArrayList<>();

        if (properties.get("onlyFrom") != null) {
            final String[] l = properties.get("onlyFrom").toString().split(",");

            for (int i = 0; l.length > i; i++) {
                onlyFromList.add(l[i].trim());
            }
        } else {
            // All host
            onlyFromList.add("*");
        }

        setOnlyFrom(onlyFromList);

        setServer((String) properties.get("server"));
        setServiceName(serviceName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBacklog() {
        return backlog;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InetAddress getBindAddr() {
        return bindAddr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getOnlyFrom() {
        return Collections.unmodifiableList(onlyFrom);
    }

    /**
     * Use to return arbitrary parameters.
     *
     * @return a parameter present in the properties file, or null if none.
     */
    public String getPameter(final String parameter) {
        try {
            return properties.get(parameter).toString().trim();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPort() {
        return port;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getServer() {
        return server;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getServiceName() {
        return serviceName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUnableToOpen() {
        return unableToOpen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBacklog(final int backlog) {
        this.backlog = backlog;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBindAddr(final InetAddress bindAddr) {
        this.bindAddr = bindAddr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDisabled(final boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnlyFrom(final List<String> onlyFrom) {
        this.onlyFrom = onlyFrom;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPort(final int port) {
        this.port = port;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setServer(final String server) {
        this.server = server;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUnableToOpen(final boolean unableToOpen) {
        this.unableToOpen = unableToOpen;
    }
}
