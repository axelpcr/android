/**************************************************************************
 * ViewComponentUtilsBase.java, demactor Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Apr 1, 2016
 *
 **************************************************************************/
package com.jeanlui.demactor.test.utils.base;


import junit.framework.Assert;
import com.jeanlui.demactor.entity.ViewComponent;



import com.jeanlui.demactor.test.utils.TestUtils;

import com.jeanlui.demactor.entity.ViewComponent.Choice;

public abstract class ViewComponentUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static ViewComponent generateRandom(android.content.Context ctx){
        ViewComponent viewComponent = new ViewComponent();

        viewComponent.setId(TestUtils.generateRandomInt(0,100) + 1);
        viewComponent.setStringField("stringField_"+TestUtils.generateRandomString(10));
        viewComponent.setText("text_"+TestUtils.generateRandomString(10));
        viewComponent.setDateTime(TestUtils.generateRandomDateTime());
        viewComponent.setDate(TestUtils.generateRandomDate());
        viewComponent.setTime(TestUtils.generateRandomTime());
        viewComponent.setLogin("login_"+TestUtils.generateRandomString(10));
        viewComponent.setPassword("password_"+TestUtils.generateRandomString(10));
        viewComponent.setEmail("email_"+TestUtils.generateRandomString(10));
        viewComponent.setPhone("phone_"+TestUtils.generateRandomString(10));
        viewComponent.setCity("city_"+TestUtils.generateRandomString(10));
        viewComponent.setZipCode(TestUtils.generateRandomInt(0,100));
        viewComponent.setCountry("country_"+TestUtils.generateRandomString(10));
        viewComponent.setByteField(TestUtils.generateRandomByte());
        viewComponent.setCharField(TestUtils.generateRandomChar());
        viewComponent.setShortField(TestUtils.generateRandomShort());
        viewComponent.setCharacter(TestUtils.generateRandomChar());
        viewComponent.setChoice(Choice.values()[TestUtils.generateRandomInt(0,Choice.values().length)]);
        viewComponent.setBooleanObject(TestUtils.generateRandomBool());

        return viewComponent;
    }

    public static boolean equals(ViewComponent viewComponent1,
            ViewComponent viewComponent2){
        return equals(viewComponent1, viewComponent2, true);
    }
    
    public static boolean equals(ViewComponent viewComponent1,
            ViewComponent viewComponent2,
            boolean checkRecursiveId){
        boolean ret = true;
        Assert.assertNotNull(viewComponent1);
        Assert.assertNotNull(viewComponent2);
        if (viewComponent1!=null && viewComponent2 !=null){
            Assert.assertEquals(viewComponent1.getId(), viewComponent2.getId());
            Assert.assertEquals(viewComponent1.getStringField(), viewComponent2.getStringField());
            Assert.assertEquals(viewComponent1.getText(), viewComponent2.getText());
            Assert.assertEquals(viewComponent1.getDateTime(), viewComponent2.getDateTime());
            Assert.assertEquals(viewComponent1.getDate(), viewComponent2.getDate());
            Assert.assertEquals(viewComponent1.getTime(), viewComponent2.getTime());
            Assert.assertEquals(viewComponent1.getLogin(), viewComponent2.getLogin());
            Assert.assertEquals(viewComponent1.getPassword(), viewComponent2.getPassword());
            Assert.assertEquals(viewComponent1.getEmail(), viewComponent2.getEmail());
            Assert.assertEquals(viewComponent1.getPhone(), viewComponent2.getPhone());
            Assert.assertEquals(viewComponent1.getCity(), viewComponent2.getCity());
            Assert.assertEquals(viewComponent1.getZipCode(), viewComponent2.getZipCode());
            Assert.assertEquals(viewComponent1.getCountry(), viewComponent2.getCountry());
            //TODO : Manage field type : byte / byte
            //TODO : Manage field type : char / char
            //TODO : Manage field type : short / short
            //TODO : Manage field type : char / char
            //TODO : Manage field type : enum / enum
            Assert.assertEquals(viewComponent1.isBooleanObject(), viewComponent2.isBooleanObject());
        }

        return ret;
    }
}

