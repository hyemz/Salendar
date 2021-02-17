<template>
  <v-app id="main">
    <!-- <Toolbar /> -->
    <router-view name="header" />
    <v-main>
      <router-view />
    </v-main>
    <router-view name="footer" />
  </v-app>
</template>

<script>
// import Toolbar from '@/components/Toolbar';
// import Footer from '@/components/Footer';
export default {
  name: 'App',

  components: {
    // Toolbar,
    // Footer,
  },

  data: () => ({
    //
  }),
};
</script>
<style>
#main {
  font-family: 'NanumBarunGothic';
}
#intropage {
  font-family: 'GmarketSansLight';
}

@font-face {
  font-family: '777Balsamtint';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_nine_@1.1/777Balsamtint.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'NanumBarunGothic';
  font-style: normal;
  font-weight: 400;
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot');
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot?#iefix')
      format('embedded-opentype'),
    url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.woff') format('woff'),
    url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.ttf') format('truetype');
}
@font-face {
  font-family: 'GmarketSansLight';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansLight.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'S-CoreDream-3Light';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-3Light.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'InfinitySans-RegularA1';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-RegularA1.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'NEXON Lv1 Gothic OTF';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'ChosunSg';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@1.0/ChosunSg.woff')
    format('woff');
  font-weight: medium;
  font-style: normal;
}
</style>
