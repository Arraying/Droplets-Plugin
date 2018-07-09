package net.thenova.droplets.droplet;

import de.arraying.kotys.JSON;
import de.arraying.kotys.JSONArray;
import net.thenova.droplets.common.redis.Redis;
import net.thenova.droplets.common.redis.RedisConstants;

import java.net.InetSocketAddress;
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
@SuppressWarnings("WeakerAccess")
public final class Droplet {

    private String identifier;
    private final InetSocketAddress address;
    private final String data;

    /**
     * Creates a new droplet.
     * @param identifier The identifier of the droplet.
     * @param address The address.
     * @param data The data.
     */
    public Droplet(String identifier, InetSocketAddress address, String data) {
        this.identifier = identifier;
        this.address = address;
        this.data = data;
    }

    /**
     * Gets the identifier.
     * @return The identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Gets the address.
     * @return The address.
     */
    public InetSocketAddress getAddress() {
        return address;
    }

    /**
     * Gets the data.
     * @return The data.
     */
    @SuppressWarnings("unused")
    public String getData() {
        return data;
    }

    /**
     * Gets the template.
     * @return The template.
     */
    public String getTemplate() {
        return identifier.substring(0, identifier.indexOf(RedisConstants.SPLIT_IDENTIFIER));
    }

    /**
     * Deletes the droplet.
     */
    public void delete() {
        Redis.INSTANCE.dispatch(RedisConstants.ACTION_DELETE, new JSON().put("i", identifier));
        DropletHandler.INSTANCE.deleteInternal(this);
    }

    /**
     * The utility class for droplets.
     */
    public static final class Util {

        /**
         * Creates a droplet from an identify payload.
         * @param data The data.
         * @return The droplet.
         */
        public static Droplet fromIdentifyData(JSON data) {
            return new Droplet(
                    data.string(RedisConstants.DATA_IDENTIFIER),
                    new InetSocketAddress(data.string(RedisConstants.DATA_IP), data.integer(RedisConstants.DATA_PORT)),
                    data.string(RedisConstants.DATA_DATA)
            );
        }

        /**
         * Creates a droplet identify payload from some information.
         * @param ip The IP of the droplet.
         * @param port The port of the droplet.
         * @param data The data.
         * @return A JSON object.
         */
        public static JSON toIdentifyData(String ip, int port, String data) {
            return new JSON()
                    .put(RedisConstants.DATA_IDENTIFIER, Redis.INSTANCE.getSelf())
                    .put(RedisConstants.DATA_IP, ip)
                    .put(RedisConstants.DATA_PORT, port)
                    .put(RedisConstants.DATA_DATA, data);
        }

        /**
         * Gets a list of droplets from JSON data.
         * @param dataRaw The data.
         * @return A potentially empty list.
         */
        public static List<Droplet> fromQueryData(JSON dataRaw) {
            List<Droplet> list = new ArrayList<>();
            JSONArray data = dataRaw.array(RedisConstants.DATA_QUERY_LIST);
            for(int i = 0; i < data.length(); i++) {
                JSON json = data.json(i);
                list.add(fromIdentifyData(json));
            }
            return list;
        }

    }

}
