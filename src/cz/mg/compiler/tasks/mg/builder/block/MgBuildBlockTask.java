package cz.mg.compiler.tasks.mg.builder.block;

import cz.mg.collections.Clump;
import cz.mg.collections.ReadableCollection;
import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.tasks.mg.builder.MgBuildTask;
import cz.mg.compiler.tasks.mg.builder.pattern.Pattern;
import cz.mg.language.LanguageException;
import cz.mg.language.entities.mg.unresolved.Stampable;
import cz.mg.language.entities.text.structured.Block;
import cz.mg.language.entities.text.structured.Part;
import cz.mg.compiler.tasks.mg.builder.part.MgBuildPartTask;

import java.util.Iterator;


public abstract class MgBuildBlockTask extends MgBuildTask {
    @Input
    private final Block block;

    public MgBuildBlockTask(Block block) {
        this.block = block;
    }

    @Override
    protected void onRun() {
        buildParts(block.getParts());
        buildBlocks(block.getBlocks());
        handoverStamps(block.getStamps());
    }

    protected void buildParts(List<Part> parts) {
        if(getProcessor() != null){
            if(parts.count() > 0) {
                createExecuteSet(getProcessor(), parts);
            } else {
                throw new LanguageException("Missing part.");
            }
        } else {
            if(parts.count() > 0){
                throw new LanguageException("Unexpected part.");
            } else {
                // nothing to do
            }
        }
    }

    protected void buildBlocks(List<Block> blocks){
        cz.mg.compiler.tasks.mg.builder.pattern.MgPatternValidatorTask validatorTask = new cz.mg.compiler.tasks.mg.builder.pattern.MgPatternValidatorTask(getPatterns());

        for(Block block : blocks){
            cz.mg.compiler.tasks.mg.builder.pattern.Pattern pattern = match(block);
            validatorTask.register(block, pattern);
            createExecuteSet(pattern.getProcessor(), block);
        }

        validatorTask.run();
    }

    private void createExecuteSet(cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor processor, Block block){
        set(processor, execute(create(processor.getSourceBuildTaskClass(), block)));
    }

    private void createExecuteSet(cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor processor, List<Part> parts){
        set(processor, execute(create(processor.getSourceBuildTaskClass(), parts)));
    }

    private MgBuildBlockTask create(Class<? extends MgBuildBlockTask> clazz, Block block){
        try {
            return clazz.getConstructor(Block.class).newInstance(block);
        } catch (ReflectiveOperationException e){
            throw new RuntimeException(e);
        }
    }

    private MgBuildPartTask create(Class<? extends MgBuildPartTask> clazz, List<Part> parts){
        try {
            return clazz.getConstructor(List.class).newInstance(parts);
        } catch (ReflectiveOperationException e){
            throw new RuntimeException(e);
        }
    }

    private MgBuildBlockTask execute(MgBuildBlockTask task){
        task.run();
        return task;
    }

    private MgBuildPartTask execute(MgBuildPartTask task){
        task.run();
        return task;
    }

    private void set(cz.mg.compiler.tasks.mg.builder.pattern.BlockProcessor processor, MgBuildBlockTask task){
        processor.getSetter().set(task, this);
    }

    private void set(cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor processor, MgBuildPartTask task){
        processor.getSetter().set(task, this);
    }

    private cz.mg.compiler.tasks.mg.builder.pattern.Pattern match(Block childBlock){
        if(getPatterns() == null){
            throw new LanguageException("Unexpected child block.");
        }

        for(cz.mg.compiler.tasks.mg.builder.pattern.Pattern pattern : getPatterns()){
            if(match(pattern.getKeywords(), childBlock.getKeywords())){
                return pattern;
            }
        }

        throw new LanguageException("Could not recognise block.");
    }

    private boolean match(
            ReadableCollection<ReadableText> expectedKeywords,
            ReadableCollection<ReadableText> actualKeywords
    ){
        if(expectedKeywords == actualKeywords) return true;
        if(expectedKeywords == null || actualKeywords == null) return false;
        if(expectedKeywords.count() != actualKeywords.count()) return false;
        Iterator<ReadableText> expectedKeywordIterator = expectedKeywords.iterator();
        Iterator<ReadableText> actualKeywordIterator = actualKeywords.iterator();
        while(expectedKeywordIterator.hasNext() && actualKeywordIterator.hasNext()){
            ReadableText expectedKeyword = expectedKeywordIterator.next();
            ReadableText actualKeyword = actualKeywordIterator.next();
            if(!expectedKeyword.equals(actualKeyword)) return false;
        }
        return true;
    }

    protected void handoverStamps(List<ReadableText> stamps) {
        if(stamps != null){
            if(!stamps.isEmpty()){
                Object output = getOutput();
                if(output instanceof Stampable){
                    Stampable stampableEntity = (Stampable) output;
                    stampableEntity.getStamps().addCollectionLast(stamps);
                } else {
                    throw new LanguageException("Cannot apply stamps to " + output.getClass().getSimpleName() + ".");
                }
            }
        }
    }

    protected abstract Object getOutput();
    protected abstract cz.mg.compiler.tasks.mg.builder.pattern.PartProcessor getProcessor();
    protected abstract Clump<Pattern> getPatterns();
}
