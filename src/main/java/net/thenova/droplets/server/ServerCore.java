package net.thenova.droplets.server;

import net.thenova.droplets.Core;
import net.thenova.droplets.common.redis.Redis;
import net.thenova.droplets.common.redis.RedisConstants;
import net.thenova.droplets.droplet.Droplet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

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
public final class ServerCore extends JavaPlugin {

    private List<ServerFinalizer> serverFinalizers = new ArrayList<>();

    /**
     * When the plugin enables.
     */
    @Override
    public void onEnable() {
        Core.INSTANCE.setServerCore(this);
        if(!Core.INSTANCE.init()) {
            getLogger().severe("Initialization failed.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        String data = Core.INSTANCE.getConfiguration().getJSON().string("data");
        data = data == null ? "" : data;
        Redis.INSTANCE.dispatch(RedisConstants.ACTION_IDENTIFY, Droplet.Util.toIdentifyData(Bukkit.getIp(), Bukkit.getPort(), data));
    }

    /**
     * Registers a finalizer.
     * @param finalizer The finalizer.
     */
    public void registerFinalizer(ServerFinalizer finalizer) {
        serverFinalizers.add(finalizer);
    }

    /**
     * Kills the droplet.
     */
    public void kill() {
        for(ServerFinalizer finalizer : serverFinalizers) {
            finalizer.finalizeDroplet();
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
    }

}
