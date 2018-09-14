package ir.touristland;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.orm.SugarApp;
import com.orm.SugarContext;

import java.io.File;

import ir.touristland.Classes.DataBaseHelper;
import ir.touristland.DI.AppModule;
import ir.touristland.DI.DaggerMainComponent;
import ir.touristland.DI.ImageLoaderMoudle;
import ir.touristland.DI.MainComponent;
import ir.touristland.DI.NetModule;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Application extends SugarApp {

    public static Typeface font;
    public static Typeface fontBold;
    public static Animation in;
    public static Animation out;
    public static SQLiteDatabase database;
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    public static int myAds = 42907631;
    public static Resources resources;
    public static Activity activity;
    public static MainComponent component;
    public static MainComponent component2;
    public static MainComponent ravis;

    public static Application get(AppCompatActivity activity) {
        return (Application) activity.getApplication();
    }

    public static MainComponent getComponent() {
        return component;
    }

    public static MainComponent getComponent2() {
        return component2;
    }

    public static MainComponent GetRavis() {
        return ravis;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        resources = getResources();

        /*Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("Touristland.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);*/
        SugarContext.init(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMedium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        component = DaggerMainComponent.builder()
                .imageLoaderMoudle(new ImageLoaderMoudle(this))
                .netModule(new NetModule("https://eghamat24.com/"))
                .appModule(new AppModule(this))
                .build();

        component2 = DaggerMainComponent.builder()
                .imageLoaderMoudle(new ImageLoaderMoudle(this))
                .netModule(new NetModule(getString(R.string.url)))
                .appModule(new AppModule(this))
                .build();

        ravis = DaggerMainComponent.builder()
                .imageLoaderMoudle(new ImageLoaderMoudle(this))
                .netModule(new NetModule(getString(R.string.ravisUrl)))
                .appModule(new AppModule(this))
                .build();

        in = AnimationUtils.loadAnimation(this,
                R.anim.zoom_in);

        out = AnimationUtils.loadAnimation(this,
                R.anim.zoom_out);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        try {
            new DataBaseHelper(getApplicationContext()) {
                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                    String DB_PATH = Environment.getDataDirectory() + "/data/" + getBaseContext().getPackageName() + "/databases/kishtravel_db.db";
                    String myPath = DB_PATH;
                    SQLiteDatabase.deleteDatabase(new File(myPath));
                    getBaseContext().deleteDatabase(Environment.getDataDirectory() + "/data/" + getBaseContext().getPackageName() + "/databases/kishtravel_db.db");
                }

                @Override
                public void onCreate(SQLiteDatabase db) {
                }
            };

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        String path = Environment.getDataDirectory() + "/data/" + getPackageName() + "/databases/kishtravel_db.db";
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        font = Typeface.createFromAsset(this.getAssets(), "fonts/IRANSansMedium.ttf");
        fontBold = Typeface.createFromAsset(this.getAssets(), "fonts/IRANSansBold.ttf");
    }
}
