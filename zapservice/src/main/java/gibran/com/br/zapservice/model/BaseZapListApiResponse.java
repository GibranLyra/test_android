package gibran.com.br.zapservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gibranlyra on 13/11/17.
 */

public class BaseZapListApiResponse<T extends List> {
    @SerializedName("Imoveis")
    @Expose
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
