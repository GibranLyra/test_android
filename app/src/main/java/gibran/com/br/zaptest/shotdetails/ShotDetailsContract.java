package gibran.com.br.zaptest.shotdetails;

import gibran.com.br.dribbleservice.model.Shot;
import gibran.com.br.zaptest.base.BaseContractPresenter;
import gibran.com.br.zaptest.base.BaseContractView;

public interface ShotDetailsContract {

    interface ContractView extends BaseContractView<Presenter> {

        void showShot(Shot shot);

        void showShotError();

        void showLoading(boolean show);

        boolean isActive();
    }

    interface Presenter extends BaseContractPresenter {
        void loadShot(int id);
    }
}
