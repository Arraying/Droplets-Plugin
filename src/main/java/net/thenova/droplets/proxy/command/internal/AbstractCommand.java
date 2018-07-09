package net.thenova.droplets.proxy.command.internal;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.Arrays;
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
public abstract class AbstractCommand extends Command implements ICommand {

    protected final List<AbstractSubCommand> subCommands = new ArrayList<>();

    /**
     * Creates a new abstract command.
     * @param name The command name.
     * @param permission The permission (nullable).
     * @param aliases The aliases.
     */
    public AbstractCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    /**
     * When the command is executed.
     * @param commandSender The sender.
     * @param strings The args.
     */
    @Override
    public final void execute(CommandSender commandSender, String[] strings) {
        if(strings.length > 0) {
            String subCommandName = strings[0].toLowerCase();
            for(AbstractSubCommand subCommand : subCommands) {
                if(subCommand.getTriggers().contains(subCommandName)) {
                    String[] args = strings.length == 1 ? new String[0] : Arrays.copyOfRange(strings, 1, strings.length);
                    subCommand.onCommand(new Context(commandSender, args));
                    return;
                }
            }
        }
        onCommand(new Context(commandSender, strings));
    }

}
