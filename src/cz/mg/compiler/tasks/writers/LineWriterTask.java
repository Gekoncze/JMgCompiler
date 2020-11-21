package cz.mg.compiler.tasks.writers;

import cz.mg.collections.list.List;
import cz.mg.language.entities.text.plain.Line;


public interface LineWriterTask {
    List<Line> getLines();
}
