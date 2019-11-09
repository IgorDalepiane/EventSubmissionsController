package submissao.categorias;

import org.hibernate.annotations.GenericGenerator;
import submissao.SubmissaoCientifica;

import javax.persistence.*;

@Entity
@Table( name = "Artigos" )
public class Artigo extends SubmissaoCientifica {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;
    private String resumo;

    @Column(name = "abstract")
    private String _abstract;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append(" Resumo: ")
                .append(resumo)
                .append( "Abstract: ")
                .append(_abstract);

        return result.toString();
    }
}
