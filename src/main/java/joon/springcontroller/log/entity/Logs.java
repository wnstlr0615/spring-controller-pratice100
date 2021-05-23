package joon.springcontroller.log.entity;

import joon.springcontroller.common.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Logs extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "log_id")
    private Long id;

    private String text;
}
