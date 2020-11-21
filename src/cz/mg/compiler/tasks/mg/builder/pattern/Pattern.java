package cz.mg.compiler.tasks.mg.builder.pattern;

import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.collections.text.ReadonlyText;


public class Pattern {
    @cz.mg.annotations.storage.Part
    private final Order order;

    @cz.mg.annotations.storage.Part
    private final cz.mg.compiler.tasks.mg.builder.pattern.Requirement requirement;

    @cz.mg.annotations.storage.Part
    private final cz.mg.compiler.tasks.mg.builder.pattern.Count count;

    @cz.mg.annotations.storage.Part
    private final BlockProcessor processor;

    @cz.mg.annotations.storage.Part
    private final List<ReadableText> keywords = new List<>();

    public Pattern(
            Order order,
            cz.mg.compiler.tasks.mg.builder.pattern.Requirement requirement,
            cz.mg.compiler.tasks.mg.builder.pattern.Count count,
            BlockProcessor processor,
            String... keywords
    ){
        this.order = order;
        this.requirement = requirement;
        this.count = count;
        this.processor = processor;
        for(String keyword : keywords){
            this.keywords.addLast(new ReadonlyText(keyword));
        }
    }

    public Order getOrder() {
        return order;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public Count getCount() {
        return count;
    }

    public BlockProcessor getProcessor() {
        return processor;
    }

    public List<ReadableText> getKeywords() {
        return keywords;
    }
}
