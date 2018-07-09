package net.thenova.droplets.proxy.command.internal.responses;

import net.md_5.bungee.api.ChatColor;
import net.thenova.droplets.proxy.command.internal.Response;

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
public final class ListedResponse implements Response {

    private final String[] members;

    /**
     * Creates a new listed response.
     * @param members The response members.
     */
    public ListedResponse(String... members) {
        this.members = members;
    }

    /**
     * Gets the response.
     * @return The response.
     */
    @Override
    public String getValue() {
        StringBuilder builder = new StringBuilder(new BannerResponse().getValue());
        for(String member : members) {
            builder.append("\n")
                    .append(ChatColor.YELLOW)
                    .append(member);
        }
        builder.append("\n")
                .append(new BannerResponse().getValue());
        return builder.toString();
    }

}
