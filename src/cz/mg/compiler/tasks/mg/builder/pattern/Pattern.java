package cz.mg.compiler.tasks.mg.builder.pattern;

import cz.mg.annotations.storage.Part;
import cz.mg.collections.list.List;
import cz.mg.collections.text.ReadableText;
import cz.mg.collections.text.ReadonlyText;


public class Pattern {
    @Part
    private final Order order;

    @Part
    private final Requirement requirement;

    @Part
    private final Count count;

    @Part
    private final BlockProcessor processor;

    @Part
    private final List<ReadableText> keywords = new List<>();

    public Pattern(
            Order order,
            Requirement requirement,
            Count count,
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
