package joon.springcontroller.user.service;

import joon.springcontroller.user.repository.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
    private final UserPointRepository userPointRepository;

}
