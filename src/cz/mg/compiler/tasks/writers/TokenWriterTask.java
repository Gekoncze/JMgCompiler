package cz.mg.compiler.tasks.writers;

import cz.mg.collections.list.List;
import cz.mg.language.entities.text.plain.Token;


public interface TokenWriterTask {
    List<Token> getTokens();
}
