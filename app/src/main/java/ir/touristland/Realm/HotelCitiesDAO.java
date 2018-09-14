package ir.touristland.Realm;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.io.Serializable;
import java.util.List;

public class HotelCitiesDAO implements Serializable {

    public static final long serialVersionUID = 1L;

    // private Realm realm;

    public HotelCitiesDAO() {
        /*realm = Realm.getDefaultInstance();*/
    }

    /*public void save(final Hotelcity cities) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createObject(Hotelcity.class, cities.getName());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
    }*/

    public List<Hotelcity> findAll() {
        List<Hotelcity> cities = Select.from(Hotelcity.class).orderBy("namefa").list();
        return cities;
    }

    public List<Hotelcity> findByCode(String fieldName, String Value) {
        /*.sort("input_ordering", Sort.DESCENDING)*/
        ;
        List<Hotelcity> cities = Select.from(Hotelcity.class).where(Condition.prop(fieldName).eq(Value)).orderBy("namefa").list();
        return cities;
    }

    public List<Hotelcity> Contain(String fieldName, String Value) {
        List<Hotelcity> cities = Select.from(Hotelcity.class).where(Condition.prop(fieldName).like("%" + Value + "%")).orderBy("namefa").list();/*.sort("input_ordering", Sort.DESCENDING)*/
        ;
        return cities;
    }

    /*public void deleteAll() {
        findAll().deleteAllFromRealm();
    }*/

    public void deletebyCode(String Code) {
        // findByCode(Code).deleteFromRealm();
    }

    public void update(final List<Hotelcity> cities) {
        for (long i = 0; i < cities.size(); i++) {
            Hotelcity hotelCityResult = new Hotelcity(cities.get((int) i).getName(),
                    cities.get((int) i).getNameFa(),
                    cities.get((int) i).getStateFa(),
                    cities.get((int) i).getLatitude(),
                    cities.get((int) i).getLongitude(),
                    cities.get((int) i).getHotels(),
                    cities.get((int) i).getOnlineHotels());
            hotelCityResult.save();
        }
    }

    /*public void close() {
        realm.close();
    }*/
}
