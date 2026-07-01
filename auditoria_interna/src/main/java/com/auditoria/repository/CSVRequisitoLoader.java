package com.auditoria.repository;

import com.auditoria.model.Requisito;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CSVRequisitoLoader {

    /**
     * Carga los 9 requisitos de la norma ISO directamente en memoria.
     * Se mantiene el parámetro InputStream para no romper la firma del método en el controlador,
     * pero ya no se utiliza el archivo físico.
     */
    public static List<Requisito> cargarRequisitosDesdeCSV(InputStream csvStream) throws Exception {
        List<Requisito> lista = new ArrayList<>();

        lista.add(new Requisito(
            "4.1",
            "¿Cómo determinan las cuestiones externas e internas que afectan al sistema? ¿Cuándo se actualizó el análisis por última vez? ¿Quién es responsable de monitorear el contexto?",
            "Análisis de contexto (FODA/PESTEL), actas de revisión por la dirección."
        ));

        lista.add(new Requisito(
            "4.2",
            "¿Quiénes son sus partes interesadas pertinentes? ¿Cómo determinan cuáles de sus necesidades se convierten en requisitos del sistema?",
            "Matriz de partes interesadas, evidencia de mecanismos de consulta."
        ));

        lista.add(new Requisito(
            "6.1",
            "¿Qué riesgos y oportunidades han identificado para el logro de los objetivos? ¿Qué acciones implementaron y cómo evalúan su eficacia?",
            "Matriz de riesgos y oportunidades, planes de acción, seguimiento de eficacia."
        ));

        lista.add(new Requisito(
            "7.1.3",
            "¿Cómo determinan y mantienen la infraestructura necesaria? ¿Qué ocurre cuando falla un equipo crítico?",
            "Programa de mantenimiento, inventario de equipos, órdenes de trabajo, calibraciones."
        ));

        lista.add(new Requisito(
            "8.1",
            "¿Cómo planifican sus procesos operativos? ¿Qué criterios de aceptación usan y cómo controlan los cambios planificados?",
            "Procedimientos operativos, planes de calidad, control de cambios."
        ));

        lista.add(new Requisito(
            "8.5.1",
            "¿Cómo se asegura que el producto o servicio se realiza bajo condiciones controladas? ¿Cómo validan procesos cuyo resultado no se puede verificar después?",
            "Instrucciones de trabajo, registros de inspección, validaciones, competencia del personal."
        ));

        lista.add(new Requisito(
            "8.7",
            "¿Qué hacen cuando detectan una salida no conforme? ¿Quién autoriza una concesión?",
            "Registro de no conformes, autorizaciones de concesión, análisis de tendencias."
        ));

        lista.add(new Requisito(
            "9.1.2",
            "¿Cómo miden la satisfacción de sus clientes o estudiantes? ¿Qué acciones surgieron de los resultados?",
            "Encuestas, informes de análisis, planes de acción derivados."
        ));

        lista.add(new Requisito(
            "10.3",
            "¿Qué mejoras han implementado en el último periodo y de dónde surgieron? ¿Cómo verifican que fueron eficaces?",
            "Planes de mejora, actas de revisión por la dirección, seguimiento a acciones de auditoría."
        ));

        return lista;
    }
}
