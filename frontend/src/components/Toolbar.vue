<template>
  <v-card class="overflow-hidden">
    <v-app-bar color="#6A76AB">
      <template v-slot:img="{ props }">
        <v-img
          v-bind="props"
          gradient="to top right, rgba(100,115,201,.7), rgba(25,32,72,.7)"
        ></v-img>
      </template>
      <v-col>
        <v-toolbar-title class="white--text text-decoration-none mb-2 font-weight-medium"
          >Salendar</v-toolbar-title
        >
      </v-col>
      <!-- <v-app-bar-nav-icon></v-app-bar-nav-icon> -->

      <v-spacer></v-spacer>

      <template>
        <v-tabs align-with-title>
          <v-tab
            ><router-link
              to="/login"
              class="white--text text-decoration-none mb-2 font-weight-medium"
              >메인페이지</router-link
            ></v-tab
          >
          <v-tab
            ><router-link
              :to="{ name: 'Signup' }"
              class="white--text text-decoration-none mb-2 font-weight-medium"
              >세일캘린더</router-link
            ></v-tab
          >
          <v-tab
            ><router-link
              to="/board"
              class="white--text text-decoration-none mb-2 font-weight-medium"
              >게시판</router-link
            ></v-tab
          >
          <v-tab
            ><router-link
              to="/signup"
              class="white--text text-decoration-none mb-2 font-weight-medium"
              >찜목록</router-link
            ></v-tab
          >
        </v-tabs>
      </template>

      <v-btn text color="white">
        <router-link :to="{ name: 'Login' }" class="white--text text-decoration-none"
          >로그인</router-link
        >
      </v-btn>
      <v-btn text>
        <router-link to="/signup" class="white--text text-decoration-none">가입하기</router-link>
      </v-btn>

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
          <v-list-item v-for="(item, i) in items" :key="i">
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
  </v-card>
</template>

<script>
export default {
  data: () => ({
    items: [{ title: '마이페이지' }, { title: '로그아웃' }],
  }),
};
</script>
