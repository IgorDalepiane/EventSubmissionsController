package submissao.categorias;

import submissao.SubmissaoCientifica;

public class Artigo extends SubmissaoCientifica {
    private String resumo;
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
