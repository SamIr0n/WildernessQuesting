package io.github.samir0n.wquesting.mixin;

import io.github.samir0n.wquesting.model.definition.AttachmentTypes;
import io.github.samir0n.wquesting.model.instance.BaseQuestInstance;
import io.github.samir0n.wquesting.model.instance.InventoryQuestEvent;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Triggers a quest event for changes to the player's inventory.
 */
@Mixin(InventoryChangeTrigger.class)
public abstract class InventoryChangeTriggerMixin {

    @Inject(
        method = "trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/item/ItemStack;)V",
        at = @At("HEAD")
    )
    private void wquesting$onInventoryChanged(final ServerPlayer player, final Inventory inventory, final ItemStack stack, final CallbackInfo ci) {
        final BaseQuestInstance quest = player.getData(AttachmentTypes.QUEST_ATTACHMENT).getQuest();
        if (quest != null) quest.update(player,new InventoryQuestEvent(stack));
    }
}
