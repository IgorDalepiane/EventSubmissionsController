package submissao;


import java.util.List;

/**
 * Interface que especifica a assinatura os métodos que devem ser implementados para manipular um conjunto de submissoes. 
 * @author Aline
 */
public interface ISubmissoes {
    
    /**
     * Inclui uma submissão a lista de submissões.
     * @param submissao Submissão a ser incluída.
     * @return True se a inclusão foi bem sucedida ou False em caso contrário. 
     */
    boolean incluir(Submissao submissao);
    
    /**
     * Consulta a submissão com o título informado.
     * @param titulo Título a ser consultado.
     * @return Submissão com o título informado ou null em caso de não existir submissão com o título informado. 
     */
    Submissao consultarTitulo(String titulo);
    
    /**
     * Consulta a lista de submissões com o autor informado.
     * @param autor Autor a ser consultado.
     * @return Lista de submissões do autor informado. 
     */
    List<Submissao> consultarAutor(String autor);
    
	/**
     * Exclui a submissão com o título informado.
     * @param titulo Título da submissão a ser excluída.
     * @return True se a submissão com o título informado 
	 * for excluída e False caso nenhuma submissão com o 
	 * título infornado for encontrada.
     */
    boolean excluir(String titulo);
}
