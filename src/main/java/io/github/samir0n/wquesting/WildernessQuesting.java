package io.github.samir0n.wquesting;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import static io.github.samir0n.wquesting.WildernessQuesting.MOD_ID;

@Mod(MOD_ID)
public class WildernessQuesting {
    public static final String MOD_ID = "wquesting";

    public WildernessQuesting(final IEventBus eventBus, final ModContainer container) {

        eventBus.addListener(this::commonSetup);



        container.registerConfig(ModConfig.Type.COMMON, WQConfig.SPEC);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

}



