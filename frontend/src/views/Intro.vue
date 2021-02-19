<template>
  <div id="intropage">
    <div id="intro" style="height:1200px;" class="d-flex flex-column align-center justify-center">
      <div
        data-aos="fade-up"
        data-aos-duration="2000"
        class="container d-flex flex-column justify-center align-center mx-auto"
      >
        <img src="../assets/logo.png" alt="" style="width:500px" />
      </div>
    </div>

    <br /><br /><br />
    <svg xmlns="http://www.w3.org/2000/svg"></svg>
    <div class="container">
      <v-row data-aos="fade-right" data-aos-duration="3000" data-aos-delay="300">
        <v-spacer></v-spacer>
        <v-col class="d-flex flex-column justify-center text-center" cols="5">
          <p><strong>바쁜 일상</strong> 속</p>
          <p>사야 할 것이</p>
          <p>너무 많은 우리</p>
        </v-col>
        <v-col class="text-center" cols="4">
          <img src="../assets/Intro/shopping-animate.min.svg" style="" alt="" />
        </v-col>
        <v-spacer></v-spacer>
      </v-row>
      <br /><br /><br /><br /><br /><br /><br /><br /><br />
      <v-row data-aos="fade-left" data-aos-duration="3000" data-aos-delay="300">
        <v-spacer></v-spacer>
        <v-col class="text-center" cols="4">
          <img src="../assets/Intro/Cyber Monday-rafiki.svg" style="" alt="" />
        </v-col>
        <v-col class="d-flex flex-column justify-center text-center" cols="6">
          <p>나에게 <strong>딱 맞는 세일</strong>은</p>
          <p>지나쳐버리기 쉽상이죠</p>
        </v-col>
        <v-spacer></v-spacer>
      </v-row>
      <br /><br /><br /><br /><br /><br /><br /><br /><br />
      <v-row data-aos="fade-right" data-aos-duration="3000" data-aos-delay="300">
        <v-spacer></v-spacer>
        <v-col class="d-flex flex-column justify-center text-center" cols="5">
          <p>한 눈에 보기 쉽게</p>
          <p><strong>달력</strong>에 표시해주면</p>
          <p>참 좋을텐데</p>
        </v-col>
        <v-col class="text-center" cols="4">
          <img src="../assets/Intro/Timeline-bro.svg" style="" alt="" />
        </v-col>
        <v-spacer></v-spacer>
      </v-row>
      <br /><br /><br /><br /><br /><br /><br /><br /><br />
      <v-row data-aos="fade-up" data-aos-duration="3000" data-aos-delay="300">
        <v-spacer></v-spacer>
        <v-col class="d-flex flex-column justify-center align-center text-center" cols="8">
          <p>원하는 세일정보를 한눈에</p>
          <p>다가오는 세일에 가슴이 두근두근</p>
          <p><strong>설레이는 세일 캘린더</strong></p>
          <p>설렌다 입니다.</p>
          <v-spacer></v-spacer>
          <br /><br /><br /><br />
          <v-hover v-slot="{ hover }">
            <router-link to="/salelist" class="font-weight-bold text-decoration-none"
              ><v-btn
                v-if="!hover"
                x-large
                rounded
                outlined
                color="sub"
                id="start"
                height="70px"
                width="290px"
              >
                get started
              </v-btn>
              <v-btn
                v-else-if="hover"
                x-large
                rounded
                color="sub"
                id="start"
                height="70px"
                width="290px"
              >
                get started
              </v-btn></router-link
            ></v-hover
          >
          <!-- <img src="../assets/logo.png" style="width:25rem" alt="" /> -->
        </v-col>
        <v-spacer></v-spacer>
      </v-row>
      <br /><br /><br /><br /><br /><br /><br /><br /><br />
    </div>
  </div>
</template>

<script>
import AOS from 'aos';
import 'aos/dist/aos.css';

export default {
  name: 'Intro',
  created() {
    AOS.init({
      // Global settings:
      disable: false, // accepts following values: 'phone', 'tablet', 'mobile', boolean, expression or function
      startEvent: 'DOMContentLoaded', // name of the event dispatched on the document, that AOS should initialize on
      initClassName: 'aos-init', // class applied after initialization
      animatedClassName: 'aos-animate', // class applied on animation
      useClassNames: false, // if true, will add content of `data-aos` as classes on scroll
      disableMutationObserver: false, // disables automatic mutations' detections (advanced)
      debounceDelay: 50, // the delay on debounce used while resizing window (advanced)
      throttleDelay: 99, // the delay on throttle used while scrolling the page (advanced)

      // Settings that can be overridden on per-element basis, by `data-aos-*` attributes:
      offset: 0, // offset (in px) from the original trigger point
      delay: 0, // values from 0 to 3000, with step 50ms
      duration: 400, // values from 0 to 3000, with step 50ms
      easing: 'ease', // default easing for AOS animations
      once: false, // whether animation should happen only once - while scrolling down
      mirror: false, // whether elements should animate out while scrolling past them
      anchorPlacement: 'top-bottom', // defines which position of the element regarding to window should trigger the animation
    });
  },
};
</script>

<style>
#intro {
  position: relative;
  z-index: 1;
}
#intro::after {
  width: 100%;
  height: 100%;
  content: '';
  background-image: url('../assets/Intro/bg.jpg');
  background-size: cover;
  opacity: 0.5;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
}
#intropage {
  font-family: 'GmarketSansLight';
}
@font-face {
  font-family: 'GmarketSansLight';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansLight.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'GmarketSansMedium';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}
p {
  font-size: 3rem;
}
#start {
  font-family: 'GmarketSansMedium';
  font-size: 1.3rem;
  align-items: center;
  color: white;
}
strong {
  color: #00aeff;
}
</style>
