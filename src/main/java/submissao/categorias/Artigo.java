package submissao.categorias;

import org.hibernate.annotations.GenericGenerator;
import submissao.SubmissaoCientifica;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Artigo extends SubmissaoCientifica {
    private String resumo;

    @Column(name = "abstract")
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
        StringBuilder result = new StringBuilder(super.toString());
        result.append("\n\nResumo: ")
                .append(resumo)
                .append("\n\nAbstract: ")
                .append(_abstract);
        return result.toString();
    }
}
