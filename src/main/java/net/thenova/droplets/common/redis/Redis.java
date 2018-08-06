package net.thenova.droplets.common.redis;

import de.arraying.kotys.JSON;
import net.thenova.droplets.Core;
import net.thenova.droplets.droplet.Droplet;
import net.thenova.droplets.droplet.DropletHandler;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisException;

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
public enum Redis {

    /**
     * The singleton instance of the Redis wrapper.
     */
    INSTANCE;

    private String self;
    private String token;
    private JedisPool pool;


    /**
     * Initializes Redis.
     * @throws JedisException If a Jedis exception occurs.
     */
    public void init() throws JedisException {
        JSON data = Core.INSTANCE.getConfiguration().getJSON();
        self = data.string("identifier") == null ? RedisConstants.SENDER_PROXY : data.string("identifier");
        token = data.string("redis-token");
        pool = new JedisPool(new JedisPoolConfig(),
                data.string("redis-host"),
                data.integer("redis-port"),
                Integer.MAX_VALUE,
                data.string("redis-auth"),
                data.integer("redis-index"));
        try(Jedis jedis = pool.getResource()) {
            jedis.ping();
        }
        new Thread(() -> {
            try(Jedis jedis = pool.getResource()) {
                jedis.subscribe(new Listener(), RedisConstants.CHANNEL);
            }
        }).start();
    }

    /**
     * Dispatches a new payload with the given action and data.
     * @param action The action.
     * @param data The data.
     */
    public void dispatch(String action, JSON data) {
        RedisPayload payload = new RedisPayload(
                action,
                self,
                data,
                token
        );
        JSON payloadRaw = new JSON(payload);
        String json = payloadRaw.marshal();
        try(Jedis jedis = pool.getResource()) {
            jedis.publish(RedisConstants.CHANNEL, json);
        } catch(JedisException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Receive handles a received payload.
     * @param payload The payload.
     */
    private synchronized void receive(RedisPayload payload) {
        if(payload.getSender().equals(self)
                && payload.getToken().equals(token)) {
            return;
        }
        switch(payload.getAction()) {
            case RedisConstants.ACTION_DELETE:
                String target = payload.getData().string("i");
                if(self.equals(RedisConstants.SENDER_PROXY)) {
                    Droplet droplet = DropletHandler.INSTANCE.getDroplet(target);
                    if(droplet == null) {
                        Core.INSTANCE.getLogger().severe("Received delete for nonexistent droplet " + target + ".");
                        return;
                    }
                    DropletHandler.INSTANCE.deleteInternal(droplet);
                } else if(target.equals(self)) {
                    Core.INSTANCE.getLogger().info("Server termination.");
                    Core.INSTANCE.getServerCore().kill();
                }
                break;
            case RedisConstants.ACTION_IDENTIFY:
                if(self.equals(RedisConstants.SENDER_PROXY)) {
                    DropletHandler.INSTANCE.register(Droplet.Util.fromIdentifyData(payload.getData()), false);
                }
                break;
            case RedisConstants.ACTION_QUERY:
                if(self.equals(RedisConstants.SENDER_PROXY)) {
                    for(Droplet droplet : Droplet.Util.fromQueryData(payload.getData())) {
                        DropletHandler.INSTANCE.register(droplet, true);
                    }
                }
                break;
        }
    }

    /**
     * Gets the self value.
     * @return The value.
     */
    public String getSelf() {
        return self;
    }

    /**
     * The listener that listens to Redis pub/sub payloads.
     */
    public final class Listener extends JedisPubSub {

        /**
         * When a message is received.
         * @param channel The channel.
         * @param message The message.
         */
        @Override
        public void onMessage(String channel, String message) {
            if(!channel.equals(RedisConstants.CHANNEL)) {
                return;
            }
            try {
                JSON payloadRaw = new JSON(message);
                RedisPayload payload = payloadRaw.marshal(RedisPayload.class);
                Redis.INSTANCE.receive(payload);
            } catch(IllegalStateException | IllegalArgumentException exception) {
                exception.printStackTrace();
            }
        }

    }

}
