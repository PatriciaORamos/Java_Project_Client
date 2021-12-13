package ca.nscc.gamefrogger.entity;

public class Player {
	
	private Long id;
	private String name;
	private Integer score;
	private boolean isPlaying;
	
	public Player() {
		this.id = 0L; this.name = ""; this.score = 0; this.isPlaying = false; 
	}
	
	public Player(String name) {
		this.id = 0L; this.name = name; this.score = 0; this.isPlaying = false; 
	}
	
	public Player(String name, int score) {
		this.id = 0L; this.name = name; this.score = score; this.isPlaying = false; 
	}
	
	public Long getId() {return id;}
	public String getName() {return name;}
	public Integer getScore() {return score;}
	public boolean isPlaying() {return isPlaying;}
	
	public void setId(Long id) {this.id = id;}
	public void setName(String name) {this.name = name;}	
	public void setScore(Integer score) {this.score = score;}
	public void setPlaying(boolean isPlaying) {this.isPlaying = isPlaying;}	
	
	
}
