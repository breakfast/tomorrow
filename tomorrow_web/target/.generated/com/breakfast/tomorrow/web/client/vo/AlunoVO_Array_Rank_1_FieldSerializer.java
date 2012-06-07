package com.breakfast.tomorrow.web.client.vo;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.user.client.rpc.impl.ReflectionHelper;

@SuppressWarnings("deprecation")
public class AlunoVO_Array_Rank_1_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, com.breakfast.tomorrow.web.client.vo.AlunoVO[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.deserialize(streamReader, instance);
  }
  
  public static com.breakfast.tomorrow.web.client.vo.AlunoVO[] instantiate(SerializationStreamReader streamReader) throws SerializationException {
    int size = streamReader.readInt();
    return new com.breakfast.tomorrow.web.client.vo.AlunoVO[size];
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, com.breakfast.tomorrow.web.client.vo.AlunoVO[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return com.breakfast.tomorrow.web.client.vo.AlunoVO_Array_Rank_1_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    com.breakfast.tomorrow.web.client.vo.AlunoVO_Array_Rank_1_FieldSerializer.deserialize(reader, (com.breakfast.tomorrow.web.client.vo.AlunoVO[])object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    com.breakfast.tomorrow.web.client.vo.AlunoVO_Array_Rank_1_FieldSerializer.serialize(writer, (com.breakfast.tomorrow.web.client.vo.AlunoVO[])object);
  }
  
}
