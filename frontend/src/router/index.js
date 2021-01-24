import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/user/Login.vue'
import Signup from '../views/user/Signup.vue'
import Board from '../views/board/Board.vue'
import Create from '../views/board/Create.vue'
import Detail from '../views/board/Detail.vue'
import MyPost from '../views/mypage/MyPost.vue'

Vue.use(VueRouter)

const routes = [

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
    path: '/board/create',
    name: 'Create',
    component: Create
  },
  {
    path: '/board/detail',
    name: 'Detail',
    component: Detail
  },
  {
    path: '/mypage/mypost',
    name: 'MyPost',
    component: MyPost
  },
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
