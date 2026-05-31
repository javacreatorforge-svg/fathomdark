package com.redstonedev.fathomdark;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Fathomdark.MODID)
public class Fathomdark {
    public static final String MODID = "fathomdark";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Fathomdark() {
        LOGGER.info("Fathomdark loaded - the deep remembers no light");
    }
}
