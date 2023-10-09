package io.github.kureung.comparer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ComparerTest {
    @Test
    void compare() {
        List<SampleA> sampleAList = List.of(new SampleA("kureung", 10), new SampleA("mark", 15));
        List<SampleB> sampleBList = List.of(new SampleB("kureung", "news"), new SampleB("June", "coffee"));

        Comparer<SampleA, SampleB, SampleMatched> sut = new ComparerBuilder<SampleA, SampleB, SampleMatched>()
                .aList(sampleAList)
                .bList(sampleBList)
                .matchingClass(SampleMatched.class)
                .matchingCondition((a, b) -> a.name().equals(b.name()))
                .build();

        assertThat(sut.compare()).isEqualTo(result());
    }

    private ComparedResult<SampleA, SampleB, SampleMatched> result() {
        List<SampleA> onlyAList = List.of(new SampleA("mark", 15));
        List<SampleB> onlyBList = List.of(new SampleB("June", "coffee"));
        List<SampleMatched> matched = List.of(new SampleMatched("kureung", "news", 10));
        return new ComparedResult<>(onlyAList, onlyBList, matched);
    }

    @Test
    void useage() {
        List<SampleA> sampleAList = List.of(new SampleA("kureung", 10), new SampleA("mark", 15));
        List<SampleB> sampleBList = List.of(new SampleB("kureung", "news"), new SampleB("June", "coffee"));

        Comparer<SampleA, SampleB, SampleMatched> sut = new ComparerBuilder<SampleA, SampleB, SampleMatched>()
                .aList(sampleAList)
                .bList(sampleBList)
                .matchingClass(SampleMatched.class)
                .matchingCondition((a, b) -> a.name().equals(b.name()))
                .build();

        ComparedResult<SampleA, SampleB, SampleMatched> result = sut.compare();

        // A 차집합
        List<SampleA> onlyAList = result.onlyAList();
        assertThat(onlyAList)
                .hasSize(1)
                .contains(new SampleA("mark", 15));

        // B 차집합
        List<SampleB> onlyBList = result.onlyBList();
        assertThat(onlyBList)
                .hasSize(1)
                .contains(new SampleB("June", "coffee"));

        // 교집합
        List<SampleMatched> matched = result.matched();
        assertThat(matched)
                .hasSize(1)
                .contains(new SampleMatched("kureung", "news", 10));
    }
}