package com.soliduslabs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindPairTest
{
    private final FindPair findPair = new FindPair();

    static FindPair.Item[] itemsListSample;

    @BeforeAll
    public static void init()
    {
        System.out.println("BeforeAll init() method called");

        itemsListSample = new FindPair.Item[]{
                new FindPair.Item("item", 500),
                new FindPair.Item("item", 700),
                new FindPair.Item("item", 1000),
                new FindPair.Item("item", 1400),
                new FindPair.Item("item", 2000),
                new FindPair.Item("item", 6000)
        };
    }

    static Stream<Arguments> itemsArrayProvider()
    {
        return Stream.of(
                Arguments.of(itemsListSample, 2500, new int[]{500, 2000}),
                Arguments.of(itemsListSample, 2300, new int[]{700, 1400}),
                Arguments.of(itemsListSample, 10000, new int[]{2000, 6000}),

                Arguments.of(new FindPair.Item[] {
                        new FindPair.Item("item", 500),
                        new FindPair.Item("item", 700),
                }, 5000, new int[]{500, 700}),
                Arguments.of(new FindPair.Item[] {
                        new FindPair.Item("item", 500),
                        new FindPair.Item("item", 700),
                        new FindPair.Item("item", 1000),
                }, 5000, new int[]{700, 1000})
        );
    }

    @ParameterizedTest
    @MethodSource("itemsArrayProvider")
    void findPair(FindPair.Item[] valueSourceItems, int valueSourceTotal, int[] result)
    {
        var items = findPair.findPair(valueSourceItems, valueSourceTotal).get();

        assertEquals(result[0], items[0].price);
        assertEquals(result[1], items[1].price);
    }

    static Stream<Arguments> notPossibleArrayProvider()
    {
        return Stream.of(
                Arguments.of(itemsListSample, 1100),
                Arguments.of(new FindPair.Item[] {}, 5000),
                Arguments.of(new FindPair.Item[] {
                        new FindPair.Item("item", 500),
                }, 5000)
        );
    }

    @ParameterizedTest
    @MethodSource("notPossibleArrayProvider")
    void findPair_NotPossible(FindPair.Item[] valueSourceItems, int valueSourceTotal)
    {
        assert(findPair.findPair(valueSourceItems, valueSourceTotal).isEmpty());
    }

    static Stream<Arguments> itemsTriadArrayProvider()
    {
        return Stream.of(
                Arguments.of(itemsListSample, 2200, new int[]{500, 700, 1000}),
                Arguments.of(itemsListSample, 2500, new int[]{500, 700, 1000}),
                Arguments.of(itemsListSample, 2600, new int[]{500, 700, 1400}),
                Arguments.of(itemsListSample, 2900, new int[]{500, 1000, 1400}),
                Arguments.of(itemsListSample, 3100, new int[]{700, 1000, 1400}),
                Arguments.of(itemsListSample, 9300, new int[]{1000, 2000, 6000}),
                Arguments.of(itemsListSample, 11500, new int[]{1400, 2000, 6000})
        );
    }

    @ParameterizedTest
    @MethodSource("itemsTriadArrayProvider")
    void findTriad(FindPair.Item[] valueSourceItems, int valueSourceTotal, int[] result)
    {
        var items = findPair.findTriad(valueSourceItems, valueSourceTotal).get();

        assertEquals(result[0], items[0].price);
        assertEquals(result[1], items[1].price);
    }
}
