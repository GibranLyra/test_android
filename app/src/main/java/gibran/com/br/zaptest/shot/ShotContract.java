package gibran.com.br.zaptest.shot;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.zaptest.base.BaseContractPresenter;
import gibran.com.br.zaptest.base.BaseContractView;

public interface ShotContract {

    interface ContractView extends BaseContractView<Presenter> {

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
