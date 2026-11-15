package io.github.samir0n.wquesting;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.samir0n.wquesting.model.definition.AttachmentTypes;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public final class WQCommands {
    private static final Predicate<CommandSourceStack> REQUIREMENT = source -> source.hasPermission(Commands.LEVEL_GAMEMASTERS);

    private WQCommands() {
    }

    public static void register(final RegisterCommandsEvent event) {
        final CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        final LiteralCommandNode<CommandSourceStack> root = dispatcher.register(Commands.literal("wildernessquesting")
            .requires(REQUIREMENT)
            .then(Commands.literal("start")
                .then(Commands.argument("quest", ResourceArgument.resource(event.getBuildContext(), QuestDefinition.REGISTRY_KEY))
                    .executes(ctx -> start(ctx, List.of(ctx.getSource().getPlayerOrException())))
                    .then(Commands.argument("targets", EntityArgument.players())
                        .executes(ctx -> start(ctx, EntityArgument.getPlayers(ctx, "targets")))))));

        dispatcher.register(Commands.literal("wq").requires(REQUIREMENT).redirect(root));
        dispatcher.register(Commands.literal("wquesting").requires(REQUIREMENT).redirect(root));
    }

    private static int start(final CommandContext<CommandSourceStack> ctx, final Collection<ServerPlayer> targets) throws CommandSyntaxException {
        final Holder.Reference<QuestDefinition> quest = ResourceArgument.getResource(ctx, "quest", QuestDefinition.REGISTRY_KEY);
        for (final ServerPlayer player : targets) player.getData(AttachmentTypes.QUEST_ATTACHMENT).setQuest(quest.value().behaviour().createInstance(quest));

        if (targets.size() == 1) {
            final Component name = targets.iterator().next().getDisplayName();
            ctx.getSource().sendSuccess(() -> Component.translatable("commands.wquesting.start.success.single", quest.value().name(), name), true);
        } else {
            final int count = targets.size();
            ctx.getSource().sendSuccess(() -> Component.translatable("commands.wquesting.start.success.multiple", quest.value().name(), count), true);
        }
        return targets.size();
    }
}
