package cz.mg.compiler.tasks.mg.builder.pattern;

import cz.mg.collections.Clump;
import cz.mg.collections.list.List;
import cz.mg.collections.map.Map;
import cz.mg.collections.special.FilteredCollection;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.tasks.Task;
import cz.mg.language.LanguageException;

import java.util.Iterator;


public class MgPatternValidatorTask extends Task {
    @Input
    private final Clump<? extends cz.mg.compiler.tasks.mg.builder.pattern.Pattern> patterns;

    private Map<Object, cz.mg.compiler.tasks.mg.builder.pattern.Pattern> patternUsageMap = new Map<>();
    private Map<cz.mg.compiler.tasks.mg.builder.pattern.Pattern, Integer> patternUsageCount = new Map<>();
    private List<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> patternUsageOrder = new List<>();

    public MgPatternValidatorTask(Clump<? extends cz.mg.compiler.tasks.mg.builder.pattern.Pattern> patterns) {
        this.patterns = patterns;
    }

    public void register(Object object, cz.mg.compiler.tasks.mg.builder.pattern.Pattern pattern){
        patternUsageMap.set(object, pattern);
        patternUsageCount.set(pattern, patternUsageCount.get(pattern, 0) + 1);
        patternUsageOrder.addLast(pattern);
    }

    @Override
    protected void onRun() {
        if(patterns != null){
            validateOrder();
            validateRequirement();
            validateCount();
        }
    }

    private void validateOrder(){
        Clump<? extends cz.mg.compiler.tasks.mg.builder.pattern.Pattern> expectation = new FilteredCollection<>(
                patterns,
                pattern -> pattern.getOrder() == Order.STRICT
        );
        Clump<cz.mg.compiler.tasks.mg.builder.pattern.Pattern> reality = patternUsageOrder;

        Iterator<? extends cz.mg.compiler.tasks.mg.builder.pattern.Pattern> expectationIterator = expectation.iterator();
        cz.mg.compiler.tasks.mg.builder.pattern.Pattern currentExpectation = expectationIterator.next();

        for(cz.mg.compiler.tasks.mg.builder.pattern.Pattern currentReality : reality){
            while(currentReality != currentExpectation){
                if(currentExpectation == null){
                    if(currentReality.getOrder() == Order.RANDOM){
                        break;
                    } else {
                        throw new LanguageException("Illegal element order.");
                    }
                }
                currentExpectation = expectationIterator.next();
            }
        }
    }

    private void validateRequirement(){
        for(cz.mg.compiler.tasks.mg.builder.pattern.Pattern pattern : patterns){
            switch (pattern.getRequirement()){
                case MANDATORY:
                    if(patternUsageCount.get(pattern, 0) < 0)
                        throw new LanguageException("Missing mandatory element.");
                    break;
                case OPTIONAL:
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

    private void validateCount(){
        for(Pattern pattern : patterns){
            switch (pattern.getCount()){
                case SINGLE:
                    if(patternUsageCount.get(pattern, 0) > 1)
                        throw new LanguageException("Element specified multiple times.");
                    break;
                case MULTIPLE:
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }
}
