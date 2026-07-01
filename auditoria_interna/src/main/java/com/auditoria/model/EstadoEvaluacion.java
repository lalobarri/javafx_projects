package com.auditoria.model;

public enum EstadoEvaluacion {
    CUMPLE("Cumple"),
    NO_CUMPLE("No Cumple"),
    OBSERVACION("Observación"),
    PENDIENTE("Pendiente");

    private final String descripcion;
    EstadoEvaluacion(String descripcion) { this.descripcion = descripcion; }
    @Override public String toString() { return descripcion; }
}
