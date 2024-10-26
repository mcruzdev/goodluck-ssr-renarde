package dev.matheuscruz.model;

public record NewDrawingModel(
        String drawingUrl,
        Integer participants,
        String drawingCode,
        String message
) {

}
