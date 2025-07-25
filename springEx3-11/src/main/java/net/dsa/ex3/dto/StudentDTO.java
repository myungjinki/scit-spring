package net.dsa.ex3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.dsa.ex3.entity.StudentEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
	private int studentId;
	private String name;
	private String major;
	private String java;
	private String db;
	private String web;

	public static void convertEntity_to_DTO(StudentEntity entity, StudentDTO dto) {
		dto.setStudentId(entity.getStudentId());
		dto.setName(entity.getName());
		dto.setMajor(entity.getMajor());
		dto.setJava(entity.getJava());
		dto.setDb(entity.getDb());
		dto.setWeb(entity.getWeb());
	}
	
	public static void convertDTO_to_Entity(StudentEntity entity, StudentDTO dto) {
		entity.setStudentId(dto.getStudentId());
		entity.setName(dto.getName());
		entity.setMajor(dto.getMajor());
		entity.setJava(dto.getJava());
		entity.setDb(dto.getDb());
		entity.setWeb(dto.getWeb());
	}
}
