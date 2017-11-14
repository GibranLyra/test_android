package gibran.com.br.zapservice.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cliente implements Parcelable {
    private String nomeFantasia;
    private int codCliente;
    private String telefone;
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        dest.writeString(this.email);
    }

    protected Cliente(Parcel in) {
        this.nomeFantasia = in.readString();
        this.codCliente = in.readInt();
        this.telefone = in.readString();
        this.email = in.readString();
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
