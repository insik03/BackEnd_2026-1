package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;
    private final ArticleService articleService;

    public MemberController(MemberRepository memberRepository, ArticleService articleService) {
        this.memberRepository = memberRepository;
        this.articleService = articleService;
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다."));
    }

    @PostMapping
    public String createMember(@Valid @RequestBody Member member) {
        memberRepository.save(member);
        return "회원 생성 성공 (ID: " + member.getId() + ")";
    }

    @PutMapping("/{id}")
    @Transactional
    public String updateMember(@PathVariable Long id, @Valid @RequestBody Member updatedMember) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다."));

        boolean emailExists = memberRepository.findAll().stream()
                .anyMatch(m -> !m.getId().equals(id) && m.getEmail().equals(updatedMember.getEmail()));
        if (emailExists) {
            throw new Exception409("이미 존재하는 이메일입니다.");
        }

        member.setName(updatedMember.getName());
        member.setEmail(updatedMember.getEmail());
        member.setPassword(updatedMember.getPassword());

        return "회원 정보 수정 성공";
    }

    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 사용자를 찾을 수 없습니다."));

        boolean hasArticles = articleService.getAllArticles().stream()
                .anyMatch(article -> id.equals(article.getAuthorId()));
        if (hasArticles) {
            throw new Exception400("작성한 게시물이 있는 사용자는 삭제할 수 없습니다.");
        }

        memberRepository.deleteById(id);
        return "회원 삭제 완료";
    }
}