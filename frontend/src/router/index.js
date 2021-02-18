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
import WishList from '../views/mypage/WishList.vue'
import SaleList from '../views/SaleList.vue'
import Toolbar from '@/components/Toolbar.vue'
import Footer from '@/components/Footer.vue'
import TeamIntro from '../views/TeamIntro.vue'


Vue.use(VueRouter)

const routes = [

  {
    path: '/',
    name: 'Intro',
    components: { default: Intro, header: Toolbar},
    props: {
      header: { colorOnScroll: 1000 },
    }
  },
  {
    path: '/team',
    name: 'TeamIntro',
    components: { default: TeamIntro, header: Toolbar, footer: Footer  },
    props: {
      header: { colorOnScroll: 100 },
      footer: { backgroundColor: 'black' }
    }
  },
  {
    path: '/login',
    name: 'Login',
    components: { default: Login, header: Toolbar, footer: Footer  },
    props: {
      header: { colorOnScroll: 100 },
    }
  },
  {
    path: '/signup',
    name: 'Signup',
    components: { default: Signup, header: Toolbar, footer: Footer },
    props: {
      header: { colorOnScroll: 400 },
    }
  },
  // 경로 수정
  {
    path: '/board',
    name: 'Board',
    components: { default: Board, header: Toolbar, footer: Footer },
    props: {
      header: { colorOnScroll: 100 },
      footer: { backgroundColor: 'black' }
    }
  },
  {
    path: '/board/free',
    name: 'BoardFree',
    components: { default: BoardFree, header: Toolbar, footer: Footer },
    props: {
      header: { colorOnScroll: 100 },
      footer: { backgroundColor: 'black' }
    }
  },
  {
    path: '/board/review',
    name: 'BoardReview',
    components: { default: BoardReview, header: Toolbar, footer: Footer },
    props: {
      header: { colorOnScroll: 100 },
      footer: { backgroundColor: 'black' }
    }
  },
  {
    path: '/board/sale',
    name: 'BoardSale',
    components: { default: BoardSale, header: Toolbar, footer: Footer },
    props: {
      header: { colorOnScroll: 100 },
      footer: { backgroundColor: 'black' }
    }
  },
  {
    path: '/calendar',
    name: 'Calendar',
    components: { default: Calendar, header: Toolbar, footer: Footer },
    props: {
      header: { colorOnScroll: 100 },
      footer: { backgroundColor: 'black' }
    }
  },
  {
    path: '/board/create/:boardNo?',
    name: 'Create',
    components: { default: Create, header: Toolbar, footer: Footer },
    props: {
      header: { colorOnScroll: 100 },
    }
  },
  {
    path: '/board/detail/:boardNo',
    name: 'Detail',
    components: { default: Detail, header: Toolbar, footer: Footer },
    props: {
      header: { colorOnScroll: 100 },
    }
  },
  {
    path: '/mypage',
    name: 'MyPage',
    components: { default: MyPage, header: Toolbar, footer: Footer },
    props: {
      header: { colorOnScroll: 100 },
    }
  },
  {
    path: '/mypage/wishlist',
    name: 'WishList',
    components: { default: WishList, header: Toolbar, footer: Footer },
    props: {
      header: { colorOnScroll: 100 },
    }
  },
  {
    path: '/salelist',
    name: 'SaleList',
    components: { default: SaleList, header: Toolbar, footer: Footer },
    props: {
      header: { colorOnScroll: 100},
    }
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router