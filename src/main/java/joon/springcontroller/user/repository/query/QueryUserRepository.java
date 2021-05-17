package joon.springcontroller.user.repository.query;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.model.UserSearch;
import joon.springcontroller.user.model.UserStatus;
import joon.springcontroller.user.model.UserSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static joon.springcontroller.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
@Transactional
public class QueryUserRepository {
    //@PersistenceContext
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;
    public List<User> findUserByEmailAndUsernameAndPhone(UserSearch userSearch){
        return queryFactory.selectFrom(user)
                .where(
                        emailEq(userSearch.getEmail()),
                        usernameEq(userSearch.getUsername()),
                        phoneEq(userSearch.getPhone())
                )
                .fetch();
    }

    public UserSummary getUserStatusCount() {
        List<Tuple> result = queryFactory.select(user.status, user.status.count())
                .from(user)
                .groupBy(user.status)
                .fetch();
        long usingUserCount=0;
        long stopUserCount=0;
        for (Tuple tuple : result) {
            if(tuple.get(user.status)==UserStatus.USING){
                usingUserCount+=tuple.get(user.status.count());
            }else{
                stopUserCount+=tuple.get(user.status.count());
            }
        }
        long totalUserCount = usingUserCount + stopUserCount;
        UserSummary userSummary=new UserSummary(stopUserCount, usingUserCount, totalUserCount);
        return userSummary;
    }

    private BooleanExpression phoneEq(String phone) {
        return phone!=null? user.phone.eq(phone):null;
    }

    private BooleanExpression usernameEq(String userName) {
        return userName!=null? user.username.eq(userName):null;
    }

    private BooleanExpression emailEq(String email) {
        return email!=null ? user.email.eq(email) :null;
    }
}
