package gibran.com.br.zapservice.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cliente implements Parcelable {
    private String nomeFantasia;
    private int codCliente;
    private String telefone;

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Cliente() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nomeFantasia);
        dest.writeInt(this.codCliente);
        dest.writeString(this.telefone);
    }

    protected Cliente(Parcel in) {
        this.nomeFantasia = in.readString();
        this.codCliente = in.readInt();
        this.telefone = in.readString();
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
