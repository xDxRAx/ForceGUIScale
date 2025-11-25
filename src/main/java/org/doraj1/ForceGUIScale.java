package org.doraj1;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForceGUIScale implements ModInitializer {
    public static final String MOD_ID = "forceguiscale";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Force GUI Scale 모드가 활성화되었습니다!");
    }
}