package com.project.plantOne.constants;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Constants {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String USERS_URL = "/plantone/api/v1/users";
    public static final String ADD_USER = USERS_URL + "/";
    public static final String HOME_URL = USERS_URL + "/home";
    public static final String AUTHENTICATE_USER = USERS_URL + "/authenticate";
    public static final String BLOG_URL = "/plantone/api/v1/blog";
    public static final String EVENTS_URL = "/plantone/api/v1/event";
    public static final String CART_URL = "/plantone/api/v1/cart";
    public static final String POST_URL = "/plantone/api/v1/post";
    public static final String COMMENT_URL = "/plantone/api/v1/comment";
    public static final String VOTE_URL = "/plantone/api/v1/blog/upvote";
    public static final String LIKE_URL = "/plantone/api/v1/post/like";

    //JWT Token
    public static final int JWT_EXPIRATION_TIME = 60;
    public static final int JWT_EXPIRATION_TIME_FACTOR = 1000;

    //Plant type and Post type
    public static final int DEFAULT_TABLE_SIZE = 0;
    public static final String CHECK_POST_TYPE_TABLE = "Select count(1) from PostType";
    public static final String CHECK_PLANT_TYPE_TABLE = "Select count(1) from PlantType";
    public static final List<String> PLANT_TYPES = Arrays.asList("Succulents", "Tropical", "Temperates", "Sub-tropical", "Forced bulbs");
    public static final List<String> POST_TYPES = Arrays.asList("Adoption", "Selling", "Exchange");

    //Post Module
    public static final String UPDATE_BUYER_FOR_POST = "Update Post p set p.buyer.user_id = ?1,is_archive = 1 where p.postUUID = ?2";
    public static final String FIND_ACTIVE_POSTS = "from Post p where p.is_archive = 0 and p.postUUID NOT IN (select ce.post.postUUID from CartEntry ce join Cart c on ce.cart.cartUUID = c.cartUUID and c.user.user_id = :userID)";

    //Cart Module
    public static final String SEARCH_CART_BY_POST = "Select ce.post.postUUID from CartEntry ce where ce.cart.cartUUID = :id and ce.isCheckedOut = false and ce.softDelete = false";
    public static final String POST_CHECKOUT_STATUS = "Select ce.post.postUUID from CartEntry ce where ce.post.postUUID = :postId and ce.isCheckedOut = true";
    public static final String UPDATE_POST_CHECKOUT_STATUS = "Update CartEntry ce set ce.isCheckedOut = 1 where ce.post.postUUID = :postId and ce.cart.cartUUID = :cartId";
    public static final String SOFT_DELETE_POST = "Update CartEntry ce set ce.softDelete = 1 where ce.post.postUUID = :postId and ce.cart.cartUUID = :cartId";
    public static final String FIND_BY_CART_ID = "from CartEntry ce where ce.cart.cartUUID = :id and ce.isCheckedOut = true and ce.softDelete = false";
    public static final String GET_CART_ITEMS = "from CartEntry ce where ce.isCheckedOut = false and ce.softDelete = false and ce.cart.user.user_id = :userID";

    //Event
    public static final int MIN_AVAILABLE_SEATS = 0;
    public static final int EVENT_UUID_BEGIN_INDEX = 0;
    public static final int EVENT_UUID_END_INDEX = 5;
    public static final int RANDOM_EVENT_UUID_END_INDEX = 2;

    //Reset Password
    public static final String FRONTEND_RESET_PASSWORD_ROUTE = "/#/verify-change-password/";
    public static final String PASSWORD_RESET_HOME = "/plantone/api/v1/passwordReset";
    public static final String VERIFY_TOKEN = PASSWORD_RESET_HOME+"/verifyToken";
    public static final String RESET_PASSWORD = PASSWORD_RESET_HOME+"/resetPassword";
    public static final String CHANGE_PASSWORD = PASSWORD_RESET_HOME+"/changePassword";

    //Vote
    public static final String CHECK_USER_ALREADY_VOTED = "Select count(1) from Vote v where v.blog.blogUUID= :blogUUID and v.user.user_id= :userId";
    public static final String GET_VOTEID_OF_USER_AND_BLOG = "Select voteUUID from Vote v where v.blog.blogUUID= :blogUUID and v.user.user_id= :userId";

    //Likes
    public static final String CHECK_USER_ALREADY_LIKED_POST = "Select count(1) from Likes v where v.post.postUUID= :postUUID and v.user.user_id= :userId";
    public static final String GET_LIKESID_OF_USER_AND_POST = "Select likeUUID from Likes v where v.post.postUUID= :postUUID and v.user.user_id= :userId";

    public Constants() {

    }

}
