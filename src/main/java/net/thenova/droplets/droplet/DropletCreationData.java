package net.thenova.droplets.droplet;

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
public final class DropletCreationData {

    private final Consumer<Droplet> consumer;
    private final String data;

    /**
     * Creates Droplet meta.
     * @param consumer The consumer.
     * @param data The data.
     */
    public DropletCreationData(Consumer<Droplet> consumer, String data) {
        this.consumer = consumer;
        this.data = data == null ? "" : data;
    }

    /**
     * Gets the consumer.
     * @return The consumer, potentially null.
     */
    Consumer<Droplet> getConsumer() {
        return consumer;
    }

    /**
     * Gets the data.
     * @return The data.
     */
    String getData() {
        return data;
    }

}
