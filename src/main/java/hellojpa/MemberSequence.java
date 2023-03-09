package hellojpa;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",    // 매핑할 디비 시퀀스 이름
        initialValue =  1,
        // allocationSize의 기본값은 50
        allocationSize = 1
)
public class MemberSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    public MemberSequence() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
