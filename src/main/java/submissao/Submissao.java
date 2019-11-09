package submissao;

public abstract class Submissao {
    private String tituloSubmissao;
    private Situacao situacaoSubmissao;
    private String[] autoresSubmissao;
    private int maxAut;

    public String getTituloSubmissao() {
        return tituloSubmissao;
    }

    public void setTituloSubmissao(String tituloSubmissao) {
        this.tituloSubmissao = tituloSubmissao;
    }

    public Situacao getSituacaoSubmissao() {
        return situacaoSubmissao;
    }

    public void setSituacaoSubmissao(Situacao situacaoSubmissao) {
        this.situacaoSubmissao = situacaoSubmissao;
    }

    public String[] getAutoresSubmissao() {
        return autoresSubmissao;
    }

    public void setAutoresSubmissao(String[] autoresSubmissao) {
        this.autoresSubmissao = autoresSubmissao;
    }

    public int getMaxAut() {
        return maxAut;
    }

    public void setMaxAut(int maxAut) {
        this.maxAut = maxAut;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Título: ")
                .append(tituloSubmissao)
                .append(" Situação: ")
                .append(situacaoSubmissao)
                .append(" Autores: ");
        for (String a :
                autoresSubmissao) {
            //é o último
            if (a.equals(autoresSubmissao[autoresSubmissao.length - 1]))
                result.append(a);
            else {
                result.append(a).append(", ");
            }
        }
        result.append(" Max autores: ")
                .append(maxAut);

        return result.toString();
    }
}
