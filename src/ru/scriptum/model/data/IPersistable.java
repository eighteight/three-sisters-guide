package ru.scriptum.model.data;

public interface IPersistable{
	void setId(Long id);

	Long getId();

	void setName(String name);
	
	public String getName();
}
