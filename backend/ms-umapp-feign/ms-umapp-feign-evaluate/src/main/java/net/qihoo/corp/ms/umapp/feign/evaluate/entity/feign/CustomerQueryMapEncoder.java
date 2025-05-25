package net.qihoo.corp.ms.umapp.feign.evaluate.entity.feign;

import feign.Param;
import feign.QueryMapEncoder;
import feign.codec.EncodeException;
import lombok.NoArgsConstructor;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 自定义feign请求参数编码器
 *
 * @author LBin
 * @date 2024-01-18 16:31:22
 */
@NoArgsConstructor
public class CustomerQueryMapEncoder implements QueryMapEncoder {

    private final Map<Class<?>, CustomerQueryMapEncoder.ObjectParamMetadata> classToMetadata = new HashMap<>();

    @Override
    public Map<String, Object> encode(Object object) {
        try {
            CustomerQueryMapEncoder.ObjectParamMetadata metadata = this.getMetadata(object.getClass());
            Map<String, Object> propertyNameToValue = new HashMap<>();
            for (PropertyDescriptor pd : metadata.objectProperties) {
                Method method = pd.getReadMethod();
                Object value = method.invoke(object);
                Param alias = method.getAnnotation(Param.class);
                String name = alias != null ? alias.value() : pd.getName();
                propertyNameToValue.put(name, value);
            }

            return propertyNameToValue;
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException var10) {
            throw new EncodeException("Failure encoding object into query map", var10);
        }
    }

    private CustomerQueryMapEncoder.ObjectParamMetadata getMetadata(Class<?> objectType) throws IntrospectionException {
        CustomerQueryMapEncoder.ObjectParamMetadata metadata = this.classToMetadata.get(objectType);
        if (metadata == null) {
            metadata = CustomerQueryMapEncoder.ObjectParamMetadata.parseObjectType(objectType);
            this.classToMetadata.put(objectType, metadata);
        }

        return metadata;
    }

    private static class ObjectParamMetadata {
        private final List<PropertyDescriptor> objectProperties;

        private ObjectParamMetadata(List<PropertyDescriptor> objectProperties) {
            this.objectProperties = Collections.unmodifiableList(objectProperties);
        }

        private static CustomerQueryMapEncoder.ObjectParamMetadata parseObjectType(Class<?> type) throws IntrospectionException {
            List<PropertyDescriptor> properties = new ArrayList<>();
            PropertyDescriptor[] var2 = Introspector.getBeanInfo(type).getPropertyDescriptors();
            for (PropertyDescriptor pd : var2) {
                boolean isGetterMethod = pd.getReadMethod() != null && !"class".equals(pd.getName());
                if (isGetterMethod) {
                    properties.add(pd);
                }
            }
            return new CustomerQueryMapEncoder.ObjectParamMetadata(properties);
        }
    }
}
