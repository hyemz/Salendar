<template>
  <v-app>
    <!-- 헤더 -->
    <Toolbar />

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import Toolbar from '@/components/Toolbar';

export default {
  name: 'App',

  components: {
    Toolbar,
  },

  data: () => ({
    //
  }),
};
</script>
<style scoped>
#title {
  font-family: '777Balsamtint';
}
@font-face {
  font-family: '777Balsamtint';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_nine_@1.1/777Balsamtint.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
</style>
