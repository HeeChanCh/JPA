package objectOrientationModeling;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long teamId;

    @Column(name = "TEAM_NAME")
    private String teamName;

    // 양방향 객체 연관관계를 위해서 아래와 같이 사용 가능!!!!
    @OneToMany(mappedBy = "team") // team은 하나, member는 여럿이니까! mappedBy는 반대쪽(member)에는 team으로 join되어있다라고 알려주는 개념
    private List<Member> members = new ArrayList<>(); // 관례상 = new ArrayList<>()를 사용한다네 ?!

    public Long getTeamId() { return teamId;}

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
