import Vue from 'vue'
import Router from 'vue-router'
import AppStore from '@/store/store'

// import specific function from library
import isEmpty from 'lodash/isEmpty';

Vue.use(Router)

const AppRoot = () => import('@/AppRoot')

// User module
const Login = () => import('@/views/User/Login')
const SignUp = () => import('@/views/User/SignUp')
const UserProfile = () => import('@/views/User/UserProfile')
const ForgotPassword = () => import('@/views/User/ForgotPassword')
const VerifyChangePassword = () => import('@/views/User/VerifyChangePassword')
const ChangePassword = () => import('@/views/User/ChangePassword')

// Knowledge module
const BlogList = () => import('@/views/Blog/BlogList')
const EditBlog = () => import('@/views/Blog/createBlog')
//const EditBlog = () => import('@/views/Blog/AddBlog')
const BlogDetail = () => import('@/views/Blog/BlogDetails.vue')

// Event module
const EventList = () => import('@/views/Event/EventList')
const EditEvent = () => import('@/views/Event/createEvent')
const EventDetail = () => import('@/views/Event/EventDetail.vue')
const EventRegister = () =>  import('@/views/Event/EventRegister.vue')

// Exchange module
const PostList = () => import('@/views/Exchange/PostList')
const PostDetails = () => import('@/views/Exchange/PostDetails')
const Cart = () => import('@/views/Exchange/Cart')
const CreatePost = () => import('@/views/Exchange/CreatePost')

const routes = [
  { path: '/', redirect: { name: "Login" } },
  { path: '/login', component: Login, name: "Login", beforeEnter: checkIfLoggedIn },
  { path: '/forgot-password', component: ForgotPassword, name: "ForgotPassword", beforeEnter: checkIfLoggedIn },
  { path: '/verify-change-password/:token', component: VerifyChangePassword, name: "VerifyChangePassword", beforeEnter: checkIfLoggedIn },
  { path: '/change-password/:token', component: ChangePassword, name: "ChangePassword", beforeEnter: checkIfLoggedIn },
  { path: '/signup', component: SignUp, name: "SignUp", beforeEnter: checkIfLoggedIn },
  
  { path: '/p/', component: AppRoot, name: "AppRoot", beforeEnter: requireAuth,  children: [
    { path: 'knowledge/', component: BlogList, name: "BlogList" },
    { path: 'knowledge/create/', component: EditBlog, name: "EditBlog" },
    { path: 'knowledge/:blogId/', component: BlogDetail, name: "BlogDetail" },
  
    { path: 'event/', component: EventList, name: "EventList" },
    { path: 'event/detail/', component: EventDetail, name: "EventDetail"},
    { path: 'event/reg/', component: EventRegister, name: "EventRegister"},
    { path: 'event/create/', component: EditEvent, name: "EditEvent" },
  
    { path: 'profile/', component: UserProfile, name: "Profile" },
  
    { path: 'exchange/', component: PostList, name: "PostList" },
    { path: 'exchange/cart', component: Cart, name: "Cart" },
    { path: 'exchange/create', component: CreatePost, name: "CreatePost" },
    { path: 'exchange/:postId', component: PostDetails, name: "PostDetails" },
  ]}

  // { path: '/page_not_found/', component: PageNotFound, name: "PageNotFound" },
  // { path: '*', redirect: { name: 'PageNotFound' } }
]

// vue router
const router = new Router({
    mode: 'hash', // https://router.vuejs.org/api/#mode history hash
    linkActiveClass: 'open active',
    linkExactActiveClass: 'open active',
    scrollBehavior: () => ({ y: 0 }),
    routes: routes,
})

function checkIfLoggedIn(to, from, next) {
  const authInfo = Vue.$cookies.get("authInfo")

  if (isEmpty(authInfo) || isEmpty(authInfo.id)) {
    next()
  } else {
    // redirect to blog list page
    router.push({ name: "BlogList" });
  }
}

/*
    This will check to see if the user is authenticated or not.
*/
function requireAuth (to, from, next) {
  const currentUser = AppStore.getters.getCurrentUser
  
  if (isEmpty(currentUser)) {
    const authInfo = Vue.$cookies.get("authInfo")

    if (!isEmpty(authInfo) && !isEmpty(authInfo.id)) {
      AppStore.dispatch("SET_CURRENT_USER_DETAILS", authInfo.id).then(() => {
        next()
      })
    } else {
      // redirect to login page
      router.push({ name: "Login" });
    }
  } else {
    next()
  }
}
  
// export it by default
export default router