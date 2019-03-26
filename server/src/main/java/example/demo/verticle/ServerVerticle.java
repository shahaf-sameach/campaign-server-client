package example.demo.verticle;

import example.demo.service.CampaignService;
import example.demo.model.entity.Campaign;
import example.demo.model.repository.CampaignRepository;
import example.demo.model.repository.ImpressionRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServerVerticle extends AbstractVerticle {

    @Autowired
    private CampaignService campaignService;

    @Override
    public void start() {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get("/api/campaign").handler(this::handleGetCampaigns);
        router.post("/api/campaign").handler(this::handleUpsertCampaigns);

        router.get("/*").handler(StaticHandler.create().setCachingEnabled(false).setWebRoot("static"));

        HttpServerOptions options = new HttpServerOptions().setLogActivity(true);

        vertx.createHttpServer(options).requestHandler(router::accept).listen(8080, res -> {
            if (res.succeeded()) {
                System.out.println("Server is online");
            } else {
                System.out.println("Failed to bind!");
            }
        });
    }

    private void handleGetCampaigns(RoutingContext routingContext) {
        String userId = routingContext.request().getParam("user_id");
        HttpServerResponse response = routingContext.response();
        if (userId == null) {
            sendError(400, response, "Invalid Request");
        } else {
            List<Campaign> campaigns = Arrays.asList();
            if (userId.equals("admin")) {
                campaigns = campaignService.getAll();
            } else {
                campaigns = campaignService.getValidCampaigns(userId)
                        .stream()
                        .map(Campaign::buildUserResponse)
                        .collect(Collectors.toList());
            }
            response.putHeader("content-type", "application/json").end(Json.encodePrettily(campaigns));

        }

    }

    private void handleUpsertCampaigns(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        JsonObject json = routingContext.getBodyAsJson();
        Campaign campaign = json.mapTo(Campaign.class);
        if (campaign == null)
            sendError(400, response, "Invalid Request");
        else {
            if (campaignService.upsert(campaign) == null)
                sendError(503, response, "Service Unavailable");
            else
                response.putHeader("content-type", "application/json").end(Json.encodePrettily(campaign));
        }
    }


    private void sendError(int statusCode, HttpServerResponse response, String message) {
        response.setStatusCode(statusCode).end(message);
    }

}
