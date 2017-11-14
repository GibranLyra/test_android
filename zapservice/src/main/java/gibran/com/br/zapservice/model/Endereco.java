package gibran.com.br.zapservice.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Endereco implements Parcelable {
    private String numero;
    private String zona;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.numero);
        dest.writeString(this.zona);
        dest.writeString(this.bairro);
        dest.writeString(this.cep);
        dest.writeString(this.cidade);
        dest.writeString(this.estado);
    }

    public Endereco() {
    }

    protected Endereco(Parcel in) {
        this.numero = in.readString();
        this.zona = in.readString();
        this.bairro = in.readString();
        this.cep = in.readString();
        this.cidade = in.readString();
        this.estado = in.readString();
    }

    public static final Creator<Endereco> CREATOR = new Creator<Endereco>() {
        @Override
        public Endereco createFromParcel(Parcel source) {
            return new Endereco(source);
        }

        @Override
        public Endereco[] newArray(int size) {
            return new Endereco[size];
        }
    };
}
