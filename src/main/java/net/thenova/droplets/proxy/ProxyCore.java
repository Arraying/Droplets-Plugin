package net.thenova.droplets.proxy;

import de.arraying.kotys.JSON;
import net.md_5.bungee.api.plugin.Plugin;
import net.thenova.droplets.Core;
import net.thenova.droplets.common.redis.Redis;
import net.thenova.droplets.common.redis.RedisConstants;
import net.thenova.droplets.proxy.command.DropletCommand;

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
public final class ProxyCore extends Plugin {

    /**
     * When the plugin is enabled.
     */
    @Override
    public void onEnable() {
        Core.INSTANCE.setProxyCore(this);
        if(!Core.INSTANCE.init()) {
            getLogger().severe("Initialization failed.");
            return;
        }
        getProxy().getPluginManager().registerCommand(this, new DropletCommand());
        Redis.INSTANCE.dispatch(RedisConstants.ACTION_QUERY, new JSON());
    }

}
