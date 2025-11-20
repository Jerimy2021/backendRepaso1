package com.naruto.charactermanager.infrastructure.utils;

import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Builder para construir criterios de búsqueda dinámicos usando JPA Criteria API
 * Facilita la creación de filtros condicionales para consultas con Specification
 * 
 * @param <R> Tipo de la entidad sobre la cual se construyen los criterios
 */
public class SearchBuilder<R> {

    private final Root<R> root;
    private final CriteriaQuery<?> query;
    private final CriteriaBuilder builder;
    private Predicate predicate;

    public SearchBuilder(Root<R> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        this.root = root;
        this.query = query;
        this.builder = builder;
    }

    public SearchBuilder<R> notDeleted() {
        var currentPredicate = builder.not(root.get("isDeleted"));

        if (predicate == null) {
            predicate = currentPredicate;
        } else {
            predicate = builder.and(this.predicate, currentPredicate);
        }

        return this;
    }

    public SearchBuilder<R> like(String field, String filter) {
        var filters = SearchBuilder.splitByLike(filter);
        var predicates = new ArrayList<Predicate>();

        if (!filters.isEmpty()) {
            Path<String> expression = root.get(field);

            for (var value : filters) {
                predicates.add(builder.like(builder.upper(expression), "%" + value + "%"));
            }

            if (!predicates.isEmpty()) {
                var currentPredicate = builder.and(predicates.toArray(new Predicate[]{}));

                if (predicate == null) {
                    predicate = currentPredicate;
                } else {
                    predicate = builder.and(this.predicate, currentPredicate);
                }
            }
        }

        return this;
    }

    public SearchBuilder<R> has(String field, Boolean filter) {
        Path<Boolean> path = root.get(field);

        if (filter != null) {
            var currentPredicate = builder.equal(path, filter);

            if (predicate == null) {
                predicate = currentPredicate;
            } else {
                predicate = builder.and(predicate, currentPredicate);
            }
        }

        return this;
    }

    private static List<String> splitByLike(String input) {
        if (input == null || input.isEmpty())
            return new ArrayList<>();

        var values = input.split(" ");

        return Arrays
                .stream(values)
                .filter(p -> !p.trim().isEmpty())
                .map(p -> p.toUpperCase(Locale.ROOT))
                .toList();
    }

    public Predicate getPredicate() {
        return predicate;
    }
}

