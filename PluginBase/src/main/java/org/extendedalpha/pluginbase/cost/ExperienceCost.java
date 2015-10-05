package org.extendedalpha.pluginbase.cost;

import org.extendedalpha.pluginbase.ExperienceManager;

import org.bukkit.entity.Player;

public class ExperienceCost extends Cost {

	protected ExperienceCost(double quantity) {
		super(quantity);
	}

	@Override
	public String getDescription() {
		return (int) getQuantity() + " XP";
	}

	@Override
	public boolean isAffordable(Player player) {
		ExperienceManager em = new ExperienceManager(player);
		return em.getCurrentExp() >= getQuantity();
	}

	@Override
	public void apply(Player player) {
		ExperienceManager em = new ExperienceManager(player);
		em.changeExp(-getQuantity());
	}
}
