<template>
  <v-container fluid>
    <v-card flat>
      <!-- <v-btn @click="follow">팔로우</v-btn> -->
      <v-card class="mx-auto" max-width="1000" flat>
        <v-col class="d-flex flex-column justify-center mt-12">
          <h1>찜 목록</h1>
        </v-col>
        <v-row dense rows="12">
          <div
            v-for="(card, i) in cards"
            :key="card.title"
            :cols="card.flex"
            class="d-flex flex-column justify-center align-center mt-2"
          >
            <v-col v-if="card.show">
              <v-hover v-slot="{ hover }">
                <v-card
                  :elevation="hover ? 12 : 1.5"
                  :class="{ 'on-hover': hover }"
                  id="c"
                  height="250"
                >
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-col cols="9">
                      <v-badge
                        :value="card.badge"
                        color="deep-purple accent-4"
                        content="세일중"
                        left
                        transition="slide-x-transition"
                      >
                      </v-badge>
                    </v-col>
                    <v-btn icon @click="unFollow(i)">
                      <v-hover v-slot="{ hover }">
                        <v-icon v-if="!hover">mdi-minus-circle</v-icon>
                        <v-icon v-else-if="hover" color="deep-orange darken-3"
                          >mdi-minus-circle</v-icon
                        >
                      </v-hover>
                    </v-btn>
                  </v-card-actions>

                  <router-link to="/calendar" class="text-decoration-none"
                    ><v-img
                      :src="card.src"
                      class="white--text d-flex flex-column justify-center"
                      gradient="to bottom, rgba(255,255,250,.3), rgba(0,0,10,.16)"
                      height="100"
                    >
                      <v-card-title v-text="card.title"></v-card-title> </v-img
                  ></router-link>
                </v-card>
              </v-hover>
            </v-col>
          </div>
        </v-row>
      </v-card>
    </v-card>
  </v-container>
</template>
<script>
import axiosClient from '../../lib/axiosClient';
import axiosDefault from '../../lib/axiosDefault';
import axios from 'axios';
import { mapState } from 'vuex';

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
    axiosDefault
      .get(`/api/sale/storelist`)
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log('매장 정보를 불러오지 못했어요.', err);
      });

    this.$store.dispatch('updateFollowing', true);
    for (let index = 0; index < this.cards.length; index++) {
      this.cards[index].show = this.following[this.cards[index].storeName];
    }
  },
  watch: {
    following: function() {
      for (let index = 0; index < this.cards.length; index++) {
        this.cards[index].show = this.following[this.cards[index].storeName];
      }
    },
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
    unFollow(i) {
      const headers = {
        'x-auth-token': localStorage.getItem('jwt'),
      };
      const baseURL = 'http://localhost:8080';
      axios
        .create({
          baseURL,
          headers,
        })
        .post(`/api/user/token/unfollow/${this.cards[i].storeName}`)
        .then((res) => {
          console.log(res);
          this.$store.dispatch('updateFollowing', true);
          alert(this.cards[i].title + '매장이 찜 목록에서 삭제되었습니다.');
        })
        .catch((err) => {
          console.log('찜 목록이 삭제 되지 못했습니다.', err);
        });
    },
  },
};
</script>
<style scoped>
#c {
  transition: opacity 0.4s ease-in-out;
}

#c:not(.on-hover) {
  opacity: 0.9;
}
</style>
