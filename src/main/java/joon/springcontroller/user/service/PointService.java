package joon.springcontroller.user.service;

import joon.springcontroller.board.model.ServiceResult;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.entity.UserPoint;
import joon.springcontroller.user.model.UserPointInput;
import joon.springcontroller.user.repository.UserPointRepository;
import joon.springcontroller.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {
    private final UserPointRepository userPointRepository;
    private final UserRepository userRepository;

    @Transactional
    public ServiceResult addPoint(String email, UserPointInput userPointInput) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("해당 사용자 정보가 없습니다.");
        }
        User user = optionalUser.get();
        UserPoint userPoint = UserPoint.of(user, userPointInput);
        userPointRepository.save(userPoint);
        return ServiceResult.success();
    }
}
