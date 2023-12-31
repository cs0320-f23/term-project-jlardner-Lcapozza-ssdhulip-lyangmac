package src.back.server;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import src.back.datasource.APIDatasource;
import src.back.datasource.TicketingDatasource;
import src.back.exception.DatasourceException;
import src.back.graph.Edge;
import src.back.handler.*;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static spark.Spark.after;

/**
 * This class makes the server which makes all the handlers related to the project
 */
public class Server {
    private static ArrayList<List<String>> loadedCSV;
    private static List<String> header;
    private static Boolean hasHeader;
    private static Boolean isLoaded;

    /**
     * This method constructs the server and instantiates the instance variables and the port number. It also makes all
     * the handlers which basically handle all the user stories and goals of the program
     */
    public Server() throws DatasourceException {
        int port = 5555;

        Spark.port(port);
        after((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "*");
        });

        APIDatasource datasource = new APIDatasource();

        ConnectionHandler connectionHandler = new ConnectionHandler(datasource);
        Spark.get("connections", connectionHandler);

        DatasetHandler datasetHandler = new DatasetHandler(datasource);
        Spark.get("dataset", datasetHandler);

        TicketingHandler ticketingHandler = new TicketingHandler(new TicketingDatasource());
        Spark.get("ticketing", ticketingHandler);

        Spark.init();
        Spark.awaitInitialization();

        System.out.println("Server started at http://localhost:" + port);
    }

}
