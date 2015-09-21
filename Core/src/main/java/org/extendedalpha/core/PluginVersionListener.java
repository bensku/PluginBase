package org.extendedalpha.core;

public interface PluginVersionListener {
	public void onVersionChanged(int oldVersion, int newVersion);
	public String getPreviousVersion();
	public void setPreviousVersion(String currentVersion);
}
