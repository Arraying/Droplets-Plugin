package net.thenova.droplets.proxy.command;

import net.thenova.droplets.proxy.command.internal.AbstractCommand;
import net.thenova.droplets.proxy.command.internal.Context;
import net.thenova.droplets.proxy.command.internal.responses.ListedResponse;
import net.thenova.droplets.proxy.command.sub.CreateCommand;
import net.thenova.droplets.proxy.command.sub.DeleteCommand;
import net.thenova.droplets.proxy.command.sub.InfoCommand;

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
public final class DropletCommand extends AbstractCommand {

    /**
     * Creates the droplet command.
     */
    public DropletCommand() {
        super("droplets", "droplets.admin", "droplet", "dr", "d");
        subCommands.add(new InfoCommand());
        subCommands.add(new CreateCommand());
        subCommands.add(new DeleteCommand());
    }

    /**
     * When the command is executed.
     * @param context The command context.
     */
    @Override
    public void onCommand(Context context) {
        context.reply(new ListedResponse(
                "/droplets info [identifier]",
                "/droplets create <template>",
                "/droplets delete [identifier]"
        ));
    }

}
