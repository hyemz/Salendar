<template>
  <v-card class="overflow-hidden" flat>
    <v-app-bar 
    :color="bg"
    flat
    fixed
    :scroll-threshold="colorOnScroll"
   style="text-shadow:initial"
    >
      <v-col >
        <v-toolbar-title 
          color="sub"
          class="white--text text-decoration-none font-weight-medium" 
          ><router-link to="/" class="text-decoration-none font-weight-medium"
            ><v-img contain src="@/assets/logo.png" width=160></v-img></router-link
          ></v-toolbar-title
        >
      </v-col>
      <!-- <v-app-bar-nav-icon></v-app-bar-nav-icon> -->

      <v-spacer></v-spacer>
      <!-- <span id="title">야야야</span> -->
      <template>
        <v-tabs align-with-title hide-slider>
          <v-tab to="/salelist" class="ml-1 text-decoration-none font-weight-bold"
            >메인페이지</v-tab
          >
          <v-tab to="/calendar" class="text-decoration-none font-weight-bold"
            >세일캘린더</v-tab
          >
          <v-tab to="/board" class="text-decoration-none font-weight-bold"
            >게시판</v-tab
          >
          <v-tab id="test" class="text-decoration-none font-weight-bold" to="/mypage/wishlist"
            >찜목록</v-tab
          >
        </v-tabs>
      </template>

      <template v-if="!isLogin">
        <v-btn color="blue-grey darken-4" rounded elevation="0" v-if="!isLogin" style="opacity:0.7" class="mm">
          <router-link :to="{ name: 'Login' }" class="text-decoration-none white--text">로그인</router-link>
        </v-btn>
        <v-btn color="main" rounded v-if="!isLogin" elevation="0" style="opacity:0.7">
          <router-link :to="{ name: 'Signup' }" class="text-decoration-none white--text">가입하기</router-link>
        </v-btn>
      </template>
      <template v-if="isLogin">
        <v-btn text color="white" disabled id="w" v-if="isLogin">{{ nickname }}님 환영합니다 💙</v-btn>
      </template>
      <template v-if="isLogin">
         <v-tooltip bottom>
      <template v-slot:activator="{ on, attrs }">
      <router-link to="/team" class="text-decoration-none"><v-btn icon v-bind="attrs"
          v-on="on">
          <v-icon>mdi-emoticon-cool-outline</v-icon>
        </v-btn></router-link></template>
           <span>팀 소개 페이지</span>
    </v-tooltip>
        <v-menu offset-y>
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon v-bind="attrs" v-on="on">
              <v-icon>mdi-chevron-down</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item>
              <v-list-item-title><router-link to =/mypage class="text-decoration-none main--text">마이페이지</router-link></v-list-item-title>
              </v-list-item>
             <v-list-item>
           <template v-if="isLogin">
           <v-list-item-title><router-link to =/login @click.native="logout" class="text-decoration-none error--text">로그아웃</router-link></v-list-item-title>
           </template>
            </v-list-item>
          </v-list>
        </v-menu>
      </template>
    </v-app-bar>
  </v-card>
</template>

<script>
import { mapState } from 'vuex';
import axios from 'axios'

export default {
  name: 'main-header',

  props: {
    transparent: Boolean,
    colorOnScroll: Number,
  },
  mounted() {
    window.onscroll = () => {
      this.changeColor();
    };
  },

  created() {
    this.token = localStorage.getItem('jwt');
    if(this.token) {
      const headers = {
          "x-auth-token": localStorage.getItem("jwt"),
      };
        const baseURL = 'http://i4a301.p.ssafy.io:8080';
      axios
      .create({
          baseURL,
          headers,
      })
      .get('/api/user/token/mypage')
      .then((res)=>{
        this.nickname = res.data.usrNick;
        this.$store.dispatch('login', true);
      })
      .catch((err)=>{
        console.log(err)
        localStorage.removeItem('jwt');
        this.$store.dispatch('login', false);
      })
    }
    console.log(this.token);
  },
  computed: {
    ...mapState(['isLogin', 'nick']),
  },
  watch: {
    isLogin: function() {
      this.token = localStorage.getItem('jwt');
      if(this.token) {
      const headers = {
          "x-auth-token": localStorage.getItem("jwt"),
      };
       const baseURL = 'http://i4a301.p.ssafy.io:8080';
      axios
      .create({
          baseURL,
          headers,
      })
      .get('/api/user/token/mypage')
      .then((res)=>{
        this.nickname = res.data.usrNick;
        this.$store.dispatch('login', true);
      })
      .catch((err)=>{
        console.log(err)
        localStorage.removeItem('jwt');
        this.$store.dispatch('login', false);
      })
    }
    },
    nick: function() {
      this.nickname = this.nick;
    }
  },
  data: () => ({
    token: localStorage.getItem('jwt'),
    items: [{ title: '마이페이지' }],
    nickname:'',
    bg:'transparent',
  }),
  methods: {
    logout() {
      localStorage.removeItem('jwt');
      this.$store.dispatch('login', false);
      this.$store.dispatch('updateFollowing', false)
    },
    changeColor() {
      if (
        document.body.scrollTop > 100 ||
        document.documentElement.scrollTop > 100
      ) {
        this.bg = 'white';
      } else {
        this.bg = 'transparent';
      }
    },
  },
};
</script>
<style scoped>
.mm {
  margin-right: 12px;
}
#w.theme--light.v-btn.v-btn--disabled {
    color: rgb(85, 85, 85) !important;
}
</style>
