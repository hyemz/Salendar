<template>
  <v-card class="overflow-hidden">
    <v-app-bar
      color="#6A76AB"
      
    >
      <template v-slot:img="{ props }">
        <v-img
          v-bind="props"
          gradient="to top right, rgba(100,115,201,.7), rgba(25,32,72,.7)"
        ></v-img>
      </template>

      <v-app-bar-nav-icon></v-app-bar-nav-icon>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>

      <v-btn icon>
        <v-icon>mdi-heart</v-icon>
      </v-btn>

      <v-menu
        bottom
        left
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            icon
            color="yellow"
            v-bind="attrs"
            v-on="on"
          >
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>

        <v-list>
          <v-list-item
            v-for="(item, i) in items"
            :key="i"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>

      <template v-slot:extension>
        <v-tabs align-with-title>
          <v-tab><router-link :to="{ name: 'Login'}"> Login </router-link></v-tab>
          <v-tab><router-link :to="{ name: 'Signup'}"> Signup </router-link></v-tab>
          <v-tab><router-link to="/login" class="white--text text-decoration-none mb-2 font-weight-medium">로그인</router-link></v-tab>
          <v-tab><router-link to="/signup" class="white--text text-decoration-none mb-2 font-weight-medium">가입하기</router-link></v-tab>
        </v-tabs>
      </template>
    </v-app-bar>
    <v-sheet
      id="scrolling-techniques-4"
      class="overflow-y-auto"
      max-height="600"
    >
      <v-container style="height: 1000px;"></v-container>
    </v-sheet>
  </v-card>
</template>

<script>
  export default {
    data: () => ({
      items: [
        { title: '로그인' },
        { title: '회원가입' },
        { title: '로그아웃' },
        { title: '회원탈퇴' },
      ],
    }),
  }
</script>