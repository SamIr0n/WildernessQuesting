package io.github.samir0n.wquesting.model.instance;

import com.mojang.serialization.Codec;
import io.github.samir0n.wquesting.model.definition.QuestDefinition;
import net.minecraft.core.Holder;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

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

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public Holder<QuestDefinition> definition() {
        return this.definition;
    }

    @Override
    public void update(QuestEvent event) {

    }

    public static final Codec<BaseQuestInstance> CODEC = QuestDefinition.REF_CODEC.<BaseQuestInstance>dispatchMap(
        "definition",
        BaseQuestInstance::definition,
        holder -> holder.value().behaviour().instanceCodec(holder)
    ).codec();

    public enum State implements QuestState, StringRepresentable {
        IN_PROGRESS, COMPLETED;

        public static final Codec<State> CODEC = StringRepresentable.fromEnum(State::values);

        @Override
        public @NotNull String getSerializedName() {
            return this.name();
        }
    }
}
