package com.piece_framework.makegood.core;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

public class MakeGoodProperty {
    private static String PRELOAD_SCRIP_KEY = "preload_script";
    private IEclipsePreferences preferences;

    public MakeGoodProperty(IResource resource) {
        preferences = new ProjectScope(resource.getProject()).getNode(Activator.PLUGIN_ID);
    }

    public MakeGoodProperty(String path) {
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        IResource resource = workspaceRoot.findMember(path);
        if (resource != null) {
            preferences = new ProjectScope(resource.getProject()).getNode(Activator.PLUGIN_ID);
        }
    }

    public String getPreloadScript() {
        return preferences.get(PRELOAD_SCRIP_KEY, "");
    }

    public void setPreloadScript(String preloadScript) {
        preferences.put(PRELOAD_SCRIP_KEY, preloadScript);
        flush();
    }

    private void flush() {
        try {
            preferences.flush();
        } catch (BackingStoreException e) {
            Activator.getDefault().getLog().log(new Status(Status.ERROR,
                                                           Activator.PLUGIN_ID,
                                                           e.getMessage(),
                                                           e
                                                           ));
        }
    }
}
