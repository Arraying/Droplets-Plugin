package net.thenova.droplets.proxy.command.sub;

import net.md_5.bungee.api.ChatColor;
import net.thenova.droplets.droplet.Droplet;
import net.thenova.droplets.proxy.command.internal.AbstractSubCommand;
import net.thenova.droplets.proxy.command.internal.Context;
import net.thenova.droplets.proxy.command.internal.responses.ListedResponse;
import net.thenova.droplets.proxy.command.internal.responses.MessageResponse;

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
public final class InfoCommand extends AbstractSubCommand {

    /**
     * Creates a new abstract sub command.
     */
    public InfoCommand() {
        super("info", "i");
    }

    /**
     * When the command is executed.
     * @param context The context.
     */
    @Override
    public void onCommand(Context context) {
        Droplet droplet = fromContext(context);
        if(droplet == null) {
            context.reply(new MessageResponse("Invalid droplet."));
            return;
        }
        context.reply(new ListedResponse(
                "Identifier: " + ChatColor.GRAY + droplet.getIdentifier(),
                "IP: " + ChatColor.GRAY + droplet.getAddress().getHostString(),
                "Port: " + ChatColor.GRAY + droplet.getAddress().getPort()
        ));
    }

}
