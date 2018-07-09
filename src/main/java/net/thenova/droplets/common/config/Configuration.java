package net.thenova.droplets.common.config;

import de.arraying.kotys.JSON;
import net.thenova.droplets.Core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
public final class Configuration {

    private JSON json;

    /**
     * Loads the configuration.
     * @param parent The parent directory.
     * @throws IOException If there is an IO error loading the config.
     */
    public void load(File parent) throws IOException {
        if(json != null) {
            return;
        }
        //noinspection ResultOfMethodCallIgnored
        parent.mkdirs();
        File config = new File(parent, "config.json");
        if(config.createNewFile()) {
            Core.INSTANCE.getLogger().severe("Could not create config.");
            throw new IOException("Config did not exist.");
        }
        List<String> lines = Files.readAllLines(config.toPath());
        StringBuilder builder = new StringBuilder();
        for(String line : lines) {
            builder.append(line.trim());
        }
        json = new JSON(builder.toString().trim());
    }

    /**
     * Whether or not the config is valid.
     * @return True if it is, false otherwise.
     */
    public boolean isValid() {
        return json.has("redis-host")
                && json.has("redis-port")
                && json.has("redis-auth")
                && json.has("redis-index")
                && json.has("redis-token");
    }

    /**
     * Gets the JSON object containing config values.
     * @return The object.
     */
    public JSON getJSON() {
        return json;
    }

}
