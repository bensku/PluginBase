package org.extendedalpha.pluginbase.cost;

import org.extendedalpha.pluginbase.EAUtilsException;
import org.extendedalpha.pluginbase.EAValidate;
import org.extendedalpha.pluginbase.LogUtils;
import org.extendedalpha.pluginbase.PluginVersionChecker;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyCost extends Cost {
    private boolean vaultLegacyMode;
	private static Economy economy;

	public EconomyCost(double quantity) {
		super(quantity);

		if (getEconomy() == null) {
            Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
            EAValidate.notNull(vault, "Economy costs not available: Vault not installed");
            EAValidate.isTrue(vault.isEnabled(), "Economy costs not available: Vault not enabled");
            int ver = PluginVersionChecker.getRelease(vault.getDescription().getVersion());
            vaultLegacyMode = ver < 1003000;  // 1.3.0
            if (vaultLegacyMode) {
                LogUtils.warning("Detected an older version of Vault.  Proper UUID functionality requires Vault 1.4.1 or later.");
            }
            RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(Economy.class);
            EAValidate.notNull(economyProvider, "Economy costs not available: no suitable Economy plugin detected");
            economy = economyProvider.getProvider();
		}
	}

	@Override
	public String getDescription() {
		return getEconomy().format(getQuantity());
	}

	@Override
	public boolean isAffordable(Player player) {
		return vaultLegacyMode ?
                getEconomy().has(player.getName(), getQuantity()) :
                getEconomy().has(player, getQuantity());
	}

	@Override
	public void apply(Player player) {
		EconomyResponse resp;
		if (getQuantity() < 0.0) {
			resp = vaultLegacyMode ?
                    getEconomy().depositPlayer(player.getName(), -getQuantity()) :
                    getEconomy().depositPlayer(player, -getQuantity());
		} else {
			resp = vaultLegacyMode ?
                    getEconomy().withdrawPlayer(player.getName(), getQuantity()) :
                    getEconomy().withdrawPlayer(player, getQuantity());
		}
		if (!resp.transactionSuccess()) {
			throw new EAUtilsException("Economy problem: " + resp.errorMessage);
		}
	}

    /**
     * Set the Vault economy service to be used by this class.
     *
     * @param economy the Vault economy service
     * @deprecated unnecessary - constructor will now do this itself if needed
     */
    @Deprecated
	public static void setEconomy(Economy economy) {
		EconomyCost.economy = economy;
	}

    /**
     * Get the Vault economy service used by this class.
     *
     * @return the Vault economy service
     */
	public static Economy getEconomy() {
		return economy;
	}
}
