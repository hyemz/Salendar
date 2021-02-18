<template>
  <v-container class="mt-12">
    <v-row no-gutters>
      <v-col cols="12" sm="1"></v-col>
      <v-col cols="12" sm="10">
        <div class="mb-10">
          <v-card flat class=" mt-12">
            <div class="titlefont"><strong>찜</strong>한 매장 목록</div>
          </v-card>
          <v-divider class="mt-12 mb-9"></v-divider>
        </div>
        <v-card flat>
          <v-card class="mx-auto mb-12" max-width="1000" flat>
            <v-col class="d-flex flex-column justify-center align-center mt-12"> </v-col>
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
                      :elevation="hover ? 3 : 1"
                      :class="{ 'on-hover': hover }"
                      id="c"
                      height="250"
                      outlined
                    >
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-col cols="9">
                          <v-badge
                            :value="card.badge"
                            color="sub"
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
                          id="test"
                          :src="card.src"
                          class="white--text d-flex flex-column justify-center"
                          height="100"
                        >
                        </v-img
                      ></router-link>
                    </v-card>
                  </v-hover>
                </v-col>
                <!-- <v-col v-if="!card.show" v-on="counting(true)"></v-col> -->
              </div>
              <router-link to="/calendar"
                ><v-img v-if="this.isNone" :src="imgPath" max-width="1000px"></v-img
              ></router-link>
            </v-row>
          </v-card>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import axiosClient from '../../lib/axiosClient';
import axiosDefault from '../../lib/axiosDefault';
import axios from 'axios';
import { mapState } from 'vuex';

export default {
  data: () => ({
    isNone: true,
    imgPath: require('@/assets/SaleListSmall/nofavorite.png'),
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
        badge: false,
        show: false,
      },
      {
        title: '미샤',
        storeName: 'Missha',
        src: require('@/assets/Logo/Missha.png'),
        flex: 6,
        badge: false,
        show: false,
      },
      {
        title: '올리브영',
        storeName: 'Oliveyoung',
        src: require('@/assets/Logo/Oliveyoung.png'),
        flex: 6,
        badge: false,
        show: false,
      },
      {
        title: '더페이스샵',
        storeName: 'Thefaceshop',
        src: require('@/assets/Logo/Thefaceshop.png'),
        flex: 6,
        badge: false,
        show: false,
      },
      {
        title: '토니모리',
        storeName: 'Tonymoly',
        src: require('@/assets/Logo/Tonymoly.png'),
        flex: 6,
        badge: false,
        show: false,
      },
    ],
  }),
  computed: {
    ...mapState(['following']),
  },
  created: function() {
    Object.values(this.following).map((v) => {
      if (v) this.isNone = false;
    });
    // 데이터 요청
    // 팔로우 매장 정보 가져오기
    axiosDefault
      .get(`/api/sale/storelist`)
      .then((res) => {
        console.log('test', res);
      })
      .catch((err) => {
        console.log('매장 정보를 불러오지 못했어요.', err);
      });

    // 팔로우 한 세일정보 가져오기
    const headers = {
      'x-auth-token': localStorage.getItem('jwt'),
    };
    const baseURL = 'http://i4a301.p.ssafy.io:8080';
    axios
      .create({
        baseURL,
        headers,
      })
      .get('/api/sale/token/list/follow')
      .then((res) => {
        const saleData = res.data;
        this.cards.forEach((store) => {
          const storeNow = saleData[store.storeName];
          for (const index in storeNow) {
            if (
              !this.$moment(storeNow[index].sale_start_date).isAfter(
                this.$moment().format('YYYY-MM-DD')
              ) &&
              this.$moment(storeNow[index].sale_end_date).isAfter(
                this.$moment().format('YYYY-MM-DD')
              )
            ) {
              store.badge = true;
            }
          }
        });
      })
      .catch((err) => {
        console.log(err);
        this.isNone = true;
      });

    // 데이터 처리
    this.$store.dispatch('updateFollowing', true);
    for (let index = 0; index < this.cards.length; index++) {
      this.cards[index].show = this.following[this.cards[index].storeName];
    }
  },
  watch: {
    following: function() {
      this.isNone = true;
      Object.values(this.following).map((v) => {
        if (v) this.isNone = false;
      });
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
      const baseURL = 'http://i4a301.p.ssafy.io:8080';
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
    counting(bool) {
      this.isNone = bool;
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
#test {
  margin-top: 20px;
}
</style>
