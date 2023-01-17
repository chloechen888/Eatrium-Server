/*
 * File: CIServer.java
 * ------------------------------
 * When it is finished, this program will implement a basic
 * ecommerce network management server.  Remember to update this comment!
 */

package edu.cis.Controller;

import acm.program.*;
import edu.cis.Model.*;
import edu.cis.Utils.SimpleServer;

import java.util.ArrayList;

public class CIServer extends ConsoleProgram
        implements SimpleServerListener
{



    /* The internet port to listen to requests on */
    private static final int PORT = 8000;

    /* The server object. All you need to do is start it */
    private SimpleServer server = new SimpleServer(this, PORT);
    private static final ArrayList<CISUser> users =  new ArrayList<CISUser>();
    private static final ArrayList<MenuItem> item =  new ArrayList<MenuItem>();
    private static final Menu menu =  new Menu(item,null);
//    private static final Menu menu = new Menu(item,);
//    Menu menu = new Menu;

    /**
     * Starts the server running so that when a program sends
     * a request to this server, the method requestMade is
     * called.
     */
    public void run()
    {
        println("Starting server on port " + PORT);
        server.start();
    }

    /**
     * When a request is sent to this server, this method is
     * called. It must return a String.
     *
     * @param request a Request object built by SimpleServer from an
     *                incoming network request by the client
     * @return
     */


    public String requestMade(Request request)
    {
        String cmd = request.getCommand();
        println(request.toString());

        // your code here.
        if (request.getCommand().equals(CISConstants.PING))
        {
            final String PING_MSG = "Hello, internet";

            //println is used instead of System.out.println to print to the server GUI
            println("   => " + PING_MSG);
            return PING_MSG;
        }
        if(request.getCommand().equals(CISConstants.CREATE_USER))
        {
            String s = createUser(request);
            return s;
        }
        if(request.getCommand().equals(CISConstants.ADD_MENU_ITEM))
        {
           String addMenuItem = addMenuItem(request);
           return addMenuItem;


        }
        if(request.getCommand().equals(CISConstants.PLACE_ORDER))
        {
            String placeOrder = placeOrder(request);
            return placeOrder;
        }
        if(request.getCommand().equals(CISConstants.DELETE_ORDER))
        {
            String deleteOrder = deleteOrder(request);
            return deleteOrder;
        }
        if(request.getCommand().equals((CISConstants.GET_ORDER)))
        {
            String getOrder = getOrder(request);
            return getOrder;
        }
        if(request.getCommand().equals((CISConstants.GET_USER)))
        {
            String getUser = getUser(request);
            return getUser;
        }
        if(request.getCommand().equals((CISConstants.GET_ITEM)))
        {
            String getItem =  getItem(request);
            return getItem;
        }
        if(request.getCommand().equals(CISConstants.GET_ALL_ITEMS))
        {
            String allItems = getAllItems(request);
            return allItems;
        }
        if(request.getCommand().equals(CISConstants.GET_CART))
        {
            String cart = getCart(request);
            return cart;
        }
        if(request.getCommand().equals(CISConstants.TOP_UP))
        {
            String addMoney =  topUp(request);
            return  addMoney;
        }
        if(request.getCommand().equals(CISConstants.GET_MONEY))
        {
            String totalMoney = getMoney(request);
            return totalMoney;
        }


        return "Error: Unknown command " + cmd + ".";
    }

    public String createUser(Request req)
    {
        String name = req.getParam(CISConstants.USER_NAME_PARAM);
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        String yearLevel = req.getParam(CISConstants.YEAR_LEVEL_PARAM);

        for(int i = 0; i<users.size();i++)
        {
            if(users.get(i).getUserID().equals(userID))
            {
                return CISConstants.DUP_USER_ERR;
            }
        }
        if(name != null||userID !=null||yearLevel !=null)
        {
            CISUser newUser =  new CISUser(userID,name,yearLevel,50.0);
            users.add(newUser);
            return CISConstants.SUCCESS;


        }
        return CISConstants.PARAM_MISSING_ERR;

    }
    public String addMenuItem(Request req)
    {
        String food = req.getParam(CISConstants.ITEM_NAME_PARAM);
        String description = req.getParam(CISConstants.DESC_PARAM);
        String p = req.getParam(CISConstants.PRICE_PARAM);
        double price = Double.parseDouble(p);
        String id = req.getParam(CISConstants.ITEM_ID_PARAM);
        String type = req.getParam(CISConstants.ITEM_TYPE_PARAM);
            if(food !=null||description !=null||id!=null||type!=null) {
                MenuItem newMenuItem = new MenuItem(food, description, price, id, 10, type);
                for(int i = 0; i< item.size();i++)
                {
                    if(item.get(i).getId().equals(id))
                    {
                        return CISConstants.DUP_ITEM_ERR;
                    }
                }
                item.add(newMenuItem);
                Menu menu = new Menu(item, null);
                return CISConstants.SUCCESS;
            }

            return CISConstants.PARAM_MISSING_ERR;

    }


    public String placeOrder(Request req)
    {
        boolean exist = false ;
        boolean itemE =  false;
        double userMoney;
        int x = 0;
        double price = 0;
        String orderID = req.getParam(CISConstants.ORDER_ID_PARAM);
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        String orderType = req.getParam(CISConstants.ORDER_TYPE_PARAM);
        String itemID = req.getParam(CISConstants.ITEM_ID_PARAM);
        Order newOrder = new Order(itemID,orderType,orderID);

       //if order is null
        if(orderID == null)
        {

            return CISConstants.ORDER_INVALID_ERR;
        }
        if( item.size() ==0)
        {
            return CISConstants.ORDER_INVALID_ERR;
        }

        //if user exist
        for(int i = 0; i<users.size();i++)
        {
            if(userID.equals(users.get(i).getUserID()))
            {

                exist = true;
                x = i;
            }
        }
        if(exist == false)
        {

            return CISConstants.USER_INVALID_ERR;
        }
        //if user has an order Id already in their order arraylist (duplciate order)
            for(int z = 0; z<users.get(x).getOrder().size(); z++)
            {

                if(orderID.equals(users.get(x).getOrder().get(z).getOrderID()))
                {
                    return CISConstants.DUP_ORDER_ERR;
                }
            }

//is it sold out

            int itemExist = 0;
                for(int i=0; i<item.size();i++)
                {

                    //find the item on the menu
                    if(item.get(i).getId().equals(itemID))
                    {

                        itemExist = i;
                        itemE = true;
                        price = item.get(i).getPrice();
                    }
                }
                if(itemE == false)
                {

                    return CISConstants.INVALID_MENU_ITEM_ERR;
                }
                if(item.get(itemExist).getPrice()>users.get(x).getMoney())
                {

                    return CISConstants.USER_BROKE_ERR;
                }
                if(item.get(itemExist).getAmountAvailable()<1)
                {

                    return CISConstants.SOLD_OUT_ERR;
                }
                else{
                    //following adjustments
                    int depository = item.get(itemExist).getAmountAvailable();
                    depository = depository-1;
                    item.get(itemExist).setAmountAvailable(depository);
                }


                users.get(x).getOrder().add(newOrder);
                userMoney = users.get(x).getMoney();
                users.get(x).setMoney(userMoney - price);
                return CISConstants.SUCCESS;




    }

    public String deleteOrder(Request req)
    {
        String userId = req.getParam(CISConstants.USER_ID_PARAM);
        String orderID = req.getParam(CISConstants.ORDER_ID_PARAM);
        boolean userExist = false;
        int user = 0;
        for(int i = 0; i< users.size();i++)
        {
            if(users.get(i).getUserID().equals(userId))
            {
                userExist = true;
                user = i;
            }
        }
        if(userExist = false)
        {
            return CISConstants.USER_INVALID_ERR;
        }

        for(int i = 0; i<users.get(user).getOrder().size();i++)
        {
            if(users.get(user).getOrder().get(i).getOrderID().equals(orderID))
            {
                users.get(user).getOrder().remove(i);
                return CISConstants.SUCCESS;
            }
        }

            return CISConstants.ORDER_INVALID_ERR;

    }

    public String getOrder (Request req)
    {
        int user =0;
        int order =0;
        boolean exist =false;
        String userId = req.getParam(CISConstants.USER_ID_PARAM);
        String orderID = req.getParam(CISConstants.ORDER_ID_PARAM);
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).getUserID().equals(userId))
            {
                exist = true;
                user =i;
            }
        }
        if(exist == false)
        {
            return CISConstants.USER_INVALID_ERR;
        }
        for(int i = 0; i<users.get(user).getOrder().size();i++)
        {
            if(users.get(user).getOrder().get(i).getOrderID().equals(orderID))
            {
                order = i;
                return (users.get(user).getOrder().get(order).toString());

            }
        }
        return CISConstants.ORDER_INVALID_ERR;

    }

    public String getUser(Request req)
    {
        int user = 0;
        String userId = req.getParam(CISConstants.USER_ID_PARAM);
        for(int i = 0; i<users.size();i++)
        {
            if(users.get(i).getUserID().equals(userId))
            {
                user = i;
                return(users.get(user).toString());


            }
        }
        return CISConstants.USER_INVALID_ERR;
    }

    public String getItem(Request req)
    {
        String itemId = req.getParam(CISConstants.ITEM_ID_PARAM);
        for(int i=0; i<item.size();i++)
        {
            if(item.get(i).getId().equals(itemId))
            {
                return(item.get(i).toString());
            }
        }
        return CISConstants.INVALID_MENU_ITEM_ERR;
    }

    public String getCart(Request req)
    {
        String userId =  req.getParam(CISConstants.USER_ID_PARAM);
        String ans="";
        for(int i =0; i<users.size();i++)
        {
            if(users.get(i).getUserID().equals(userId))
            {
                for(int c = 0;c<users.get(i).getOrder().size();c++)
                {
                    ans = ans + "<"+ users.get(i).getOrder().get(c).toString();

                }
            }
        }
        return ans;
    }

    public String getAllItems(Request req)
    {
        String allItem = "";

        for(int i = 0; i<item.size();i++)
        {
            allItem = allItem + "<" +item.get(i).toString();

        }
        return allItem;
    }

    public String topUp(Request req)
    {

        double money = 0;
        double topUpFinal = 0;
        String topup = req.getParam(CISConstants.TOP_UP_AMOUNT);
        money = Double.parseDouble(topup);
        String userId =  req.getParam(CISConstants.USER_ID_PARAM);

        for(int i = 0; i<users.size();i++)
        {
            if(users.get(i).getUserID().equals(userId))
            {

                topUpFinal= users.get(i).getMoney() +money;
                users.get(i).setMoney(topUpFinal);

            }
        }
        return CISConstants.SUCCESS;
    }

    public String getMoney (Request req)
    {
        double userMoney =0;
        String usersTotalMoney = "";
        String userId =  req.getParam(CISConstants.USER_ID_PARAM);
        for(int i = 0; i<users.size();i++)
        {
            if(users.get(i).getUserID().equals(userId))
            {
                userMoney = users.get(i).getMoney();
            }
        }
        usersTotalMoney = Double.toString(userMoney);
        return usersTotalMoney;

    }











    public static void main(String[] args)
    {
        CIServer f = new CIServer();
        f.start(args);
    }



}
