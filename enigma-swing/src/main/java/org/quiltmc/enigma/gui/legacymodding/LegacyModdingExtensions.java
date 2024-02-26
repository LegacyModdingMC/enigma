package org.quiltmc.enigma.gui.legacymodding;

public class LegacyModdingExtensions {
	private static final boolean ENABLED = Boolean.parseBoolean(System.getProperty("enigma.legacymodding", "false"));

	public static boolean isEnabled() {
		return ENABLED;
	}
}
