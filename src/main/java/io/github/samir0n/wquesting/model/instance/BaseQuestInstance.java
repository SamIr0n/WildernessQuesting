package io.github.samir0n.wquesting.model.instance;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import io.github.samir0n.wquesting.model.registry.QuestBehaviour;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

/**
 * Common quest state for all quest instances, e.g. whether it has been completed and which quest it is.
 * */
public abstract class BaseQuestInstance implements QuestInstance {

    protected State state = State.IN_PROGRESS;
    protected Holder<QuestDefinition> definition;

    public BaseQuestInstance(Holder<QuestDefinition> definition, State state) {
        this.definition = definition;
        this.state = state;
    }

    public BaseQuestInstance(Holder<QuestDefinition> definition) {
        this(definition, State.IN_PROGRESS);
    }

    public BaseQuestInstance() {
    }

    @Override
    public State state() {
        return this.state;
    }

    public void setState(final State state) {
        this.state = state;
    }

    @Override
    public Holder<QuestDefinition> definition() {
        return this.definition;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void update(final ServerPlayer player, final QuestEvent event) {
        ((QuestBehaviour<BaseQuestInstance>) definition.value().behaviour()).handle(player, this, event);
    }

    public static final Codec<BaseQuestInstance> CODEC = QuestDefinition.REF_CODEC.<BaseQuestInstance>dispatchMap(
        "definition",
        BaseQuestInstance::definition,
        holder -> holder.value().behaviour().instanceCodec(holder)
    ).codec();

    protected static <T extends BaseQuestInstance> Products.P1<RecordCodecBuilder.Mu<T>, State> commonFields(final RecordCodecBuilder.Instance<T> builder) {
        return builder.group(State.CODEC.optionalFieldOf("state", State.IN_PROGRESS).forGetter(BaseQuestInstance::state));
    }

    /**
     * The state of an active quest.
     * */
    public enum State implements QuestState, StringRepresentable {
        /**
         * The quest has been started but has not been completed yet.
         * */
        IN_PROGRESS,
        /**
         * The quest has been completed but a new one has not been started yet.
         * */
        COMPLETED;

        public static final Codec<State> CODEC = StringRepresentable.fromEnum(State::values);

        @Override
        public @NotNull String getSerializedName() {
            return this.name();
        }
    }
}
