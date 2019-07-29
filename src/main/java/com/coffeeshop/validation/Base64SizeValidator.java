package com.coffeeshop.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Base64SizeValidator implements ConstraintValidator<Base64Size, String> {

   private int maxKbSize;

   @Override
   public void initialize(Base64Size base64Size) {
         this.maxKbSize = base64Size.maxSizeKb();
   }

   @Override
   public boolean isValid(String base64Size, ConstraintValidatorContext context) {
       if(base64Size == null){
           return true;
       }
       if(base64Size.isEmpty()){
           return true;
       }
       if (calculateBase64StringSize(base64Size) < maxKbSize){
           return true;
       }
       return false;
   }


    private Integer calculateBase64StringSize(String base64StringSize) {
        Integer res = (int) (4 * Math.ceil(base64StringSize.length() / 3));
        if (base64StringSize.endsWith("==")) {
            return res - 2;
        } else if (base64StringSize.endsWith("=")) {
            return res - 1;
        }
         return res / 1024;
        }


   }

