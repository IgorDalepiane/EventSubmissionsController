package submissao.categorias;

import submissao.SubmissaoCientifica;

import javax.persistence.PrimaryKeyJoinColumn;

import javax.persistence.Entity;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Monografia extends SubmissaoCientifica {
    private Tipo tipo;
    private String orientador;
    private String curso;
    private int ano;
    private int nPaginas;
    private String resumo;
    private String _abstract;

    public Monografia() {
        super(1, 1, 4);
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getOrientador() {
        return orientador;
    }

    public void setOrientador(String orientador) {
        this.orientador = orientador;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
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
        StringBuilder result = new StringBuilder(super.toString());
        result.append("\n\nTipo: ")
                .append(tipo)
                .append("\n\nOrientador: ")
                .append(orientador)
                .append("\n\nCurso: ")
                .append(curso)
                .append("\n\nAno: ")
                .append(ano)
                .append("\n\nNº Páginas: ")
                .append(nPaginas)
                .append("\n\nResumo: ")
                .append(resumo)
                .append("\n\nAbstract: ")
                .append(_abstract);

        return result.toString();
    }
}
