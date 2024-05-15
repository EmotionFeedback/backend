package capstone.example.EF.domain.member;

import capstone.example.EF.domain.subject.SubjectCheck;
import capstone.example.EF.dto.member.UpdateMemberDto;
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

    private int callingPoint;

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
        member.callingPoint = 1;

        return member;
    }

    public static Member createNonePasswordMember(Email email, int age, byte[] imgs, String city,Mbti mbti, String job) {

        Member member = new Member();

        member.email = email;
        member.age = age;
        member.imgs = imgs;
        member.city = city;
        member.mbti = mbti;
        member.job = job;
        member.callingPoint = 1;

        return member;
    }

    public Member updateUserInfo (UpdateMemberDto updateMemberDto){
        this.age = updateMemberDto.getAge();
        this.imgs = updateMemberDto.getImgs();
        this.city = updateMemberDto.getCity();
        this.mbti = updateMemberDto.getMbti();
        this.job = updateMemberDto.getJob();

        return this;
    }

    public void updateCallingPoint(Integer i){
        this.callingPoint = i;
    }

}
