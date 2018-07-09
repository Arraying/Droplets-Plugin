package net.thenova.droplets.droplet;

import de.arraying.kotys.JSON;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.thenova.droplets.Core;
import net.thenova.droplets.common.redis.Redis;
import net.thenova.droplets.common.redis.RedisConstants;
import net.thenova.droplets.proxy.event.DropletAvailableEvent;
import net.thenova.droplets.proxy.event.DropletUnavailableEvent;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

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
public enum DropletHandler {

    /**
     * The singleton droplet handler.
     */
    INSTANCE;

    private final Consumption consumption = new Consumption();
    private final Map<String, Droplet> droplets = new ConcurrentHashMap<>();

    /**
     * Gets the droplet by identifier.
     * @param identifier The identifier.
     * @return The droplet, or null if it does not exist.
     */
    public Droplet getDroplet(String identifier) {
        return droplets.get(identifier);
    }

    /**
     * Gets all droplets.
     * @return A collection of droplets.
     */
    public Collection<Droplet> getAll() {
        return droplets.values();
    }

    /**
     * Create creates a new droplet.
     * @param template The template of the droplet.
     * @param data Droplet creation data.
     */
    public void create(String template, DropletCreationData data) {
        Redis.INSTANCE.dispatch(RedisConstants.ACTION_CREATE, new JSON()
                .put(RedisConstants.DATA_CREATE_TEMPLATE, template)
                .put(RedisConstants.DATA_DATA, data.getData())
        );
        consumption.add(template, data.getConsumer());
    }

    /**
     * Register registers a new Droplet that is to be added to the Bungee.
     * @param droplet The droplet.
     */
    public void register(Droplet droplet) {
        ServerInfo info = ProxyServer.getInstance().constructServerInfo(droplet.getIdentifier(),
                droplet.getAddress(),
                "Droplet",
                false);
        ProxyServer.getInstance().getServers().put(droplet.getIdentifier(), info);
        droplets.put(droplet.getIdentifier(), droplet);
        consumption.invoke(droplet);
        ProxyServer.getInstance().getPluginManager().callEvent(new DropletAvailableEvent(droplet));
        Core.INSTANCE.getLogger().info("Registered droplet " + droplet.getIdentifier() + ".");
    }

    /**
     * Internally handles a droplet deletion.
     * @param droplet The droplet.
     */
    public void deleteInternal(Droplet droplet) {
        ProxyServer proxy = ProxyServer.getInstance();
        ServerInfo server = proxy.getServerInfo(droplet.getIdentifier());
        if(server == null) {
            return;
        }
        String fallback = Core.INSTANCE.getConfiguration().getJSON().string("fallback");
        if(fallback != null) {
            ServerInfo fallbackServer = proxy.getServerInfo(fallback);
            if(fallbackServer != null) {
                for(ProxiedPlayer player : server.getPlayers()) {
                    player.connect(fallbackServer);
                }
            } else {
                Core.INSTANCE.getLogger().warning("Fallback server invalid.");
            }
        } else {
            Core.INSTANCE.getLogger().warning("Fallback server undefined.");
        }
        proxy.getServers().remove(droplet.getIdentifier());
        droplets.remove(droplet.getIdentifier());
        proxy.getPluginManager().callEvent(new DropletUnavailableEvent(droplet));
        Core.INSTANCE.getLogger().info("Unregistered droplet " + droplet.getIdentifier() + ".");
    }

    /**
     * Consumption stores all queued consumers.
     */
    public static final class Consumption {

        private final Map<String, LinkedList<Consumer<Droplet>>> consumerQueue = new ConcurrentHashMap<>();

        /**
         * Adds a consumer to the consumption collection.
         * @param template The template.
         * @param consumer The consumer.
         */
        private void add(String template, Consumer<Droplet> consumer) {
            LinkedList<Consumer<Droplet>> list = consumerQueue.computeIfAbsent(template, k -> new LinkedList<>());
            list.offer(consumer);
        }

        /**
         * Invokes the first consumer for the template.
         * @param droplet The droplet.
         */
        private void invoke(Droplet droplet) {
            LinkedList<Consumer<Droplet>> list = consumerQueue.getOrDefault(droplet.getTemplate(), new LinkedList<>());
            if(!list.isEmpty()) {
                Consumer<Droplet> consumer = list.poll();
                if(consumer != null) {
                    consumer.accept(droplet);
                }
            }
        }

    }

}
