package io.github.kureung.comparer;

import java.util.List;

public record ComparedResult<A, B, C>(
        List<A> onlyAList,
        List<B> onlyBList,
        List<C> matched
) {
}
