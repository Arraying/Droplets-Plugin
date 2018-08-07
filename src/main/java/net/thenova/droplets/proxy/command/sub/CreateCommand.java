package net.thenova.droplets.proxy.command.sub;

import net.thenova.droplets.droplet.DropletCreationData;
import net.thenova.droplets.droplet.DropletHandler;
import net.thenova.droplets.proxy.command.internal.AbstractSubCommand;
import net.thenova.droplets.proxy.command.internal.Context;
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
public final class CreateCommand extends AbstractSubCommand {

    /**
     * Creates a new abstract sub command.
     */
    public CreateCommand() {
        super("create", "c");
    }

    /**
     * When the command is executed.
     * @param context The context.
     */
    @Override
    public void onCommand(Context context) {
        if(context.getArgs().length == 0) {
            context.reply(new MessageResponse("Please provide the droplet template."));
            return;
        }
        String template = context.getArgs()[0];
        context.reply(new MessageResponse("Attempting to create a droplet with this template, if unsuccessful this will fail silently."));
        DropletHandler.INSTANCE.create(template, new DropletCreationData(droplet -> context.reply(new MessageResponse("Created droplet " + droplet.getIdentifier() + ".")), null));
    }

}
