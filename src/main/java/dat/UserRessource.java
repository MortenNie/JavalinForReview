package dat;

import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class UserRessource {
    private static Map<String, User> users = new HashMap<>();
    public static void spinUpServerAndRoutes(int port) {
        Javalin app = Javalin.create().start(port);
        initializeRoutes(app);


    }

    private static void initializeRoutes(Javalin app) {
        users.put("user1",new User("user1", "Morten"));
        users.put("user2", new User("user2", "Kage"));

        app.before("/api/*", ctx -> {
            String authHeader = ctx.header("Authorization");
            if (authHeader == null || !authHeader.equals("login token")) {
                ctx.status(401).result("Unauthorized");
            }
        });


        app.after("/*", ctx -> {
            System.out.println(ctx.method() + " request to " + ctx.url() + " - Status: " + ctx.status());
        });


        app.routes(() -> {
            app.get("/api/users/{id}", ctx -> {
                if ("Unauthorized".equals(ctx.resultString())) {
                    return;
                }
                String userId = ctx.pathParam("id");
                User user = users.get(userId);
                if (user != null) {
                    ctx.json(user);
                } else {
                    ctx.status(404).result("User not found");
                }
            });

            app.get("/api/users", ctx -> {
                if ("Unauthorized".equals(ctx.resultString())) {
                    return;
                }
                ctx.json(users);
            });

            app.post("/api/users/name", ctx -> {
                if ("Unauthorized".equals(ctx.resultString())) {
                    return;
                }
                CreateUserRequest request = ctx.bodyAsClass(CreateUserRequest.class);
                String name = request.getName();

                // Now 'name' contains the value from the JSON object
                String userId = "user" + (users.size() + 1);
                User newUser = new User(userId, name);
                users.put(userId, newUser);

                ctx.status(201).json(newUser);
            });

            app.delete("/api/users/{id}", ctx -> {
                String userId = ctx.pathParam("id");
                User removedUser = users.remove(userId);
                if (removedUser != null) {
                    ctx.status(204);
                } else {
                    ctx.status(404);
                }
            });
        });
    }



    @Getter
    @Setter
    static class CreateUserRequest {
        private String name;


    }
}


