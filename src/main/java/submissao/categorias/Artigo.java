package submissao.categorias;

import submissao.SubmissaoCientifica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Artigo extends SubmissaoCientifica {
    @Column(columnDefinition = "TEXT")
    private String resumo;

    @Column(name = "abstract", columnDefinition = "TEXT")
    private String _abstract;

    public Artigo() {
        super(8, 8, 4);
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
        return super.toString() +
                "\n\nResumo: " +
                resumo +
                "\n\nAbstract: " +
                _abstract;
    }
}
