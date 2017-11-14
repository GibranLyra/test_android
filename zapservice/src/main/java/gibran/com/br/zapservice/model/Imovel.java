package gibran.com.br.zapservice.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Imovel implements Parcelable {
    private String dataAtualizacao;
    private String informacoesComplementares;
    private String tipoImovel;
    private ArrayList<String> fotos;
    private int areaTotal;
    private int areaUtil;
    private int vagas;
    private String subtipoImovel;
    private int precoCondominio;
    private int codImovel;
    private int precoVenda;
    private Endereco endereco;
    private String subTipoOferta;
    private String observacao;
    private ArrayList<String> caracteristicas;
    private int dormitorios;
    private Cliente cliente;
    private ArrayList<String> caracteristicasComum;
    private int suites;
    private String urlImagem;

    public String getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getInformacoesComplementares() {
        return informacoesComplementares;
    }

    public void setInformacoesComplementares(String informacoesComplementares) {
        this.informacoesComplementares = informacoesComplementares;
    }

    public String getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public ArrayList<String> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<String> fotos) {
        this.fotos = fotos;
    }

    public int getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(int areaTotal) {
        this.areaTotal = areaTotal;
    }

    public int getAreaUtil() {
        return areaUtil;
    }

    public void setAreaUtil(int areaUtil) {
        this.areaUtil = areaUtil;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public String getSubtipoImovel() {
        return subtipoImovel;
    }

    public void setSubtipoImovel(String subtipoImovel) {
        this.subtipoImovel = subtipoImovel;
    }

    public int getPrecoCondominio() {
        return precoCondominio;
    }

    public void setPrecoCondominio(int precoCondominio) {
        this.precoCondominio = precoCondominio;
    }

    public int getCodImovel() {
        return codImovel;
    }

    public void setCodImovel(int codImovel) {
        this.codImovel = codImovel;
    }

    public int getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(int precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getSubTipoOferta() {
        return subTipoOferta;
    }

    public void setSubTipoOferta(String subTipoOferta) {
        this.subTipoOferta = subTipoOferta;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public ArrayList<String> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(ArrayList<String> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public int getDormitorios() {
        return dormitorios;
    }

    public void setDormitorios(int dormitorios) {
        this.dormitorios = dormitorios;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<String> getCaracteristicasComum() {
        return caracteristicasComum;
    }

    public void setCaracteristicasComum(ArrayList<String> caracteristicasComum) {
        this.caracteristicasComum = caracteristicasComum;
    }

    public int getSuites() {
        return suites;
    }

    public void setSuites(int suites) {
        this.suites = suites;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public Imovel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dataAtualizacao);
        dest.writeString(this.informacoesComplementares);
        dest.writeString(this.tipoImovel);
        dest.writeStringList(this.fotos);
        dest.writeInt(this.areaTotal);
        dest.writeInt(this.areaUtil);
        dest.writeInt(this.vagas);
        dest.writeString(this.subtipoImovel);
        dest.writeInt(this.precoCondominio);
        dest.writeInt(this.codImovel);
        dest.writeInt(this.precoVenda);
        dest.writeParcelable(this.endereco, flags);
        dest.writeString(this.subTipoOferta);
        dest.writeString(this.observacao);
        dest.writeStringList(this.caracteristicas);
        dest.writeInt(this.dormitorios);
        dest.writeParcelable(this.cliente, flags);
        dest.writeStringList(this.caracteristicasComum);
        dest.writeInt(this.suites);
        dest.writeString(this.urlImagem);
    }

    protected Imovel(Parcel in) {
        this.dataAtualizacao = in.readString();
        this.informacoesComplementares = in.readString();
        this.tipoImovel = in.readString();
        this.fotos = in.createStringArrayList();
        this.areaTotal = in.readInt();
        this.areaUtil = in.readInt();
        this.vagas = in.readInt();
        this.subtipoImovel = in.readString();
        this.precoCondominio = in.readInt();
        this.codImovel = in.readInt();
        this.precoVenda = in.readInt();
        this.endereco = in.readParcelable(Endereco.class.getClassLoader());
        this.subTipoOferta = in.readString();
        this.observacao = in.readString();
        this.caracteristicas = in.createStringArrayList();
        this.dormitorios = in.readInt();
        this.cliente = in.readParcelable(Cliente.class.getClassLoader());
        this.caracteristicasComum = in.createStringArrayList();
        this.suites = in.readInt();
        this.urlImagem = in.readString();
    }

    public static final Creator<Imovel> CREATOR = new Creator<Imovel>() {
        @Override
        public Imovel createFromParcel(Parcel source) {
            return new Imovel(source);
        }

        @Override
        public Imovel[] newArray(int size) {
            return new Imovel[size];
        }
    };
}
