package net.thenova.droplets.proxy.command.internal;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

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
public final class Context {

    private final CommandSender sender;
    private final String[] args;

    /**
     * Creates a new context.
     * @param sender The sender.
     * @param args The arguments.
     */
    Context(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    /**
     * Gets the sender.
     * @return The sender.
     */
    @SuppressWarnings("WeakerAccess")
    public CommandSender getSender() {
        return sender;
    }

    /**
     * Gets the arguments.
     * @return The arguments.
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * Replies to the command sender.
     * @param response The response
     */
    public void reply(Response response) {
        sender.sendMessage(TextComponent.fromLegacyText(response.getValue(), ChatColor.GRAY));
    }

}
