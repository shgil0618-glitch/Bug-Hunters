package project2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project2.dao.MaterialDao;
import project2.dto.MaterialDto;

@Service
public class MaterialServiceImpl implements MaterialService {
	 @Autowired  private MaterialDao dao;
	 @Autowired PasswordEncoder pwencoder;

	@Override
	public int insertMaterial(MaterialDto dto) {
		try {
			return dao.insertMaterial(dto);
		}catch(DataAccessException e) {e.printStackTrace();
		return 0;
		}
	}

	@Override
	public int updateMaterial(MaterialDto dto) {
		try {
			return dao.updateMaterial(dto);
		}catch(DataAccessException e) {
			e.printStackTrace();
			return 0;
			}
	}
	@Override
	public int deleteMaterial(MaterialDto dto) {
		try{
			return dao.deleteMaterial(dto);
		}catch (DataAccessException e) {
			e.printStackTrace();
		return 0;
		}
	}
	@Override
	public List<MaterialDto> MaterialList() {
		try{
			return dao.MaterialList();
		}catch (DataAccessException e) {
			e.printStackTrace();	
			return null;
		}
	}
	@Override
	public MaterialDto selectMaterial(MaterialDto dto) {
		try {
			return dao.selectMaterial(dto);
		}catch(DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}
