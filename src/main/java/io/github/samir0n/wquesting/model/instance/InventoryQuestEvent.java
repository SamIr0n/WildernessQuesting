package io.github.samir0n.wquesting.model.instance;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

/**
 * An event that is sent when a slot in the player's inventory changes.
 *
 * @param player  the player whose inventory changed
 * @param changed the new contents of the changed slot, which is {@link ItemStack#EMPTY} when the slot was emptied
 */
public record InventoryQuestEvent(ServerPlayer player, ItemStack changed) implements QuestEvent {
}
