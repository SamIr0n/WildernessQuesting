package io.github.samir0n.wquesting;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import static io.github.samir0n.wquesting.WildernessQuesting.MOD_ID;

@Mod(value = MOD_ID, dist = Dist.CLIENT)
public class WildernessQuestingClient {

    public WildernessQuestingClient(final IEventBus eventBus, final ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
