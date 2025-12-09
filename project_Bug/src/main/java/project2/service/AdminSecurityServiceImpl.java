package project2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project2.dao.AppUserMapper;
import project2.dto.AppUserDto;

@Service
public class AdminSecurityServiceImpl implements AdminSecurityService {

    @Autowired
    private AppUserMapper dao;

    @Override
    public List<AppUserDto> selectAll() {
        return dao.selectAll();
    }

    @Override
    public AppUserDto select(int appUserId) {
        return dao.select(appUserId);
    }

    @Override
    public int update(AppUserDto dto) {
        return dao.update(dto);
    }

    @Override
    public int delete(AppUserDto dto) {
        dao.deleteAuthByEmail(dto.getEmail()); 
        return dao.delete(dto);              
    }

    @Override
    public int updateNickname(int appUserId, String nickname) {
        return dao.updateNickname(appUserId, nickname);
    }
}
