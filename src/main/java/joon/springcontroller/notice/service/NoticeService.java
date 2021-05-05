package joon.springcontroller.notice.service;

import joon.springcontroller.notice.entity.Notice;
import joon.springcontroller.notice.model.NoticeInput;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {
    final NoticeRepository noticeRepository;

    @Transactional
    public Notice save(NoticeInput noticeInput) {
        Notice notice=Notice.create(noticeInput);
        return  noticeRepository.save(notice);
    }
}
