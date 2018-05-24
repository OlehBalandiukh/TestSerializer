import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

public class CustomSerializer {

    private String getPropertiesFields(Object object, String name) throws IllegalAccessException {
        String result = "";
        Class c = object.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Class fieldType = field.getType();
            if (fieldType.isLocalClass()) {
                result += name + "." + getPropertiesFields(field.get(object), field.getName());
            } else
                result += name + "." + field.getName() + "=" + field.get(object) + "\n";
        }
        return result;
    }

    private String getJsonFields(Object object, String name) throws IllegalAccessException {
        String result = "\"" + name + "\":{\n";
        Class c = object.getClass();
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Class fieldType = fields[i].getType();
            if (fieldType.isLocalClass()) {
                result += getJsonFields(fields[i].get(object), fields[i].getName());
                if (i != fields.length - 1) {
                    result += "},";
                }
            } else
                result += "\t\"" + fields[i].getName() + "\":\"" + fields[i].get(object) + "\"";
            if (i != fields.length - 1) {
                result += ",\n";
            } else result += "}";
        }
        return result;
    }

    private String propertiesSerialize(Object object) throws IllegalAccessException {
        String result = "";
        Class c = object.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Class fieldType = field.getType();
            if (fieldType.isLocalClass()) {
                result += getPropertiesFields(field.get(object), field.getName());
            } else
                result += field.getName() + "=" + field.get(object) + "\n";
        }
        return result;
    }

    private String jsonSerialize(Object object) throws IllegalAccessException {
        String result = "{" + "\n";
        Class c = object.getClass();
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Class fieldType = fields[i].getType();
            if (fieldType.isLocalClass()) {
                result += getJsonFields(fields[i].get(object), fields[i].getName());
                if (i != fields.length - 1) {
                    result += ",\n";
                }
            } else {
                result += "\"" + fields[i].getName() + "\":\"" + fields[i].get(object) + "\"";
                if (i != fields.length - 1) {
                    result += ",\n";
                } else result += "\n";
            }
        }
        return result + "}";
    }

    public void serialize(Object object, SereializerType type) {
        String result = "";

        if (type == SereializerType.PROPERTIES) {
            try {
                result = propertiesSerialize(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (type == SereializerType.JSON) {
            try {
                result = jsonSerialize(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(type.name() +
                object.getClass().getSimpleName() + ".txt"))) {
            bw.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}