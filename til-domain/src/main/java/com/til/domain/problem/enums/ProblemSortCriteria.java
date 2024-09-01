package com.til.domain.problem.enums;

public enum ProblemSortCriteria {

    ID("id"),
    TITLE("title"),
    LEVEL("level"),
    PASSED_COUNT("passedCount"),
    PASS_RATE("passRate");

    private final String fieldName;

    ProblemSortCriteria(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static ProblemSortCriteria fromString(String fieldName) {
        for (ProblemSortCriteria field : ProblemSortCriteria.values()) {
            if (field.fieldName.equalsIgnoreCase(fieldName)) {
                return field;
            }
        }
        throw new IllegalArgumentException("정렬할 수 없는 속성입니다: " + fieldName);
    }
}
