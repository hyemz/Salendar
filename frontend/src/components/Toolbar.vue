<template>

  <v-card class="overflow-hidden" flat>
    <v-app-bar 
  
    color='transparent'
    :scroll-threshold="colorOnScroll"
    >
      <!-- <template v-slot:img="{ props }">
        <v-img
          v-bind="props"
          gradient="to top right, rgba(100,115,201,.7), rgba(25,32,72,.7)"
        ></v-img>
      </template> -->
      <v-col>
        <v-toolbar-title 
          color="#6A76AB"
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
        <v-tabs align-with-title>
          <v-tab to="/salelist" class="ml-1 text-decoration-none font-weight-medium"
            >메인페이지</v-tab
          >
          <v-tab to="/calendar" class="text-decoration-none font-weight-medium"
            >세일캘린더</v-tab
          >
          <v-tab to="/board" class="text-decoration-none font-weight-medium"
            >게시판</v-tab
          >
          <v-tab id="test" class="text-decoration-none font-weight-medium" to="/mypage/wishlist"
            >찜목록</v-tab
          >
        </v-tabs>
      </template>

      <template v-if="!isLogin">
        <v-btn text color="white" v-if="!isLogin">
          <router-link :to="{ name: 'Login' }" class="text-decoration-none">로그인</router-link>
        </v-btn>
        <v-btn text v-if="!isLogin">
          <router-link :to="{ name: 'Signup' }" class="text-decoration-none">가입하기</router-link>
        </v-btn>
      </template>
      <template v-if="isLogin">
        <v-btn text color="white" disabled class="w" v-if="isLogin">{{ nickname }}님 환영합니다.</v-btn>
        <!-- <v-btn text color="white" @click.native="logout" v-if="isLogin">
          <router-link :to="{ name: 'Login' }" color="red" class="text-decoration-none"
            >로그아웃</router-link
          >
        </v-btn> -->
      </template>
      <template v-if="isLogin">
        <v-btn icon>
          <v-icon>mdi-bell</v-icon>
        </v-btn>

        <v-menu bottom left>
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon v-bind="attrs" v-on="on">
              <v-icon>mdi-chevron-down</v-icon>
            </v-btn>
          </template>

          <v-list>
            <v-list-item>
              <v-list-item-title><router-link to =/mypage class="text-decoration-none">마이페이지</router-link></v-list-item-title>
              </v-list-item>
             <v-list-item>
           <template v-if="isLogin">
           <v-list-item-title><router-link to =/login @click.native="logout" class="text-decoration-none">로그아웃</router-link></v-list-item-title>
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
    colorOnScroll: Number
  },
  created() {
    this.token = localStorage.getItem('jwt');
    if(this.token) {
      const headers = {
          "x-auth-token": localStorage.getItem("jwt"),
      };
      const baseURL = "http://i4a301.p.ssafy.io:8080";
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
    ...mapState(['isLogin']),
  },
  watch: {
    isLogin: function() {
      this.token = localStorage.getItem('jwt');
      if(this.token) {
      const headers = {
          "x-auth-token": localStorage.getItem("jwt"),
      };
      const baseURL = "http://i4a301.p.ssafy.io:8080";
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
    }
  },
  data: () => ({
    token: localStorage.getItem('jwt'),
    items: [{ title: '마이페이지' }],
    nickname:'',
  }),
  methods: {
    logout() {
      localStorage.removeItem('jwt');
      this.$store.dispatch('login', false);
      this.$store.dispatch('updateFollowing', false)
    },
  },
};
</script>
