package com.yc.bilibili.service;

import com.yc.bilibili.dao.FollowingGroupDao;
import com.yc.bilibili.daomin.FollowingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowingGroupService {

    @Autowired
    private FollowingGroupDao followingGroupDao;

    public FollowingGroup getByType(String type){
        return followingGroupDao.getByType(type);
    }

    public FollowingGroup getById(Long id){
        return followingGroupDao.getById(id);
    }
}
