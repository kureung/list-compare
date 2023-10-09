package io.github.kureung.comparer;

import java.util.List;
import java.util.function.BiPredicate;

public class ComparerBuilder<A, B, C> {
    private List<A> aList;
    private List<B> bList;
    private Class<C> matchingClass;
    private BiPredicate<A, B> matchingCondition;

    public ComparerBuilder(List<A> aList, List<B> bList, BiPredicate<A, B> matchingCondition) {
        this.aList = aList;
        this.bList = bList;
        this.matchingCondition = matchingCondition;
    }

    public ComparerBuilder(List<A> aList, List<B> bList) {
        this.aList = aList;
        this.bList = bList;
    }

    public ComparerBuilder() {
    }

    public ComparerBuilder<A, B, C> aList(List<A> aList) {
        this.aList = aList;
        return this;
    }

    public ComparerBuilder<A, B, C> bList(List<B> bList) {
        this.bList = bList;
        return this;
    }

    public ComparerBuilder<A, B, C> matchingClass(Class<C> matchingClass) {
        this.matchingClass = matchingClass;
        return this;
    }

    public ComparerBuilder<A, B, C> matchingCondition(BiPredicate<A, B> matchingCondition) {
        this.matchingCondition = matchingCondition;
        return this;
    }

    public Comparer<A, B, C> build() {
        return new Comparer<>(aList, bList, matchingClass, matchingCondition);
    }
}
