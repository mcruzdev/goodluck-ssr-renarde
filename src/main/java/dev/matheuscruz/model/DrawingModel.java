package dev.matheuscruz.model;

import java.util.Set;

public record DrawingModel(Set<Participant> participants, String drawingUrl, String winner) {

    public Integer participantsSize() {
        return participants.size();
    }
}
