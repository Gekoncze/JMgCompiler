package cz.mg.compiler.tasks.mg.parser;

import cz.mg.collections.array.Array;
import cz.mg.collections.text.ReadableText;
import cz.mg.compiler.annotations.Input;
import cz.mg.compiler.annotations.Output;
import cz.mg.compiler.tasks.Task;
import cz.mg.language.entities.text.plain.Page;


public class MgParsePageTask extends Task {
    @Input
    private final ReadableText text;

    @Output
    private Page page = null;

    public MgParsePageTask(ReadableText text) {
        this.text = text;
    }

    public Page getPage() {
        return page;
    }

    @Override
    protected void onRun() {
        page = new Page();
        Array<ReadableText> rawLines = text.splitByEach("\n");
        for(ReadableText rawLine : rawLines){
            cz.mg.compiler.tasks.mg.parser.MgParseLineTask task = new MgParseLineTask(rawLine);
            task.run();
            page.getLines().addLast(task.getLine());
        }
    }
}
