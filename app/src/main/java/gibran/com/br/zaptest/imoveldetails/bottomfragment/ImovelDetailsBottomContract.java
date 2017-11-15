package gibran.com.br.zaptest.imoveldetails.bottomfragment;

import gibran.com.br.zapservice.model.Imovel;
import gibran.com.br.zaptest.base.BaseContractPresenter;
import gibran.com.br.zaptest.base.BaseContractView;

public interface ImovelDetailsBottomContract {

    interface ContractView extends BaseContractView<Presenter> {

        void showImovel(Imovel imovel);

        void showImovelError();

        void showLoading(boolean show);

        boolean isActive();
    }

    interface Presenter extends BaseContractPresenter {
        void loadImovel(int id);
    }
}
