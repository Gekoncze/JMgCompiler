package cz.mg.compiler.tasks.mg.resolver.command.utilities;

import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Link;
import cz.mg.annotations.storage.Value;
import cz.mg.collections.text.ReadableText;
import cz.mg.language.entities.mg.unresolved.components.MgUnresolvedLunaryOperator;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgBinaryOperator;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgLunaryOperator;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgOperator;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgRunaryOperator;


public final class OperatorInfo {
    @Mandatory @Value
    private final Position position;

    @Mandatory @Link
    private final ReadableText name;

    @Mandatory @Value
    private final int priority;

    public OperatorInfo(@Mandatory @Value Position position, @Mandatory @Link ReadableText name, int priority) {
        this.position = position;
        this.name = name;
        this.priority = priority;
    }

    public Position getPosition() {
        return position;
    }

    public ReadableText getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public enum Position {
        LEFT,
        MIDDLE,
        RIGHT;

        public static Position create(MgOperator operator){
            if(operator instanceof MgLunaryOperator){
                return LEFT;
            } else if(operator instanceof MgBinaryOperator){
                return MIDDLE;
            } else if(operator instanceof MgRunaryOperator){
                return RIGHT;
            } else {
                throw new RuntimeException();
            }
        }
    }
}