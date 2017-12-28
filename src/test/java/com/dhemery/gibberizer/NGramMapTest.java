package com.dhemery.gibberizer;

import org.junit.jupiter.api.Test;

import static com.dhemery.gibberizer.utils.TestUtils.nGramsOf;
import static org.assertj.core.api.Assertions.assertThat;

public class NGramMapTest {
    @Test
    public void firstNgramOfEachListIsAStarter() {
        NGramMap nGramMap = new NGramMap();
        nGramMap.add(nGramsOf("aaa", "bbb", "ccc", "ddd"));
        nGramMap.add(nGramsOf("eee", "fff", "ggg", "hhh"));
        nGramMap.add(nGramsOf("iii", "jjj", "kkk", "lll"));
        assertThat(nGramMap.starters()).containsOnlyElementsOf(nGramsOf("aaa", "eee", "iii"));

    }

    @Test
    public void lastNgramOfEachListIsAnEnder() {
        NGramMap nGramMap = new NGramMap();
        nGramMap.add(nGramsOf("aaa", "bbb", "ccc", "ddd"));
        nGramMap.add(nGramsOf("eee", "fff", "ggg", "hhh"));
        nGramMap.add(nGramsOf("iii", "jjj", "kkk", "lll"));
        assertThat(nGramMap.enders()).containsOnlyElementsOf(nGramsOf("ddd", "hhh", "lll"));
    }
}
