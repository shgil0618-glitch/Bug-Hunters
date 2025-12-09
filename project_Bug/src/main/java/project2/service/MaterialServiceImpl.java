package project2.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project2.dao.MaterialDao;
import project2.dto.MaterialDto;

@Service
public class MaterialServiceImpl implements MaterialService {
	 @Autowired MaterialDao dao;
	 @Autowired PasswordEncoder pwencoder;

	@Override public int insertMaterial(MaterialDto dto) {	
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
	public int deleteMaterial(int materialid) {
		try{
			return dao.deleteMaterial(materialid);
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
	public MaterialDto selectMaterial(int materialid) {
		try {
			return dao.selectMaterial(materialid);
		}catch(DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public MaterialDto selectTitle(String title) {
	try {
		return dao.selectTitle(title);
	}catch(DataAccessException e) {
		e.printStackTrace();
		return null;
	}
}

	@Override
	public int insert2Material(MultipartFile file, MaterialDto dto) {
		if(!file.isEmpty()) {  // 파일이 비어있는게 아니라면
			   String fileName   = file.getOriginalFilename(); // 원본파일이름
			   String uploadPath = "C:/file/";
			   File   img        = new File(uploadPath + fileName);  //java.io.File
			   try { 
				   file.transferTo(img); //파일올리기
				   dto.setImageurl(fileName); 
			   }catch (IOException e) { e.printStackTrace(); }
		   }
		return dao.insertMaterial(dto);
	}

	@Override
	public int update2Material(MultipartFile file, MaterialDto dto) {
		if(  !file.isEmpty() ) {  // 파일이 비어있는게 아니라면
			   String fileName   = file.getOriginalFilename(); // 원본파일이름
			   String uploadPath = "C:/file/";
			   File   img        = new File(uploadPath + fileName);  //java.io.File
			   try { 
				   file.transferTo(img); //파일올리기
				   dto.setImageurl(fileName); 
			   }catch (IOException e) { e.printStackTrace(); }
		   }
		return dao.updateMaterial(dto);
	}

	@Override
	public List<MaterialDto> select10(int pstartno) {
		HashMap<String, Object> para = new HashMap();
		int start=(pstartno-1)*10 + 1;
		para.put("start", start);
		para.put("end", start + 10 -1);
		return dao.select10(para);
	}

	@Override
	public int selectTotalCnt() {return dao.selectTotalCnt();}
}
