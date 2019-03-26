package com.heshaowei.myproj.file.repository;

import com.heshaowei.myproj.file.entity.FileBaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public interface FileBaseInfoRepository extends JpaRepository<FileBaseInfo, Long> {

    @Query("select coalesce(sum (fbi.size), 0) from FileBaseInfo fbi")
    long selectTotalSize();

    long countByCreateTimeBetween(Date start, Date end);

    Stream<FileBaseInfo> streamAllByCreateTimeBetween(Date start, Date end);

}
