import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/user/Login.vue'
import Signup from '../views/user/Signup.vue'
import Board from '../views/board/Board.vue'
import Create from '../views/board/Create.vue'
import Calendar from '../views/CalendarPage.vue'
import Detail from '../views/board/Detail.vue'

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
    path: '/calendar',
    name: 'Calendar',
    component: Calendar
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
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
