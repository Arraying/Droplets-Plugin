package net.thenova.droplets.droplet;


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
public final class DropletTemplate {

    private final String template;
    private final boolean restricted;

    /**
     * Creates a new droplet template.
     * @param template The template name.
     * @param restricted Whether or not the template is restricted.
     */
    public DropletTemplate(String template, boolean restricted) {
        this.template = template;
        this.restricted = restricted;
    }

    /**
     * Gets the template name.
     * @return The name.
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Whether or not this template is restricted.
     * @return True if it is, false otherwise.
     */
    public boolean isRestricted() {
        return restricted;
    }

}
