/**************************************************************************
 * ClientUtilsBase.java, demactor Android
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
import com.jeanlui.demactor.entity.Client;

import com.jeanlui.demactor.entity.User;

import com.jeanlui.demactor.test.utils.TestUtils;
import com.jeanlui.demactor.test.utils.UserUtils;

public abstract class ClientUtilsBase {

    // If you have enums, you may have to override this method to generate the random enums values
    /**
     * Generate a random entity
     *
     * @return The randomly generated entity
     */
    public static Client generateRandom(android.content.Context ctx){
        Client client = new Client();
        User user = UserUtils.generateRandom(ctx);
        client.setId(user.getId());
        client.setLogin(user.getLogin());
        client.setPassword(user.getPassword());
        client.setFirstname(user.getFirstname());
        client.setLastname(user.getLastname());
        client.setCreatedAt(user.getCreatedAt());
        client.setBirthdate(user.getBirthdate());
        client.setUserGroup(user.getUserGroup());
        client.setTitle(user.getTitle());
        client.setFullName(user.getFullName());
        client.setFriends(user.getFriends());

        client.setAdress("adress_"+TestUtils.generateRandomString(10));

        return client;
    }

    public static boolean equals(Client client1,
            Client client2){
        return equals(client1, client2, true);
    }
    
    public static boolean equals(Client client1,
            Client client2,
            boolean checkRecursiveId){
        boolean ret = UserUtils.equals(client1, client2);
        Assert.assertNotNull(client1);
        Assert.assertNotNull(client2);
        if (client1!=null && client2 !=null){
            Assert.assertEquals(client1.getAdress(), client2.getAdress());
        }

        return ret;
    }
}

