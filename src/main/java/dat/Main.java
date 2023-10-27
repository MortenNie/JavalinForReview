package dat;
import io.javalin.Javalin;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Main {


    public static void main(String[] args) {


        UserRessource.spinUpServerAndRoutes(7008);

    }

}