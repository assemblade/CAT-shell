package com.assemblade.shell.commands;

import com.assemblade.client.ClientException;
import com.assemblade.client.Folders;
import com.assemblade.client.Users;
import com.assemblade.client.model.Folder;
import com.assemblade.client.model.User;
import com.assemblade.shell.Context;

public class ListFolderCommand implements Command {
    private static final ListReportDefinition folderReport;

    static {
        folderReport = new ListReportDefinition("Folders");
        folderReport.addColumn(new ListColumn("Name", 20, "getName"));
        folderReport.addColumn(new ListColumn("Description", 30, "getDescription"));
    }

    @Override
    public CommandStatus run(Context context, String parameters) {
        Folders folders = new Folders(context.getAuthenticationProcessor().getAuthentication());

        folderReport.printHeader(context);
        try {
            for (Folder folder : folders.getRootFolders()) {
                folderReport.printLine(context, folder);
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return CommandStatus.Continue;
    }
}
