package ch.hslu.mobpro.opendata.transport;

import ch.hslu.mobpro.opendata.transport.model.ConnectionResult;
import ch.hslu.mobpro.opendata.transport.model.LocationResult;
import ch.hslu.mobpro.opendata.transport.model.StationboardResult;
import ch.hslu.mobpro.opendata.transport.parameter.ConnectionParameter;
import ch.hslu.mobpro.opendata.transport.parameter.StationboardParameter;
import ch.hslu.mobpro.opendata.transport.type.LocationType;
import ch.hslu.mobpro.opendata.transport.type.TransportationType;
import ch.hslu.mobpro.opendata.transport.util.BooleanUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.HttpUrl;
import okhttp3.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import android.util.Log;

/**
 * Handles the connection the open data transport api.
 */
public class TransportClient {
    private final OkHttpClient client = new OkHttpClient();
    private final String baseUrl = "http://transport.opendata.ch/v1/";

    private final Gson gson;

    public TransportClient() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    }

    /**
     * Returns the matching locations for the given parameters.
     *
     * @param query        Specifies the location name to search for
     * @param locationType Specifies the location type
     * @return Returns the matching locations for the given parameters.
     */
    public LocationResult getLocations(String query, LocationType locationType) throws IOException {
        HttpUrl url = HttpUrl.parse(baseUrl.concat("locations")).newBuilder()
        .addQueryParameter("query", query)
        .addQueryParameter("type", locationType.toString()).build();


        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), LocationResult.class);
        }
    }

    /**
     * Returns the matching locations for the given parameters.
     *
     * @param x                   Latitude
     * @param y                   Longitude
     * @param transportationTypes Transportation means
     * @return Returns the matching locations for the given parameters.
     */
    public LocationResult getLocations(double x, double y, TransportationType... transportationTypes) throws IOException {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl.concat("locations")).newBuilder()
                .addQueryParameter("x", new Double(x).toString())
                .addQueryParameter("y", new Double(y).toString());

        for (TransportationType t : transportationTypes) {
            builder.addQueryParameter("transportations[]", t.toString());
        }

        Request request = new Request.Builder().url(builder.build()).build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), LocationResult.class);
        }
    }

    /**
     * Returns the next connections from a location to another.
     *
     * @param from Specifies the departure location of the connection
     * @param to   Specifies the arrival location of the connection
     * @return Returns the next connections from a location to another.
     */
    public ConnectionResult getConnections(String from, String to) {
        try {
            return getConnections(new ConnectionParameter(from, to));
        } catch (Exception e) {
            Log.e("TransportClient", e.toString());
        }

        return null;
    }

    /**
     * Returns the next connections from a location to another.
     *
     * @param params Connections parameter object
     * @return Returns the next connections from a location to another.
     */
    public ConnectionResult getConnections(ConnectionParameter params) throws IOException {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl.concat("connections")).newBuilder()
                .addQueryParameter("from", params.getFrom())
                .addQueryParameter("to", params.getTo());

        for (String via : params.getVia()) {
            builder.addQueryParameter("via[]", via);
        }

        if (params.getDate() != null) builder.addQueryParameter("date", params.getDate());
        if (params.getTime() != null) builder.addQueryParameter("time", params.getTime());

        for (TransportationType t : params.getTransportations()) {
            builder.addQueryParameter("transportations[]", t.toString());
        }

        builder.addQueryParameter("direct", BooleanUtils.toNumeralString(params.isDirect()));
        builder.addQueryParameter("sleeper", BooleanUtils.toNumeralString(params.isSleeper()));
        builder.addQueryParameter("couchette", BooleanUtils.toNumeralString(params.isCouchette()));
        builder.addQueryParameter("bike", BooleanUtils.toNumeralString(params.isBike()));

        builder.addQueryParameter("accessability", params.getAccessability().toString());


        Request request = new Request.Builder().url(builder.build()).build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), ConnectionResult.class);
        }
    }

    /**
     * Returns the next connections leaving from a specific location.
     *
     * @param station Specifies the location of which a stationboard should be returned
     * @return Returns the next connections leaving from a specific location.
     */
    public StationboardResult getStationboardByStation(final String station) throws IOException {
        return getStationboard(new StationboardParameter() {{
            setId(station);
        }});
    }

    /**
     * Returns the next connections leaving from a specific location.
     *
     * @param id The id of the station whose stationboard should be returned
     * @return Returns the next connections leaving from a specific location.
     */
    public StationboardResult getStationboardById(final String id) throws IOException {
        return getStationboard(new StationboardParameter() {{
            setId(id);
        }});
    }

    /**
     * Returns the next connections leaving from a specific location.
     *
     * @param params Stationboard parameter object
     * @return Returns the next connections leaving from a specific location.
     */
    public StationboardResult getStationboard(StationboardParameter params) throws IOException {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl.concat("stationboard")).newBuilder();

        if (params.getStation() != null) builder.addQueryParameter("station", params.getStation());
        if (params.getId() != null) builder.addQueryParameter("id", params.getId());

        for (TransportationType t : params.getTransportations()) {
            builder.addQueryParameter("transportations[]", t.toString());
        }

        if (params.getDateTime() != null) builder.addQueryParameter("datetime", params.getDateTime());
        builder.addQueryParameter("type", params.getDateTimeType().toString());
        builder.addQueryParameter("limit", String.valueOf(params.getLimit()));


        Request request = new Request.Builder().url(builder.build()).build();
        try (Response response = client.newCall(request).execute()) {
            return gson.fromJson(response.body().string(), StationboardResult.class);
        }
    }
}
