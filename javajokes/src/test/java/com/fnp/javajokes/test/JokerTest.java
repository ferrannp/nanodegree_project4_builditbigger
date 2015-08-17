package com.fnp.javajokes.test;

import com.fnp.javajokes.Joker;
import org.junit.Test;

public class JokerTest {

    @Test
    public void test() {
        Joker joker = new Joker();
        assert joker.getJoke().equals("The best place to hide a dead body " +
                "is page 2 of Google search results.");
    }
}
