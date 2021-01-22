<template>
  <v-container fluid>
    <v-card flat>
      <v-col class="col-md-2 offset-md-2">
        <h1>찜 목록</h1>
      </v-col>

      <v-card class="mx-auto" max-width="1000">
        <v-row dense rows="12">
          <v-col v-for="(card, i) in cards" :key="card.title" :cols="card.flex">
            <v-card v-if="card.show">
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-col cols="10">
                  <v-badge
                    :value="card.badge"
                    color="deep-purple accent-4"
                    content="세일중"
                    left
                    transition="slide-x-transition"
                  >
                  </v-badge>
                </v-col>
                <v-btn icon @click="removeCard(i)">
                  <v-icon>mdi-minus-circle</v-icon>
                </v-btn>
              </v-card-actions>

              <router-link to="/calendar" class="white--text text-decoration-none"
                ><v-img
                  :src="card.src"
                  class="white--text align-end"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.3)"
                  height="200px"
                >
                  <v-card-title v-text="card.title"></v-card-title> </v-img
              ></router-link>
            </v-card>
          </v-col>
        </v-row>
      </v-card>
    </v-card>
  </v-container>
</template>
<script>
export default {
  data: () => ({
    cards: [
      {
        title: '올리브영',
        src: 'https://na001.leafletcdns.com/kr/data/21/logo.png',
        flex: 6,
        badge: false,
        show: true,
      },
      {
        title: '랄라블라',
        src: 'https://blog.kakaocdn.net/dn/b5q55v/btqwOBWQnuK/vIHQXK4M218BbvAuAGnEtk/img.jpg',
        flex: 6,
        badge: true,
        show: true,
      },
      {
        title: '롭스',
        src:
          'https://s3-ap-northeast-2.amazonaws.com/blog.lotte.co.kr/wp-content/uploads/2013/05/%EB%A1%AD%EC%8A%A4-BI1.jpg',
        flex: 6,
        badge: true,
        show: true,
      },
      {
        title: '이니스프리',
        src: 'https://blog.kakaocdn.net/dn/zoFUG/btqxhyrRYKy/UXmpubpxrivvbykDyOzHN1/img.jpg',
        flex: 6,
        badge: false,
        show: true,
      },
    ],
  }),
  methods: {
    removeCard(i) {
      alert(this.cards[i].title + '를 찜 목록에서 삭제합니다.');
      this.cards.splice(this.cards[i].title, 1);
    },
  },
};
</script>
