package objectOrientationModeling;


import javax.persistence.*;

@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "MEMBER_NAME")
    private String name;

//    @Column(name = "TEAM_ID")
//    private Long teamId;
    // 테이블에 맞춰 모델링 하지 말고, 객체 지향 모델링을 한다면

    // Member - Team이 다대일 관계이므로 ManyToOne 어노테이션 사용
    @ManyToOne
    // 외래키는 JoinColumn(name = "")로 연관관계 만들어줌.
    @JoinColumn(name = "TEAM_ID")
    private Team team;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}

