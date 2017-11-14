package gibran.com.br.zapservice.imovel;

import java.util.ArrayList;

import gibran.com.br.zapservice.model.Imovel;
import io.reactivex.Observable;

/**
 * Created by gibranlyra on 13/09/17.
 */

public interface ImovelDataSource {
    Observable<ArrayList<Imovel>> getImoveis();

    Observable<Imovel> getImovel(int imovelId);
}
