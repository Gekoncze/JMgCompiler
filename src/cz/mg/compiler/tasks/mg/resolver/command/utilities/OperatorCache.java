package cz.mg.compiler.tasks.mg.resolver.command.utilities;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.List;
import cz.mg.collections.list.ReadableList;
import cz.mg.collections.map.Map;
import cz.mg.collections.text.ReadableText;
import cz.mg.collections.text.ReadonlyText;
import cz.mg.language.LanguageException;
import cz.mg.annotations.requirement.Mandatory;
import cz.mg.annotations.storage.Part;
import cz.mg.annotations.storage.Value;
import cz.mg.language.entities.mg.runtime.components.types.functions.MgOperator;
import cz.mg.compiler.tasks.mg.resolver.context.Context;
import cz.mg.compiler.tasks.mg.resolver.search.operator.OperatorSearch;


public class OperatorCache {
    @Mandatory @Value
    private final int minPriority;

    @Mandatory @Value
    private final int maxPriority;

    @Mandatory @Part
    private final Array<List<OperatorInfo>> operatorArray;

    @Mandatory @Part
    private final Map<ReadableText, OperatorInfo> operatorMap = new Map<>();

    public OperatorCache(Context context) {
        if(context != null){
            // find all functions available in this context
            OperatorSearch filter = new OperatorSearch(context.getGlobalSource(), null);
            ReadableList<MgOperator> availableOperators = filter.findAll();

            // fund the min and max priority of the available functions
            int minOperatorPriority = 0;
            int maxOperatorPriority = 0;
            for(MgOperator operator : availableOperators){
                minOperatorPriority = Math.min(minOperatorPriority, operator.getPriority());
                maxOperatorPriority = Math.max(maxOperatorPriority, operator.getPriority());
            }
            this.minPriority = minOperatorPriority;
            this.maxPriority = maxOperatorPriority;

            // create array of prioritized function lists
            this.operatorArray = new Array<>(maxOperatorPriority - minOperatorPriority + 1);
            for(int i = 0; i < this.operatorArray.count(); i++){
                this.operatorArray.set(new List<>(), i);
            }

            // fill the prioritized function list with found operator functions
            for(MgOperator operator : availableOperators){
                this.operatorArray.get(p2i(operator.getPriority())).addLast(new OperatorInfo(
                    OperatorInfo.Position.create(operator),
                    operator.getName(),
                    operator.getPriority()
                ));
            }
        } else {
            this.operatorArray = new Array<>();
            this.minPriority = 0;
            this.maxPriority = 0;
        }
    }

    public int getMinPriority(){
        return minPriority;
    }

    public int getMaxPriority(){
        return maxPriority;
    }

//    public List<OperatorInfo> getOperators(int priority) { todo - remove if unused
//        if(operatorArray.isEmpty()) return null;
//        else return operatorArray.get(p2i(priority));
//    }

    private final int p2i(int p){
        return p - minPriority;
    }

    private final int i2p(int i){
        return minPriority + i;
    }

    private static final OperatorInfo UNKNOWN = new OperatorInfo(OperatorInfo.Position.LEFT, new ReadonlyText(""), 0);

    public OperatorInfo findOperator(ReadableText operatorName){
        OperatorInfo cachedOperator = operatorMap.get(operatorName, UNKNOWN);
        if(cachedOperator == UNKNOWN){
            for(List<OperatorInfo> array : operatorArray){
                for(OperatorInfo operator : array){
                    if(operator.getName().equals(operatorName)){
                        if(cachedOperator == UNKNOWN){
                            cachedOperator = operator;
                        } else {
                            if(!isCompatible(cachedOperator, operator)){
                                throw new LanguageException("Ambiguous operator '" + operatorName + "'.");
                            }
                        }
                    }
                }
            }
            if(cachedOperator == UNKNOWN) cachedOperator = null;
            operatorMap.set(operatorName, cachedOperator);
        }
        return cachedOperator;
    }

    private static boolean isCompatible(OperatorInfo first, OperatorInfo second){
        return first.getPosition() == second.getPosition() && first.getPriority() == second.getPriority();
    }
}
