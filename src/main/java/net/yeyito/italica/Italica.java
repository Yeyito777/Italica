package net.yeyito.italica;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Italica implements ModInitializer {
	public static final String MOD_ID = "italica";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final double VERSION = 1.0;

	@Override
	public void onInitialize() {
		LOGGER.info("Italica Version: " + VERSION);
	}
}
