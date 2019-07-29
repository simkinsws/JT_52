package com.coffeeshop.validation;


import io.leangen.geantyref.AnnotationFormatException;
import io.leangen.geantyref.TypeFactory;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import static com.coffeeshop.validation.IMAGE_TEST_API.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class Base64SizeValidationTest {

    private Base64Size base64SizeAnnotation;
    private Base64SizeValidator validator;


    @SneakyThrows
    private Base64Size getInstanceOfAnnotation(int size) {
            Map<String,Object> annotationParams = new HashMap<>();
            annotationParams.put("maxSizeKb",size);
            base64SizeAnnotation = TypeFactory.annotation(Base64Size.class,annotationParams);
            validator = new Base64SizeValidator();
            validator.initialize(base64SizeAnnotation);
            return base64SizeAnnotation;
    }

    @Test
    public void testEmptyBase64ImageSize() throws AnnotationFormatException {
        getInstanceOfAnnotation(16);
        assertTrue(validator.isValid("", null));
    }

    @Test
    public void testValidBase64ImageSize() throws AnnotationFormatException {
        getInstanceOfAnnotation(16);
        String image = encoder(COFFE_2_15KB);
        assertTrue(validator.isValid(image,null));

    }

    @Test
    public void testNotValidBase64ImageSize() throws AnnotationFormatException {
        getInstanceOfAnnotation(14);
        String image = encoder(COFFE_2_15KB);
        assertFalse(validator.isValid(image, null));
    }

    @Test
    public void testNullBase64ImageSize() throws AnnotationFormatException {
        getInstanceOfAnnotation(16);
        assertTrue(validator.isValid(null, null));
    }

    @SneakyThrows({FileNotFoundException.class, IOException.class})
    private String encoder(String imageSrc){
        String base64 = "" ;
        InputStream inputStream = new FileInputStream(imageSrc);
            byte[] imageToBytes = IOUtils.toByteArray(inputStream);
            base64 = Base64.getEncoder().encodeToString(imageToBytes);
            return base64;
    }


}
