package gibran.com.br.zaptest.imoveldetails.toolbarfragment;

import gibran.com.br.zaptest.base.BaseContractPresenter;
import gibran.com.br.zaptest.base.BaseContractView;

public interface ImovelDetailsToolbarContract {

    interface ContractView extends BaseContractView<Presenter> {

        void showLoading(boolean show);

        boolean isActive();
    }

    interface Presenter extends BaseContractPresenter {
    }
}
