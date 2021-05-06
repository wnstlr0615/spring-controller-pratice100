package joon.springcontroller.notice.service;

import joon.springcontroller.notice.entity.Notice;
import joon.springcontroller.notice.exception.AlreadyDeletedException;
import joon.springcontroller.notice.exception.NotFoundNoticeException;
import joon.springcontroller.notice.model.NoticeDeleteInput;
import joon.springcontroller.notice.model.NoticeInput;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.AlreadyBoundException;
import java.util.List;
import java.util.Optional;

/**
JPA 더티체킹 사용

 * */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {
    final NoticeRepository noticeRepository;

    @Transactional
    public Notice saveNoticeInput(NoticeInput noticeInput) {
        Notice notice=Notice.create(noticeInput);
        return  noticeRepository.save(notice);
    }

    /**

     * */
    @Transactional
    public Notice save(Notice notice) {
        return  noticeRepository.save(notice);
    }
    /**
        게시글 보기
     * */
    public Notice findNotice(Long noticeId) {
        Optional<Notice> findNotice = noticeRepository.findById(noticeId);
        if(findNotice.isEmpty()){
            throw new NotFoundNoticeException("해당 공지를 찾을 수 없습니다.");
        }
        findNotice.get().addView();
        return findNotice.get();
    }
    /**
        사용자용 게시글 읽기
     * */
    @Transactional
    public Notice readNotice(Long noticeId) {
        Optional<Notice> findNotice = noticeRepository.findById(noticeId);
        if(findNotice.isEmpty()){
            throw new NotFoundNoticeException("해당 공지를 찾을 수 없습니다.");
        }
        findNotice.get().addView();
        return findNotice.get();
    }
    /**
     공지사항 내용 수정
     * */
    @Transactional
    public Notice noticeUpdate(Long noticeId, NoticeInput noticeInput) {
        Optional<Notice> findNotice = noticeRepository.findById(noticeId);
        if(findNotice.isEmpty()) {
           throw new NotFoundNoticeException("해당 공지를 찾을 수 없습니다.");
        }
        Notice notice = findNotice.get();
        notice.updateTitleAndContent(noticeInput);
        return notice;
    }

    public void deleteNotice(Long noticeId) {
        try {
            noticeRepository.deleteById(noticeId);
        }catch (IllegalArgumentException | EmptyResultDataAccessException e){
            throw new NotFoundNoticeException("해당 공지를 찾을 수 없습니다.");
        }
    }
    @Transactional
    public void deleteNoticeFlag(Long noticeId) {
        Notice findNotice = noticeRepository.findById(noticeId).orElseThrow(
                () -> new NotFoundNoticeException("해당 공지를 찾을 수 없습니다."));
        if(findNotice.isDeleted()){
            throw new AlreadyDeletedException("이미 삭제된 글입니다.");
        }
        findNotice.deleted();
    }
    @Transactional
    public void deleteNoticeList(NoticeDeleteInput noticeDeleteInput) {
        List<Notice> notices = noticeRepository.findByIdIn(noticeDeleteInput.getDeleteIdList()).orElseThrow(
                () -> new NotFoundNoticeException("해당 공지를 찾을 수 없습니다.")
        );
        if(notices.size()==0){
            throw new NotFoundNoticeException("해당 공지를 찾을 수 없습니다.");
        }
        for (Notice notice : notices) {
            if(notice.isDeleted()){
                throw new AlreadyDeletedException("이미 삭제된 글입니다.");
            }
            notice.deleted();
        }
    }

    public void deleteAll() {
        noticeRepository.deleteAll();
    }

    public List<Notice> findAllNotice() {
        return noticeRepository.findAll();
    }
}
