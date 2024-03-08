package hello.itemservice.web.member;

import hello.itemservice.domain.member.Member;
import hello.itemservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.*;

/**
 * 동시성 문제가 고려되어 있지 않음. 실무에서는 ConcurruentHashMap, AtomicLong 사용 고려
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String addForm(@ModelAttribute Member member){
        return "member/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Member member, BindingResult result){
        if (result.hasErrors()){
            return "member/addMemberFom";
        }
        memberRepository.save(member);
        return "redirect:/";
    }


}
