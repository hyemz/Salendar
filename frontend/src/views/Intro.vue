<template>
  <div>
    <div
      id="intro"
      style="height:1200px;"
      class="d-flex flex-column align-center justify-center"
      >
      <div class="container d-flex flex-column justify-center align-center mx-auto" >
        <img data-aos="fade-up" src="../assets/logo.png" alt="" style="width:500px">
        <h2>설레이는 세일 캘린더</h2>
        <h1>'설렌다'</h1>
      </div>
    </div>
    
    
    <svg xmlns="http://www.w3.org/2000/svg">
    
    </svg>
    <div class="d-flex flex-column align-center">

      <img src="..\assets\Intro\shopping-animate.min.svg" style="width:600px" alt="">
      <img src="..\assets\Intro\shopping-animate.min.svg" style="width:600px" alt="">
      <img src="..\assets\Intro\shopping-animate.svg" style="width:600px" alt="">
      <img src="..\assets\Intro\shopping-animate.svg" style="width:600px" alt="">

    </div>

  </div>
  
</template>

<script>
import AOS from 'aos'
import "aos/dist/aos.css";

export default {
  name:'intro',
  created() {
    AOS.init();
  },
}

</script>

<style>
#intro {
  position: relative;
  z-index: 1;
}
#intro::after {
  width: 100%;
  height: 100%;
  content:"";
  background-image: url('https://pixabay.com/get/g861af49c095642949e8f9feb9d2eea3996b09003b48f4fe9c90d4ff9b30ca084b159565c4cf56ae7460003be533fa673.jpg');
  background-size:cover;
  opacity: 0.5;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
}
</style>