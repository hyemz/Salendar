import Vue from 'vue'
import VueRouter from 'vue-router'
import Intro from '../views/Intro.vue'
import Login from '../views/user/Login.vue'
import Signup from '../views/user/Signup.vue'
import Board from '../views/board/Board.vue'
import BoardFree from '../views/board/BoardFree.vue'
import BoardReview from '../views/board/BoardReview.vue'
import BoardSale from '../views/board/BoardSale.vue'
import Create from '../views/board/Create.vue'
import Calendar from '../views/CalendarPage.vue'
import Detail from '../views/board/Detail.vue'
import MyPage from '../views/mypage/MyPage.vue'
import MyPost from '../views/mypage/MyPost.vue'
import WishList from '../views/mypage/WishList.vue'
import SaleList from '../views/SaleList.vue'


Vue.use(VueRouter)

const routes = [

  {
    path: '/',
    name: 'Intro',
    component: Intro,
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  // 경로 수정
  {
    path: '/board',
    name: 'Board',
    component: Board
  },
  {
    path: '/board/free',
    name: 'BoardFree',
    component: BoardFree
  },
  {
    path: '/board/review',
    name: 'BoardReview',
    component: BoardReview
  },
  {
    path: '/board/sale',
    name: 'BoardSale',
    component: BoardSale
  },
  {
    path: '/calendar',
    name: 'Calendar',
    component: Calendar
  },
  {
    path: '/board/create/:boardNo?',
    name: 'Create',
    component: Create
  },
  {
    path: '/board/detail/:boardNo',
    name: 'Detail',
    component: Detail
  },
  {
    path: '/mypage',
    name: 'MyPage',
    component: MyPage
  },
  {
    path: '/mypage/mypost',
    name: 'MyPost',
    component: MyPost
  },
  {
    path: '/mypage/wishlist',
    name: 'WishList',
    component: WishList
  },
  {
    path: '/salelist',
    name: 'SaleList',
    component: SaleList
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router