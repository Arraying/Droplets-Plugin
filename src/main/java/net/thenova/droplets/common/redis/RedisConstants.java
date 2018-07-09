package net.thenova.droplets.common.redis;

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
public final class RedisConstants {

    /**
     * Used to create a droplet.
     */
    public static final String ACTION_CREATE = "c";

    /**
     * Used to delete a droplet.
     */
    public static final String ACTION_DELETE = "d";

    /**
     * Used to mark a droplet as available and ok.
     */
    public static final String ACTION_IDENTIFY = "i";

    /**
     * Used to query all droplets.
     */
    public static final String ACTION_QUERY = "q";

    /**
     * The key for the template template name.
     */
    public static final String DATA_CREATE_TEMPLATE = "x";

    /**
     * The key for the data list.
     */
    public static final String DATA_QUERY_LIST = "l";

    /**
     * The key for any identifier.
     */
    public static final String DATA_IDENTIFIER = "i";

    /**
     * The key for the IP.
     */
    public static final String DATA_IP = "h";

    /**
     * The key for the port.
     */
    public static final String DATA_PORT = "p";

    /**
     * The key for the create data.
     */
    public static final String DATA_DATA = "v";

    /**
     * The character that splits the identifier.
     */
    public static final String SPLIT_IDENTIFIER = "-";

    /**
     * The pub/sub channel name.
     */
    static final String CHANNEL = "ch_dr";

    /**
     * The identifier for a proxy sender.
     */
    static final String SENDER_PROXY = "_";

}
