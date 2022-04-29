package dao;

import model.Aluno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class AlunoDAO extends DAO {	
	public alunoDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Aluno aluno) {
		boolean status = false;
		try {
			String sql = "INSERT INTO aluno (nome, nota, semestre, datamatricula, dataconclusao) "
		               + "VALUES ('" + aluno.getNome() + "', "
		               + aluno.getPreco() + ", " + aluno.getSemestre() + ", ?, ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(aluno.getDataMaricula()));
			st.setDate(2, Date.valueOf(aluno.getDataConclusao()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Aluno get(int id) {
		Aluno aluno = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM aluno WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	aluno = new Aluno(rs.getInt("id"), rs.getString("nome"), (float)rs.getDouble("nota"), 
	                				   rs.getInt("semestre"), 
	        			               rs.getTimestamp("datamatricula").toLocalDateTime(),
	        			               rs.getDate("dataconclusao").toLocalDate());
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return aluno;
	}
	
	
	public List<Aluno> get() {
		return get("");
	}

	
	public List<Aluno> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Aluno> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Aluno> getOrderByNota() {
		return get("nota");		
	}
	
	
	private List<Aluno> get(String orderBy) {
		List<Aluno> alunos = new ArrayList<Aluno>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM aluno" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Aluno p = new Aluno(rs.getInt("id"), rs.getString("nome"), (float)rs.getDouble("nota"), 
	        			                rs.getInt("semestre"),
	        			                rs.getTimestamp("datamatricula").toLocalDateTime(),
	        			                rs.getDate("dataconclusao").toLocalDate());
	        	alunos.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return alunos;
	}
	
	
	public boolean update(Aluno aluno) {
		boolean status = false;
		try {  
			String sql = "UPDATE aluno SET Nome = '" + aluno.getNome() + "', "
					   + "nota = " + aluno.getNota() + ", " 
					   + "semestre = " + aluno.getSemestre() + ","
					   + "datamatricula = ?, " 
					   + "dataconclusao = ? WHERE id = " + aluno.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(aluno.getDataMatricula()));
			st.setDate(2, Date.valueOf(aluno.getConclusao()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM aluno WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}