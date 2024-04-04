These are workspace level settings so they will be used by all (wicketstuff) proejcts in your workspace.

wicket-workspace-javacode-cleanup-profile.xml
This clean up profile can be imported into Eclipse on Window -> Preferences -> Java -> Code Style -> Clean Up panel. This is the same as it is in Wicket.

wicket-workspace-javacode-formatter-profile.xml
This formatter profile can be imported into Eclipse on Window -> Preferences -> Java -> Code Style -> Formatter panel. This is the same as it is in Wicket.

A complete package or java source directory can be formatted, clean up-ed by right clicking on it in the "Project Explorer" and activating it in Source menu.

To have these run automatically on save the following should be configured: Window -> Preferences -> Java -> Editor -> Save Actions:
Check all checkboxes. Formatting will be done by the current active formatting profile but the clean up will be not (sadly). You have to use the "Configure" button to set up the same options as in the imported clean up profile (luckily there are not many).
