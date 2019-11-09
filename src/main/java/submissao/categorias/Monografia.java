package submissao.categorias;

import submissao.SubmissaoCientifica;

import java.util.Date;

public class Monografia extends SubmissaoCientifica {
    private Tipo tipo;
    private String orientador;
    private String curso;
    private Date ano;
    private int nPaginas;
    private String resumo;
    private String _abstract;


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append(" Tipo: ")
                .append(tipo)
                .append(" Orientador: ")
                .append(orientador)
                .append(" Curso: ")
                .append(curso)
                .append(" Ano: ")
                .append(ano)
                .append(" Nº Páginas: ")
                .append(nPaginas)
                .append(" Resumo: ")
                .append(resumo)
                .append(" Abstract: ")
                .append(_abstract);

        return result.toString();
    }
}
