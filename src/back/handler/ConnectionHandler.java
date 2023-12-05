package wnba.backend.handler;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import wnba.backend.datasource.Datasource;
import wnba.backend.exception.DatasourceException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.cache.Cache;
import wnba.backend.graph.Edge;

/**
 * This class deals with getting the broadband percentage
 */
public class ConnectionHandler implements Route {
    private Datasource datasource;

    /**
     * The constructor builds a broadband handler with a cache and the datasource
     * @param datasource is the data source
     */
    public ConnectionHandler(Datasource datasource) {
        this.datasource = datasource;
    }
    /**
     * This method gets the state and county names and gets the broadband percentage
     * @param request is the request
     * @param response is the response
     * @return a 2D JSon Array
     */
    public Object handle(Request request, Response response) {
        Moshi moshi = new Moshi.Builder().build();
        Type mapObject = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> mapAdapter = moshi.adapter(mapObject);
        Map<String, Object> responseMap = new HashMap<>();

        String player1 = request.queryParams("player1");
        String player2 = request.queryParams("player2");


        ArrayList<Edge> data;
        data = this.datasource.getConnection(null, null);

        responseMap.put("result", "success");

        return mapAdapter.toJson(responseMap);
    }
}
