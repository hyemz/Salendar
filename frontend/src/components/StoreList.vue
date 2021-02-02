<template>
  <div>
    <v-card max-width="350" class="mx-auto" flat>
      <v-toolbar flat>

        <v-toolbar-title>매장 목록</v-toolbar-title>
        <v-spacer></v-spacer>

        <v-btn icon @click="show = !show">
          <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
        </v-btn>
      </v-toolbar>

      <v-list two-line>
        <v-list-item-group v-model="selected" active-class="white--text" multiple v-show="show">
          
          <template v-for="(item, index) in items">
            <v-list-item :key="item.title" @click="follow(item)">
              <template v-slot:default="{ active }">
                
                <v-list-item-content>
                  <v-card flat>
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
import axiosClient from '../lib/axiosClient'

export default {
  data() {
    return {
      selected: [],
      show: true,
      following: [],
      items: [
        {
          title: '올리브영',
          avatar: require('@/assets/storeLogo/Oliveyoung.png'),
          storeName: 'Oliveyoung',
          followed: false,
        },
        {
          title: '랄라블라',
          avatar: require('@/assets/storeLogo/Lalavla.png'),
          storeName: 'Lalavla',
          followed: false,
        },
        {
          title: '이니스프리',
          avatar: require('@/assets/storeLogo/Innisfree.png'),
          storeName: 'Innisfree',
          followed: false,
        },
        {
          title: '더페이스샵',
          avatar: require('@/assets/storeLogo/Thefaceshop.png'),
          storeName: 'Thefaceshop',
          followed: false,
        },
        {
          title: '에뛰드하우스',
          avatar: require('@/assets/storeLogo/Etude.png'),
          storeName: 'Etude',
          followed: false,
        },
        {
          title: '미샤', 
          avatar: require('@/assets/storeLogo/Missha.png'),
          storeName: 'Missha',
          followed: false,
        },
        {
          title: '아리따움',
          avatar: require('@/assets/storeLogo/Aritaum.png'),
          storeName: 'Aritaum',
          followed: false,
        },
        {
          title: '토니모리',
          avatar: require('@/assets/storeLogo/Tonymoly.png'),
          storeName: 'Tonymoly',
          followed: false,
        },
      ],
    };
  },
  watch: {
    selected: function () {
      this.$emit('select', this.selected)
    }
  },
  created () {  
    axiosClient
      .get("/api/user/token/followings")
      .then((res) => {
        this.following = res.data
        for (const idx in this.items){
          if (this.following[this.items[idx].storeName]) {
            this.items[idx].followed = true
            this.selected.push(Number(idx))
          }
        }
      })
      .catch((err) => {
        console.log('찜 목록을 불러오지 못했습니다.', err);
      });
    this.$emit('select', this.selected)
    
  },
  methods: {
    follow(item) {
      console.log(item)
      if(item.followed){
        alert( item.title + " 매장을 언팔로우 했습니다")
        item.followed = false
        this.unFollow(item.storeName)
      } else{
        alert( item.title + " 매장을 팔로우 했습니다")
        item.followed = true
        this.Follow(item.storeName)
      }
    },
    Follow(storeName) {
      axiosClient
      .post(`/api/user/token/follow/${storeName}`)
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log("팔로우에 실패했습니다.", err);
      });
    },
    
    unFollow(storeName) {
      axiosClient
      .post(`/api/user/token/unfollow/${storeName}`)
      .then((res) => {
        console.log(res);
      })
      .catch((err) => {
        console.log("언팔로우에 실패했습니다.", err);
      });
    },
    
  }
};
</script>
<style scoped>
div {
  flex: 0.55;
}

.v-toolbar__title {
  font-size: 1rem;
}
</style>
