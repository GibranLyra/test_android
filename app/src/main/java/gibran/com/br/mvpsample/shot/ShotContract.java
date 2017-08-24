package gibran.com.br.mvpsample.shot;

import java.util.ArrayList;

import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.mvpsample.base.BaseContractPresenter;
import gibran.com.br.mvpsample.base.BaseView;

public interface ShotContract {

    interface View extends BaseView<Presenter> {

        void showShots(ArrayList<Shot> shots);

        void showShotsError();

        void showLoading(boolean show);
    }

    interface Presenter extends BaseContractPresenter {
        void loadShots();
    }
}
