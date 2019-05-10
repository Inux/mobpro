package ch.hslu.mobpro.routify.persistence;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    private Context context;

    public DatabaseHelper(Context context) {
        this.context = context;
        String databaseName = context.getString(R.string.database_name);
        this.routifyDatabase = Room.databaseBuilder(context, RoutifyDatabase.class, databaseName).build();
    }

    public void getAllConnections(ListView listView) {
        GetConnectionTask getterTask = new GetConnectionTask(routifyDatabase, listView, context);
        getterTask.execute();
    }

    public void saveConnection(Connection c) {
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
        AsyncTask.execute(() -> routifyDatabase.connectionEntityDao().insert(connectionEntity));
    }

    private static class GetConnectionTask extends AsyncTask<Void, Void, List<ConnectionEntity>> {

        private RoutifyDatabase db;
        private ListView listView;
        private Context context;

        GetConnectionTask(RoutifyDatabase routifyDatabase, ListView listView, Context context) {
            this.db = routifyDatabase;
            this.listView = listView;
            this.context = context;
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
                        c.getId(),
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
            //ArrayAdapter<Connection> arrayAdapter = new ArrayAdapter<Connection>(context, R.layout.connection_item_layout, connectionList);
            ArrayAdapter<Connection> arrayAdapter = new ArrayAdapter<Connection>(context, android.R.layout.simple_list_item_1, connectionList);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to delete this connection?").setPositiveButton("YES", (dialog, which) -> {
                        DeleteConnectionTask deleter = new DeleteConnectionTask(db, context);
                        deleter.execute(connectionList.get(position).getId());
                        dialog.dismiss();
                    }).setNegativeButton("NO", (dialog, which) -> dialog.dismiss()).show();
                    return true;
                }
            });
        }
    }


    private static class DeleteConnectionTask extends AsyncTask<Integer, Void, Void> {

        private RoutifyDatabase db;
        private Context context;

        DeleteConnectionTask(RoutifyDatabase routifyDatabase, Context context) {
            this.db = routifyDatabase;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            // Todo: Select the DB entry
            ConnectionEntity entity = db.connectionEntityDao().loadOne(integers[0]);
            // Todo: Delete it
            db.connectionEntityDao().delete(entity);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Todo: Refresh the GUI
            /*
            ListView listView = (ListView)findViewById(R.id.connection_listview);
            listView.setAdapter(null);
            new DatabaseHelper(context).getAllConnections(listView);
             */
        }
    }
}
