package gibran.com.br.zapservice.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cliente implements Parcelable {
    private String nomeFantasia;
    private int codCliente;

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nomeFantasia);
        dest.writeInt(this.codCliente);
    }

    public Cliente() {
    }

    protected Cliente(Parcel in) {
        this.nomeFantasia = in.readString();
        this.codCliente = in.readInt();
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel source) {
            return new Cliente(source);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };
}
