package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.AlunoDAO;
import model.Aluno;
import spark.Request;
import spark.Response;


public class AlunoService {

	private AlunoDAO alunoDAO = new AlunoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_NOTA = 3;
	
	
	public AlunoService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Aluno(), FORM_ORDERBY_NOME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Aluno(), orderBy);
	}

	
	public void makeForm(int tipo, Aluno aluno, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umAluno = "";
		if(tipo != FORM_INSERT) {
			umAluno += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/produto/list/1\">Novo Produto</a></b></font></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t</table>";
			umAluno += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/aluno/";
			String name, nome, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Aluno";
				nome = "";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + aluno.getID();
				name = "Atualizar Aluno (ID " + aluno.getID() + ")";
				nome = aluno.getNome();
				buttonLabel = "Atualizar";
			}
			umAluno += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umAluno += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""+ nome +"\"></td>";
			umAluno += "\t\t\t<td>Nota: <input class=\"input--register\" type=\"text\" name=\"preco\" value=\""+ aluno.getNota() +"\"></td>";
			umAluno += "\t\t\t<td>Semestre: <input class=\"input--register\" type=\"text\" name=\"quantidade\" value=\""+ aluno.getSemestre() +"\"></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td>&nbsp;Data de matricula: <input class=\"input--register\" type=\"text\" name=\"dataFabricacao\" value=\""+ aluno.getDataMatricula().toString() + "\"></td>";
			umAluno += "\t\t\t<td>Data de conclusao: <input class=\"input--register\" type=\"text\" name=\"dataValidade\" value=\""+ aluno.getDataConclusao().toString() + "\"></td>";
			umAluno += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t</table>";
			umAluno += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umAluno += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Produto (ID " + aluno.getID() + ")</b></font></td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td>&nbsp;Nome: "+ aluno.getNome() +"</td>";
			umAluno += "\t\t\t<td>Nota: "+ aluno.getNota() +"</td>";
			umAluno += "\t\t\t<td>Semestre: "+ aluno.getSemestre() +"</td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t\t<tr>";
			umAluno += "\t\t\t<td>&nbsp;Data de matricula: "+ aluno.getDataMatricula().toString() + "</td>";
			umAluno += "\t\t\t<td>Data de conclusao: "+ aluno.getDataConclusao().toString() + "</td>";
			umAluno += "\t\t\t<td>&nbsp;</td>";
			umAluno += "\t\t</tr>";
			umAluno += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-ALUNO>", umAluno);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Produtos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/aluno/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/aluno/list/" + FORM_ORDERBY_NOME + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/aluno/list/" + FORM_ORDERBY_NOTA + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Aluno> alunos;
		if (orderBy == FORM_ORDERBY_ID) {                 	alunos = alunoDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_NOME) {		    alunos = alunoDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_NOTA) {			alunos = alunoDAO.getOrderByNota();
		} else {											alunos = alunoDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Aluno p : alunos) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getNota() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteProduto('" + p.getID() + "', '" + p.getNome() + "', '" + p.getNota() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PRODUTO>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		float nota = Float.parseFloat(request.queryParams("nota"));
		int semestre = Integer.parseInt(request.queryParams("semestre"));
		LocalDateTime dataMatricula = LocalDateTime.parse(request.queryParams("dataMatricula"));
		LocalDate dataConclusao = LocalDate.parse(request.queryParams("dataConclusao"));
		
		String resp = "";
		
		Aluno aluno = new Aluno(-1, nome, nota, semestre, dataMatricula, dataConclusao);
		
		if(alunoDAO.insert(aluno) == true) {
            resp = "Aluno (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Aluno (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Aluno aluno = (Aluno) alunoDAO.get(id);
		
		if (aluno != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, aluno, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Aluno " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Produto produto = (Produto) alunoDAO.get(id);
		
		if (produto != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, produto, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Aluno " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Aluno aluno = alunoDAO.get(id);
        String resp = "";       

        if (aluno != null) {
        	aluno.setNome(request.queryParams("nome"));
        	aluno.setNota(Float.parseFloat(request.queryParams("nota")));
        	aluno.setSemestre(Integer.parseInt(request.queryParams("semestre")));
        	aluno.setDataMatricula(LocalDateTime.parse(request.queryParams("dataMatricula")));
        	aluno.setDataConclusao(LocalDate.parse(request.queryParams("dataConclusao")));
        	alunoDAO.update(aluno);
        	response.status(200); // success
            resp = "Aluno (ID " + aluno.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Aluno (ID \" + aluno.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Aluno aluno = alunoDAO.get(id);
        String resp = "";       

        if (aluno != null) {
            alunoDAO.delete(id);
            response.status(200); // success
            resp = "Aluno (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Aluno (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}