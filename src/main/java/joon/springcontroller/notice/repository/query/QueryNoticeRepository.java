package joon.springcontroller.notice.repository.query;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import joon.springcontroller.user.model.UserLogCount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.jpa.JPAExpressions.select;
import static joon.springcontroller.notice.entity.QNotice.notice;
import static joon.springcontroller.notice.entity.QNoticeLike.noticeLike;
import static joon.springcontroller.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class QueryNoticeRepository {
    final JPAQueryFactory jpaQueryFactory;
    public List<UserLogCount> findUserLogCount() {

        List<Tuple> result = jpaQueryFactory.select(user.id, user.email, user.username
                , jpaQueryFactory.select(notice.count()).from(notice).where(notice.user.eq(user))
                , jpaQueryFactory.select(noticeLike.count()).from(noticeLike).where(noticeLike.user.eq(user)))
                .from(user)
                .fetch();
        return result.stream().map(objects -> new UserLogCount(
                objects.get(user.id),
                objects.get(user.email),
                objects.get(user.username),
                objects.get(3, Long.class),
                objects.get(4, Long.class))
        ).collect(Collectors.toList());
    }

    public List<UserLogCount> findUserLikeBest() {
        List<Tuple> result = jpaQueryFactory.select(user.id, user.email, user.username
                , ExpressionUtils.as(select(notice.count()).from(notice).where(notice.user.eq(user)),"noticeCount")
                , select(noticeLike.count().as("noticeLike")).from(noticeLike).where(noticeLike.user.eq(user)))
                .from(user)//TODO Inline View 사용 시 ORDER BY 사용법 찾기
                .limit(10)
                .fetch();
        System.out.println(result.get(0).get(notice.count()));

        return result.stream().map(objects -> new UserLogCount(
                objects.get(user.id),
                objects.get(user.email),
                objects.get(user.username),
                objects.get(3, Long.class),
                objects.get(4, Long.class))
        ).collect(Collectors.toList());
    }
}
