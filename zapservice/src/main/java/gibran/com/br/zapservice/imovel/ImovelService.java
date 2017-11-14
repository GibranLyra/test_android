package gibran.com.br.zapservice.imovel;

import java.util.ArrayList;

import gibran.com.br.zapservice.model.BaseZapApiResponse;
import gibran.com.br.zapservice.model.BaseZapListApiResponse;
import gibran.com.br.zapservice.model.Imovel;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by gibranlyra on 13/09/17.
 */

public interface ImovelService {

    @GET("imoveis")
    Observable<BaseZapListApiResponse<ArrayList<Imovel>>> getImoveis();

    @GET("imoveis/{imovelId}")
    Observable<BaseZapApiResponse<Imovel>> getImovel(@Path("imovelId") int imovelId);

}
