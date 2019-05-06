package ch.hslu.mobpro.routify.persistence;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ch.hslu.mobpro.routify.R;
import ch.hslu.mobpro.routify.model.Connection;
import ch.hslu.mobpro.routify.model.Filters;
import ch.hslu.mobpro.routify.model.Settings;

public class DatabaseHelper {

    private RoutifyDatabase routifyDatabase;

    public DatabaseHelper(Context context) {
        String databaseName = context.getString(R.string.database_name);
        this.routifyDatabase = Room.databaseBuilder(context, RoutifyDatabase.class, databaseName).build();
    }

    public void getAllConnections(TextView textView) {
        GetConnectionTask getterTask = new GetConnectionTask(routifyDatabase, textView);
        getterTask.execute();
    }

    public int saveConnection(Connection c) {
        final ConnectionEntity connectionEntity = new ConnectionEntity(
                c.getFrom(),
                c.getTo(),
                c.getFilters().getMaxDuration(),
                c.getFilters().getBusAllowed(),
                c.getFilters().getTrainAllowed(),
                c.getSettings().getShowFrom().toString(), // Todo: LocalDateTime
                c.getSettings().getShowTo().toString(), // Todo: LocalDateTime
                c.getSettings().getMonday(),
                c.getSettings().getTuesday(),
                c.getSettings().getWednesday(),
                c.getSettings().getThursday(),
                c.getSettings().getFriday(),
                c.getSettings().getSaturday(),
                c.getSettings().getSunday()
        );
        //InsertTask insertTask = new InsertTask(routifyDatabase);
        AsyncTask.execute(() -> routifyDatabase.connectionEntityDao().insert(connectionEntity));
        //insertTask.doInBackground(connectionEntity);
        return connectionEntity.getId();
    }

    private static class GetConnectionTask extends AsyncTask<Void, Void, List<ConnectionEntity>> {

        private RoutifyDatabase db;
        private TextView textView;

        GetConnectionTask(RoutifyDatabase routifyDatabase, TextView textView) {
            this.db = routifyDatabase;
            this.textView = textView;
        }

        @Override
        protected List<ConnectionEntity> doInBackground(Void... voids) {
            return db.connectionEntityDao().loadAll();
        }

        @Override
        protected void onPostExecute(List<ConnectionEntity> entityList) {
            ArrayList<Connection> connectionList = new ArrayList<>();
            for(ConnectionEntity c : entityList) {
                Calendar cal = Calendar.getInstance();
                connectionList.add(new Connection(
                        c.getFrom(),
                        c.getTo(),
                        new Filters(
                                c.getMaxDuration(),
                                c.getBusAllowed(),
                                c.getTrainAllowed()
                        ),
                        new Settings(
                                LocalDateTime.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0),
                                LocalDateTime.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59),
                                c.getMonday(),
                                c.getTuesday(),
                                c.getWednesday(),
                                c.getThursday(),
                                c.getFriday(),
                                c.getSaturday(),
                                c.getSunday()
                        )));
            }
            textView.setText(String.valueOf(connectionList.size()));
        }
    }
}
