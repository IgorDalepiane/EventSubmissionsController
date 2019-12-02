package submissao.categorias;

import submissao.SubmissaoCientifica;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Resumo extends SubmissaoCientifica {
    public Resumo() {
        super(8, 8, 4);
    }

}
