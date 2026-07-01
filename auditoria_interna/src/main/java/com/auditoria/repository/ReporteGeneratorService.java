package com.auditoria.repository;

import com.auditoria.model.Requisito;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ReporteGeneratorService {

    public static void exportarReporte(List<Requisito> datos, String formato, File destino) throws Exception {
        if (formato.contains("CSV")) {
            generarCSV(datos, destino);
        } else if (formato.contains("WORD")) {
            generarWordPlaceholder(datos, destino);
        } else if (formato.contains("PDF")) {
            generarPDFPlaceholder(datos, destino);
        }
    }

    private static void generarCSV(List<Requisito> datos, File destino) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(destino, StandardCharsets.UTF_8))) {
            // Byte Order Mark (BOM) para asegurar correcta codificación de acentos en Excel
            bw.write('\ufeff');
            bw.write("Cláusula Norma,Dictamen Final,Evidencias y Notas Encontradas\n");
            for (Requisito req : datos) {
                String obsLimpia = req.getObservacionesText().replace("\"", "\"\"");
                bw.write(String.format("\"%s\",\"%s\",\"%s\"\n", 
                        req.getId(), req.getEstado().toString(), obsLimpia));
            }
        }
    }

    private static void generarWordPlaceholder(List<Requisito> datos, File destino) throws Exception {
        // Implementación recomendada utilizando la biblioteca Apache POI XWPF Document
        // Permite adjuntar el formato de encabezados corporativos requeridos por la guía de auditoría
        try (FileWriter writer = new FileWriter(destino)) {
            writer.write("[Formato Estructurado de Auditoría Interna DOCX - Apache POI Placeholder]\n");
            for(Requisito req : datos) {
                writer.write(req.getId() + " - " + req.getEstado() + " - " + req.getObservacionesText() + "\n");
            }
        }
    }

    private static void generarPDFPlaceholder(List<Requisito> datos, File destino) throws Exception {
        // Implementación recomendada utilizando la biblioteca iText o OpenPDF
        // Ideal para estructurar tablas definitivas no alterables
        try (FileWriter writer = new FileWriter(destino)) {
            writer.write("[Formato de Reporte de Salida PDF Estricto - iText/OpenPDF Placeholder]\n");
            for(Requisito req : datos) {
                writer.write(req.getId() + " - " + req.getEstado() + " - " + req.getObservacionesText() + "\n");
            }
        }
    }
}
