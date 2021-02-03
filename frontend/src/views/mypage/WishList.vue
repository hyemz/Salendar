<template>
  <v-container fluid>
    <v-card flat>
      <v-col class="col-md-2 offset-md-2">
        <h1>찜 목록</h1>
      </v-col>
      <!-- <v-btn @click="follow">팔로우</v-btn> -->
      <v-card class="mx-auto" max-width="1000" flat>
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
import axiosClient from '../../lib/axiosClient';
import { mapState } from 'vuex'

export default {
  data: () => ({
    cards: [
      {
        title: '아리따움',
        storeName: 'Aritaum',
        src: require('@/assets/Logo/Aritaum.png'),
        flex: 6,
        badge: false,
        show: false,
      },
      {
        title: '에뛰드하우스',
        storeName: 'Etude',
        src: require('@/assets/Logo/Etude.png'),
        flex: 6,
        badge: false,
        show: false,
      },
      {
        title: '이니스프리',
        storeName: 'Innisfree',
        src: require('@/assets/Logo/Innisfree.png'),
        flex: 6,
        badge: false,
        show: false,
      },
      {
        title: '랄라블라',
        storeName: 'Lalavla',
        src: require('@/assets/Logo/Lalavla.png'),
        flex: 6,
        badge: true,
        show: false,
      },
      {
        title: '미샤',
        storeName: 'Missha',
        src: require('@/assets/Logo/Missha.png'),
        flex: 6,
        badge: true,
        show: false,
      },
      {
        title: '올리브영',
        storeName: 'Oliveyoung',
        src: require('@/assets/Logo/Oliveyoung.png'),
        flex: 6,
        badge: true,
        show: false,
      },
      {
        title: '더페이스샵',
        storeName: 'Thefaceshop',
        src: require('@/assets/Logo/Thefaceshop.png'),
        flex: 6,
        badge: true,
        show: false,
      },
      {
        title: '토니모리',
        storeName: 'Tonymoly',
        src: require('@/assets/Logo/Tonymoly.png'),
        flex: 6,
        badge: true,
        show: false,
      },
    ],
  }),
  computed: {
    ...mapState(['following']),
  },
  created: function() {
    this.$store.dispatch('updateFollowing', true)
    for (let index = 0; index < this.cards.length; index++) {
      this.cards[index].show = this.following[this.cards[index].storeName];
    }
  },
  watch: {
    following: function () {
      for (let index = 0; index < this.cards.length; index++) {
        this.cards[index].show = this.following[this.cards[index].storeName];
      }
    }
  },
  methods: {
    // 테스트를 위한 팔로우 함수
    follow() {
      axiosClient
        .post(`/api/user/token/follow/Aritaum`)
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log('찜 목록이 삭제 되지 못했습니다.', err);
        });
    },

    // 팔로우 취소
    removeCard(i) {
      alert(this.cards[i].title + '를 찜 목록에서 삭제합니다.');
      axiosClient
        .post(`/api/user/token/unfollow/${this.cards[i].storeName}`)
        .then((res) => {
          console.log(res);
          this.$store.dispatch('updateFollowing', true)
          alert(this.cards[i].title + '가 찜 목록에서 삭제되었습니다.');
        })
        .catch((err) => {
          console.log('찜 목록이 삭제 되지 못했습니다.', err);
        });
      this.cards.splice(this.cards[i], 1);
    },
  },
};
</script>
