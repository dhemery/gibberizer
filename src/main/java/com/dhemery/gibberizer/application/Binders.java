package com.dhemery.gibberizer.application;

import javafx.beans.Observable;
import javafx.beans.binding.ListBinding;
import javafx.beans.binding.MapBinding;
import javafx.beans.binding.SetBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.util.List;
import java.util.function.Supplier;

public class Binders {
    static <V, T extends ObservableList<V>> ListBinding<V> createListBinding(Supplier<T> supplier, Observable... dependencies) {
        return new ListBinding<>() {
            {
                super.bind(dependencies);
            }

            @Override
            protected ObservableList<V> computeValue() {
                return supplier.get();
            }
        };
    }

    static <K, V, T extends ObservableMap<K, V>> MapBinding<K, V> createMapBinding(Supplier<T> supplier, Observable... dependencies) {
        return new MapBinding<>() {
            {
                super.bind(dependencies);
            }

            @Override
            protected ObservableMap<K, V> computeValue() {
                return supplier.get();
            }
        };
    }

    static <V, T extends ObservableSet<V>> SetBinding<V> createSetBinding(Supplier<T> supplier, Observable... dependencies) {
        return new SetBinding<>() {
            {
                super.bind(dependencies);
            }

            @Override
            protected ObservableSet<V> computeValue() {
                return supplier.get();
            }
        };
    }
}
