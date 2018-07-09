package net.thenova.droplets.proxy.command.internal;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.thenova.droplets.droplet.Droplet;
import net.thenova.droplets.droplet.DropletHandler;

import java.util.ArrayList;
import java.util.Collections;
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
public abstract class AbstractSubCommand implements ICommand {

    private final List<String> triggers;

    /**
     * Creates a new abstract sub command.
     * @param name The name.
     * @param aliases The aliases.
     */
    public AbstractSubCommand(String name, String... aliases) {
        triggers = new ArrayList<>();
        triggers.add(name);
        Collections.addAll(triggers, aliases);
    }

    /**
     * Gets the command triggers.
     * @return The triggers.
     */
    List<String> getTriggers() {
        return triggers;
    }

    /**
     * Gets a droplet via the context.
     * @param context The context.
     * @return The Droplet, possibly null.
     */
    protected Droplet fromContext(Context context) {
        Droplet droplet = null;
        if(context.getArgs().length < 1) {
            if(context.getSender() instanceof ProxiedPlayer) {
                droplet = DropletHandler.INSTANCE.getDroplet(((ProxiedPlayer) context.getSender()).getServer().getInfo().getName());
            }
        } else {
            droplet = DropletHandler.INSTANCE.getDroplet(context.getArgs()[0]);
        }
        return droplet;
    }

}
