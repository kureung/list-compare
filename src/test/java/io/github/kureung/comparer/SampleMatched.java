package io.github.kureung.comparer;

public record SampleMatched(
        String name,
        String title,
        int age
) {
    public SampleMatched(SampleA a, SampleB b) {
        this(a.name(), b.title(), a.age());
    }
}
