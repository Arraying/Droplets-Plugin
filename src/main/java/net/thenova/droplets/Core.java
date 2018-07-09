package net.thenova.droplets;

import net.thenova.droplets.common.config.Configuration;
import net.thenova.droplets.common.redis.Redis;
import net.thenova.droplets.proxy.ProxyCore;
import net.thenova.droplets.server.ServerCore;
import redis.clients.jedis.exceptions.JedisException;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Copyright 2018 Arraying
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public enum Core {

    /**
     * The singleton instance.
     */
    INSTANCE;

    /**
     * The main method.
     * @param args The arguments.
     */
    public static void main(String[] args) {
       System.out.println("You're doing something very wrong.");
    }

    private Logger logger;
    private Configuration configuration = new Configuration();
    private ProxyCore proxyCore;
    private ServerCore serverCore;

    /**
     * Attempts to initialize the plugin.
     * @return True if success, false if failure.
     */
    public boolean init() {
        try {
            configuration.load(getParent());
            logger.info("Configuration: " + configuration.getJSON());
            if(!configuration.isValid()) {
                logger.severe("Configuration is invalid.");
                return false;
            }
            Redis.INSTANCE.init();
        } catch(IOException | JedisException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Gets the logger.
     * @return The logger.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Gets the configuration.
     * @return The configuration.
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Gets the proxy core.
     * @return The proxy core, null if the plugin is running on Spigot.
     */
    @SuppressWarnings("unused")
    public ProxyCore getProxyCore() {
        return proxyCore;
    }

    /**
     * Gets the server core.
     * @return The server core, null if the plugin is running on Bungee.
     */
    public ServerCore getServerCore() {
        return serverCore;
    }

    /**
     * Sets the proxy core.
     * @param proxyCore The proxy core.
     */
    public void setProxyCore(ProxyCore proxyCore) {
        this.proxyCore = proxyCore;
        logger = proxyCore.getLogger();
    }

    /**
     * Sets the server core.
     * @param serverCore The server core.
     */
    public void setServerCore(ServerCore serverCore) {
        this.serverCore = serverCore;
        logger = serverCore.getLogger();

    }

    /**
     * Gets the parent file.
     * @return The file, not null.
     * @throws IOException If something went wrong.
     */
    private File getParent() throws IOException {
        if(proxyCore != null) {
            return proxyCore.getDataFolder();
        } else if(serverCore != null) {
            return serverCore.getDataFolder();
        } else {
            throw new IOException("No parent folder found (both proxy + server are null).");
        }
    }

}
