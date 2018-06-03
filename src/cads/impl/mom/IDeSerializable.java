package cads.impl.shared;

import org.json.simple.JSONObject;

public interface IDeSerializable<T> {

	T deSerialize(JSONObject jsonObject) throws Exception;
}
