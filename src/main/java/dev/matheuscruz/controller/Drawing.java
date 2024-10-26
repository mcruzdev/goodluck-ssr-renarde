package dev.matheuscruz.controller;

import dev.matheuscruz.model.Database;
import dev.matheuscruz.model.DrawingModel;
import dev.matheuscruz.model.NewDrawingModel;
import dev.matheuscruz.model.Participant;
import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class Drawing extends Controller {

    @ConfigProperty(name = "host.url")
    String host;

    @CheckedTemplate
    static class Templates {

        public static native TemplateInstance newDrawing(NewDrawingModel model);

        public static native TemplateInstance participants(String drawingCode);

        public static native TemplateInstance waitDrawingResult(String drawingCode);

        public static native TemplateInstance winner(String winner);

    }

    public TemplateInstance newDrawing() {

        String newDrawingCode = UUID.randomUUID().toString();

        String drawingUrl = "%s/drawings/%s/participants".formatted(
                host, newDrawingCode
        );

        DrawingModel newModel = new DrawingModel(new HashSet<>(), drawingUrl, null);

        Database.DRAWINGS.put(newDrawingCode, newModel);

        System.out.println("was created a new drawing" + newDrawingCode);

        return Templates.newDrawing(new NewDrawingModel(
                drawingUrl, newModel.participantsSize(), newDrawingCode, ""
        ));
    }

    @Path("/drawings/{drawingCode}/participants")
    public TemplateInstance participants(
            @PathParam(value = "drawingCode") String drawingCode) {

        notFoundIfNull(Database.DRAWINGS.get(drawingCode));

        return Templates.participants(drawingCode);
    }

    @Path("/Drawing/winner/{drawingCode}")
    public TemplateInstance winner(@PathParam(value = "drawingCode") String drawingCode) {

        System.out.println("finding a winner for " + drawingCode);
        DrawingModel drawingModel = Database.DRAWINGS.get(drawingCode);

        List<Participant> list = new ArrayList<>(drawingModel.participants()
                .stream().toList());

        if (!list.isEmpty()) {
            Collections.shuffle(list);

            Participant first = list.getFirst();

            Database.add(drawingCode,
                    new DrawingModel(
                            drawingModel.participants(),
                            drawingModel.drawingUrl(),
                            first.email()
                    ));

            return Templates.winner(first.email());
        }

        return Templates.newDrawing(new NewDrawingModel(
                drawingModel.drawingUrl(),
                drawingModel.participantsSize(),
                drawingCode,
                "There is no participant"
        ));
    }

    @POST
    @Path("/drawings/{drawingCode}/participants")
    public TemplateInstance addParticipant(@RestPath String drawingCode,
                                           @RestForm @NotBlank String email) {

        DrawingModel drawingModel = Database.DRAWINGS.get(drawingCode);

        notFoundIfNull(drawingModel);

        drawingModel.participants().add(new Participant(
                email
        ));

        return Templates.waitDrawingResult(drawingCode);
    }

}
