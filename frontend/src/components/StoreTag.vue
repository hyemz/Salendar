<template>
  <div class="d-flex justify-center mb-6">
    <v-card
      flat
      color="white"
      outlined
      width="70%"
      class="d-flex flex-column justify-center align-center mt-12 sm-8"
    >
      <div class="text-center mt-2 mb-5 sm-9" style="width:1000px">
        <span v-for="store in stores" :key="store.title">
          <v-chip
            id="desc"
            class="ma-3"
            outlined
            :color="store.followed ? 'sub' : 'grey lighten-1'"
            :text-color="store.followed ? 'sub' : 'grey lighten-1'"
            @click="follow(store)"
          >
            {{ store.title }}
            <v-icon color="sub" right v-if="store.followed">mdi-heart</v-icon>
          </v-chip>
        </span>
      </div>
    </v-card>
  </div>
</template>
<script>
import axios from 'axios';
import { mapState } from 'vuex';
export default {
  data() {
    return {
      selected: [],
      show: true,
      stores: [
        {
          title: '올리브영',
          avatar: require('@/assets/Logo/Oliveyoung.png'),
          storeName: 'Oliveyoung',
          followed: false,
        },
        {
          title: '랄라블라',
          avatar: require('@/assets/Logo/Lalavla.png'),
          storeName: 'Lalavla',
          followed: false,
        },
        // {
        //   title: '이니스프리',
        //   avatar: require('@/assets/Logo/Innisfree.png'),
        //   storeName: 'Innisfree',
        //   followed: false,
        // },
        {
          title: '더페이스샵',
          avatar: require('@/assets/Logo/Thefaceshop.png'),
          storeName: 'Thefaceshop',
          followed: false,
        },
        {
          title: '에뛰드하우스',
          avatar: require('@/assets/Logo/Etude.png'),
          storeName: 'Etude',
          followed: false,
        },
        {
          title: '미샤',
          avatar: require('@/assets/Logo/Missha.png'),
          storeName: 'Missha',
          followed: false,
        },
        {
          title: '아리따움',
          avatar: require('@/assets/Logo/Aritaum.png'),
          storeName: 'Aritaum',
          followed: false,
        },
        {
          title: '토니모리',
          avatar: require('@/assets/Logo/Tonymoly.png'),
          storeName: 'Tonymoly',
          followed: false,
        },
      ],
    };
  },
  computed: {
    ...mapState(['following', 'isLogin']),
  },
  watch: {
    selected: function() {
      this.$emit('select', this.selected);
    },
    following: function() {
      this.selected = [];
      for (const idx in this.stores) {
        if (this.following[this.stores[idx].storeName]) {
          this.stores[idx].followed = true;
          this.selected.push(Number(idx));
        }
      }
    },
  },
  created() {
    this.$store.dispatch('updateFollowing', true);
    this.$emit('select', this.selected);
  },
  methods: {
    follow(store) {
      if (!this.isLogin) {
        alert('로그인이 필요한 서비스입니다.');
        this.$router.push('/login');
      } else {
        if (store.followed) {
          alert(store.title + ' 매장을 언팔로우 했습니다');
          store.followed = false;
          this.unFollow(store.storeName);
        } else {
          alert(store.title + ' 매장을 팔로우 했습니다');
          store.followed = true;
          this.Follow(store.storeName);
        }
      }
    },
    Follow(storeName) {
      const headers = {
        'x-auth-token': localStorage.getItem('jwt'),
      };
      const baseURL = 'http://i4a301.p.ssafy.io:8080';
      axios
        .create({
          baseURL,
          headers,
        })
        .post(`/api/user/token/follow/${storeName}`)
        .then((res) => {
          console.log(res);
          this.$store.dispatch('updateFollowing', true);
        })
        .catch((err) => {
          console.log('팔로우에 실패했습니다.', err);
        });
    },

    unFollow(storeName) {
      const headers = {
        'x-auth-token': localStorage.getItem('jwt'),
      };
      const baseURL = 'http://i4a301.p.ssafy.io:8080';
      axios
        .create({
          baseURL,
          headers,
        })
        .post(`/api/user/token/unfollow/${storeName}`)
        .then((res) => {
          console.log(res);
          this.$store.dispatch('updateFollowing', true);
        })
        .catch((err) => {
          console.log('언팔로우에 실패했습니다.', err);
        });
    },
  },
};
</script>
<style scoped>
#desc {
  font-family: 'NanumBarunGothic';
}
@font-face {
  font-family: 'NanumBarunGothic';
  font-style: normal;
  font-weight: 1000;
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot');
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot?#iefix')
      format('embedded-opentype'),
    url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.woff') format('woff'),
    url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.ttf') format('truetype');
}
</style>
