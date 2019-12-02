package submissao.categorias;

import submissao.SubmissaoCientifica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class RelatorioTecnico extends SubmissaoCientifica {
    private int ano;
    private int nPaginas;

    @Column(columnDefinition = "TEXT")
    private String resumo;
    @Column(name = "abstract", columnDefinition = "TEXT")
    private String _abstract;

    public RelatorioTecnico() {
        super(4, 1, 4);
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getnPaginas() {
        return nPaginas;
    }

    public void setnPaginas(int nPaginas) {
        this.nPaginas = nPaginas;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String get_abstract() {
        return _abstract;
    }

    public void set_abstract(String _abstract) {
        this._abstract = _abstract;
    }

    @Override
    public String toString() {

        return super.toString() + "\n\nAno: " +
                ano +
                "\n\nNº Páginas: " +
                nPaginas +
                "\n\nResumo: " +
                resumo +
                "\n\nAbstract: " +
                _abstract;
    }
}
