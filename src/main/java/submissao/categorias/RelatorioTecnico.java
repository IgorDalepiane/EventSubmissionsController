package submissao.categorias;

import submissao.SubmissaoCientifica;

import java.util.Date;

public class RelatorioTecnico extends SubmissaoCientifica {
    private Date ano;
    private int nPaginas;
    private String resumo;
    private String _abstract;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append(" Ano: ")
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
