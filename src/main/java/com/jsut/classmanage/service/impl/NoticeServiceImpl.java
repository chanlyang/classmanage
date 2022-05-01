package com.jsut.classmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsut.classmanage.mapper.NoticeMapper;
import com.jsut.classmanage.model.Notice;
import com.jsut.classmanage.service.NoticeService;
import org.springframework.stereotype.Service;

/**
 * @className NoticeServiceImpl
 **/
@Service("noticeService")
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}
