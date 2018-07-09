package net.thenova.droplets.proxy.event;

import net.md_5.bungee.api.plugin.Event;
import net.thenova.droplets.droplet.Droplet;

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
public abstract class DropletEvent extends Event {

    private final Droplet droplet;

    /**
     * Creates a new droplet event.
     * @param droplet The droplet.
     */
    DropletEvent(Droplet droplet) {
        this.droplet = droplet;
    }

    /**
     * Gets the droplet.
     * @return The droplet.
     */
    @SuppressWarnings("unused")
    public Droplet getDroplet() {
        return droplet;
    }

}
