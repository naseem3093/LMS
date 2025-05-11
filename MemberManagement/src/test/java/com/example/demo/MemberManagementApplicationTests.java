package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.exceptions.MemberNotFoundException;
import com.example.model.Member;
import com.example.repository.MemberRepository;
import com.example.service.MemberServiceImpl;

@SpringBootTest
class MemberManagementApplicationTests {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testRegisterMember() {
        Member member = new Member();
        when(memberRepository.save(any(Member.class))).thenAnswer(invocation -> {
            Member savedMember = invocation.getArgument(0);
            savedMember.setMembershipExpiryDate(LocalDate.now().plusYears(1));
            return savedMember;
        });

        String result = memberService.registerMember(member);

        assertEquals("Member registered successfully!", result);
        assertNotNull(member.getMembershipExpiryDate());
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void testUpdateMember() {
        Member member = new Member();
        when(memberRepository.save(member)).thenReturn(member);

        Member result = memberService.updateMember(member);

        assertEquals(member, result);
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    void testGetMemberFound() throws MemberNotFoundException {
        Member member = new Member();
        when(memberRepository.findById(1)).thenReturn(Optional.of(member));

        Member result = memberService.getMember(1);

        assertEquals(member, result);
    }

    @Test
    void testGetMemberNotFound() {
        when(memberRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class, () -> memberService.getMember(1));
    }

    @Test
    void testGetAllMembers() {
        List<Member> members = Arrays.asList(
            new Member(),
            new Member()
        );
        when(memberRepository.findAll()).thenReturn(members);

        List<Member> result = memberService.getAllMembers();

        assertEquals(2, result.size());
    }

    @Test
    void testChangeMembershipStatus() throws MemberNotFoundException {
        Member member = new Member();
        when(memberRepository.findById(1)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        String result = memberService.changeMembershipStatus(1, "Inactive");

        assertEquals("Membership status updated successfully!", result);
        assertEquals("Inactive", member.getMembershipStatus());
    }

    @Test
    void testRenewMembership() throws MemberNotFoundException {
        Member member = new Member();
        when(memberRepository.findById(1)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        String result = memberService.renewMembership(1);

        assertEquals("Membership renewed successfully!", result);
        assertEquals(LocalDate.now().plusYears(1), member.getMembershipExpiryDate());
    }

    @Test
    void testDeleteMember() {
        doNothing().when(memberRepository).deleteById(1);

        String result = memberService.deleteMember(1);

        assertEquals("Member deleted successfully!", result);
        verify(memberRepository, times(1)).deleteById(1);
    }
}
