package io.github.kureung.comparer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;

public record Comparer<A, B, C>(
        List<A> aList,
        List<B> bList,
        Class<C> matchedClass,
        BiPredicate<A, B> matchingCondition
) {
    public Comparer {
        if (aList.isEmpty()) {
            throw new IllegalStateException("aList can not empty");
        }

        if (bList.isEmpty()) {
            throw new IllegalStateException("bList can not empty");
        }

        if (matchingCondition == null) {
            throw new IllegalStateException("matching condition can not null");
        }
    }

    public <T> ComparedResult<A, B, C> compare() {
        Constructor<C> matchingClassConstructor = matchingClassConstructor();
        List<A> changeableAList = new ArrayList<>(aList);
        List<B> changeableBList = new ArrayList<>(bList);
        List<C> matchingList = matchExtraction(changeableAList, changeableBList, matchingClassConstructor);
        return new ComparedResult<>(changeableAList, changeableBList, matchingList);
    }

    private List<C> matchExtraction(List<A> changeableAList, List<B> changeableBList, Constructor<C> matchingClassConstructor) {
        List<C> matchingList = new ArrayList<>();
        Iterator<A> aIterator = changeableAList.iterator();

        Loop:
        while (aIterator.hasNext()) {
            A a = aIterator.next();
            Iterator<B> bIterator = changeableBList.iterator();
            while(bIterator.hasNext()) {
                B b = bIterator.next();
                if (matchingCondition.test(a, b)) {
                    var matchedInstance = matchedInstance(a, b, matchingClassConstructor);
                    matchingList.add(matchedInstance);
                    aIterator.remove();
                    bIterator.remove();
                    continue Loop;
                }
            }
        }
        return matchingList;
    }

    private Constructor<C> matchingClassConstructor() {
        Class<A> aClass = (Class<A>) aList.get(0).getClass();
        Class<B> bClass = (Class<B>) bList.get(0).getClass();
        try {
            return matchedClass.getConstructor(aClass, bClass);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("matchedClass has not constructor created from class A and class B");
        }
    }

    private C matchedInstance(A a, B b, Constructor<C> matchingClassConstructor) {
        try {
            return matchingClassConstructor.newInstance(a, b);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
