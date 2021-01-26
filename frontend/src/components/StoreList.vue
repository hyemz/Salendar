<template>
  <div>
    <v-card max-width="500" class="mx-auto" flat>
      <v-toolbar flat>
        <!-- <v-app-bar-nav-icon></v-app-bar-nav-icon> -->

        <v-toolbar-title>매장 목록</v-toolbar-title>

        <v-spacer></v-spacer>

        <v-btn icon @click="show = !show">
          <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
        </v-btn>
      </v-toolbar>
      <v-list two-line>
        <v-list-item-group v-model="selected" active-class="white--text" multiple v-show="show">
          <template v-for="(item, index) in items">
            <v-list-item :key="item.title">
              <template v-slot:default="{ active }">
                <v-list-item-content>
                  <v-list-item-title v-text="item.title"></v-list-item-title>
                  <v-card flat :height="item.avatar.flex" class="align-center">
                    <v-img :src="item.avatar" class="white--text align-center"></v-img>
                  </v-card>
                </v-list-item-content>

                <v-list-item-action>
                  <v-list-item-action-text v-text="item.title"></v-list-item-action-text>

                  <v-icon v-if="!active" color="grey lighten-1">
                    mdi-heart-outline
                  </v-icon>

                  <v-icon v-else color="pink darken-1">
                    mdi-heart
                  </v-icon>
                </v-list-item-action>
              </template>
            </v-list-item>

            <v-divider v-if="index < items.length - 1" :key="index"></v-divider>
          </template>
        </v-list-item-group>
      </v-list>
    </v-card>
  </div>
</template>
<script>
export default {
  data() {
    return {
      selected: [2],
      show: false,
      items: [
        {
          title: '올리브영',
          avatar:
            'https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbqDcKW%2FbtqwO6vv4to%2F7FPyH3g06HP63Pek7sw14k%2Fimg.jpg',
        },
        {
          title: '랄라블라',
          avatar: 'https://blog.kakaocdn.net/dn/b5q55v/btqwOBWQnuK/vIHQXK4M218BbvAuAGnEtk/img.jpg',
        },
        {
          title: '이니스프리',
          avatar: 'https://blog.kakaocdn.net/dn/zoFUG/btqxhyrRYKy/UXmpubpxrivvbykDyOzHN1/img.jpg',
        },
        {
          title: '더페이스샵',
          avatar:
            'https://storage.googleapis.com/leebangin/2019/07/%EB%8D%94%ED%8E%98%EC%9D%B4%EC%8A%A4%EC%83%B5.png',
        },
        {
          title: '에뛰드하우스',
          avatar:
            'https://dn5hzapyfrpio.cloudfront.net/var/app/current/common/upload/20200312/1583977882405.jpg',
        },
        { title: '미샤', avatar: 'https://www.kdfnews.com/news/photo/201804/30450_1_1.jpg' },
        {
          title: '아리따움',
          avatar:
            'https://images.squarespace-cdn.com/content/v1/5b35dd4b36099b1e5fe89dfb/1534156588788-2KO5Z5FI24DCGWSN9J8J/ke17ZwdGBToddI8pDm48kFIWX6rNQTHLgnk4fj-bOoEUqsxRUqqbr1mOJYKfIPR7LoDQ9mXPOjoJoqy81S2I8N_N4V1vUb5AoIIIbLZhVYy7Mythp_T-mtop-vrsUOmeInPi9iDjx9w8K4ZfjXt2dtlaSK0QYhS8AqkU1PVV8zY-5Sg2w9EXBnlxgm_eLri7ZDqXZYzu2fuaodM4POSZ4w/Aritaum.jpg',
        },
        {
          title: '토니모리',
          avatar: 'https://blog.kakaocdn.net/dn/C229R/btqxpzJDXuF/kqw62SeBzYA0UkQ0rbchD1/img.jpg',
        },
      ],
    };
  },
};
</script>
<style scoped>
div {
  flex: 0.6;
}
</style>
