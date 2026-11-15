package io.github.samir0n.wquesting.model.registry;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import io.github.samir0n.wquesting.model.instance.BaseQuestInstance;
import io.github.samir0n.wquesting.model.instance.CollectionQuestInstance;
import io.github.samir0n.wquesting.model.instance.InventoryQuestEvent;
import io.github.samir0n.wquesting.model.instance.QuestEvent;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class CollectionQuestBehaviour extends QuestBehaviour<CollectionQuestInstance> {

    public static final MapCodec<CollectionQuestBehaviour> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ItemPredicate.CODEC.fieldOf("item").forGetter(CollectionQuestBehaviour::itemType),
            ExtraCodecs.POSITIVE_INT.fieldOf("count").forGetter(CollectionQuestBehaviour::itemCount)).apply(instance, CollectionQuestBehaviour::new));

    private final int itemCount;

    private final ItemPredicate itemType;

    public CollectionQuestBehaviour(final ItemPredicate itemType, final int itemCount) {
        this.itemCount = itemCount;
        this.itemType = itemType;
    }

    public int itemCount() {
        return itemCount;
    }

    public ItemPredicate itemType() {
        return itemType;
    }

    @Override
    public BehaviourType<?> getType() {
        return BehaviourType.COLLECTION.get();
    }

    @Override
    public MapCodec<CollectionQuestInstance> instanceCodec(Holder<QuestDefinition> definition) {
        return CollectionQuestInstance.codec(definition);
    }

    @Override
    public CollectionQuestInstance createInstance(Holder<QuestDefinition> definition) {
        return new CollectionQuestInstance(definition);
    }

    @Override
    public void handle(ServerPlayer player, CollectionQuestInstance instance, QuestEvent event) {
        if (!(event instanceof InventoryQuestEvent) || instance.state() != BaseQuestInstance.State.IN_PROGRESS) {
            return;
        }

        Inventory inventory = player.getInventory();
        int totalItemCount = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!(stack.isEmpty()) && itemType().test(stack))
                totalItemCount += stack.getCount();
        }

        if(totalItemCount >= itemCount) {
            instance.setState(BaseQuestInstance.State.COMPLETED);
            player.playNotifySound(SoundEvents.EVOKER_PREPARE_WOLOLO, SoundSource.HOSTILE, 1f, 1f);
        }
    }
}
