package gibran.com.br.zapservice.imovel;

import java.util.ArrayList;

import gibran.com.br.zapservice.ZapApiModule;
import gibran.com.br.zapservice.model.Imovel;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by gibran.lyra on 13/09/2017.
 */
public class ImovelApi implements ImovelDataSource {
    private static ImovelApi instance;
    private final ImovelService imovelService;

    private ImovelApi() {
        Retrofit retrofit = ZapApiModule.getRetrofit();
        imovelService = retrofit.create(ImovelService.class);
    }

    public static ImovelApi getInstance() {
        if (instance == null) {
            instance = new ImovelApi();
        }
        return instance;
    }

    public static void renewInstance() {
        instance = new ImovelApi();
    }


    public Observable<ArrayList<Imovel>> getImoveis() {
        return imovelService.getImoveis()
                .doOnError(e -> Timber.e(e, "getImoveis: %s", e.getMessage()))
                .map(imoveis -> imoveis.getData());
    }

    @Override
    public Observable<Imovel> getImovel(int imovelId) {
        return imovelService.getImovel(imovelId)
                .doOnError(e -> Timber.e(e, "getImovel: %s", e.getMessage()))
                .map(imovel -> imovel.getData());
    }
}