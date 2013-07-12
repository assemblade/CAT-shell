/*
 * Copyright 2013 Mike Adamson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.assemblade.shell.commands;

import com.assemblade.client.ClientException;
import com.assemblade.client.Folders;
import com.assemblade.client.model.Folder;
import com.assemblade.shell.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddFolderCommand implements Command {
    private Pattern pattern = Pattern.compile("^\\s*([^,]*),([^,]*),([^,]*),([^,]*)");

    @Override
    public CommandStatus run(Context context, String body) {
        Matcher commandMatcher = pattern.matcher(body);
        String name = null;
        String description = null;
        String readGroups = null;
        String readWriteGroups = null;

        try {
            if (commandMatcher.matches()) {
                name = commandMatcher.group(1);
                description = commandMatcher.group(2);
                readGroups = commandMatcher.group(3);
                readWriteGroups = commandMatcher.group(4);
            } else if (context.isInteractive()) {
                context.println();
                name = context.readLine("Enter user Id []: ");
                description = context.readLine("Enter full name of user []: ");
            }
            Folders folders = new Folders(context.getAuthenticationProcessor().getAuthentication());
            Folder folder = new Folder();
            folder.setName(name);
            folder.setDescription(description);
            folders.addRootFolder(folder);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return CommandStatus.Continue;
    }
}
