import APIService from "./APIService"

let API_ENDPOINT = "http://localhost:8081/plantone/api/v1";
if (process.env.VUE_APP_API_ENDPOINT) {
    API_ENDPOINT = process.env.VUE_APP_API_ENDPOINT
}

const endPoints = {
    "users": API_ENDPOINT + "/users/",
    "passwordReset": API_ENDPOINT + "/passwordReset/",
    "blogs" : API_ENDPOINT + "/blog/",
    "events" : API_ENDPOINT + "/event/",
    "posts": API_ENDPOINT + "/post/",
    "comments": API_ENDPOINT + "/comment/",
    "cart": API_ENDPOINT + "/cart/"
}

export default {
    CancelToken: APIService.CancelToken,

    login(postData) {
        return APIService.post(endPoints["users"] + 'authenticate', postData)
    },

    forgotPassword(postData) {
        return APIService.post(endPoints["passwordReset"] + 'resetPassword', postData)
    },

    verifyChangePasswordToken(queryParams) {
        return APIService.get(endPoints["passwordReset"] + 'verifyToken', queryParams)
    },

    changePassword(postData) {
        return APIService.post(endPoints["passwordReset"] + 'changePassword', postData)
    },

    getUsers() {
        return APIService.get(endPoints["users"])
    },

    getUser(userID) {
        return APIService.get(endPoints["users"] + userID)
    },

    createUser(postData) {
        return APIService.post(endPoints["users"], postData)
    },

    updateUser(putData) {
        return APIService.put(endPoints["users"], putData)
    },

    deleteUser(userID) {
        return APIService.delete(endPoints["users"] + userID)
    },

    // -------------------------------- POSTS ----------------------------------------------
    getPlantTypes() {
        return APIService.get(endPoints["posts"] + "getPlantType")
    },

    getPostTypes() {
        return APIService.get(endPoints["posts"] + "getPostType")
    },

    getPosts(userId) {
        return APIService.get(endPoints["posts"] + "allPosts/" + userId)
    },

    getPost(postID) {
        return APIService.get(endPoints["posts"] + postID)
    },

    createPost(postData) {
        return APIService.post(endPoints["posts"] + "add", postData)
    },

    updatePost(postID, putData) {
        return APIService.put(endPoints["posts"] + postID, putData)
    },

    deletePost(postID) {
        return APIService.delete(endPoints["posts"] + postID)
    },

    likePost(postId, userId) {
        return APIService.post(endPoints["posts"] + "like/" + postId + "/" + userId)
    },

    // -------------------------------- COMMENT -------------------------------------------

    addComment(postId, postData) {
        return APIService.post(endPoints["comments"] + "add/" + postId, postData)
    },

    deleteComment(commentId) {
        return APIService.delete(endPoints["comments"] + commentId)
    },

    // -------------------------------- CART ----------------------------------------------
    getCartItems(userID) {
        return APIService.get(endPoints["cart"] + "getCartItems/" + userID)
    },

    addToCart(userID, postID) {
        return APIService.post(endPoints["cart"] + "add/" + userID + "/" + postID)
    },

    removeFromCart(cartEntryId) {
        return APIService.delete(endPoints["cart"] + "deleteCartItem/" + cartEntryId)
    },

    checkoutCart(userId) {
        return APIService.post(endPoints["cart"] + "checkout/" + userId)
    },

    // -----------------------------------Blogs--------------------------------------------
    getBlogs(userId) {
        return APIService.get(endPoints["blogs"] + "getAllBlogs/" + userId)
    },

    getBlog(blogUUID) {
        return APIService.get(endPoints["blogs"] + blogUUID)
    },

    createBlog(blogData) {
        return APIService.post(endPoints["blogs"], blogData)
    },

    upvoteBlog(blogID,userID){
            return APIService.post(endPoints["blogs"]+"upvote/"+ blogID+"/"+userID)
    },

    deleteBlog(blogID){
            return APIService.delete(endPoints["blogs"]+ blogID)
    },

    // -----------------------------------Events--------------------------------------------


    getEvents() {
        return APIService.get(endPoints["events"])
    },

    getEvent(eventID) {
        return APIService.get(endPoints["events"] + eventID)
    },

    createEvent(eventData) {
        return APIService.post(endPoints["events"] + "add", eventData)
    },

    registerEvent(eventData){
           return APIService.post(endPoints["events"]+"register/"+eventData.event.eventUUID, eventData)
    },

    deleteEvent(eventId){
        return APIService.delete(endPoints["events"]+ eventId)
    }

}