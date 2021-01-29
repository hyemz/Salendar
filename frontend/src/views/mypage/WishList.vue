<template>
  <v-container fluid>
    <v-card flat>
      <v-col class="col-md-2 offset-md-2">
        <h1>찜 목록</h1>
      </v-col>

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
import axios from 'axios';
export default {
  data: () => ({
    baseURI: 'http://localhost:8080/',
    datas: [],
    cards: [
      {
        title: '올리브영',
        src: require('@/assets/storeLogo/oliveyoung.png'),
        flex: 6,
        badge: false,
        show: true,
      },
      {
        title: '랄라블라',
        src: require('@/assets/storeLogo/lalavla.png'),
        flex: 6,
        badge: true,
        show: true,
      },
      {
        title: '미샤',
        src: require('@/assets/storeLogo/missha.png'),
        flex: 6,
        badge: true,
        show: true,
      },
      {
        title: '이니스프리',
        src: require('@/assets/storeLogo/innisfree.png'),
        flex: 6,
        badge: false,
        show: true,
      },
    ],
  }),
  created: function() {
    // var headerForm = {
    //   headers: {
    //     'x-auth-token': 'Bearer ' + localStorage.getItem('jwt')
    //   }
    // }
    axios
      .create({
        headers: {
          'x-auth-token':localStorage.getItem('jwt')
        }
      })
      .get("http://localhost:8080/api/user/token/followings")
      .then((res) => {
        console.log(res);
        // this.datas = res.data;
      })
      .catch((err) => {
        console.log('찜 목록을 불러오지 못했습니다.', err);
      });
  },
  methods: {
    removeCard(i) {
      alert(this.cards[i].title + '를 찜 목록에서 삭제합니다.');
      this.cards.splice(this.cards[i], 1);
      // 삭제?
      axios.delete(`${this.baseURI}/나머지주소` + i);

      // true, false로 바꾸기?
      axios
        .patch(`${this.baseURI}/나머지주소`, this.cards[i].show)
        .then((res) => {
          console.log(res.data);
        })
        .catch((err) => {
          console('찜 목록에서 삭제를 못했어요', err);
        });
    },
  },
};
</script>
