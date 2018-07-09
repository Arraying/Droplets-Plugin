package net.thenova.droplets.common.redis;

import de.arraying.kotys.JSON;
import de.arraying.kotys.JSONField;

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
public final class RedisPayload {

    @JSONField(key = "a")
    private String action;

    @JSONField(key = "s")
    private String sender;

    @JSONField(key = "d")
    private JSON data;

    @JSONField(key = "t")
    private String token;

    /**
     * Creates a new Redis payload.
     * Empty constructor for JSON ORM.
     */
    @SuppressWarnings("unused")
    public RedisPayload() {}

    /**
     * Creates a new Redis payload.
     * @param action The action.
     * @param sender The sender.
     * @param data The data.
     * @param token The authentication token.
     */
    public RedisPayload(String action, String sender, JSON data, String token) {
        this.action = action;
        this.sender = sender;
        this.data = data;
        this.token = token;
    }

    /**
     * Gets the action.
     * @return The action.
     */
    public String getAction() {
        return action;
    }

    /**
     * Gets the sender.
     * @return The sender.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets the data.
     * @return The data.
     */
    public JSON getData() {
        return data;
    }

    /**
     * Gets the token.
     * @return The token.
     */
    public String getToken() {
        return token;
    }

}
