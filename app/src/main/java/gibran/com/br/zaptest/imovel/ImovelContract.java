package gibran.com.br.zaptest.imovel;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import gibran.com.br.zapservice.model.Imovel;
import gibran.com.br.zaptest.base.BaseContractPresenter;
import gibran.com.br.zaptest.base.BaseContractView;

public interface ImovelContract {

    interface ContractView extends BaseContractView<Presenter> {

        void showImoveis(ArrayList<Imovel> imovels);

        void showImovelError();

        void showLoading(boolean show);

        boolean isActive();

        void showImovelDetailsUi(Imovel imovel, @Nullable android.view.View v);

    }

    interface Presenter extends BaseContractPresenter {
        void loadImoveis();

        void openImovelDetails(Imovel imovel, @Nullable android.view.View v);

    }
}
