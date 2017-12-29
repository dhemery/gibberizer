package com.dhemery.gibberizer;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

public class Gibberish implements Supplier<String> {
    private static final int SIZE = 1000;
    private static final Random RANDOM = new Random();
    private final List<Step> startSteps = new ArrayList<>();
    private final Map<String, List<Step>> stepsByHead;

    public Gibberish(int overlap, String text) {
        stepsByHead = IntStream.rangeClosed(0, text.length() - overlap)
                .mapToObj(p -> new Step(text, p, overlap))
                .peek(s -> Optional.of(s).filter(Step::isStartStep).ifPresent(startSteps::add))
                .collect(groupingBy(Step::head));
    }

    @Override
    public String get() {
        Step startStep = selectRandom(startSteps);
        return stepStream(startStep)
                .limit(SIZE)
                .map(Step::lastChar)
                .collect(joining("", startStep.head(), ""));
    }

    private Stream<Step> stepStream(Step startStep) {
        return Stream.iterate(startStep, s -> !s.isEndStep(), this::randomNextStep);
    }

    private Step randomNextStep(Step step) {
        return selectRandom(stepsByHead.get(step.tail()));
    }

    private static Step selectRandom(List<Step> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
}
