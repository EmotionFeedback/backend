package capstone.example.EF.domain.member;

import capstone.example.EF.domain.subject.SubjectCheck;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Email email;

    @Column(unique = true)
    private String password;
    private int age;
    private byte[] imgs;
    private String city;
    @Enumerated(EnumType.STRING)
    private Mbti mbti;
    private String job;

    @OneToMany(mappedBy = "member")
    List<Hobby> hobbies = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    List<SubjectCheck> checkers = new ArrayList<>();

    protected Member(){}

    public static Member createMember(Email email, String password, int age, byte[] imgs, String city,Mbti mbti, String job) {

        Member member = new Member();

        member.email = email;
        member.password = password;
        member.age = age;
        member.imgs = imgs;
        member.city = city;
        member.mbti = mbti;
        member.job = job;

        return member;
    }

    public Long updateUserInfo (Member member, int age, byte[] imgs, String city, Mbti mbti, String job){
        member.age = age;
        member.imgs = imgs;
        member.city = city;
        member.mbti = mbti;
        member.job = job;

        return member.id;
    }

}
