package dev.matheuscruz.controller;

import dev.matheuscruz.model.NewDrawingModel;
import io.quarkiverse.renarde.Controller;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.Path;

public class Home extends Controller {

    @CheckedTemplate
    static class Templates {
        public static native TemplateInstance index(NewDrawingModel model);
    }

    @Path("/")
    public TemplateInstance index() {
        return Templates.index(null);
    }

}
