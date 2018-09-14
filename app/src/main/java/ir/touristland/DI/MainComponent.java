package ir.touristland.DI;

import javax.inject.Singleton;

import dagger.Component;
import ir.touristland.Activities.Flight.FlightActivity;
import ir.touristland.Activities.Flight.FlightDetailActivity;
import ir.touristland.Activities.Hotels.HotelDetailsActivity;
import ir.touristland.Activities.Hotels.HotelListActivity;
import ir.touristland.Activities.Hotels.HotelsCentersActivity;
import ir.touristland.Activities.Kishgardi.KishgardiCentersActivity;
import ir.touristland.Activities.SplashActivity;
import ir.touristland.Adapters.FlightAdapter;
import ir.touristland.Adapters.HotelAdapter;
import ir.touristland.Adapters.RoomsAdapter;
import ir.touristland.Asynktask.AsynctaskGetHotelList;
import ir.touristland.Asynktask.AsynctaskGetPost;
import ir.touristland.Asynktask.AsynctaskHotelsTypes;
import ir.touristland.Asynktask.AsynctaskReserveFlight;
import ir.touristland.Fragments.SlideShowFragment;
import ir.touristland.SliderTypes.BaseSliderView;

/**
 * Created by KingStar on 3/2/2018.
 */
@Singleton
@MainScope
@Component(modules = {AppModule.class, ImageLoaderMoudle.class, NetModule.class})
public interface MainComponent {

    void Inject(SplashActivity mainActivity);

    void Inject(SlideShowFragment mainActivity);

    void Inject(BaseSliderView mainActivity);

    void Inject(FlightDetailActivity mainActivity);

    void Inject(KishgardiCentersActivity mainActivity);

    void Inject(HotelsCentersActivity mainActivity);

    void Inject(FlightActivity mainActivity);

    void Inject(RoomsAdapter mainActivity);

    void Inject(HotelListActivity mainActivity);

    void Inject(AsynctaskGetPost mainActivity);

    void Inject(AsynctaskGetHotelList mainActivity);

    void Inject(AsynctaskHotelsTypes mainActivity);

    void Inject(FlightAdapter mainActivity);

    void Inject(HotelAdapter mainActivity);

    void Inject(HotelDetailsActivity mainActivity);

    void Inject(AsynctaskReserveFlight mainActivity);

}
