package com.joker.core.mvc.model;

import java.util.HashMap;
import java.util.Set;

/**
 * 模型类
 * @author
 * 
 */
public class Model implements Cloneable{
	
	private static Model model = new Model();

	//存储视图中需要的数据内容的map
	private HashMap<String, Object> map = new HashMap<String, Object>();

	// 存储数据
	public Object put(String key, Object value) {
		return this.map.put(key, value);
	}

	// 获得数据
	public Object get(String key) {
		return this.map.get(key);
	}

	// 移除数据
	public Object remove(String key) {
		return this.map.remove(key);
	}

	// 获得数据模型对象实例（使用clone克隆方法生成对象）
	public static Model getModelInstance() {
		return model.clone();
	}

	// key迭代器方法
	public Set<String> getMapKey() {
		return this.map == null ? null : this.map.keySet();
	}

	/**
	 * 重载超类中clone 方法（深复制）
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Model clone() {
		try {
			Model newModel = (Model) super.clone();
			newModel.map = (HashMap<String, Object>) this.map.clone();
			newModel.map.clear();
			return newModel;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return new Model();
	}

}
