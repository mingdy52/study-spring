package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
         등록 V1: 요청 값으로 Member 엔티티를 직접 받는다.
         문제점
            - 엔티티에 프레젠테이션 계층을 위한 로직이 추가된다.
            - 엔티티에 API 검증을 위한 로직이 들어간다. (@NotEmpty 등등)
            - 실무에서는 회원 엔티티를 위한 API가 다양하게 만들어지는데, 한 엔티티에 각각의 API를 위한 모든 요청 요구사항을 담기는 어렵다.
            - 엔티티가 변경되면 API 스펙이 변한다.

            - 엔티티는 여러곳에서 쓰이는 만큼 바뀔 확률이 높다. 엔티티가 바뀔 때, api 스펙이 바뀌면 안된다.
         결론
            - API 요청 스펙에 맞추어 별도의 DTO를 파라미터로 받는다.
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
        등록 V2: 요청 값으로 Member 엔티티 대신에 별도의 DTO를 받는다.
            엔티티를 변경해도 api 스펙이 변경되지 않는다.
            Member는 변수 중 어떤 값이 파라미터로 넘어올지 알 수 없다.
            DTO로 받으면 api 스펙 자체가 정의되기 때문에 알기 쉽다.

         - CreateMemberRequest 를 Member 엔티티 대신에 RequestBody와 매핑한다.
         - 엔티티와 프레젠테이션 계층을 위한 로직을 분리할 수 있다.
         - 엔티티와 API 스펙을 명확하게 분리할 수 있다.
         - 엔티티가 변해도 API 스펙이 변하지 않는다.

        엔티티를 API 스펙에 노출하지 말자!
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    /**
     * 수정 API
     */
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    /**
         조회 V1: 응답 값으로 엔티티를 직접 외부에 노출한다.
                 @JsonIgnore -> 이건 정말 최악, api가 이거 하나인가! 화면에 종속적이지 마라!
         문제점
            - 엔티티에 프레젠테이션 계층을 위한 로직이 추가된다.
            - 기본적으로 엔티티의 모든 값이 노출된다.
            - 응답 스펙을 맞추기 위해 로직이 추가된다. (@JsonIgnore, 별도의 뷰 로직 등등)
            - 실무에서는 같은 엔티티에 대해 API가 용도에 따라 다양하게 만들어지는데, 한 엔티티에 각각의
         API를 위한 프레젠테이션 응답 로직을 담기는 어렵다.
             - 엔티티가 변경되면 API 스펙이 변한다.
             - 추가로 컬렉션을 직접 반환하면 항후 API 스펙을 변경하기 어렵다.(별도의 Result 클래스 생성으로 해결)
         결론
             - API 응답 스펙에 맞추어 별도의 DTO를 반환한다.
     */
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    /**
        조회 V2: 응답 값으로 엔티티가 아닌 별도의 DTO를 반환한다.
     */
    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        //엔티티 -> DTO 변환
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

}
