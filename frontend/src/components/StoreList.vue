<template>
  <div>
    <v-card max-width="350" class="mx-auto" flat>
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
                  <!-- <v-list-item-title v-text="item.title"></v-list-item-title> -->
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
import getFollowing from '../lib/getFollowing'

export default {
  data() {
    return {
      selected: [2],
      show: true,
      items: [
        {
          title: '올리브영',
          avatar: require('@/assets/storeLogo/oliveyoung.png'),
        },
        {
          title: '랄라블라',
          avatar: require('@/assets/storeLogo/lalavla.png'),
        },
        {
          title: '이니스프리',
          avatar: require('@/assets/storeLogo/innisfree.png'),
        },
        {
          title: '더페이스샵',
          avatar: require('@/assets/storeLogo/thefaceshop.png'),
        },
        {
          title: '에뛰드하우스',
          avatar: require('@/assets/storeLogo/etudehouse.png'),
        },
        {
          title: '미샤', 
          avatar: require('@/assets/storeLogo/missha.png') 
        },
        {
          title: '아리따움',
          avatar: require('@/assets/storeLogo/aritaum.png'),
        },
        {
          title: '토니모리',
          avatar: require('@/assets/storeLogo/tonymoly.png'),
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
    getFollowing;
    this.$emit('select', this.selected)
    
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
