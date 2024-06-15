
package com.nemirko.navigation.entity;

public enum VertexType {
    Cabinet,
    Crossroad,
    Port;
    public static VertexType fromStringIgnoreCase(String value) {
        return switch (value.toLowerCase()) {
            case "cabinet" -> Cabinet;
            case "crossroad" -> Crossroad;
            case "port" -> Port;
            default -> throw new IllegalArgumentException("No enum constant " + VertexType.class.getCanonicalName() + "." + value);
        };
    }
}
