package gibran.com.br.mvpsample.shot;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.mvpsample.base.BaseContractPresenter;
import gibran.com.br.mvpsample.base.BaseView;

public interface ShotContract {

    interface View extends BaseView<Presenter> {

        void showShots(ArrayList<Shot> shots);

        void showShotsError();

        void showLoading(boolean show);

        boolean isActive();

        void showShotDetailsUi(Shot shot, @Nullable android.view.View v);

        void addShots(ArrayList<Shot> shots);

        void addMoreShotsError();
    }

    interface Presenter extends BaseContractPresenter {
        void loadShots();

        void loadPage(int currentPage);

        void openShotDetails(Shot shot, @Nullable android.view.View v);

    }
}
