package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.*;

public class Aluno {
	private int id;
	private String nome;
	private float nota;
	private int semestre;
	private LocalDateTime dataMatricula;
	private LocalDate dataConclusao;
	
	public Aluno(){
		id = -1;
		nome = "";
		nota = 0.00F;
		semestre = 0;
		dataMatricula = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		dataConclusao = LocalDate.now().plusYears(5);
	}
	
	public Aluno(int id, String nome, float nota, int semestre, LocalDateTime matricula, LocalDate c) {
		setId(id);
		setNome(nome);
		setNota(nota);
		setSemestre(semestre);
		setDataMatricula(matricula);
		setDataConclusao(c);
	}
	
	public int getID() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public float getNota() {
		return nota;
	}
	
	public void setNota(float nota) {
		this.nota = nota;
	}
	
	public int getSemestre() {
		return semestre;
	}
	
	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}
	
	public LocalDate getDataConclusao() {
		return dataConclusao;
	}
	
	public LocalDateTime getDataMatricula() {
		return dataMatricula;
	}
	
	public void setDataMatricula(LocalDateTime dataMatricula) {
		LocalDateTime agora = LocalDateTime.now();
		
		if(agora.compareTo(dataMatricula) >= 0)
			this.dataMatricula = dataMatricula;
	}
	
	public void setDataConclusao(LocalDate dataConclusao) {
		
		if(getDataMatricula().isBefore(dataConclusao.atStartOfDay()))
			this.dataConclusao = dataConclusao;
	}
	public boolean emValidade() {
		return LocalDateTime.now().isBefore(this.getDataConclusao().atTime(23, 59));
	}
	
	@Override
	public String toString() {
		return "Aluno: " + nome + "	Nota: R$" + nota + "	Semestre:" + semestre + "	Matricula" + dataMatricula + "	Data de Conclusao:" + dataConclusao;
	}
	
	@Override
	public boolean equals(Object obj) {
		return(this.getID() == ((Aluno) obj).getID());
	}
}
