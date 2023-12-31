# list-compare

### 두 개의 리스트의 비교 결과

![img.png](img.png)
`Comparer` 클래스를 통해서 두 개의 리스트를 비교하여 각각의 차집합과 교집합을 구할 수 있습니다.

<br>

### usage
```java
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
```
<br>

### 사용 조건
```java
public SampleMatched(SampleA a, SampleB b) {
    this(a.name(), b.title(), a.age());
}
```
- 교집합인`SampleMatched`클래스는 비교대상인 `A 클래스`와 `B 클래스`를 인수로 갖는 생성자를 가지고 있어야 합니다.
